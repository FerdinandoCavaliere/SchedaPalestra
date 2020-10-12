package it.ermete.mercurio.schedapalestra;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class DialogInserimentoEsercizioCardio extends DialogFragment {

    public interface NoticeDialogInserimentoEsercizioCardioListener {
        void onDialogInserimentoEsercizioCardioNegativeClick(DialogFragment dialog);
    }

    DialogInserimentoEsercizioCardio.NoticeDialogInserimentoEsercizioCardioListener mListener;

    private ArrayList<GruppoMuscolareDaXml> gruppi;
    private ArrayList<String> nomiEsercizi;
    private ArrayList<EsercizioDaXml> esercizi;
    private int idUltimaScheda;
    private String bgColor = "Bianco";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (NoticeDialogInserimentoEsercizioCardioListener) activity;
    }

    public static DialogInserimentoEsercizioCardio getInstance(ArrayList<GruppoMuscolareDaXml> gruppi,
                                                               ArrayList<EsercizioDaXml> esercizi,
                                                               ArrayList<String> nomiEsercizi,
                                                               int idUltimaScheda) {

        DialogInserimentoEsercizioCardio dialog = new DialogInserimentoEsercizioCardio();
        Bundle bundle = new Bundle();
        bundle.putSerializable("gruppi", gruppi);
        bundle.putSerializable("esercizi", esercizi);
        bundle.putSerializable("nomiEsercizi", nomiEsercizi);
        bundle.putInt("idUltimaScheda", idUltimaScheda);
        dialog.setArguments(bundle);

        return  dialog;
    }


    @Override
    @SuppressWarnings("unchecked")
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        gruppi = (ArrayList<GruppoMuscolareDaXml>) getArguments().get("gruppi");
        esercizi = (ArrayList<EsercizioDaXml>) getArguments().get("esercizi");
        nomiEsercizi = (ArrayList<String>)getArguments().get("nomiEsercizi");
        idUltimaScheda = getArguments().getInt("idUltimaScheda");

        // Giorni della settimana
        ArrayAdapter<CharSequence> adapterGiorni = ArrayAdapter.createFromResource(getActivity(),
                R.array.Giorni, android.R.layout.simple_spinner_dropdown_item);

        // Routine
        ArrayAdapter<CharSequence> adapterRoutine = ArrayAdapter.createFromResource(getActivity(),
                R.array.Routine, android.R.layout.simple_spinner_dropdown_item);

        // Esercizi
        Collections.sort(nomiEsercizi);
        ArrayAdapter<String> adapterEsercizi = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                this.nomiEsercizi);

        AlertDialog.Builder builderInserimentoEserciziCardio = new AlertDialog.Builder(getActivity());
        LayoutInflater inflaterInserimentoEserciziCardio = getActivity().getLayoutInflater();
        final View layoutInserimentoEserciziCardio = inflaterInserimentoEserciziCardio.inflate(R.layout.dialog_inserimento_esercizio_cardio, null);

        Spinner spinnerEsercizi = (Spinner)layoutInserimentoEserciziCardio.findViewById(R.id.spinnerEsercizi);
        spinnerEsercizi.setAdapter(adapterEsercizi);

        // Setto bottoni per settare il colore di sfondo
        Button btnBgBianco = (Button) layoutInserimentoEserciziCardio.findViewById(R.id.btnBgBianco);
        btnBgBianco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutInserimentoEserciziCardio.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorBianco));
                bgColor = "Bianco";
            }
        });

        Button btnBgViola = (Button) layoutInserimentoEserciziCardio.findViewById(R.id.btnBgViola);
        btnBgViola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutInserimentoEserciziCardio.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorViola));
                bgColor = "Viola";
            }
        });

        Button btnBgArancio = (Button) layoutInserimentoEserciziCardio.findViewById(R.id.btnBgArancio);
        btnBgArancio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutInserimentoEserciziCardio.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorArancio));
                bgColor = "Arancio";
            }
        });

        Button btnBgBlu = (Button) layoutInserimentoEserciziCardio.findViewById(R.id.btnBgBlu);
        btnBgBlu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutInserimentoEserciziCardio.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorBlu));
                bgColor = "Blu";
            }
        });

        Button btnBgAzzurro = (Button) layoutInserimentoEserciziCardio.findViewById(R.id.btnBgAzzurro);
        btnBgAzzurro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutInserimentoEserciziCardio.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorAzzurro));
                bgColor = "Azzurro";
            }
        });

        Button btnBgVerde = (Button) layoutInserimentoEserciziCardio.findViewById(R.id.btnBgVerde);
        btnBgVerde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutInserimentoEserciziCardio.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorVerde));
                bgColor = "Verde";
            }
        });

        Button btnBgGiallo = (Button) layoutInserimentoEserciziCardio.findViewById(R.id.btnBgGiallo);
        btnBgGiallo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutInserimentoEserciziCardio.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorGiallo));
                bgColor = "Giallo";
            }
        });

        Button btnBgRosso = (Button) layoutInserimentoEserciziCardio.findViewById(R.id.btnBgRosso);
        btnBgRosso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutInserimentoEserciziCardio.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorRosso));
                bgColor = "Rosso";
            }
        });

        Button btnBgRosa = (Button) layoutInserimentoEserciziCardio.findViewById(R.id.btnBgRosa);
        btnBgRosa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutInserimentoEserciziCardio.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorRosa));
                bgColor = "Rosa";
            }
        });

        Button btnBgVerdeAcqua = (Button) layoutInserimentoEserciziCardio.findViewById(R.id.btnBgVerdeAcqua);
        btnBgVerdeAcqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutInserimentoEserciziCardio.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorVerdeAcqua));
                bgColor = "VerdeAcqua";
            }
        });

        Spinner spinnerGiorni = (Spinner) layoutInserimentoEserciziCardio.findViewById(R.id.spinnerGiorni);
        spinnerGiorni.setAdapter(adapterGiorni);

        Spinner spinnerRoutine = (Spinner) layoutInserimentoEserciziCardio.findViewById(R.id.spinnerRoutine);
        spinnerRoutine.setAdapter(adapterRoutine);

        builderInserimentoEserciziCardio.setView(layoutInserimentoEserciziCardio);

        builderInserimentoEserciziCardio.setPositiveButton(R.string.testoBottoneSalva, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builderInserimentoEserciziCardio.setNegativeButton(R.string.testoBottoneChiudi, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onDialogInserimentoEsercizioCardioNegativeClick(DialogInserimentoEsercizioCardio.this);
            }
        });

        return builderInserimentoEserciziCardio.create();
    }

    @Override
    public void onStart() {
        super.onStart();

        AlertDialog d = (AlertDialog)getDialog();
        if(d != null)
        {
            d.getButton(Dialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String esito = InserisciEsercizio(getDialog());
                    if (!TextUtils.isEmpty(esito)) {
                        Toast.makeText(getActivity(),
                                esito,
                                Toast.LENGTH_SHORT).show();
                    }
                    else {
                        pulisciCampiDialogInserimentoEsercizio(getDialog());
                        Toast.makeText(getActivity(),
                                "Esercizio memorizzato",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    protected String InserisciEsercizio(DialogInterface dialog) {
        try {
            Dialog d = (Dialog)dialog;

            // Ottengo i riferimenti ai campi del dialog
            Spinner spinnerEsercizi = (Spinner) d.findViewById(R.id.spinnerEsercizi);
            Spinner spinnerGiorni = (Spinner) d.findViewById(R.id.spinnerGiorni);
            EditText editTextProgramma = (EditText) d.findViewById(R.id.editTextProgramma);
            EditText editTextIntensita = (EditText) d.findViewById(R.id.editTextIntensita);
            EditText editTextPendenza = (EditText) d.findViewById(R.id.editTextPendenza);
            EditText editTextVelocita = (EditText) d.findViewById(R.id.editTextVelocita);
            EditText editTextTempo = (EditText) d.findViewById(R.id.editTextTempo);
            EditText editTextNote = (EditText) d.findViewById(R.id.editTextNote);
            Spinner spinnerRoutine = (Spinner) d.findViewById(R.id.spinnerRoutine);

            // Leggo i valori inseriti dall'utente nel dialog
            EsercizioDaXml esercizioSelezionato = getEsercizioBySpinnerPosition(spinnerEsercizi);
            int idEsercizioSelezionatoPerInsert = esercizioSelezionato.getId();
            String nomeEsercizioSelezionatoPerInsert = esercizioSelezionato.getNome();
            GruppoMuscolareDaXml gruppoMuscolareDaXml = getGruppoById(esercizioSelezionato.getIdGruppoMuscolare());
            int idGruppoSelezionatoPerInsert = gruppoMuscolareDaXml.getId();
            String nomeGruppoSelezionatoPerInsert = gruppoMuscolareDaXml.getGruppo();

            String programmaPerInsert = editTextProgramma.getText().toString();
            String intensitaPerInsert = editTextIntensita.getText().toString();
            String pendenzaPerInsert = editTextPendenza.getText().toString();

            float velocitaInsert = 0;
            if (!TextUtils.isEmpty(editTextVelocita.getText().toString()))
                velocitaInsert = Float.valueOf(editTextVelocita.getText().toString());

            float tempoPerInsert = 0;
            if (!TextUtils.isEmpty(editTextTempo.getText().toString()))
                tempoPerInsert = Float.valueOf(editTextTempo.getText().toString());

            String notePerInsert = editTextNote.getText().toString();

            String giornoPerInsert = getGiornooBySpinnerPosition(spinnerGiorni);

            String routinePerInsert = getRoutineBySpinnerPosition(spinnerRoutine);

            EsercizioDaDb esercizioDaInserire = new EsercizioDaDb(0,
                    this.idUltimaScheda,
                    idGruppoSelezionatoPerInsert,
                    nomeGruppoSelezionatoPerInsert,
                    idEsercizioSelezionatoPerInsert,
                    nomeEsercizioSelezionatoPerInsert,
                    0,
                    0,
                    0,
                    notePerInsert,
                    giornoPerInsert,
                    0,
                    bgColor,
                    "",
                    routinePerInsert,
                    0);

            DatiEsercizioCardioDaDb datiEsercizioCardioDaDb = new DatiEsercizioCardioDaDb(0,
                    programmaPerInsert,
                    intensitaPerInsert,
                    pendenzaPerInsert,
                    velocitaInsert,
                    tempoPerInsert);

            EserciziController eserciziController = new EserciziController(getActivity());
            long nuovoId = eserciziController.InsertEsercizio(esercizioDaInserire, datiEsercizioCardioDaDb);
            if (nuovoId < 0) {
                return getString(R.string.testoEsercizioNonInserito);
            }
            return "";
        }
        catch (Exception ex) {
            return ex.getMessage();
        }
    }

    protected void pulisciCampiDialogInserimentoEsercizio(DialogInterface dialog) {
        Dialog d = (Dialog)dialog;

        // Ottengo i riferimenti ai campi del dialog
        Spinner spinnerEsercizi = (Spinner) d.findViewById(R.id.spinnerEsercizi);
        Spinner spinnerGiorni = (Spinner) d.findViewById(R.id.spinnerGiorni);
        EditText editTextProgramma = (EditText) d.findViewById(R.id.editTextProgramma);
        EditText editTextIntensita = (EditText) d.findViewById(R.id.editTextIntensita);
        EditText editTextPendenza = (EditText) d.findViewById(R.id.editTextPendenza);
        EditText editTextVelocita = (EditText) d.findViewById(R.id.editTextVelocita);
        EditText editTextTempo = (EditText) d.findViewById(R.id.editTextTempo);
        EditText editTextNote = (EditText) d.findViewById(R.id.editTextNote);
        Spinner spinnerRoutine = (Spinner) d.findViewById(R.id.spinnerRoutine);

        spinnerEsercizi.setSelection(0);
        spinnerGiorni.setSelection(0);
        editTextProgramma.setText("");
        editTextIntensita.setText("");
        editTextPendenza.setText("");
        editTextVelocita.setText("");
        editTextTempo.setText("");
        editTextNote.setText("");
        spinnerRoutine.setSelection(0);

        editTextProgramma.requestFocus();
    }

    protected EsercizioDaXml getEsercizioBySpinnerPosition(Spinner spinner) {
        try {
            boolean trovato = false;
            int contatorePosizione = -1; // Scandisce l'arraylist esercizi per cercare
                                         // il gruppo il cui testo è stato selezionato
                                         // dallo spinner per recuperarne i'id
            String nomeEsercizioSelezionato = nomiEsercizi.get(spinner.getSelectedItemPosition());
            EsercizioDaXml esercizioTmp = null;
            while (!trovato) {
                contatorePosizione++;
                esercizioTmp = esercizi.get(contatorePosizione);
                if (nomeEsercizioSelezionato.equals(esercizioTmp.getNome())) {
                    trovato = true;
                }
            }
            return esercizioTmp;
        }
        catch (Exception ex) {
            return null;
        }
    }

    protected GruppoMuscolareDaXml getGruppoById(int idGruppo) {
        try {
            boolean trovato = false;
            int contatorePosizione = -1;
            GruppoMuscolareDaXml gruppoTmp = null;
            while (!trovato) {
                contatorePosizione++;
                gruppoTmp = gruppi.get(contatorePosizione);
                if (gruppoTmp.getId() == idGruppo) {
                    trovato = true;
                }
            }
            return gruppoTmp;
        }
        catch (Exception ex) {
            return null;
        }
    }

    protected String getGiornooBySpinnerPosition(Spinner spinner) {
        try {
            String[] giorni = getActivity().getResources().getStringArray(R.array.Giorni);
            boolean trovato = false;
            int contatorePosizione = -1; // Scandisce l'array dei giorni
            if (spinner.getSelectedItemPosition() > 0) {
                return giorni[spinner.getSelectedItemPosition()];
            }
            else {
                return "";
            }
        }
        catch (Exception ex) {
            return "";
        }
    }

    protected String getRoutineBySpinnerPosition(Spinner spinner) {
        try {
            String[] routines = getActivity().getResources().getStringArray(R.array.Routine);
            boolean trovato = false;
            int contatorePosizione = -1; // Scandisce l'array delle routines
            if (spinner.getSelectedItemPosition() > 0) {
                return routines[spinner.getSelectedItemPosition()];
            }
            else {
                return "";
            }
        }
        catch (Exception ex) {
            return "";
        }
    }
}
