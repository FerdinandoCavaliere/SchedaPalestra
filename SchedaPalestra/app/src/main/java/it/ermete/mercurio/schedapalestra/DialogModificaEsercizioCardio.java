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
import android.widget.Spinner;
import android.widget.TextView;

public class DialogModificaEsercizioCardio extends DialogFragment {

    private String bgColor = "Bianco";

    public interface NoticeDialogModificaEsercizioCardioListener {
        void onDialogModificaEsercizioCardioPositiveClick(DialogFragment dialog, String esito);
    }

    NoticeDialogModificaEsercizioCardioListener mListener;

    EsercizioDaDb esercizioSelezionato;
    int idUltimaScheda;

    public static DialogModificaEsercizioCardio getInstance(EsercizioDaDb esercizioSelezionato,
                                                            int idUltimaScheda) {

        DialogModificaEsercizioCardio dialog = new DialogModificaEsercizioCardio();
        Bundle bundle = new Bundle();
        bundle.putSerializable("esercizioSelezionato", esercizioSelezionato);
        bundle.putInt("idUltimaScheda", idUltimaScheda);
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (NoticeDialogModificaEsercizioCardioListener) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        esercizioSelezionato = (EsercizioDaDb)getArguments().get("esercizioSelezionato");
        idUltimaScheda = getArguments().getInt("idUltimaScheda");

        AlertDialog.Builder builderModificaEsercizioCardio = new AlertDialog.Builder(getActivity());
        LayoutInflater inflaterModificaEsercizi = getActivity().getLayoutInflater();

        final View layoutModificaEsercizioCardio = inflaterModificaEsercizi.inflate(R.layout.dialog_modifica_esercizio_cardio, null);

        // Setto bottoni per settare il colore di sfondo
        Button btnBgBianco = (Button) layoutModificaEsercizioCardio.findViewById(R.id.btnBgBianco);
        btnBgBianco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutModificaEsercizioCardio.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorBianco));
                bgColor = "Bianco";
            }
        });

        Button btnBgViola = (Button) layoutModificaEsercizioCardio.findViewById(R.id.btnBgViola);
        btnBgViola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutModificaEsercizioCardio.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorViola));
                bgColor = "Viola";
            }
        });

        Button btnBgArancio = (Button) layoutModificaEsercizioCardio.findViewById(R.id.btnBgArancio);
        btnBgArancio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutModificaEsercizioCardio.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorArancio));
                bgColor = "Arancio";
            }
        });

        Button btnBgBlu = (Button) layoutModificaEsercizioCardio.findViewById(R.id.btnBgBlu);
        btnBgBlu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutModificaEsercizioCardio.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorBlu));
                bgColor = "Blu";
            }
        });

        Button btnBgAzzurro = (Button) layoutModificaEsercizioCardio.findViewById(R.id.btnBgAzzurro);
        btnBgAzzurro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutModificaEsercizioCardio.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorAzzurro));
                bgColor = "Azzurro";
            }
        });

        Button btnBgVerde = (Button) layoutModificaEsercizioCardio.findViewById(R.id.btnBgVerde);
        btnBgVerde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutModificaEsercizioCardio.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorVerde));
                bgColor = "Verde";
            }
        });

        Button btnBgGiallo = (Button) layoutModificaEsercizioCardio.findViewById(R.id.btnBgGiallo);
        btnBgGiallo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutModificaEsercizioCardio.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorGiallo));
                bgColor = "Giallo";
            }
        });

        Button btnBgRosso = (Button) layoutModificaEsercizioCardio.findViewById(R.id.btnBgRosso);
        btnBgRosso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutModificaEsercizioCardio.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorRosso));
                bgColor = "Rosso";
            }
        });

        Button btnBgRosa = (Button) layoutModificaEsercizioCardio.findViewById(R.id.btnBgRosa);
        btnBgRosa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutModificaEsercizioCardio.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorRosa));
                bgColor = "Rosa";
            }
        });

        Button btnBgVerdeAcqua = (Button) layoutModificaEsercizioCardio.findViewById(R.id.btnBgVerdeAcqua);
        btnBgVerdeAcqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayoutContenitore = (LinearLayout) layoutModificaEsercizioCardio.findViewById(R.id.linearLayoutContenitore);
                linearLayoutContenitore.setBackgroundColor(getResources().getColor(R.color.esercizioBgColorVerdeAcqua));
                bgColor = "VerdeAcqua";
            }
        });

        RiempiEsercizioPerModifica(layoutModificaEsercizioCardio, esercizioSelezionato);

        builderModificaEsercizioCardio.setView(layoutModificaEsercizioCardio);
        builderModificaEsercizioCardio.setPositiveButton(R.string.testoBottoneSalva, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String esito = ModificaEsercizio(getDialog(), esercizioSelezionato);
                mListener.onDialogModificaEsercizioCardioPositiveClick(DialogModificaEsercizioCardio.this, esito);
            }
        });
        builderModificaEsercizioCardio.setNegativeButton(R.string.testoBottoneAnnulla, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Nessuna azione
            }
        });

        return builderModificaEsercizioCardio.create();
    }

    private void RiempiEsercizioPerModifica(View dialog, EsercizioDaDb esercizioDaModificare) {
        try {
            bgColor = esercizioDaModificare.getBgColor();
            // Ottengo i riferimenti ai campi del dialog
            LinearLayout linearLayoutContenitore = (LinearLayout) dialog.findViewById(R.id.linearLayoutContenitore) ;
            TextView textViewNomeEsercizio = (TextView) dialog.findViewById(R.id.textViewNomeEsercizio);
            EditText editTextProgramma = (EditText) dialog.findViewById(R.id.editTextProgramma);
            EditText editTextIntensita = (EditText) dialog.findViewById(R.id.editTextIntensita);
            EditText editTextPendenza = (EditText) dialog.findViewById(R.id.editTextPendenza);
            EditText editTextVelocita = (EditText) dialog.findViewById(R.id.editTextVelocita);
            EditText editTextTempo = (EditText) dialog.findViewById(R.id.editTextTempo);
            EditText editTextNote = (EditText) dialog.findViewById(R.id.editTextNote);

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
            textViewNomeEsercizio.setText(esercizioDaModificare.getNomeEsercizio());
            editTextNote.setText(esercizioDaModificare.getNote());
            spinnerGiorni.setSelection(GetSetValoriDiArray.getPosizioneGiornoInArray(getActivity(), esercizioDaModificare.getGiorno()));
            spinnerRoutine.setSelection(GetSetValoriDiArray.getPosizioneRoutineInArray(getActivity(), esercizioDaModificare.getRoutine()));

            DatiEsercizioCardioDaDb datiEsercizioCardioDaDb = esercizioDaModificare.getDatiEsercizioCardio();
            editTextProgramma.setText(datiEsercizioCardioDaDb.getProgramma());
            editTextIntensita.setText(datiEsercizioCardioDaDb.getIntensita());
            editTextPendenza.setText(datiEsercizioCardioDaDb.getPendenza());
            editTextVelocita.setText(datiEsercizioCardioDaDb.getVelocita() > 0 ? Utility.FormatFloat(datiEsercizioCardioDaDb.getVelocita()).replace(",", ".") : "");
            editTextTempo.setText(datiEsercizioCardioDaDb.getTempo() > 0 ? Utility.FormatFloat(datiEsercizioCardioDaDb.getTempo()).replace(",", ".") : "");
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
            EditText editTextProgramma = (EditText) d.findViewById(R.id.editTextProgramma);
            EditText editTextIntensita = (EditText) d.findViewById(R.id.editTextIntensita);
            EditText editTextPendenza = (EditText) d.findViewById(R.id.editTextPendenza);
            EditText editTextVelocita = (EditText) d.findViewById(R.id.editTextVelocita);
            EditText editTextTempo = (EditText) d.findViewById(R.id.editTextTempo);
            Spinner spinnerGiorni = (Spinner) d.findViewById(R.id.spinnerGiorni);
            Spinner spinnerRoutine = (Spinner) d.findViewById(R.id.spinnerRoutine);
            EditText editTextNote = (EditText) d.findViewById(R.id.editTextNote);

            String programmaPerUpdate = editTextProgramma.getText().toString();
            String intensitaPerUpdate = editTextIntensita.getText().toString();
            String pendenzaPerUpdate = editTextPendenza.getText().toString();

            float velocitaUpdate = 0;
            if (!TextUtils.isEmpty(editTextVelocita.getText().toString()))
                velocitaUpdate = Float.valueOf(editTextVelocita.getText().toString());

            float tempoPerUpdate = 0;
            if (!TextUtils.isEmpty(editTextTempo.getText().toString()))
                tempoPerUpdate = Float.valueOf(editTextTempo.getText().toString());

            esercizioDaModificare.setNote(editTextNote.getText().toString());
            esercizioDaModificare.setGiorno(GetSetValoriDiArray.getGiornoBySpinnerPosition(getActivity(), spinnerGiorni));
            esercizioDaModificare.setRoutine(GetSetValoriDiArray.getRoutineBySpinnerPosition(getActivity(), spinnerRoutine));

            esercizioDaModificare.setIsPiramidale(0);

            DatiEsercizioCardioDaDb datiEsercizioCardioDaDb = new DatiEsercizioCardioDaDb(0,
                    programmaPerUpdate,
                    intensitaPerUpdate,
                    pendenzaPerUpdate,
                    velocitaUpdate,
                    tempoPerUpdate);

            EserciziController eserciziController = new EserciziController(getActivity());
            int righeModificate = eserciziController.UpdateEsercizio(esercizioDaModificare, datiEsercizioCardioDaDb);
            if (righeModificate == 0)
                return getString(R.string.testoEsercizioNonInserito);

            return "";
        }
        catch (Exception ex) {
            return ex.getMessage();
        }
    }
}
