package it.ermete.mercurio.schedapalestra;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

public class GestioneSuperSetActivity extends AppCompatActivity implements
        DialogConfermaEliminazioneSuperSet.NoticeDialogEliminazioneSupersetListener {

    ArrayList<EsercizioDaDb> eserciziScheda;
    HashSet<Integer> elencoIdSelezionatiPerSuperSet;

    // Controllers
    private SchedeController schedeController;
    private EserciziController eserciziController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestione_super_set);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.SettaListenerBottoni();
        this.SettaListanerListView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.VisualizzaUltimaScheda();
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
                DialogAiutoGestioneSuperSet aiuto = DialogAiutoGestioneSuperSet.getInstance();
                aiuto.show(getTransacion(), "Aiuto");
                break;

            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void SettaListanerListView() {
        try {
            ListView listViewElencoEsercizi = (ListView) findViewById(R.id.listViewElencoEsercizi);
            listViewElencoEsercizi.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    EsercizioDaDb esercizioSelezionato = eserciziScheda.get(position);
                    DialogConfermaEliminazioneSuperSet dialog = DialogConfermaEliminazioneSuperSet.getInstance(esercizioSelezionato);
                    dialog.show(getTransacion(), "ConfermaEliminazioneSuperSet");
                    return true;
                }
            });
        }
        catch (Exception ex) {
            Log.d("GestioneSuperSet", ex.getMessage());
        }
    }

    private void SettaListenerBottoni() {
        try {
            Button btnSalvaSuperSet = (Button) findViewById(R.id.btnSalvaSuperSet);
            btnSalvaSuperSet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (VerificaEserciziSelezionati()) {
                        if (eserciziController == null)
                            eserciziController = new EserciziController(GestioneSuperSetActivity.this);
                        eserciziController.SalvaSuperSet(elencoIdSelezionatiPerSuperSet);
                        elencoIdSelezionatiPerSuperSet = null;
                        VisualizzaUltimaScheda();
                    }
                }
            });
        }
        catch (Exception ex) {
            Log.d("GestioneSuperSet", ex.getMessage());
        }
    }

    private void VisualizzaUltimaScheda() {
        try {
            if (schedeController == null)
                schedeController = new SchedeController(GestioneSuperSetActivity.this);
            int idUltimaScheda = schedeController.LeggiIdSchedaAttiva();
            if (idUltimaScheda > -1) {
                this.VisualizzaEserciziByIdScheda(idUltimaScheda);
            }
            else {
                Toast.makeText(GestioneSuperSetActivity.this,
                        R.string.testoNessunaSchedaTrovata,
                        Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception ex) {
            Log.d("GestioneSuperSet", ex.getMessage());
        }
    }

    private void VisualizzaEserciziByIdScheda(int idScheda) {
        try {
            if (eserciziController == null)
                eserciziController = new EserciziController(GestioneSuperSetActivity.this);
            eserciziScheda = eserciziController.LeggiEserciziByIdScheda(idScheda);

            EsercizioPerGestioneSuperSetAdapter adapter = new EsercizioPerGestioneSuperSetAdapter(GestioneSuperSetActivity.this,
                    R.layout.esercizio_gestionesuperset_list_item, eserciziScheda);

            ListView listViewElencoEsercizi = (ListView) findViewById(R.id.listViewElencoEsercizi);
            listViewElencoEsercizi.setAdapter(adapter);

            Button btnSalvaSuperSet = (Button) findViewById(R.id.btnSalvaSuperSet);

            if (eserciziScheda == null || eserciziScheda.size() == 0) {
                Toast.makeText(GestioneSuperSetActivity.this,
                        R.string.testoNessunEsercizioPresente,
                        Toast.LENGTH_SHORT).show();

                // Rendo invisibile il bottone Salva
                btnSalvaSuperSet.setVisibility(View.INVISIBLE);
            }
            else {
                btnSalvaSuperSet.setVisibility(View.VISIBLE);
            }
        }
        catch (Exception ex) {
            Log.d("GestioneSuperSet", ex.getMessage());
        }
    }

    public void AggiungiIdEsercizio(int _id) {
        if (elencoIdSelezionatiPerSuperSet == null)
            elencoIdSelezionatiPerSuperSet = new HashSet<>();
        elencoIdSelezionatiPerSuperSet.add(_id);
    }

    public void RimuoviIdEsercizio(int _id) {
        elencoIdSelezionatiPerSuperSet.remove(_id);
    }

    private boolean VerificaEserciziSelezionati() {
        try {
            if (eserciziController == null)
                eserciziController = new EserciziController(GestioneSuperSetActivity.this);
            String esitoVerifica = eserciziController.VerificaEserciziSelezionati(elencoIdSelezionatiPerSuperSet);
            if (TextUtils.isEmpty(esitoVerifica))
                return true;
            else {
                Toast.makeText(GestioneSuperSetActivity.this,
                        esitoVerifica,
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        catch (Exception ex) {
            Log.d("EserciziSuccessivi", ex.getMessage());
            return false;
        }
    }

    @Override
    public void onDialogEliminazioneSupersetPositiveClick(DialogFragment dialog, int numeroRigheEliminate) {
        try {
            if (numeroRigheEliminate > 0) {
                Toast.makeText(GestioneSuperSetActivity.this,
                        R.string.testoSuperSetEliminata,
                        Toast.LENGTH_SHORT).show();
                VisualizzaUltimaScheda();
            }
        }
        catch (Exception ex) {
            Log.d("ConfermaEliminazione", ex.getMessage());
        }
    }

    private FragmentTransaction getTransacion() {
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        tr.addToBackStack("null");
        return tr;
    }
}
