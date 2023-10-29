package dam.surdubobandreea1091.proiect3;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "Rezervare",
        primaryKeys = { "idCursa", "idPasager" },
        foreignKeys = {
                @ForeignKey(entity = Cursa.class,
                        parentColumns = "id",
                        childColumns = "idCursa"),
                @ForeignKey(entity = Utilizator.class,
                        parentColumns = "id",
                        childColumns = "idPasager")
        })
public class Rezervare {
    private int idCursa;
    private int idPasager;

    public Rezervare(int idCursa, int idPasager) {
        this.idCursa = idCursa;
        this.idPasager = idPasager;
    }

    public int getIdCursa() {
        return idCursa;
    }

    public void setIdCursa(int idCursa) {
        this.idCursa = idCursa;
    }

    public int getIdPasager() {
        return idPasager;
    }

    public void setIdPasager(int idPasager) {
        this.idPasager = idPasager;
    }
}
