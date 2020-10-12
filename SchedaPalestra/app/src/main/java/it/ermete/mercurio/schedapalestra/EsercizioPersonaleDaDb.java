package it.ermete.mercurio.schedapalestra;

import java.io.Serializable;

public class EsercizioPersonaleDaDb implements Serializable {

    private int _id;
    public int getId() {
        return _id;
    }
    public void setId(int _id) {
        this._id = _id;
    }

    private String nome;
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    private int idGruppoMuscolare;
    public int getIdGruppoMuscolare() {
        return idGruppoMuscolare;
    }
    public void setIdGruppoMuscolare(int idGruppoMuscolare) {
        this.idGruppoMuscolare = idGruppoMuscolare;
    }

    private String nomeGruppoMuscolare;
    public String getNomeGruppoMuscolare() {
        return nomeGruppoMuscolare;
    }
    public void setNomeGruppoMuscolare(String nomeGruppoMuscolare) {
        this.nomeGruppoMuscolare = nomeGruppoMuscolare;
    }

    public EsercizioPersonaleDaDb(int _id, String nome, int idGruppoMuscolare, String nomeGruppoMuscolare) {
        this._id = _id;
        this.nome = nome;
        this.idGruppoMuscolare = idGruppoMuscolare;
        this.nomeGruppoMuscolare = nomeGruppoMuscolare;
    }
}
