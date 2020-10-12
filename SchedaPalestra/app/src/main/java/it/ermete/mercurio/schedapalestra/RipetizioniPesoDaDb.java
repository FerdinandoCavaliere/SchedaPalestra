package it.ermete.mercurio.schedapalestra;

import java.io.Serializable;

public class RipetizioniPesoDaDb implements Serializable {

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

    private String ripetizioni;
    public String getRipetizioni() {
        return this.ripetizioni;
    }
    public void setRipetizioni(String ripetizioni) {
        this.ripetizioni = ripetizioni;
    }

    private float peso;
    public float getPeso() {
        return this.peso;
    }
    public void setPeso(float peso) {
        this.peso = peso;
    }

    private float recupero;
    public float getRecupero() {
        return this.recupero;
    }
    public void setRecupero(float recupero) {
        this.recupero = recupero;
    }

    private float percentuale;
    public float getPercentuale() {
        return this.percentuale;
    }
    public void setPercentuale(float percentuale) {
        this.percentuale = percentuale;
    }

    private int rpe;
    public int getRpe() {
        return this.rpe;
    }
    public void setRpe(int rpe) {
        this.rpe = rpe;
    }

    public RipetizioniPesoDaDb(int _id,
                               String ripetizioni,
                               float peso,
                               float recupero,
                               float percentuale,
                               int rpe) {
        this.setId(_id);
        this.setRipetizioni(ripetizioni);
        this.setPeso(peso);
        this.setRecupero((recupero));
        this.setPercentuale((percentuale));
        this.setRpe((rpe));

    }

    public RipetizioniPesoDaDb(int _id,
                               int idEsercizio_FK,
                               String ripetizioni,
                               float peso,
                               float recupero,
                               float percentuale,
                               int rpe) {
        this.setId(_id );
        this.setIdEsercizio_FK(idEsercizio_FK);
        this.setRipetizioni(ripetizioni);
        this.setPeso(peso);
        this.setRecupero((recupero));
        this.setPercentuale((percentuale));
        this.setRpe((rpe));
    }
}
