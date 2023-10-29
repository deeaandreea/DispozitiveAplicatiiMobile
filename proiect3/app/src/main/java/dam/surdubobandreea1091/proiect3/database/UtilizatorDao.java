package dam.surdubobandreea1091.proiect3.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dam.surdubobandreea1091.proiect3.Utilizator;

@Dao
public interface UtilizatorDao {
    @Insert
    void insert(Utilizator utilizator);

    @Query("Select * from Utilizator")
    List<Utilizator> selectAll();

    @Update
    void update(Utilizator utilizator);

    @Query("Delete from Utilizator")
    void deleteAll();

    @Delete
    int delete(Utilizator utilizator);
}
