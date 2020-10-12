package it.ermete.mercurio.schedapalestra;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class DialogNuovaScheda extends DialogFragment {

    public interface NoticeDialogNuovaSchedaListener {
        void onDialogNuovaSchedaPositiveClick(DialogFragment dialog, long nuovoId);
    }

    NoticeDialogNuovaSchedaListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (NoticeDialogNuovaSchedaListener) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builderInserimentoScheda = new AlertDialog.Builder(getActivity());
        LayoutInflater inflaterInserimentoScheda = getActivity().getLayoutInflater();
        View dialogInserimentoScheda = inflaterInserimentoScheda.inflate(R.layout.dialog_inserimento_scheda, null);
        dialogInserimentoScheda.findViewById(R.id.editTextNuovaScheda).requestFocus();
        builderInserimentoScheda.setView(dialogInserimentoScheda);

        builderInserimentoScheda.setPositiveButton(R.string.testoBottoneSalva, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                long nuovoId = CreaNuovaScheda(dialog);
                mListener.onDialogNuovaSchedaPositiveClick(DialogNuovaScheda.this, nuovoId);
            }
        });

        builderInserimentoScheda.setNegativeButton(R.string.testoBottoneAnnulla, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Nessuna azione
            }
        });

        return builderInserimentoScheda.create();
    }

    private long CreaNuovaScheda(DialogInterface dialog) {
        try {
            Dialog d = (Dialog)dialog;
            EditText editTextNuovaScheda = (EditText) d.findViewById(R.id.editTextNuovaScheda);
            SchedeController schedeController = new SchedeController(getActivity());
            return schedeController.InsertScheda(editTextNuovaScheda.getText().toString());
        }
        catch (Exception ex) {
            return -1;
        }
    }
}
