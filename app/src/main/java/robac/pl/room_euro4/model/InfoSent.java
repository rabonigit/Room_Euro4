package robac.pl.room_euro4.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.TypeConverters;

import java.util.Objects;

import robac.pl.room_euro4.SQLTypeConverters;

public class InfoSent implements Parcelable {
   // String Key;
//   @TypeConverters(value = SQLTypeConverters.class)
    Long numerZleceniaSamochodowego;
//    @TypeConverters(value = SQLTypeConverters.class)
    String kierowca;
//    @TypeConverters(value = SQLTypeConverters.class)
    String nazwaKlienta;
//    @TypeConverters(value = SQLTypeConverters.class)
    Double masaOdpadu;
//    @TypeConverters(value = SQLTypeConverters.class)
    Long numerZleceniaMarketingowego;
//    @TypeConverters(value = SQLTypeConverters.class)
    String samochod;
//    @TypeConverters(value = SQLTypeConverters.class)
    String kodOdpadu;
//    @TypeConverters(value = SQLTypeConverters.class)
    Integer sentStatus;
//    @TypeConverters(value = SQLTypeConverters.class)
    String kpoId;
//    @TypeConverters(value = SQLTypeConverters.class)
    String sentNumber;

    public InfoSent() {
        this.numerZleceniaSamochodowego = -1L;
        this.kierowca = "Brak";
        this.nazwaKlienta = "Brak";
        this.masaOdpadu = 0d;
        this.numerZleceniaMarketingowego = -1L;
        this.samochod = "Brak";
        this.kodOdpadu = "Brak";
        this.sentStatus = -1;
        this.kpoId = "Brak";
        this.sentNumber = "Brak";
    }

    public InfoSent(Long numerZleceniaSamochodowego, String kierowca, String nazwaKlienta, Double masaOdpadu, Long numerZleceniaMarketingowego, String samochod, String kodOdpadu, Integer sentStatus, String kpoId, String sentNumber) {
        this.numerZleceniaSamochodowego = numerZleceniaSamochodowego;
        this.kierowca = kierowca;
        this.nazwaKlienta = nazwaKlienta;
        this.masaOdpadu = masaOdpadu;
        this.numerZleceniaMarketingowego = numerZleceniaMarketingowego;
        this.samochod = samochod;
        this.kodOdpadu = kodOdpadu;
        this.sentStatus = sentStatus;
        this.kpoId = kpoId;
        this.sentNumber = sentNumber;
    }

    protected InfoSent(Parcel in) {
        if (in.readByte() == 0) {
            numerZleceniaSamochodowego = null;
        } else {
            numerZleceniaSamochodowego = in.readLong();
        }
        kierowca = in.readString();
        nazwaKlienta = in.readString();
        if (in.readByte() == 0) {
            masaOdpadu = null;
        } else {
            masaOdpadu = in.readDouble();
        }
        if (in.readByte() == 0) {
            numerZleceniaMarketingowego = null;
        } else {
            numerZleceniaMarketingowego = in.readLong();
        }
        samochod = in.readString();
        kodOdpadu = in.readString();
        if (in.readByte() == 0) {
            sentStatus = null;
        } else {
            sentStatus = in.readInt();
        }
        kpoId = in.readString();
        sentNumber = in.readString();
    }

    public static final Creator<InfoSent> CREATOR = new Creator<InfoSent>() {
        @Override
        public InfoSent createFromParcel(Parcel in) {
            return new InfoSent(in);
        }

        @Override
        public InfoSent[] newArray(int size) {
            return new InfoSent[size];
        }
    };

    public Long getNumerZleceniaSamochodowego() {
        return numerZleceniaSamochodowego;
    }

    public void setNumerZleceniaSamochodowego(Long numerZleceniaSamochodowego) {
        this.numerZleceniaSamochodowego = numerZleceniaSamochodowego;
    }

    public String getKierowca() {
        return kierowca;
    }

    public void setKierowca(String kierowca) {
        this.kierowca = kierowca;
    }

    public String getNazwaKlienta() {
        return nazwaKlienta;
    }

    public void setNazwaKlienta(String nazwaKlienta) {
        this.nazwaKlienta = nazwaKlienta;
    }

    public Double getMasaOdpadu() {
        return masaOdpadu;
    }

    public void setMasaOdpadu(Double masaOdpadu) {
        this.masaOdpadu = masaOdpadu;
    }

    public Long getNumerZleceniaMarketingowego() {
        return numerZleceniaMarketingowego;
    }

    public void setNumerZleceniaMarketingowego(Long numerZleceniaMarketingowego) {
        this.numerZleceniaMarketingowego = numerZleceniaMarketingowego;
    }

    public String getSamochod() {
        return samochod;
    }

    public void setSamochod(String samochod) {
        this.samochod = samochod;
    }

    public String getKodOdpadu() {
        return kodOdpadu;
    }

    public void setKodOdpadu(String kodOdpadu) {
        this.kodOdpadu = kodOdpadu;
    }

    public Integer getSentStatus() {
        return sentStatus;
    }

    public void setSentStatus(Integer sentStatus) {
        this.sentStatus = sentStatus;
    }

    public String getKpoId() {
        return kpoId;
    }

    public void setKpoId(String kpoId) {
        this.kpoId = kpoId;
    }

    public String getSentNumber() {
        return sentNumber;
    }

    public void setSentNumber(String sentNumber) {
        this.sentNumber = sentNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InfoSent)) return false;
        InfoSent infoSent = (InfoSent) o;
        return getSentNumber().trim().equals(infoSent.getSentNumber().trim());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSentNumber());
    }

    @Override
    public String toString() {
        return "InfoSent{" +
                "numerZleceniaSamochodowego=" + numerZleceniaSamochodowego +
                ", kierowca='" + kierowca + '\'' +
                ", nazwaKlienta='" + nazwaKlienta + '\'' +
                ", masaOdpadu=" + masaOdpadu +
                ", numerZleceniaMarketingowego=" + numerZleceniaMarketingowego +
                ", samochod='" + samochod + '\'' +
                ", kodOdpadu='" + kodOdpadu + '\'' +
                ", sentStatus=" + sentStatus +
                ", kpoId='" + kpoId + '\'' +
                ", sentNumber='" + sentNumber + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (numerZleceniaSamochodowego == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(numerZleceniaSamochodowego);
        }
        parcel.writeString(kierowca);
        parcel.writeString(nazwaKlienta);
        if (masaOdpadu == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(masaOdpadu);
        }
        if (numerZleceniaMarketingowego == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(numerZleceniaMarketingowego);
        }
        parcel.writeString(samochod);
        parcel.writeString(kodOdpadu);
        if (sentStatus == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(sentStatus);
        }
        parcel.writeString(kpoId);
        parcel.writeString(sentNumber);
    }
}
