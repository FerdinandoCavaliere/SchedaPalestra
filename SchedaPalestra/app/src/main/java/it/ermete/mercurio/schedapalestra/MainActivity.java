package it.ermete.mercurio.schedapalestra;

import android.Manifest;
import android.os.Build;
import android.support.v4.app.DialogFragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.woxthebox.draglistview.DragListView;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity
        implements DialogNuovaScheda.NoticeDialogNuovaSchedaListener,
        DialogInserimentoEsercizio.NoticeDialogInserimentoEsercizioListener,
        DialogModificaEsercizio.NoticeDialogModificaEsercizioListener,
        DialogConfermaEliminazione.NoticeDialogEliminazioneEsercizioListener,
        DialogInserimentoEsercizioPiramidale.NoticeDialogInserimentoEsercizioPiramidaleListener,
        DialogModificaEsercizioPiramidale.NoticeDialogModificaEsercizioPiramidaleListener,
        DialogModificaNotaScheda.NoticeDialoModificaNotaSchedaListener,
        DialogInserimentoEsercizioCardio.NoticeDialogInserimentoEsercizioCardioListener,
        DialogModificaEsercizioCardio.NoticeDialogModificaEsercizioCardioListener,
        DialogFiltroGruppiMuscolari.NoticeDialogFiltroGruppiMuscolari
{

    // Gruppi muscolari
    private final ArrayList<GruppoMuscolareDaXml> gruppi = new ArrayList<>(); // Elenco di tutti i gruppi da xml
    private final ArrayList<String> nomiGruppi = new ArrayList<>(); // Per lo Spinner

    // Esercizi
    private final ArrayList<EsercizioDaXml> esercizi = new ArrayList<>(); // Elenco di tutti gli esercizi da xml
    private final ArrayList<String> nomiEsercizi = new ArrayList<>(); // Per lo Spinner

    // Controllers
    private SchedeController schedeController;
    private EserciziController eserciziController;
    private EserciziPersonaliController eserciziPersonaliController;

    int idSchedaAttiva = -1;
    String dataSchedaAttiva = "";
    String notaSchedaAttiva = "";

    ArrayList<EsercizioDaDb> eserciziScheda;

    int idGruppoFiltrato = -1; // Intero che indica se sto visualizzando gli esercizi di un gruppo specifico oppure tutta la scheda
    String giornoFiltrato = ""; // Stringa che indica se sto visualizzando gli esercizi di un giorno specifico oppure tutti
    HashSet<String> gruppiFiltrati = null; // HashSet che indica se sto visualizzando gli esercizi di n gruppi muscolari
    String routineFiltrata = ""; // Stringa che indica se sto visualizzando gli esercizi di una routine specifico oppure tutti

    private boolean layerVisibile = false; // Serve per abilitare/disabilitare l'actionMenu sulla base della visibilità o meno del layer trasparente

    DragListView mDragListView;
    ArrayList<Pair<Long, EsercizioDaDb>> mItemArray;
    int primoItemVisibilePerPostOperazioni;
    LinearLayoutManager managerSalvato;

    // Cronometro
    private ImageView cronometro;
    private final DialogCronometro dialogCronometro = new DialogCronometro();

    // Export scheda
    private final int WRITE_EXTERNAL_STORAGE = 1;
    private final int ELENCO_FILES = 2;
    private Enumeratori.TipoExport tipoExport;

    private DrawerLayout drawer;
    private final int ACTIVITY_DI_PROVENIENZA = 0;

    // Metodi
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        this.SettaListenerInserimentoNuovoEsercizio();

        // Rendo invisibile il layer dei bottoni specifici
        ConstraintLayout layoutSceltaTipoDiInserimento = (ConstraintLayout) findViewById(R.id.LayoutSceltaTipoDiInserimento);
        layoutSceltaTipoDiInserimento.setVisibility(View.INVISIBLE);

        // Setto listener draglistview
        SettaListenerDragListView();

        // Coloro i bottoni specifici
        this.SettaColoriFloatingButton();

        // Setto listerner bottoni specifici
        this.SettaListenersBottoniSpecifici();

        // Setto listener per il cronometro
        this.SettaListenerCronometro();

        // Setto listener per il titolo della scheda
        this.SettaListenerNotaScheda();

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

        // Per non fare andare lo smartphone in sleep
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
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

        this.LeggiGruppiMuscolariConEsercizi();

        this.CaricaUltimaScheda();

        Intent intent = getIntent();
        if (intent != null) {
            Bundle valoriIntent = intent.getExtras();
            if (valoriIntent != null && valoriIntent.containsKey("idScheda")) {
                this.PulisciTipoDiVisualizzazioneEsercizi();
                intent.removeExtra("idScheda");
            }
        }
        this.AggiornaListViewDopoOperazione();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(android.support.v4.view.GravityCompat.START)) {
            drawer.closeDrawer(android.support.v4.view.GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        this.AggiungiSottomenuFiltroGruppoMuscolare(menu);

        MenuItem itemVisualizzaGruppiMuscolari = menu.add(0, 200, 2, R.string.VoceMenuFiltroPerGruppiMuscolari);

        this.AggiungiSottomenuFiltroGiorno(menu);

        this.AggiungiSottomenuFiltroRoutine(menu);

        MenuItem itemGestioneSuperset = menu.add(0, 500, 5, R.string.VoceMenuGestioneSuperSet);

        MenuItem itemNuovaScheda = menu.add(0, 600, 6, R.string.VoceMenuCreazioneNuovaScheda);

        MenuItem itemStoricoSchede = menu.add(0, 700, 7, R.string.VoceMenuStoricoSchede);

        MenuItem itemGestioneEsercizi = menu.add(0, 800, 8, R.string.VoceMenuGestioneEsercizi);

        this.AggiungiSottomenuCopiaScheda(menu);

        MenuItem itemGuida = menu.add(0, 1000, 10, R.string.VoceMenuAiuto);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.setGroupEnabled(0, !layerVisibile);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id)
        {
            case 200: // Filtro per gruppi muscolare
                DialogFiltroGruppiMuscolari dialog = DialogFiltroGruppiMuscolari.getInstance(nomiGruppi);
                dialog.show(getTransacion(), "FiltroGruppi");
                break;

            case 500: // Gestione superset
                getPosizioneScrollbar();
                Intent gestioneSuperSet = new Intent(MainActivity.this, GestioneSuperSetActivity.class);
                startActivity(gestioneSuperSet);
                break;

            case 600: // Creazione nuova scheda
                DialogNuovaScheda nuova = new DialogNuovaScheda();
                nuova.show(getTransacion(), "NuovaScheda");
                break;

            case 700: // Selezione scheda
                PulisciTipoDiVisualizzazioneEsercizi();
                primoItemVisibilePerPostOperazioni = 0; // Se sto cambiando scheda devo visualizzare dal primo esercizio
                Intent storicoSchede = new Intent(MainActivity.this, StoricoActivity.class);
                startActivity(storicoSchede);
                break;

            case 800: // gestione esercizi personali
                getPosizioneScrollbar();
                Intent gestioneEserciziPersonali = new Intent(MainActivity.this, GestioneEserciziPersonaliActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("gruppi", gruppi);
                bundle.putSerializable("nomiGruppi", nomiGruppi);
                gestioneEserciziPersonali.putExtras(bundle);
                startActivity(gestioneEserciziPersonali);
                break;

            case 1000:
                DialogAiutoMain aiuto = DialogAiutoMain.getInstance();
                aiuto.show(getTransacion(), "Aiuto");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void CaricaUltimaScheda() {
        if (schedeController == null)
            schedeController = new SchedeController(MainActivity.this);

        idSchedaAttiva = schedeController.LeggiIdSchedaAttiva();
        dataSchedaAttiva = schedeController.LeggiDataInserimentoSchedaAttiva(idSchedaAttiva);

        notaSchedaAttiva = schedeController.LeggiNotaSchedaAttiva(idSchedaAttiva);
        if (notaSchedaAttiva.equals(""))
            notaSchedaAttiva = this.getResources().getString(R.string.nessuna_nota_inserita);
    }

    private void SettaListenerDrawer() {
        ConstraintLayout LayoutMenuExportPdfElenco = (ConstraintLayout) findViewById(R.id.LayoutMenuExportPdfElenco);
        LayoutMenuExportPdfElenco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eserciziScheda != null && eserciziScheda.size() > 0) {
                    tipoExport = Enumeratori.TipoExport.Elenco;
                    // Per Android 6 o superiore bisogna verificare il permesso di scrivere files
                    // a run time
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                    {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                WRITE_EXTERNAL_STORAGE);
                    }
                    else
                    {
                        generaExport();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this,
                            R.string.testoNessunEsercizioPresente,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        ConstraintLayout LayoutMenuExportPdfGruppi = (ConstraintLayout) findViewById(R.id.LayoutMenuExportPdfGruppi);
        LayoutMenuExportPdfGruppi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eserciziScheda != null && eserciziScheda.size() > 0) {
                    tipoExport = Enumeratori.TipoExport.Gruppi;
                    // Per Android 6 o superiore bisogna verificare il permesso di scrivere files
                    // a run time
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                    {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                WRITE_EXTERNAL_STORAGE);
                    }
                    else
                    {
                        generaExport();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this,
                            R.string.testoNessunEsercizioPresente,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        ConstraintLayout LayoutMenuExportPdfRoutine = (ConstraintLayout) findViewById(R.id.LayoutMenuExportPdfRoutine);
        LayoutMenuExportPdfRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eserciziScheda != null && eserciziScheda.size() > 0) {
                    tipoExport = Enumeratori.TipoExport.Routine;
                    // Per Android 6 o superiore bisogna verificare il permesso di scrivere files
                    // a run time
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                    {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                WRITE_EXTERNAL_STORAGE);
                    }
                    else
                    {
                        generaExport();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this,
                            R.string.testoNessunEsercizioPresente,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        ConstraintLayout LayoutMenuExportPdfGiorni = (ConstraintLayout) findViewById(R.id.LayoutMenuExportPdfGiorni);
        LayoutMenuExportPdfGiorni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eserciziScheda != null && eserciziScheda.size() > 0) {
                    tipoExport = Enumeratori.TipoExport.Giorni;
                    // Per Android 6 o superiore bisogna verificare il permesso di scrivere files
                    // a run time
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                    {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                WRITE_EXTERNAL_STORAGE);
                    }
                    else
                    {
                        generaExport();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this,
                            R.string.testoNessunEsercizioPresente,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        ConstraintLayout LayoutElencoExport = (ConstraintLayout) findViewById(R.id.LayoutElencoExport);
        LayoutElencoExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            ELENCO_FILES);
                }
                else
                {
                    getPosizioneScrollbar();
                    Intent intentElencoExport = new Intent(MainActivity.this, ElencoExportsActivity.class);
                    intentElencoExport.putExtra("PROVENIENZA", ACTIVITY_DI_PROVENIENZA);
                    startActivity(intentElencoExport);
                }
            }
        });
    }

    private void SettaListenerDragListView() {
        mDragListView = (DragListView) findViewById(R.id.listViewEserciziInUltimaScheda);
        mDragListView.setDragListListener(new DragListView.DragListListener() {
            @Override
            public void onItemDragStarted(int position) {

            }

            @Override
            public void onItemDragging(int itemPosition, float x, float y) {

            }

            @Override
            public void onItemDragEnded(int fromPosition, int toPosition) {
                getPosizioneScrollbar();
                Scambia(fromPosition, toPosition);
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
                    ExportPdfElencoAsyncTask exportPdfElenco = new ExportPdfElencoAsyncTask(MainActivity.this,
                            idSchedaAttiva,
                            editTextNomeExport.getText().toString());
                    exportPdfElenco.execute();
                    break;
                case Gruppi:
                    ExportPdfPerGruppiAsyncTask exportPdfPerGruppi = new ExportPdfPerGruppiAsyncTask(MainActivity.this,
                            idSchedaAttiva,
                            gruppi,
                            editTextNomeExport.getText().toString());
                    exportPdfPerGruppi.execute();
                    break;
                case Routine:
                    ExportPdfPerRoutineAsyncTask exportPdfPerRoutine = new ExportPdfPerRoutineAsyncTask(MainActivity.this,
                            idSchedaAttiva,
                            editTextNomeExport.getText().toString());
                    exportPdfPerRoutine.execute();
                    break;
                case Giorni:
                    ExportPdfPerGiornoAsyncTask exportPdfPerGiorno = new ExportPdfPerGiornoAsyncTask(MainActivity.this,
                            idSchedaAttiva,
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
                    Intent intentElencoExport = new Intent(MainActivity.this, ElencoExportsActivity.class);
                    intentElencoExport.putExtra("PROVENIENZA", ACTIVITY_DI_PROVENIENZA);
                    startActivity(intentElencoExport);
                }
                break;
        }
    }

    private void SettaTestoNota() {
        if (schedeController == null) {
            schedeController = new SchedeController(MainActivity.this);
        }
        notaSchedaAttiva = schedeController.LeggiNotaSchedaAttiva(idSchedaAttiva);

        if (notaSchedaAttiva.equals("")) {
            notaSchedaAttiva = this.getResources().getString(R.string.nessuna_nota_inserita);
        }

        TextView testoNotaScheda = (TextView) findViewById(R.id.testoNotaScheda);
        testoNotaScheda.setText(notaSchedaAttiva);
    }

    private void SettaColoriFloatingButton() {
        FloatingActionButton fabTornaPrincipale = (FloatingActionButton) findViewById(R.id.fabTornaPrincipale);
        fabTornaPrincipale.setBackgroundTintList(getResources().getColorStateList(R.color.Rosso));
    }

    private void SettaListenersBottoniSpecifici() {
        try {
            // Bottone serie costanti
            FloatingActionButton fabAggiungiPesoCostante = (FloatingActionButton) findViewById(R.id.fabAggiungiPesoCostante);
            fabAggiungiPesoCostante.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ConstraintLayout layoutSceltaTipoDiInserimento = (ConstraintLayout) findViewById(R.id.LayoutSceltaTipoDiInserimento);
                    Animation nascondiLayer = AnimationUtils.loadAnimation(MainActivity.this, R.anim.nascondi_layer_pulsanti_tipo_inserimento);
                    layoutSceltaTipoDiInserimento.startAnimation(nascondiLayer);
                    nascondiLayer.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {}

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            layoutSceltaTipoDiInserimento.setVisibility(View.INVISIBLE);
                            SettaListenerInserimentoNuovoEsercizio();
                            layerVisibile = false;

                            mDragListView = (DragListView) findViewById(R.id.listViewEserciziInUltimaScheda);
                            mDragListView.setDragEnabled(true);
                            SettaListenerCronometro();
                            SettaListenerNotaScheda();

                            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

                            ArrayList<String> nomiGruppiNoCardio = new ArrayList<>();
                            for (String n : nomiGruppi) {
                                if (!n.equals("Cardio")) {
                                    nomiGruppiNoCardio.add(n);
                                }
                            }
                            DialogInserimentoEsercizio dialog = DialogInserimentoEsercizio.getInstance(nomiGruppiNoCardio,
                                    gruppi,
                                    esercizi,
                                    idSchedaAttiva);
                            dialog.show(getTransacion(), "InserimentoEsercizi");
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {}
                    });
                }
            });

            // Bottone serie piramidali
            FloatingActionButton fabAggiungiPesoPiramidale = (FloatingActionButton) findViewById(R.id.fabAggiungiPesoPiramidale);
            fabAggiungiPesoPiramidale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ConstraintLayout layoutSceltaTipoDiInserimento = (ConstraintLayout) findViewById(R.id.LayoutSceltaTipoDiInserimento);
                    Animation nascondiLayer = AnimationUtils.loadAnimation(MainActivity.this, R.anim.nascondi_layer_pulsanti_tipo_inserimento);
                    layoutSceltaTipoDiInserimento.startAnimation(nascondiLayer);
                    nascondiLayer.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {}

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            layoutSceltaTipoDiInserimento.setVisibility(View.INVISIBLE);
                            SettaListenerInserimentoNuovoEsercizio();
                            layerVisibile = false;

                            mDragListView = (DragListView) findViewById(R.id.listViewEserciziInUltimaScheda);
                            mDragListView.setDragEnabled(true);
                            SettaListenerCronometro();
                            SettaListenerNotaScheda();

                            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

                            ArrayList<String> nomiGruppiNoCardio = new ArrayList<>();
                            for (String n : nomiGruppi) {
                                if (!n.equals("Cardio")) {
                                    nomiGruppiNoCardio.add(n);
                                }
                            }
                            DialogInserimentoEsercizioPiramidale dialog = DialogInserimentoEsercizioPiramidale.getInstance(nomiGruppiNoCardio,
                                    gruppi,
                                    esercizi,
                                    idSchedaAttiva);

                            dialog.show(getTransacion(), "InserimentoEserciziPiramidali");
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {}
                    });
                }
            });

            // Bottone esercizio cardio
            FloatingActionButton fabAggiungiEsercizioAerobico = (FloatingActionButton) findViewById(R.id.fabAggiungiEsercizioAerobico);
            fabAggiungiEsercizioAerobico.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ConstraintLayout layoutSceltaTipoDiInserimento = (ConstraintLayout) findViewById(R.id.LayoutSceltaTipoDiInserimento);
                    Animation nascondiLayer = AnimationUtils.loadAnimation(MainActivity.this, R.anim.nascondi_layer_pulsanti_tipo_inserimento);
                    layoutSceltaTipoDiInserimento.startAnimation(nascondiLayer);
                    nascondiLayer.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {}

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            layoutSceltaTipoDiInserimento.setVisibility(View.INVISIBLE);
                            SettaListenerInserimentoNuovoEsercizio();
                            layerVisibile = false;

                            mDragListView = (DragListView) findViewById(R.id.listViewEserciziInUltimaScheda);
                            mDragListView.setDragEnabled(true);
                            SettaListenerCronometro();
                            SettaListenerNotaScheda();

                            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

                            ArrayList<String> nomiEserciziCardio = new ArrayList<>();
                            for (EsercizioDaXml esercizio : esercizi) {
                                if (esercizio.getIdGruppoMuscolare() == 1000) {
                                    nomiEserciziCardio.add(esercizio.getNome());
                                }
                            }

                            DialogInserimentoEsercizioCardio dialog = DialogInserimentoEsercizioCardio.getInstance(gruppi,
                                    esercizi,
                                    nomiEserciziCardio,
                                    idSchedaAttiva);

                            dialog.show(getTransacion(), "InserimentoEserciziCardio");
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {}
                    });
                }
            });

            // Bottone chiudi
            FloatingActionButton fabTornaPrincipale = (FloatingActionButton) findViewById(R.id.fabTornaPrincipale);
            fabTornaPrincipale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ConstraintLayout layoutSceltaTipoDiInserimento = (ConstraintLayout) findViewById(R.id.LayoutSceltaTipoDiInserimento);
                    Animation nascondiLayer = AnimationUtils.loadAnimation(MainActivity.this, R.anim.nascondi_layer_pulsanti_tipo_inserimento);
                    layoutSceltaTipoDiInserimento.startAnimation(nascondiLayer);
                    nascondiLayer.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {}

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            layoutSceltaTipoDiInserimento.setVisibility(View.INVISIBLE);
                            SettaListenerInserimentoNuovoEsercizio();
                            layerVisibile = false;

                            mDragListView = (DragListView) findViewById(R.id.listViewEserciziInUltimaScheda);
                            mDragListView.setDragEnabled(true);
                            SettaListenerCronometro();
                            SettaListenerNotaScheda();

                            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {}
                    });
                }
            });
        }
        catch (Exception ex) {
            Log.d("Main", ex.getMessage());
        }
    }

    private void SettaListenerCronometro() {
        try {
            cronometro = (ImageView) findViewById(R.id.imgCronometro);
            cronometro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogCronometro.setCancelable(false);
                    dialogCronometro.show(getTransacion(), "Cronometro");
                }
            });
        }
        catch (Exception ex) {
            Log.d("Main", ex.getMessage());
        }
    }

    private void RimuoviListenerCronometro() {
        try {
            cronometro = (ImageView) findViewById(R.id.imgCronometro);
            cronometro.setOnClickListener(null);
        }
        catch (Exception ex) {
            Log.d("Main", ex.getMessage());
        }
    }

    private void SettaListenerNotaScheda() {
        try {
            TextView testoNotaScheda = (TextView) findViewById(R.id.testoNotaScheda);
            testoNotaScheda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogModificaNotaScheda dialogModificaNotaScheda = DialogModificaNotaScheda.getInstance(idSchedaAttiva);
                    dialogModificaNotaScheda.show(getTransacion(), "ModificaNota");
                }
            });
        }
        catch (Exception ex) {
            Log.d("Main", ex.getMessage());
        }
    }

    private void RimuovoListenerNotaScheda() {
        try {
            TextView testoNotaScheda = (TextView) findViewById(R.id.testoNotaScheda);
            testoNotaScheda.setOnClickListener(null);
        }
        catch (Exception ex) {
            Log.d("Main", ex.getMessage());
        }
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
                    VisualizzaEserciziByIdSchedaConIdGruppoMuscolare(item.getItemId());
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
                VisualizzaEserciziByIdScheda(idSchedaAttiva, true);
                return true;
            }
        });
    }

    private void AggiungiSottomenuCopiaScheda(Menu menu) {
        SubMenu sottomenuFiltroGruppoMuscolare = menu.addSubMenu(0, 900, 9, R.string.VoceMenuCopiaScheda);

        MenuItem voceCopiaConPesiZero = sottomenuFiltroGruppoMuscolare.add(0,
                901,
                1,
                R.string.VoceMenuCopiaSchedaPesiZero);
        voceCopiaConPesiZero.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                primoItemVisibilePerPostOperazioni = 0;
                CopiaSchedaAttiva(false);
                return true;
            }
        });

        MenuItem voceCopiaMantenendoPesi = sottomenuFiltroGruppoMuscolare.add(0,
                902,
                2,
                R.string.VoceMenuCopiaSchedaMantenendoPesi);
        voceCopiaMantenendoPesi.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                primoItemVisibilePerPostOperazioni = 0;
                CopiaSchedaAttiva(true);
                return true;
            }
        });
    }

    // Aggiungo un sottomenu per selezionare gli esercizi di un determinato giorno
    // inseriti in scheda
    private void AggiungiSottomenuFiltroGiorno(Menu menu) {
        SubMenu sottomenuFiltroGiorno = menu.addSubMenu(0, 300, 3, R.string.VoceMenuFiltroPerGiorno);
        MenuItem nuovo;
        String[] giorni = getResources().getStringArray(R.array.Giorni);
        for (int i = 1; i < giorni.length; i++) {
            nuovo = sottomenuFiltroGiorno.add(0, i, i, giorni[i]);
            nuovo.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    primoItemVisibilePerPostOperazioni = 0;
                    VisualizzaEserciziByIdSchedaConGiorno(item.getTitle().toString());
                    return true;
                }
            });
        }

        MenuItem voceIndifferente = sottomenuFiltroGiorno.add(0,
                10000,
                10000,
                "Tutti");
        voceIndifferente.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                primoItemVisibilePerPostOperazioni = 0;
                VisualizzaEserciziByIdScheda(idSchedaAttiva, true);
                return true;
            }
        });
    }

    // Aggiungo un sottomenu per selezionare gli esercizi di una determinata routine
    // inseriti in scheda
    private void AggiungiSottomenuFiltroRoutine(Menu menu) {
        SubMenu sottomenuFiltroRoutine = menu.addSubMenu(0, 400, 4, R.string.VoceMenuFiltroPerRoutine);
        MenuItem nuovo;
        String[] routines = getResources().getStringArray(R.array.Routine);
        for (int i = 1; i < routines.length; i++) {
            nuovo = sottomenuFiltroRoutine.add(0, i, i, routines[i]);
            nuovo.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    primoItemVisibilePerPostOperazioni = 0;
                    VisualizzaEserciziByIdSchedaConRoutine(item.getTitle().toString());
                    return true;
                }
            });
        }

        MenuItem voceIndifferente = sottomenuFiltroRoutine.add(0,
                10000,
                10000,
                "Tutti");
        voceIndifferente.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                primoItemVisibilePerPostOperazioni = 0;
                VisualizzaEserciziByIdScheda(idSchedaAttiva, true);
                return true;
            }
        });
    }

    // Leggo in memoria tutti i gruppi muscolari e gli esercizi
    private void LeggiGruppiMuscolariConEsercizi() {
        try {
            // Pulisco le liste degli esercizi
            gruppi.clear();
            nomiGruppi.clear();
            esercizi.clear();
            nomiEsercizi.clear();

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
                                nomiGruppi.add(nuovoGruppo.getGruppo());
                                break;
                            case "Esercizio":
                                nuovoEsercizio = new EsercizioDaXml(
                                        Integer.parseInt(parse.getAttributeValue(null, "id")),
                                        parse.getAttributeValue(null, "nome"),
                                        Integer.parseInt(parse.getAttributeValue(null, "idGruppoMuscolare")),
                                        false);
                                esercizi.add(nuovoEsercizio);
                                nomiEsercizi.add(nuovoEsercizio.getNome());
                                break;
                        }
                        break;
                }
                tipo = parse.next();
            }

            // Leggo eventuali esercizi personali inseriti dall'utente
            if (eserciziPersonaliController == null) {
                eserciziPersonaliController = new EserciziPersonaliController(MainActivity.this);
            }
            eserciziPersonaliController.LeggiEserciziPersonali(esercizi, nomiEsercizi);
        }
        catch (Exception ex){
            Log.d("Eccezione XML Parser", ex.getMessage());
        }
    }

    private void SettaListenerInserimentoNuovoEsercizio() {
        try {
            final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAggiungiEsercizio);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final ConstraintLayout layoutSceltaTipoDiInserimento = (ConstraintLayout) findViewById(R.id.LayoutSceltaTipoDiInserimento);
                    Animation visualizzaLayer = AnimationUtils.loadAnimation(MainActivity.this, R.anim.visualizza_layer_pulsanti_tipo_inserimento);
                    layoutSceltaTipoDiInserimento.startAnimation(visualizzaLayer);
                    visualizzaLayer.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {}

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            layoutSceltaTipoDiInserimento.setVisibility(View.VISIBLE);
                            RimuoviListenerInserimentoNuovoEsercizio();
                            layerVisibile = true;

                            mDragListView = (DragListView) findViewById(R.id.listViewEserciziInUltimaScheda);
                            mDragListView.setDragEnabled(false);
                            RimuoviListenerCronometro();
                            RimuovoListenerNotaScheda();

                            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {}
                    });
                }
            });
        }
        catch (Exception ex) {
            Log.d("Main", ex.getMessage());
        }
    }

    private void RimuoviListenerInserimentoNuovoEsercizio() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAggiungiEsercizio);
        fab.setOnClickListener(null);
    }

    private void VisualizzaSchedaAttiva(boolean visualizzaMessaggioNessunEsercizioPresente) {
        try {
            this.PulisciTipoDiVisualizzazioneEsercizi();
            if (schedeController == null)
                schedeController = new SchedeController(MainActivity.this);

            idSchedaAttiva = schedeController.LeggiIdSchedaAttiva();
            dataSchedaAttiva = schedeController.LeggiDataInserimentoSchedaAttiva(idSchedaAttiva);

            SettaTestoNota();

            if (idSchedaAttiva > -1) {
                this.VisualizzaEserciziByIdScheda(idSchedaAttiva, visualizzaMessaggioNessunEsercizioPresente);
            }
            else {
                Toast.makeText(MainActivity.this,
                        R.string.testoNessunaSchedaTrovata,
                        Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception ex) {
            Log.d("Main", ex.getMessage());
        }
    }

    private void VisualizzaEserciziByIdScheda(int idScheda, boolean visualizzaMessaggioNessunEsercizioPresente) {
        this.PulisciTipoDiVisualizzazioneEsercizi();

        if (eserciziController == null)
            eserciziController = new EserciziController(MainActivity.this);
        eserciziScheda = eserciziController.LeggiEserciziByIdScheda(idScheda);

        SettaTestoNota();

        mItemArray = new ArrayList<>();
        long contatore = 0;
        for (EsercizioDaDb singolo : eserciziScheda) {
            mItemArray.add(new Pair<>(contatore, singolo));
            contatore++;
        }

        if (mDragListView == null) {
            SettaListenerDragListView();
        }
        mDragListView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        EsercizioDragAdapter listAdapter = new EsercizioDragAdapter(mItemArray, R.layout.esercizio_list_item, R.id.image, false, MainActivity.this);
        mDragListView.setAdapter(listAdapter, false);
        mDragListView.setCanDragHorizontally(false);

        setPosizioneScrollbar();

        if (eserciziScheda == null || eserciziScheda.size() == 0) {
            if (visualizzaMessaggioNessunEsercizioPresente) {
                Toast.makeText(MainActivity.this,
                        R.string.testoNessunEsercizioPresente,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void VisualizzaEserciziByIdSchedaConIdGruppoMuscolare(int idGruppo) {
        this.PulisciTipoDiVisualizzazioneEsercizi();
        idGruppoFiltrato = idGruppo;
        if (eserciziController == null)
            eserciziController = new EserciziController(MainActivity.this);
        eserciziScheda = eserciziController.LeggiEserciziByIdSchedaConIdGruppo(idSchedaAttiva, idGruppo);
        VisualizzaEserciziFiltrati(getResources().getString(R.string.testoNessunEsercizioDelGruppoMuscolareSelezionato));
    }

    private void VisualizzaEserciziByIdSchedaConGruppiMuscolari(HashSet<String> nomiGruppi) {
        this.PulisciTipoDiVisualizzazioneEsercizi();
        gruppiFiltrati = nomiGruppi;
        if (eserciziController == null)
            eserciziController = new EserciziController(MainActivity.this);
        eserciziScheda = eserciziController.LeggiEserciziByIdSchedaConGruppi(idSchedaAttiva, nomiGruppi);
        VisualizzaEserciziFiltrati(getResources().getString(R.string.testoNessunEsercizioDelGruppiMuscolariSelezionati));
    }

    private void VisualizzaEserciziByIdSchedaConGiorno(String giorno) {
        this.PulisciTipoDiVisualizzazioneEsercizi();
        giornoFiltrato = giorno;
        if (eserciziController == null)
            eserciziController = new EserciziController(MainActivity.this);
        eserciziScheda = eserciziController.LeggiEserciziByIdSchedaConGiorno(idSchedaAttiva, giorno);
        VisualizzaEserciziFiltrati(getResources().getString(R.string.testoNessunEsercizioDelGiornoSelezionato));
    }

    private void VisualizzaEserciziByIdSchedaConRoutine(String routine) {
        this.PulisciTipoDiVisualizzazioneEsercizi();
        routineFiltrata = routine;
        if (eserciziController == null)
            eserciziController = new EserciziController(MainActivity.this);
        eserciziScheda = eserciziController.LeggiEserciziByIdSchedaConRoutine(idSchedaAttiva, routine);
        VisualizzaEserciziFiltrati(getResources().getString(R.string.testoNessunEsercizioDellaRoutineSelezionata));
    }

    private void VisualizzaEserciziFiltrati(String messaggioNessunEsercizioTrovato) {
        SettaTestoNota();

        if (eserciziScheda == null || eserciziScheda.size() == 0) {
            Toast.makeText(MainActivity.this,
                    messaggioNessunEsercizioTrovato,
                    Toast.LENGTH_SHORT).show();
        }
        else {
            mItemArray = new ArrayList<>();
            long contatore = 0;
            for (EsercizioDaDb singolo : eserciziScheda) {
                mItemArray.add(new Pair<>(contatore, singolo));
                contatore++;
            }

            if (mDragListView == null) {
                SettaListenerDragListView();
            }

            mDragListView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            EsercizioDragAdapter listAdapter = new EsercizioDragAdapter(mItemArray, R.layout.esercizio_list_item, R.id.image, false, MainActivity.this);
            mDragListView.setAdapter(listAdapter, false);
            mDragListView.setCanDragHorizontally(false);

            setPosizioneScrollbar();
        }
    }

    public int getIdUltimaScheda() {
        return idSchedaAttiva;
    }

    public boolean getBottoniSpecificiVisibility() {
        return layerVisibile;
    }

    public void AggiornaListViewDopoOperazione() {
        try {
            switch (GetTipoDiVisualizzazioneEsercizi()) {
                case Tutti:
                    VisualizzaSchedaAttiva(true);
                    break;
                case GiornoDellaSettimana:
                    VisualizzaEserciziByIdSchedaConGiorno(giornoFiltrato);
                    break;
                case GruppoMuscolare:
                    VisualizzaEserciziByIdSchedaConIdGruppoMuscolare(idGruppoFiltrato);
                    break;
                case GruppiMuscolari:
                    VisualizzaEserciziByIdSchedaConGruppiMuscolari(gruppiFiltrati);
                    break;
                case Routine:
                    VisualizzaEserciziByIdSchedaConRoutine(routineFiltrata);
                    break;
            }
            setPosizioneScrollbar();

        }
        catch (Exception ex) {
            Log.d("Main", ex.getMessage());
        }
    }

    private void Scambia(int fromPosition, int toPosition) {
        try {
            if (fromPosition != toPosition) {
                if (fromPosition != toPosition) {
                    Boolean supersetCoinvolta = false;
                    // Reperisco i valori del campo ordine in base agli indici degli elementi restituiti dalla listview
                    int dallaPosizione = eserciziScheda.get(fromPosition).getOrdine();
                    int allaPosizione = eserciziScheda.get(toPosition).getOrdine();
                    if (fromPosition != toPosition) {
                        if (fromPosition > toPosition) { // es da 3 a 1 sto spostando 3 in 1
                            // In avanti
                            supersetCoinvolta = eserciziController.ScambiaPosizioniInAvanti(idSchedaAttiva, dallaPosizione, allaPosizione);
                        }
                        else { // es da 1 a 3 sto spostanto 1 in 3
                            // All'indietro
                            supersetCoinvolta = eserciziController.ScambiaPosizioniAllindietro(idSchedaAttiva, dallaPosizione, allaPosizione);
                        }
                    }
                    if (supersetCoinvolta) {
                        Toast.makeText(MainActivity.this,
                                R.string.testoOperazioneRiportatoRimozioneSuperSet,
                                Toast.LENGTH_SHORT).show();
                    }
                    AggiornaListViewDopoOperazione();
                }
            }
        }
        catch (Exception ex) {
            Log.d("Main", ex.getMessage());
        }
    }

    private void CopiaSchedaAttiva(boolean mantieniPesi) {
        if (eserciziScheda != null && eserciziScheda.size() > 0) {
            CopiaSchedaAsyncTask task = new CopiaSchedaAsyncTask(this, idSchedaAttiva, mantieniPesi);
            task.execute();
        }
        else {
            Toast.makeText(MainActivity.this,
                    R.string.testoNessunEsercizioPresente,
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void getPosizioneScrollbar() {
        managerSalvato = (LinearLayoutManager) mDragListView.getRecyclerView().getLayoutManager();
        if (managerSalvato != null) {
            primoItemVisibilePerPostOperazioni = managerSalvato.findFirstVisibleItemPosition();
            if (primoItemVisibilePerPostOperazioni == RecyclerView.NO_POSITION) {
                primoItemVisibilePerPostOperazioni = 0;
            }
        } else {
            primoItemVisibilePerPostOperazioni = 0;
        }
    }

    public void setPosizioneScrollbar() {
        managerSalvato = (LinearLayoutManager) mDragListView.getRecyclerView().getLayoutManager();
        if (managerSalvato != null) {
            managerSalvato.scrollToPosition(primoItemVisibilePerPostOperazioni);
            mDragListView.setLayoutManager(managerSalvato);
        }
    }

    public void EseguiAzioniDopoCopia(int nuovoIdScheda, String errore) {
        if (errore.equals("") && nuovoIdScheda > -1) {
            this.PulisciTipoDiVisualizzazioneEsercizi();
            idSchedaAttiva = nuovoIdScheda;
            this.AggiornaListViewDopoOperazione();
            Toast.makeText(MainActivity.this,
                    R.string.schedaCopiataConsuccesso,
                    Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(MainActivity.this,
                    getResources().getString(R.string.testoCopiaNonCreato),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void EseguiAzioniDopoExport(String fileName, String errore) {
        if (errore.equals("")) {
            EditText editTextNomeExport = (EditText) findViewById(R.id.editTextNomeExport);
            editTextNomeExport.setText("");

            Toast.makeText(MainActivity.this,
                    getResources().getString(R.string.schedaEsportataConsuccesso) + fileName,
                    Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(MainActivity.this,
                    getResources().getString(R.string.testoExportNonCreato),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDialogNuovaSchedaPositiveClick(DialogFragment dialog, long nuovoId) {
        try {
            primoItemVisibilePerPostOperazioni = 0;
            if (nuovoId > 0) {
                Toast.makeText(MainActivity.this,
                        R.string.testoNuovaSchedaCreata,
                        Toast.LENGTH_SHORT).show();
                VisualizzaSchedaAttiva(false);
            }
        }
        catch (Exception ex) {
            Log.d("Main", ex.getMessage());
        }
    }

    @Override
    public void onDialogInserimentoEsercizioNegativeClick(DialogFragment dialog) {
        primoItemVisibilePerPostOperazioni = 0;
        this.VisualizzaSchedaAttiva(true);
    }

    @Override
    public void onDialogInserimentoEsercizioPiramidaleNegativeClick(DialogFragment dialog) {
        primoItemVisibilePerPostOperazioni = 0;
        this.VisualizzaSchedaAttiva(true);
    }

    @Override
    public void onDialogInserimentoEsercizioCardioNegativeClick(DialogFragment dialog) {
        primoItemVisibilePerPostOperazioni = 0;
        this.VisualizzaSchedaAttiva(true);
    }

    @Override
    public void onDialogModificaEsercizioPositiveClick(DialogFragment dialog, String esito) {
        try {
            if (!TextUtils.isEmpty(esito)) {
                Toast.makeText(MainActivity.this,
                        esito,
                        Toast.LENGTH_SHORT).show();
            }
            else {
                this.AggiornaListViewDopoOperazione();
            }
        }
        catch (Exception ex) {
            Log.d("Main", ex.getMessage());
        }
    }

    @Override
    public void onDialogModificaEsercizioPiramidalePositiveClick(DialogFragment dialog, String esito) {
        try {
            if (!TextUtils.isEmpty(esito)) {
                Toast.makeText(MainActivity.this,
                        esito,
                        Toast.LENGTH_SHORT).show();
            }
            else {
                this.AggiornaListViewDopoOperazione();
            }
        }
        catch (Exception ex) {
            Log.d("Main", ex.getMessage());
        }
    }

    @Override
    public void onDialogModificaEsercizioCardioPositiveClick(DialogFragment dialog, String esito) {
        try {
            if (!TextUtils.isEmpty(esito)) {
                Toast.makeText(MainActivity.this,
                        esito,
                        Toast.LENGTH_SHORT).show();
            }
            else {
                this.AggiornaListViewDopoOperazione();
            }
        }
        catch (Exception ex) {
            Log.d("Main", ex.getMessage());
        }
    }

    @Override
    public void onDialogEliminazioneEsercizioPositiveClick(DialogFragment dialog, int numeroRigheEliminate) {
        try {
            if (numeroRigheEliminate == 1) {
                Toast.makeText(MainActivity.this,
                        R.string.testoEsercizioEliminato,
                        Toast.LENGTH_SHORT).show();
            }
            else if (numeroRigheEliminate > 1) {
                Toast.makeText(MainActivity.this,
                        R.string.testoEsercizioEliminatoConSuperset,
                        Toast.LENGTH_SHORT).show();
            }
            this.AggiornaListViewDopoOperazione();
        }
        catch (Exception ex) {
            Log.d("Main", ex.getMessage());
        }
    }

    @Override
    public void onDialogFiltroGruppiMuscolariPositiveButton(DialogFragment dialog, HashSet<String> gruppiSelezionati) {
        try {
            if (gruppiSelezionati != null && gruppiSelezionati.size() > 0) {
                primoItemVisibilePerPostOperazioni = 0;
                this.VisualizzaEserciziByIdSchedaConGruppiMuscolari(gruppiSelezionati);
            }
            else {
                Toast.makeText(MainActivity.this,
                        R.string.testoNessunGruppoSelezionato,
                        Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception ex) {
            Log.d("Main", ex.getMessage());
        }
    }

    @Override
    public void onDialogModificaNotaSchedaPositiveClick(DialogFragment dialog, long esito) {
        if (esito == 1) {
            if (schedeController == null)
                schedeController = new SchedeController(MainActivity.this);
            SettaTestoNota();
        }
        else {
            Toast.makeText(MainActivity.this,
                    R.string.testoNotaNonModificata,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private Enumeratori.TipoDiVisualizzazioneEsercizi GetTipoDiVisualizzazioneEsercizi() {

        if (idGruppoFiltrato != -1) {
            return Enumeratori.TipoDiVisualizzazioneEsercizi.GruppoMuscolare;
        }

        if (!TextUtils.isEmpty(giornoFiltrato)) {
            return Enumeratori.TipoDiVisualizzazioneEsercizi.GiornoDellaSettimana;
        }

        if (gruppiFiltrati != null) {
            return Enumeratori.TipoDiVisualizzazioneEsercizi.GruppiMuscolari;
        }

        if (!TextUtils.isEmpty(routineFiltrata)) {
            return Enumeratori.TipoDiVisualizzazioneEsercizi.Routine;
        }

        return  Enumeratori.TipoDiVisualizzazioneEsercizi.Tutti;
    }

    private void PulisciTipoDiVisualizzazioneEsercizi() {
        idGruppoFiltrato = -1;
        giornoFiltrato = "";
        gruppiFiltrati = null;
        routineFiltrata = "";
    }

    private FragmentTransaction getTransacion() {
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        tr.addToBackStack("null");
        return tr;
    }
}
