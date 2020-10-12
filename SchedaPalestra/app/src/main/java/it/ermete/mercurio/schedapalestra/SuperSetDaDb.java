package it.ermete.mercurio.schedapalestra;

import java.io.Serializable;

public class SuperSetDaDb implements Serializable {

    private int _id;
    public int getId() {
        return this._id;
    }
    public void setId(int id) {
        this._id = id;
    }

    private String guid;
    public String getGuid() {
        return this.guid;
    }
    public void setGuid(String guid) {
        this.guid = guid;
    }

    private int _idEsercizio_FK;
    public int getIdEsercizio_FK() {
        return this._idEsercizio_FK;
    }
    public void setIdEsercizio_FK(int idScheda_FK) {
        this._idEsercizio_FK = idScheda_FK;
    }

    private int isPrimo;
    public int getIsPrimo() {
        return this.isPrimo;
    }
    public void setIsPrimo(int isPrimoInSuperset) {
        this.isPrimo = isPrimoInSuperset;
    }

    private int isUltimo;
    public int getIsUltimo() {
        return this.isUltimo;
    }
    public void setIsUltimo(int isUltimo) {
        this.isUltimo = isUltimo;
    }

    public SuperSetDaDb(int _id,
                        String guid,
                        int idEsercizio_FK,
                        int isPrimo,
                        int isUltimo) {
        this.setId(_id);
        this.setGuid(guid);
        this.setIdEsercizio_FK(idEsercizio_FK);
        this.setIsPrimo(isPrimo);
        this.setIsUltimo(isUltimo);
    }
}
