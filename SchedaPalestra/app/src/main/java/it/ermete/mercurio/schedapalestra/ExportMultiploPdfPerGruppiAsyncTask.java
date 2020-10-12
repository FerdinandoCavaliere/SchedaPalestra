package it.ermete.mercurio.schedapalestra;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;

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
import java.util.HashMap;
import java.util.HashSet;

public class ExportMultiploPdfPerGruppiAsyncTask extends AsyncTask<HashSet<Integer>, Integer, Integer> {

    private final StoricoActivity activity;
    private final HashSet<Integer> elencoSchedeDaEsportare;
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

    public ExportMultiploPdfPerGruppiAsyncTask(StoricoActivity _activity,
                                               HashSet<Integer> _elencoSchedeDaEsportare,
                                               ArrayList<GruppoMuscolareDaXml> _gruppiMuscolari,
                                               String _nomeFile) {
        super();
        this.activity = _activity;
        this.elencoSchedeDaEsportare = _elencoSchedeDaEsportare;
        this.nomeFile = _nomeFile;

        mappaGruppi = new HashMap<>();
        for (GruppoMuscolareDaXml singolo : _gruppiMuscolari) {
            mappaGruppi.put(singolo.getGruppo(), singolo.getId());
        }
    }

    @Override
    protected Integer doInBackground(HashSet<Integer>... hashSets) {
        if (errore.equals("")) {
            try {
                schedeController = new SchedeController(activity);
                eserciziController = new EserciziController(activity);

                File file = new File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        "SP_Gr_" + nomeFile + ".pdf"
                );

                percorsoSalvataggio = file.getCanonicalPath();

                OutputStream fos = new FileOutputStream(file, false);

                Document document = new Document(PageSize.A4);
                PdfWriter writer = PdfWriter.getInstance(document, fos);

                document.open();

                for (int singoloIdDaEsportare : this.elencoSchedeDaEsportare) {

                    String colonna = "sinistra"; // Indica se il gruppo di esercizi va scritto nella colonna destra o sinistra della tabella di layout

                    // Chiedo i dati della scheda iesima
                    notaScheda = schedeController.LeggiNotaSchedaAttiva(singoloIdDaEsportare);
                    dataScheda = schedeController.LeggiDataInserimentoSchedaAttiva(singoloIdDaEsportare);
                    eserciziDaEsportare = eserciziController.LeggiEserciziByIdScheda(singoloIdDaEsportare);

                    if (eserciziDaEsportare != null && eserciziDaEsportare.size() > 0) {
                        // Intestazione
                        document.add(UtilityPdf.creaIntestazione(this.activity, dataScheda, notaScheda));

                        // Esercizi
                        PdfPTable tbEsercizi = new PdfPTable(2); // Tabella esterna che serve solo per impaginazione
                        PdfPCell cellaSinistra = new PdfPCell();
                        cellaSinistra.setBorder(PdfPCell.NO_BORDER);
                        PdfPCell cellaDestra = new PdfPCell();
                        cellaDestra.setBorder(PdfPCell.NO_BORDER);

                        if (colonna.equals("sinistra")) {
                            if (creaTabellaGruppoMuscolare(cellaSinistra, singoloIdDaEsportare, "Pettorali")) {
                                colonna = this.CambiaColonna(colonna);
                            }
                        }
                        else {
                            if (creaTabellaGruppoMuscolare(cellaDestra, singoloIdDaEsportare, "Pettorali")) {
                                colonna = this.CambiaColonna(colonna);
                            }
                        }

                        if (colonna.equals("sinistra")) {
                            if (creaTabellaGruppoMuscolare(cellaSinistra, singoloIdDaEsportare, "Dorsali")) {
                                colonna = this.CambiaColonna(colonna);
                            }
                        }
                        else {
                            if (creaTabellaGruppoMuscolare(cellaDestra, singoloIdDaEsportare, "Dorsali")) {
                                colonna = this.CambiaColonna(colonna);
                            }
                        }

                        if (colonna.equals("sinistra")) {
                            if (creaTabellaGruppoMuscolare(cellaSinistra, singoloIdDaEsportare, "Spalle")) {
                                colonna = this.CambiaColonna(colonna);
                            }
                        }
                        else {
                            if (creaTabellaGruppoMuscolare(cellaDestra, singoloIdDaEsportare, "Spalle")) {
                                colonna = this.CambiaColonna(colonna);
                            }
                        }

                        if (colonna.equals("sinistra")) {
                            if (creaTabellaGruppoMuscolare(cellaSinistra, singoloIdDaEsportare, "Gambe")) {
                                colonna = this.CambiaColonna(colonna);
                            }
                        }
                        else {
                            if (creaTabellaGruppoMuscolare(cellaDestra, singoloIdDaEsportare, "Gambe")) {
                                colonna = this.CambiaColonna(colonna);
                            }
                        }

                        if (colonna.equals("sinistra")) {
                            if (creaTabellaGruppoMuscolare(cellaSinistra, singoloIdDaEsportare, "Bicipiti")) {
                                colonna = this.CambiaColonna(colonna);
                            }
                        }
                        else {
                            if (creaTabellaGruppoMuscolare(cellaDestra, singoloIdDaEsportare, "Bicipiti")) {
                                colonna = this.CambiaColonna(colonna);
                            }
                        }

                        if (colonna.equals("sinistra")) {
                            if (creaTabellaGruppoMuscolare(cellaSinistra, singoloIdDaEsportare, "Tricipiti")) {
                                colonna = this.CambiaColonna(colonna);
                            }
                        }
                        else {
                            if (creaTabellaGruppoMuscolare(cellaDestra, singoloIdDaEsportare, "Tricipiti")) {
                                colonna = this.CambiaColonna(colonna);
                            }
                        }

                        if (colonna.equals("sinistra")) {
                            if (creaTabellaGruppoMuscolare(cellaSinistra, singoloIdDaEsportare, "Avambracci")) {
                                colonna = this.CambiaColonna(colonna);
                            }
                        }
                        else {
                            if (creaTabellaGruppoMuscolare(cellaDestra, singoloIdDaEsportare, "Avambracci")) {
                                colonna = this.CambiaColonna(colonna);
                            }
                        }

                        if (colonna.equals("sinistra")) {
                            if (creaTabellaGruppoMuscolare(cellaSinistra, singoloIdDaEsportare, "Polpacci")) {
                                colonna = this.CambiaColonna(colonna);
                            }
                        }
                        else {
                            if (creaTabellaGruppoMuscolare(cellaDestra, singoloIdDaEsportare, "Polpacci")) {
                                colonna = this.CambiaColonna(colonna);
                            }
                        }

                        if (colonna.equals("sinistra")) {
                            if (creaTabellaGruppoMuscolare(cellaSinistra, singoloIdDaEsportare, "Addominali")) {
                                colonna = this.CambiaColonna(colonna);
                            }
                        }
                        else {
                            if (creaTabellaGruppoMuscolare(cellaDestra, singoloIdDaEsportare, "Addominali")) {
                                colonna = this.CambiaColonna(colonna);
                            }
                        }

                        if (colonna.equals("sinistra")) {
                            if (creaTabellaGruppoMuscolare(cellaSinistra, singoloIdDaEsportare, "Cardio")) {
                                colonna = this.CambiaColonna(colonna);
                            }
                        }
                        else {
                            if (creaTabellaGruppoMuscolare(cellaDestra, singoloIdDaEsportare, "Cardio")) {
                                colonna = this.CambiaColonna(colonna);
                            }
                        }

                        tbEsercizi.addCell(cellaSinistra);
                        tbEsercizi.addCell(cellaDestra);

                        document.add(tbEsercizi);

                        // Aggiungo pagina
                        document.newPage();
                    }
                }

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
            progress = new ProgressDialog(activity);
            progress.setIndeterminate(true);
            progress.setTitle(activity.getResources().getString(R.string.testoTitolProgressExport));
            progress.setMessage(activity.getResources().getString(R.string.testoProgressExport));
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

    private boolean creaTabellaGruppoMuscolare(PdfPCell cellaTabellaEsterna, int idSchedaDaEsportare, String gruppo) {
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
