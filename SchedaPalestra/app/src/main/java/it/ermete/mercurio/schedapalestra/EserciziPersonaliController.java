package it.ermete.mercurio.schedapalestra;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class EserciziPersonaliController {

    private final EserciziPersonaliModel model;

    public EserciziPersonaliController(Context context) {
        this.model = new EserciziPersonaliModel(context);
    }

    public void LeggiEserciziPersonali(ArrayList<EsercizioDaXml> _esercizi,
                                       ArrayList<String> _nomi)
    {
        Cursor cursore = null;
        try {
            model.ApriConnessionePerLettura();
            cursore = model.LeggiEserciziPersonali();
            if (cursore.getCount() > 0) {
                EsercizioDaXml esercizioDaXml;
                while (cursore.moveToNext()) {
                    esercizioDaXml = new EsercizioDaXml(cursore.getInt(0),
                            cursore.getString(1),
                            cursore.getInt(2),
                            true);
                    _esercizi.add(esercizioDaXml);
                    _nomi.add(cursore.getString(1));
                }
            }
        }
        catch (Exception ex) {
            Log.d("LeggiEserciziPersonali", ex.getMessage());
        }
        finally {
            if (cursore != null && !cursore.isClosed())
                cursore.close();
            model.ChiudiConnessione();
        }
    }

    public ArrayList<EsercizioPersonaleDaDb> LeggiEserciziPersonali(ArrayList<GruppoMuscolareDaXml> gruppi) {
        Cursor cursore = null;
        try {
            // Creo una tabella hash per accedere velocemente al nome dell'esercizio
            HashMap<Integer, String> tabella = new HashMap<>();
            for (GruppoMuscolareDaXml gruppo : gruppi) {
                tabella.put(gruppo.getId(), gruppo.getGruppo());
            }

            ArrayList<EsercizioPersonaleDaDb> elenco = new ArrayList<>();
            model.ApriConnessionePerLettura();
            cursore = model.LeggiEserciziPersonali();
            if (cursore.getCount() > 0) {
                while (cursore.moveToNext()) {
                    EsercizioPersonaleDaDb nuovo = new EsercizioPersonaleDaDb(cursore.getInt(0),
                            cursore.getString(1),
                            cursore.getInt(2),
                            tabella.get(cursore.getInt(2)));
                    elenco.add(nuovo);
                }
            }
            return elenco;
        }
        catch (Exception ex) {
            Log.d("LeggiEserciziPersonali", ex.getMessage());
            return null;
        }
        finally {
            if (cursore != null && !cursore.isClosed())
                cursore.close();
            model.ChiudiConnessione();
        }
    }

    public ArrayList<EsercizioPersonaleDaDb> LeggiEserciziPersonaliConIdGruppo(ArrayList<GruppoMuscolareDaXml> gruppi, int idGruppo) {
        Cursor cursore = null;
        try {
            // Creo una tabella hash per accedere velocemenet al nome dell'esercizio
            HashMap<Integer, String> tabella = new HashMap<>();
            for (GruppoMuscolareDaXml gruppo : gruppi) {
                tabella.put(gruppo.getId(), gruppo.getGruppo());
            }

            ArrayList<EsercizioPersonaleDaDb> elenco = new ArrayList<>();
            model.ApriConnessionePerLettura();
            cursore = model.LeggiEserciziPersonaliConIdGruppo(idGruppo);
            if (cursore.getCount() > 0) {
                while (cursore.moveToNext()) {
                    EsercizioPersonaleDaDb nuovo = new EsercizioPersonaleDaDb(cursore.getInt(0),
                            cursore.getString(1),
                            cursore.getInt(2),
                            tabella.get(cursore.getInt(2)));
                    elenco.add(nuovo);
                }
            }
            return elenco;
        }
        catch (Exception ex) {
            Log.d("LeggiEserciziPersonali", ex.getMessage());
            return null;
        }
        finally {
            if (cursore != null && !cursore.isClosed())
                cursore.close();
            model.ChiudiConnessione();
        }
    }

    public long InsertEsercizioPersonale(EsercizioPersonaleDaDb _esercizio) {
        try {
            return model.InsertEsercizioPersonale(_esercizio);
        }
        catch (Exception ex) {
            return -2;
        }
    }

    public long UpdateEsercizioPersonale(EsercizioPersonaleDaDb _esercizio) {
        try {
            return model.UpdateEsercizioPersonale(_esercizio);
        }
        catch (Exception ex) {
            return -2;
        }
    }

    public int DeleteEsercizioPersonale(int _idEsercizio) {
        try {
            return model.DeleteEsercizioPersonale(_idEsercizio);
        }
        catch (Exception ex) {
            return -2;
        }
    }

    public boolean VerificaSePossibiliUpdateDelete(EsercizioPersonaleDaDb _esercizio) {
        try {
            return model.VerificaSePossibiliUpdateDelete(_esercizio);
        }
        catch (Exception ex) {
            return false;
        }
    }
}
