package it.ermete.mercurio.schedapalestra;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class ExportPdfPerGruppiAsyncTask extends AsyncTask<Integer, Integer, Integer> {

    private final MainActivity activity;
    private final int idSchedaDaEsportare;
    private final String nomeFile;

    private ProgressDialog progress;
    private String esercizio;

    SchedeController schedeController;
    EserciziController eserciziController;
    String notaScheda;
    String dataScheda;
    ArrayList<EsercizioDaDb> eserciziDaEsportare;

    final HashMap<String, Integer> mappaGruppi;

    private String percorsoSalvataggio;

    private String errore = "";

    public ExportPdfPerGruppiAsyncTask(MainActivity _activity,
                                       int _idSchedaDaEsportare,
                                       ArrayList<GruppoMuscolareDaXml> _gruppiMuscolari,
                                       String _nomeFile) {
        super();
        this.activity = _activity;
        this.idSchedaDaEsportare = _idSchedaDaEsportare;
        this.nomeFile = _nomeFile;

        mappaGruppi = new HashMap<>();
        for (GruppoMuscolareDaXml singolo : _gruppiMuscolari) {
            mappaGruppi.put(singolo.getGruppo(), singolo.getId());
        }
    }

    @Override
    protected Integer doInBackground(Integer... params) {
        if (errore.equals("")) {
            try {
                String colonna = "sinistra"; // Indica se il gruppo di esercizi va scritto nella colonna destra o sinistra della tabella di layout

                File file = new File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        "SP_Gr_" + nomeFile + ".pdf"
                );

                percorsoSalvataggio = file.getCanonicalPath();

                OutputStream fos = new FileOutputStream(file, false);

                Document document = new Document(PageSize.A4);
                PdfWriter writer = PdfWriter.getInstance(document, fos);

                document.open();

                // Intestazione
                document.add(UtilityPdf.creaIntestazione(this.activity, dataScheda, notaScheda));

                // Esercizi
                PdfPTable tbEsercizi = new PdfPTable(2); // Tabella esterna che serve solo per impaginazione
                PdfPCell cellaSinistra = new PdfPCell();
                cellaSinistra.setBorder(PdfPCell.NO_BORDER);
                PdfPCell cellaDestra = new PdfPCell();
                cellaDestra.setBorder(PdfPCell.NO_BORDER);

                if (colonna.equals("sinistra")) {
                    if (creaTabellaGruppoMuscolare(cellaSinistra, "Pettorali")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                else {
                    if (creaTabellaGruppoMuscolare(cellaDestra, "Pettorali")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                publishProgress(1);

                if (colonna.equals("sinistra")) {
                    if (creaTabellaGruppoMuscolare(cellaSinistra, "Dorsali")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                else {
                    if (creaTabellaGruppoMuscolare(cellaDestra, "Dorsali")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                publishProgress(1);

                if (colonna.equals("sinistra")) {
                    if (creaTabellaGruppoMuscolare(cellaSinistra, "Spalle")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                else {
                    if (creaTabellaGruppoMuscolare(cellaDestra, "Spalle")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                publishProgress(1);

                if (colonna.equals("sinistra")) {
                    if (creaTabellaGruppoMuscolare(cellaSinistra, "Gambe")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                else {
                    if (creaTabellaGruppoMuscolare(cellaDestra, "Gambe")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                publishProgress(1);

                if (colonna.equals("sinistra")) {
                    if (creaTabellaGruppoMuscolare(cellaSinistra, "Bicipiti")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                else {
                    if (creaTabellaGruppoMuscolare(cellaDestra, "Bicipiti")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                publishProgress(1);

                if (colonna.equals("sinistra")) {
                    if (creaTabellaGruppoMuscolare(cellaSinistra, "Tricipiti")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                else {
                    if (creaTabellaGruppoMuscolare(cellaDestra, "Tricipiti")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                publishProgress(1);

                if (colonna.equals("sinistra")) {
                    if (creaTabellaGruppoMuscolare(cellaSinistra, "Avambracci")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                else {
                    if (creaTabellaGruppoMuscolare(cellaDestra, "Avambracci")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                publishProgress(1);

                if (colonna.equals("sinistra")) {
                    if (creaTabellaGruppoMuscolare(cellaSinistra, "Polpacci")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                else {
                    if (creaTabellaGruppoMuscolare(cellaDestra, "Polpacci")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                publishProgress(1);

                if (colonna.equals("sinistra")) {
                    if (creaTabellaGruppoMuscolare(cellaSinistra, "Addominali")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                else {
                    if (creaTabellaGruppoMuscolare(cellaDestra, "Addominali")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                publishProgress(1);

                if (colonna.equals("sinistra")) {
                    if (creaTabellaGruppoMuscolare(cellaSinistra, "Cardio")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                else {
                    if (creaTabellaGruppoMuscolare(cellaDestra, "Cardio")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                publishProgress(1);

                tbEsercizi.addCell(cellaSinistra);
                tbEsercizi.addCell(cellaDestra);

                document.add(tbEsercizi);

                document.close();
                fos.close();
            }
            catch (FileNotFoundException f_ex)
            {
                //Log.d("ExportPdf_fofException", f_ex.getMessage());
                errore = f_ex.getMessage();
            }
            catch (Exception ex)
            {
                //Log.d("ExportPdf", ex.getMessage());
                errore = ex.getMessage();
            }
        }

        return null;
    }

    @NonNull
    private String CambiaColonna(String colonna) {
        if (colonna.equals("sinistra")) {
            return "destra";
        }
        else {
            return  "sinistra";
        }
    }

    @Override
    protected void onPreExecute() {
        try {
            // Reperisco gli esercizi della scheda da esportare
            schedeController = new SchedeController(activity);
            notaScheda = schedeController.LeggiNotaSchedaAttiva(idSchedaDaEsportare);
            dataScheda = schedeController.LeggiDataInserimentoSchedaAttiva(idSchedaDaEsportare);
            eserciziController = new EserciziController(activity);
            eserciziDaEsportare = eserciziController.LeggiEserciziByIdScheda(idSchedaDaEsportare);

            progress = new ProgressDialog(activity);

            // Setto la dimensione massima del progress
            progress.setMax(mappaGruppi.size());

            progress.setProgress(0);
            progress.setTitle(activity.getResources().getString(R.string.testoTitolProgressExport));
            progress.setMessage(activity.getResources().getString(R.string.testoProgressExport));
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.setCancelable(false);
            progress.show();
        }
        catch (Exception ex) {
            errore = ex.getMessage();
        }
    }

    @Override
    protected void onPostExecute(Integer l) {
        if (progress.isShowing()) {
            progress.dismiss();
        }
        activity.EseguiAzioniDopoExport(percorsoSalvataggio, errore);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progress.setMessage(esercizio);
        progress.incrementProgressBy(values[0]);
    }

    private boolean creaTabellaGruppoMuscolare(PdfPCell cellaTabellaEsterna, String gruppo) {
        boolean eserciziPresentiPerGruppo = false;
        int idGruppo = mappaGruppi.get(gruppo);
        eserciziDaEsportare = eserciziController.LeggiEserciziByIdSchedaConIdGruppo(idSchedaDaEsportare, idGruppo);
        if (eserciziDaEsportare != null && eserciziDaEsportare.size() > 0) {
            eserciziPresentiPerGruppo = true;
            PdfPTable tbGruppo = new PdfPTable(1);
            tbGruppo.addCell(creaCellaGruppoMuscolare(gruppo, eserciziDaEsportare));
            cellaTabellaEsterna.addElement(tbGruppo);
        }
        return eserciziPresentiPerGruppo;
    }

    private PdfPCell creaCellaGruppoMuscolare(String nomeGruppo,
                                              ArrayList<EsercizioDaDb> eserciziDaEsportare) {
        PdfPCell cellaGruppo = new PdfPCell();
        cellaGruppo.setBorder(PdfPCell.NO_BORDER);
        cellaGruppo.setPadding(5f);

        Font font = new Font();
        font.setSize(7);
        font.setStyle(Font.BOLD);

        // Gruppo
        Paragraph pNomeGruppo = new Paragraph();
        pNomeGruppo.setFont(font);
        pNomeGruppo.setLeading(1f, 1f);
        pNomeGruppo.setAlignment(Element.ALIGN_LEFT);
        pNomeGruppo.setPaddingTop(5f);
        pNomeGruppo.add(new Chunk(nomeGruppo));
        cellaGruppo.addElement(pNomeGruppo);

        // Esercizi
        PdfPTable tbEsercizi = new PdfPTable(1);
        tbEsercizi.setWidthPercentage(100f);
        for (EsercizioDaDb esercizio : eserciziDaEsportare) {
            tbEsercizi.addCell(creaCellaEsercizio(esercizio));
        }
        cellaGruppo.addElement(tbEsercizi);

        return cellaGruppo;
    }

    private PdfPCell creaCellaEsercizio(EsercizioDaDb esercizio) {

        PdfPCell cellaFinale = new PdfPCell();
        Font font = new Font();
        font.setSize(7);

        // Tabella esercizio con immagine SuperSet
        float[] columnWidths = {20f, 1f};
        PdfPTable tbEsercizio = new PdfPTable(columnWidths);
        tbEsercizio.setPaddingTop(0f);

        // Cella esercizio
        PdfPCell cellaEsercizio = new PdfPCell();
        cellaEsercizio.setBorder(PdfPCell.NO_BORDER);

        // Esercizio con Giorno
        Paragraph pEsercizio = new Paragraph();
        pEsercizio.setFont(font);
        pEsercizio.setLeading(1f, 1f);
        pEsercizio.add(new Chunk(esercizio.getNomeEsercizio() + "          " + esercizio.getGiorno()));
        cellaEsercizio.addElement(pEsercizio);

        // Routine
        if (!TextUtils.isEmpty(esercizio.getRoutine())) {
            // Esercizio con Routine
            Paragraph pRoutine = new Paragraph();
            pRoutine.setFont(font);
            pRoutine.setLeading(1f, 1f);
            pRoutine.add(new Chunk(activity.getResources().getString(R.string.testoLabelRoutine) + " " +  esercizio.getRoutine()));
            cellaEsercizio.addElement(pRoutine);
        }

        // Massimale
        if (esercizio.getMassimale() != 0.0) {
            Paragraph pMassimale = new Paragraph();
            pMassimale.setFont(font);
            pMassimale.setLeading(1f, 1f);
            pMassimale.add(new Chunk(activity.getResources().getString(R.string.testoLabelMassimale) + " " +  Float.toString(esercizio.getMassimale())));
            cellaEsercizio.addElement(pMassimale);
        }

        // Verifica se si tratta di un esercizio cardio oppure no
        if (esercizio.getIdGruppoMuscolare() == 1000) {
            //SetValoriEsercizioCardio(cellaEsercizio, esercizio);
            cellaEsercizio.addElement(UtilityPdf.SetValoriEsercizioCardio(this.activity, esercizio));
        }
        else {
            //SetValoriEsercizioNonCardio(cellaEsercizio, esercizio);
            cellaEsercizio.addElement(UtilityPdf.SetValoriEsercizioNonCardio(this.activity, esercizio));
        }
        // Fine verifica se si tratta di un esercizio cardio oppure no

        // Note
        cellaEsercizio.addElement(UtilityPdf.GetParagraphNote(esercizio));

        tbEsercizio.addCell(cellaEsercizio);

        // Cella immagine SuperSet
        tbEsercizio.addCell(UtilityPdf.GetCellaImmagineSuperSet(this.activity, esercizio));

        cellaFinale.addElement(tbEsercizio);
        return cellaFinale;
    }
}
