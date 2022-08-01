package robac.pl.room_euro4.Util;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import robac.pl.room_euro4.MainActivity;
import robac.pl.room_euro4.model.Message;

public class Util {

    public static Message getQrCode_Info(@NonNull String data,String phoneNumber)
    {
        boolean pojemnik=false;
        String data_out=data;
        if(data.contains("www.robac.pl"))
        {
            pojemnik=true;
        }
        Log.d("Util1","getQrCode_Info="+data+"pojemnik="+pojemnik);
        Message out=new Message("Brak","Brak",false,false,phoneNumber,false);

        try {
        String[] arrOfStr = data_out.split(",", 2);

        String cos = arrOfStr[0];
        if(!pojemnik&&cos.contains("CardId")) {
            arrOfStr = cos.split(":", 2);
            out.setQrcode(arrOfStr[1].replace("\"",""));
            out.setBdo(true);
            out.setGps(MainActivity.obgps);
        }else
        if(cos.contains("qrId")&&pojemnik){
            arrOfStr = cos.split(":", 2);
            out.setQrcode(arrOfStr[1].replace("\"",""));
            out.setBdo(false);
            out.setGps(MainActivity.obgps);
        }
        else out.setQrcode("Brak");
        }catch(ArrayIndexOutOfBoundsException xx)
        {
            out.setQrcode("Brak");

        }
        Log.d("Util2","getQrCode_Info="+out+"pojemnik="+pojemnik);
        return out;
    }
    public static String getProperty(String key, Context context) throws IOException {
        Properties properties = new Properties();;
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open("rabbit.properties");
        properties.load(inputStream);
        return properties.getProperty(key);

    }

}
