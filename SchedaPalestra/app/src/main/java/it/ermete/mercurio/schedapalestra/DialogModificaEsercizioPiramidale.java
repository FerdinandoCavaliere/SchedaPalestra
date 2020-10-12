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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class DialogModificaEsercizioPiramidale extends DialogFragment {

    private String bgColor = "Bianco";
    private String unitaDiMisura = "Kg";

    public interface NoticeDialogModificaEsercizioPiramidaleListener {
        void onDialogModificaEsercizioPiramidalePositiveClick(DialogFragment dialog, String esito);
    }

    NoticeDialogModificaEsercizioPiramidaleListener mListener;

    EsercizioDaDb esercizioSelezionato;
    int idUltimaScheda;
    int contatoreSerie = 0;

    public static DialogModificaEsercizioPiramidale getInstance(EsercizioDaDb esercizioSelezionato,
                                                                int idUltimaScheda) {

        DialogModificaEsercizioPiramidale dialog = new DialogModificaEsercizioPiramidale();
        Bundle bundle = new Bundle();
        bundle.putSerializable("esercizioSelezionato", esercizioSelezionato);
        bundle.putInt("idUltimaScheda", idUltimaScheda);
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (NoticeDialogModificaEsercizioPiramidaleListener) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        esercizioSelezionato = (EsercizioDaDb)getArguments().get("esercizioSelezionato");
        idUltimaScheda = getArguments().getInt("idUltimaScheda");

        AlertDialog.Builder builderModificaEserciziPiramidali = new AlertDialog.Builder(getActivity());
        LayoutInflater inflaterModificaEserciziPiramidali = getActivity().getLayoutInflater();

        final View layoutModificaEserciziPiramidali = inflaterModificaEserciziPiramidali.inflate(R.layout.dialog_modifica_esercizio_piramidale, null);

        // Setto bottoni per settare il colore di sfondo
        Button btnBgBianco = (Button) layoutModificaEserciziPiramidali.findViewById(R.id.btnBgBianco);
        btnBgBianco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutModificaEserciziPiramidali.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorBianco));
                bgColor = "Bianco";
            }
        });

        Button btnBgViola = (Button) layoutModificaEserciziPiramidali.findViewById(R.id.btnBgViola);
        btnBgViola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutModificaEserciziPiramidali.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorViola));
                bgColor = "Viola";
            }
        });

        Button btnBgArancio = (Button) layoutModificaEserciziPiramidali.findViewById(R.id.btnBgArancio);
        btnBgArancio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutModificaEserciziPiramidali.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorArancio));
                bgColor = "Arancio";
            }
        });

        Button btnBgBlu = (Button) layoutModificaEserciziPiramidali.findViewById(R.id.btnBgBlu);
        btnBgBlu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutModificaEserciziPiramidali.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorBlu));
                bgColor = "Blu";
            }
        });

        Button btnBgAzzurro = (Button) layoutModificaEserciziPiramidali.findViewById(R.id.btnBgAzzurro);
        btnBgAzzurro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutModificaEserciziPiramidali.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorAzzurro));
                bgColor = "Azzurro";
            }
        });

        Button btnBgVerde = (Button) layoutModificaEserciziPiramidali.findViewById(R.id.btnBgVerde);
        btnBgVerde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutModificaEserciziPiramidali.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorVerde));
                bgColor = "Verde";
            }
        });

        Button btnBgGiallo = (Button) layoutModificaEserciziPiramidali.findViewById(R.id.btnBgGiallo);
        btnBgGiallo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutModificaEserciziPiramidali.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorGiallo));
                bgColor = "Giallo";
            }
        });

        Button btnBgRosso = (Button) layoutModificaEserciziPiramidali.findViewById(R.id.btnBgRosso);
        btnBgRosso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutModificaEserciziPiramidali.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorRosso));
                bgColor = "Rosso";
            }
        });

        Button btnBgRosa = (Button) layoutModificaEserciziPiramidali.findViewById(R.id.btnBgRosa);
        btnBgRosa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutModificaEserciziPiramidali.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorRosa));
                bgColor = "Rosa";
            }
        });

        Button btnBgVerdeAcqua = (Button) layoutModificaEserciziPiramidali.findViewById(R.id.btnBgVerdeAcqua);
        btnBgVerdeAcqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutModificaEserciziPiramidali.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorVerdeAcqua));
                bgColor = "VerdeAcqua";
            }
        });

        final LinearLayout linearLayoutContenitoreItemDinamici = (LinearLayout) layoutModificaEserciziPiramidali.findViewById(R.id.linearLayoutContenitoreItemDinamici);

        // Unità di misura
        RadioButton radioKg = (RadioButton) layoutModificaEserciziPiramidali.findViewById(R.id.radioKg);
        RadioButton radiolb = (RadioButton) layoutModificaEserciziPiramidali.findViewById(R.id.radiolb);

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

        RiempiEsercizioPerModifica(layoutModificaEserciziPiramidali, esercizioSelezionato);

        // Setto il listener del bottone per aggiungere ripetizioni e peso dinamiche
        final LayoutInflater inflaterItemInserimentoRipetizioniPeso = getActivity().getLayoutInflater();
        Button btnAggiungi = (Button) layoutModificaEserciziPiramidali.findViewById(R.id.btnAggiungi);
        btnAggiungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contatoreSerie++;
                View item = inflaterItemInserimentoRipetizioniPeso.inflate(R.layout.ripetizioni_peso_item_per_piramidale, null);
                item.setTag(contatoreSerie); // Tag del LinearLayout (singolo item)
                TextView textViewSerie = (TextView) item.findViewById(R.id.textViewSerie);
                textViewSerie.setText(Integer.toString(contatoreSerie));
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
                ((EditText) item.findViewById(R.id.editTextRipetizioni)).requestFocus();
                linearLayoutContenitoreItemDinamici.addView(item);
            }
        });

        builderModificaEserciziPiramidali.setView(layoutModificaEserciziPiramidali);
        builderModificaEserciziPiramidali.setPositiveButton(R.string.testoBottoneSalva, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String esito = ModificaEsercizioPiramidale(getDialog(), esercizioSelezionato);
                mListener.onDialogModificaEsercizioPiramidalePositiveClick(DialogModificaEsercizioPiramidale.this, esito);
            }
        });
        builderModificaEserciziPiramidali.setNegativeButton(R.string.testoBottoneAnnulla, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Nessuna azione
            }
        });

        return builderModificaEserciziPiramidali.create();
    }

    private void RiempiEsercizioPerModifica(View dialog, EsercizioDaDb esercizioDaModificare) {
        bgColor = esercizioDaModificare.getBgColor();
        // Riempio di campi dell'esercizio non dinamici
        // Ottengo i riferimenti ai campi del dialog
        LinearLayout linearLayoutContenitore = (LinearLayout) dialog.findViewById(R.id.linearLayoutContenitore) ;
        TextView textViewNomeGruppoMuscolare = (TextView) dialog.findViewById(R.id.textViewNomeGruppoMuscolare);
        TextView textViewNomeEsercizio = (TextView) dialog.findViewById(R.id.textViewNomeEsercizio);
        EditText editTextNote = (EditText) dialog.findViewById(R.id.editTextNote);
        RadioButton radioKg = (RadioButton) dialog.findViewById(R.id.radioKg);
        RadioButton radiolb = (RadioButton) dialog.findViewById(R.id.radiolb);
        EditText editTextMassimale = (EditText) dialog.findViewById(R.id.editTextPesoMassimale);

        unitaDiMisura = esercizioDaModificare.getUnitaDiMisura();
        if (esercizioDaModificare.getUnitaDiMisura().equals(getActivity().getResources().getString(R.string.testoLabelPesoKg))) {
            radioKg.setChecked(true);
        }
        else {
            radiolb.setChecked(true);
        }

        // Giorni della settimana
        ArrayAdapter<CharSequence> adapterGiorni = ArrayAdapter.createFromResource(getActivity(),
                R.array.Giorni, android.R.layout.simple_spinner_dropdown_item);

        Spinner spinnerGiorni = (Spinner) dialog.findViewById(R.id.spinnerGiorni);
        spinnerGiorni.setAdapter(adapterGiorni);

        // Routine
        ArrayAdapter<CharSequence> adapterRoutine = ArrayAdapter.createFromResource(getActivity(),
                R.array.Routine, android.R.layout.simple_spinner_dropdown_item);

        Spinner spinnerRoutine = (Spinner) dialog.findViewById(R.id.spinnerRoutine);
        spinnerRoutine.setAdapter(adapterRoutine);

        // Riempio i campi del dialog
        linearLayoutContenitore.setBackgroundResource(Utility.getBgColor(esercizioDaModificare.getBgColor()));
        textViewNomeGruppoMuscolare.setText(esercizioDaModificare.getNomeGruppoMuscolare());
        textViewNomeEsercizio.setText(esercizioDaModificare.getNomeEsercizio());
        editTextNote.setText(esercizioDaModificare.getNote());
        spinnerGiorni.setSelection(GetSetValoriDiArray.getPosizioneGiornoInArray(getActivity(), esercizioDaModificare.getGiorno()));
        spinnerRoutine.setSelection(GetSetValoriDiArray.getPosizioneRoutineInArray(getActivity(), esercizioDaModificare.getRoutine()));
        editTextMassimale.setText(esercizioDaModificare.getMassimale() > 0 ? Float.toString(esercizioDaModificare.getMassimale()) : "");

        // Riempio i campi dell'esercizio dinamici
        final LinearLayout linearLayoutContenitoreItemDinamici = (LinearLayout) dialog.findViewById(R.id.linearLayoutContenitoreItemDinamici);
        final LayoutInflater inflaterItemModificaRipetizioniPeso = getActivity().getLayoutInflater();
        ArrayList<RipetizioniPesoDaDb> ripetizioniPeso = esercizioDaModificare.getRipetizioniPeso();
        for (RipetizioniPesoDaDb singolo : ripetizioniPeso) {
            // Creao l'elemento dinamico
            contatoreSerie++;
            View item = inflaterItemModificaRipetizioniPeso.inflate(R.layout.ripetizioni_peso_item_per_piramidale, null);
            item.setTag(contatoreSerie); // Tag del LinearLayout (singolo item)
            TextView textViewSerie = (TextView) item.findViewById(R.id.textViewSerie);
            textViewSerie.setText(Integer.toString(contatoreSerie));
            EditText editTextRipetizioni = (EditText) item.findViewById(R.id.editTextRipetizioni);
            editTextRipetizioni.setText(singolo.getRipetizioni());
            EditText editTextPeso = (EditText) item.findViewById(R.id.editTextPeso);
            editTextPeso.setText(singolo.getPeso() > 0 ? Utility.FormatFloat(singolo.getPeso()).replace(",", ".") : "");
            TextView textViewUnitaDiMisura = (TextView) item.findViewById(R.id.textViewUnitaDiMisura);
            textViewUnitaDiMisura.setText(unitaDiMisura);
            EditText editTextRecuperoPiramidale = (EditText) item.findViewById(R.id.editTextRecuperoPiramidale);
            editTextRecuperoPiramidale.setText(singolo.getRecupero() > 0 ? Utility.FormatFloat(singolo.getRecupero()).replace(",", ".") : "");
            EditText editTextPercentualePiramidale = (EditText) item.findViewById(R.id.editTextPercentualePiramidale);
            editTextPercentualePiramidale.setText(singolo.getPercentuale() > 0 ? Utility.FormatFloat(singolo.getPercentuale()).replace(",", ".") : "");
            EditText editTextRPEPiramidale = (EditText) item.findViewById(R.id.editTextRPEPiramidale);
            editTextRPEPiramidale.setText(singolo.getRpe() > 0 ? Integer.toString(singolo.getRpe()) : "");

            Button btnElimina = (Button) item.findViewById(R.id.btnElimina);
            if (contatoreSerie == 1) {
                // Il primo idem non può essere eliminato
                btnElimina.setVisibility(View.INVISIBLE);
            }
            else {
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
            }
            linearLayoutContenitoreItemDinamici.addView(item);
        }
    }

    private String ModificaEsercizioPiramidale(DialogInterface dialog, EsercizioDaDb esercizioDaModificare) {
        try {

            Dialog d = (Dialog)dialog;

            esercizioDaModificare.setIdScheda_FK(idUltimaScheda);

            esercizioDaModificare.setBgColor(bgColor);

            esercizioDaModificare.setUnitaDiMisura(unitaDiMisura);

            // Leggo ripetizioni e serie
            LinearLayout linearLayoutContenitoreItemDinamici = (LinearLayout) d.findViewById(R.id.linearLayoutContenitoreItemDinamici);
            ArrayList<RipetizioniPesoDaDb> ripetizioniPeso = new ArrayList<>();
            LinearLayout linearLayoutSingoloRipetizioniPeso;
            String ripetizioniPerUpdate;
            float pesoPerUpdate = 0;
            float recuperoPerUpdate = 0;
            float percentualePerUpdate = 0;
            int rpePerUpdate = 0;
            EditText editTextRipetizioni;
            EditText editTextPeso;
            EditText editTextRecuperoPiramidale;
            EditText editTextPercentualePiramidale;
            EditText editTextRPEPiramidale;
            for (int i = 1; i <= contatoreSerie; i++) {
                linearLayoutSingoloRipetizioniPeso = (LinearLayout) linearLayoutContenitoreItemDinamici.findViewWithTag(i);

                ripetizioniPerUpdate = "";
                editTextRipetizioni = (EditText) linearLayoutSingoloRipetizioniPeso.findViewById(R.id.editTextRipetizioni);
                if (!TextUtils.isEmpty(editTextRipetizioni.getText().toString()))
                    ripetizioniPerUpdate = editTextRipetizioni.getText().toString();
                else {
                    return getString(R.string.testoInserireRipetizioniPerPiramidale);
                }

                pesoPerUpdate = 0;
                editTextPeso = (EditText) linearLayoutSingoloRipetizioniPeso.findViewById(R.id.editTextPeso);
                if (!TextUtils.isEmpty(editTextPeso.getText().toString()))
                    pesoPerUpdate = Float.valueOf(editTextPeso.getText().toString());

                recuperoPerUpdate = 0;
                editTextRecuperoPiramidale = (EditText) linearLayoutSingoloRipetizioniPeso.findViewById(R.id.editTextRecuperoPiramidale);
                if (!TextUtils.isEmpty(editTextRecuperoPiramidale.getText().toString()))
                    recuperoPerUpdate = Float.valueOf(editTextRecuperoPiramidale.getText().toString());

                percentualePerUpdate = 0;
                editTextPercentualePiramidale = (EditText) linearLayoutSingoloRipetizioniPeso.findViewById(R.id.editTextPercentualePiramidale);
                if (!TextUtils.isEmpty(editTextPercentualePiramidale.getText().toString()))
                    percentualePerUpdate = Float.valueOf(editTextPercentualePiramidale.getText().toString());

                rpePerUpdate = 0;
                editTextRPEPiramidale =  (EditText) linearLayoutSingoloRipetizioniPeso.findViewById(R.id.editTextRPEPiramidale);
                if (!TextUtils.isEmpty(editTextRPEPiramidale.getText().toString()))
                    rpePerUpdate = Integer.valueOf(editTextRPEPiramidale.getText().toString());

                RipetizioniPesoDaDb nuovo = new RipetizioniPesoDaDb(0, ripetizioniPerUpdate, pesoPerUpdate, recuperoPerUpdate, percentualePerUpdate, rpePerUpdate);
                ripetizioniPeso.add(nuovo);
            }

            // Ottengo i riferimenti ai campi del dialog
            EditText editTextNote = (EditText) d.findViewById(R.id.editTextNote);

            esercizioDaModificare.setNote(editTextNote.getText().toString());

            Spinner spinnerGiorni = (Spinner) d.findViewById(R.id.spinnerGiorni);
            esercizioDaModificare.setGiorno(GetSetValoriDiArray.getGiornoBySpinnerPosition(getActivity(), spinnerGiorni));

            Spinner spinnerRoutine = (Spinner) d.findViewById(R.id.spinnerRoutine);
            esercizioDaModificare.setRoutine(GetSetValoriDiArray.getRoutineBySpinnerPosition(getActivity(), spinnerRoutine));

            esercizioDaModificare.setIsPiramidale(1);

            EditText editTextMassimale = (EditText) d.findViewById(R.id.editTextPesoMassimale);
            float massimalePerUpdate = 0;
            if (!TextUtils.isEmpty(editTextMassimale.getText().toString())) {
                massimalePerUpdate = Float.valueOf(editTextMassimale.getText().toString());
            }
            esercizioDaModificare.setMassimale(massimalePerUpdate);

            EserciziController eserciziController = new EserciziController(getActivity());
            int righeModificate = eserciziController.UpdateEsercizio(esercizioDaModificare, ripetizioniPeso);
            if (righeModificate == 0)
                return getString(R.string.testoEsercizioNonInserito);

            return "";
        }
        catch (Exception ex) {
            return ex.getMessage();
        }
    }
}
