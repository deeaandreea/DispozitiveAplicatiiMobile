package dam.surdubobandreea1091.proiect3;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "Utilizator")
public class Utilizator implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "nume")
    private String nume;

    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "telefon")
    private String telefon;
    @ColumnInfo(name = "dataNasterii")
    private Date dataNasterii;
    @ColumnInfo(name = "esteSofer")
    private boolean esteSofer;

    @ColumnInfo(name = "rating")
    private int rating;

    public Utilizator() {
    }

    public Utilizator(int id, String nume, String email, String telefon,
                      Date dataNasterii, boolean esteSofer) {
        this.id = id;
        this.nume = nume;
        this.email = email;
        this.telefon = telefon;
        this.dataNasterii = dataNasterii;
        this.esteSofer = esteSofer;
    }

    protected Utilizator(Parcel in) {
        id = in.readInt();
        nume = in.readString();
        email = in.readString();
        telefon = in.readString();
        String dataNasteriiStr = in.readString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dataNasterii = dateFormat.parse(dataNasteriiStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        esteSofer = in.readByte() != 0;
        rating = in.readInt();
    }

    public static final Creator<Utilizator> CREATOR = new Creator<Utilizator>() {
        @Override
        public Utilizator createFromParcel(Parcel in) {
            return new Utilizator(in);
        }

        @Override
        public Utilizator[] newArray(int size) {
            return new Utilizator[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefon() {
        return telefon;
    }

    public Date getDataNasterii() {
        return dataNasterii;
    }

    public boolean isEsteSofer() {
        return esteSofer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public void setDataNasterii(Date dataNasterii) {
        this.dataNasterii = dataNasterii;
    }

    public void setEsteSofer(boolean esteSofer) {
        this.esteSofer = esteSofer;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(nume);
        parcel.writeString(email);
        parcel.writeString(telefon);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataNasteriiStr = dateFormat.format(dataNasterii);
        parcel.writeString(dataNasteriiStr);
        parcel.writeByte((byte) (esteSofer ? 1 : 0));
        parcel.writeInt(rating);
    }

    @Override
    public String toString() {
        return "Utilizator{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", email='" + email + '\'' +
                ", telefon='" + telefon + '\'' +
                ", dataNasterii=" + dataNasterii +
                ", esteSofer=" + esteSofer +
                ", rating=" + rating +
                '}';
    }
}
