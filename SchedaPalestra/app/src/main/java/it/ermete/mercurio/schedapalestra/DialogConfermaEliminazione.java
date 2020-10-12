package it.ermete.mercurio.schedapalestra;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class DialogConfermaEliminazione extends DialogFragment {

    public interface NoticeDialogEliminazioneEsercizioListener {
        void onDialogEliminazioneEsercizioPositiveClick(DialogFragment dialog, int numeroRigheEliminate);
    }

    NoticeDialogEliminazioneEsercizioListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (NoticeDialogEliminazioneEsercizioListener) activity;
    }

    public static DialogConfermaEliminazione getInstance(EsercizioDaDb _esercizio) {

        DialogConfermaEliminazione dialog = new DialogConfermaEliminazione();
        Bundle bundle = new Bundle();
        bundle.putSerializable("esercizioSelezionato", _esercizio);
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final EsercizioDaDb esercizioSelezionato = (EsercizioDaDb)getArguments().get("esercizioSelezionato");
        AlertDialog.Builder builderConferma = new AlertDialog.Builder(getActivity());
        builderConferma.setTitle(R.string.testoConferma);
        builderConferma.setMessage(R.string.testoConfermaEliminazioneEsercizio);
        builderConferma.setPositiveButton(R.string.testoBottoneOk, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EserciziController eserciziController = new EserciziController(getActivity());
                int numeroRigheEliminate = eserciziController.DeleteEsercizio(esercizioSelezionato.getId());
                mListener.onDialogEliminazioneEsercizioPositiveClick(DialogConfermaEliminazione.this, numeroRigheEliminate);
            }
        });
        builderConferma.setNegativeButton(R.string.testoBottoneAnnulla, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Nessuna azione
            }
        });

        return builderConferma.create();
    }
}
