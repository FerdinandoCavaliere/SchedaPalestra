package it.ermete.mercurio.schedapalestra;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

public class DialogAiutoGestioneSuperSet extends DialogFragment {

    public static DialogAiutoGestioneSuperSet getInstance() {

        return new DialogAiutoGestioneSuperSet();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builderAiuto = new AlertDialog.Builder(getActivity());
        LayoutInflater inflaterAiuto = getActivity().getLayoutInflater();
        View aiuto = inflaterAiuto.inflate(R.layout.dialog_guida_gestione_super_set, null);
        builderAiuto.setView(aiuto);
        builderAiuto.setPositiveButton(R.string.testoBottoneChiudi, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Nessuna azione
            }
        });

        return builderAiuto.create();
    }
}
