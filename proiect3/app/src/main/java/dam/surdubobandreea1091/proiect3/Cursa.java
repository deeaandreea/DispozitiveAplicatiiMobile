package dam.surdubobandreea1091.proiect3;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity(tableName = "Cursa")
public class Cursa implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int idSofer;
    @ColumnInfo(name = "plecare")
    private String plecare;
    @ColumnInfo(name = "destinatie")
    private String destinatie;
    @ColumnInfo(name = "dataPlecare")
    private Date dataPlecare;
    @ColumnInfo(name = "oraPlecare")
    private String oraPlecare;
    @ColumnInfo(name = "nrPasageri")
    private int nrPasageri;
    @ColumnInfo(name = "pretPerPasager")
    private double pretPerPasager;
    @ColumnInfo(name = "acceptaAnimaleCompanie")
    private boolean acceptaAnimaleCompanie;

    public Cursa(String plecare, String destinatie, Date dataPlecare, String oraPlecare,
                 int nrPasageri, double pretPerPasager, boolean acceptaAnimaleCompanie) {
        this.plecare = plecare;
        this.destinatie = destinatie;
        this.dataPlecare = dataPlecare;
        this.oraPlecare = oraPlecare;
        this.nrPasageri = nrPasageri;
        this.pretPerPasager = pretPerPasager;
        this.acceptaAnimaleCompanie = acceptaAnimaleCompanie;
    }

    protected Cursa(Parcel in) {
        plecare = in.readString();
        destinatie = in.readString();
        String dataPlecareStr = in.readString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dataPlecare = dateFormat.parse(dataPlecareStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        oraPlecare = in.readString();
        nrPasageri = in.readInt();
        pretPerPasager = in.readDouble();
        acceptaAnimaleCompanie = in.readByte() != 0;
    }

    public static final Creator<Cursa> CREATOR = new Creator<Cursa>() {
        @Override
        public Cursa createFromParcel(Parcel in) {
            return new Cursa(in);
        }

        @Override
        public Cursa[] newArray(int size) {
            return new Cursa[size];
        }
    };

    @Override
    public String toString() {
        return "Cursa{" +
                "plecare='" + plecare + '\'' +
                ", destinatie='" + destinatie + '\'' +
                ", dataPlecare=" + dataPlecare +
                ", oraPlecare=" + oraPlecare +
                ", nrPasageri=" + nrPasageri +
                ", pretPerPasager=" + pretPerPasager +
                ", acceptaAnimaleCompanie=" + acceptaAnimaleCompanie +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int flags) {
        parcel.writeString(plecare);
        parcel.writeString(destinatie);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataPlecareStr = dateFormat.format(dataPlecare);
        parcel.writeString(dataPlecareStr);
        parcel.writeString(String.valueOf(oraPlecare));
        parcel.writeInt(nrPasageri);
        parcel.writeDouble(pretPerPasager);
        parcel.writeByte((byte) (acceptaAnimaleCompanie ? 1 : 0));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSofer() {
        return idSofer;
    }

    public void setIdSofer(int idSofer) {
        this.idSofer = idSofer;
    }

    public String getPlecare() {
        return plecare;
    }

    public void setPlecare(String plecare) {
        this.plecare = plecare;
    }

    public String getDestinatie() {
        return destinatie;
    }

    public void setDestinatie(String destinatie) {
        this.destinatie = destinatie;
    }

    public Date getDataPlecare() {
        return dataPlecare;
    }

    public void setDataPlecare(Date dataPlecare) {
        this.dataPlecare = dataPlecare;
    }

    public String getOraPlecare() {
        return oraPlecare;
    }

    public void setOraPlecare(String oraPlecare) {
        this.oraPlecare = oraPlecare;
    }

    public int getNrPasageri() {
        return nrPasageri;
    }

    public void setNrPasageri(int nrPasageri) {
        this.nrPasageri = nrPasageri;
    }

    public double getPretPerPasager() {
        return pretPerPasager;
    }

    public void setPretPerPasager(double pretPerPasager) {
        this.pretPerPasager = pretPerPasager;
    }

    public boolean isAcceptaAnimaleCompanie() {
        return acceptaAnimaleCompanie;
    }

    public void setAcceptaAnimaleCompanie(boolean acceptaAnimaleCompanie) {
        this.acceptaAnimaleCompanie = acceptaAnimaleCompanie;
    }
}
