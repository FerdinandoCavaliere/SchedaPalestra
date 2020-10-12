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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class DialogModificaEsercizio extends DialogFragment {

    public interface NoticeDialogModificaEsercizioListener {
        void onDialogModificaEsercizioPositiveClick(DialogFragment dialog, String esito);
    }

    NoticeDialogModificaEsercizioListener mListener;

    EsercizioDaDb esercizioSelezionato;
    int idUltimaScheda;
    private String bgColor = "Bianco";

    public static DialogModificaEsercizio getInstance(EsercizioDaDb esercizioSelezionato,
                                                      int idUltimaScheda) {

        DialogModificaEsercizio dialog = new DialogModificaEsercizio();
        Bundle bundle = new Bundle();
        bundle.putSerializable("esercizioSelezionato", esercizioSelezionato);
        bundle.putInt("idUltimaScheda", idUltimaScheda);
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (NoticeDialogModificaEsercizioListener) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        esercizioSelezionato = (EsercizioDaDb)getArguments().get("esercizioSelezionato");
        idUltimaScheda = getArguments().getInt("idUltimaScheda");

        AlertDialog.Builder builderModificaEsercizi = new AlertDialog.Builder(getActivity());
        LayoutInflater inflaterModificaEsercizi = getActivity().getLayoutInflater();

        final View layoutModificaEsercizi = inflaterModificaEsercizi.inflate(R.layout.dialog_modifica_esercizio, null);

        // Setto bottoni per settare il colore di sfondo
        Button btnBgBianco = (Button) layoutModificaEsercizi.findViewById(R.id.btnBgBianco);
        btnBgBianco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutModificaEsercizi.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorBianco));
                bgColor = "Bianco";
            }
        });

        Button btnBgViola = (Button) layoutModificaEsercizi.findViewById(R.id.btnBgViola);
        btnBgViola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutModificaEsercizi.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorViola));
                bgColor = "Viola";
            }
        });

        Button btnBgArancio = (Button) layoutModificaEsercizi.findViewById(R.id.btnBgArancio);
        btnBgArancio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutModificaEsercizi.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorArancio));
                bgColor = "Arancio";
            }
        });

        Button btnBgBlu = (Button) layoutModificaEsercizi.findViewById(R.id.btnBgBlu);
        btnBgBlu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutModificaEsercizi.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorBlu));
                bgColor = "Blu";
            }
        });

        Button btnBgAzzurro = (Button) layoutModificaEsercizi.findViewById(R.id.btnBgAzzurro);
        btnBgAzzurro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutModificaEsercizi.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorAzzurro));
                bgColor = "Azzurro";
            }
        });

        Button btnBgVerde = (Button) layoutModificaEsercizi.findViewById(R.id.btnBgVerde);
        btnBgVerde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutModificaEsercizi.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorVerde));
                bgColor = "Verde";
            }
        });

        Button btnBgGiallo = (Button) layoutModificaEsercizi.findViewById(R.id.btnBgGiallo);
        btnBgGiallo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutModificaEsercizi.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorGiallo));
                bgColor = "Giallo";
            }
        });

        Button btnBgRosso = (Button) layoutModificaEsercizi.findViewById(R.id.btnBgRosso);
        btnBgRosso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutModificaEsercizi.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorRosso));
                bgColor = "Rosso";
            }
        });

        Button btnBgRosa = (Button) layoutModificaEsercizi.findViewById(R.id.btnBgRosa);
        btnBgRosa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutModificaEsercizi.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorRosa));
                bgColor = "Rosa";
            }
        });

        Button btnBgVerdeAcqua = (Button) layoutModificaEsercizi.findViewById(R.id.btnBgVerdeAcqua);
        btnBgVerdeAcqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutModificaEsercizi.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorVerdeAcqua));
                bgColor = "VerdeAcqua";
            }
        });

        // Unità di misura
        RadioButton radioKg = (RadioButton) layoutModificaEsercizi.findViewById(R.id.radioKg);
        RadioButton radiolb = (RadioButton) layoutModificaEsercizi.findViewById(R.id.radiolb);
        final TextView textViewUnitaDiMisura = (TextView)layoutModificaEsercizi.findViewById(R.id.textViewUnitaDiMisura);
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

        RiempiEsercizioPerModifica(layoutModificaEsercizi, esercizioSelezionato);

        builderModificaEsercizi.setView(layoutModificaEsercizi);
        builderModificaEsercizi.setPositiveButton(R.string.testoBottoneSalva, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String esito = ModificaEsercizio(getDialog(), esercizioSelezionato);
                mListener.onDialogModificaEsercizioPositiveClick(DialogModificaEsercizio.this, esito);
            }
        });
        builderModificaEsercizi.setNegativeButton(R.string.testoBottoneAnnulla, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Nessuna azione
            }
        });

        return builderModificaEsercizi.create();
    }

    private void RiempiEsercizioPerModifica(View dialog, EsercizioDaDb esercizioDaModificare) {
        try {
            bgColor = esercizioDaModificare.getBgColor();
            // Ottengo i riferimenti ai campi del dialog
            LinearLayout linearLayoutContenitore = (LinearLayout) dialog.findViewById(R.id.linearLayoutContenitore) ;
            TextView textViewNomeGruppoMuscolare = (TextView) dialog.findViewById(R.id.textViewNomeGruppoMuscolare);
            TextView textViewNomeEsercizio = (TextView) dialog.findViewById(R.id.textViewNomeEsercizio);
            EditText editTextSerie = (EditText) dialog.findViewById(R.id.editTextSerie);
            EditText editTextRipetizioni = (EditText) dialog.findViewById(R.id.editTextRipetizioni);
            EditText editTextPeso = (EditText) dialog.findViewById(R.id.editTextPeso);
            EditText editTextRecupero = (EditText) dialog.findViewById(R.id.editTextRecupero);
            EditText editTextNote = (EditText) dialog.findViewById(R.id.editTextNote);
            RadioButton radioKg = (RadioButton) dialog.findViewById(R.id.radioKg);
            RadioButton radiolb = (RadioButton) dialog.findViewById(R.id.radiolb);
            TextView textViewUnitaDiMisura = (TextView) dialog.findViewById(R.id.textViewUnitaDiMisura);
            EditText editTextMassimale = (EditText) dialog.findViewById(R.id.editTextPesoMassimale);
            EditText editTextPercentuale = (EditText) dialog.findViewById(R.id.editTextPercentuale);
            EditText editTextRPE = (EditText) dialog.findViewById(R.id.editTextRPE);

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
            editTextSerie.setText(esercizioDaModificare.getSerie() > 0 ? Integer.toString(esercizioDaModificare.getSerie()) : "");
            editTextRecupero.setText(esercizioDaModificare.getRecupero() > 0 ? Utility.FormatFloat(esercizioDaModificare.getRecupero()).replace(",", ".") : "");
            editTextNote.setText(esercizioDaModificare.getNote());
            spinnerGiorni.setSelection(GetSetValoriDiArray.getPosizioneGiornoInArray(getActivity(), esercizioDaModificare.getGiorno()));
            spinnerRoutine.setSelection(GetSetValoriDiArray.getPosizioneRoutineInArray(getActivity(), esercizioDaModificare.getRoutine()));
            textViewUnitaDiMisura.setText(esercizioDaModificare.getUnitaDiMisura());
            editTextMassimale.setText(esercizioDaModificare.getMassimale() > 0 ? Float.toString(esercizioDaModificare.getMassimale()) : "");
            if (esercizioDaModificare.getUnitaDiMisura().equals(getActivity().getResources().getString(R.string.testoLabelPesoKg))) {
                radioKg.setChecked(true);
            }
            else {
                radiolb.setChecked(true);
            }

            ArrayList<RipetizioniPesoDaDb> ripetizioniPeso = esercizioDaModificare.getRipetizioniPeso();
            RipetizioniPesoDaDb singolo = ripetizioniPeso.get(0);
            editTextRipetizioni.setText(singolo.getRipetizioni());
            editTextPeso.setText(singolo.getPeso() > 0 ? Utility.FormatFloat(singolo.getPeso()).replace(",", ".") : "");
            editTextPercentuale.setText(singolo.getPercentuale() > 0 ? Utility.FormatFloat(singolo.getPercentuale()).replace(",", ".") : "");
            editTextRPE.setText(singolo.getRpe() > 0 ? Integer.toString(singolo.getRpe()) : "");
        }
        catch (Exception ex) {
            Log.d("RiempiEsercizio", ex.getMessage());
        }
    }

    private String ModificaEsercizio(DialogInterface dialog, EsercizioDaDb esercizioDaModificare) {
        try {
            Dialog d = (Dialog)dialog;

            esercizioDaModificare.setIdScheda_FK(idUltimaScheda);

            esercizioDaModificare.setBgColor(bgColor);

            // Ottengo i riferimenti ai campi del dialog
            EditText editTextSerie = (EditText) d.findViewById(R.id.editTextSerie);
            EditText editTextRipetizioni = (EditText) d.findViewById(R.id.editTextRipetizioni);
            EditText editTextPeso = (EditText) d.findViewById(R.id.editTextPeso);
            EditText editTextRecupero = (EditText) d.findViewById(R.id.editTextRecupero);
            EditText editTextNote = (EditText) d.findViewById(R.id.editTextNote);
            RadioButton radioKg = (RadioButton) d.findViewById(R.id.radioKg);
            RadioButton radiolb = (RadioButton) d.findViewById(R.id.radiolb);
            TextView textViewUnitaDiMisura = (TextView) d.findViewById(R.id.textViewUnitaDiMisura);
            EditText editTextMassimale = (EditText) d.findViewById(R.id.editTextPesoMassimale);
            EditText editTextPercentuale = (EditText) d.findViewById(R.id.editTextPercentuale);
            EditText editTextRPE = (EditText) d.findViewById(R.id.editTextRPE);

            esercizioDaModificare.setUnitaDiMisura(textViewUnitaDiMisura.getText().toString());

            if (!TextUtils.isEmpty(editTextSerie.getText().toString()))
                esercizioDaModificare.setSerie(Integer.valueOf(editTextSerie.getText().toString()));
            else {
                return getString(R.string.testoInserireSerieRipetizioni);
            }

            String ripetizioniPerUpdate;
            if (!TextUtils.isEmpty(editTextRipetizioni.getText().toString()))
                ripetizioniPerUpdate = editTextRipetizioni.getText().toString();
            else {
                return getString(R.string.testoInserireSerieRipetizioni);
            }

            float pesoPerUpdate = 0;
            if (!TextUtils.isEmpty(editTextPeso.getText().toString())) {
                pesoPerUpdate = Float.valueOf(editTextPeso.getText().toString());
            }

            float recuperoPerUpdate = 0;
            if (!TextUtils.isEmpty(editTextRecupero.getText().toString())) {
                recuperoPerUpdate = Float.valueOf(editTextRecupero.getText().toString());
            }
            esercizioDaModificare.setRecupero(recuperoPerUpdate);

            float massimalePerUpdate = 0;
            if (!TextUtils.isEmpty(editTextMassimale.getText().toString())) {
                massimalePerUpdate = Float.valueOf(editTextMassimale.getText().toString());
            }
            esercizioDaModificare.setMassimale(massimalePerUpdate);

            float percentualePerUpdate = 0;
            if (!TextUtils.isEmpty(editTextPercentuale.getText().toString()))
                percentualePerUpdate = Float.valueOf(editTextPercentuale.getText().toString());

            int rpePerUpdate = 0;
            if (!TextUtils.isEmpty(editTextRPE.getText().toString()))
                rpePerUpdate = Integer.valueOf(editTextRPE.getText().toString());

            esercizioDaModificare.setNote(editTextNote.getText().toString());

            Spinner spinnerGiorni = (Spinner) d.findViewById(R.id.spinnerGiorni);
            esercizioDaModificare.setGiorno(GetSetValoriDiArray.getGiornoBySpinnerPosition(getActivity(), spinnerGiorni));

            Spinner spinnerRoutine = (Spinner) d.findViewById(R.id.spinnerRoutine);
            esercizioDaModificare.setRoutine(GetSetValoriDiArray.getRoutineBySpinnerPosition(getActivity(), spinnerRoutine));

            esercizioDaModificare.setIsPiramidale(0);

            ArrayList<RipetizioniPesoDaDb> ripetizioniPeso = new ArrayList<>();
            RipetizioniPesoDaDb nuovo = new RipetizioniPesoDaDb(0, ripetizioniPerUpdate, pesoPerUpdate, 0, percentualePerUpdate, rpePerUpdate);
            ripetizioniPeso.add(nuovo);

            EserciziController eserciziController = new EserciziController(getActivity());
            int righeModificate = eserciziController.UpdateEsercizio(esercizioDaModificare, ripetizioniPeso);
            if (righeModificate == 0)
                return getString(R.string.testoEsercizioNonModificato);

            return "";
        }
        catch (Exception ex) {
            return ex.getMessage();
        }
    }
}
