package it.ermete.mercurio.schedapalestra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    // Nome e versione del database
    private static final String NOME_DATABASE = "SCHEDA_PALESTRA.db";
    private static final int VERSIONE_DATABASE = 13;

    // Instanza del DbHelper per implementare il singleton della connessione
    private static DbHelper helper;
    public static synchronized DbHelper getInstanzaDbHelper(Context context) {
        if (helper == null)
            helper = new DbHelper(context);
        return helper;
    }

    // Costruttore
    public DbHelper(Context context) {
        super(context, NOME_DATABASE, null, VERSIONE_DATABASE);
    }

    // Override dei metodi onCreate e onUpdate
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            // Esegui gli script di creazione delle tabelle
            db.execSQL(DbScriptCreazioneTabelle.CREA_TABELLA_SCHEDE);
            db.execSQL(DbScriptCreazioneTabelle.CREA_TABELLA_ESERCIZI);
            db.execSQL(DbScriptCreazioneTabelle.CREA_TABELLA_RIPETIZIONI_PESI);
            db.execSQL(DbScriptCreazioneTabelle.CREA_TABELLA_SUPERSET);
            db.execSQL(DbScriptCreazioneTabelle.CREA_TABELLA_ESERCIZI_PERSONALI);
            db.execSQL(DbScriptCreazioneTabelle.CREA_TABELLA_SCHEDA_ATTIVA);
            db.execSQL(DbScriptCreazioneTabelle.CREA_TABELLA_DATI_ESERCIZIO_CARDIO);

            // Inserisco una prima scheda vuota per inizializzare il sistema
            ContentValues valori = new ContentValues();
            valori.put(DbNomiTabelleCampi.CAMPO_dataInserimento_TABELLA_SCHEDA, Utility.GetDataAttualePerDb());
            valori.put(DbNomiTabelleCampi.CAMPO_note_TABELLA_SCHEDA, "");
            db.insert(DbNomiTabelleCampi.NOME_TABELLA_SCHEDA, null, valori);

            // Rendo attiva la prima scheda
            Cursor cursoreIdSchede = db.rawQuery("SELECT MAX( " + DbNomiTabelleCampi.CAMPO_id_TABELLA_SCHEDA +
                    " ) FROM " + DbNomiTabelleCampi.NOME_TABELLA_SCHEDA, null);
            if (cursoreIdSchede.getCount() > 0) {
                cursoreIdSchede.moveToFirst();
                int idUltimaScheda = cursoreIdSchede.getInt(0);

                int risultato = db.delete(DbNomiTabelleCampi.NOME_TABELLA_SCHEDA_ATTIVA, "", null);
                ContentValues valoriSchedaAttiva = new ContentValues();
                valoriSchedaAttiva.put(DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_SCHEDA_ATTIVA, idUltimaScheda);
                db.insert(DbNomiTabelleCampi.NOME_TABELLA_SCHEDA_ATTIVA, null, valoriSchedaAttiva);
            }

            if (!cursoreIdSchede.isClosed())
                cursoreIdSchede.close();
        }
        catch (Exception ex) {
            Log.d(DbHelper.class.getName(), ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            if (oldVersion == 1 && newVersion == 13) {
                updateToVersione2(db);
                updateToVersione3(db);
                updateToVersione4(db);
                updateToVersione5(db);
                updateToVersione6(db);
                updateToVersione7(db);
                updateToVersione8(db);
                updateToVersione9(db);
                updateToVersione10(db);
                updateToVersione11(db);
                updateToVersione12(db);
                updateToVersione13(db);
            }

            if (oldVersion == 2 && newVersion == 13) {
                updateToVersione3(db);
                updateToVersione4(db);
                updateToVersione5(db);
                updateToVersione6(db);
                updateToVersione7(db);
                updateToVersione8(db);
                updateToVersione9(db);
                updateToVersione10(db);
                updateToVersione11(db);
                updateToVersione12(db);
                updateToVersione13(db);
            }

            if (oldVersion == 3 && newVersion == 13) {
                updateToVersione4(db);
                updateToVersione5(db);
                updateToVersione6(db);
                updateToVersione7(db);
                updateToVersione8(db);
                updateToVersione9(db);
                updateToVersione10(db);
                updateToVersione11(db);
                updateToVersione12(db);
                updateToVersione13(db);
            }

            if (oldVersion == 4 && newVersion == 13) {
                updateToVersione5(db);
                updateToVersione6(db);
                updateToVersione7(db);
                updateToVersione8(db);
                updateToVersione9(db);
                updateToVersione10(db);
                updateToVersione11(db);
                updateToVersione12(db);
                updateToVersione13(db);
            }

            if (oldVersion == 5 && newVersion == 13) {
                updateToVersione6(db);
                updateToVersione7(db);
                updateToVersione8(db);
                updateToVersione9(db);
                updateToVersione10(db);
                updateToVersione11(db);
                updateToVersione12(db);
                updateToVersione13(db);
            }

            if (oldVersion == 6 && newVersion == 13) {
                updateToVersione7(db);
                updateToVersione8(db);
                updateToVersione9(db);
                updateToVersione10(db);
                updateToVersione11(db);
                updateToVersione12(db);
                updateToVersione13(db);
            }

            if (oldVersion == 7 && newVersion == 13) {
                updateToVersione8(db);
                updateToVersione9(db);
                updateToVersione10(db);
                updateToVersione11(db);
                updateToVersione12(db);
                updateToVersione13(db);
            }

            if (oldVersion == 8 && newVersion == 13) {
                updateToVersione9(db);
                updateToVersione10(db);
                updateToVersione11(db);
                updateToVersione12(db);
                updateToVersione13(db);
            }

            if (oldVersion == 9 && newVersion == 13) {
                updateToVersione10(db);
                updateToVersione11(db);
                updateToVersione12(db);
                updateToVersione13(db);
            }

            if (oldVersion == 10 && newVersion == 13) {
                updateToVersione11(db);
                updateToVersione12(db);
                updateToVersione13(db);
            }

            if (oldVersion == 11 && newVersion == 13) {
                updateToVersione12(db);
                updateToVersione13(db);
            }

            if (oldVersion == 12 && newVersion == 13) {
                updateToVersione13(db);
            }
        }
        catch (Exception ex) {
            Log.d(DbHelper.class.getName(), ex.getMessage());
        }
    }

    private void updateToVersione2(SQLiteDatabase db) {
        try {
            // Non posso eliminare le colonne RIPETIZIONI e PESI  quindi opero in più passaggi
            // 1) Rinomino la tabella originale il _old
            db.execSQL("ALTER TABLE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " RENAME TO " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "_old;");
            // 2) Creo la nuova tabella senza i campi che voglio eliminare
            db.execSQL(DbScriptCreazioneTabelle.CREA_TABELLA_ESERCIZI_SENZA_RIPETIZIONI_PESI);
            // 3) Creo la tabella PESI
            db.execSQL(DbScriptCreazioneTabelle.CREA_TABELLA_RIPETIZIONI_PESI);
            // 4) Riempio le nuove tabelle
            String query = "Select " +
                    DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.CAMPO_idGruppoMuscolare_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.CAMPO_nomeGruppoMuscolare_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.CAMPO_idEsercizio_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.CAMPO_serie_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.CAMPO_recupero_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.CAMPO_note_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.CAMPO_giorno_TABELLA_ESERCIZI + ", " +
                    DbNomiTabelleCampi.CAMPO_ripetizioni_TABELLA_RIPETIZIONI_PESI + ", " +
                    DbNomiTabelleCampi.CAMPO_peso_TABELLA_RIPETIZIONI_PESI + " " +
                    "From " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "_old ";
            Cursor cursore_1 = db.rawQuery(query, null);
            while (cursore_1.moveToNext()) {
                ContentValues valoriEsercizio = new ContentValues();
                valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_ESERCIZI, cursore_1.getInt(0));
                valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_idGruppoMuscolare_TABELLA_ESERCIZI, cursore_1.getInt(1));
                valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_nomeGruppoMuscolare_TABELLA_ESERCIZI, cursore_1.getString(2));
                valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_idEsercizio_TABELLA_ESERCIZI,cursore_1.getInt(3));
                valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI, cursore_1.getString(4));
                valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_serie_TABELLA_ESERCIZI, cursore_1.getInt(5));
                valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_recupero_TABELLA_ESERCIZI, cursore_1.getFloat(6));
                valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI, cursore_1.getInt(7));
                valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_note_TABELLA_ESERCIZI, cursore_1.getString(8));
                valoriEsercizio.put(DbNomiTabelleCampi.CAMPO_giorno_TABELLA_ESERCIZI, cursore_1.getString(9));
                long nuovoIdEsercizio = db.insert(DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI, null, valoriEsercizio);

                ContentValues valoriRipetizioniPeso = new ContentValues();
                valoriRipetizioniPeso.put(DbNomiTabelleCampi.CAMPO_idEsercizio_FK_TABELLA_RIPETIZIONI_PESI, nuovoIdEsercizio);
                valoriRipetizioniPeso.put(DbNomiTabelleCampi.CAMPO_ripetizioni_TABELLA_RIPETIZIONI_PESI, cursore_1.getInt(10));
                valoriRipetizioniPeso.put(DbNomiTabelleCampi.CAMPO_peso_TABELLA_RIPETIZIONI_PESI, cursore_1.getFloat(11));
                long nuovoIdRipetizioniPesi = db.insert(DbNomiTabelleCampi.NOME_TABELLA_RIPETIZIONI_PESI, null, valoriRipetizioniPeso);
            }

            // 5) Cancello la tabella vecchia
            db.execSQL("DROP TABLE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "_old;");

            if (!cursore_1.isClosed())
                cursore_1.close();
        }
        catch (Exception ex) {
            Log.d(DbHelper.class.getName(), ex.getMessage());
        }
    }

    private void updateToVersione3(SQLiteDatabase db) {
        try {
            // Aggiungo il campo isPiramidale su ESERCIZI
            String queryAddColumn = "ALTER TABLE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "ADD COLUMN " + DbNomiTabelleCampi.CAMPO_isPiramidale_TABELLA_ESERCIZI + " BOOLEAN ";
            db.execSQL(queryAddColumn);

            // Inizializzo a 0 il nuovo campo
            String update = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_isPiramidale_TABELLA_ESERCIZI + " = 0 ";
            db.execSQL(update);
        }
        catch (Exception ex) {
            Log.d(DbHelper.class.getName(), ex.getMessage());
        }
    }

    private void updateToVersione4(SQLiteDatabase db) {
        try {
            // Aggiorno i nomi degli esercizi nella tabella ESERCIZI secondo i valori cambiati nel file xml
            String update1 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Bilanciere EZ presa inversa' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Bilanciere angolare imp inversa' ";
            db.execSQL(update1);

            String update2 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Affondi con manubri' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Affondi' ";
            db.execSQL(update2);

            String update3 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'French press bilanciere EZ' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'French press' ";
            db.execSQL(update3);

            String update4 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Alzate frontali con manubrio singolo braccio' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Alzate frontali con man. singolo braccio' ";
            db.execSQL(update4);

            // Aggiornamento campo "ordine" secondo l'ordine di inserimento nella scheda
            String querySelectIdSchede = "SELECT _id FROM SCHEDE";
            Cursor cursoreIdSchede = db.rawQuery(querySelectIdSchede, null);
            if (cursoreIdSchede.getCount() > 0) {
                int ordine;
                while (cursoreIdSchede.moveToNext()) { // Per ogni scheda
                    // Leggo gli esercizi della scheda corrente
                    ordine = -1;
                    String[] parametriWhere = {Integer.toString(cursoreIdSchede.getInt(0))};
                    String querySelectIdEsercizi = "SELECT _id FROM " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                            "WHERE " + DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_ESERCIZI + " = ? " +
                            "ORDER BY _id ";
                    Cursor cursoreIdEsercizi = db.rawQuery(querySelectIdEsercizi, parametriWhere);
                    if (cursoreIdEsercizi.getCount() > 0) {
                        while (cursoreIdEsercizi.moveToNext()) { // Per ogni esercizio
                            ordine++;
                            String[] parametriPerUpdate = {Integer.toString(ordine),
                                    Integer.toString(cursoreIdEsercizi.getInt(0))};
                            String updateOrdine = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                                    "SET " + DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + " = ? " +
                                    "WHERE " + DbNomiTabelleCampi.CAMPO_id_TABELLA_ESERCIZI + " = ? ";
                            db.execSQL(updateOrdine, parametriPerUpdate);
                        }
                    }
                    if (!cursoreIdEsercizi.isClosed())
                        cursoreIdEsercizi.close();
                }
            }
            if (!cursoreIdSchede.isClosed())
                cursoreIdSchede.close();
        }
        catch (Exception ex) {
            Log.d(DbHelper.class.getName(), ex.getMessage());
        }
    }

    private void updateToVersione5(SQLiteDatabase db) {
        try {
            // Aggiorno i nomi degli esercizi nella tabella ESERCIZI secondo i valori cambiati nel file xml
            String update1 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'French press bilanciere EZ' , " +
                             DbNomiTabelleCampi.CAMPO_idEsercizio_TABELLA_ESERCIZI + " = 901 " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'French press con bila' ";
            db.execSQL(update1);

            String update2 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Croci su panca orizzontale' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Croci su panca orizz.' ";
            db.execSQL(update2);

            String update3 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Croci su panca declinata' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Croci su panca decli.' ";
            db.execSQL(update3);

            String update4 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Croci su panca inclinata' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Croci su panca incli.' ";
            db.execSQL(update4);

            String update5 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Panca orizzontale con bilanciere' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Panca orizz. con bilanciere' ";
            db.execSQL(update5);

            String update6 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Panca declinata con bilanciere' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Panca decli. con bilanciere' ";
            db.execSQL(update6);

            String update7 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Panca inclinata con bilanciere' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Panca incli. con bilanciere' ";
            db.execSQL(update7);

            String update8 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Panca orizzontale con manubri' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Panca orizz. con manubri' ";
            db.execSQL(update8);

            String update9 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Panca inclinata con manubri' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Panca incli. con manubri' ";
            db.execSQL(update9);

            String update10 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Panca declinata con manubri' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Panca decl. con manubri' ";
            db.execSQL(update10);

            String update11 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Croci su panca orizzontale ai cavi' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Croci su panca orizz. ai cavi' ";
            db.execSQL(update11);

            String update12 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Croci su panca declinata ai cavi' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Croci su panca decli. ai cavi' ";
            db.execSQL(update12);

            String update13 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Croci su panca inclinata ai cavi' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Croci su panca incli. ai cavi' ";
            db.execSQL(update13);

            String update14 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Panca orizzontale bilanciere presa stretta' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Panca orizz. bilanciere presa stretta' ";
            db.execSQL(update14);

            String update15 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Gambe flesse con bilanciere' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Gambe flesse su bil. o man.' ";
            db.execSQL(update15);

            String update16 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Gambe tese con bilanciere' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Gambe rigide bil. o man.' ";
            db.execSQL(update16);

            String update17 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Rematore con bilanciere' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Rematore bilanciere' ";
            db.execSQL(update17);

            String update18 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Rematore con manubrio' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Rematore manubrio' ";
            db.execSQL(update18);

            String update19 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Alzate frontali con manubrio' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Alzate frontali con man.' ";
            db.execSQL(update19);

            // Aggiornamento campo "ordine" per evitare buchi
            String querySelectIdSchede = "SELECT _id FROM SCHEDE";
            Cursor cursoreIdSchede = db.rawQuery(querySelectIdSchede, null);
            if (cursoreIdSchede.getCount() > 0) {
                int ordine;
                while (cursoreIdSchede.moveToNext()) { // Per ogni scheda
                    // Leggo gli esercizi della scheda corrente
                    ordine = -1;
                    String[] parametriWhere = {Integer.toString(cursoreIdSchede.getInt(0))};
                    String querySelectIdEsercizi = "SELECT _id FROM " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                            "WHERE " + DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_ESERCIZI + " = ? " +
                            "ORDER BY ordine ";
                    Cursor cursoreIdEsercizi = db.rawQuery(querySelectIdEsercizi, parametriWhere);
                    if (cursoreIdEsercizi.getCount() > 0) {
                        while (cursoreIdEsercizi.moveToNext()) { // Per ogni esercizio
                            ordine++;
                            String[] parametriPerUpdate = {Integer.toString(ordine),
                                    Integer.toString(cursoreIdEsercizi.getInt(0))};
                            String updateOrdine = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                                    "SET " + DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + " = ? " +
                                    "WHERE " + DbNomiTabelleCampi.CAMPO_id_TABELLA_ESERCIZI + " = ? ";
                            db.execSQL(updateOrdine, parametriPerUpdate);
                        }
                    }
                    if (!cursoreIdEsercizi.isClosed())
                        cursoreIdEsercizi.close();
                }
            }

            // Creo la tabella SUPERSET
            db.execSQL(DbScriptCreazioneTabelle.CREA_TABELLA_SUPERSET);

            if (!cursoreIdSchede.isClosed())
                cursoreIdSchede.close();
        }
        catch (Exception ex) {
            Log.d(DbHelper.class.getName(), ex.getMessage());
        }
    }

    private void updateToVersione6(SQLiteDatabase db) {
        try {
            // Creo la tabella ESERCIZI_PERSONALI
            db.execSQL(DbScriptCreazioneTabelle.CREA_TABELLA_ESERCIZI_PERSONALI);
        }
        catch (Exception ex) {
            Log.d(DbHelper.class.getName(), ex.getMessage());
        }
    }

    private void updateToVersione7(SQLiteDatabase db) {
        try {
            // Creo la tabella ESERCIZI_PERSONALI
            db.execSQL(DbScriptCreazioneTabelle.CREA_TABELLA_SCHEDA_ATTIVA);

            // Rendo attiva la prima scheda
            Cursor cursoreIdSchede = db.rawQuery("SELECT MAX( " + DbNomiTabelleCampi.CAMPO_id_TABELLA_SCHEDA +
                    " ) FROM " + DbNomiTabelleCampi.NOME_TABELLA_SCHEDA, null);
            if (cursoreIdSchede.getCount() > 0) {
                cursoreIdSchede.moveToFirst();
                int idUltimaScheda = cursoreIdSchede.getInt(0);

                int risultato = db.delete(DbNomiTabelleCampi.NOME_TABELLA_SCHEDA_ATTIVA, "", null);
                ContentValues valori = new ContentValues();
                valori.put(DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_SCHEDA_ATTIVA, idUltimaScheda);
                db.insert(DbNomiTabelleCampi.NOME_TABELLA_SCHEDA_ATTIVA, null, valori);
            }
            if (!cursoreIdSchede.isClosed())
                cursoreIdSchede.close();
        }
        catch (Exception ex) {
            Log.d(DbHelper.class.getName(), ex.getMessage());
        }
    }

    private void updateToVersione8(SQLiteDatabase db) {
        try {
            // Creo la tabella DATI_ESERCIZIO_CARDIO)
            db.execSQL(DbScriptCreazioneTabelle.CREA_TABELLA_DATI_ESERCIZIO_CARDIO);
        }
        catch (Exception ex) {
            Log.d(DbHelper.class.getName(), ex.getMessage());
        }
    }

    private void updateToVersione9(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            // Aggiungo la colonna recupero alla tebella RIPETIZIONI_PESO
            String queryAddColumn = "ALTER TABLE " + DbNomiTabelleCampi.NOME_TABELLA_RIPETIZIONI_PESI + " " +
                    "ADD COLUMN " + DbNomiTabelleCampi.CAMPO_recupero_TABELLA_RIPETIZIONI_PESI + " REAL ";
            db.execSQL(queryAddColumn);

            // Leggo tutta la tabella RIPETIZIONI_PESO
            String queryRipetizioniPeso = "SELECT " +
            DbNomiTabelleCampi.NOME_TABELLA_RIPETIZIONI_PESI + "." + DbNomiTabelleCampi.CAMPO_id_TABELLA_RIPETIZIONI_PESI + ", " +
            DbNomiTabelleCampi.NOME_TABELLA_RIPETIZIONI_PESI + "." + DbNomiTabelleCampi.CAMPO_idEsercizio_FK_TABELLA_RIPETIZIONI_PESI + " " +
            "FROM " + DbNomiTabelleCampi.NOME_TABELLA_RIPETIZIONI_PESI;

            Cursor cursorRipetizioniPeso = db.rawQuery(queryRipetizioniPeso, null);

            if (cursorRipetizioniPeso.getCount() > 0) {
                while (cursorRipetizioniPeso.moveToNext()) { // Per ogni ripetizione peso

                    // Leggo il relativo record in esercizi
                    int _idRipetizioniPesi = cursorRipetizioniPeso.getInt(0);
                    int _idEsercizio = cursorRipetizioniPeso.getInt(1);

                    String[] parametri = {Integer.toString(_idEsercizio)};
                    String queryEsercizi = "SELECT " +
                            DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_id_TABELLA_ESERCIZI + ", " +
                            DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + "." + DbNomiTabelleCampi.CAMPO_recupero_TABELLA_ESERCIZI + " " +
                            "FROM " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                            "WHERE " + DbNomiTabelleCampi.CAMPO_id_TABELLA_ESERCIZI + " = ? ";

                    Cursor cursorEsercizi = db.rawQuery(queryEsercizi, parametri);
                    if (cursorEsercizi.getCount() > 0) {
                        cursorEsercizi.moveToFirst();
                        float _recupero = cursorEsercizi.getFloat(1);

                        // Faccio l'update del nuovo campo in RIPETIZIONI_PESO
                        String[] parametriPerUpdate = {Float.toString(_recupero), Integer.toString(_idRipetizioniPesi)};
                        String updateOrdine = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_RIPETIZIONI_PESI + " " +
                                "SET " + DbNomiTabelleCampi.CAMPO_recupero_TABELLA_RIPETIZIONI_PESI + " = ? " +
                                "WHERE " + DbNomiTabelleCampi.CAMPO_id_TABELLA_RIPETIZIONI_PESI + " = ? ";
                        db.execSQL(updateOrdine, parametriPerUpdate);
                    }

                    if (!cursorEsercizi.isClosed())
                        cursorEsercizi.close();
                }
            }

            if (!cursorRipetizioniPeso.isClosed())
                cursorRipetizioniPeso.close();
            db.setTransactionSuccessful();
        }
        catch (Exception ex)
        {
            Log.d(DbHelper.class.getName(), ex.getMessage());
        }
        finally {
            db.endTransaction();
        }
    }

    private void updateToVersione10(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            // Aggiungo la colonna bgColor alla tebella ESERCIZI
            String queryAddColumn = "ALTER TABLE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "ADD COLUMN " + DbNomiTabelleCampi.CAMPO_bgColor_TABELLA_ESERCIZI + " TEXT ";
            db.execSQL(queryAddColumn);

            // Setto il colore di default
            String[] parametriPerUpdate = {"Bianco"};
            String updateBgColor = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_bgColor_TABELLA_ESERCIZI + " = ? ";
            db.execSQL(updateBgColor, parametriPerUpdate);

            // Aggiorno i nomi degli esercizi nella tabella ESERCIZI secondo i valori cambiati nel file xml
            String update1 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Addominali alla macchina' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Addominali -n- machine' ";
            db.execSQL(update1);

            String update2 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Iso row' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Iso row hammer' ";
            db.execSQL(update2);

            String update3 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Iso pull down' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Iso pull down hammer' ";
            db.execSQL(update3);

            String update4 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Iso high row' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Iso high row hammer' ";
            db.execSQL(update4);

            String update5 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Good morning' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Good - morning' ";
            db.execSQL(update5);

            String update6 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Sitting leg curl' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Leg curl seduto -N-' ";
            db.execSQL(update6);

            String update7 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Wide chest' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Wide chest hammer' ";
            db.execSQL(update7);

            String update8 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Decline press' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Decline press hammer' ";
            db.execSQL(update8);

            String update9 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Military press' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Military press hammer' ";
            db.execSQL(update9);

            String update10 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Iso shoulder press' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Iso shoulder press hammer' ";
            db.execSQL(update10);

            String update11 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Shoulder press' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Shoulder press life fitness' ";
            db.execSQL(update11);

            String update12 = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Lateral raise' " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " = 'Lateral raise -N- hammer' ";
            db.execSQL(update12);

            db.setTransactionSuccessful();
        }
        catch (Exception ex)
        {
            Log.d(DbHelper.class.getName(), ex.getMessage());
        }
        finally {
            db.endTransaction();
        }
    }

    private void updateToVersione11(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            // Aggiungo la colonna UnitaDiMisura alla tebella ESERCIZI
            String queryAddColumnUnitaDiMisura = "ALTER TABLE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "ADD COLUMN " + DbNomiTabelleCampi.CAMPO_UnitaDiMisura_TABELLA_ESERCIZI + " TEXT ";
            db.execSQL(queryAddColumnUnitaDiMisura);

            // Aggiungo la colonna Routine alla tebella ESERCIZI
            String queryAddColumnRoutine = "ALTER TABLE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "ADD COLUMN " + DbNomiTabelleCampi.CAMPO_Routine_TABELLA_ESERCIZI + " TEXT ";
            db.execSQL(queryAddColumnRoutine);

            // Setto l'unità di misura su Kg
            String[] parametriPerUpdateUnitaDiMisura = {"Kg"};
            String updateUnitaDiMisura = "UPDATE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "SET " + DbNomiTabelleCampi.CAMPO_UnitaDiMisura_TABELLA_ESERCIZI + " = ? " +
                    "WHERE " + DbNomiTabelleCampi.CAMPO_nomeGruppoMuscolare_TABELLA_ESERCIZI + " <> 'Cardio' ";
            db.execSQL(updateUnitaDiMisura, parametriPerUpdateUnitaDiMisura);

            db.setTransactionSuccessful();
        }
        catch (Exception ex)
        {
            Log.d(DbHelper.class.getName(), ex.getMessage());
        }
        finally {
            db.endTransaction();
        }
    }

    private void updateToVersione12(SQLiteDatabase db) {
        try {
            // Devo cambiare il tipo ad un campo
            // 1) Rinomino la tabella originale il _old
            db.execSQL("ALTER TABLE " + DbNomiTabelleCampi.NOME_TABELLA_RIPETIZIONI_PESI + " RENAME TO " + DbNomiTabelleCampi.NOME_TABELLA_RIPETIZIONI_PESI + "_old;");

            // 2) Creo la nuova tabella con i campi giusti
            db.execSQL(DbScriptCreazioneTabelle.CREA_TABELLA_RIPETIZIONI_PESI);

            // 3) Riempio la nuova tabella
            String query = "Select " +
                    DbNomiTabelleCampi.CAMPO_idEsercizio_FK_TABELLA_RIPETIZIONI_PESI + ", " +
                    DbNomiTabelleCampi.CAMPO_ripetizioni_TABELLA_RIPETIZIONI_PESI + ", " +
                    DbNomiTabelleCampi.CAMPO_peso_TABELLA_RIPETIZIONI_PESI + ", " +
                    DbNomiTabelleCampi.CAMPO_recupero_TABELLA_RIPETIZIONI_PESI + " " +
                    "From " + DbNomiTabelleCampi.NOME_TABELLA_RIPETIZIONI_PESI + "_old ";
            Cursor cursore_1 = db.rawQuery(query, null);
            while (cursore_1.moveToNext()) {
                ContentValues valoriRipetizioniPeso = new ContentValues();
                valoriRipetizioniPeso.put(DbNomiTabelleCampi.CAMPO_idEsercizio_FK_TABELLA_RIPETIZIONI_PESI, cursore_1.getInt(0));
                valoriRipetizioniPeso.put(DbNomiTabelleCampi.CAMPO_ripetizioni_TABELLA_RIPETIZIONI_PESI, cursore_1.getInt(1));
                valoriRipetizioniPeso.put(DbNomiTabelleCampi.CAMPO_peso_TABELLA_RIPETIZIONI_PESI, cursore_1.getFloat(2));
                valoriRipetizioniPeso.put(DbNomiTabelleCampi.CAMPO_recupero_TABELLA_RIPETIZIONI_PESI, cursore_1.getFloat(3));
                long nuovoIdRipetizioniPesi = db.insert(DbNomiTabelleCampi.NOME_TABELLA_RIPETIZIONI_PESI, null, valoriRipetizioniPeso);
            }

            // 4) Cancello la tabella vecchia
            db.execSQL("DROP TABLE " + DbNomiTabelleCampi.NOME_TABELLA_RIPETIZIONI_PESI + "_old;");

            if (!cursore_1.isClosed())
                cursore_1.close();
        }
        catch (Exception ex) {
            Log.d(DbHelper.class.getName(), ex.getMessage());
        }
    }

    private void updateToVersione13(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            // Aggiungo la colonna massimale alla tebella ESERCIZI
            String queryAddColumnMassimale = "ALTER TABLE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " " +
                    "ADD COLUMN " + DbNomiTabelleCampi.CAMPO_massimale_TABELLA_ESERCIZI + " REAL ";
            db.execSQL(queryAddColumnMassimale);

            // Aggiungo la colonna percentuale alla tebella RIPETIZIONI_PESI
            String queryAddColumnPercentuale = "ALTER TABLE " + DbNomiTabelleCampi.NOME_TABELLA_RIPETIZIONI_PESI + " " +
                    "ADD COLUMN " + DbNomiTabelleCampi.CAMPO_percentuale_TABELLA_RIPETIZIONI_PESI + " REAL ";
            db.execSQL(queryAddColumnPercentuale);

            // Aggiungo la colonna rpe alla tebella RIPETIZIONI_PESI
            String queryAddColumnRpe = "ALTER TABLE " + DbNomiTabelleCampi.NOME_TABELLA_RIPETIZIONI_PESI + " " +
                    "ADD COLUMN " + DbNomiTabelleCampi.CAMPO_rpe_TABELLA_RIPETIZIONI_PESI + " REAL ";
            db.execSQL(queryAddColumnRpe);

            db.setTransactionSuccessful();
        }
        catch (Exception ex)
        {
            Log.d(DbHelper.class.getName(), ex.getMessage());
        }
        finally {
            db.endTransaction();
        }
    }
}
