package it.ermete.mercurio.schedapalestra;

public class DbNomiTabelleCampi {

    // Tabella SCHEDE
    public static final String NOME_TABELLA_SCHEDA = "SCHEDE";
    public static final String CAMPO_id_TABELLA_SCHEDA = "_id";
    public static final String CAMPO_dataInserimento_TABELLA_SCHEDA = "dataInserimento";
    public static final String CAMPO_note_TABELLA_SCHEDA = "note";

    // Tabella ESERCIZI
    public static final String NOME_TABELLA_ESERCIZI = "ESERCIZI";
    public static final String CAMPO_id_TABELLA_ESERCIZI = "_id";
    public static final String CAMPO_idScheda_FK_TABELLA_ESERCIZI = "idScheda_FK";
    public static final String CAMPO_idGruppoMuscolare_TABELLA_ESERCIZI = "idGruppoMuscolare";
    public static final String CAMPO_nomeGruppoMuscolare_TABELLA_ESERCIZI = "nomeGruppoMuscolare";
    public static final String CAMPO_idEsercizio_TABELLA_ESERCIZI = "idEsercizio";
    public static final String CAMPO_nomeEsercizio_TABELLA_ESERCIZI = "nomeEsercizio";
    public static final String CAMPO_serie_TABELLA_ESERCIZI = "serie";
    public static final String CAMPO_recupero_TABELLA_ESERCIZI = "recupero";
    public static final String CAMPO_ordine_TABELLA_ESERCIZI = "ordine";
    public static final String CAMPO_note_TABELLA_ESERCIZI = "note";
    public static final String CAMPO_giorno_TABELLA_ESERCIZI = "giorno";
    public static final String CAMPO_isPiramidale_TABELLA_ESERCIZI = "isPiramidale";
    public static final String CAMPO_bgColor_TABELLA_ESERCIZI = "bgColor";
    public static final String CAMPO_UnitaDiMisura_TABELLA_ESERCIZI = "UnitaDiMisura";
    public static final String CAMPO_Routine_TABELLA_ESERCIZI = "Routine";
    public static final String CAMPO_massimale_TABELLA_ESERCIZI  = "massimale";

    // Tabella RIPETIZIONI_PESI
    public static final String NOME_TABELLA_RIPETIZIONI_PESI = "RIPETIZIONI_PESI";
    public static final String CAMPO_id_TABELLA_RIPETIZIONI_PESI = "_id";
    public static final String CAMPO_idEsercizio_FK_TABELLA_RIPETIZIONI_PESI = "idEsercizio_FK";
    public static final String CAMPO_ripetizioni_TABELLA_RIPETIZIONI_PESI = "ripetizioni";
    public static final String CAMPO_peso_TABELLA_RIPETIZIONI_PESI = "peso";
    public static final String CAMPO_recupero_TABELLA_RIPETIZIONI_PESI = "recupero";
    public static final String CAMPO_percentuale_TABELLA_RIPETIZIONI_PESI = "percentuale";
    public static final String CAMPO_rpe_TABELLA_RIPETIZIONI_PESI = "rpe";

    // Tabella SUPERSET
    public static final String NOME_TABELLA_SUPERSET = "SUPERSET";
    public static final String CAMPO_id_TABELLA_SUPERSET = "_id";
    public static final String CAMPO_guid_TABELLA_SUPERSET = "guid";
    public static final String CAMPO_idEsercizio_FK_TABELLA_SUPERSET = "idEsercizio_FK";
    public static final String CAMPO_isPrimo_TABELLA_SUPERSET = "isPrimo";
    public static final String CAMPO_isUltimo_TABELLA_SUPERSET = "isUltimo";

    // Tabella ESERCIZI_PERSONALI
    public static final String NOME_TABELLA_ESERCIZI_PERSONALI = "ESERCIZI_PERSONALI";
    public static final String CAMPO_id_TABELLA_ESERCIZI_PERSONALI= "_id";
    public static final String CAMPO_nome_TABELLA_ESERCIZI_PERSONALI = "nome";
    public static final String CAMPO_idGruppoMuscolare_TABELLA_ESERCIZI_PERSONALI = "idGruppoMuscolare";

    // Tabella SCHEDA_ATTIVA
    public static final String NOME_TABELLA_SCHEDA_ATTIVA = "SCHEDA_ATTIVA";
    public static final String CAMPO_id_TABELLA_SCHEDA_ATTIVA = "_id";
    public static final String CAMPO_idScheda_FK_TABELLA_SCHEDA_ATTIVA = "idScheda_FK";

    // Tabella DATI_ESERCIZIO_CARDIO
    public static final String NOME_TABELLA_DATI_ESERCIZIO_CARDIO = "DATI_ESERCIZIO_CARDIO";
    public static final String CAMPO_id_TABELLA_DATI_ESERCIZIO_CARDIO = "_id";
    public static final String CAMPO_idEsercizio_FK_TABELLA_DATI_ESERCIZIO_CARDIO = "idEsercizio_FK";
    public static final String CAMPO_programma_TABELLA_DATI_ESERCIZIO_CARDIO = "programma";
    public static final String CAMPO_intensita_TABELLA_DATI_ESERCIZIO_CARDIO = "intensita";
    public static final String CAMPO_pendenza_TABELLA_DATI_ESERCIZIO_CARDIO = "pendenza";
    public static final String CAMPO_velocita_TABELLA_DATI_ESERCIZIO_CARDIO = "velocita";
    public static final String CAMPO_tempo_TABELLA_DATI_ESERCIZIO_CARDIO = "tempo";
}