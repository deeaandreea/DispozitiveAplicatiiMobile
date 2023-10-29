package dam.surdubobandreea1091.proiect3.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import dam.surdubobandreea1091.proiect3.Cursa;
import dam.surdubobandreea1091.proiect3.Rezervare;
import dam.surdubobandreea1091.proiect3.Utilizator;

@Dao
public interface RezervareDao {
    @Insert
    void insert(Rezervare rezervare);

    @Query("SELECT * FROM Rezervare")
    List<Rezervare> selectAll();

    @Query("SELECT * FROM Rezervare WHERE idCursa=:idCursa AND idPasager=:idPasager")
    List<Rezervare> findById(int idCursa, int idPasager);

    @Query("SELECT Utilizator.* FROM Utilizator" +
            " INNER JOIN Rezervare ON Rezervare.idPasager=Utilizator.id" +
            " WHERE idCursa=:idCursa")
    List<Utilizator> getUtilizatoriCursa(int idCursa);

    @Query("SELECT Cursa.* FROM Cursa" +
            " INNER JOIN Rezervare ON Rezervare.idCursa=Cursa.id" +
            " WHERE idPasager=:idUtilizator")
    List<Cursa> getCurseUtilizator(int idUtilizator);
}
