package it.ermete.mercurio.schedapalestra;

public class DbScriptCreazioneTabelle {

    public static final String CREA_TABELLA_SCHEDE =
            "CREATE TABLE " + DbNomiTabelleCampi.NOME_TABELLA_SCHEDA +
            " ( " +
                DbNomiTabelleCampi.CAMPO_id_TABELLA_SCHEDA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbNomiTabelleCampi.CAMPO_dataInserimento_TABELLA_SCHEDA + " DATE NOT NULL, " +
                DbNomiTabelleCampi.CAMPO_note_TABELLA_SCHEDA + " TEXT " +
            " ); ";

    public static final String CREA_TABELLA_ESERCIZI =
            "CREATE TABLE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI +
            " ( " +
                    DbNomiTabelleCampi.CAMPO_id_TABELLA_ESERCIZI + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_ESERCIZI + " INTEGER NOT NULL, " +
                    DbNomiTabelleCampi.CAMPO_idGruppoMuscolare_TABELLA_ESERCIZI + " INTEGER NOT NULL, " +
                    DbNomiTabelleCampi.CAMPO_nomeGruppoMuscolare_TABELLA_ESERCIZI + " TEXT NOT NULL, " +
                    DbNomiTabelleCampi.CAMPO_idEsercizio_TABELLA_ESERCIZI + " INTEGER NOT NULL, " +
                    DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " TEXT NOT NULL, " +
                    DbNomiTabelleCampi.CAMPO_serie_TABELLA_ESERCIZI + " INTEGER, " +
                    DbNomiTabelleCampi.CAMPO_recupero_TABELLA_ESERCIZI + " REAL, " +
                    DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + " INTEGER, " +
                    DbNomiTabelleCampi.CAMPO_note_TABELLA_ESERCIZI + " TEXT, " +
                    DbNomiTabelleCampi.CAMPO_giorno_TABELLA_ESERCIZI + " TEXT, " +
                    DbNomiTabelleCampi.CAMPO_isPiramidale_TABELLA_ESERCIZI + " BOOLEAN, " +
                    DbNomiTabelleCampi.CAMPO_bgColor_TABELLA_ESERCIZI + " TEXT, " +
                    DbNomiTabelleCampi.CAMPO_UnitaDiMisura_TABELLA_ESERCIZI + " TEXT, " +
                    DbNomiTabelleCampi.CAMPO_Routine_TABELLA_ESERCIZI + " TEXT, " +
                    DbNomiTabelleCampi.CAMPO_massimale_TABELLA_ESERCIZI + " REAL, " +
                "FOREIGN KEY ( " + DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_ESERCIZI + " ) " +
                "REFERENCES " + DbNomiTabelleCampi.NOME_TABELLA_SCHEDA + " ( " + DbNomiTabelleCampi.CAMPO_id_TABELLA_SCHEDA + " )" +
            " ); ";

    public static final String CREA_TABELLA_RIPETIZIONI_PESI =
            "CREATE TABLE " + DbNomiTabelleCampi.NOME_TABELLA_RIPETIZIONI_PESI +
                    " ( " +
                    DbNomiTabelleCampi.CAMPO_id_TABELLA_RIPETIZIONI_PESI + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DbNomiTabelleCampi.CAMPO_idEsercizio_FK_TABELLA_RIPETIZIONI_PESI + " INTEGER NOT NULL, " +
                    DbNomiTabelleCampi.CAMPO_ripetizioni_TABELLA_RIPETIZIONI_PESI + " TEXT, " +
                    DbNomiTabelleCampi.CAMPO_peso_TABELLA_RIPETIZIONI_PESI + " REAL, " +
                    DbNomiTabelleCampi.CAMPO_recupero_TABELLA_RIPETIZIONI_PESI + " REAL, " +
                    DbNomiTabelleCampi.CAMPO_percentuale_TABELLA_RIPETIZIONI_PESI + " REAL, " +
                    DbNomiTabelleCampi.CAMPO_rpe_TABELLA_RIPETIZIONI_PESI + " REAL, " +
                    "FOREIGN KEY ( " + DbNomiTabelleCampi.CAMPO_idEsercizio_FK_TABELLA_RIPETIZIONI_PESI + " ) " +
                    "REFERENCES " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " ( " + DbNomiTabelleCampi.CAMPO_id_TABELLA_ESERCIZI + " )" +
                    " ); ";

    public static final String CREA_TABELLA_ESERCIZI_SENZA_RIPETIZIONI_PESI =
            "CREATE TABLE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI +
                    " ( " +
                    DbNomiTabelleCampi.CAMPO_id_TABELLA_ESERCIZI + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_ESERCIZI + " INTEGER NOT NULL, " +
                    DbNomiTabelleCampi.CAMPO_idGruppoMuscolare_TABELLA_ESERCIZI + " INTEGER NOT NULL, " +
                    DbNomiTabelleCampi.CAMPO_nomeGruppoMuscolare_TABELLA_ESERCIZI + " TEXT NOT NULL, " +
                    DbNomiTabelleCampi.CAMPO_idEsercizio_TABELLA_ESERCIZI + " INTEGER NOT NULL, " +
                    DbNomiTabelleCampi.CAMPO_nomeEsercizio_TABELLA_ESERCIZI + " TEXT NOT NULL, " +
                    DbNomiTabelleCampi.CAMPO_serie_TABELLA_ESERCIZI + " INTEGER, " +
                    DbNomiTabelleCampi.CAMPO_recupero_TABELLA_ESERCIZI + " REAL, " +
                    DbNomiTabelleCampi.CAMPO_ordine_TABELLA_ESERCIZI + " INTEGER, " +
                    DbNomiTabelleCampi.CAMPO_note_TABELLA_ESERCIZI + " TEXT, " +
                    DbNomiTabelleCampi.CAMPO_giorno_TABELLA_ESERCIZI + " TEXT, " +
                    "FOREIGN KEY ( " + DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_ESERCIZI + " ) " +
                    "REFERENCES " + DbNomiTabelleCampi.NOME_TABELLA_SCHEDA + " ( " + DbNomiTabelleCampi.CAMPO_id_TABELLA_SCHEDA + " )" +
                    " ); ";

    public static final String CREA_TABELLA_SUPERSET =
            "CREATE TABLE " + DbNomiTabelleCampi.NOME_TABELLA_SUPERSET +
                    " ( " +
                    DbNomiTabelleCampi.CAMPO_id_TABELLA_SUPERSET + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DbNomiTabelleCampi.CAMPO_guid_TABELLA_SUPERSET + " TEXT NOT NULL, " +
                    DbNomiTabelleCampi.CAMPO_idEsercizio_FK_TABELLA_SUPERSET + " INTEGER NOT NULL, " +
                    DbNomiTabelleCampi.CAMPO_isPrimo_TABELLA_SUPERSET + " BOOLEAN, " +
                    DbNomiTabelleCampi.CAMPO_isUltimo_TABELLA_SUPERSET + " BOOLEAN, " +
                    "FOREIGN KEY ( " + DbNomiTabelleCampi.CAMPO_idEsercizio_FK_TABELLA_SUPERSET + " ) " +
                    "REFERENCES " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " ( " + DbNomiTabelleCampi.CAMPO_id_TABELLA_ESERCIZI + " )" +
                    " ); ";

    public static final String CREA_TABELLA_ESERCIZI_PERSONALI =
            "CREATE TABLE " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI_PERSONALI +
                    " ( " +
                    DbNomiTabelleCampi.CAMPO_id_TABELLA_ESERCIZI_PERSONALI + " INTEGER PRIMARY KEY, " +
                    DbNomiTabelleCampi.CAMPO_nome_TABELLA_ESERCIZI_PERSONALI + " TEXT NOT NULL, " +
                    DbNomiTabelleCampi.CAMPO_idGruppoMuscolare_TABELLA_ESERCIZI_PERSONALI + " INTEGER NOT NULL " +
                    " ); ";

    public static final String CREA_TABELLA_SCHEDA_ATTIVA =
            "CREATE TABLE " + DbNomiTabelleCampi.NOME_TABELLA_SCHEDA_ATTIVA +
                    " ( " +
                    DbNomiTabelleCampi.CAMPO_id_TABELLA_SCHEDA_ATTIVA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_SCHEDA_ATTIVA + " INTEGER NOT NULL, " +
                    "FOREIGN KEY ( " + DbNomiTabelleCampi.CAMPO_idScheda_FK_TABELLA_SCHEDA_ATTIVA + " ) " +
                    "REFERENCES " + DbNomiTabelleCampi.NOME_TABELLA_SCHEDA + " ( " + DbNomiTabelleCampi.CAMPO_id_TABELLA_SCHEDA + " )" +
                    " ); ";

    public static final String CREA_TABELLA_DATI_ESERCIZIO_CARDIO =
            "CREATE TABLE " + DbNomiTabelleCampi.NOME_TABELLA_DATI_ESERCIZIO_CARDIO +
                    " ( " +
                    DbNomiTabelleCampi.CAMPO_id_TABELLA_DATI_ESERCIZIO_CARDIO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DbNomiTabelleCampi.CAMPO_idEsercizio_FK_TABELLA_DATI_ESERCIZIO_CARDIO + " INTEGER NOT NULL, " +
                    DbNomiTabelleCampi.CAMPO_programma_TABELLA_DATI_ESERCIZIO_CARDIO + " TEXT, " +
                    DbNomiTabelleCampi.CAMPO_intensita_TABELLA_DATI_ESERCIZIO_CARDIO + " TEXT, " +
                    DbNomiTabelleCampi.CAMPO_pendenza_TABELLA_DATI_ESERCIZIO_CARDIO + " TEXT, " +
                    DbNomiTabelleCampi.CAMPO_velocita_TABELLA_DATI_ESERCIZIO_CARDIO + " REAL, " +
                    DbNomiTabelleCampi.CAMPO_tempo_TABELLA_DATI_ESERCIZIO_CARDIO + " REAL, " +
                    "FOREIGN KEY ( " + DbNomiTabelleCampi.CAMPO_idEsercizio_FK_TABELLA_DATI_ESERCIZIO_CARDIO + " ) " +
                    "REFERENCES " + DbNomiTabelleCampi.NOME_TABELLA_ESERCIZI + " ( " + DbNomiTabelleCampi.CAMPO_id_TABELLA_ESERCIZI + " )" +
                    " ); ";
}
