package it.ermete.mercurio.schedapalestra;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utility {

    static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
    static final DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    public static String GetDataAttualePerDb() {
        return formatter.format(new Date());
    }

    public static Date GetOggettoDataDaStringa(String data) throws ParseException{
        return formatter.parse(data);
    }

    public static String GetStringDaData(Date data) {
        return formatter.format(data);
    }

    public static String FormatFloat(float valore) {
        return decimalFormat.format(valore);
    }

    public static int getBgColor(String coloreDb) {

        int bgColorTornato = R.drawable.border_esercizio_list_item;

        switch (coloreDb)
        {
            case "Bianco":
                bgColorTornato = R.drawable.border_esercizio_list_item;
                break;
            case "Viola":
                bgColorTornato = R.drawable.border_esercizio_list_item_viola;
                break;
            case "Arancio":
                bgColorTornato = R.drawable.border_esercizio_list_item_arancio;
                break;
            case "Blu":
                bgColorTornato = R.drawable.border_esercizio_list_item_blu;
                break;
            case "Azzurro":
                bgColorTornato = R.drawable.border_esercizio_list_item_azzurro;
                break;
            case "Verde":
                bgColorTornato = R.drawable.border_esercizio_list_item_verde;
                break;
            case "Giallo":
                bgColorTornato = R.drawable.border_esercizio_list_item_giallo;
                break;
            case "Rosso":
                bgColorTornato = R.drawable.border_esercizio_list_item_rosso;
                break;
            case "Rosa":
                bgColorTornato = R.drawable.border_esercizio_list_item_rosa;
                break;
            case "VerdeAcqua":
                bgColorTornato = R.drawable.border_esercizio_list_item_verdeacqua;
                break;
            default:
                bgColorTornato = R.drawable.border_esercizio_list_item;
                break;
        }

        return bgColorTornato;
    }
}
