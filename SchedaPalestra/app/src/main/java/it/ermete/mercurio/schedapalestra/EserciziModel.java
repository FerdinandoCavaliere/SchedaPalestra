package it.ermete.mercurio.schedapalestra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashSet;

public class EserciziModel {

    protected SQLiteDatabase db;
    private DbHelper dbHelper;
    private final Context context;

    public EserciziModel(Context _context) {
        this.context = _context;
        dbHelper = DbHelper.getInstanzaDbHelper(this.context);
    }

    public void ApriConnessionePerLettura() throws SQLException {
        if (dbHelper == null)
            dbHelper = DbHelper.getInstanzaDbHelper(context);
        db = dbHelper.getReadableDatabase();
    }

    public  void ApriConnessionePerScrittura() throws SQLException {
        if (dbHelper == null)
            dbHelper = DbHelper.getInstanzaDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void ChiudiConnessione() throws SQLException {
        db.close();
    }

    public Cursor LeggiEserciziByIdScheda(int _idScheda) {
        try {
            String[] parametri = {Integer.toString(_idScheda)};
            String query = "SELECT " +
                            DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_id_TABELLA_ESERCIZI + ", " +
                            DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_idGruppoMuscolare_TABELLA_ESERCIZI + ", " +
                            DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_nomeGruppoMuscolare_TABELLA_ESERCIZI + ", " +
                            DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_idEsercizio_TABELLA_ESERCIZI + ", " +
                            DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + ", " +
                            DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_serie_TABELLA_ESERCIZI + ", " +
                            DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_recupero_TABELLA_ESERCIZI + ", " +
                            DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + ", " +
                            DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_note_TABELLA_ESERCIZI + ", " +
                            DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_giorno_TABELLA_ESERCIZI + ", " +
                            DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_isPiramidale_TABELLA_ESERCIZI + ", " +
                            DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_bgColor_TABELLA_ESERCIZI + ", " +
                            DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_UnitaDiMisura_TABELLA_ESERCIZI + ", " +
                            DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_Routine_TABELLA_ESERCIZI + ", " +
                            DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_massimale_TABELLA_ESERCIZI + " " +
                           "FROM " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                           "WHERE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_ESERCIZI + " = ? " +
                           "ORDER BY " +
                                DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI;
            return db.rawQuery(query, parametri);
        }
        catch (Exception ex) {
            return null;
        }
    }

    public Cursor LeggiEserciziByIdSchedaConIdGruppo(int _idScheda, int _idGruppo) {
        try {
            String[] parametri = {Integer.toString(_idScheda), Integer.toString(_idGruppo)};
            String query = "SELECT " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_id_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_idGruppoMuscolare_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_nomeGruppoMuscolare_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_idEsercizio_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_serie_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_recupero_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_note_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_giorno_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_isPiramidale_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_bgColor_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_UnitaDiMisura_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_Routine_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_massimale_TABELLA_ESERCIZI + " " +
                    "FROM " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "WHERE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_ESERCIZI + " = ? " +
                    "AND " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_idGruppoMuscolare_TABELLA_ESERCIZI + " = ? " +
                    "ORDER BY " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI;
            return db.rawQuery(query, parametri);
        }
        catch (Exception ex) {
            return null;
        }
    }

    public Cursor LeggiEserciziByIdSchedaConGruppi(int _idScheda, HashSet<String> nomiGruppi) {
        try {
            StringBuilder nomiGruppiConcatenati = new StringBuilder();
            for (String singoloGruppo : nomiGruppi) {
                nomiGruppiConcatenati.append("'").append(singoloGruppo).append("',");
            }
            String nomiGruppiDefinitivi = nomiGruppiConcatenati.substring(0,nomiGruppiConcatenati.length() -1);
            String[] parametri = {Integer.toString(_idScheda)};
            String query = "SELECT " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_id_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_idGruppoMuscolare_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_nomeGruppoMuscolare_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_idEsercizio_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_serie_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_recupero_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_note_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_giorno_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_isPiramidale_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_bgColor_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_UnitaDiMisura_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_Routine_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_massimale_TABELLA_ESERCIZI + " " +
                    "FROM " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "WHERE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_ESERCIZI + " = ? " +
                    "AND " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_nomeGruppoMuscolare_TABELLA_ESERCIZI + " IN ( " + nomiGruppiDefinitivi + " ) " +
                    "ORDER BY " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI;
            return db.rawQuery(query, parametri);
        }
        catch (Exception ex) {
            return null;
        }
    }

    public Cursor LeggiEserciziByIdSchedaConGiorno(int _idScheda, String _giorno) {
        try {
            String[] parametri = {Integer.toString(_idScheda), _giorno};
            String query = "SELECT " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_id_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_idGruppoMuscolare_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_nomeGruppoMuscolare_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_idEsercizio_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_serie_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_recupero_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_note_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_giorno_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_isPiramidale_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_bgColor_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_UnitaDiMisura_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_Routine_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_massimale_TABELLA_ESERCIZI + " " +
                    "FROM " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "WHERE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_ESERCIZI + " = ? AND " +
                    "( " +  DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_giorno_TABELLA_ESERCIZI + " = ? OR " +
                            DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_giorno_TABELLA_ESERCIZI + " IS NULL OR " +
                            DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_giorno_TABELLA_ESERCIZI + " = '' " +
                    ")" +
                    "ORDER BY " +
                            DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI;
            return db.rawQuery(query, parametri);
        }
        catch (Exception ex) {
            return null;
        }
    }

    public Cursor LeggiEserciziByIdSchedaConRoutine(int _idScheda, String _routine) {
        try {
            String[] parametri = {Integer.toString(_idScheda), _routine};
            String query = "SELECT " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_id_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_idGruppoMuscolare_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_nomeGruppoMuscolare_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_idEsercizio_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_serie_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_recupero_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_note_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_giorno_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_isPiramidale_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_bgColor_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_UnitaDiMisura_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_Routine_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_massimale_TABELLA_ESERCIZI + " " +
                    "FROM " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "WHERE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_ESERCIZI + " = ? AND " +
                    "( " +  DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_Routine_TABELLA_ESERCIZI + " = ? OR " +
                            DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_Routine_TABELLA_ESERCIZI + " IS NULL OR " +
                            DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_Routine_TABELLA_ESERCIZI + " = '' " +
                    ")" +
                    "ORDER BY " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI;
            return db.rawQuery(query, parametri);
        }
        catch (Exception ex) {
            return null;
        }
    }

    /*
     * Insert Esercizio non di cardio
     * */
    public long InsertEsercizio(EsercizioDaDb _esercizio, ArrayList<RipetizioniPesoDaDb> ripetizioniPeso) {
        ApriConnessionePerScrittura();
        db.beginTransaction();
        try {
            // Reperisco il massimo ordine della scheda
            String[] valoriWhere = {Integer.toString(_esercizio.getIdScheda_FK())};
            String selectMaxOrdine = "SELECT MAX( " + DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + " ) " +
                                     "FROM " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                                     "WHERE " + DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_ESERCIZI + " = ? ";
            Cursor cursore = db.rawQuery(selectMaxOrdine, valoriWhere);
            int ordineDaInserire = 0;
            if (cursore.getCount() > 0) {
                cursore.moveToFirst();
                String ordineS = cursore.getString(0);
                if (!TextUtils.isEmpty(ordineS)) {
                    ordineDaInserire = Integer.parseInt(ordineS) + 1;
                }
            }
            _esercizio.setOrdine(ordineDaInserire);

            // Insert in ESERCIZI
            ContentValues valoriEsercizio = new ContentValues();
            valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_ESERCIZI, _esercizio.getIdScheda_FK());
            valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_idGruppoMuscolare_TABELLA_ESERCIZI, _esercizio.getIdGruppoMuscolare());
            valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_nomeGruppoMuscolare_TABELLA_ESERCIZI, _esercizio.getNomeGruppoMuscolare());
            valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_idEsercizio_TABELLA_ESERCIZI, _esercizio.getIdEsercizio());
            valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI, _esercizio.getNomeEsercizio());
            valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_serie_TABELLA_ESERCIZI, _esercizio.getSerie());
            valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_recupero_TABELLA_ESERCIZI, _esercizio.getRecupero());
            valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI, _esercizio.getOrdine());
            valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_note_TABELLA_ESERCIZI, _esercizio.getNote());
            valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_giorno_TABELLA_ESERCIZI, _esercizio.getGiorno());
            valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_isPiramidale_TABELLA_ESERCIZI, _esercizio.getIsPiramidale());
            valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_bgColor_TABELLA_ESERCIZI, _esercizio.getBgColor());
            valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_UnitaDiMisura_TABELLA_ESERCIZI, _esercizio.getUnitaDiMisura());
            valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_Routine_TABELLA_ESERCIZI, _esercizio.getRoutine());
            valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_massimale_TABELLA_ESERCIZI, _esercizio.getMassimale());
            long nuovoIdEsercizio = db.insert(DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI, null, valoriEsercizio);
            if (nuovoIdEsercizio > -1) {
                // Insert in RIPETIZIONI_PESI
                for (RipetizioniPesoDaDb singolo : ripetizioniPeso) {
                    ContentValues valoriRipetizioniPeso = new ContentValues();
                    valoriRipetizioniPeso.put(DbNomiTabelleCampi.CAMPO_idEsercizio_FK_TABELLA_RIPETIZIONI_PESI, nuovoIdEsercizio);
                    valoriRipetizioniPeso.put(DbNomiTabelleCampi.CAMPO_ripetizioni_TABELLA_RIPETIZIONI_PESI, singolo.getRipetizioni());
                    valoriRipetizioniPeso.put(DbNomiTabelleCampi.CAMPO_peso_TABELLA_RIPETIZIONI_PESI, singolo.getPeso());
                    valoriRipetizioniPeso.put(DbNomiTabelleCampi.CAMPO_recupero_TABELLA_RIPETIZIONI_PESI, singolo.getRecupero());
                    valoriRipetizioniPeso.put(DbNomiTabelleCampi.CAMPO_percentuale_TABELLA_RIPETIZIONI_PESI, singolo.getPercentuale());
                    valoriRipetizioniPeso.put(DbNomiTabelleCampi.CAMPO_rpe_TABELLA_RIPETIZIONI_PESI, singolo.getRpe());
                    long nuovoIdRipetizioniPesi = db.insert(DbNomiTabelleCampi.NOME_TABELLA_RIPETIZIONI_PESI, null, valoriRipetizioniPeso);
                }
            }

            if (!cursore.isClosed())
                cursore.close();
            db.setTransactionSuccessful();
            return nuovoIdEsercizio;
        }
        catch (Exception ex) {
            return -2;
        }
        finally {
            db.endTransaction();
            this.ChiudiConnessione();
        }
    }

    /*
    * Insert esercizio di cardio
    * */
    public long InsertEsercizio(EsercizioDaDb _esercizio, DatiEsercizioCardioDaDb datiEsercizioCardioDaDb) {
        ApriConnessionePerScrittura();
        db.beginTransaction();
        try {
            // Reperisco il massimo ordine della scheda
            String[] valoriWhere = {Integer.toString(_esercizio.getIdScheda_FK())};
            String selectMaxOrdine = "SELECT MAX( " + DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + " ) " +
                    "FROM " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_ESERCIZI + " = ? ";
            Cursor cursore = db.rawQuery(selectMaxOrdine, valoriWhere);
            int ordineDaInserire = 0;
            if (cursore.getCount() > 0) {
                cursore.moveToFirst();
                String ordineS = cursore.getString(0);
                if (!TextUtils.isEmpty(ordineS)) {
                    ordineDaInserire = Integer.parseInt(ordineS) + 1;
                }
            }
            _esercizio.setOrdine(ordineDaInserire);

            // Insert in ESERCIZI
            ContentValues valoriEsercizio = new ContentValues();
            valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_ESERCIZI, _esercizio.getIdScheda_FK());
            valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_idGruppoMuscolare_TABELLA_ESERCIZI, _esercizio.getIdGruppoMuscolare());
            valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_nomeGruppoMuscolare_TABELLA_ESERCIZI, _esercizio.getNomeGruppoMuscolare());
            valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_idEsercizio_TABELLA_ESERCIZI, _esercizio.getIdEsercizio());
            valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI, _esercizio.getNomeEsercizio());
            valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_serie_TABELLA_ESERCIZI, _esercizio.getSerie());
            valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_recupero_TABELLA_ESERCIZI, _esercizio.getRecupero());
            valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI, _esercizio.getOrdine());
            valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_note_TABELLA_ESERCIZI, _esercizio.getNote());
            valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_giorno_TABELLA_ESERCIZI, _esercizio.getGiorno());
            valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_isPiramidale_TABELLA_ESERCIZI, _esercizio.getIsPiramidale());
            valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_bgColor_TABELLA_ESERCIZI, _esercizio.getBgColor());
            valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_UnitaDiMisura_TABELLA_ESERCIZI, _esercizio.getUnitaDiMisura());
            valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_Routine_TABELLA_ESERCIZI, _esercizio.getRoutine());
            valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_massimale_TABELLA_ESERCIZI, _esercizio.getMassimale());
            long nuovoIdEsercizio = db.insert(DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI, null, valoriEsercizio);
            if (nuovoIdEsercizio > -1) {

                // Insert in DATI_ESERCIZIO_CARDIO
                ContentValues valoriDatiEsercizioCardio = new ContentValues();
                valoriDatiEsercizioCardio.put(DbNomiTabelleCampi.CAMPO_idEsercizio_FK_TABELLA_DATI_ESERCIZIO_CARDIO, nuovoIdEsercizio);
                valoriDatiEsercizioCardio.put(DbNomiTabelleCampi.CAMPO_programma_TABELLA_DATI_ESERCIZIO_CARDIO, datiEsercizioCardioDaDb.getProgramma());
                valoriDatiEsercizioCardio.put(DbNomiTabelleCampi.CAMPO_intensita_TABELLA_DATI_ESERCIZIO_CARDIO, datiEsercizioCardioDaDb.getIntensita());
                valoriDatiEsercizioCardio.put(DbNomiTabelleCampi.CAMPO_pendenza_TABELLA_DATI_ESERCIZIO_CARDIO, datiEsercizioCardioDaDb.getPendenza());
                valoriDatiEsercizioCardio.put(DbNomiTabelleCampi.CAMPO_velocita_TABELLA_DATI_ESERCIZIO_CARDIO, datiEsercizioCardioDaDb.getVelocita());
                valoriDatiEsercizioCardio.put(DbNomiTabelleCampi.CAMPO_tempo_TABELLA_DATI_ESERCIZIO_CARDIO, datiEsercizioCardioDaDb.getTempo());
                long nuovoIdDatiEsercizioCardioDaDb = db.insert(DbNomiTabelleCampi.NOME_TABELLA_DATI_ESERCIZIO_CARDIO, null, valoriDatiEsercizioCardio);
            }

            if (!cursore.isClosed())
                cursore.close();
            db.setTransactionSuccessful();
            return nuovoIdEsercizio;
        }
        catch (Exception ex) {
            return -2;
        }
        finally {
            db.endTransaction();
            this.ChiudiConnessione();
        }
    }

    /*
    * Update esercizio non di cardio
    * */
    public int UpdateEsercizio(EsercizioDaDb _esercizio, ArrayList<RipetizioniPesoDaDb> _ripetizioniPeso) {
        ApriConnessionePerScrittura();
        db.beginTransaction();
        try {
            String[] valoriWhere = {Integer.toString(_esercizio.getId())};

            // Delete in RIPETIZIONI_PESI
            int numeroRigheCancellate = db.delete(DbNomiTabelleCampi.NOME_TABELLA_RIPETIZIONI_PESI,
                                        DbNomiTabelleCampi.CAMPO_idEsercizio_FK_TABELLA_RIPETIZIONI_PESI + " = ?",
                                        valoriWhere);

            // Insert in RIPETIZIONI_PESI
            for (RipetizioniPesoDaDb singolo : _ripetizioniPeso) {
                ContentValues valoriRipetizioniPeso = new ContentValues();
                valoriRipetizioniPeso.put(DbNomiTabelleCampi.CAMPO_idEsercizio_FK_TABELLA_RIPETIZIONI_PESI, _esercizio.getId());
                valoriRipetizioniPeso.put(DbNomiTabelleCampi.CAMPO_ripetizioni_TABELLA_RIPETIZIONI_PESI, singolo.getRipetizioni());
                valoriRipetizioniPeso.put(DbNomiTabelleCampi.CAMPO_peso_TABELLA_RIPETIZIONI_PESI, singolo.getPeso());
                valoriRipetizioniPeso.put(DbNomiTabelleCampi.CAMPO_recupero_TABELLA_RIPETIZIONI_PESI, singolo.getRecupero());
                valoriRipetizioniPeso.put(DbNomiTabelleCampi.CAMPO_percentuale_TABELLA_RIPETIZIONI_PESI, singolo.getPercentuale());
                valoriRipetizioniPeso.put(DbNomiTabelleCampi.CAMPO_rpe_TABELLA_RIPETIZIONI_PESI, singolo.getRpe());
                long nuovoIdRipetizioniPesi = db.insert(DbNomiTabelleCampi.NOME_TABELLA_RIPETIZIONI_PESI, null, valoriRipetizioniPeso);
            }

            // Update in ESERCIZI
            ContentValues valori = new ContentValues();
            valori.put(DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_ESERCIZI, _esercizio.getIdScheda_FK());
            valori.put(DbNomiTabelleCampi.CAMPO_idGruppoMuscolare_TABELLA_ESERCIZI, _esercizio.getIdGruppoMuscolare());
            valori.put(DbNomiTabelleCampi.CAMPO_nomeGruppoMuscolare_TABELLA_ESERCIZI, _esercizio.getNomeGruppoMuscolare());
            valori.put(DbNomiTabelleCampi.CAMPO_idEsercizio_TABELLA_ESERCIZI, _esercizio.getIdEsercizio());
            valori.put(DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI, _esercizio.getNomeEsercizio());
            valori.put(DbNomiTabelleCampi.CAMPO_serie_TABELLA_ESERCIZI, _esercizio.getSerie());
            valori.put(DbNomiTabelleCampi.CAMPO_recupero_TABELLA_ESERCIZI, _esercizio.getRecupero());
            valori.put(DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI, _esercizio.getOrdine());
            valori.put(DbNomiTabelleCampi.CAMPO_note_TABELLA_ESERCIZI, _esercizio.getNote());
            valori.put(DbNomiTabelleCampi.CAMPO_giorno_TABELLA_ESERCIZI, _esercizio.getGiorno());
            valori.put(DbNomiTabelleCampi.CAMPO_isPiramidale_TABELLA_ESERCIZI, _esercizio.getIsPiramidale());
            valori.put(DbNomiTabelleCampi.CAMPO_bgColor_TABELLA_ESERCIZI, _esercizio.getBgColor());
            valori.put(DbNomiTabelleCampi.CAMPO_UnitaDiMisura_TABELLA_ESERCIZI, _esercizio.getUnitaDiMisura());
            valori.put(DbNomiTabelleCampi.CAMPO_Routine_TABELLA_ESERCIZI, _esercizio.getRoutine());
            valori.put(DbNomiTabelleCampi.CAMPO_massimale_TABELLA_ESERCIZI, _esercizio.getMassimale());
            int numeroRigheCoinvolte = db.update(DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI,
                    valori,
                    DbNomiTabelleCampi.CAMPO_id_TABELLA_ESERCIZI + " = ?",
                    valoriWhere);

            db.setTransactionSuccessful();
            return numeroRigheCoinvolte;
        }
        catch (Exception ex) {
            return -2;
        }
        finally {
            db.endTransaction();
            this.ChiudiConnessione();
        }
    }

    /*
    * Update esercizio cardio
    * */
    public int UpdateEsercizio(EsercizioDaDb _esercizio, DatiEsercizioCardioDaDb datiEsercizioCardioDaDb) {
        ApriConnessionePerScrittura();
        db.beginTransaction();
        try {
            String[] valoriWhere = {Integer.toString(_esercizio.getId())};

            // Delete in DATI_ESERCIZIO_CARDIO
            int numeroRigheCancellate = db.delete(DbNomiTabelleCampi.NOME_TABELLA_DATI_ESERCIZIO_CARDIO,
                    DbNomiTabelleCampi.CAMPO_idEsercizio_FK_TABELLA_DATI_ESERCIZIO_CARDIO + " = ?",
                    valoriWhere);

            // Insert in DATI_ESERCIZIO_CARDIO
            ContentValues valoriDatiEsercizioCardio = new ContentValues();
            valoriDatiEsercizioCardio.put(DbNomiTabelleCampi.CAMPO_idEsercizio_FK_TABELLA_DATI_ESERCIZIO_CARDIO, _esercizio.getId());
            valoriDatiEsercizioCardio.put(DbNomiTabelleCampi.CAMPO_programma_TABELLA_DATI_ESERCIZIO_CARDIO, datiEsercizioCardioDaDb.getProgramma());
            valoriDatiEsercizioCardio.put(DbNomiTabelleCampi.CAMPO_intensita_TABELLA_DATI_ESERCIZIO_CARDIO, datiEsercizioCardioDaDb.getIntensita());
            valoriDatiEsercizioCardio.put(DbNomiTabelleCampi.CAMPO_pendenza_TABELLA_DATI_ESERCIZIO_CARDIO, datiEsercizioCardioDaDb.getPendenza());
            valoriDatiEsercizioCardio.put(DbNomiTabelleCampi.CAMPO_velocita_TABELLA_DATI_ESERCIZIO_CARDIO, datiEsercizioCardioDaDb.getVelocita());
            valoriDatiEsercizioCardio.put(DbNomiTabelleCampi.CAMPO_tempo_TABELLA_DATI_ESERCIZIO_CARDIO, datiEsercizioCardioDaDb.getTempo());
            long nuovoIdDatiEsercizioCardioDaDb = db.insert(DbNomiTabelleCampi.NOME_TABELLA_DATI_ESERCIZIO_CARDIO, null, valoriDatiEsercizioCardio);

            // Update in ESERCIZI
            ContentValues valori = new ContentValues();
            valori.put(DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_ESERCIZI, _esercizio.getIdScheda_FK());
            valori.put(DbNomiTabelleCampi.CAMPO_idGruppoMuscolare_TABELLA_ESERCIZI, _esercizio.getIdGruppoMuscolare());
            valori.put(DbNomiTabelleCampi.CAMPO_nomeGruppoMuscolare_TABELLA_ESERCIZI, _esercizio.getNomeGruppoMuscolare());
            valori.put(DbNomiTabelleCampi.CAMPO_idEsercizio_TABELLA_ESERCIZI, _esercizio.getIdEsercizio());
            valori.put(DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI, _esercizio.getNomeEsercizio());
            valori.put(DbNomiTabelleCampi.CAMPO_serie_TABELLA_ESERCIZI, _esercizio.getSerie());
            valori.put(DbNomiTabelleCampi.CAMPO_recupero_TABELLA_ESERCIZI, _esercizio.getRecupero());
            valori.put(DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI, _esercizio.getOrdine());
            valori.put(DbNomiTabelleCampi.CAMPO_note_TABELLA_ESERCIZI, _esercizio.getNote());
            valori.put(DbNomiTabelleCampi.CAMPO_giorno_TABELLA_ESERCIZI, _esercizio.getGiorno());
            valori.put(DbNomiTabelleCampi.CAMPO_isPiramidale_TABELLA_ESERCIZI, _esercizio.getIsPiramidale());
            valori.put(DbNomiTabelleCampi.CAMPO_bgColor_TABELLA_ESERCIZI, _esercizio.getBgColor());
            valori.put(DbNomiTabelleCampi.CAMPO_UnitaDiMisura_TABELLA_ESERCIZI, _esercizio.getUnitaDiMisura());
            valori.put(DbNomiTabelleCampi.CAMPO_Routine_TABELLA_ESERCIZI, _esercizio.getRoutine());
            valori.put(DbNomiTabelleCampi.CAMPO_massimale_TABELLA_ESERCIZI, _esercizio.getMassimale());
            int numeroRigheCoinvolte = db.update(DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI,
                    valori,
                    DbNomiTabelleCampi.CAMPO_id_TABELLA_ESERCIZI + " = ?",
                    valoriWhere);

            db.setTransactionSuccessful();
            return numeroRigheCoinvolte;
        }
        catch (Exception ex) {
            return -2;
        }
        finally {
            db.endTransaction();
            this.ChiudiConnessione();
        }
    }

    /*
    * Delete che vale sia per esercizi cardio che non cardio
    * */
    public int DeleteEsercizio(int _idEsercizio) {
        this.ApriConnessionePerScrittura();
        db.beginTransaction();
        try {
            String[] valori = {Integer.toString(_idEsercizio)};
            int righeCancellateInSuperset = 0;
            int numeroRigheCancellate = 0;

            // Reperisco il guid per eliminare la super set
            String selectGuid = "SELECT " +
                    DbNomiTabelleCampi.NOME_TABELLA_SUPERSET + "." + DbNomiTabelleCampi.CAMPO_guid_TABELLA_SUPERSET + " " +
                    "FROM " + DbNomiTabelleCampi.NOME_TABELLA_SUPERSET + " " +
                    "WHERE " +
                    DbNomiTabelleCampi.NOME_TABELLA_SUPERSET + "." + DbNomiTabelleCampi.CAMPO_idEsercizio_FK_TABELLA_SUPERSET + " = ? ";
            Cursor cursoreSuperset = db.rawQuery(selectGuid, valori);
            if (cursoreSuperset.getCount() > 0) {
                cursoreSuperset.moveToFirst();
                String[] valoriGuid = {cursoreSuperset.getString(0)};

                // Elimino l'intera superset
                righeCancellateInSuperset = db.delete(DbNomiTabelleCampi.NOME_TABELLA_SUPERSET,
                        DbNomiTabelleCampi.CAMPO_guid_TABELLA_SUPERSET + " = ? ", valoriGuid);
            }

            // Reperisco l'esercizio che sto per cancellare. Mi serve per leggere il suo ordine in modo
            // da poter eseguire lo shift in avanti nel modo corretto
            String query = "SELECT " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_ESERCIZI + " " +
                    "FROM " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "WHERE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_id_TABELLA_ESERCIZI + " = ? ";
            Cursor cursore = db.rawQuery(query, valori);
            cursore.moveToFirst();
            int ordineLetto = cursore.getInt(0);
            int idSchedaLetto = cursore.getInt(1);

            // Delete in RIPETIZIONI_PESI
            numeroRigheCancellate = db.delete(DbNomiTabelleCampi.NOME_TABELLA_RIPETIZIONI_PESI,
                    DbNomiTabelleCampi.CAMPO_idEsercizio_FK_TABELLA_RIPETIZIONI_PESI + " = ?",
                    valori);

            // Delete in DATI_ESERCIZIO_CARDIO
            numeroRigheCancellate = db.delete(DbNomiTabelleCampi.NOME_TABELLA_DATI_ESERCIZIO_CARDIO,
                    DbNomiTabelleCampi.CAMPO_idEsercizio_FK_TABELLA_DATI_ESERCIZIO_CARDIO + " = ?",
                    valori);

            // Delete in ESERCIZI
            int righeCancellate = db.delete(DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI,
                    DbNomiTabelleCampi.CAMPO_id_TABELLA_ESERCIZI + " = ?",
                    valori);

            // shift in avanti di tutti gli esercizi successivi a quello cancellato
            String[] valoriShift = {Integer.toString(ordineLetto), Integer.toString(idSchedaLetto)};
            String stringShift = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + " " +
                    "= " + DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + " - 1 " +
                    "WHERE " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + " > ? AND " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_ESERCIZI + " = ? ";
            db.execSQL(stringShift, valoriShift);

            if (!cursore.isClosed())
                cursore.close();

            if (!cursoreSuperset.isClosed())
                cursoreSuperset.close();

            db.setTransactionSuccessful();

            // In questo modo se il valore di ritorno è 1, ho cancellato un esercizio non presente in superset.
            // Se il valore è > 1 allora ho cancellato un esercizio + n righe nella tabella delle superset
            return righeCancellate + righeCancellateInSuperset;
        }
        catch (Exception ex) {
            return -2;
        }
        finally {
            db.endTransaction();
            this.ChiudiConnessione();
        }
    }

    public int DeleteSuperset(int _idEsercizio) {
        this.ApriConnessionePerScrittura();
        db.beginTransaction();
        try {
            // Reperisco il guid della superset di cui faparte l'esercizio
            String[] valori = {Integer.toString(_idEsercizio)};
            String selectGuid = "SELECT " +
                                          DbNomiTabelleCampi.NOME_TABELLA_SUPERSET + "." + DbNomiTabelleCampi.CAMPO_guid_TABELLA_SUPERSET + " " +
                                "FROM " + DbNomiTabelleCampi.NOME_TABELLA_SUPERSET + " " +
                                "WHERE " +
                                          DbNomiTabelleCampi.NOME_TABELLA_SUPERSET + "." + DbNomiTabelleCampi.CAMPO_idEsercizio_FK_TABELLA_SUPERSET + " = ? ";
            Cursor cursore = db.rawQuery(selectGuid, valori);
            cursore.moveToFirst();
            String[] valoriGuid = {cursore.getString(0)};

            // Elimino l'intera superset
            int righeCancellate = db.delete(DbNomiTabelleCampi.NOME_TABELLA_SUPERSET,
                    DbNomiTabelleCampi.CAMPO_guid_TABELLA_SUPERSET + " = ? ", valoriGuid);

            if (!cursore.isClosed())
                cursore.close();
            db.setTransactionSuccessful();
            return righeCancellate;
        }
        catch (Exception ex) {
            return -2;
        }
        finally {
            db.endTransaction();
            this.ChiudiConnessione();
        }
    }

    public Cursor LeggiRipetizioniPesoByIdEsercizio(int _idEsercizio)
    {
        try {
            String[] parametri = {Integer.toString(_idEsercizio)};
            String query = "SELECT " +
                    DbNomiTabelleCampi.NOME_TABELLA_RIPETIZIONI_PESI + "." + DbNomiTabelleCampi.CAMPO_id_TABELLA_RIPETIZIONI_PESI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_RIPETIZIONI_PESI + "." + DbNomiTabelleCampi.CAMPO_idEsercizio_FK_TABELLA_RIPETIZIONI_PESI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_RIPETIZIONI_PESI + "." + DbNomiTabelleCampi.CAMPO_ripetizioni_TABELLA_RIPETIZIONI_PESI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_RIPETIZIONI_PESI + "." + DbNomiTabelleCampi.CAMPO_peso_TABELLA_RIPETIZIONI_PESI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_RIPETIZIONI_PESI + "." + DbNomiTabelleCampi.CAMPO_recupero_TABELLA_RIPETIZIONI_PESI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_RIPETIZIONI_PESI + "." + DbNomiTabelleCampi.CAMPO_percentuale_TABELLA_RIPETIZIONI_PESI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_RIPETIZIONI_PESI + "." + DbNomiTabelleCampi.CAMPO_rpe_TABELLA_RIPETIZIONI_PESI + " " +
                    "FROM " + DbNomiTabelleCampi.NOME_TABELLA_RIPETIZIONI_PESI + " " +
                    "WHERE " + DbNomiTabelleCampi.NOME_TABELLA_RIPETIZIONI_PESI + "." + DbNomiTabelleCampi.CAMPO_idEsercizio_FK_TABELLA_RIPETIZIONI_PESI + " = ? " +
                    "ORDER BY " +
                    DbNomiTabelleCampi.NOME_TABELLA_RIPETIZIONI_PESI + "." + DbNomiTabelleCampi.CAMPO_id_TABELLA_RIPETIZIONI_PESI;
            return db.rawQuery(query, parametri);
        }
        catch (Exception ex) {
            return null;
        }
    }

    public Cursor LeggiDatiEsercizioCardioByIdEsercizio(int _idEsercizio) {
        try {
            String[] parametri = {Integer.toString(_idEsercizio)};
            String query = "SELECT " +
                    DbNomiTabelleCampi.NOME_TABELLA_DATI_ESERCIZIO_CARDIO + "." + DbNomiTabelleCampi.CAMPO_id_TABELLA_DATI_ESERCIZIO_CARDIO + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_DATI_ESERCIZIO_CARDIO + "." + DbNomiTabelleCampi.CAMPO_idEsercizio_FK_TABELLA_DATI_ESERCIZIO_CARDIO + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_DATI_ESERCIZIO_CARDIO + "." + DbNomiTabelleCampi.CAMPO_programma_TABELLA_DATI_ESERCIZIO_CARDIO + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_DATI_ESERCIZIO_CARDIO + "." + DbNomiTabelleCampi.CAMPO_intensita_TABELLA_DATI_ESERCIZIO_CARDIO + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_DATI_ESERCIZIO_CARDIO + "." + DbNomiTabelleCampi.CAMPO_pendenza_TABELLA_DATI_ESERCIZIO_CARDIO + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_DATI_ESERCIZIO_CARDIO + "." + DbNomiTabelleCampi.CAMPO_velocita_TABELLA_DATI_ESERCIZIO_CARDIO + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_DATI_ESERCIZIO_CARDIO + "." + DbNomiTabelleCampi.CAMPO_tempo_TABELLA_DATI_ESERCIZIO_CARDIO + " " +
                    "FROM " + DbNomiTabelleCampi.NOME_TABELLA_DATI_ESERCIZIO_CARDIO + " " +
                    "WHERE " + DbNomiTabelleCampi.NOME_TABELLA_DATI_ESERCIZIO_CARDIO + "." + DbNomiTabelleCampi.CAMPO_idEsercizio_FK_TABELLA_DATI_ESERCIZIO_CARDIO + " = ? ";
            return db.rawQuery(query, parametri);
        }
        catch (Exception ex) {
            return null;
        }
    }

    public Cursor LeggiSuperSetByIdEsercizio(int _idEsercizio) {
        try {
            String[] parametri = {Integer.toString(_idEsercizio)};
            String query = "SELECT " +
                                    DbNomiTabelleCampi.NOME_TABELLA_SUPERSET + "." + DbNomiTabelleCampi.CAMPO_id_TABELLA_SUPERSET + ", " +
                                    DbNomiTabelleCampi.NOME_TABELLA_SUPERSET + "." + DbNomiTabelleCampi.CAMPO_guid_TABELLA_SUPERSET + ", " +
                                    DbNomiTabelleCampi.NOME_TABELLA_SUPERSET + "." + DbNomiTabelleCampi.CAMPO_idEsercizio_FK_TABELLA_SUPERSET + ", " +
                                    DbNomiTabelleCampi.NOME_TABELLA_SUPERSET + "." + DbNomiTabelleCampi.CAMPO_isPrimo_TABELLA_SUPERSET + ", " +
                                    DbNomiTabelleCampi.NOME_TABELLA_SUPERSET + "." + DbNomiTabelleCampi.CAMPO_isUltimo_TABELLA_SUPERSET + " " +
                            "FROM " + DbNomiTabelleCampi.NOME_TABELLA_SUPERSET + " " +
                            "WHERE " + DbNomiTabelleCampi.NOME_TABELLA_SUPERSET + "." + DbNomiTabelleCampi.CAMPO_idEsercizio_FK_TABELLA_SUPERSET + " = ? " +
                            "ORDER BY " +
                                    DbNomiTabelleCampi.NOME_TABELLA_SUPERSET + "." + DbNomiTabelleCampi.CAMPO_id_TABELLA_SUPERSET;
            return db.rawQuery(query, parametri);
        }
        catch (Exception ex) {
            return null;
        }
    }

    public Boolean ScambiaPosizioniInAvanti(int _idScheda, int _posizioneIniziale, int _posizioneFinale) {
        this.ApriConnessionePerScrittura();
        db.beginTransaction();
        try {
            Boolean ritorno = false; // Il valore di ritorno viene posto a true se serve che il chiamante ricarica la listview

            // Reperisco gli id degli esercizi coinvolti in base all'ordine
            String query = "SELECT " + DbNomiTabelleCampi.CAMPO_id_TABELLA_ESERCIZI + " " +
                    "FROM " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_ESERCIZI + " = " + Integer.toString(_idScheda) + " " +
                    "AND " + DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + " " +
                    "IN ( " + Integer.toString(_posizioneIniziale) + ", " + Integer.toString(_posizioneFinale) + " )";
            Cursor cursoreIdEsercizi = db.rawQuery(query, null);
            while (cursoreIdEsercizi.moveToNext()) {
                String[] valori = {Integer.toString(cursoreIdEsercizi.getInt(0))};
                String selectGuid = "SELECT " +
                        DbNomiTabelleCampi.NOME_TABELLA_SUPERSET + "." + DbNomiTabelleCampi.CAMPO_guid_TABELLA_SUPERSET + " " +
                        "FROM " + DbNomiTabelleCampi.NOME_TABELLA_SUPERSET + " " +
                        "WHERE " +
                        DbNomiTabelleCampi.NOME_TABELLA_SUPERSET + "." + DbNomiTabelleCampi.CAMPO_idEsercizio_FK_TABELLA_SUPERSET + " = ? ";
                Cursor cursoreGuid = db.rawQuery(selectGuid, valori);
                if (cursoreGuid.getCount() > 0) {
                    cursoreGuid.moveToFirst();
                    String[] valoriGuid = {cursoreGuid.getString(0)};

                    // Elimino l'intera superset
                    int righeCancellate = db.delete(DbNomiTabelleCampi.NOME_TABELLA_SUPERSET,
                            DbNomiTabelleCampi.CAMPO_guid_TABELLA_SUPERSET + " = ? ", valoriGuid);

                    ritorno = true;
                }
                if (!cursoreGuid.isClosed())
                    cursoreGuid.close();
            }

            // Sposto l'elemento in un valore molto alto come variabile di scambio
            String[] valoriWhereTemp = {Integer.toString(_idScheda),
                    Integer.toString(_posizioneIniziale)};
            String updateTemp = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + " = 10000 " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_ESERCIZI + " = ? AND " +
                    DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + " = ? ;";
            db.execSQL(updateTemp, valoriWhereTemp);

            // Shift
            String[] valoriWhereShift = {Integer.toString(_idScheda),
                    Integer.toString(_posizioneIniziale),
                    Integer.toString(_posizioneFinale)};

            String updateShift = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                                 "SET " + DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + " = " + DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + " + 1 " +
                                 "WHERE " + DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_ESERCIZI + " = ? AND " +
                                            DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + " < ? AND " +
                                            DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + " >= ? ;";
            db.execSQL(updateShift, valoriWhereShift);

            // Sposto l'elemento
            String[] valoriWhereAggiorna = {Integer.toString(_idScheda),
                    Integer.toString(10000)};

            String updateAggiorna = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + " = " + _posizioneFinale + " " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_ESERCIZI + " = ? AND " +
                               DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + " = ? ;";
            db.execSQL(updateAggiorna, valoriWhereAggiorna);

            if (!cursoreIdEsercizi.isClosed())
                cursoreIdEsercizi.close();
            db.setTransactionSuccessful();

            return ritorno;
        } finally {
            db.endTransaction();
            this.ChiudiConnessione();
        }
    }

    public Boolean ScambiaPosizioniAllIndietro(int _idScheda, int _posizioneIniziale, int _posizioneFinale) {
        this.ApriConnessionePerScrittura();
        db.beginTransaction();
        try {
            Boolean ritorno = false; // Il valore di ritorno viene posto a true se serve che il chiamante ricarica la listview

            // Reperisco gli id degli esercizi coinvolti in base all'ordine
            String query = "SELECT " + DbNomiTabelleCampi.CAMPO_id_TABELLA_ESERCIZI + " " +
                    "FROM " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_ESERCIZI + " = " + Integer.toString(_idScheda) + " " +
                    "AND " + DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + " " +
                    "IN ( " + Integer.toString(_posizioneIniziale) + ", " + Integer.toString(_posizioneFinale) + " )";
            Cursor cursoreIdEsercizi = db.rawQuery(query, null);
            while (cursoreIdEsercizi.moveToNext()) {
                String[] valori = {Integer.toString(cursoreIdEsercizi.getInt(0))};
                String selectGuid = "SELECT " +
                        DbNomiTabelleCampi.NOME_TABELLA_SUPERSET + "." + DbNomiTabelleCampi.CAMPO_guid_TABELLA_SUPERSET + " " +
                        "FROM " + DbNomiTabelleCampi.NOME_TABELLA_SUPERSET + " " +
                        "WHERE " +
                        DbNomiTabelleCampi.NOME_TABELLA_SUPERSET + "." + DbNomiTabelleCampi.CAMPO_idEsercizio_FK_TABELLA_SUPERSET + " = ? ";
                Cursor cursoreGuid = db.rawQuery(selectGuid, valori);
                if (cursoreGuid.getCount() > 0) {
                    cursoreGuid.moveToFirst();
                    String[] valoriGuid = {cursoreGuid.getString(0)};

                    // Elimino l'intera superset
                    int righeCancellate = db.delete(DbNomiTabelleCampi.NOME_TABELLA_SUPERSET,
                            DbNomiTabelleCampi.CAMPO_guid_TABELLA_SUPERSET + " = ? ", valoriGuid);

                    ritorno = true;
                }
                if (!cursoreGuid.isClosed())
                    cursoreGuid.close();
            }

            // Sposto l'elemento in un valore molto alto come variabile di scambio
            String[] valoriWhereTemp = {Integer.toString(_idScheda),
                    Integer.toString(_posizioneIniziale)};
            String updateTemp = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + " = 10000 " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_ESERCIZI + " = ? AND " +
                    DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + " = ? ;";
            db.execSQL(updateTemp, valoriWhereTemp);

            // Shift
            String[] valoriWhereShift = {Integer.toString(_idScheda),
                    Integer.toString(_posizioneIniziale),
                    Integer.toString(_posizioneFinale)};

            String updateShift = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + " = " + DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + " - 1 " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_ESERCIZI + " = ? AND " +
                               DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + " > ? AND " +
                               DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + " <= ? ;";
            db.execSQL(updateShift, valoriWhereShift);

            // Sposto l'elemento
            String[] valoriWhereAggiorna = {Integer.toString(_idScheda),
                    Integer.toString(10000)};

            String updateAggiorna = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + " = " + _posizioneFinale + " " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_ESERCIZI + " = ? AND " +
                    DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + " = ? ;";
            db.execSQL(updateAggiorna, valoriWhereAggiorna);

            if (!cursoreIdEsercizi.isClosed())
                cursoreIdEsercizi.close();
            db.setTransactionSuccessful();

            return ritorno;
        } finally {
            db.endTransaction();
            this.ChiudiConnessione();
        }
    }

    public void SalvaSuperSet(HashSet<Integer> elenco) {
        this.ApriConnessionePerScrittura();
        db.beginTransaction();
        try {
            // Genero un guid per unire la superset
            java.util.UUID guid = java.util.UUID.randomUUID();
            int contatore = 0;
            int isPrimo;
            int isUltimo;
            // Riordino gli id degli esercizi passati sulla base dell'ordine che hanno nel db
            StringBuilder idPerClausolaIN = new StringBuilder();
            for (int singolo : elenco) {
                idPerClausolaIN.append(Integer.toString(singolo)).append(",");
            }
            String idPerClausolaINdefinitivo = idPerClausolaIN.substring(0, idPerClausolaIN.length() - 1);
            String query = "SELECT " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_id_TABELLA_ESERCIZI + " " +
                    "FROM " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "WHERE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_id_TABELLA_ESERCIZI + " IN " +
                    "( " + idPerClausolaINdefinitivo  + " " +
                    ") " +
                    "ORDER BY " +
                          DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI;
            Cursor cursore = db.rawQuery(query, null);
            while (cursore.moveToNext()) {
                ContentValues valoriSuperset = new ContentValues();
                valoriSuperset.put(DbNomiTabelleCampi.CAMPO_guid_TABELLA_SUPERSET, guid.toString());
                valoriSuperset.put(DbNomiTabelleCampi.CAMPO_idEsercizio_FK_TABELLA_SUPERSET, cursore.getInt(0));
                if (contatore == 0) {
                    // Primo esercizio della superset
                    isPrimo = 1;
                    isUltimo = 0;
                }
                else if (contatore == elenco.size() - 1) {
                    // Ultimo esercizio della superdet
                    isPrimo = 0;
                    isUltimo = 1;
                }
                else {
                    // Esercizi intermedi
                    isPrimo = 0;
                    isUltimo = 0;
                }
                valoriSuperset.put(DbNomiTabelleCampi.CAMPO_isPrimo_TABELLA_SUPERSET, isPrimo);
                valoriSuperset.put(DbNomiTabelleCampi.CAMPO_isUltimo_TABELLA_SUPERSET, isUltimo);
                long nuovoIdSuperset = db.insert(DbNomiTabelleCampi.NOME_TABELLA_SUPERSET, null, valoriSuperset);

                contatore++;
            }

            if (!cursore.isClosed())
                cursore.close();
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            this.ChiudiConnessione();
        }
    }

    public ArrayList<Integer> getElencoOrdiniEserciziPerVerificaSuccessivi(HashSet<Integer> elenco) {
        this.ApriConnessionePerLettura();
        ArrayList<Integer> elencoRestituito = new ArrayList<>();
        try {
            // Riordino gli id degli esercizi passati sulla base dell'ordine che hanno nel db
            StringBuilder idPerClausolaIN = new StringBuilder();
            for (int singolo : elenco) {
                idPerClausolaIN.append(Integer.toString(singolo)).append(",");
            }
            String idPerClausolaINdefinitivo = idPerClausolaIN.substring(0, idPerClausolaIN.length() - 1);
            String query = "SELECT " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + " " +
                    "FROM " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "WHERE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_id_TABELLA_ESERCIZI + " IN " +
                    "( " + idPerClausolaINdefinitivo  + " " +
                    ") " +
                    "ORDER BY " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI;
            Cursor cursore = db.rawQuery(query, null);
            while (cursore.moveToNext()) {
                elencoRestituito.add(cursore.getInt(0));
            }
            if (!cursore.isClosed())
                cursore.close();

            return elencoRestituito;
        } finally {
            this.ChiudiConnessione();
        }
    }
}
