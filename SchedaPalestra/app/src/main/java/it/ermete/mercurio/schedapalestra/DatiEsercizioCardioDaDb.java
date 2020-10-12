package it.ermete.mercurio.schedapalestra;

import java.io.Serializable;

public class DatiEsercizioCardioDaDb implements Serializable {

    private int _id;
    public int getId() {
        return this._id;
    }
    public void setId(int id) {
        this._id = id;
    }

    private int _idEsercizio_FK;
    public int getIdEsercizio_FK() {
        return this._idEsercizio_FK;
    }
    public void setIdEsercizio_FK(int idScheda_FK) {
        this._idEsercizio_FK = idScheda_FK;
    }

    private String programma;
    public String getProgramma() { return this.programma; }
    public void setProgramma(String programma) { this.programma = programma; }

    private String intensita;
    public String getIntensita() {
        return this.intensita;
    }
    public void setIntensita(String intensita) {
        this.intensita = intensita;
    }

    private String pendenza;
    public String getPendenza() {
        return this.pendenza;
    }
    public void setPendenza(String pendenza) {
        this.pendenza = pendenza;
    }

    private float velocita;
    public float getVelocita() {
        return this.velocita;
    }
    public void setVelocita(float velocita) {
        this.velocita = velocita;
    }

    private float tempo;
    public float getTempo() {
        return this.tempo;
    }
    public void setTempo(float tempo) {
        this.tempo = tempo;
    }

    public DatiEsercizioCardioDaDb(int _id,
                                   String programma,
                                   String intensita,
                                   String pendenza,
                                   float velocita,
                                   float tempo) {
        this.setId(_id);
        this.setProgramma(programma);
        this.setIntensita(intensita);
        this.setPendenza(pendenza);
        this.setVelocita(velocita);
        this.setTempo(tempo);
    }

    public DatiEsercizioCardioDaDb(int _id,
                                   int idEsercizio_FK,
                                   String intensita,
                                   String pendenza,
                                   float velocita,
                                   float tempo) {
        this.setId(_id);
        this.setIdEsercizio_FK(idEsercizio_FK);
        this.setProgramma(programma);
        this.setIntensita(intensita);
        this.setPendenza(pendenza);
        this.setVelocita(velocita);
        this.setTempo(tempo);
    }
}
