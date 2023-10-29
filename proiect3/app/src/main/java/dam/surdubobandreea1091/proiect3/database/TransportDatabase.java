package dam.surdubobandreea1091.proiect3.database;

import dam.surdubobandreea1091.proiect3.Cursa;
import dam.surdubobandreea1091.proiect3.Rezervare;
import dam.surdubobandreea1091.proiect3.Utilizator;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Cursa.class, Utilizator.class, Rezervare.class},
        version = 1)
@TypeConverters(DateConverter.class)
public abstract class TransportDatabase extends RoomDatabase {

    public abstract UtilizatorDao getUtilizatorDao();
    public abstract CursaDao getCursaDao();
    public abstract RezervareDao getRezervareDao();
}
