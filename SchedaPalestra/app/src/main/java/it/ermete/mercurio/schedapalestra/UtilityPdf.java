package it.ermete.mercurio.schedapalestra;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.*;

import java.io.ByteArrayOutputStream;

public class UtilityPdf {

    public static PdfPTable creaIntestazione(
            AppCompatActivity activity,
            String dataScheda,
            String notaScheda) {

        float[] columnWidths = {1f, 10f};
        PdfPTable tbIntestazione = new PdfPTable(columnWidths);
        try {
            Drawable drawable = activity.getResources().getDrawable(R.drawable.iconatoolbar);
            BitmapDrawable btmDrawable = (BitmapDrawable) drawable;
            Bitmap bmp = btmDrawable.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image logo = Image.getInstance(stream.toByteArray());
            logo.scaleAbsolute(30f, 30f);

            PdfPCell cellaLogo = new PdfPCell();
            cellaLogo.setBorder(PdfPCell.NO_BORDER);
            cellaLogo.setPadding(0f);
            cellaLogo.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellaLogo.setHorizontalAlignment(Element.ALIGN_BOTTOM);
            cellaLogo.addElement(logo);
            tbIntestazione.addCell(cellaLogo);

            Font font = new Font();
            font.setSize(8);

            Paragraph pData = new Paragraph();
            pData.setFont(font);
            pData.add(new Chunk(activity.getResources().getString(R.string.testoLabelDataInserimento) +
                    ": " +
                    dataScheda));
            Paragraph pNota = new Paragraph();
            pNota.setFont(font);
            pNota.add(new Chunk(activity.getResources().getString(R.string.testoLabelNotaInserimento) +
                    ": " +
                    notaScheda));

            PdfPCell cellaDatiScheda = new PdfPCell();
            cellaDatiScheda.setBorder(PdfPCell.NO_BORDER);
            cellaDatiScheda.setPaddingBottom(10f);
            cellaDatiScheda.setVerticalAlignment(Element.ALIGN_TOP);
            cellaDatiScheda.addElement(pData);
            cellaDatiScheda.addElement(pNota);
            tbIntestazione.addCell(cellaDatiScheda);
        }
        catch (Exception ex){
            Log.d("Errore Image", ex.getMessage());
        }
        return tbIntestazione;
    }

    public static PdfPTable SetValoriEsercizioCardio(AppCompatActivity activity,
                                                     EsercizioDaDb esercizio) {
        Font font = new Font();
        font.setSize(7);

        PdfPTable tbDatiEsercizio = new PdfPTable(1);
        tbDatiEsercizio.setWidthPercentage(100f);
        PdfPCell cellaDati = new PdfPCell();
        cellaDati.setBorder(PdfPCell.NO_BORDER);
        cellaDati.setPadding(0f);

        DatiEsercizioCardioDaDb datiEsercizioCardioDaDb = esercizio.getDatiEsercizioCardio();

        Chunk cProgramma = new Chunk(datiEsercizioCardioDaDb.getProgramma() == "" ? "-" : datiEsercizioCardioDaDb.getProgramma());
        Chunk cIntensita = new Chunk(datiEsercizioCardioDaDb.getIntensita() == "" ? "-" : datiEsercizioCardioDaDb.getIntensita());
        Chunk cPendenza = new Chunk(datiEsercizioCardioDaDb.getPendenza() == "" ? "-" : datiEsercizioCardioDaDb.getPendenza());
        Chunk cVelocita = new Chunk(datiEsercizioCardioDaDb.getVelocita() != 0 ? Float.toString(datiEsercizioCardioDaDb.getVelocita()) : "-");
        Chunk cTempo = new Chunk(datiEsercizioCardioDaDb.getTempo() != 0 ? Float.toString(datiEsercizioCardioDaDb.getTempo()) : "-");

        Paragraph pProgramma = new Paragraph();
        pProgramma.setFont(font);
        pProgramma.setLeading(1f, 1f);
        pProgramma.add(new Chunk(activity.getResources().getString(R.string.testoLabelPogramma) + " " + cProgramma));
        cellaDati.addElement(pProgramma);

        Paragraph pIntensita = new Paragraph();
        pIntensita.setFont(font);
        pIntensita.setLeading(1f, 1f);
        pIntensita.add(new Chunk(activity.getResources().getString(R.string.testoLabelIntensita) + " " + cIntensita));
        cellaDati.addElement(pIntensita);

        Paragraph pPendenza = new Paragraph();
        pPendenza.setFont(font);
        pPendenza.setLeading(1f, 1f);
        pPendenza.add(new Chunk(activity.getResources().getString(R.string.testoLabelPendenza) + " " + cPendenza));
        cellaDati.addElement(pPendenza);

        Paragraph pVelocitaTempo = new Paragraph();
        pVelocitaTempo.setFont(font);
        pVelocitaTempo.setLeading(1f, 1f);
        pVelocitaTempo.add(new Chunk(cVelocita +
                " " +
                activity.getResources().getString(R.string.testoLabelPesoKmH) + "    " +
                cTempo +
                " " +
                activity.getResources().getString(R.string.testoLabelTempoMin)));
        cellaDati.addElement(pVelocitaTempo);

        tbDatiEsercizio.addCell(cellaDati);

        //cella.addElement(tbDatiEsercizio);
        return tbDatiEsercizio;
    }

    public static PdfPTable SetValoriEsercizioNonCardio(AppCompatActivity activity,
                                                        EsercizioDaDb esercizio) {
        Font font = new Font();
        font.setSize(7);

        PdfPCell cellaVuota = new PdfPCell();
        cellaVuota.setBorder(PdfPCell.NO_BORDER);
        cellaVuota.setPadding(0f);

        if(esercizio.getIsPiramidale() == 1) {
            // Piramidale
            PdfPTable tbDatiEsercizio = new PdfPTable(1);
            tbDatiEsercizio.setWidthPercentage(100f);
            for (RipetizioniPesoDaDb singolo : esercizio.getRipetizioniPeso()) {
                PdfPCell cellaDati = new PdfPCell();
                cellaDati.setBorder(PdfPCell.NO_BORDER);
                cellaDati.setPadding(0f);

                Chunk pRipetizioni = new Chunk(singolo.getRipetizioni());
                Chunk pPeso = new Chunk(singolo.getPeso() != 0.0 ? Utility.FormatFloat(singolo.getPeso()) : "-");
                Chunk pRecupero = new Chunk(singolo.getRecupero() != 0.0 ? Utility.FormatFloat(singolo.getRecupero()) : "-");

                PdfPTable tbDatiCellaEsercizio = new PdfPTable(3);
                tbDatiCellaEsercizio.setWidthPercentage(100f);

                // Riga 1 Cella 1 (serie, ripetizioni)
                PdfPCell cellaSerieRipetizioni = new PdfPCell();
                cellaSerieRipetizioni.setBorder(PdfPCell.NO_BORDER);
                cellaSerieRipetizioni.setPadding(0f);
                Paragraph pDatiSerieRipetizioni = new Paragraph();
                pDatiSerieRipetizioni.setFont(font);
                pDatiSerieRipetizioni.setLeading(1f, 1f);
                pDatiSerieRipetizioni.add(new Chunk("1" +
                        " x " +
                        pRipetizioni));
                cellaSerieRipetizioni.addElement(pDatiSerieRipetizioni);
                tbDatiCellaEsercizio.addCell(cellaSerieRipetizioni);

                // Riga 1 Cella 2 (peso)
                PdfPCell cellaPeso = new PdfPCell();
                cellaPeso.setBorder(PdfPCell.NO_BORDER);
                cellaPeso.setPadding(0f);
                Paragraph pDatiPeso = new Paragraph();
                pDatiPeso.setFont(font);
                pDatiPeso.setLeading(1f, 1f);
                pDatiPeso.add(new Chunk(pPeso +
                        " " +
                        esercizio.getUnitaDiMisura()));
                cellaPeso.addElement(pDatiPeso);
                tbDatiCellaEsercizio.addCell(cellaPeso);

                // Riga 1 Cella 2 (recupero)
                PdfPCell cellaRecupero = new PdfPCell();
                cellaRecupero.setBorder(PdfPCell.NO_BORDER);
                cellaRecupero.setPadding(0f);
                Paragraph pDatiRecupero = new Paragraph();
                pDatiRecupero.setFont(font);
                pDatiRecupero.setLeading(1f, 1f);
                pDatiRecupero.add(new Chunk(pRecupero +
                        " " +
                        activity.getResources().getString(R.string.testoLabelTempoMin)));
                cellaRecupero.addElement(pDatiRecupero);
                tbDatiCellaEsercizio.addCell(cellaRecupero);

                if (singolo.getPercentuale() != 0.0) {
                    Chunk pPercentuale = new Chunk(singolo.getPercentuale() != 0.0 ? Utility.FormatFloat(singolo.getPercentuale()) : "-");
                    // Riga 2 Cella 1 (vuota)
                    tbDatiCellaEsercizio.addCell(cellaVuota);

                    // Riga 2 Cella 2 (percentuale)

                    PdfPCell cellaPercentuale = new PdfPCell();
                    cellaPercentuale.setBorder(PdfPCell.NO_BORDER);
                    cellaPercentuale.setPadding(0f);
                    Paragraph pDatiPercentuale = new Paragraph();
                    pDatiPercentuale.setFont(font);
                    pDatiPercentuale.setLeading(1f, 1f);
                    pDatiPercentuale.add(new Chunk(pPercentuale +
                            " " +
                            activity.getResources().getString(R.string.testoLabelPesoPercentualeAbbreviato)));
                    cellaPercentuale.addElement(pDatiPercentuale);
                    tbDatiCellaEsercizio.addCell(cellaPercentuale);

                    // Riga 2 Cella 3 (vuota)
                    tbDatiCellaEsercizio.addCell(cellaVuota);
                }

                if (singolo.getRpe() != 0 ) {
                    Chunk pRpe = new Chunk(singolo.getRpe() != 0 ? Integer.toString(singolo.getRpe()) : "-");
                    // Riga 3 Cella 1 (vuota)
                    tbDatiCellaEsercizio.addCell(cellaVuota);

                    // Riga 2 Cella 2 (rpe)
                    PdfPCell cellaRPE = new PdfPCell();
                    cellaRPE.setBorder(PdfPCell.NO_BORDER);
                    cellaRPE.setPadding(0f);
                    Paragraph pDatiRPE = new Paragraph();
                    pDatiRPE.setFont(font);
                    pDatiRPE.setLeading(1f, 1f);
                    pDatiRPE.add(new Chunk(activity.getResources().getString(R.string.testoLabelPesoRPE)+
                            " " + pRpe));
                    cellaRPE.addElement(pDatiRPE);
                    tbDatiCellaEsercizio.addCell(cellaRPE);

                    // Riga 3 Cella 3 (vuota)
                    tbDatiCellaEsercizio.addCell(cellaVuota);
                }

                cellaDati.addElement(tbDatiCellaEsercizio);

                tbDatiEsercizio.addCell(cellaDati);
            }

            //cella.addElement(tbDatiEsercizio);
            return tbDatiEsercizio;
        }
        else {
            // Non piramidale
            PdfPTable tbDatiEsercizio = new PdfPTable(1);
            tbDatiEsercizio.setWidthPercentage(100f);
            PdfPCell cellaDati = new PdfPCell();
            cellaDati.setBorder(PdfPCell.NO_BORDER);
            cellaDati.setPadding(0f);

            Chunk pSerie = new Chunk(Integer.toString(esercizio.getSerie()));
            RipetizioniPesoDaDb ripetizioniPeso = esercizio.getRipetizioniPeso().get(0);
            Chunk pRipetizioni = new Chunk(ripetizioniPeso.getRipetizioni());
            Chunk pPeso = new Chunk(ripetizioniPeso.getPeso() != 0.0 ? Utility.FormatFloat(ripetizioniPeso.getPeso()) : "-");
            Chunk pRecupero = new Chunk(esercizio.getRecupero() != 0.0 ? Utility.FormatFloat(esercizio.getRecupero()) : "-");

            PdfPTable tbDatiCellaEsercizio = new PdfPTable(3);
            tbDatiCellaEsercizio.setWidthPercentage(100f);

            PdfPCell cellaSerieRipetizioni = new PdfPCell();
            cellaSerieRipetizioni.setBorder(PdfPCell.NO_BORDER);
            cellaSerieRipetizioni.setPadding(0f);
            Paragraph pDatiSerieRipetizioni = new Paragraph();
            pDatiSerieRipetizioni.setFont(font);
            pDatiSerieRipetizioni.setLeading(1f, 1f);
            pDatiSerieRipetizioni.add(new Chunk(pSerie +
                    " x " +
                    pRipetizioni));
            cellaSerieRipetizioni.addElement(pDatiSerieRipetizioni);
            tbDatiCellaEsercizio.addCell(cellaSerieRipetizioni);

            PdfPCell cellaPeso = new PdfPCell();
            cellaPeso.setBorder(PdfPCell.NO_BORDER);
            cellaPeso.setPadding(0f);
            Paragraph pDatiPeso = new Paragraph();
            pDatiPeso.setFont(font);
            pDatiPeso.setLeading(1f, 1f);
            pDatiPeso.add(new Chunk(pPeso +
                    " " +
                    esercizio.getUnitaDiMisura()));
            cellaPeso.addElement(pDatiPeso);
            tbDatiCellaEsercizio.addCell(cellaPeso);

            PdfPCell cellaRecupero = new PdfPCell();
            cellaRecupero.setBorder(PdfPCell.NO_BORDER);
            cellaRecupero.setPadding(0f);
            Paragraph pDatiRecupero = new Paragraph();
            pDatiRecupero.setFont(font);
            pDatiRecupero.setLeading(1f, 1f);
            pDatiRecupero.add(new Chunk(pRecupero +
                    " " +
                    activity.getResources().getString(R.string.testoLabelTempoMin)));
            cellaRecupero.addElement(pDatiRecupero);
            tbDatiCellaEsercizio.addCell(cellaRecupero);

            if (ripetizioniPeso.getPercentuale() != 0.0) {
                Chunk pPercentuale = new Chunk(ripetizioniPeso.getPercentuale() != 0.0 ? Utility.FormatFloat(ripetizioniPeso.getPercentuale()) : "-");
                // Riga 2 Cella 1 (vuota)
                tbDatiCellaEsercizio.addCell(cellaVuota);

                // Riga 2 Cella 2 (percentuale)

                PdfPCell cellaPercentuale = new PdfPCell();
                cellaPercentuale.setBorder(PdfPCell.NO_BORDER);
                cellaPercentuale.setPadding(0f);
                Paragraph pDatiPercentuale = new Paragraph();
                pDatiPercentuale.setFont(font);
                pDatiPercentuale.setLeading(1f, 1f);
                pDatiPercentuale.add(new Chunk(pPercentuale +
                        " " +
                        activity.getResources().getString(R.string.testoLabelPesoPercentualeAbbreviato)));
                cellaPercentuale.addElement(pDatiPercentuale);
                tbDatiCellaEsercizio.addCell(cellaPercentuale);

                // Riga 2 Cella 3 (vuota)
                tbDatiCellaEsercizio.addCell(cellaVuota);
            }

            if (ripetizioniPeso.getRpe() != 0) {
                Chunk pRpe = new Chunk(ripetizioniPeso.getRpe() != 0 ? Integer.toString(ripetizioniPeso.getRpe()) : "-");
                // Riga 3 Cella 1 (vuota)
                tbDatiCellaEsercizio.addCell(cellaVuota);

                // Riga 2 Cella 2 (rpe)
                PdfPCell cellaRPE = new PdfPCell();
                cellaRPE.setBorder(PdfPCell.NO_BORDER);
                cellaRPE.setPadding(0f);
                Paragraph pDatiRPE = new Paragraph();
                pDatiRPE.setFont(font);
                pDatiRPE.setLeading(1f, 1f);
                pDatiRPE.add(new Chunk(activity.getResources().getString(R.string.testoLabelPesoRPE)+
                        " " + pRpe));
                cellaRPE.addElement(pDatiRPE);
                tbDatiCellaEsercizio.addCell(cellaRPE);

                // Riga 3 Cella 3 (vuota)
                tbDatiCellaEsercizio.addCell(cellaVuota);
            }

            cellaDati.addElement(tbDatiCellaEsercizio);
            tbDatiEsercizio.addCell(cellaDati);

            //cella.addElement(tbDatiEsercizio);
            return tbDatiEsercizio;
        }
    }

    public static PdfPCell GetCellaImmagineSuperSet(AppCompatActivity activity,
                                                    EsercizioDaDb esercizio) {
        PdfPCell cellaImmagineSuperset = new PdfPCell();
        cellaImmagineSuperset.setBorder(PdfPCell.NO_BORDER);
        cellaImmagineSuperset.setPadding(0f);
        if (esercizio.getSuperSet() != null) {
            Drawable drawable;
            if (esercizio.getSuperSet().getIsPrimo() == 1) {
                if (esercizio.getIsPiramidale() == 1 || esercizio.getIdGruppoMuscolare() == 1000)
                    drawable = activity.getResources().getDrawable(R.drawable.ssprimop);
                else
                    drawable = activity.getResources().getDrawable(R.drawable.ssprimo);
            }
            else if (esercizio.getSuperSet().getIsUltimo() == 1) {
                if (esercizio.getIsPiramidale() == 1 || esercizio.getIdGruppoMuscolare() == 1000)
                    drawable = activity.getResources().getDrawable(R.drawable.ssultimop);
                else
                    drawable = activity.getResources().getDrawable(R.drawable.ssultimo);
            }
            else {
                if (esercizio.getIsPiramidale() == 1 || esercizio.getIdGruppoMuscolare() == 1000)
                    drawable = activity.getResources().getDrawable(R.drawable.ssintermediop);
                else
                    drawable = activity.getResources().getDrawable(R.drawable.ssintermedio);
            }
            try {
                BitmapDrawable btmDrawable = (BitmapDrawable) drawable;
                Bitmap bmp = btmDrawable.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                Image immagineSuperSet = Image.getInstance(stream.toByteArray());
                immagineSuperSet.setScaleToFitHeight(true);
                cellaImmagineSuperset.addElement(immagineSuperSet);
            }
            catch (Exception ex){
                Log.d("Errore Image", ex.getMessage());
            }
        }
        return cellaImmagineSuperset;
    }

    public static Paragraph GetParagraphNote(EsercizioDaDb esercizio) {
        Font font = new Font();
        font.setSize(7);
        Paragraph pNote = new Paragraph();
        pNote.setFont(font);
        pNote.setLeading(1f, 1f);
        pNote.add(new Chunk(esercizio.getNote()));
        return pNote;
    }
}
