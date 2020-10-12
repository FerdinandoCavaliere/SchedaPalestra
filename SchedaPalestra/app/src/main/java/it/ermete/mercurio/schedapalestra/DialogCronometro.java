package it.ermete.mercurio.schedapalestra;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Chronometer;

public class DialogCronometro extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View viewCronometro = inflater.inflate(R.layout.dialog_cronometro, null);
        final Chronometer cronometro = (Chronometer) viewCronometro.findViewById(R.id.chronometerRecupero);
        builder.setPositiveButton(R.string.testoBottoneRiavvia, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton(R.string.testoBottoneChiudi, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cronometro.stop();
            }
        });
        builder.setView(viewCronometro);
        cronometro.start();

        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();

        final AlertDialog d = (AlertDialog)getDialog();
        if (d != null) {
            d.getButton(Dialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Chronometer cronometro = (Chronometer) d.findViewById(R.id.chronometerRecupero);
                    cronometro.setBase(SystemClock.elapsedRealtime());
                }
            });
        }
    }
}
