package it.ermete.mercurio.schedapalestra;

import java.io.Serializable;

public class EsercizioDaXml implements Serializable {

    private int _id;
    public int getId(){
        return this._id;
    }
    public void setId(int id){
        this._id = id;
    }

    private String _nome;
    public String getNome(){
        return this._nome;
    }
    public void setNome(String nome){
        this._nome = nome;
    }

    private int _idGruppoMuscolare;
    public int getIdGruppoMuscolare(){
        return this._idGruppoMuscolare;
    }
    public void setIdGruppoMuscolare(int idGruppoMuscolare){
        this._idGruppoMuscolare = idGruppoMuscolare;
    }

    private boolean _isPersonalizzato;
    public boolean getIsPersonalizzato(){
        return this._isPersonalizzato;
    }
    public void setIsPersonalizzato(boolean isPersonalizzato){
        this._isPersonalizzato = isPersonalizzato;
    }

    public EsercizioDaXml(int id, String nome, int idGruppoMuscolare, boolean isPersonalizzato)
    {
        this.setId(id);
        this.setNome(nome);
        this.setIdGruppoMuscolare(idGruppoMuscolare);
        this.setIsPersonalizzato(isPersonalizzato);
    }
}
