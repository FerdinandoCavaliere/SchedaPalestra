package it.ermete.mercurio.schedapalestra;

import android.Manifest;
import android.support.v4.app.DialogFragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class StoricoActivity extends AppCompatActivity
        implements DialogConfermaEliminazioneScheda.NoticeDialogEliminazioneSchedaListener {

    private SchedeController schedeController;

    ArrayList<SchedaDaDb> elencoSchede;
    private final ArrayList<GruppoMuscolareDaXml> gruppi = new ArrayList<>(); // Elenco di tutti i gruppi da xml

    // Export scheda
    private final int WRITE_EXTERNAL_STORAGE = 1;
    private final int ELENCO_FILES = 2;
    private Enumeratori.TipoExport tipoExport;

    private DrawerLayout drawer;
    private final int ACTIVITY_DI_PROVENIENZA = 1;

    // Controllers
    private EserciziController eserciziController;

    HashSet<Integer> elencoIdSelezionatiPerExport;

    static int idSchedaAttiva = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Drawer
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.drawerAperto,
                R.string.drawerChiuso);
        toggle.syncState();

        // Setto il drawer laterale
        this.SettaListenerDrawer();

        this.SettaBackButton();

        // Per non fare andare lo smartphone in sleep
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(android.support.v4.view.GravityCompat.START)) {
            drawer.closeDrawer(android.support.v4.view.GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.CaricaElencoSchede();
        this.LeggiGruppiMuscolari();
        this.CaricaUltimaScheda();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(android.support.v4.view.GravityCompat.START)) {
            drawer.closeDrawer(android.support.v4.view.GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuItem itemGuida = menu.add(0, 2, 2, R.string.VoceMenuAiuto);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 2:
                DialogAiutoStoricoSchede aiuto = DialogAiutoStoricoSchede.getInstance();
                aiuto.show(getTransacion(), "Aiuto");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void CaricaUltimaScheda() {
        if (schedeController == null)
            schedeController = new SchedeController(StoricoActivity.this);

        idSchedaAttiva = schedeController.LeggiIdSchedaAttiva();
    }

    private void CaricaElencoSchede() {
        try {
            if (schedeController == null)
                schedeController = new SchedeController(StoricoActivity.this);

            elencoSchede = schedeController.LeggiStoricoSchede();

            SchedeAdapter schedeAdapter = new SchedeAdapter(StoricoActivity.this,
                    R.layout.scheda_list_item,
                    elencoSchede);
            ListView listViewStoricoSchede = (ListView) findViewById(R.id.listViewStoricoSchede);
            listViewStoricoSchede.setAdapter(schedeAdapter);

            if (elencoSchede == null || elencoSchede.size() == 0) {
                Toast.makeText(StoricoActivity.this,
                        R.string.testoNessunaSchedaTrovataNelloStorico,
                        Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception ex) {
            Log.d("Storico", ex.getMessage());
        }
    }

    // Leggo in memoria tutti i gruppi muscolari
    private void LeggiGruppiMuscolari() {
        try {
            // Pulisco le liste degli esercizi
            gruppi.clear();

            XmlPullParser parse = getResources().getXml(R.xml.elenco_esercizi_palestra);
            int tipo = parse.next();
            String nomeTag = "";

            GruppoMuscolareDaXml nuovoGruppo = null;
            EsercizioDaXml nuovoEsercizio = null;
            while (tipo != XmlPullParser.END_DOCUMENT)
            {
                switch(tipo)
                {
                    case XmlPullParser.START_TAG:
                        switch (parse.getName())
                        {
                            case "GruppoMuscolare":
                                nuovoGruppo = new GruppoMuscolareDaXml(
                                        Integer.parseInt(parse.getAttributeValue(null, "id")),
                                        parse.getAttributeValue(null, "nome"));
                                gruppi.add(nuovoGruppo);
                                break;
                        }
                        break;
                }
                tipo = parse.next();
            }
        }
        catch (Exception ex){
            Log.d("Eccezione XML Parser", ex.getMessage());
        }
    }

    public void CaricaSchedaSelezionata(int position) {
        int idScheda = elencoSchede.get(position).getId();

        // Modifico la scheda attiva sul DB
        SchedeController schedeController = new SchedeController(StoricoActivity.this);
        int esito = schedeController.SettaSchedaAttiva(idScheda);
        if (esito == 0) {
            // Torno alla Main Activity
            NavUtils.navigateUpFromSameTask(StoricoActivity.this);
        }
        else {
            Toast.makeText(StoricoActivity.this,
                    R.string.testoImpossibileSettareScheda,
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void EliminaSchedaSelezionata(int position) {
        int idScheda = elencoSchede.get(position).getId();
        if (idScheda != idSchedaAttiva) {
            DialogConfermaEliminazioneScheda dialog = DialogConfermaEliminazioneScheda.getInstance(idScheda);
            dialog.show(getTransacion(), "EliminaScheda");
        }
        else {
            Toast.makeText(StoricoActivity.this,
                    R.string.testoImpossibileEliminareSchedaAttiva,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void SettaBackButton() {
        ImageButton imgIndietro = (ImageButton) findViewById(R.id.imgIndietro);
        imgIndietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(StoricoActivity.this);
            }
        });
    }

    public void AggiungiIdEsercizio(int _id) {
        if (elencoIdSelezionatiPerExport == null)
            elencoIdSelezionatiPerExport = new HashSet<>();
        elencoIdSelezionatiPerExport.add(_id);
    }

    public void RimuoviIdEsercizio(int _id) {
        elencoIdSelezionatiPerExport.remove(_id);
    }

    private void SettaListenerDrawer() {
        ConstraintLayout LayoutMenuExportPdfElenco = (ConstraintLayout) findViewById(R.id.LayoutMenuExportPdfElenco);
        LayoutMenuExportPdfElenco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (elencoIdSelezionatiPerExport != null && elencoIdSelezionatiPerExport.size() > 0) {
                    tipoExport = Enumeratori.TipoExport.Elenco;
                    // Per Android 6 o superiore bisogna verificare il permesso di scrivere files
                    // a run time
                    if (ContextCompat.checkSelfPermission(StoricoActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                    {
                        ActivityCompat.requestPermissions(StoricoActivity.this,
                                new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                WRITE_EXTERNAL_STORAGE);
                    }
                    else
                    {
                        generaExport();
                    }
                }
                else {
                    Toast.makeText(StoricoActivity.this,
                            R.string.testoExportNessunaSchedaSelezionata,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        ConstraintLayout LayoutMenuExportPdfGruppi = (ConstraintLayout) findViewById(R.id.LayoutMenuExportPdfGruppi);
        LayoutMenuExportPdfGruppi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (elencoIdSelezionatiPerExport != null && elencoIdSelezionatiPerExport.size() > 0) {
                    tipoExport = Enumeratori.TipoExport.Gruppi;
                    // Per Android 6 o superiore bisogna verificare il permesso di scrivere files
                    // a run time
                    if (ContextCompat.checkSelfPermission(StoricoActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                    {
                        ActivityCompat.requestPermissions(StoricoActivity.this,
                                new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                WRITE_EXTERNAL_STORAGE);
                    }
                    else
                    {
                        generaExport();
                    }
                }
                else {
                    Toast.makeText(StoricoActivity.this,
                            R.string.testoExportNessunaSchedaSelezionata,
                            Toast.LENGTH_SHORT).show();
               }
            }
        });

        ConstraintLayout LayoutMenuExportPdfRoutine = (ConstraintLayout) findViewById(R.id.LayoutMenuExportPdfRoutine);
        LayoutMenuExportPdfRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (elencoIdSelezionatiPerExport != null && elencoIdSelezionatiPerExport.size() > 0) {
                    tipoExport = Enumeratori.TipoExport.Routine;
                    // Per Android 6 o superiore bisogna verificare il permesso di scrivere files
                    // a run time
                    if (ContextCompat.checkSelfPermission(StoricoActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                    {
                        ActivityCompat.requestPermissions(StoricoActivity.this,
                                new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                WRITE_EXTERNAL_STORAGE);
                    }
                    else
                    {
                        generaExport();
                    }
                }
                else {
                    Toast.makeText(StoricoActivity.this,
                            R.string.testoExportNessunaSchedaSelezionata,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        ConstraintLayout LayoutMenuExportPdfGiorni = (ConstraintLayout) findViewById(R.id.LayoutMenuExportPdfGiorni);
        LayoutMenuExportPdfGiorni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (elencoIdSelezionatiPerExport != null && elencoIdSelezionatiPerExport.size() > 0) {
                    tipoExport = Enumeratori.TipoExport.Giorni;
                    // Per Android 6 o superiore bisogna verificare il permesso di scrivere files
                    // a run time
                    if (ContextCompat.checkSelfPermission(StoricoActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                    {
                        ActivityCompat.requestPermissions(StoricoActivity.this,
                                new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                WRITE_EXTERNAL_STORAGE);
                    }
                    else
                    {
                        generaExport();
                    }
                }
                else {
                    Toast.makeText(StoricoActivity.this,
                            R.string.testoExportNessunaSchedaSelezionata,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        ConstraintLayout LayoutElencoExport = (ConstraintLayout) findViewById(R.id.LayoutElencoExport);
        LayoutElencoExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(StoricoActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(StoricoActivity.this,
                            new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            ELENCO_FILES);
                }
                else
                {
                    Intent intentElencoExport = new Intent(StoricoActivity.this, ElencoExportsActivity.class);
                    intentElencoExport.putExtra("PROVENIENZA", ACTIVITY_DI_PROVENIENZA);
                    startActivity(intentElencoExport);
                }
            }
        });
    }

    private void generaExport()
    {
        EditText editTextNomeExport = (EditText) findViewById(R.id.editTextNomeExport);
        if (TextUtils.isEmpty(editTextNomeExport.getText().toString())) {
            Toast.makeText(this, R.string.testoNessunNomeExport, Toast.LENGTH_SHORT).show();
        }
        else {
            switch (tipoExport) {
                case Elenco:
                    ExportMultiploPdfElencoAsyncTask exportPdfElenco = new ExportMultiploPdfElencoAsyncTask(StoricoActivity.this,
                            elencoIdSelezionatiPerExport,
                            editTextNomeExport.getText().toString());
                    exportPdfElenco.execute();
                    break;
                case Gruppi:
                    ExportMultiploPdfPerGruppiAsyncTask exportPdfPerGruppi = new ExportMultiploPdfPerGruppiAsyncTask(StoricoActivity.this,
                            elencoIdSelezionatiPerExport,
                            gruppi,
                            editTextNomeExport.getText().toString());
                    exportPdfPerGruppi.execute();
                    break;
                case Routine:
                    ExportMultiploPdfPerRoutineAsyncTask exportPdfPerRoutine = new ExportMultiploPdfPerRoutineAsyncTask(StoricoActivity.this,
                            elencoIdSelezionatiPerExport,
                            editTextNomeExport.getText().toString());
                    exportPdfPerRoutine.execute();
                    break;
                case Giorni:
                    ExportMultiploPdfPerGiornoAsyncTask exportPdfPerGiorno = new ExportMultiploPdfPerGiornoAsyncTask(StoricoActivity.this,
                            elencoIdSelezionatiPerExport,
                            editTextNomeExport.getText().toString());
                    exportPdfPerGiorno.execute();
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    generaExport();
                }
                break;
            case ELENCO_FILES:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Intent intentElencoExport = new Intent(StoricoActivity.this, ElencoExportsActivity.class);
                    intentElencoExport.putExtra("PROVENIENZA", ACTIVITY_DI_PROVENIENZA);
                    startActivity(intentElencoExport);
                }
                break;
        }
    }

    @Override
    public void onDialogEliminazioneSchedaPositiveClick(DialogFragment dialog, int numeroRigheEliminate) {
        try {
            this.CaricaElencoSchede();
        }
        catch (Exception ex) {
            Log.d("Storico", ex.getMessage());
        }
    }

    public void EseguiAzioniDopoExport(String fileName, String errore) {
        if (errore.equals("")) {
            EditText editTextNomeExport = (EditText) findViewById(R.id.editTextNomeExport);
            editTextNomeExport.setText("");
            elencoIdSelezionatiPerExport = null;
            this.CaricaElencoSchede();

            Toast.makeText(StoricoActivity.this,
                    getResources().getString(R.string.schedaEsportataConsuccesso) + fileName,
                    Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(StoricoActivity.this,
                    getResources().getString(R.string.testoExportNonCreato),
                    Toast.LENGTH_LONG).show();
        }
    }

    private FragmentTransaction getTransacion() {
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        tr.addToBackStack("null");
        return tr;
    }
}
