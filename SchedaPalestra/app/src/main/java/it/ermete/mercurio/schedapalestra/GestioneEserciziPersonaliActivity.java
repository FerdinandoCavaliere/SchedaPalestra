package it.ermete.mercurio.schedapalestra;

import android.support.v4.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class GestioneEserciziPersonaliActivity extends AppCompatActivity
        implements DialogInserimentoEsercizioPersonale.NoticeDialogInserimentoEsercizioPersonaleListener,
                   DialogModificaEsercizioPersonale.NoticeDialogModificaEsercizioPersonaleListener,
                   DialogConfermaEliminazioneEsercizioPersonale.NoticeDialogEliminazioneEsercizioPersonaleListener {

    // Gruppi muscolari
    private ArrayList<GruppoMuscolareDaXml> gruppi; // Elenco di tutti i gruppi da xml
    private ArrayList<String> nomiGruppi; // Per lo Spinner

    private int idGruppoFiltrato;
    private EserciziPersonaliController eserciziPersonaliController;

    private ArrayList<EsercizioPersonaleDaDb> eserciziPersonali;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestione_esercizi_personali);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Leggo i valori passati
        Intent intento = getIntent();
        gruppi = (ArrayList<GruppoMuscolareDaXml>) intento.getSerializableExtra("gruppi");
        nomiGruppi = intento.getStringArrayListExtra("nomiGruppi");

        // Setto listeners
        this.SettaListenerPerListView();
        this.SettaListenersBottoniSpecifici();

        this.VisualizzaEsercizi();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.AggiungiSottomenuFiltroGruppoMuscolare(menu);
        MenuItem itemGuida = menu.add(0, 300, 3, R.string.VoceMenuAiuto);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 300:
                DialogAiutoGestioneEserciziPersonali aiuto = DialogAiutoGestioneEserciziPersonali.getInstance();
                aiuto.show(getTransacion(), "Aiuto");
                break;

            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Aggiungo un sottomenu per selezionare gli esercizi di un determinato gruppo muscolare
    // inseriti in scheda
    private void AggiungiSottomenuFiltroGruppoMuscolare(Menu menu) {
        SubMenu sottomenuFiltroGruppoMuscolare = menu.addSubMenu(0, 100, 1, R.string.VoceMenuFiltroPerGruppoMuscolare);
        MenuItem nuovo;
        for (GruppoMuscolareDaXml singoloGruppo : gruppi) {
            nuovo = sottomenuFiltroGruppoMuscolare.add(0,
                    singoloGruppo.getId(),
                    singoloGruppo.getId(),
                    singoloGruppo.getGruppo());
            nuovo.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    VisualizzaEserciziByIdGruppoMuscolare(item.getItemId());
                    return true;
                }
            });
        }
        MenuItem voceTutti = sottomenuFiltroGruppoMuscolare.add(0,
                10000,
                10000,
                R.string.VoceMenuTutti);
        voceTutti.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                VisualizzaEsercizi();
                return true;
            }
        });
    }

    private void SettaListenerPerListView() {
        try {
            ListView listViewEserciziPersonali = (ListView) findViewById(R.id.listViewEserciziPersonali);

            listViewEserciziPersonali.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Verifico che l'esercizio sia modificabile
                    EsercizioPersonaleDaDb esercizioSelezionato = eserciziPersonali.get(position);
                    if (eserciziPersonaliController == null)
                        eserciziPersonaliController = new EserciziPersonaliController(GestioneEserciziPersonaliActivity.this);
                    boolean possibileModificare = eserciziPersonaliController.VerificaSePossibiliUpdateDelete(esercizioSelezionato);
                    if (possibileModificare) {
                        DialogModificaEsercizioPersonale dialog = DialogModificaEsercizioPersonale.getInstance(esercizioSelezionato);
                        dialog.show(getTransacion(), "ModificaEsercizioPersonale");
                    }
                    else {
                        Toast.makeText(GestioneEserciziPersonaliActivity.this,
                                R.string.testoNonPossibileModificareCancellareEsercizioPersonale,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });

            listViewEserciziPersonali.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    EsercizioPersonaleDaDb esercizioSelezionato = eserciziPersonali.get(position);
                    if (eserciziPersonaliController == null)
                        eserciziPersonaliController = new EserciziPersonaliController(GestioneEserciziPersonaliActivity.this);
                    boolean possibileModificare = eserciziPersonaliController.VerificaSePossibiliUpdateDelete(esercizioSelezionato);
                    if (possibileModificare) {
                        DialogConfermaEliminazioneEsercizioPersonale dialog = DialogConfermaEliminazioneEsercizioPersonale.getInstance(esercizioSelezionato);
                        dialog.show(getTransacion(), "EliminazioneEsercizioPersonale");
                    }
                    else {
                        Toast.makeText(GestioneEserciziPersonaliActivity.this,
                                R.string.testoNonPossibileModificareCancellareEsercizioPersonale,
                                Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
            });
        }
        catch (Exception ex) {
            Log.d("EserciziPersonali", ex.getMessage());
        }
    }

    private void SettaListenersBottoniSpecifici() {
        try {
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogInserimentoEsercizioPersonale dialog = DialogInserimentoEsercizioPersonale.getInstance(nomiGruppi, gruppi);
                    dialog.show(getTransacion(), "InserimentoEsercizioPersonale");
                }
            });
        }
        catch (Exception ex) {
            Log.d("EserciziPersonali", ex.getMessage());
        }
    }

    private void VisualizzaEsercizi() {
        try {
            idGruppoFiltrato = -1;
            if (eserciziPersonaliController == null)
                eserciziPersonaliController = new EserciziPersonaliController(GestioneEserciziPersonaliActivity.this);
            eserciziPersonali = eserciziPersonaliController.LeggiEserciziPersonali(gruppi);

            ListView listViewEserciziPersonali = (ListView) findViewById(R.id.listViewEserciziPersonali);
            EserciziPersonaliAdapter adapter = new EserciziPersonaliAdapter(GestioneEserciziPersonaliActivity.this,
                    R.layout.esercizio_personale_list_item,
                    eserciziPersonali);
            listViewEserciziPersonali.setAdapter(adapter);

            if (eserciziPersonali == null || eserciziPersonali.size() == 0) {
                Toast.makeText(GestioneEserciziPersonaliActivity.this,
                        R.string.testoNessunEsercizioPersonale,
                        Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception ex) {
            Log.d("VisualizzaEsercizi", ex.getMessage());
        }
    }

    private void VisualizzaEserciziByIdGruppoMuscolare(int idGruppo) {
        try {
            idGruppoFiltrato = idGruppo;
            if (eserciziPersonaliController == null)
                eserciziPersonaliController = new EserciziPersonaliController(GestioneEserciziPersonaliActivity.this);
            eserciziPersonali = eserciziPersonaliController.LeggiEserciziPersonaliConIdGruppo(gruppi, idGruppo);

            ListView listViewEserciziPersonali = (ListView) findViewById(R.id.listViewEserciziPersonali);
            EserciziPersonaliAdapter adapter = new EserciziPersonaliAdapter(GestioneEserciziPersonaliActivity.this,
                    R.layout.esercizio_personale_list_item,
                    eserciziPersonali);
            listViewEserciziPersonali.setAdapter(adapter);

            if (eserciziPersonali == null || eserciziPersonali.size() == 0) {
                Toast.makeText(GestioneEserciziPersonaliActivity.this,
                        R.string.testoNessunEsercizioPersonaleDelGruppoMuscolareSelezionato,
                        Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception ex) {
            Log.d("VisualizzaEsIdGruppo", ex.getMessage());
        }
    }

    public void AggiornaListViewDopoOperazione() {
        try {
            if (idGruppoFiltrato == -1)
                VisualizzaEsercizi();
            else {
                VisualizzaEserciziByIdGruppoMuscolare(idGruppoFiltrato);
            }
        }
        catch (Exception ex) {
            Log.d("EserciziPersonali", ex.getMessage());
        }
    }

    @Override
    public void onDialogInserimentoEsercizioNegativeClick(DialogFragment dialog) {
        this.VisualizzaEsercizi();
    }

    @Override
    public void onDialogModificaEsercizioPersonalePositiveClick(DialogFragment dialog, String esito) {
        try {
            if (!TextUtils.isEmpty(esito)) {
                Toast.makeText(GestioneEserciziPersonaliActivity.this,
                        esito,
                        Toast.LENGTH_SHORT).show();
            }
            else {
                this.AggiornaListViewDopoOperazione();
            }
        }
        catch (Exception ex) {
            Log.d("EserciziPersonali", ex.getMessage());
        }
    }

    @Override
    public void onDialogEliminazioneEsercizioPersonalePositiveClick(DialogFragment dialog, int numeroRigheEliminate) {
        try {
            if (numeroRigheEliminate == 1) {
                Toast.makeText(GestioneEserciziPersonaliActivity.this,
                        R.string.testoEsercizioEliminato,
                        Toast.LENGTH_SHORT).show();
                this.AggiornaListViewDopoOperazione();
            }
        }
        catch (Exception ex) {
            Log.d("EserciziPersonali", ex.getMessage());
        }
    }

    private FragmentTransaction getTransacion() {
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        tr.addToBackStack("null");
        return tr;
    }
}
