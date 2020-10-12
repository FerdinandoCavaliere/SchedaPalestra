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

public class DialogModificaNotaScheda extends DialogFragment {

    int idUltimaScheda;

    public interface NoticeDialoModificaNotaSchedaListener {
        void onDialogModificaNotaSchedaPositiveClick(DialogFragment dialog, long esito);
    }

    NoticeDialoModificaNotaSchedaListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (NoticeDialoModificaNotaSchedaListener) activity;
    }

    public static DialogModificaNotaScheda getInstance(int idUltimaScheda) {

        DialogModificaNotaScheda dialog = new DialogModificaNotaScheda();
        Bundle bundle = new Bundle();
        bundle.putInt("idUltimaScheda", idUltimaScheda);
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        idUltimaScheda = getArguments().getInt("idUltimaScheda");

        AlertDialog.Builder builderModificaNotaScheda = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogModificaNotaScheda = inflater.inflate(R.layout.dialog_modifica_nota_scheda, null);
        builderModificaNotaScheda.setView(dialogModificaNotaScheda);

        builderModificaNotaScheda.setPositiveButton(R.string.testoBottoneSalva, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                long esito = ModificaNotaScheda(dialog);
                mListener.onDialogModificaNotaSchedaPositiveClick(DialogModificaNotaScheda.this, esito);
            }
        });

        builderModificaNotaScheda.setNegativeButton(R.string.testoBottoneAnnulla, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Nessuna azione
            }
        });

        return builderModificaNotaScheda.create();
    }

    private long ModificaNotaScheda(DialogInterface dialog) {
        try {
            Dialog d = (Dialog)dialog;
            EditText editTextNotaScheda = (EditText) d.findViewById(R.id.editTextNotaScheda);
            SchedeController schedeController = new SchedeController(getActivity());
            return schedeController.UpdateNotaScheda(idUltimaScheda, editTextNotaScheda.getText().toString());
        }
        catch (Exception ex) {
            return -1;
        }
    }
}
