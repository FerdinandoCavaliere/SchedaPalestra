package it.ermete.mercurio.schedapalestra;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class DialogConfermaEliminazioneEsercizioPersonale extends DialogFragment {

    public interface NoticeDialogEliminazioneEsercizioPersonaleListener {
        void onDialogEliminazioneEsercizioPersonalePositiveClick(DialogFragment dialog, int numeroRigheEliminate);
    }

    NoticeDialogEliminazioneEsercizioPersonaleListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (NoticeDialogEliminazioneEsercizioPersonaleListener) activity;
    }

    public static DialogConfermaEliminazioneEsercizioPersonale getInstance(EsercizioPersonaleDaDb _esercizio) {

        DialogConfermaEliminazioneEsercizioPersonale dialog = new DialogConfermaEliminazioneEsercizioPersonale();
        Bundle bundle = new Bundle();
        bundle.putSerializable("esercizioSelezionato", _esercizio);
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final EsercizioPersonaleDaDb esercizioSelezionato = (EsercizioPersonaleDaDb)getArguments().get("esercizioSelezionato");
        AlertDialog.Builder builderConferma = new AlertDialog.Builder(getActivity());
        builderConferma.setTitle(R.string.testoConferma);
        builderConferma.setMessage(R.string.testoConfermaEliminazioneEsercizio);
        builderConferma.setPositiveButton(R.string.testoBottoneOk, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EserciziPersonaliController eserciziPersonaliController = new EserciziPersonaliController(getActivity());
                int numeroRigheEliminate = eserciziPersonaliController.DeleteEsercizioPersonale(esercizioSelezionato.getId());
                mListener.onDialogEliminazioneEsercizioPersonalePositiveClick(DialogConfermaEliminazioneEsercizioPersonale.this, numeroRigheEliminate);
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
