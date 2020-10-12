package it.ermete.mercurio.schedapalestra;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.HashSet;

public class DialogFiltroGruppiMuscolari extends DialogFragment {

    public interface NoticeDialogFiltroGruppiMuscolari {
        void onDialogFiltroGruppiMuscolariPositiveButton(DialogFragment dialog, HashSet<String> gruppiSelezionati);
    }

    private ArrayList<String> nomiGruppi;
    private HashSet<String> gruppiSelezionati;

    NoticeDialogFiltroGruppiMuscolari mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (NoticeDialogFiltroGruppiMuscolari) activity;
    }

    public static DialogFiltroGruppiMuscolari getInstance(ArrayList<String> _nomigruppi) {
        DialogFiltroGruppiMuscolari dialog = new DialogFiltroGruppiMuscolari();
        Bundle bundle = new Bundle();
        bundle.putSerializable("nomigruppi", _nomigruppi);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        nomiGruppi = (ArrayList<String>) getArguments().get("nomigruppi");
        CharSequence[] gruppiDaSelezionare = new CharSequence[nomiGruppi.size()];
        int contatore = 0;
        for (String singoloNomeGruppo : nomiGruppi) {
            gruppiDaSelezionare[contatore] = singoloNomeGruppo;
            contatore++;
        }
        builder.setTitle(R.string.titoloDialogFiltroGruppiMuscolari);
        builder.setMultiChoiceItems(gruppiDaSelezionare, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    if (gruppiSelezionati == null)
                        gruppiSelezionati = new HashSet<>();
                    gruppiSelezionati.add(nomiGruppi.get(which));
                }
                else {
                    gruppiSelezionati.remove(nomiGruppi.get(which));
                }
            }
        });
        builder.setPositiveButton(R.string.testoBottoneOk, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onDialogFiltroGruppiMuscolariPositiveButton(DialogFiltroGruppiMuscolari.this, gruppiSelezionati);
            }
        });
        builder.setNegativeButton(R.string.testoBottoneAnnulla, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
}
