package it.ermete.mercurio.schedapalestra;

import java.io.Serializable;
import java.util.Date;

public class SchedaDaDb implements Serializable {

    private int _id;
    public int getId() {
        return this._id;
    }
    public void setId(int id) {
        this._id = id;
    }

    private Date data;
    public Date getData() { return this.data; }
    public void setData(Date data) {
        this.data = data;
    }

    private String note;
    public String getNote() { return this.note; }
    public void setNote(String note) { this.note = note; }

    public SchedaDaDb(int _id, Date data, String note) {
        this.setId(_id);
        this.setData(data);
        this.setNote(note);
    }
}
