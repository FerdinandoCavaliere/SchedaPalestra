package it.ermete.mercurio.schedapalestra;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class DialogConfermaEliminazioneFile extends DialogFragment {

    public interface NoticeDialogEliminazioneFileListener {
        void onDialogEliminazioneFilePositiveClick(String esito);
    }

    DialogConfermaEliminazioneFile.NoticeDialogEliminazioneFileListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mListener = (DialogConfermaEliminazioneFile.NoticeDialogEliminazioneFileListener) activity;
    }

    public static DialogConfermaEliminazioneFile getInstance(String _nomeFileDaEliminare) {

        DialogConfermaEliminazioneFile dialog = new DialogConfermaEliminazioneFile();
        Bundle bundle = new Bundle();
        bundle.putString("_nomeFileDaEliminare", _nomeFileDaEliminare);
        dialog.setArguments(bundle);

        return dialog;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builderConferma = new AlertDialog.Builder(getActivity());
        builderConferma.setTitle(R.string.testoConferma);
        builderConferma.setMessage(R.string.testoConfermaEliminazioneFile);
        builderConferma.setPositiveButton(R.string.testoBottoneOk, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FileController fileController = new FileController();
                String eisto = fileController.EliminaFile(
                        getArguments().getString("_nomeFileDaEliminare"));
                mListener.onDialogEliminazioneFilePositiveClick(eisto);
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
