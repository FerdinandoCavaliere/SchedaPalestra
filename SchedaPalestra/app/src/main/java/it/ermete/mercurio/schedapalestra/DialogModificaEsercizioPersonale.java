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
import android.widget.EditText;
import android.widget.TextView;

public class DialogModificaEsercizioPersonale extends DialogFragment {

    public interface NoticeDialogModificaEsercizioPersonaleListener {
        void onDialogModificaEsercizioPersonalePositiveClick(DialogFragment dialog, String esito);
    }

    NoticeDialogModificaEsercizioPersonaleListener mListener;

    EsercizioPersonaleDaDb esercizio;

    public static DialogModificaEsercizioPersonale getInstance(EsercizioPersonaleDaDb _esercizio) {
        DialogModificaEsercizioPersonale dialog = new DialogModificaEsercizioPersonale();
        Bundle bundle = new Bundle();
        bundle.putSerializable("esercizio", _esercizio);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mListener = (NoticeDialogModificaEsercizioPersonaleListener)activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        esercizio = (EsercizioPersonaleDaDb) getArguments().getSerializable("esercizio");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_modifica_esercizio_personale, null);
        this.RiempiEsercizioPerModifica(view, esercizio);
        builder.setView(view);
        builder.setPositiveButton(R.string.testoBottoneSalva, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String esito = ModificaEsercizio(dialog, esercizio);
                mListener.onDialogModificaEsercizioPersonalePositiveClick(DialogModificaEsercizioPersonale.this, esito);
            }
        });
        builder.setNegativeButton(R.string.testoBottoneAnnulla, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }

    private void RiempiEsercizioPerModifica(View dialog, EsercizioPersonaleDaDb esercizio) {
        try {
            TextView textViewNomeGruppoMuscolare = (TextView) dialog.findViewById(R.id.textViewNomeGruppoMuscolare);
            textViewNomeGruppoMuscolare.setText(esercizio.getNomeGruppoMuscolare());
            EditText editTextNomeEsercizio = (EditText) dialog.findViewById(R.id.editTextNomeEsercizio);
            editTextNomeEsercizio.setText(esercizio.getNome());
        }
        catch (Exception ex) {
            Log.d("RiempiEsercizio", ex.getMessage());
        }
    }

    private String ModificaEsercizio(DialogInterface dialog, EsercizioPersonaleDaDb esercizio) {
        try {
            Dialog d = (Dialog)dialog;
            EditText editTextNomeEsercizio = (EditText) d.findViewById(R.id.editTextNomeEsercizio);
            if (TextUtils.isEmpty(editTextNomeEsercizio.getText())) {
                return "Inserire un esercizio";
            }
            else {
                esercizio.setNome(editTextNomeEsercizio.getText().toString());
                EserciziPersonaliController eserciziPersonaliController = new EserciziPersonaliController(getActivity());
                long righeModificate = eserciziPersonaliController.UpdateEsercizioPersonale(esercizio);
                if (righeModificate == 0)
                    return getString(R.string.testoEsercizioNonInserito);

                return "";
            }
        }
        catch (Exception ex) {
            return ex.getMessage();
        }
    }

}
