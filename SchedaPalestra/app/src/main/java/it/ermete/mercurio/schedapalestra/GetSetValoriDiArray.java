package it.ermete.mercurio.schedapalestra;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Spinner;


public class GetSetValoriDiArray {

    public static String getGiornoBySpinnerPosition(Context activity, Spinner spinner) {
        try {
            String[] giorni = activity.getResources().getStringArray(R.array.Giorni);
            boolean trovato = false;
            int contatorePosizione = -1; // Scandisce l'array dei giorni
            if (spinner.getSelectedItemPosition() > 0) {
                return giorni[spinner.getSelectedItemPosition()];
            }
            else {
                return "";
            }
        }
        catch (Exception ex) {
            return "";
        }
    }

    public static String getRoutineBySpinnerPosition(Context activity, Spinner spinner) {
        try {
            String[] routines = activity.getResources().getStringArray(R.array.Routine);
            boolean trovato = false;
            int contatorePosizione = -1; // Scandisce l'array delle routines
            if (spinner.getSelectedItemPosition() > 0) {
                return routines[spinner.getSelectedItemPosition()];
            }
            else {
                return "";
            }
        }
        catch (Exception ex) {
            return "";
        }
    }

    public static int getPosizioneGiornoInArray(Context activity, String giorno) {
        try {
            int posizione = 0;
            if (TextUtils.isEmpty(giorno))
                return 0;
            else {
                int contatore = -1;
                boolean trovato = false;
                String[] giorni = activity.getResources().getStringArray(R.array.Giorni);
                while (!trovato) {
                    contatore++;
                    if (giorni[contatore].equals(giorno)) {
                        posizione = contatore;
                        trovato = true;
                    }
                }
            }
            return posizione;
        }
        catch (Exception ex) {
            return 0;
        }
    }

    public static int getPosizioneRoutineInArray(Context activity, String routine) {
        try {
            int posizione = 0;
            if (TextUtils.isEmpty(routine))
                return 0;
            else {
                int contatore = -1;
                boolean trovato = false;
                String[] routines = activity.getResources().getStringArray(R.array.Routine);
                while (!trovato) {
                    contatore++;
                    if (routines[contatore].equals(routine)) {
                        posizione = contatore;
                        trovato = true;
                    }
                }
            }
            return posizione;
        }
        catch (Exception ex) {
            return 0;
        }
    }
}
