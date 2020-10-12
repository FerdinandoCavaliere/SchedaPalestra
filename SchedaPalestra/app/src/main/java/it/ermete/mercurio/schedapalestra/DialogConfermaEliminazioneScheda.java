package it.ermete.mercurio.schedapalestra;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class DialogConfermaEliminazioneScheda extends DialogFragment {

    public interface NoticeDialogEliminazioneSchedaListener {
        void onDialogEliminazioneSchedaPositiveClick(DialogFragment dialog, int numeroRigheEliminate);
    }

    NoticeDialogEliminazioneSchedaListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mListener = (NoticeDialogEliminazioneSchedaListener) activity;
    }

    public static DialogConfermaEliminazioneScheda getInstance(int _idschedaDaEliminare) {

        DialogConfermaEliminazioneScheda dialog = new DialogConfermaEliminazioneScheda();
        Bundle bundle = new Bundle();
        bundle.putInt("_idschedaDaEliminare", _idschedaDaEliminare);
        dialog.setArguments(bundle);

        return dialog;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builderConferma = new AlertDialog.Builder(getActivity());
        builderConferma.setTitle(R.string.testoConferma);
        builderConferma.setMessage(R.string.testoConfermaEliminazioneSchedaDalloStorico);
        builderConferma.setPositiveButton(R.string.testoBottoneOk, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SchedeController schedeController = new SchedeController(getActivity());
                int numeroRigheEliminate = schedeController.DeleteScheda(getArguments().getInt("_idschedaDaEliminare"));
                mListener.onDialogEliminazioneSchedaPositiveClick(DialogConfermaEliminazioneScheda.this, numeroRigheEliminate);
            }
        });
        builderConferma.setNegativeButton(R.string.testoBottoneAnnulla, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builderConferma.create();
    }
}
