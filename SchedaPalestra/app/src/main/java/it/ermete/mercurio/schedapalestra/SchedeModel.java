package it.ermete.mercurio.schedapalestra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SchedeModel {

    protected SQLiteDatabase db;
    private DbHelper dbHelper;
    private final Context context;

    public SchedeModel(Context _context) {
        this.context = _context;
        dbHelper = DbHelper.getInstanzaDbHelper(this.context);
    }

    public void ApriConnessionePerLettura() throws SQLException {
        if (dbHelper == null)
            dbHelper = DbHelper.getInstanzaDbHelper(context);
        db = dbHelper.getReadableDatabase();
    }

    public void ApriConnessionePerScrittura() throws SQLException {
        if (dbHelper == null)
            dbHelper = DbHelper.getInstanzaDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void ChiudiConnessione() throws SQLException {
        if (db.isOpen())
            db.close();
    }

    public long InsertScheda(String _note) {
        this.ApriConnessionePerScrittura();
        db.beginTransaction();
        long nuovoId = -1;
        try {
            // Inserisco nuova scheda
            ContentValues valoriDaInserire = new ContentValues();
            valoriDaInserire.put(DbNomiTabelleCampi.CAMPO_dataInserimento_TABELLA_SCHEDA, Utility.GetDataAttualePerDb());
            valoriDaInserire.put(DbNomiTabelleCampi.CAMPO_note_TABELLA_SCHEDA, _note);
            nuovoId = db.insert(DbNomiTabelleCampi.NOME_TABELLA_SCHEDA, null, valoriDaInserire);

            // Rendo attiva la scheda inserita
            int righeEliminate = db.delete(DbNomiTabelleCampi.NOME_TABELLA_SCHEDA_ATTIVA, "", null);
            ContentValues valoriPerSchedaAttiva = new ContentValues();
            valoriPerSchedaAttiva.put(DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_SCHEDA_ATTIVA, nuovoId);
            db.insert(DbNomiTabelleCampi.NOME_TABELLA_SCHEDA_ATTIVA, null, valoriPerSchedaAttiva);

            db.setTransactionSuccessful();
            return nuovoId;
        }
        catch (Exception ex) {
            return -1;
        }
        finally {
            db.endTransaction();
            this.ChiudiConnessione();
        }
    }

    public Cursor LeggiIdSchedaAttiva() {
        Cursor cursore = null;
        try {
            cursore = db.rawQuery("SELECT " + DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_SCHEDA_ATTIVA +
            " FROM " + DbNomiTabelleCampi.NOME_TABELLA_SCHEDA_ATTIVA, null);
            return cursore;
        }
        catch (Exception ex) {
            return null;
        }
    }

    public Cursor LeggiDataInserimentoSchedaAttiva(int _idSchedaAttiva) {
        String[] whereArgs = {Integer.toString(_idSchedaAttiva)};
        Cursor cursore = null;
        try {
            String query = "SELECT " + DbNomiTabelleCampi.CAMPO_dataInserimento_TABELLA_SCHEDA + " " +
                           "FROM " + DbNomiTabelleCampi.NOME_TABELLA_SCHEDA + " " +
                           "WHERE " + DbNomiTabelleCampi.NOME_TABELLA_SCHEDA + "." + DbNomiTabelleCampi.CAMPO_id_TABELLA_SCHEDA + " = ? ";


            cursore = db.rawQuery(query, whereArgs);
            return cursore;
        }
        catch (Exception ex) {
            return null;
        }
    }

    public Cursor LeggiNotaSchedaAttiva(int _idSchedaAttiva) {
        String[] whereArgs = {Integer.toString(_idSchedaAttiva)};
        Cursor cursore = null;
        try {
            String query = "SELECT " + DbNomiTabelleCampi.CAMPO_note_TABELLA_SCHEDA + " " +
                    "FROM " + DbNomiTabelleCampi.NOME_TABELLA_SCHEDA + " " +
                    "WHERE " + DbNomiTabelleCampi.NOME_TABELLA_SCHEDA + "." + DbNomiTabelleCampi.CAMPO_id_TABELLA_SCHEDA + " = ? ";
            cursore = db.rawQuery(query, whereArgs);
            return cursore;
        }
        catch (Exception ex) {
            return null;
        }
    }

    public Cursor LeggiStoricoSchede() {
        // Leggo l'id della scheda attiva
//        Cursor cursoreScedaAttva = LeggiIdSchedaAttiva();
//        cursoreScedaAttva.moveToFirst();
//        int idSchedaAttiva = cursoreScedaAttva.getInt(0);

        Cursor cursore = null;
        try {
            String query = "SELECT " +
                    DbNomiTabelleCampi.CAMPO_id_TABELLA_SCHEDA +
                    ", " +
                    DbNomiTabelleCampi.CAMPO_dataInserimento_TABELLA_SCHEDA +
                    ", " +
                    DbNomiTabelleCampi.CAMPO_note_TABELLA_SCHEDA +
                    " FROM " + DbNomiTabelleCampi.NOME_TABELLA_SCHEDA +
                    " Order By " + DbNomiTabelleCampi.CAMPO_id_TABELLA_SCHEDA + " desc";
            cursore = db.rawQuery(query, null);
            return cursore;
        }
        catch (Exception ex) {
            return  null;
        }
    }

    public int DeleteScheda(int _idScheda) {
        String[] whereArgs = {Integer.toString(_idScheda)};
        this.ApriConnessionePerScrittura();
        db.beginTransaction();
        int numeroRigheEliminate = -1;
        try {
            // Reperisco gli id degli esercizi da eliminare
            String query = "SELECT " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_id_TABELLA_ESERCIZI + " " +
                    "FROM " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "WHERE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_ESERCIZI + " = ? ";
            Cursor cursore = db.rawQuery(query, whereArgs);
            // Elimino le righe da RIPETIZIONI_PESI e ESERCIZI
            while (cursore.moveToNext()) {
                db.execSQL("Delete From " +
                        DbNomiTabelleCampi.NOME_TABELLA_RIPETIZIONI_PESI + " Where " +
                        DbNomiTabelleCampi.CAMPO_idEsercizio_FK_TABELLA_RIPETIZIONI_PESI + " = " +
                        Integer.toString(cursore.getInt(0)));

                db.execSQL("Delete From " +
                        DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " Where " +
                        DbNomiTabelleCampi.CAMPO_id_TABELLA_ESERCIZI + " = " +
                        Integer.toString(cursore.getInt(0)));
            }
            // Elimino scheda
            numeroRigheEliminate = db.delete(DbNomiTabelleCampi.NOME_TABELLA_SCHEDA,
                             DbNomiTabelleCampi.CAMPO_id_TABELLA_SCHEDA + " = ?",
                             whereArgs);

            if (!cursore.isClosed())
                cursore.close();
            db.setTransactionSuccessful();
            return numeroRigheEliminate;
        }
        catch (Exception ex) {
            return  -2;
        }
        finally {
            db.endTransaction();
            this.ChiudiConnessione();
        }
    }

    public int SettaSchedaAttiva(int _idScheda) {
        this.ApriConnessionePerScrittura();
        db.beginTransaction();
        try {
            int righeEliminate = db.delete(DbNomiTabelleCampi.NOME_TABELLA_SCHEDA_ATTIVA, "", null);
            ContentValues valoriPerSchedaAttiva = new ContentValues();
            valoriPerSchedaAttiva.put(DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_SCHEDA_ATTIVA, _idScheda);
            db.insert(DbNomiTabelleCampi.NOME_TABELLA_SCHEDA_ATTIVA, null, valoriPerSchedaAttiva);

            db.setTransactionSuccessful();
            return 0;
        }
        catch (Exception ex) {
            return  -2;
        }
        finally {
            db.endTransaction();
            this.ChiudiConnessione();
        }
    }

    public int UpdateNotaScheda(int _idScheda, String _nota) {
        try {
            this.ApriConnessionePerScrittura();

            String[] valoriWhere = {Integer.toString(_idScheda)};

            ContentValues valori = new ContentValues();
            valori.put(DbNomiTabelleCampi.CAMPO_note_TABELLA_SCHEDA, _nota);

            return db.update(DbNomiTabelleCampi.NOME_TABELLA_SCHEDA,
                    valori,
                    DbNomiTabelleCampi.CAMPO_id_TABELLA_SCHEDA + " = ?",
                    valoriWhere);
        }
        catch (Exception ex) {
            return  -2;
        }
    }
}
