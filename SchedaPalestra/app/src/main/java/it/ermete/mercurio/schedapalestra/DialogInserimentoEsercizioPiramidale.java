package it.ermete.mercurio.schedapalestra;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
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

public class DialogInserimentoEsercizioPiramidale extends DialogFragment {

    public interface NoticeDialogInserimentoEsercizioPiramidaleListener {
        void onDialogInserimentoEsercizioPiramidaleNegativeClick(DialogFragment dialog);
    }

    NoticeDialogInserimentoEsercizioPiramidaleListener mListener;

    private ArrayList<String> nomiGruppi;
    private ArrayList<GruppoMuscolareDaXml> gruppi;
    private ArrayList<String> nomiEsercizi;
    private ArrayList<EsercizioDaXml> esercizi;
    private ArrayList<EsercizioDaXml> eserciziFiltrati;
    private int idUltimaScheda;
    private int contatoreSerie = 1;
    private String bgColor = "Bianco";
    private String unitaDiMisura = "Kg";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (NoticeDialogInserimentoEsercizioPiramidaleListener) activity;
    }

    public static DialogInserimentoEsercizioPiramidale getInstance(ArrayList<String> nomiGruppi,
                                                         ArrayList<GruppoMuscolareDaXml> gruppi,
                                                         ArrayList<EsercizioDaXml> esercizi,
                                                         int idUltimaScheda) {

        DialogInserimentoEsercizioPiramidale dialog = new DialogInserimentoEsercizioPiramidale();
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
        LayoutInflater inflaterInserimentoEserciziPiramidali = getActivity().getLayoutInflater();
        final View layoutInserimentoEserciziPiramidali = inflaterInserimentoEserciziPiramidali.inflate(R.layout.dialog_inserimento_esercizio_piramidale, null);

        final LinearLayout linearLayoutContenitoreItemDinamici = (LinearLayout) layoutInserimentoEserciziPiramidali.findViewById(R.id.linearLayoutContenitoreItemDinamici);

        // Unità di misura
        RadioButton radioKg = (RadioButton) layoutInserimentoEserciziPiramidali.findViewById(R.id.radioKg);
        RadioButton radiolb = (RadioButton) layoutInserimentoEserciziPiramidali.findViewById(R.id.radiolb);

        radioKg.setChecked(true);
        radioKg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unitaDiMisura = getActivity().getResources().getString(R.string.testoLabelPesoKg);
                for (int i = 1; i <= contatoreSerie; i++) {
                    LinearLayout linearLayoutSingoloRipetizioniPeso = (LinearLayout) linearLayoutContenitoreItemDinamici.findViewWithTag(i);
                    TextView textViewUnitaDiMisura = (TextView) linearLayoutSingoloRipetizioniPeso.findViewById(R.id.textViewUnitaDiMisura);
                    textViewUnitaDiMisura.setText(unitaDiMisura);
                }
            }
        });
        radiolb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unitaDiMisura = getActivity().getResources().getString(R.string.testoLabelPesolb);
                for (int i = 1; i <= contatoreSerie; i++) {
                    LinearLayout linearLayoutSingoloRipetizioniPeso = (LinearLayout) linearLayoutContenitoreItemDinamici.findViewWithTag(i);
                    TextView textViewUnitaDiMisura = (TextView) linearLayoutSingoloRipetizioniPeso.findViewById(R.id.textViewUnitaDiMisura);
                    textViewUnitaDiMisura.setText(unitaDiMisura);
                }
            }
        });

        Spinner spinnerGiorni = (Spinner) layoutInserimentoEserciziPiramidali.findViewById(R.id.spinnerGiorni);
        spinnerGiorni.setAdapter(adapterGiorni);

        Spinner spinnerRoutine = (Spinner) layoutInserimentoEserciziPiramidali.findViewById(R.id.spinnerRoutine);
        spinnerRoutine.setAdapter(adapterRoutine);

        Spinner spinnerGruppiMuscolari = (Spinner) layoutInserimentoEserciziPiramidali.findViewById(R.id.spinnerGruppiMuscolari);
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

                Spinner spinnerEsercizi = (Spinner) layoutInserimentoEserciziPiramidali.findViewById(R.id.spinnerEsercizi);
                spinnerEsercizi.setAdapter(adapterEsercizi);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Setto bottoni per settare il colore di sfondo
        Button btnBgBianco = (Button) layoutInserimentoEserciziPiramidali.findViewById(R.id.btnBgBianco);
        btnBgBianco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutInserimentoEserciziPiramidali.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorBianco));
                bgColor = "Bianco";
            }
        });

        Button btnBgViola = (Button) layoutInserimentoEserciziPiramidali.findViewById(R.id.btnBgViola);
        btnBgViola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutInserimentoEserciziPiramidali.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorViola));
                bgColor = "Viola";
            }
        });

        Button btnBgArancio = (Button) layoutInserimentoEserciziPiramidali.findViewById(R.id.btnBgArancio);
        btnBgArancio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutInserimentoEserciziPiramidali.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorArancio));
                bgColor = "Arancio";
            }
        });

        Button btnBgBlu = (Button) layoutInserimentoEserciziPiramidali.findViewById(R.id.btnBgBlu);
        btnBgBlu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutInserimentoEserciziPiramidali.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorBlu));
                bgColor = "Blu";
            }
        });

        Button btnBgAzzurro = (Button) layoutInserimentoEserciziPiramidali.findViewById(R.id.btnBgAzzurro);
        btnBgAzzurro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutInserimentoEserciziPiramidali.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorAzzurro));
                bgColor = "Azzurro";
            }
        });

        Button btnBgVerde = (Button) layoutInserimentoEserciziPiramidali.findViewById(R.id.btnBgVerde);
        btnBgVerde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutInserimentoEserciziPiramidali.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorVerde));
                bgColor = "Verde";
            }
        });

        Button btnBgGiallo = (Button) layoutInserimentoEserciziPiramidali.findViewById(R.id.btnBgGiallo);
        btnBgGiallo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutInserimentoEserciziPiramidali.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorGiallo));
                bgColor = "Giallo";
            }
        });

        Button btnBgRosso = (Button) layoutInserimentoEserciziPiramidali.findViewById(R.id.btnBgRosso);
        btnBgRosso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutInserimentoEserciziPiramidali.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorRosso));
                bgColor = "Rosso";
            }
        });

        Button btnBgRosa = (Button) layoutInserimentoEserciziPiramidali.findViewById(R.id.btnBgRosa);
        btnBgRosa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutInserimentoEserciziPiramidali.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorRosa));
                bgColor = "Rosa";
            }
        });

        Button btnBgVerdeAcqua = (Button) layoutInserimentoEserciziPiramidali.findViewById(R.id.btnBgVerdeAcqua);
        btnBgVerdeAcqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutInserimentoEserciziPiramidali.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorVerdeAcqua));
                bgColor = "VerdeAcqua";
            }
        });

        builderInserimentoEsercizi.setView(layoutInserimentoEserciziPiramidali);

        builderInserimentoEsercizi.setPositiveButton(R.string.testoBottoneSalva, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builderInserimentoEsercizi.setNegativeButton(R.string.testoBottoneChiudi, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onDialogInserimentoEsercizioPiramidaleNegativeClick(DialogInserimentoEsercizioPiramidale.this);
            }
        });

//        final LinearLayout linearLayoutContenitoreItemDinamici = (LinearLayout) layoutInserimentoEserciziPiramidali.findViewById(R.id.linearLayoutContenitoreItemDinamici);
        final LayoutInflater inflaterItemInserimentoRipetizioniPeso = getActivity().getLayoutInflater();

        // Aggiungo listener per l'aggiunta di item
        Button btnAggiungi = (Button) layoutInserimentoEserciziPiramidali.findViewById(R.id.btnAggiungi);
        btnAggiungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contatoreSerie++;
                View item = inflaterItemInserimentoRipetizioniPeso.inflate(R.layout.ripetizioni_peso_item_per_piramidale, null);
                item.setTag(contatoreSerie); // Tag del LinearLayout (singolo item)
                TextView textViewSerie = (TextView) item.findViewById(R.id.textViewSerie);
                textViewSerie.setText(Integer.toString(contatoreSerie));
                TextView textViewUnitaDiMisura = (TextView) item.findViewById(R.id.textViewUnitaDiMisura);
                textViewUnitaDiMisura.setText(unitaDiMisura);
                Button btnElimina = (Button) item.findViewById(R.id.btnElimina);
                btnElimina.setTag(contatoreSerie + 100); // Tag del Button di eliminazione. Facendo questo tag - 100 pttengo il tag del LinearLayout che lo contiene
                btnElimina.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Tag del bottone
                        int tagBottone = Integer.parseInt(String.valueOf(v.getTag()));
                        int tagLinearDaEliminare = tagBottone - 100;
                        View linearDaEliminare = linearLayoutContenitoreItemDinamici.findViewWithTag(tagLinearDaEliminare);
                        linearLayoutContenitoreItemDinamici.removeView(linearDaEliminare);
                        // Shift di coppie ripetizioni - peso
                        if (tagLinearDaEliminare < contatoreSerie) {
                            for (int i = tagLinearDaEliminare + 1; i <= contatoreSerie; i++) {
                                // reperisco il LinearLayout da shiftare in avanti
                                View linearDaModificare = linearLayoutContenitoreItemDinamici.findViewWithTag(i);
                                // Ricostruisco i valori dei Tag e della TextView del numero della i-ma serie
                                int interoShiftatoPerNuovoTag = i - 1;
                                Button btnElimina = (Button) linearDaModificare.findViewById(R.id.btnElimina);
                                btnElimina.setTag(interoShiftatoPerNuovoTag + 100);
                                TextView textViewSerie = (TextView) linearDaModificare.findViewById(R.id.textViewSerie);
                                textViewSerie.setText(Integer.toString(interoShiftatoPerNuovoTag));
                                linearDaModificare.setTag(interoShiftatoPerNuovoTag);
                            }
                        }
                        contatoreSerie--;
                    }
                });
                ((EditText)item.findViewById(R.id.editTextRipetizioni)).requestFocus();
                linearLayoutContenitoreItemDinamici.addView(item);
            }
        });

        // Aggiungo il primo item di inserimento peso e ripetizione
        aggiungiPrimoItem(inflaterItemInserimentoRipetizioniPeso, linearLayoutContenitoreItemDinamici);

        return builderInserimentoEsercizi.create();
    }

    private void aggiungiPrimoItem(LayoutInflater inflaterItemInserimentoRipetizioniPeso,
                                   LinearLayout linearLayoutContenitoreItemDinamici) {
        View item = inflaterItemInserimentoRipetizioniPeso.inflate(R.layout.ripetizioni_peso_item_per_piramidale, null);
        item.setTag(contatoreSerie);
        TextView textViewSerie = (TextView) item.findViewById(R.id.textViewSerie);
        textViewSerie.setText(Integer.toString(contatoreSerie));
        TextView textViewUnitaDiMisura = (TextView) item.findViewById(R.id.textViewUnitaDiMisura);
        textViewUnitaDiMisura.setText(unitaDiMisura);
        Button btnElimina = (Button) item.findViewById(R.id.btnElimina);
        btnElimina.setVisibility(View.INVISIBLE);
        linearLayoutContenitoreItemDinamici.addView(item);
        ((EditText)item.findViewById(R.id.editTextRipetizioni)).requestFocus();
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

            // Leggo ripetizioni e serie
            LinearLayout linearLayoutContenitoreItemDinamici = (LinearLayout) d.findViewById(R.id.linearLayoutContenitoreItemDinamici);
            ArrayList<RipetizioniPesoDaDb> ripetizioniPeso = new ArrayList<>();
            LinearLayout linearLayoutSingoloRipetizioniPeso;
            String ripetizioniPerInsert;
            float pesoPerInsert = 0;
            float recuperoPerInsert = 0;
            EditText editTextRipetizioni;
            EditText editTextPeso;
            EditText editTextRecuperoPiramidale;
            EditText editTextPercentualePiramidale;
            EditText editTextRPEPiramidale;
            for (int i = 1; i <= contatoreSerie; i++) {
                linearLayoutSingoloRipetizioniPeso = (LinearLayout) linearLayoutContenitoreItemDinamici.findViewWithTag(i);

                ripetizioniPerInsert = "";
                editTextRipetizioni = (EditText) linearLayoutSingoloRipetizioniPeso.findViewById(R.id.editTextRipetizioni);
                if (!TextUtils.isEmpty(editTextRipetizioni.getText().toString()))
                    ripetizioniPerInsert = editTextRipetizioni.getText().toString();
                else {
                    return getString(R.string.testoInserireRipetizioniPerPiramidale);
                }

                pesoPerInsert = 0;
                editTextPeso = (EditText) linearLayoutSingoloRipetizioniPeso.findViewById(R.id.editTextPeso);
                if (!TextUtils.isEmpty(editTextPeso.getText().toString()))
                    pesoPerInsert = Float.valueOf(editTextPeso.getText().toString());

                recuperoPerInsert = 0;
                editTextRecuperoPiramidale = (EditText) linearLayoutSingoloRipetizioniPeso.findViewById(R.id.editTextRecuperoPiramidale);
                if (!TextUtils.isEmpty(editTextRecuperoPiramidale.getText().toString()))
                    recuperoPerInsert = Float.valueOf(editTextRecuperoPiramidale.getText().toString());

                float percentualePerInsert = 0;
                editTextPercentualePiramidale = (EditText) linearLayoutSingoloRipetizioniPeso.findViewById(R.id.editTextPercentualePiramidale);
                if (!TextUtils.isEmpty(editTextPercentualePiramidale.getText().toString()))
                    percentualePerInsert = Float.valueOf(editTextPercentualePiramidale.getText().toString());

                int rpePerInsert = 0;
                editTextRPEPiramidale =  (EditText) linearLayoutSingoloRipetizioniPeso.findViewById(R.id.editTextRPEPiramidale);
                if (!TextUtils.isEmpty(editTextRPEPiramidale.getText().toString()))
                    rpePerInsert = Integer.valueOf(editTextRPEPiramidale.getText().toString());

                RipetizioniPesoDaDb nuovo = new RipetizioniPesoDaDb(0, ripetizioniPerInsert, pesoPerInsert, recuperoPerInsert, percentualePerInsert, rpePerInsert);
                ripetizioniPeso.add(nuovo);
            }

            // Leggo i dati dell'esercizio
            // Ottengo i riferimenti ai campi del dialog
            Spinner spinnerGruppiMuscolari = (Spinner) d.findViewById(R.id.spinnerGruppiMuscolari);
            Spinner spinnerEsercizi = (Spinner) d.findViewById(R.id.spinnerEsercizi);
            EditText editTextRecupero = (EditText) d.findViewById(R.id.editTextRecupero);
            EditText editTextNote = (EditText) d.findViewById(R.id.editTextNote);
            Spinner spinnerGiorni = (Spinner) d.findViewById(R.id.spinnerGiorni);
            Spinner spinnerRoutine = (Spinner) d.findViewById(R.id.spinnerRoutine);
            EditText editTextMassimale = (EditText) d.findViewById(R.id.editTextPesoMassimale);

            // Leggo i valori inseriti dall'utente nel dialog
            GruppoMuscolareDaXml gruppoSelezionato = getGruppoBySpinnerPosition(spinnerGruppiMuscolari);
            int idGruppoSelezionatoPerInsert = gruppoSelezionato.getId();
            String nomeGruppoSelezionatoPerInsert = gruppoSelezionato.getGruppo();
            EsercizioDaXml esercizioSelezionato = getEsercizioBySpinnerPosition(spinnerEsercizi);
            int idEsercizioSelezionatoPerInsert = esercizioSelezionato.getId();
            String nomeEsercizioSelezionatoPerInsert = esercizioSelezionato.getNome();

            String notePerInsert = editTextNote.getText().toString();

            String giornoPerInsert = GetSetValoriDiArray.getGiornoBySpinnerPosition(getActivity(), spinnerGiorni);

            String routinePerInsert = GetSetValoriDiArray.getRoutineBySpinnerPosition(getActivity(), spinnerRoutine);

            float massimalePerInsert = 0;
            if (!TextUtils.isEmpty(editTextMassimale.getText().toString()))
                massimalePerInsert = Float.valueOf(editTextMassimale.getText().toString());

            EsercizioDaDb esercizioDaInserire = new EsercizioDaDb(0,
                    this.idUltimaScheda,
                    idGruppoSelezionatoPerInsert,
                    nomeGruppoSelezionatoPerInsert,
                    idEsercizioSelezionatoPerInsert,
                    nomeEsercizioSelezionatoPerInsert,
                    contatoreSerie,
                    recuperoPerInsert,
                    0,
                    notePerInsert,
                    giornoPerInsert,
                    1,
                    bgColor,
                    unitaDiMisura,
                    routinePerInsert,
                    massimalePerInsert);

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
        try {
            Dialog d = (Dialog)dialog;

            // Ottengo i riferimenti ai campi del dialog
            Spinner spinnerEsercizi = (Spinner) d.findViewById(R.id.spinnerEsercizi);
            RadioButton radioKg = (RadioButton) d.findViewById(R.id.radioKg);
            RadioButton radiolb = (RadioButton) d.findViewById(R.id.radiolb);
            Spinner spinnerGiorni = (Spinner) d.findViewById(R.id.spinnerGiorni);
            EditText editTextNote = (EditText) d.findViewById(R.id.editTextNote);
            Spinner spinnerRoutine = (Spinner) d.findViewById(R.id.spinnerRoutine);

            spinnerEsercizi.setSelection(0);
            spinnerGiorni.setSelection(0);
            editTextNote.setText("");
            spinnerRoutine.setSelection(0);

            unitaDiMisura = getActivity().getResources().getString(R.string.testoLabelPesoKg);
            radioKg.setChecked(true);

            // Ripetizioni Pesi dinamici
            LinearLayout linearLayoutContenitoreItemDinamici = (LinearLayout) d.findViewById(R.id.linearLayoutContenitoreItemDinamici);
            LinearLayout linearLayoutSingoloRipetizioniPeso;
            for (int i=1; i <= contatoreSerie; i++) {
                linearLayoutSingoloRipetizioniPeso = (LinearLayout) linearLayoutContenitoreItemDinamici.findViewWithTag(i);
                linearLayoutContenitoreItemDinamici.removeView(linearLayoutSingoloRipetizioniPeso);
            }

            contatoreSerie = 1;
            this.aggiungiPrimoItem(getActivity().getLayoutInflater(), linearLayoutContenitoreItemDinamici);
        }
        catch (Exception ex) {
            Log.d("Eccezione", ex.getMessage());
        }
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
