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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class DialogInserimentoEsercizioPersonale extends DialogFragment {

    public interface NoticeDialogInserimentoEsercizioPersonaleListener {
        void onDialogInserimentoEsercizioNegativeClick(DialogFragment dialog);
    }

    NoticeDialogInserimentoEsercizioPersonaleListener mListener;

    private ArrayList<String> nomiGruppi;
    private ArrayList<GruppoMuscolareDaXml> gruppi;

    public static DialogInserimentoEsercizioPersonale getInstance(ArrayList<String> nomiGruppi,
                                                                  ArrayList<GruppoMuscolareDaXml> gruppi) {
        DialogInserimentoEsercizioPersonale dialog = new DialogInserimentoEsercizioPersonale();
        Bundle bundle = new Bundle();
        bundle.putSerializable("nomiGruppi", nomiGruppi);
        bundle.putSerializable("gruppi", gruppi);
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (NoticeDialogInserimentoEsercizioPersonaleListener) activity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        nomiGruppi = (ArrayList<String>) getArguments().get("nomiGruppi");
        gruppi = (ArrayList<GruppoMuscolareDaXml>) getArguments().get("gruppi");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_inserimento_esercizio_personale, null);

        Spinner spinnerGruppiMuscolari = (Spinner) view.findViewById(R.id.spinnerGruppiMuscolari);
        ArrayAdapter<String> adapterGruppi = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                this.nomiGruppi);
        spinnerGruppiMuscolari.setAdapter(adapterGruppi);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setPositiveButton(R.string.testoBottoneSalva, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton(R.string.testoBottoneChiudi, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onDialogInserimentoEsercizioNegativeClick(DialogInserimentoEsercizioPersonale.this);
            }
        });

        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();

        AlertDialog d = (AlertDialog) getDialog();
        if (d != null) {
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
            Dialog d = (Dialog) dialog;
            EditText editTextNomeEsercizio = (EditText) d.findViewById(R.id.editTextNomeEsercizio);
            if (TextUtils.isEmpty(editTextNomeEsercizio.getText())) {
                return "Inserire un esercizio";
            }
            else {
                Spinner spinnerGruppiMuscolari = (Spinner) d.findViewById(R.id.spinnerGruppiMuscolari);
                GruppoMuscolareDaXml gruppoSelezionato = getGruppoBySpinnerPosition(spinnerGruppiMuscolari);
                int idGruppoSelezionatoPerInsert = gruppoSelezionato.getId();

                EsercizioPersonaleDaDb esercizioDaInserire = new EsercizioPersonaleDaDb(0,
                        editTextNomeEsercizio.getText().toString(),
                        idGruppoSelezionatoPerInsert,
                        "");

                EserciziPersonaliController eserciziPersonaliController = new EserciziPersonaliController(getActivity());
                long nuovoId = eserciziPersonaliController.InsertEsercizioPersonale(esercizioDaInserire);
                if (nuovoId < 0) {
                    return getString(R.string.testoEsercizioNonInserito);
                }
                return "";
            }
        }
        catch (Exception ex) {
            return ex.getMessage();
        }
    }

    private void pulisciCampiDialogInserimentoEsercizio(DialogInterface dialog) {
        Dialog d = (Dialog)dialog;
        Spinner spinnerGruppiMuscolari = (Spinner) d.findViewById(R.id.spinnerGruppiMuscolari);
        spinnerGruppiMuscolari.setSelection(0);
        EditText editTextNomeEsercizio = (EditText) d.findViewById(R.id.editTextNomeEsercizio);
        editTextNomeEsercizio.setText("");
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
}
