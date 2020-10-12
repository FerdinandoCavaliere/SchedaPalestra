package it.ermete.mercurio.schedapalestra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class EserciziPersonaliModel {

    protected SQLiteDatabase db;
    DbHelper dbHelper;
    final Context context;

    public EserciziPersonaliModel(Context _context) {
        this.context = _context;
        dbHelper = DbHelper.getInstanzaDbHelper(context);
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
        db.close();
    }

    public Cursor LeggiEserciziPersonali() {
        try {
            String query = "SELECT " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI_PERSONALI + "." + DbNomiTabelleCampi.CAMPO_id_TABELLA_ESERCIZI_PERSONALI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI_PERSONALI + "." + DbNomiTabelleCampi.CAMPO_nome_TABELLA_ESERCIZI_PERSONALI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI_PERSONALI + "." + DbNomiTabelleCampi.CAMPO_idGruppoMuscolare_TABELLA_ESERCIZI_PERSONALI + " " +
                    "FROM " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI_PERSONALI + " " +
                    "ORDER BY " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI_PERSONALI + "." + DbNomiTabelleCampi.CAMPO_idGruppoMuscolare_TABELLA_ESERCIZI_PERSONALI + " ASC, " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI_PERSONALI + "." + DbNomiTabelleCampi.CAMPO_nome_TABELLA_ESERCIZI_PERSONALI + " ASC ";
            return db.rawQuery(query, null);
        }
        catch (Exception ex) {
            return null;
        }
    }

    public Cursor LeggiEserciziPersonaliConIdGruppo(int idGruppo) {
        try {
            String[] parametri = {Integer.toString(idGruppo)};
            String query = "SELECT " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI_PERSONALI + "." + DbNomiTabelleCampi.CAMPO_id_TABELLA_ESERCIZI_PERSONALI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI_PERSONALI + "." + DbNomiTabelleCampi.CAMPO_nome_TABELLA_ESERCIZI_PERSONALI + ", " +
                    DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI_PERSONALI + "." + DbNomiTabelleCampi.CAMPO_idGruppoMuscolare_TABELLA_ESERCIZI_PERSONALI + " " +
                    "FROM " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI_PERSONALI + " " +
                    "WHERE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI_PERSONALI + "." + DbNomiTabelleCampi.CAMPO_idGruppoMuscolare_TABELLA_ESERCIZI_PERSONALI + " = ? ";
            return db.rawQuery(query, parametri);
        }
        catch (Exception ex) {
            return null;
        }
    }

    public long InsertEsercizioPersonale(EsercizioPersonaleDaDb _esercizio) {
        this.ApriConnessionePerScrittura();
        db.beginTransaction();
        try {
            long massimoIdPresente = -1;
            long nuovoId = -1;

            // Leggo il massimo id della tabella
            String query = "SELECT MAX(" + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI_PERSONALI + "." + DbNomiTabelleCampi.CAMPO_id_TABELLA_ESERCIZI_PERSONALI + ")" +
                           "FROM " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI_PERSONALI;
            Cursor cursore =  db.rawQuery(query, null);

            ContentValues valori = new ContentValues();

            // Determino il nuovo id
            cursore.moveToFirst();
            if (cursore.getInt(0) > 0) {
                massimoIdPresente = cursore.getInt(0);
                valori.put(DbNomiTabelleCampi.CAMPO_id_TABELLA_ESERCIZI_PERSONALI, massimoIdPresente + 1);
            }
            else {
                valori.put(DbNomiTabelleCampi.CAMPO_id_TABELLA_ESERCIZI_PERSONALI, 10000);
            }

            valori.put(DbNomiTabelleCampi.CAMPO_nome_TABELLA_ESERCIZI_PERSONALI, _esercizio.getNome());
            valori.put(DbNomiTabelleCampi.CAMPO_idGruppoMuscolare_TABELLA_ESERCIZI_PERSONALI, _esercizio.getIdGruppoMuscolare());
            nuovoId = db.insert(DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI_PERSONALI, null, valori);

            if (!cursore.isClosed())
                cursore.close();
            db.setTransactionSuccessful();

            return nuovoId;
        }
        catch (Exception ex) {
            return -2;
        }
        finally {
            db.endTransaction();
            this.ChiudiConnessione();
        }
    }

    public long UpdateEsercizioPersonale(EsercizioPersonaleDaDb _esercizio) {
        this.ApriConnessionePerScrittura();
        try {
            String[] valoriWhere = {Integer.toString(_esercizio.getId())};

            ContentValues valori = new ContentValues();
            valori.put(DbNomiTabelleCampi.CAMPO_nome_TABELLA_ESERCIZI_PERSONALI, _esercizio.getNome());
            valori.put(DbNomiTabelleCampi.CAMPO_idGruppoMuscolare_TABELLA_ESERCIZI_PERSONALI, _esercizio.getIdGruppoMuscolare());

            return db.update(DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI_PERSONALI,
                    valori,
                    DbNomiTabelleCampi.CAMPO_id_TABELLA_ESERCIZI_PERSONALI + " = ?",
                    valoriWhere);
        }
        catch (Exception ex) {
            return -2;
        }
        finally {
            this.ChiudiConnessione();
        }
    }

    public int DeleteEsercizioPersonale(int _idEsercizio) {
        this.ApriConnessionePerScrittura();
        try {
            String[] valori = {Integer.toString(_idEsercizio)};

            return db.delete(DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI_PERSONALI,
                    DbNomiTabelleCampi.CAMPO_id_TABELLA_ESERCIZI_PERSONALI + " = ?",
                    valori);
        }
        catch (Exception ex) {
            return -2;
        }
        finally {
            this.ChiudiConnessione();
        }
    }

    public boolean VerificaSePossibiliUpdateDelete(EsercizioPersonaleDaDb _esercizio) {
        this.ApriConnessionePerLettura();
        try {
            // Verifico se ci sono esercizi in qualche scheda che usano il valore
            String[] parametri = {Integer.toString(_esercizio.getId())};
            String query = "SELECT COUNT(*) " +
                    "FROM " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "WHERE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_idEsercizio_TABELLA_ESERCIZI + " = ? ";
            Cursor cursore = db.rawQuery(query, parametri);
            cursore.moveToFirst();
            boolean esito = cursore.getInt(0) == 0;
            if (!cursore.isClosed())
                cursore.close();
            return esito;
        }
        catch (Exception ex) {
            return false;
        }
        finally {
            this.ChiudiConnessione();
        }
    }
}
