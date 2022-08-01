package robac.pl.room_euro4;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import robac.pl.room_euro4.GPS.GPS;
import robac.pl.room_euro4.model.PojemnikiInfo;

public class SQLTypeConverters {

//    @TypeConverter
//    public Date fromTimestamp(String value) {
//        Date defaultDate = new Date();
//        SimpleDateFormat format = new SimpleDateFormat();
//        try {
//            return value.equals("") ? defaultDate : format.parse(value);
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return defaultDate;
//        }
//    }
//
//    @TypeConverter
//    public String toTimestamp(Date date) {
//        if (date == null) {
//            return "";
//        } else {
//            return date.toString();
//        }
//    }

    @TypeConverter
    public Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }

    @TypeConverter
    public String toPojemnikString(PojemnikiInfo data) {
        Gson gson = new Gson();
        if (data == null) {
            return ";";
        }
//        else{return (gson.toJson(gson.toJson(data.getPojemnikiInfos())));}
        else {
            return (gson.toJson(data.getPojemniki()) + ";" + gson.toJson(data.getPojemnikiInfos()));
        }

    }

    @TypeConverter
    public PojemnikiInfo toStringPojemnikiInfo(String data) {
        Gson gson = new Gson();
        if (data == null) {
            return new PojemnikiInfo();
        } else {
//            String[] Pojemniki = gson.fromJson((new PojemnikiInfo().getPojemniki(),String[].class);
//            boolean[] PojemnikiInfos = gson.fromJson(data, boolean[].class);

            String[] Pojemniki = gson.fromJson(data.split(";")[0], String[].class);
            boolean[] PojemnikiInfos = gson.fromJson(data.split(";")[1], boolean[].class);
            return new PojemnikiInfo(Pojemniki, PojemnikiInfos);
        }

    }

    @TypeConverter
    public String toMessageString(robac.pl.room_euro4.model.Message data) {
        GsonBuilder builder = new GsonBuilder();
        builder.setVersion(2.0);
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        Gson gsonx = builder.create();
        return gsonx.toJson(data);

    }

//    @TypeConverter
    public  static robac.pl.room_euro4.model.InfoSent toStringInfoSent(String data) {
        if (data == null) return new robac.pl.room_euro4.model.InfoSent();
        else {
            GsonBuilder builder = new GsonBuilder();
            builder.setVersion(2.0);

            Gson gsonx = builder.create();
            return gsonx.fromJson(data, robac.pl.room_euro4.model.InfoSent.class);

        }
    }

    @TypeConverter
    public robac.pl.room_euro4.model.Message toStringMessage(String data) {
        if (data == null) return new robac.pl.room_euro4.model.Message();
        else {
            GsonBuilder builder = new GsonBuilder();
            builder.setVersion(2.0);

            Gson gsonx = builder.create();
            return gsonx.fromJson(data, robac.pl.room_euro4.model.Message.class);

        }
    }

    @TypeConverter
    public String toStringGPS(GPS ob) {
        if (ob == null) return "{Latitude:0.0,Longitude:0.0}";
        else {
            GsonBuilder builder = new GsonBuilder();
            builder.setVersion(2.0);
            Gson gsonx = builder.create();
            return gsonx.toJson(ob);
        }
    }

    @TypeConverter
    public GPS toGPSString(String data) {
        if (data == null) return new GPS(0d, 0d);
        else {
            GsonBuilder builder = new GsonBuilder();
            builder.setVersion(2.0);

            Gson gsonx = builder.create();
            return gsonx.fromJson(data, GPS.class);
        }

    }

}
