package it.ermete.mercurio.schedapalestra;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class ExportPdfElencoAsyncTask extends AsyncTask<Integer, Integer, Integer> {

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

    private String percorsoSalvataggio;

    private String errore = "";

    public ExportPdfElencoAsyncTask(MainActivity _activity,
                                    int _idSchedaDaEsportare,
                                    String _nomeFile) {
        super();
        this.activity = _activity;
        this.idSchedaDaEsportare = _idSchedaDaEsportare;
        this.nomeFile = _nomeFile;
    }

    @Override
    protected Integer doInBackground(Integer... params) {
        if (errore.equals("")) {
            try {
                File file = new File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        "SP_El_" + nomeFile + ".pdf"
                );

                percorsoSalvataggio = file.getCanonicalPath();

                OutputStream fos = new FileOutputStream(file, false);

                Document document = new Document(PageSize.A4);
                PdfWriter writer = PdfWriter.getInstance(document, fos);

                document.open();

                // Intestazione
                document.add(UtilityPdf.creaIntestazione(this.activity, dataScheda, notaScheda));

                // Esercizi
                PdfPTable tbEsercizi = new PdfPTable(2); // Tabella esterna
                PdfPTable tbEserciziSinistra = new PdfPTable(1); // Tabella figlia di sinistra
                tbEserciziSinistra.setWidthPercentage(100f);
                PdfPTable tbEserciziDestra = new PdfPTable(1); // Tabella figlia di destra
                tbEserciziDestra.setWidthPercentage(100f);

                int indice = 1;
                if (eserciziDaEsportare.size() > 1) {
                    int numeroEserciziPerColonna = (int) Math.round(eserciziDaEsportare.size() / 2);
                    if (eserciziDaEsportare.size() % 2 == 1) {
                        numeroEserciziPerColonna++;
                    }
                    for (int i = 0; i < numeroEserciziPerColonna; i++) {
                        // Esercizio della prima colonna
                        tbEserciziSinistra.addCell(creaCellaEsercizio(eserciziDaEsportare.get(i), indice));
                        publishProgress(1);
                        // Esercizio della seconda colonna
                        if (eserciziDaEsportare.size() > i + numeroEserciziPerColonna) {
                            tbEserciziDestra.addCell(creaCellaEsercizio(eserciziDaEsportare.get(i + numeroEserciziPerColonna), indice + numeroEserciziPerColonna));
                        }
                        else {
                            tbEserciziDestra.addCell(new PdfPCell());
                        }
                        publishProgress(1);
                        indice++;
                    }
                }
                else {
                    tbEserciziSinistra.addCell(creaCellaEsercizio(eserciziDaEsportare.get(0), indice));
                }

                PdfPCell cellaSinistra = new PdfPCell();
                cellaSinistra.setBorder(PdfPCell.NO_BORDER);
                cellaSinistra.setPadding(5f);
                cellaSinistra.addElement(tbEserciziSinistra);
                PdfPCell cellaDestra = new PdfPCell();
                cellaDestra.setBorder(PdfPCell.NO_BORDER);
                cellaDestra.setPadding(5f);
                cellaDestra.addElement(tbEserciziDestra);

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
            progress.setMax(eserciziDaEsportare.size());

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

    private PdfPCell creaCellaEsercizio(EsercizioDaDb esercizio,
                                        int indice) {

        PdfPCell cellaFinale = new PdfPCell();
        Font font = new Font();
        font.setSize(7);
        Font fontBold = new Font();
        fontBold.setSize(7);
        fontBold.setStyle(Font.BOLD);

        // Tabella esercizio con immagine SuperSet
        float[] columnWidths = {0.5f, 20f, 1.2f};
        PdfPTable tbEsercizio = new PdfPTable(columnWidths);
        tbEsercizio.setPaddingTop(0f);

        // Cella indice
        PdfPCell cellaIndice = new PdfPCell();
        cellaIndice.setBorder(PdfPCell.NO_BORDER);
        cellaIndice.setPadding(0f);
        cellaIndice.setPaddingLeft(-20f);
        Paragraph pIndice = new Paragraph();
        pIndice.setFont(font);
        pIndice.setLeading(1f, 1f);
        pIndice.setAlignment(Element.ALIGN_LEFT);
        pIndice.add(new Chunk(Integer.toString(indice) + " )"));
        cellaIndice.addElement(pIndice);
        tbEsercizio.addCell(cellaIndice);

        // Cella esercizio
        PdfPCell cellaEsercizio = new PdfPCell();
        cellaEsercizio.setBorder(PdfPCell.NO_BORDER);
        cellaEsercizio.setPadding(0f);
        cellaIndice.setPaddingLeft(-20f);

        // Gruppo muscolare
        Paragraph pGruppo = new Paragraph();
        pGruppo.setFont(fontBold);
        pGruppo.setLeading(1f, 1f);
        pGruppo.add(new Chunk(esercizio.getNomeGruppoMuscolare()));
        cellaEsercizio.addElement(pGruppo);

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
            cellaEsercizio.addElement(UtilityPdf.SetValoriEsercizioCardio(this.activity, esercizio));
        }
        else {
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
