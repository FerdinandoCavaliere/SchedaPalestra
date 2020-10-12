package it.ermete.mercurio.schedapalestra;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class FileModel {

    public ArrayList<FileDaFS> GetElencoExportsPdf() {

        ArrayList<FileDaFS> elenco = new ArrayList<>();

        try {
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            File dir = new File(path);
            File[] elencoFiles = dir.listFiles();
            for (File singolo : elencoFiles) {
                if (singolo.isFile()) {
                    if (singolo.getName().contains("SP_El_") ||
                            singolo.getName().contains("SP_Gr_") ||
                            singolo.getName().contains("SP_Ro_") ||
                            singolo.getName().contains("SP_Gi_")) {
                        FileDaFS nuovo = new FileDaFS();
                        Date dataCreazione = new Date(singolo.lastModified());
                        nuovo.setNome(singolo.getName());
                        nuovo.setDataCreazione(Utility.GetStringDaData(dataCreazione));
                        elenco.add(nuovo);
                    }
                }
            }

        }
        catch (Exception ex){
            Log.d("Errore Image", ex.getMessage());
        }
        return elenco;
    }

    public String EliminaFile(String nomeFile) {
        String esito = "";
        try {
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            File fileDaEliminare = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    + "/" + nomeFile);
            fileDaEliminare.delete();
        }
        catch (Exception ex) {
            esito = ex.getMessage();
        }
        return esito;
    }
}
