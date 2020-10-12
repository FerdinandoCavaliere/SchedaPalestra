package it.ermete.mercurio.schedapalestra;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class DialogInserimentoEsercizio extends DialogFragment {

    public interface NoticeDialogInserimentoEsercizioListener {
        void onDialogInserimentoEsercizioNegativeClick(DialogFragment dialog);
    }

    NoticeDialogInserimentoEsercizioListener mListener;

    private ArrayList<String> nomiGruppi;
    private ArrayList<GruppoMuscolareDaXml> gruppi;
    private ArrayList<String> nomiEsercizi;
    private ArrayList<EsercizioDaXml> esercizi;
    private ArrayList<EsercizioDaXml> eserciziFiltrati;
    private int idUltimaScheda;
    private String bgColor = "Bianco";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (NoticeDialogInserimentoEsercizioListener) activity;
    }

    public static DialogInserimentoEsercizio getInstance(ArrayList<String> nomiGruppi,
                                                         ArrayList<GruppoMuscolareDaXml> gruppi,
                                                         ArrayList<EsercizioDaXml> esercizi,
                                                         int idUltimaScheda) {

        DialogInserimentoEsercizio dialog = new DialogInserimentoEsercizio();
        Bundle bundle = new Bundle();
        bundle.putSerializable("nomiGruppi", nomiGruppi);
        bundle.putSerializable("gruppi", gruppi);
        bundle.putSerializable("esercizi", esercizi);
        bundle.putInt("idUltimaScheda", idUltimaScheda);
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        nomiGruppi = (ArrayList<String>) getArguments().get("nomiGruppi");
        gruppi = (ArrayList<GruppoMuscolareDaXml>) getArguments().get("gruppi");
        esercizi = (ArrayList<EsercizioDaXml>) getArguments().get("esercizi");
        idUltimaScheda = getArguments().getInt("idUltimaScheda");

        // Giorni della settimana
        ArrayAdapter<CharSequence> adapterGiorni = ArrayAdapter.createFromResource(getActivity(),
                R.array.Giorni, android.R.layout.simple_spinner_dropdown_item);

        // Routine
        ArrayAdapter<CharSequence> adapterRoutine = ArrayAdapter.createFromResource(getActivity(),
                R.array.Routine, android.R.layout.simple_spinner_dropdown_item);

        // Gruppi muscolari
        ArrayAdapter<String> adapterGruppi = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                this.nomiGruppi);

        AlertDialog.Builder builderInserimentoEsercizi = new AlertDialog.Builder(getActivity());
        LayoutInflater inflaterInserimentoEsercizi = getActivity().getLayoutInflater();
        final View layoutInserimentoEsercizi = inflaterInserimentoEsercizi.inflate(R.layout.dialog_inserimento_esercizio, null);

        // Unità di misura
        RadioButton radioKg = (RadioButton) layoutInserimentoEsercizi.findViewById(R.id.radioKg);
        RadioButton radiolb = (RadioButton) layoutInserimentoEsercizi.findViewById(R.id.radiolb);
        final TextView textViewUnitaDiMisura = (TextView)layoutInserimentoEsercizi.findViewById(R.id.textViewUnitaDiMisura);
        radioKg.setChecked(true);
        textViewUnitaDiMisura.setText(R.string.testoLabelPesoKg);
        radioKg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewUnitaDiMisura.setText(R.string.testoLabelPesoKg);
            }
        });
        radiolb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewUnitaDiMisura.setText(R.string.testoLabelPesolb);
            }
        });

        Spinner spinnerGiorni = (Spinner) layoutInserimentoEsercizi.findViewById(R.id.spinnerGiorni);
        spinnerGiorni.setAdapter(adapterGiorni);

        Spinner spinnerRoutine = (Spinner) layoutInserimentoEsercizi.findViewById(R.id.spinnerRoutine);
        spinnerRoutine.setAdapter(adapterRoutine);

        Spinner spinnerGruppiMuscolari = (Spinner) layoutInserimentoEsercizi.findViewById(R.id.spinnerGruppiMuscolari);
        spinnerGruppiMuscolari.setAdapter(adapterGruppi);

        spinnerGruppiMuscolari.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Recupero l'id del gruppo selezionato
                boolean trovato = false;
                int contatorePosizione = -1; // Scandisce l'arraylist gruppi per cercare
                // il gruppo il cui testo è stato selezionato
                // dallo spinner per recuperarne i'id
                String nomeGruppoSelezionato = nomiGruppi.get(position);
                int idGruppoSelezionato = -1;
                while (!trovato) {
                    contatorePosizione++;
                    if (nomeGruppoSelezionato.equals(gruppi.get(contatorePosizione).getGruppo())) {
                        idGruppoSelezionato = gruppi.get(contatorePosizione).getId();
                        trovato = true;
                    }
                }

                nomiEsercizi = new ArrayList<>();
                eserciziFiltrati = new ArrayList<>();
                for (EsercizioDaXml singolo : esercizi) {
                    if (singolo.getIdGruppoMuscolare() == idGruppoSelezionato) {
                        eserciziFiltrati.add(singolo);
                        nomiEsercizi.add(singolo.getNome());
                    }
                }

                Collections.sort(nomiEsercizi);

                ArrayAdapter<String> adapterEsercizi = new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_spinner_dropdown_item,
                        nomiEsercizi);

                Spinner spinnerEsercizi = (Spinner) layoutInserimentoEsercizi.findViewById(R.id.spinnerEsercizi);
                spinnerEsercizi.setAdapter(adapterEsercizi);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Setto bottoni per settare il colore di sfondo
        Button btnBgBianco = (Button) layoutInserimentoEsercizi.findViewById(R.id.btnBgBianco);
        btnBgBianco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutInserimentoEsercizi.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorBianco));
                bgColor = "Bianco";
            }
        });

        Button btnBgViola = (Button) layoutInserimentoEsercizi.findViewById(R.id.btnBgViola);
        btnBgViola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutInserimentoEsercizi.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorViola));
                bgColor = "Viola";
            }
        });

        Button btnBgArancio = (Button) layoutInserimentoEsercizi.findViewById(R.id.btnBgArancio);
        btnBgArancio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutInserimentoEsercizi.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorArancio));
                bgColor = "Arancio";
            }
        });

        Button btnBgBlu = (Button) layoutInserimentoEsercizi.findViewById(R.id.btnBgBlu);
        btnBgBlu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutInserimentoEsercizi.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorBlu));
                bgColor = "Blu";
            }
        });

        Button btnBgAzzurro = (Button) layoutInserimentoEsercizi.findViewById(R.id.btnBgAzzurro);
        btnBgAzzurro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutInserimentoEsercizi.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorAzzurro));
                bgColor = "Azzurro";
            }
        });

        Button btnBgVerde = (Button) layoutInserimentoEsercizi.findViewById(R.id.btnBgVerde);
        btnBgVerde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutInserimentoEsercizi.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorVerde));
                bgColor = "Verde";
            }
        });

        Button btnBgGiallo = (Button) layoutInserimentoEsercizi.findViewById(R.id.btnBgGiallo);
        btnBgGiallo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutInserimentoEsercizi.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorGiallo));
                bgColor = "Giallo";
            }
        });

        Button btnBgRosso = (Button) layoutInserimentoEsercizi.findViewById(R.id.btnBgRosso);
        btnBgRosso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutInserimentoEsercizi.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorRosso));
                bgColor = "Rosso";
            }
        });

        Button btnBgRosa = (Button) layoutInserimentoEsercizi.findViewById(R.id.btnBgRosa);
        btnBgRosa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutInserimentoEsercizi.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorRosa));
                bgColor = "Rosa";
            }
        });

        Button btnBgVerdeAcqua = (Button) layoutInserimentoEsercizi.findViewById(R.id.btnBgVerdeAcqua);
        btnBgVerdeAcqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutInserimentoEsercizi.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorVerdeAcqua));
                bgColor = "VerdeAcqua";
            }
        });

        builderInserimentoEsercizi.setView(layoutInserimentoEsercizi);

        builderInserimentoEsercizi.setPositiveButton(R.string.testoBottoneSalva, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builderInserimentoEsercizi.setNegativeButton(R.string.testoBottoneChiudi, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onDialogInserimentoEsercizioNegativeClick(DialogInserimentoEsercizio.this);
            }
        });

        return builderInserimentoEsercizi.create();
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


    private String InserisciEsercizio(DialogInterface dialog) {
        try {
            Dialog d = (Dialog)dialog;

            // Ottengo i riferimenti ai campi del dialog
            Spinner spinnerGruppiMuscolari = (Spinner) d.findViewById(R.id.spinnerGruppiMuscolari);
            Spinner spinnerEsercizi = (Spinner) d.findViewById(R.id.spinnerEsercizi);
            TextView textViewUnitaDiMisura = (TextView) d.findViewById(R.id.textViewUnitaDiMisura);
            EditText editTextSerie = (EditText) d.findViewById(R.id.editTextSerie);
            EditText editTextRipetizioni = (EditText) d.findViewById(R.id.editTextRipetizioni);
            EditText editTextPeso = (EditText) d.findViewById(R.id.editTextPeso);
            EditText editTextRecupero = (EditText) d.findViewById(R.id.editTextRecupero);
            EditText editTextNote = (EditText) d.findViewById(R.id.editTextNote);
            Spinner spinnerGiorni = (Spinner) d.findViewById(R.id.spinnerGiorni);
            Spinner spinnerRoutine = (Spinner) d.findViewById(R.id.spinnerRoutine);
            EditText editTextMassimale = (EditText) d.findViewById(R.id.editTextPesoMassimale);
            EditText editTextPercentuale = (EditText) d.findViewById(R.id.editTextPercentuale);
            EditText editTextRPE = (EditText) d.findViewById(R.id.editTextRPE);

            int seriePerInsert = 0;
            if (!TextUtils.isEmpty(editTextSerie.getText().toString()))
                seriePerInsert = Integer.valueOf(editTextSerie.getText().toString());
            else {
                return  getString(R.string.testoInserireSerieRipetizioni);
            }

            String ripetizioniPerInsert;
            if (!TextUtils.isEmpty(editTextRipetizioni.getText().toString()))
                ripetizioniPerInsert = editTextRipetizioni.getText().toString();
            else {
                return  getString(R.string.testoInserireSerieRipetizioni);
            }

            // Leggo i valori inseriti dall'utente nel dialog
            GruppoMuscolareDaXml gruppoSelezionato = getGruppoBySpinnerPosition(spinnerGruppiMuscolari);
            int idGruppoSelezionatoPerInsert = gruppoSelezionato.getId();
            String nomeGruppoSelezionatoPerInsert = gruppoSelezionato.getGruppo();
            EsercizioDaXml esercizioSelezionato = getEsercizioBySpinnerPosition(spinnerEsercizi);
            int idEsercizioSelezionatoPerInsert = esercizioSelezionato.getId();
            String nomeEsercizioSelezionatoPerInsert = esercizioSelezionato.getNome();

            float pesoPerInsert = 0;
            if (!TextUtils.isEmpty(editTextPeso.getText().toString()))
                pesoPerInsert = Float.valueOf(editTextPeso.getText().toString());

            float recuperoPerInsert = 0;
            if (!TextUtils.isEmpty(editTextRecupero.getText().toString()))
                recuperoPerInsert = Float.valueOf(editTextRecupero.getText().toString());

            float massimalePerInsert = 0;
            if (!TextUtils.isEmpty(editTextMassimale.getText().toString()))
                massimalePerInsert = Float.valueOf(editTextMassimale.getText().toString());

            float percentualePerInsert = 0;
            if (!TextUtils.isEmpty(editTextPercentuale.getText().toString()))
                percentualePerInsert = Float.valueOf(editTextPercentuale.getText().toString());

            int rpePerInsert = 0;
            if (!TextUtils.isEmpty(editTextRPE.getText().toString()))
                rpePerInsert = Integer.valueOf(editTextRPE.getText().toString());

            String notePerInsert = editTextNote.getText().toString();

            String giornoPerInsert = GetSetValoriDiArray.getGiornoBySpinnerPosition(getActivity(), spinnerGiorni);

            String routinePerInsert = GetSetValoriDiArray.getRoutineBySpinnerPosition(getActivity(), spinnerRoutine);

            String unitaDiMisura = textViewUnitaDiMisura.getText().toString();

            EsercizioDaDb esercizioDaInserire = new EsercizioDaDb(0,
                    this.idUltimaScheda,
                    idGruppoSelezionatoPerInsert,
                    nomeGruppoSelezionatoPerInsert,
                    idEsercizioSelezionatoPerInsert,
                    nomeEsercizioSelezionatoPerInsert,
                    seriePerInsert,
                    recuperoPerInsert,
                    0,
                    notePerInsert,
                    giornoPerInsert,
                    0,
                    bgColor,
                    unitaDiMisura,
                    routinePerInsert,
                    massimalePerInsert);

            ArrayList<RipetizioniPesoDaDb> ripetizioniPeso = new ArrayList<>();
            RipetizioniPesoDaDb nuovo = new RipetizioniPesoDaDb(0,
                    ripetizioniPerInsert,
                    pesoPerInsert,
                    0,
                    percentualePerInsert,
                    rpePerInsert);
            ripetizioniPeso.add(nuovo);

            EserciziController eserciziController = new EserciziController(getActivity());
            long nuovoId = eserciziController.InsertEsercizio(esercizioDaInserire, ripetizioniPeso);
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
        RadioButton radioKg = (RadioButton) d.findViewById(R.id.radioKg);
        RadioButton radiolb = (RadioButton) d.findViewById(R.id.radiolb);
        TextView textViewUnitaDiMisura = (TextView) d.findViewById(R.id.textViewUnitaDiMisura);
        Spinner spinnerEsercizi = (Spinner) d.findViewById(R.id.spinnerEsercizi);
        Spinner spinnerGiorni = (Spinner) d.findViewById(R.id.spinnerGiorni);
        EditText editTextSerie = (EditText) d.findViewById(R.id.editTextSerie);
        EditText editTextRipetizioni = (EditText) d.findViewById(R.id.editTextRipetizioni);
        EditText editTextPeso = (EditText) d.findViewById(R.id.editTextPeso);
        EditText editTextRecupero = (EditText) d.findViewById(R.id.editTextRecupero);
        EditText editTextNote = (EditText) d.findViewById(R.id.editTextNote);
        Spinner spinnerRoutine = (Spinner) d.findViewById(R.id.spinnerRoutine);
        EditText editTextMassimale = (EditText) d.findViewById(R.id.editTextPesoMassimale);
        EditText editTextPercentuale = (EditText) d.findViewById(R.id.editTextPercentuale);
        EditText editTextRPE = (EditText) d.findViewById(R.id.editTextRPE);

        spinnerEsercizi.setSelection(0);
        spinnerGiorni.setSelection(0);
        editTextSerie.setText("");
        editTextRipetizioni.setText("");
        editTextPeso.setText("");
        editTextRecupero.setText("");
        editTextNote.setText("");
        spinnerRoutine.setSelection(0);
        editTextMassimale.setText("");
        editTextPercentuale.setText("");
        editTextRPE.setText("");

        radioKg.setChecked(true);
        textViewUnitaDiMisura.setText(R.string.testoLabelPesoKg);

        editTextSerie.requestFocus();
    }

    protected GruppoMuscolareDaXml getGruppoBySpinnerPosition(Spinner spinner) {
        try {
            boolean trovato = false;
            int contatorePosizione = -1; // Scandisce l'arraylist gruppi per cercare
            // il gruppo il cui testo è stato selezionato
            // dallo spinner per recuperarne i'id
            String nomeGruppoSelezionato = nomiGruppi.get(spinner.getSelectedItemPosition());
            GruppoMuscolareDaXml gruppoTmp = null;
            while (!trovato) {
                contatorePosizione++;
                gruppoTmp = gruppi.get(contatorePosizione);
                if (nomeGruppoSelezionato.equals(gruppoTmp.getGruppo())) {
                    trovato = true;
                }
            }
            return gruppoTmp;
        }
        catch (Exception ex) {
            return null;
        }
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
                esercizioTmp = eserciziFiltrati.get(contatorePosizione);
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
}
