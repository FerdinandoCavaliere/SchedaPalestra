package it.ermete.mercurio.schedapalestra;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.HashSet;

import static android.app.ProgressDialog.STYLE_HORIZONTAL;
import static it.ermete.mercurio.schedapalestra.R.string.testoCopia;
import static it.ermete.mercurio.schedapalestra.R.string.testoProgressCopia;
import static it.ermete.mercurio.schedapalestra.R.string.testoTitolProgressCopia;

class CopiaSchedaAsyncTask extends AsyncTask<Integer, Integer, Integer> {

    private final MainActivity activity;
    private final int idSchedaDaCopiare;
    private int idNuovaScheda = -1;
    private final boolean mantieniPesi;

    private ProgressDialog progress;
    private String esercizio;

    private SchedeController schedeController;
    private EserciziController eserciziController;
    private ArrayList<EsercizioDaDb> eserciziDaCopiare;

    private String errore = "";

    public CopiaSchedaAsyncTask(MainActivity _activity,
                                int _idSchedaDaCopiare,
                                boolean _mantieniPesi) {
        super();
        this.activity = _activity;
        this.idSchedaDaCopiare = _idSchedaDaCopiare;
        this.mantieniPesi = _mantieniPesi;
    }

    @Override
    protected Integer doInBackground(Integer... params) {
        if (errore.equals("")) {
            try {
                // Creo la nuova scheda
                String descrizioneSchedaDaCopiare = schedeController.LeggiNotaSchedaAttiva(idSchedaDaCopiare);

                // Creo la nuova scheda e mi salvo l'id
                idNuovaScheda = (int) schedeController.InsertScheda(
                        descrizioneSchedaDaCopiare +
                                activity.getResources().getString(testoCopia));

                HashSet<Integer> superSet = new HashSet<>();
                Boolean isSuperSet = false;
                Boolean isUltimoInSuperSet = false; // Serve per sapere se la SS è finita e quindi posso crearla

                if (mantieniPesi) {
                    // Ciclo gli esercizi da copiare mantenendo i pesi
                    for (EsercizioDaDb singolo : eserciziDaCopiare) {
                        esercizio = singolo.getNomeEsercizio();

                        if (singolo.getSuperSet() != null) {
                            isSuperSet = true;
                            isUltimoInSuperSet = singolo.getSuperSet().getIsUltimo() == 1;
                        } else {
                            isSuperSet = false;
                        }

                        singolo.setId(0);
                        singolo.setIdScheda_FK(idNuovaScheda);
                        long nuovoIdEsercizio = -1;

                        if (singolo.getIdGruppoMuscolare() == 1000) {
                            // Esercizio cardio
                            DatiEsercizioCardioDaDb datiEsercizioCardioDaDb = singolo.getDatiEsercizioCardio();
                            nuovoIdEsercizio = eserciziController.InsertEsercizio(singolo, datiEsercizioCardioDaDb);
                        } else {
                            // Esercizio non cardio
                            ArrayList<RipetizioniPesoDaDb> ripetizioniPeso = singolo.getRipetizioniPeso();
                            nuovoIdEsercizio = eserciziController.InsertEsercizio(singolo, ripetizioniPeso);
                        }

                        // Se l'esercizio originario era in superset lo è anche quello creato
                        if (isSuperSet) {
                            superSet.add((int) nuovoIdEsercizio);
                            // Se l'esercizio originario era l'ultimo nella superset allora è arrivato il momento di creare la SS
                            if (isUltimoInSuperSet) {
                                eserciziController.SalvaSuperSet(superSet); // Creo la superset
                                superSet.clear(); // Svuoto l'elenco per la SS
                            }
                        }

                        publishProgress(1);
                    }
                } else {
                    // Ciclo gli esercizi da copiare non mantenendo i pesi
                    for (EsercizioDaDb singolo : eserciziDaCopiare) {
                        esercizio = singolo.getNomeEsercizio();

                        if (singolo.getSuperSet() != null) {
                            isSuperSet = true;
                            isUltimoInSuperSet = singolo.getSuperSet().getIsUltimo() == 1;
                        } else {
                            isSuperSet = false;
                        }

                        singolo.setId(0);
                        singolo.setIdScheda_FK(idNuovaScheda);
                        long nuovoIdEsercizio = -1;

                        if (singolo.getIdGruppoMuscolare() == 1000) {
                            // Esercizio cardio
                            DatiEsercizioCardioDaDb datiEsercizioCardioDaDb = singolo.getDatiEsercizioCardio();
                            nuovoIdEsercizio = eserciziController.InsertEsercizio(singolo, datiEsercizioCardioDaDb);
                        } else {
                            // Esercizio non cardio
                            ArrayList<RipetizioniPesoDaDb> ripetizioniPeso = singolo.getRipetizioniPeso();
                            for (RipetizioniPesoDaDb singoloRipetizionePeso : ripetizioniPeso) {
                                singoloRipetizionePeso.setPeso(0f);
                                singoloRipetizionePeso.setPercentuale(0f);
                                singoloRipetizionePeso.setRpe(0);
                            }
                            nuovoIdEsercizio = eserciziController.InsertEsercizio(singolo, ripetizioniPeso);
                        }

                        // Se l'esercizio originario era in superset lo è anche quello creato
                        if (isSuperSet) {
                            superSet.add((int) nuovoIdEsercizio);
                            // Se l'esercizio originario era l'ultimo nella superset allora è arrivato il momento di creare la SS
                            if (isUltimoInSuperSet) {
                                eserciziController.SalvaSuperSet(superSet); // Creo la superset
                                superSet.clear(); // Svuoto l'elenco per la SS
                            }
                        }
                        publishProgress(1);
                    }
                }

                // Setto la scheda attiva
                schedeController.SettaSchedaAttiva(idNuovaScheda);
            }
            catch (Exception ex) {
                errore = ex.getMessage();
                if (idNuovaScheda > -1) {
                    schedeController.DeleteScheda(idNuovaScheda);
                    schedeController.SettaSchedaAttiva(idSchedaDaCopiare);
                }
                idNuovaScheda = -1;
            }
        }
        return idNuovaScheda;
    }

    @Override
    protected void onPostExecute(Integer l) {
        if (progress.isShowing()) {
            progress.dismiss();
        }
        activity.EseguiAzioniDopoCopia((Integer) l, errore);
    }

    @Override
    protected void onPreExecute() {
        try {
            // Reperisco gli esercizi della scheda da copia
            schedeController = new SchedeController(activity);
            eserciziController = new EserciziController(activity);
            eserciziDaCopiare = eserciziController.LeggiEserciziByIdScheda(idSchedaDaCopiare);

            progress = new ProgressDialog(activity);

            // Setto la dimensione massima del progress
            progress.setMax(eserciziDaCopiare.size());

            progress.setProgress(0);
            progress.setTitle(activity.getResources().getString(testoTitolProgressCopia));
            progress.setMessage(activity.getResources().getString(testoProgressCopia));
            progress.setProgressStyle(STYLE_HORIZONTAL);
            progress.setCancelable(false);
            progress.show();
        }
        catch (Exception ex) {
            errore = ex.getMessage();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progress.setMessage(esercizio);
        progress.incrementProgressBy(values[0]);
    }
}
