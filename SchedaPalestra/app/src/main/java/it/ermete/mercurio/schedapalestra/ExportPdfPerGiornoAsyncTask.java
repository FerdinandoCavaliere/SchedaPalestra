package it.ermete.mercurio.schedapalestra;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class ExportPdfPerGiornoAsyncTask extends AsyncTask<Integer, Integer, Integer> {

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

    public ExportPdfPerGiornoAsyncTask(MainActivity _activity,
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
                String colonna = "sinistra"; // Indica se il gruppo di esercizi va scritto nella colonna destra o sinistra della tabella di layout

                File file = new File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        "SP_Gi_" + nomeFile + ".pdf"
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
                    if (creaTabellaGiorno(cellaSinistra, "Lunedi")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                else {
                    if (creaTabellaGiorno(cellaDestra, "Lunedi")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                publishProgress(1);

                if (colonna.equals("sinistra")) {
                    if (creaTabellaGiorno(cellaSinistra, "Martedi")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                else {
                    if (creaTabellaGiorno(cellaDestra, "Martedi")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                publishProgress(1);

                if (colonna.equals("sinistra")) {
                    if (creaTabellaGiorno(cellaSinistra, "Mercoledi")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                else {
                    if (creaTabellaGiorno(cellaDestra, "Mercoledi")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                publishProgress(1);

                if (colonna.equals("sinistra")) {
                    if (creaTabellaGiorno(cellaSinistra, "Giovedi")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                else {
                    if (creaTabellaGiorno(cellaDestra, "Giovedi")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                publishProgress(1);

                if (colonna.equals("sinistra")) {
                    if (creaTabellaGiorno(cellaSinistra, "Venerdi")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                else {
                    if (creaTabellaGiorno(cellaDestra, "Venerdi")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                publishProgress(1);

                if (colonna.equals("sinistra")) {
                    if (creaTabellaGiorno(cellaSinistra, "Sabato")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                else {
                    if (creaTabellaGiorno(cellaDestra, "Sabato")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                publishProgress(1);

                if (colonna.equals("sinistra")) {
                    if (creaTabellaGiorno(cellaSinistra, "Domenica")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                else {
                    if (creaTabellaGiorno(cellaDestra, "Domenica")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                publishProgress(1);

                if (colonna.equals("sinistra")) {
                    if (creaTabellaGiorno(cellaSinistra, "Indifferente")) {
                        colonna = this.CambiaColonna(colonna);
                    }
                }
                else {
                    if (creaTabellaGiorno(cellaDestra, "Indifferente")) {
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
            progress.setMax(8); // Al massimo 8 giorni. 7 + "Indifferente"

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

    private boolean creaTabellaGiorno(PdfPCell cellaTabellaEsterna, String giorno) {
        boolean eserciziPresentiPerGiorno = false;
        // Se giorno = "Indifferente" lo sostituisco con uno qualsiasi dei valori dal momento che dal db gli esercizi con giorno
        // "Indifferente arrivano in aggiunta a qualsiasi valore"
        if (giorno.equals("Indifferente")) {
            eserciziDaEsportare = eserciziController.LeggiEserciziByIdSchedaConGiorno(idSchedaDaEsportare, "Lunedi");
        }
        else {
            eserciziDaEsportare = eserciziController.LeggiEserciziByIdSchedaConGiorno(idSchedaDaEsportare, giorno);
        }
        if (eserciziDaEsportare != null && eserciziDaEsportare.size() > 0) {
            PdfPCell cellaGiorno = creaCellaGruppoGiorno(giorno, eserciziDaEsportare);
            if (cellaGiorno != null) {
                eserciziPresentiPerGiorno = true;
                PdfPTable tbGiorno = new PdfPTable(1);
                tbGiorno.addCell(cellaGiorno);
                cellaTabellaEsterna.addElement(tbGiorno);
            }
        }
        return  eserciziPresentiPerGiorno;
    }

    @Nullable
    private PdfPCell creaCellaGruppoGiorno(String giorno,
                                           ArrayList<EsercizioDaDb> eserciziDaEsportare) {
        // Verifico se ci sta almeno un esercizio con il giorno settato che altrimenti sono
        // tutti "indifferente" che devo visualizzare alla fine.
        boolean trovato = false;

        // Se giorno è "Indifferente" devo cercare gli esercizi che non sono collegati a nessun giorno
        if (giorno.equals("Indifferente")) {
            for (EsercizioDaDb esercizio : eserciziDaEsportare) {
                if (TextUtils.isEmpty(esercizio.getGiorno())) {
                    trovato = true;
                }
            }
        } // Devo cercare gli esercizi collegati al giorno passato
        else {
            for (EsercizioDaDb esercizio : eserciziDaEsportare) {
                if (!TextUtils.isEmpty(esercizio.getGiorno()) && esercizio.getGiorno().equals(giorno)) {
                    trovato = true;
                }
            }
        }

        if (trovato)
        {
            PdfPCell cellaGruppo = new PdfPCell();
            cellaGruppo.setBorder(PdfPCell.NO_BORDER);
            cellaGruppo.setPadding(5f);

            Font font = new Font();
            font.setSize(7);
            font.setStyle(Font.BOLD);

            // Giorno
            Paragraph pNomeGiorno= new Paragraph();
            pNomeGiorno.setFont(font);
            pNomeGiorno.setLeading(1f, 1f);
            pNomeGiorno.setAlignment(Element.ALIGN_LEFT);
            pNomeGiorno.setPaddingTop(5f);
            pNomeGiorno.add(new Chunk(giorno));
            cellaGruppo.addElement(pNomeGiorno);

            // Esercizi
            PdfPTable tbEsercizi = new PdfPTable(1);
            tbEsercizi.setWidthPercentage(100f);
            // Se giorno è "Indifferente" devo cercare gli esercizi che non sono collegati a nessun giorno
            if (giorno.equals("Indifferente")) {
                for (EsercizioDaDb esercizio : eserciziDaEsportare) {
                    if (TextUtils.isEmpty(esercizio.getGiorno())) {
                        tbEsercizi.addCell(creaCellaEsercizio(esercizio));
                    }
                }
            } // Devo cercare gli esercizi collegati al giorno passato
            else {
                for (EsercizioDaDb esercizio : eserciziDaEsportare) {
                    if (esercizio.getGiorno().equals(giorno)) {
                        tbEsercizi.addCell(creaCellaEsercizio(esercizio));
                    }
                }
            }

            cellaGruppo.addElement(tbEsercizi);

            return cellaGruppo;
        }
        else {
            return  null;
        }
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

        // Gruppo muscolare
        Paragraph pGruppo = new Paragraph();
        pGruppo.setFont(font);
        pGruppo.setLeading(1f, 1f);
        pGruppo.add(new Chunk(esercizio.getNomeGruppoMuscolare()));
        cellaEsercizio.addElement(pGruppo);

        // Esercizio
        Paragraph pEsercizio = new Paragraph();
        pEsercizio.setFont(font);
        pEsercizio.setLeading(1f, 1f);
        pEsercizio.add(new Chunk(esercizio.getNomeEsercizio()));
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
