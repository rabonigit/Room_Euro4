package robac.pl.room_euro4.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;
import java.util.Objects;

import robac.pl.room_euro4.GPS.GPS;
import robac.pl.room_euro4.MainActivity;
import robac.pl.room_euro4.SQLTypeConverters;


@Entity(tableName = "wiadomosci")
public class Message implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "qrcode")
    private String qrcode;
    @ColumnInfo(name = "komentarz")
    private String content;
    @ColumnInfo(name = "isbdo")
    private Boolean isBdo;
    @ColumnInfo(name = "isfirebase")
    private Boolean isFirebase;
    @ColumnInfo(name = "isRabbitMQ")
    private Boolean isRabbitMQ;
    @ColumnInfo(name = "phonenumber")
    private String phoneNumber;
    @TypeConverters(value = SQLTypeConverters.class)
    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    private Date timestamp;
    @TypeConverters(value = SQLTypeConverters.class)
    @ColumnInfo(name = "pojemnikiinfo")
    private PojemnikiInfo pojemnikiInfo;
    @TypeConverters(value = SQLTypeConverters.class)
    @ColumnInfo(name = "gps")
    private GPS gps;

    protected Message(Parcel in) {
        id = in.readLong();
        qrcode = in.readString();
        content = in.readString();
        byte tmpIsBdo = in.readByte();
        isBdo = tmpIsBdo == 0 ? null : tmpIsBdo == 1;
        byte tmpIsFirebase = in.readByte();
        isFirebase = tmpIsFirebase == 0 ? null : tmpIsFirebase == 1;
        byte tmpIsRabbitMQ = in.readByte();
        isRabbitMQ = tmpIsRabbitMQ == 0 ? null : tmpIsRabbitMQ == 1;
        phoneNumber = in.readString();
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    public Boolean getRabbitMQ() {
        return isRabbitMQ;
    }

    public void setRabbitMQ(Boolean rabbitMQ) {
        isRabbitMQ = rabbitMQ;
    }




    //    public Message( String qrcode, String sentnumber, Boolean isFirebase) {
//
//        this.qrcode = qrcode;
//        this.sentnumber = sentnumber;
//        this.isFirebase = isFirebase;
//        this.timestamp = new Date();
//    }
    @Ignore
    public Message(String qrcode, String content, Boolean isBdo, Boolean isFirebase, String phoneNumber,Boolean isRabbitMQ) {
        this.qrcode = qrcode;
        this.content = content;
        this.isBdo = isBdo;
        this.isFirebase = isFirebase;
        this.timestamp = new Date();
        this.phoneNumber = phoneNumber;
        this.pojemnikiInfo = new PojemnikiInfo();
        this.isRabbitMQ=isRabbitMQ;
    }

    @Ignore
    Message(String qrcode, String content, Boolean isBdo, Boolean isFirebase, String phoneNumber, PojemnikiInfo pojemnikiInfo, GPS gps,Boolean isRabbitMQ) {
        this.qrcode = qrcode;
        this.content = content;
        this.isBdo = isBdo;
        this.isFirebase = isFirebase;
        this.timestamp = new Date();
        this.phoneNumber = phoneNumber;
        this.pojemnikiInfo = pojemnikiInfo;
        this.gps = gps;
        this.isRabbitMQ=isRabbitMQ;
    }

    @Ignore
    public Message(String qrcode, String content) {
        this.qrcode = qrcode;
        this.content = content;
        this.isFirebase = false;
//        this.id=id;
        this.timestamp = new Date();
    }

    public Message() {
        isFirebase = false;
        isBdo = false;

        this.id = 0;
        this.qrcode = "Brak";
        this.content = "Brak";


        this.phoneNumber = MainActivity.PhoneNumber;
        this.timestamp = new Date();
        this.pojemnikiInfo = new PojemnikiInfo();
        this.gps = new GPS(0.0,0.0);
    }

//    public Message(Parcel in) {
//        id = in.readLong();
//        qrcode = in.readString();
//        sentnumber = in.readString();
//        byte tmpIsBdo = in.readByte();
//        isBdo = tmpIsBdo == 0 ? null : tmpIsBdo == 1;
//        byte tmpIsFirebase = in.readByte();
//        isFirebase = tmpIsFirebase == 0 ? null : tmpIsFirebase == 1;
//        phoneNumber = in.readString();
//        byte tmpIsRabbitMQ = in.readByte();
//        isRabbitMQ=tmpIsRabbitMQ == 0 ? null : tmpIsRabbitMQ == 1;
//
//
//    }

    //    public Message(String qrcode, String sentnumber, Boolean isBdo, Boolean isFirebase,String phoneNumber,PojemnikiInfo pojemnikiInfo) {
//        this.qrcode = qrcode;
//        this.sentnumber = sentnumber;
//        this.isBdo = isBdo;
//        this.isFirebase = isFirebase;
//        this.timestamp = new Date();
//        this.phoneNumber=phoneNumber;
//        this.pojemnikiInfo= pojemnikiInfo;
//    }
    public PojemnikiInfo getPojemnikiInfo() {
        return pojemnikiInfo;
    }

    public void setPojemnikiInfo(PojemnikiInfo pojemnikiInfo) {
        this.pojemnikiInfo = pojemnikiInfo;
    }

    public Boolean getBdo() {
        return isBdo;
    }

    public void setBdo(Boolean bdo) {
        isBdo = bdo;
    }
//    public Message(long id, String receiverPhoneNumber, String sentnumber) {
//        this.id = id;
//        this.receiverPhoneNumber = receiverPhoneNumber;
//        this.sentnumber = sentnumber;
//    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Boolean getFirebase() {
        return isFirebase;
    }

    public void setFirebase(Boolean firebase) {
        isFirebase = firebase;
    }

    public String getContent() {
        return content;
    }



    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return getId() == message.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public GPS getGps() {
        return gps;
    }

    public void setGps(GPS gps) {
        this.gps = gps;
    }

    public void set(Message ob) {
        this.id = ob.getId();
        this.qrcode = ob.getQrcode();
        this.content = ob.getContent();
        this.isBdo = ob.getBdo();
        this.isFirebase = ob.getFirebase();
        this.phoneNumber = ob.getPhoneNumber();
        this.timestamp = ob.getTimestamp();
        this.pojemnikiInfo = ob.getPojemnikiInfo();
        this.isRabbitMQ=ob.getRabbitMQ();
        this.gps = ob.getGps();
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", qrcode='" + qrcode + '\'' +
                ", sentnumber='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Creates and returns a copy of this object.  The precise meaning
     * of "copy" may depend on the class of the object. The general
     * intent is that, for any object {@code x}, the expression:
     * <blockquote>
     * <pre>
     * x.clone() != x</pre></blockquote>
     * will be true, and that the expression:
     * <blockquote>
     * <pre>
     * x.clone().getClass() == x.getClass()</pre></blockquote>
     * will be {@code true}, but these are not absolute requirements.
     * While it is typically the case that:
     * <blockquote>
     * <pre>
     * x.clone().equals(x)</pre></blockquote>
     * will be {@code true}, this is not an absolute requirement.
     * <p>
     * By convention, the returned object should be obtained by calling
     * {@code super.clone}.  If a class and all of its superclasses (except
     * {@code Object}) obey this convention, it will be the case that
     * {@code x.clone().getClass() == x.getClass()}.
     * <p>
     * By convention, the object returned by this method should be independent
     * of this object (which is being cloned).  To achieve this independence,
     * it may be necessary to modify one or more fields of the object returned
     * by {@code super.clone} before returning it.  Typically, this means
     * copying any mutable objects that comprise the internal "deep structure"
     * of the object being cloned and replacing the references to these
     * objects with references to the copies.  If a class contains only
     * primitive fields or references to immutable objects, then it is usually
     * the case that no fields in the object returned by {@code super.clone}
     * need to be modified.
     * <p>
     * The method {@code clone} for class {@code Object} performs a
     * specific cloning operation. First, if the class of this object does
     * not implement the interface {@code Cloneable}, then a
     * {@code CloneNotSupportedException} is thrown. Note that all arrays
     * are considered to implement the interface {@code Cloneable} and that
     * the return type of the {@code clone} method of an array type {@code T[]}
     * is {@code T[]} where T is any reference or primitive type.
     * Otherwise, this method creates a new instance of the class of this
     * object and initializes all its fields with exactly the contents of
     * the corresponding fields of this object, as if by assignment; the
     * contents of the fields are not themselves cloned. Thus, this method
     * performs a "shallow copy" of this object, not a "deep copy" operation.
     * <p>
     * The class {@code Object} does not itself implement the interface
     * {@code Cloneable}, so calling the {@code clone} method on an object
     * whose class is {@code Object} will result in throwing an
     * exception at run time.
     *
     * @return a clone of this instance.
     * @throws CloneNotSupportedException if the object's class does not
     *                                    support the {@code Cloneable} interface. Subclasses
     *                                    that override the {@code clone} method can also
     *                                    throw this exception to indicate that an instance cannot
     *                                    be cloned.
     * @see Cloneable
     */
    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(id);
        dest.writeString(qrcode);
        dest.writeString(content);
        dest.writeByte((byte) (isBdo == null ? 0 : isBdo ? 1 : 2));
        dest.writeByte((byte) (isFirebase == null ? 0 : isFirebase ? 1 : 2));
        dest.writeByte((byte) (isRabbitMQ == null ? 0 : isRabbitMQ ? 1 : 2));
        dest.writeString(phoneNumber);
    }


}
