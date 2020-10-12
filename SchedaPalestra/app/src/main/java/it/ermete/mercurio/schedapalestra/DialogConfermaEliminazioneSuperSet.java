package it.ermete.mercurio.schedapalestra;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class DialogConfermaEliminazioneSuperSet extends DialogFragment {

    public interface NoticeDialogEliminazioneSupersetListener {
        void onDialogEliminazioneSupersetPositiveClick(DialogFragment dialog, int numeroRigheEliminate);
    }

    NoticeDialogEliminazioneSupersetListener mListener;

    public static DialogConfermaEliminazioneSuperSet getInstance(EsercizioDaDb _esercizio) {

        DialogConfermaEliminazioneSuperSet dialog = new DialogConfermaEliminazioneSuperSet();
        Bundle bundle = new Bundle();
        bundle.putSerializable("esercizioSelezionato", _esercizio);
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (NoticeDialogEliminazioneSupersetListener) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final EsercizioDaDb esercizioSelezionato = (EsercizioDaDb) getArguments().get("esercizioSelezionato");
        AlertDialog.Builder builderConferma = new AlertDialog.Builder(getActivity());
        builderConferma.setTitle(R.string.testoConferma);
        builderConferma.setMessage(R.string.testoConfermaEliminazioneSuperSet);
        builderConferma.setPositiveButton(R.string.testoBottoneOk, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EserciziController eserciziController = new EserciziController(getActivity());
                int numeroRigheEliminate = eserciziController.DeleteSuperset(esercizioSelezionato.getId());
                mListener.onDialogEliminazioneSupersetPositiveClick(DialogConfermaEliminazioneSuperSet.this, numeroRigheEliminate);
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
