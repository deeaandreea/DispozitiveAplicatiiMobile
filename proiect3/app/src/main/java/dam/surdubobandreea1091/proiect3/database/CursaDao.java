package dam.surdubobandreea1091.proiect3.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dam.surdubobandreea1091.proiect3.Cursa;

@Dao
public interface CursaDao {
    @Insert
    void insert(Cursa cursa);

    @Query("Select * from Cursa")
    List<Cursa> selectAll();

    @Update
    void update(Cursa cursa);

    @Query("Delete from Cursa")
    void deleteAll();

    @Delete
    int delete(Cursa cursa);

    @Query("SELECT * FROM Cursa WHERE idSofer=:idUtilizator")
    List<Cursa> selectCurseUtilizator(int idUtilizator);
}
