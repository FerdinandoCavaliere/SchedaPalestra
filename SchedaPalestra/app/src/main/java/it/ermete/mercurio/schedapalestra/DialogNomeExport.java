package it.ermete.mercurio.schedapalestra;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DialogNomeExport extends DialogFragment {

    public interface NoticeDialogNomeExportListener {
        void onDialogNomeExportPositiveClick(DialogFragment dialog, String nomeExport);
    }

    DialogNomeExport.NoticeDialogNomeExportListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (DialogNomeExport.NoticeDialogNomeExportListener) activity;
    }

    @Override
    public void onStart() {
        super.onStart();

        final AlertDialog d = (AlertDialog)getDialog();
        if(d != null)
        {
            d.getButton(Dialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText editTextNomeExport = (EditText) d.findViewById(R.id.editTextNomeExport);
                    if (TextUtils.isEmpty(editTextNomeExport.getText().toString())) {
                        Toast.makeText(getActivity(),
                                R.string.testoNessunNomeExport,
                                Toast.LENGTH_SHORT).show();
                    }
                    else {
                        mListener.onDialogNomeExportPositiveClick(DialogNomeExport.this, editTextNomeExport.getText().toString());
                        d.dismiss();
                    }
                }
            });
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builderNomeExport = new AlertDialog.Builder(getActivity());
        LayoutInflater inflaterNomeExport = getActivity().getLayoutInflater();
        View dialogNomeExport = inflaterNomeExport.inflate(R.layout.dialog_nome_export, null);
        final EditText editTextNomeExport = (EditText) dialogNomeExport.findViewById(R.id.editTextNomeExport);
        dialogNomeExport.findViewById(R.id.editTextNomeExport).requestFocus();
        builderNomeExport.setView(dialogNomeExport);

        builderNomeExport.setPositiveButton(R.string.testoBottoneSalva, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builderNomeExport.setNegativeButton(R.string.testoBottoneAnnulla, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Nessuna azione
            }
        });

        return builderNomeExport.create();
    }
}
