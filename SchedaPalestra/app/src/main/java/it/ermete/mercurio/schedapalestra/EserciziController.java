package it.ermete.mercurio.schedapalestra;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashSet;

public class EserciziController {

    private final EserciziModel model;

    public EserciziController(Context context) {
        this.model = new EserciziModel(context);
    }

    public ArrayList<EsercizioDaDb> LeggiEserciziByIdScheda(int _idScheda) {
        Cursor cursore = null; // Cursore esercizi
        Cursor cursoreRipetizioniPesi = null; // Cursore ripetizioni peso
        Cursor cursoreDatiEsercizioCardio = null; // Cursore dati esercizio cardio
        Cursor cursoreSuperset = null; // Cursore superset
        try {
            ArrayList<EsercizioDaDb> elenco = new ArrayList<>();
            model.ApriConnessionePerLettura();
            cursore = model.LeggiEserciziByIdScheda(_idScheda);
            if (cursore.getCount() > 0) {
                EsercizioDaDb nuovoEsercizio;
                while(cursore.moveToNext()) {
                    nuovoEsercizio = new EsercizioDaDb(cursore.getInt(0),
                            cursore.getInt(1),
                            cursore.getString(2),
                            cursore.getInt(3),
                            cursore.getString(4),
                            !cursore.isNull(5) ? cursore.getInt(5) : 0,
                            !cursore.isNull(6) ? cursore.getFloat(6) : 0,
                            !cursore.isNull(7) ? cursore.getInt(7) : 0,
                            cursore.getString(8),
                            cursore.getString(9),
                            cursore.getInt(10),
                            cursore.getString(11),
                            cursore.getString(12),
                            cursore.getString(13),
                            !cursore.isNull(14) ? cursore.getFloat(14) : 0);

                    // Leggo ripetizioni e peso dell'esercizio
                    cursoreRipetizioniPesi = model.LeggiRipetizioniPesoByIdEsercizio(nuovoEsercizio.getId());
                    ArrayList<RipetizioniPesoDaDb> ripetizioniPeso = new ArrayList<>();
                    if (cursoreRipetizioniPesi.getCount() > 0) {
                        RipetizioniPesoDaDb nuovoRipetizioniPeso;
                        while (cursoreRipetizioniPesi.moveToNext()) {
                            nuovoRipetizioniPeso = new RipetizioniPesoDaDb(cursoreRipetizioniPesi.getInt(0),
                                    !cursoreRipetizioniPesi.isNull(2) ? cursoreRipetizioniPesi.getString(2) : "",
                                    !cursoreRipetizioniPesi.isNull(3) ? cursoreRipetizioniPesi.getFloat(3) : 0,
                                    !cursoreRipetizioniPesi.isNull(4) ? cursoreRipetizioniPesi.getFloat(4) : 0,
                                    !cursoreRipetizioniPesi.isNull(5) ? cursoreRipetizioniPesi.getFloat(5) : 0,
                                    !cursoreRipetizioniPesi.isNull(6) ? cursoreRipetizioniPesi.getInt(6) : 0);
                            ripetizioniPeso.add(nuovoRipetizioniPeso);
                        }
                    }
                    // Attacco ripetizioni e peso all'esercizio
                    nuovoEsercizio.setRipetizioniPeso(ripetizioniPeso);

                    // Leggo dati esercizio di cardio
                    cursoreDatiEsercizioCardio = model.LeggiDatiEsercizioCardioByIdEsercizio(nuovoEsercizio.getId());
                    if (cursoreDatiEsercizioCardio.getCount() > 0) {
                        cursoreDatiEsercizioCardio.moveToFirst();
                        DatiEsercizioCardioDaDb datiEsercizioCardioDaDb = new DatiEsercizioCardioDaDb(
                                cursoreDatiEsercizioCardio.getInt(0),
                                cursoreDatiEsercizioCardio.getString(2),
                                cursoreDatiEsercizioCardio.getString(3),
                                cursoreDatiEsercizioCardio.getString(4),
                                !cursoreDatiEsercizioCardio.isNull(5) ? cursoreDatiEsercizioCardio.getFloat(5) : 0,
                                !cursoreDatiEsercizioCardio.isNull(6) ? cursoreDatiEsercizioCardio.getFloat(6) : 0);
                        // Attacco dati esercizio cardio
                        nuovoEsercizio.setDatiEsercizioCardio(datiEsercizioCardioDaDb);
                    }

                    // Leggo se l'esercizio fa parte di una superset
                    cursoreSuperset = model.LeggiSuperSetByIdEsercizio(nuovoEsercizio.getId());
                    if (cursoreSuperset != null && cursoreSuperset.getCount() > 0) {
                        SuperSetDaDb nuovoSuperSet;
                        cursoreSuperset.moveToFirst();
                        nuovoSuperSet = new SuperSetDaDb(cursoreSuperset.getInt(0),
                                cursoreSuperset.getString(1),
                                cursoreSuperset.getInt(2),
                                cursoreSuperset.getInt(3),
                                cursoreSuperset.getInt(4));
                        nuovoEsercizio.setSuperSet(nuovoSuperSet);
                    }

                    // Aggiungo l'esercizio all'elenco
                    elenco.add(nuovoEsercizio);
                }
            }
            return elenco;
        }
        catch (Exception ex) {
            return null;
        }
        finally {
            if (cursore != null && !cursore.isClosed())
                cursore.close();
            if (cursoreRipetizioniPesi != null && !cursoreRipetizioniPesi.isClosed())
                cursoreRipetizioniPesi.close();
            if (cursoreSuperset != null && !cursoreSuperset.isClosed())
                cursoreSuperset.close();
            model.ChiudiConnessione();
        }
    }

    public ArrayList<EsercizioDaDb> LeggiEserciziByIdSchedaConIdGruppo(int _idScheda, int _idGruppo) {
        Cursor cursore = null; // Cursore esercizi
        Cursor cursoreRipetizioniPesi = null; // Cursore ripetizioni peso
        Cursor cursoreDatiEsercizioCardio = null; // Cursore dati esercizio cardio
        Cursor cursoreSuperset = null; // Cursore superset
        try {
            ArrayList<EsercizioDaDb> elenco = new ArrayList<>();
            model.ApriConnessionePerLettura();
            cursore = model.LeggiEserciziByIdSchedaConIdGruppo(_idScheda, _idGruppo);
            if (cursore.getCount() > 0) {
                EsercizioDaDb nuovoEsercizio;
                while(cursore.moveToNext()) {
                    nuovoEsercizio = new EsercizioDaDb(cursore.getInt(0),
                            cursore.getInt(1),
                            cursore.getString(2),
                            cursore.getInt(3),
                            cursore.getString(4),
                            !cursore.isNull(5) ? cursore.getInt(5) : 0,
                            !cursore.isNull(6) ? cursore.getFloat(6) : 0,
                            !cursore.isNull(7) ? cursore.getInt(7) : 0,
                            cursore.getString(8),
                            cursore.getString(9),
                            cursore.getInt(10),
                            cursore.getString(11),
                            cursore.getString(12),
                            cursore.getString(13),
                            !cursore.isNull(14) ? cursore.getFloat(14) : 0);

                    // Leggo ripetizioni e peso dell'esercizio
                    cursoreRipetizioniPesi = model.LeggiRipetizioniPesoByIdEsercizio(nuovoEsercizio.getId());
                    ArrayList<RipetizioniPesoDaDb> ripetizioniPeso = new ArrayList<>();
                    if (cursoreRipetizioniPesi.getCount() > 0) {
                        RipetizioniPesoDaDb nuovoRipetizioniPeso;
                        while (cursoreRipetizioniPesi.moveToNext()) {
                            nuovoRipetizioniPeso = new RipetizioniPesoDaDb(cursoreRipetizioniPesi.getInt(0),
                                    !cursoreRipetizioniPesi.isNull(2) ? cursoreRipetizioniPesi.getString(2) : "",
                                    !cursoreRipetizioniPesi.isNull(3) ? cursoreRipetizioniPesi.getFloat(3) : 0,
                                    !cursoreRipetizioniPesi.isNull(4) ? cursoreRipetizioniPesi.getFloat(4) : 0,
                                    !cursoreRipetizioniPesi.isNull(5) ? cursoreRipetizioniPesi.getFloat(5) : 0,
                                    !cursoreRipetizioniPesi.isNull(6) ? cursoreRipetizioniPesi.getInt(6) : 0);
                            ripetizioniPeso.add(nuovoRipetizioniPeso);
                        }
                    }
                    // Attacco ripetizioni e peso all'esercizio
                    nuovoEsercizio.setRipetizioniPeso(ripetizioniPeso);

                    // Leggo dati esercizio di cardio
                    cursoreDatiEsercizioCardio = model.LeggiDatiEsercizioCardioByIdEsercizio(nuovoEsercizio.getId());
                    if (cursoreDatiEsercizioCardio.getCount() > 0) {
                        cursoreDatiEsercizioCardio.moveToFirst();
                        DatiEsercizioCardioDaDb datiEsercizioCardioDaDb = new DatiEsercizioCardioDaDb(
                                cursoreDatiEsercizioCardio.getInt(0),
                                cursoreDatiEsercizioCardio.getString(2),
                                cursoreDatiEsercizioCardio.getString(3),
                                cursoreDatiEsercizioCardio.getString(4),
                                !cursoreDatiEsercizioCardio.isNull(5) ? cursoreDatiEsercizioCardio.getFloat(5) : 0,
                                !cursoreDatiEsercizioCardio.isNull(6) ? cursoreDatiEsercizioCardio.getFloat(6) : 0);
                        // Attacco dati esercizio cardio
                        nuovoEsercizio.setDatiEsercizioCardio(datiEsercizioCardioDaDb);
                    }

                    // Leggo se l'esercizio fa parte di una superset
                    cursoreSuperset = model.LeggiSuperSetByIdEsercizio(nuovoEsercizio.getId());
                    if (cursoreSuperset != null && cursoreSuperset.getCount() > 0) {
                        SuperSetDaDb nuovoSuperSet;
                        cursoreSuperset.moveToFirst();
                        nuovoSuperSet = new SuperSetDaDb(cursoreSuperset.getInt(0),
                                cursoreSuperset.getString(1),
                                cursoreSuperset.getInt(2),
                                cursoreSuperset.getInt(3),
                                cursoreSuperset.getInt(4));
                        nuovoEsercizio.setSuperSet(nuovoSuperSet);
                    }
                    // Aggiungo l'esercizio all'elenco
                    elenco.add(nuovoEsercizio);
                }
            }
            return elenco;
        }
        catch (Exception ex) {
            return null;
        }
        finally {
            if (cursore != null && !cursore.isClosed())
                cursore.close();
            if (cursoreRipetizioniPesi != null && !cursoreRipetizioniPesi.isClosed())
                cursoreRipetizioniPesi.close();
            if (cursoreSuperset != null && !cursoreSuperset.isClosed())
                cursoreSuperset.close();
            model.ChiudiConnessione();
        }
    }

    public ArrayList<EsercizioDaDb> LeggiEserciziByIdSchedaConGruppi(int _idScheda, HashSet<String> nomiGruppi) {
        Cursor cursore = null; // Cursore esercizi
        Cursor cursoreRipetizioniPesi = null; // Cursore ripetizioni peso
        Cursor cursoreDatiEsercizioCardio = null; // Cursore dati esercizio cardio
        Cursor cursoreSuperset = null; // Cursore superset
        try {
            ArrayList<EsercizioDaDb> elenco = new ArrayList<>();
            model.ApriConnessionePerLettura();
            cursore = model.LeggiEserciziByIdSchedaConGruppi(_idScheda, nomiGruppi);
            if (cursore.getCount() > 0) {
                EsercizioDaDb nuovoEsercizio;
                while(cursore.moveToNext()) {
                    nuovoEsercizio = new EsercizioDaDb(cursore.getInt(0),
                            cursore.getInt(1),
                            cursore.getString(2),
                            cursore.getInt(3),
                            cursore.getString(4),
                            !cursore.isNull(5) ? cursore.getInt(5) : 0,
                            !cursore.isNull(6) ? cursore.getFloat(6) : 0,
                            !cursore.isNull(7) ? cursore.getInt(7) : 0,
                            cursore.getString(8),
                            cursore.getString(9),
                            cursore.getInt(10),
                            cursore.getString(11),
                            cursore.getString(12),
                            cursore.getString(13),
                            !cursore.isNull(14) ? cursore.getFloat(14) : 0);

                    // Leggo ripetizioni e peso dell'esercizio
                    cursoreRipetizioniPesi = model.LeggiRipetizioniPesoByIdEsercizio(nuovoEsercizio.getId());
                    ArrayList<RipetizioniPesoDaDb> ripetizioniPeso = new ArrayList<>();
                    if (cursoreRipetizioniPesi.getCount() > 0) {
                        RipetizioniPesoDaDb nuovoRipetizioniPeso;
                        while (cursoreRipetizioniPesi.moveToNext()) {
                            nuovoRipetizioniPeso = new RipetizioniPesoDaDb(cursoreRipetizioniPesi.getInt(0),
                                    !cursoreRipetizioniPesi.isNull(2) ? cursoreRipetizioniPesi.getString(2) : "",
                                    !cursoreRipetizioniPesi.isNull(3) ? cursoreRipetizioniPesi.getFloat(3) : 0,
                                    !cursoreRipetizioniPesi.isNull(4) ? cursoreRipetizioniPesi.getFloat(4) : 0,
                                    !cursoreRipetizioniPesi.isNull(5) ? cursoreRipetizioniPesi.getFloat(5) : 0,
                                    !cursoreRipetizioniPesi.isNull(6) ? cursoreRipetizioniPesi.getInt(6) : 0);
                            ripetizioniPeso.add(nuovoRipetizioniPeso);
                        }
                    }
                    // Attacco ripetizioni e peso all'esercizio
                    nuovoEsercizio.setRipetizioniPeso(ripetizioniPeso);

                    // Leggo dati esercizio di cardio
                    cursoreDatiEsercizioCardio = model.LeggiDatiEsercizioCardioByIdEsercizio(nuovoEsercizio.getId());
                    if (cursoreDatiEsercizioCardio.getCount() > 0) {
                        cursoreDatiEsercizioCardio.moveToFirst();
                        DatiEsercizioCardioDaDb datiEsercizioCardioDaDb = new DatiEsercizioCardioDaDb(
                                cursoreDatiEsercizioCardio.getInt(0),
                                cursoreDatiEsercizioCardio.getString(2),
                                cursoreDatiEsercizioCardio.getString(3),
                                cursoreDatiEsercizioCardio.getString(4),
                                !cursoreDatiEsercizioCardio.isNull(5) ? cursoreDatiEsercizioCardio.getFloat(5) : 0,
                                !cursoreDatiEsercizioCardio.isNull(6) ? cursoreDatiEsercizioCardio.getFloat(6) : 0);
                        // Attacco dati esercizio cardio
                        nuovoEsercizio.setDatiEsercizioCardio(datiEsercizioCardioDaDb);
                    }

                    // Leggo se l'esercizio fa parte di una superset
                    cursoreSuperset = model.LeggiSuperSetByIdEsercizio(nuovoEsercizio.getId());
                    if (cursoreSuperset != null && cursoreSuperset.getCount() > 0) {
                        SuperSetDaDb nuovoSuperSet;
                        cursoreSuperset.moveToFirst();
                        nuovoSuperSet = new SuperSetDaDb(cursoreSuperset.getInt(0),
                                cursoreSuperset.getString(1),
                                cursoreSuperset.getInt(2),
                                cursoreSuperset.getInt(3),
                                cursoreSuperset.getInt(4));
                        nuovoEsercizio.setSuperSet(nuovoSuperSet);
                    }
                    // Aggiungo l'esercizio all'elenco
                    elenco.add(nuovoEsercizio);
                }
            }
            return elenco;
        }
        catch (Exception ex) {
            return null;
        }
        finally {
            if (cursore != null && !cursore.isClosed())
                cursore.close();
            if (cursoreRipetizioniPesi != null && !cursoreRipetizioniPesi.isClosed())
                cursoreRipetizioniPesi.close();
            if (cursoreSuperset != null && !cursoreSuperset.isClosed())
                cursoreSuperset.close();
            model.ChiudiConnessione();
        }
    }

    public ArrayList<EsercizioDaDb> LeggiEserciziByIdSchedaConGiorno(int _idScheda, String _giorno) {
        Cursor cursore = null; // Cursore esercizi
        Cursor cursoreRipetizioniPesi = null; // Cursore ripetizioni peso
        Cursor cursoreDatiEsercizioCardio = null; // Cursore dati esercizio cardio
        Cursor cursoreSuperset = null; // Cursore superset
        try {
            ArrayList<EsercizioDaDb> elenco = new ArrayList<>();
            model.ApriConnessionePerLettura();
            cursore = model.LeggiEserciziByIdSchedaConGiorno(_idScheda, _giorno);
            if (cursore.getCount() > 0) {
                EsercizioDaDb nuovoEsercizio;
                while(cursore.moveToNext()) {
                    nuovoEsercizio = new EsercizioDaDb(cursore.getInt(0),
                            cursore.getInt(1),
                            cursore.getString(2),
                            cursore.getInt(3),
                            cursore.getString(4),
                            !cursore.isNull(5) ? cursore.getInt(5) : 0,
                            !cursore.isNull(6) ? cursore.getFloat(6) : 0,
                            !cursore.isNull(7) ? cursore.getInt(7) : 0,
                            cursore.getString(8),
                            cursore.getString(9),
                            cursore.getInt(10),
                            cursore.getString(11),
                            cursore.getString(12),
                            cursore.getString(13),
                            !cursore.isNull(14) ? cursore.getFloat(14) : 0);

                    // Leggo ripetizioni e peso dell'esercizio
                    cursoreRipetizioniPesi = model.LeggiRipetizioniPesoByIdEsercizio(nuovoEsercizio.getId());
                    ArrayList<RipetizioniPesoDaDb> ripetizioniPeso = new ArrayList<>();
                    if (cursoreRipetizioniPesi.getCount() > 0) {
                        RipetizioniPesoDaDb nuovoRipetizioniPeso;
                        while (cursoreRipetizioniPesi.moveToNext()) {
                            nuovoRipetizioniPeso = new RipetizioniPesoDaDb(cursoreRipetizioniPesi.getInt(0),
                                    !cursoreRipetizioniPesi.isNull(2) ? cursoreRipetizioniPesi.getString(2) : "",
                                    !cursoreRipetizioniPesi.isNull(3) ? cursoreRipetizioniPesi.getFloat(3) : 0,
                                    !cursoreRipetizioniPesi.isNull(4) ? cursoreRipetizioniPesi.getFloat(4) : 0,
                                    !cursoreRipetizioniPesi.isNull(5) ? cursoreRipetizioniPesi.getFloat(5) : 0,
                                    !cursoreRipetizioniPesi.isNull(6) ? cursoreRipetizioniPesi.getInt(6) : 0);
                            ripetizioniPeso.add(nuovoRipetizioniPeso);
                        }
                    }
                    // Attacco ripetizioni e peso all'esercizio
                    nuovoEsercizio.setRipetizioniPeso(ripetizioniPeso);

                    // Leggo dati esercizio di cardio
                    cursoreDatiEsercizioCardio = model.LeggiDatiEsercizioCardioByIdEsercizio(nuovoEsercizio.getId());
                    if (cursoreDatiEsercizioCardio.getCount() > 0) {
                        cursoreDatiEsercizioCardio.moveToFirst();
                        DatiEsercizioCardioDaDb datiEsercizioCardioDaDb = new DatiEsercizioCardioDaDb(
                                cursoreDatiEsercizioCardio.getInt(0),
                                cursoreDatiEsercizioCardio.getString(2),
                                cursoreDatiEsercizioCardio.getString(3),
                                cursoreDatiEsercizioCardio.getString(4),
                                !cursoreDatiEsercizioCardio.isNull(5) ? cursoreDatiEsercizioCardio.getFloat(5) : 0,
                                !cursoreDatiEsercizioCardio.isNull(6) ? cursoreDatiEsercizioCardio.getFloat(6) : 0);
                        // Attacco dati esercizio cardio
                        nuovoEsercizio.setDatiEsercizioCardio(datiEsercizioCardioDaDb);
                    }

                    // Leggo se l'esercizio fa parte di una superset
                    cursoreSuperset = model.LeggiSuperSetByIdEsercizio(nuovoEsercizio.getId());
                    if (cursoreSuperset != null && cursoreSuperset.getCount() > 0) {
                        SuperSetDaDb nuovoSuperSet;
                        cursoreSuperset.moveToFirst();
                        nuovoSuperSet = new SuperSetDaDb(cursoreSuperset.getInt(0),
                                cursoreSuperset.getString(1),
                                cursoreSuperset.getInt(2),
                                cursoreSuperset.getInt(3),
                                cursoreSuperset.getInt(4));
                        nuovoEsercizio.setSuperSet(nuovoSuperSet);
                    }
                    // Aggiungo l'esercizio all'elenco
                    elenco.add(nuovoEsercizio);
                }
            }
            return elenco;
        }
        catch (Exception ex) {
            return null;
        }
        finally {
            if (cursore != null && !cursore.isClosed())
                cursore.close();
            if (cursoreRipetizioniPesi != null && !cursoreRipetizioniPesi.isClosed())
                cursoreRipetizioniPesi.close();
            if (cursoreSuperset != null && !cursoreSuperset.isClosed())
                cursoreSuperset.close();
            model.ChiudiConnessione();
        }
    }

    public ArrayList<EsercizioDaDb> LeggiEserciziByIdSchedaConRoutine(int _idScheda, String _routine) {
        Cursor cursore = null; // Cursore esercizi
        Cursor cursoreRipetizioniPesi = null; // Cursore ripetizioni peso
        Cursor cursoreDatiEsercizioCardio = null; // Cursore dati esercizio cardio
        Cursor cursoreSuperset = null; // Cursore superset
        try {
            ArrayList<EsercizioDaDb> elenco = new ArrayList<>();
            model.ApriConnessionePerLettura();
            cursore = model.LeggiEserciziByIdSchedaConRoutine(_idScheda, _routine);
            if (cursore.getCount() > 0) {
                EsercizioDaDb nuovoEsercizio;
                while(cursore.moveToNext()) {
                    nuovoEsercizio = new EsercizioDaDb(cursore.getInt(0),
                            cursore.getInt(1),
                            cursore.getString(2),
                            cursore.getInt(3),
                            cursore.getString(4),
                            !cursore.isNull(5) ? cursore.getInt(5) : 0,
                            !cursore.isNull(6) ? cursore.getFloat(6) : 0,
                            !cursore.isNull(7) ? cursore.getInt(7) : 0,
                            cursore.getString(8),
                            cursore.getString(9),
                            cursore.getInt(10),
                            cursore.getString(11),
                            cursore.getString(12),
                            cursore.getString(13),
                            !cursore.isNull(14) ? cursore.getFloat(14) : 0);

                    // Leggo ripetizioni e peso dell'esercizio
                    cursoreRipetizioniPesi = model.LeggiRipetizioniPesoByIdEsercizio(nuovoEsercizio.getId());
                    ArrayList<RipetizioniPesoDaDb> ripetizioniPeso = new ArrayList<>();
                    if (cursoreRipetizioniPesi.getCount() > 0) {
                        RipetizioniPesoDaDb nuovoRipetizioniPeso;
                        while (cursoreRipetizioniPesi.moveToNext()) {
                            nuovoRipetizioniPeso = new RipetizioniPesoDaDb(cursoreRipetizioniPesi.getInt(0),
                                    !cursoreRipetizioniPesi.isNull(2) ? cursoreRipetizioniPesi.getString(2) : "",
                                    !cursoreRipetizioniPesi.isNull(3) ? cursoreRipetizioniPesi.getFloat(3) : 0,
                                    !cursoreRipetizioniPesi.isNull(4) ? cursoreRipetizioniPesi.getFloat(4) : 0,
                                    !cursoreRipetizioniPesi.isNull(5) ? cursoreRipetizioniPesi.getFloat(5) : 0,
                                    !cursoreRipetizioniPesi.isNull(6) ? cursoreRipetizioniPesi.getInt(6) : 0);
                            ripetizioniPeso.add(nuovoRipetizioniPeso);
                        }
                    }
                    // Attacco ripetizioni e peso all'esercizio
                    nuovoEsercizio.setRipetizioniPeso(ripetizioniPeso);

                    // Leggo dati esercizio di cardio
                    cursoreDatiEsercizioCardio = model.LeggiDatiEsercizioCardioByIdEsercizio(nuovoEsercizio.getId());
                    if (cursoreDatiEsercizioCardio.getCount() > 0) {
                        cursoreDatiEsercizioCardio.moveToFirst();
                        DatiEsercizioCardioDaDb datiEsercizioCardioDaDb = new DatiEsercizioCardioDaDb(
                                cursoreDatiEsercizioCardio.getInt(0),
                                cursoreDatiEsercizioCardio.getString(2),
                                cursoreDatiEsercizioCardio.getString(3),
                                cursoreDatiEsercizioCardio.getString(4),
                                !cursoreDatiEsercizioCardio.isNull(5) ? cursoreDatiEsercizioCardio.getFloat(5) : 0,
                                !cursoreDatiEsercizioCardio.isNull(6) ? cursoreDatiEsercizioCardio.getFloat(6) : 0);
                        // Attacco dati esercizio cardio
                        nuovoEsercizio.setDatiEsercizioCardio(datiEsercizioCardioDaDb);
                    }

                    // Leggo se l'esercizio fa parte di una superset
                    cursoreSuperset = model.LeggiSuperSetByIdEsercizio(nuovoEsercizio.getId());
                    if (cursoreSuperset != null && cursoreSuperset.getCount() > 0) {
                        SuperSetDaDb nuovoSuperSet;
                        cursoreSuperset.moveToFirst();
                        nuovoSuperSet = new SuperSetDaDb(cursoreSuperset.getInt(0),
                                cursoreSuperset.getString(1),
                                cursoreSuperset.getInt(2),
                                cursoreSuperset.getInt(3),
                                cursoreSuperset.getInt(4));
                        nuovoEsercizio.setSuperSet(nuovoSuperSet);
                    }
                    // Aggiungo l'esercizio all'elenco
                    elenco.add(nuovoEsercizio);
                }
            }
            return elenco;
        }
        catch (Exception ex) {
            return null;
        }
        finally {
            if (cursore != null && !cursore.isClosed())
                cursore.close();
            if (cursoreRipetizioniPesi != null && !cursoreRipetizioniPesi.isClosed())
                cursoreRipetizioniPesi.close();
            if (cursoreSuperset != null && !cursoreSuperset.isClosed())
                cursoreSuperset.close();
            model.ChiudiConnessione();
        }
    }

    /*
    * Insert esercizio non di cardio
    * */
    public long InsertEsercizio(EsercizioDaDb _esercizio, ArrayList<RipetizioniPesoDaDb> ripetizioniPeso) {
        try {
            return model.InsertEsercizio(_esercizio, ripetizioniPeso);
        }
        catch (Exception ex) {
            return -2;
        }
    }

    /*
    * Insert esercizio di cardio
    * */
    public long InsertEsercizio(EsercizioDaDb _esercizio, DatiEsercizioCardioDaDb datiEsercizioCardioDaDb) {
        try {
            return model.InsertEsercizio(_esercizio, datiEsercizioCardioDaDb);
        }
        catch (Exception ex) {
            return -2;
        }
    }

    /*
    * Update esercizio non di cardio
    * */
    public int UpdateEsercizio(EsercizioDaDb _esercizio, ArrayList<RipetizioniPesoDaDb> ripetizioniPeso) {
        try {
            return model.UpdateEsercizio(_esercizio, ripetizioniPeso);
        }
        catch (Exception ex) {
            return -2;
        }
    }

    /*
    * Update esercizio di cardio
    * */
    public int UpdateEsercizio(EsercizioDaDb _esercizio, DatiEsercizioCardioDaDb datiEsercizioCardioDaDb) {
        try {
            return model.UpdateEsercizio(_esercizio, datiEsercizioCardioDaDb);
        }
        catch (Exception ex) {
            return -2;
        }
    }

    public int DeleteEsercizio(int _idEsercizio) {
        try {
            return model.DeleteEsercizio(_idEsercizio);
        }
        catch (Exception ex) {
            return -2;
        }
    }

    public Boolean ScambiaPosizioniAllindietro(int _idScheda, int _posizioneIniziale, int _posizioneFinale) {
        return model.ScambiaPosizioniAllIndietro(_idScheda, _posizioneIniziale, _posizioneFinale);
    }

    public Boolean ScambiaPosizioniInAvanti(int _idScheda, int _posizioneIniziale, int _posizioneFinale) {
        return model.ScambiaPosizioniInAvanti(_idScheda, _posizioneIniziale, _posizioneFinale);
    }

    public void SalvaSuperSet(HashSet<Integer> elenco) {
        model.SalvaSuperSet(elenco);
    }

    public int DeleteSuperset(int _idEsercizio) {
        try {
            return model.DeleteSuperset(_idEsercizio);
        }
        catch (Exception ex) {
            return -2;
        }
    }

    public String VerificaEserciziSelezionati(HashSet<Integer> elencoIdSelezionatiPerSuperSet) {
        try {
            String esito = "";
            if (elencoIdSelezionatiPerSuperSet != null && elencoIdSelezionatiPerSuperSet.size() > 1) {
                ArrayList<Integer> elencoOrdiniDaEsaminare = model.getElencoOrdiniEserciziPerVerificaSuccessivi(elencoIdSelezionatiPerSuperSet);
                for (int i = 0; i < elencoOrdiniDaEsaminare.size() - 1; i++) {
                    int ordineEsercizioAttuale = elencoOrdiniDaEsaminare.get(i);
                    int ordineEsercizioSuccessivo = elencoOrdiniDaEsaminare.get(i+1);
                    if (ordineEsercizioSuccessivo - ordineEsercizioAttuale > 1) {
                        esito = "Selezionare esercizi consecutivi";
                    }
                }
            }
            else {
                esito = "Selezionare almeno due esercizi";
            }
            return esito;
        }
        catch (Exception ex) {
            return "Errore nella verifica di essercizi successivi";
        }
    }
}
