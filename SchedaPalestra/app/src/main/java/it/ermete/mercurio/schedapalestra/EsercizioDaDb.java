package it.ermete.mercurio.schedapalestra;

import java.io.Serializable;
import java.util.ArrayList;

public class EsercizioDaDb implements Serializable {

    private int _id;
    public int getId() {
        return this._id;
    }
    public void setId(int id) {
        this._id = id;
    }

    private int _idScheda_FK;
    public int getIdScheda_FK() {
        return this._idScheda_FK;
    }
    public void setIdScheda_FK(int idScheda_FK) {
        this._idScheda_FK = idScheda_FK;
    }

    private int idGruppoMuscolare;
    public int getIdGruppoMuscolare() {
        return this.idGruppoMuscolare;
    }
    public void setIdGruppoMuscolare(int idGruppoMuscolare) {
        this.idGruppoMuscolare = idGruppoMuscolare;
    }

    private String nomeGruppoMuscolare;
    public String getNomeGruppoMuscolare() {
        return this.nomeGruppoMuscolare;
    }
    public void setNomeGruppoMuscolare(String nomeGruppoMuscolare) {
        this.nomeGruppoMuscolare = nomeGruppoMuscolare;
    }

    private int idEsercizio;
    public int getIdEsercizio() {
        return this.idEsercizio;
    }
    public void setIdEsercizio(int idEsercizio) {
        this.idEsercizio = idEsercizio;
    }

    private String nomeEsercizio;
    public String getNomeEsercizio() {
        return this.nomeEsercizio;
    }
    public void setNomeEsercizio(String nomeEsercizio) {
        this.nomeEsercizio = nomeEsercizio;
    }

    private int serie;
    public int getSerie() {
        return this.serie;
    }
    public void setSerie(int serie) {
        this.serie = serie;
    }

    private float recupero;
    public float getRecupero() {
        return this.recupero;
    }
    public void setRecupero(float recupero) {
        this.recupero = recupero;
    }

    private int ordine;
    public int getOrdine() {
        return this.ordine;
    }
    public void setOrdine(int ordine) {
        this.ordine = ordine;
    }

    private String note;
    public String getNote() {
        return this.note;
    }
    public void setNote(String note) {
        this.note = note;
    }

    private String giorno;
    public String getGiorno() {
        return this.giorno;
    }
    public void setGiorno(String giorno) {
        this.giorno = giorno;
    }

    private int isPiramidale;
    public int getIsPiramidale() {
        return this.isPiramidale;
    }
    public void setIsPiramidale(int isPiramidale) {
        this.isPiramidale = isPiramidale;
    }

    private String unitaDiMisura;
    public String getUnitaDiMisura() {
        return this.unitaDiMisura;
    }
    public void setUnitaDiMisura(String unitaDiMisura) {
        this.unitaDiMisura = unitaDiMisura;
    }

    private String routine;
    public String getRoutine() {
        return this.routine;
    }
    public void setRoutine(String routine) {
        this.routine = routine;
    }

    private float massimale;
    public float getMassimale() {
        return this.massimale;
    }
    public void setMassimale(float massimale) {
        this.massimale = massimale;
    }

    // RIPETIZIONI_PESO (In alternativa a DATI_ESERCIZI_CARDIO)
    private ArrayList<RipetizioniPesoDaDb> ripetizioniPeso;
    public ArrayList<RipetizioniPesoDaDb> getRipetizioniPeso() { return this.ripetizioniPeso; }
    public void setRipetizioniPeso(ArrayList<RipetizioniPesoDaDb> ripetizioniPeso) { this.ripetizioniPeso = ripetizioniPeso; }

    // DATI_ESERCIZI_CARDIO (In alternativa a DATI_ESERCIZI_CARDIO)
    private DatiEsercizioCardioDaDb datiEsercizioCardio;
    public DatiEsercizioCardioDaDb getDatiEsercizioCardio() { return this.datiEsercizioCardio; }
    public void setDatiEsercizioCardio(DatiEsercizioCardioDaDb datiEsercizioCardio) {this.datiEsercizioCardio = datiEsercizioCardio; }

    // SUPERSET
    private SuperSetDaDb superSet;
    public SuperSetDaDb getSuperSet() { return this.superSet; }
    public void setSuperSet(SuperSetDaDb superset) { this.superSet = superset; }

    // BACKGROUNDCOLOR
    private String bgColor;
    public String getBgColor() { return this.bgColor; }
    public void setBgColor(String bgColor) { this.bgColor = bgColor; }

    public EsercizioDaDb(int _id,
                         int idGruppoMuscolare,
                         String nomeGruppoMuscolare,
                         int idEsercizio,
                         String nomeEsercizio,
                         int serie,
                         float recupero,
                         int ordine,
                         String note,
                         String giorno,
                         int isPiramidale,
                         String bgColor,
                         String unitaDiMisura,
                         String routine,
                         float massimale) {
        this.setId(_id);
        this.setIdGruppoMuscolare(idGruppoMuscolare);
        this.setNomeGruppoMuscolare(nomeGruppoMuscolare);
        this.setIdEsercizio(idEsercizio);
        this.setNomeEsercizio(nomeEsercizio);
        this.setSerie(serie);
        this.setRecupero(recupero);
        this.setOrdine(ordine);
        this.setNote(note);
        this.setGiorno(giorno);
        this.setIsPiramidale(isPiramidale);
        this.setBgColor(bgColor);
        this.setUnitaDiMisura(unitaDiMisura);
        this.setRoutine(routine);
        this.setMassimale(massimale);
    }

    public EsercizioDaDb(int _id,
                         int idScheda_FK,
                         int idGruppoMuscolare,
                         String nomeGruppoMuscolare,
                         int idEsercizio,
                         String nomeEsercizio,
                         int serie,
                         float recupero,
                         int ordine,
                         String note,
                         String giorno,
                         int isPiramidale,
                         String bgColor,
                         String unitaDiMisura,
                         String routine,
                         float massimale) {
        this.setId(_id);
        this.setIdScheda_FK(idScheda_FK);
        this.setIdGruppoMuscolare(idGruppoMuscolare);
        this.setNomeGruppoMuscolare(nomeGruppoMuscolare);
        this.setIdEsercizio(idEsercizio);
        this.setNomeEsercizio(nomeEsercizio);
        this.setSerie(serie);
        this.setRecupero(recupero);
        this.setOrdine(ordine);
        this.setNote(note);
        this.setGiorno(giorno);
        this.setIsPiramidale(isPiramidale);
        this.setBgColor(bgColor);
        this.setUnitaDiMisura(unitaDiMisura);
        this.setRoutine(routine);
        this.setMassimale(massimale);
    }

    public EsercizioDaDb(int _id,
                         int idGruppoMuscolare,
                         String nomeGruppoMuscolare,
                         int idEsercizio,
                         String nomeEsercizio,
                         int serie,
                         float recupero,
                         int ordine,
                         String note,
                         String giorno,
                         int isPiramidale,
                         String bgColor,
                         String unitaDiMisura,
                         String routine,
                         float massimale,
                         ArrayList<RipetizioniPesoDaDb> ripetizioniPeso) {
        this.setId(_id);
        this.setIdGruppoMuscolare(idGruppoMuscolare);
        this.setNomeGruppoMuscolare(nomeGruppoMuscolare);
        this.setIdEsercizio(idEsercizio);
        this.setNomeEsercizio(nomeEsercizio);
        this.setSerie(serie);
        this.setRecupero(recupero);
        this.setOrdine(ordine);
        this.setNote(note);
        this.setGiorno(giorno);
        this.setIsPiramidale(isPiramidale);
        this.setBgColor(bgColor);
        this.setUnitaDiMisura(unitaDiMisura);
        this.setRoutine(routine);
        this.setMassimale(massimale);
        this.setRipetizioniPeso(ripetizioniPeso);
    }

    public EsercizioDaDb(int _id,
                         int idScheda_FK,
                         int idGruppoMuscolare,
                         String nomeGruppoMuscolare,
                         int idEsercizio,
                         String nomeEsercizio,
                         int serie,
                         float recupero,
                         int ordine,
                         String note,
                         String giorno,
                         int isPiramidale,
                         String bgColor,
                         String unitaDiMisura,
                         String routine,
                         float massimale,
                         ArrayList<RipetizioniPesoDaDb> ripetizioniPeso) {
        this.setId(_id);
        this.setIdScheda_FK(idScheda_FK);
        this.setIdGruppoMuscolare(idGruppoMuscolare);
        this.setNomeGruppoMuscolare(nomeGruppoMuscolare);
        this.setIdEsercizio(idEsercizio);
        this.setNomeEsercizio(nomeEsercizio);
        this.setSerie(serie);
        this.setRecupero(recupero);
        this.setOrdine(ordine);
        this.setNote(note);
        this.setGiorno(giorno);
        this.setIsPiramidale(isPiramidale);
        this.setBgColor(bgColor);
        this.setUnitaDiMisura(unitaDiMisura);
        this.setRoutine(routine);
        this.setMassimale(massimale);
        this.setRipetizioniPeso(ripetizioniPeso);
    }

    public EsercizioDaDb(int _id,
                         int idGruppoMuscolare,
                         String nomeGruppoMuscolare,
                         int idEsercizio,
                         String nomeEsercizio,
                         int serie,
                         float recupero,
                         int ordine,
                         String note,
                         String giorno,
                         int isPiramidale,
                         String bgColor,
                         String unitaDiMisura,
                         String routine,
                         DatiEsercizioCardioDaDb datiEsercizioCardio) {
        this.setId(_id);
        this.setIdGruppoMuscolare(idGruppoMuscolare);
        this.setNomeGruppoMuscolare(nomeGruppoMuscolare);
        this.setIdEsercizio(idEsercizio);
        this.setNomeEsercizio(nomeEsercizio);
        this.setSerie(serie);
        this.setRecupero(recupero);
        this.setOrdine(ordine);
        this.setNote(note);
        this.setGiorno(giorno);
        this.setIsPiramidale(isPiramidale);
        this.setBgColor(bgColor);
        this.setUnitaDiMisura(unitaDiMisura);
        this.setRoutine(routine);
        this.setDatiEsercizioCardio(datiEsercizioCardio);
    }

    public EsercizioDaDb(int _id,
                         int idScheda_FK,
                         int idGruppoMuscolare,
                         String nomeGruppoMuscolare,
                         int idEsercizio,
                         String nomeEsercizio,
                         int serie,
                         float recupero,
                         int ordine,
                         String note,
                         String giorno,
                         int isPiramidale,
                         String bgColor,
                         String unitaDiMisura,
                         String routine,
                         DatiEsercizioCardioDaDb datiEsercizioCardio) {
        this.setId(_id);
        this.setIdScheda_FK(idScheda_FK);
        this.setIdGruppoMuscolare(idGruppoMuscolare);
        this.setNomeGruppoMuscolare(nomeGruppoMuscolare);
        this.setIdEsercizio(idEsercizio);
        this.setNomeEsercizio(nomeEsercizio);
        this.setSerie(serie);
        this.setRecupero(recupero);
        this.setOrdine(ordine);
        this.setNote(note);
        this.setGiorno(giorno);
        this.setIsPiramidale(isPiramidale);
        this.setBgColor(bgColor);
        this.setUnitaDiMisura(unitaDiMisura);
        this.setRoutine(routine);
        this.setDatiEsercizioCardio(datiEsercizioCardio);
    }
}
