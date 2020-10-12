package it.ermete.mercurio.schedapalestra;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

import java.util.ArrayList;

public class SchedeController {

    private final SchedeModel model;

    public SchedeController(Context context) {
        this.model = new SchedeModel(context);
    }

    public long InsertScheda(String _note) {
        return model.InsertScheda(_note);
    }

    public int LeggiIdSchedaAttiva() {
        Cursor cursore = null;
        try {
            model.ApriConnessionePerLettura();
            cursore = model.LeggiIdSchedaAttiva();
            if (cursore.getCount() > 0) {
                cursore.moveToFirst();
                return cursore.getInt(0);
            }
            else {
                return -1;
            }
        }
        catch (Exception ex) {
            return -1;
        }
        finally {
            if (cursore != null && !cursore.isClosed())
                cursore.close();
            model.ChiudiConnessione();
        }
    }

    public String LeggiDataInserimentoSchedaAttiva(int _idSchedaAttiva) {
        Cursor cursore = null;
        try {
            model.ApriConnessionePerLettura();
            cursore = model.LeggiDataInserimentoSchedaAttiva(_idSchedaAttiva);
            if (cursore.getCount() > 0) {
                cursore.moveToFirst();
                return cursore.getString(0);
            }
            else {
                return "";
            }
        }
        catch (Exception ex) {
            return "";
        }
        finally {
            if (cursore != null && !cursore.isClosed())
                cursore.close();
            model.ChiudiConnessione();
        }
    }

    public String LeggiNotaSchedaAttiva(int _idSchedaAttiva) {
        Cursor cursore = null;
        try {
            model.ApriConnessionePerLettura();
            cursore = model.LeggiNotaSchedaAttiva(_idSchedaAttiva);
            if (cursore.getCount() > 0) {
                cursore.moveToFirst();
                return cursore.getString(0);
            }
            else {
                return "";
            }
        }
        catch (Exception ex) {
            return "Errore descrizione";
        }
        finally {
            if (cursore != null && !cursore.isClosed())
                cursore.close();
            model.ChiudiConnessione();
        }
    }

    public ArrayList<SchedaDaDb> LeggiStoricoSchede() {
        Cursor cursore = null;
        try {
            ArrayList<SchedaDaDb> elenco = new ArrayList<>();
            model.ApriConnessionePerLettura();
            cursore = model.LeggiStoricoSchede();
            if (cursore.getCount() > 0) {
                SchedaDaDb nuovaScheda;
                while (cursore.moveToNext()) {
                    nuovaScheda = new SchedaDaDb(cursore.getInt(0),
                            Utility.GetOggettoDataDaStringa(cursore.getString(1)),
                            TextUtils.isEmpty(cursore.getString(2)) ? "Descrizione non inserita" : cursore.getString(2));
                    elenco.add(nuovaScheda);
                }
            }
            return elenco;
        }
        catch (Exception ex) {
            return null;
        }
        finally {
            if (!cursore.isClosed())
                cursore.close();
            model.ChiudiConnessione();
        }
    }

    public int DeleteScheda(int _idScheda) {
        try {
            return model.DeleteScheda(_idScheda);
        }
        catch (Exception ex) {
            return -2;
        }
    }

    public int SettaSchedaAttiva(int _idScheda) {
        try {
            return model.SettaSchedaAttiva(_idScheda);
        }
        catch (Exception ex) {
            return -2;
        }
    }

    public int UpdateNotaScheda(int _idScheda, String _nota) {
        try {
            return model.UpdateNotaScheda(_idScheda, _nota);
        }
        catch (Exception ex) {
            return -2;
        }
    }
}
