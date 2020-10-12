package it.ermete.mercurio.schedapalestra;

import java.io.Serializable;

public class GruppoMuscolareDaXml implements Serializable {

    private int _id;
    public int getId(){
        return this._id;
    }
    public void setId(int id){
        this._id = id;
    }

    private String _gruppo;
    public String getGruppo(){
        return this._gruppo;
    }
    public void setGruppo(String gruppo){
        this._gruppo = gruppo;
    }

    public GruppoMuscolareDaXml(int id, String gruppo)
    {
        this.setId(id);
        this.setGruppo(gruppo);
    }

}
