package robac.pl.room_euro4;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.SEND_SMS;
import static android.Manifest.permission.VIBRATE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Location;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase;
import com.github.pwittchen.reactivewifi.ReactiveWifi;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialPickerConfig;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import robac.pl.room_euro4.GPS.GPS;
import robac.pl.room_euro4.GPS.GpsTracker;
import robac.pl.room_euro4.Util.Util;
import robac.pl.room_euro4.dao.MessageDao;
import robac.pl.room_euro4.database.AppDatabase;
import robac.pl.room_euro4.databinding.ActivityMainBinding;
import robac.pl.room_euro4.model.Answer;
import robac.pl.room_euro4.model.InfoSent;
import robac.pl.room_euro4.model.Message;
import robac.pl.room_euro4.model.QueueingConsumer;
import robac.pl.room_euro4.sent.SentholderItem;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_ID = 101;
    private static final int RESOLVE_HINT = 1;
    private final static int PERMISSION_CODE = 10;
    private static final int CREDENTIAL_PICKER_REQUEST = 1;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    // rabbit stop
    private static final String TAG = "MainActivity";
    //wifi
    public static WifiManager wifi;
    //rabbit start
    public static String RABBIT_MQ_SERVER_LOGIN = "";
    public static String RABBIT_MQ_SERVER_PASSWORD = "";
    public static String RABBIT_MQ_SERVER_ADDRESS = "";
    public static String RABBIT_MQ_SERVER_PORT = "";
    public static int Licznik = 0;
    //tel
    public static String PhoneNumber = "";
    // obsluga GPS
    public static GPS obgps;
    //obsluga room
    public static int Liczba_Poj_OK, Liczba_Poj_No;
    public static AppDatabase appDatabase;
    public static MessageDao messageDao;
    public static Boolean GPS_OK = true;
    public static List<InfoSent> ListaSent = new ArrayList<>();
    public static List<SentholderItem> ListaholderItem = new ArrayList<>();

    private final BlockingDeque<Message> queue = new LinkedBlockingDeque<Message>();
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final CompositeDisposable compositeDisposables = new CompositeDisposable();
    private final List<String> items = new ArrayList<>();
    public NavController navController;
    ConnectionFactory factory = new ConnectionFactory();
    Thread subscribeThread;
    Thread publishThread;
    private Disposable rabbitLoader;
    private io.reactivex.disposables.Disposable firebaseLoader;
    private Message obArgumnt;
    private TextView mToolbarTitle;
    private io.reactivex.disposables.Disposable firebase;
    private AppBarConfiguration appBarConfiguration;
    private DatabaseReference databasefirabase;
    private NavController action_setting;
    private ActivityMainBinding binding;
    private ArrayAdapter<String> adapter;

//  public static  PojemnikiInfo statusModel;

    public static void sendSms(String sms) {

//        Observable.just(messageDao).subscribeOn(Schedulers.io()).map(db -> {
//            db.insert(new Message(phoneEditText.getText().toString(), smsToSendContent.getText().toString()));
//            List<Message> messages = db.getMessagesByReceiver(phoneEditText.getText().toString());
//            List<String> messagesContent = new ArrayList<>();
//            for (Message message : messages) {
//                messagesContent.add(message.getContent());
//            }
//            return new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messagesContent);
//        }).observeOn(AndroidSchedulers.mainThread()).subscribe(stringArrayAdapter -> messagesListView.setAdapter(stringArrayAdapter));

        if (sms.length() < 161) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("516923777", null, sms, null, null);
            smsManager.sendTextMessage("602656082", null, sms, null, null);

            //     Toast.makeText(getApplicationContext(), "Wiadomość wysłana", Toast.LENGTH_LONG).show();
        } else {
            SmsManager smsManager = SmsManager.getDefault();
            List<String> parts = partString(sms);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                smsManager.sendMultipartTextMessage("516923777", null, parts, null, null, 0);
                smsManager.sendMultipartTextMessage("602656082", null, parts, null, null, 0);
            }
//            Toast.makeText(getApplicationContext(), "Wiadomość wysłana", Toast.LENGTH_LONG).show();
        }
    }

    public static List<String> partString(String str) {
        int j = 0;
        List<String> parts = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            if (i % 50 == 0) {
                String part = str.substring(j, i);
                j = i;
                parts.add(part);
            }
        }
        return parts;
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "baza-wiadomosci").build();
        messageDao = appDatabase.messageDao();

        //Aktualizacja info o pojemnikach
        //Uaktualnij inform
        Observable.just(messageDao).subscribeOn(Schedulers.io()).map(db -> {
            List<Message> messages = db.getAllnotSendRabbit(false);
            return messages.size();
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(listNieWyslanych -> {
                    Liczba_Poj_No = listNieWyslanych;

                },
                error -> Log.e(TAG, "Brak dostepu do info db.getAllnotSendRabbit= " + error.getMessage()));
        // koniec aktualizacji o pojemnikach

        mToolbarTitle = findViewById(R.id.textView3);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        action_setting=  Navigation.findNavController(this, R.id.action_settings);

//rabbits
        try {
            RABBIT_MQ_SERVER_LOGIN = Util.getProperty("RABBIT_MQ_SERVER_LOGIN", getApplicationContext());
            RABBIT_MQ_SERVER_PASSWORD = Util.getProperty("RABBIT_MQ_SERVER_PASSWORD", getApplicationContext());
            RABBIT_MQ_SERVER_ADDRESS = Util.getProperty("RABBIT_MQ_SERVER_ADDRESS", getApplicationContext());
            RABBIT_MQ_SERVER_PORT = Util.getProperty("RABBIT_MQ_SERVER_PORT", getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }


        // true or false to activate/deactivate wifi
        setupConnectionFactory();
        publishToAMQP();
        //rabbit
        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(false);
//        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
        //stop wifi

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {

                Log.d(TAG, "destination(= " + destination.getLabel() + "|" + destination.getNavigatorName() + " }");
                Log.d(TAG, "destination.getId(= " + arguments + " |" + (arguments != null ? arguments.deepCopy().containsKey("messageUpdate") : ":"));
                if (arguments != null && arguments.deepCopy().containsKey("messageUpdate")) {
//                 Bundle xxx = new Bundle(arguments);
//                 Message obxxx = new Message(xxx.getParcelable("messageUpdate"));
//                    Log.d(TAG, "obxxx= ");
                }
                if (arguments != null && arguments.deepCopy().containsKey("messageUpdate")) {

//                    obArgumnt = new Message(arguments.deepCopy().getParcelable("messageUpdate"));
//   Message obxxx= Message.CREATOR.createFromParcel(arguments.getParcelableArray("messageUpdate").length);

                    //KomentarzFragmentArgs.fromBundle(arguments.deepCopy().getBundle("messageUpdate")).getMessage();
//                            new Message(arguments.getParcelable("messageUpdate"));
                    Bundle xxxy = new Bundle(arguments);
//                    Message obxxx=new Message(xxx.getParcelable("messageUpdate"));
//                    Log.d(TAG, "destination(= " + xxxy.getParcelable("messageUpdate") + " }");

                    Message obxxxy = xxxy.getParcelable("messageUpdate");
//                    Log.d(TAG, "destination(P= " + obxxxy + " }");
                }

                //   destination.getAction(R.id.action_QrCodeFragment_to_CommentFragment).

            }
        });




        if (checkPermission()) {
            // if permission is already granted display a toast message
            Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }
        String androidId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        UUID androidId_UUID = null;
        androidId_UUID = UUID.nameUUIDFromBytes(androidId.getBytes(StandardCharsets.UTF_8));
        PhoneNumber = androidId_UUID.toString();
        Log.d(TAG, "androidId_UUID=" + androidId_UUID);
        //    Toast.makeText(MainActivity.this, "androidId_UUID=" + androidId_UUID, Toast.LENGTH_LONG).show();

//        if (PhoneNumber.length() < 3) {
//            try {
//                requestHint();
//            } catch (IntentSender.SendIntentException e) {
//                e.printStackTrace();
//            }
//        }
        ReactiveWifi.observeWifiAccessPointChanges(MainActivity.this)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(wifiInfo -> {
                    Log.d(TAG, "wifiInfo= " + wifiInfo);
                    // do something with wifiInfo
                });

        ReactiveWifi.observeWifiStateChange(MainActivity.this)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(WifiState -> {
                    Log.d(TAG, "WifiState= " + WifiState);
                    // do something with wifiInfo
                });
        ReactiveWifi.observeWifiSignalLevel(MainActivity.this, 100)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(observeWifiSignalLevel -> {
                    Log.d(TAG, "observeWifiSignalLevel= " + observeWifiSignalLevel);
                    // do something with wifiInfo
                });
      
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

//start sms
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                    sendSms();
//                }
                //stopsms
                Observable.just(messageDao).subscribeOn(Schedulers.io()).map(db -> {
                    List<Message> messages = db.getAllnotSendRabbit(false);
                    return messages;
                }).observeOn(AndroidSchedulers.mainThread()).subscribe(stringArrayAdapter -> {
                            for (Message x : stringArrayAdapter
                            ) {
                                publishMessage(x);

                            }
                            RabbitLooper();
                        },
                        error -> Log.e(TAG, "Couldn't load Messages  QrCodeFragmentOnResume= " + error.getMessage()));
            }


        });
//start



      
        //stop
        //start  handler
//        @SuppressLint("HandlerLeak") final Handler incomingMessageHandler = new Handler() {
//            @RequiresApi(api = Build.VERSION_CODES.R)
//            @Override
//            public void handleMessage(android.os.Message msg) {
//                String message = msg.getData().getString("msg");
//                Log.d(TAG, "message1= " + message);
//
//                GsonBuilder builder = new GsonBuilder();
//                builder.setVersion(2.0);
//
//                Gson gsonx = builder.create();
//                Answer ob = gsonx.fromJson(message, Answer.class);
//
//
//                Log.d(TAG, "message2= ob " + ob.getId() + " :" + ob.getStatus());
//                Observable.just(messageDao).subscribeOn(Schedulers.io()).map(db -> {
//                    Message ustawRabbitOk = new Message();
//                    List<Message> rabbitOk = db.getSingle(ob.getId());
//
//                    Log.d(TAG, "rabbitOk.size " + rabbitOk.size());
//                    if (rabbitOk.size() != 1) {
//                        Log.d(TAG, "Odebrano   Wiadomość3 = " + ob.getStatus() + "|" + ob.getStatus().trim().contains("sms"));
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                            Log.d(TAG, "Odebrano   Wiadomość4 = " + ob.getStatus() + "|" + ob.getStatus().trim().contains("sms"));
//                            if (ob.getStatus().trim().contains("sms")) {
//                                Log.d(TAG, "Odebrano   Wiadomość5 = " + ob.getStatus() + "|" + ob.getStatus().trim().contains("sms"));
//                                Log.d(TAG, "sendSms() = ");
//                                sendSms();
//                            }
//                        }
//                    } else {
//
//                        ustawRabbitOk = rabbitOk.get(0);
//                        if (!ob.getStatus().equalsIgnoreCase("Ok")) {
//                        } else {
//                            ustawRabbitOk.setRabbitMQ(true);
//                            disposables.add(
//
//                                    messageDao.update(ustawRabbitOk)
//                                            .subscribeOn(io.reactivex.schedulers.Schedulers.io())
//                                            .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
//                                            .subscribe(
//                                                    () -> {
//                                                        //start  uaktualnij licznik
//
//                                                        //stop
//
//                                                        Log.d(TAG, "Odebrano   Wiadomość = " + rabbitOk.get(0).getQrcode());
//                                                    },
//                                                    error -> Log.e(TAG, "Couldn't Update  Wiadomość = " + error.toString())
//                                            )
//                            );
//                        }
//                    }
//
//                    return ustawRabbitOk;
//                }).observeOn(AndroidSchedulers.mainThread()).subscribe(changeRabbit -> {
//
//
//                        },
//                        error -> {
//                            Log.e(TAG, "Couldn't update  " + error.getMessage());
//
//                        }
//                );
//
//
//            }
//        };
        //koniec handler


        Observable.just(messageDao).subscribeOn(Schedulers.io()).map(db -> {
            List<Message> messages = db.getAllnotSendRabbit(false);
            return messages.size();
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(liczbaNieWyslanych -> {
                    Liczba_Poj_No = liczbaNieWyslanych;

                },
                error -> Log.e(TAG, "Problem z listą  db.getAllnotSendRabbit(false)= " + error.getMessage()));
        Observable.just(messageDao).subscribeOn(Schedulers.io()).map(db -> {
            List<Message> messages = db.getAllnotSendRabbit(true);
            return messages.size();
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(liczbaWyslanych -> {
                    Liczba_Poj_OK = liczbaWyslanych;

                },
                error -> Log.e(TAG, "Problem z listą  db.getAllnotSendRabbit(false)= " + error.getMessage()));

        databasefirabase = FirebaseDatabase.getInstance().getReference();
        Log.d("Fire", "Start xx="+(databasefirabase.getRoot()));
//        databasefirabase.child(PhoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            if(snapshot.getKey().equalsIgnoreCase(PhoneNumber))
//            {
//                Log.d("Fire", "Wynik2x=: " + snapshot.getKey());
//                updateItems2(snapshot.getChildren());
//            }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.w("Fire", "Failed to read value.", error.toException());
//            }
//        });
//        firebaseLoader = RxFirebaseDatabase
//                .dataChanges(databasefirabase)
//                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
//
//                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
//                .subscribe(data -> Log.d("Wynik1=", data.getValue().toString() + firebaseLoader.toString()), error -> Log.e("Problem", error.getMessage()));
//        //start
//        Log.d("Fire", "Wynik2=: " + PhoneNumber);
//        databasefirabase.child(PhoneNumber).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                //  String value = dataSnapshot.getValue(String.class);
//
//            if(dataSnapshot.getKey().equalsIgnoreCase(PhoneNumber))
//            {
//                Log.d("Fire", "Wynik2x=: " + dataSnapshot.getKey());
//                updateItems2(dataSnapshot.getChildren());
//            }
////                Log.d("Fire", "Wynik2y=: " + dataSnapshot.getChildren());
//
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w("Fire", "Failed to read value.", error.toException());
//            }
//        });//ok
        //stop
        // Get keys
        io.reactivex.Observable<DataSnapshot> dataSnapshotObservable = RxFirebaseDatabase
                .dataChanges(databasefirabase.child(PhoneNumber));

        dataSnapshotObservable.subscribeOn(io.reactivex.schedulers.Schedulers.io());
        dataSnapshotObservable.observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread());

        io.reactivex.Observable
                .timer(1, TimeUnit.SECONDS)
                .subscribe(time -> firebase = dataSnapshotObservable
                        .subscribe(
                                data -> {
//                                    Log.d("Wynik3=", data.getKey());

//                                    updateItems(data.getChildren());
                                   updateItems2(data.getChildren());
                                },
                                error -> Log.e(TAG, error.getMessage())
                        ));
    }// stop on Create

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void sendSms() {
        if (ContextCompat.checkSelfPermission(this, SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        } else {
            sendSms(PhoneNumber);
        }
    }

    private void updateItems(Iterable<DataSnapshot> children) {
        //  items.clear();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        Map<String, InfoSent> TablicaSent = new TreeMap<String, InfoSent>();


        for (DataSnapshot data : children) {
//            Log.d("Wynik=", PhoneNumber+"\t data.getKey="+data.getKey());
            if (!data.getKey().contains(PhoneNumber)) continue;

//    Log.d("Wynikxx=","Liczba="+data.getChildrenCount()+"-"+data.getKey() + " <=> " + data.get);
            int licznik = 0;


            for (DataSnapshot data2 : data.getChildren()) {
                InfoSent obSent = new InfoSent();
//                Log.d("Wynikxy=", "Liczba?=" + data.getChildrenCount() + "-" + data2.getKey() + " <=> " + data2.getValue());
                Scanner scanner = new Scanner(data2.getValue().toString());
                scanner.useDelimiter("=");

                licznik = 0;
                while (scanner.hasNext()) {
                    licznik++;
                    String wiersz = scanner.next();

                    if (licznik < 2) {
                        continue;
                    }


                    Scanner tokenob = new Scanner(wiersz);
                    tokenob.useDelimiter(",");
//                    Log.d("Wynikxyz=",tokenob.next()+"Licznik="+licznik);
                    switch (licznik) {
                        case 2:
//
                            if (tokenob.hasNextInt()) {

                                obSent.setNumerZleceniaSamochodowego(tokenob.nextLong());
//                                Log.d("Wynikxyz=", "Licznik" + licznik + "-" + obSent.getNumerZleceniaSamochodowego());
                            }

                            break;
                        case 3:

                            obSent.setKierowca(tokenob.next());
//                            Log.d("Wynikxyz=", "Licznik" + licznik + "-" + obSent.getKierowca());
                            break;
                        case 4:
                            if (tokenob.hasNextDouble()) {

                                obSent.setMasaOdpadu(tokenob.nextDouble());
//                                Log.d("Wynikxyz=", obSent.getMasaOdpadu().toString());
                            } else {
                                String tym = tokenob.next();
                                try {
                                    double masa = Double.parseDouble(tym);
                                    obSent.setMasaOdpadu(masa);
                                } catch (NumberFormatException ex) {
                                    tym.replace(".", ",");
                                    obSent.setMasaOdpadu(Double.parseDouble(tym));
                                }
//                                Log.d("Wynikxyzggg=", "Licznik" + licznik + "-" + obSent.getMasaOdpadu().toString());

                            }
                            break;
                        case 5:

                            if (tokenob.hasNextLong()) {
                                obSent.setNumerZleceniaMarketingowego(tokenob.nextLong());
                            }
//                            Log.d("Wynikxyz=", "Licznik" + licznik + "-" + obSent.getNumerZleceniaMarketingowego());
                            break;
                        case 6:
                            obSent.setSamochod(tokenob.next());
//                            Log.d("Wynikxyz=", "Licznik" + licznik + "-" + obSent.getSamochod());
                            break;
                        case 7:
                            obSent.setKodOdpadu(tokenob.next());
//                            Log.d("Wynikxyz=", "Licznik" + licznik + "-" + obSent.getKodOdpadu());
                            break;
                        case 8:
                            obSent.setNazwaKlienta(tokenob.next().trim());
//                            Log.d("Wynikxyz=", "Licznik" + licznik + "-" + obSent.getNazwaKlienta());
                            break;
                        case 9:
                            obSent.setKpoId(tokenob.next().trim());
//                            Log.d("Wynikxyz=", "Licznik" + licznik + "-" + obSent.getKpoId());
                            break;
                        case 10:
                            obSent.setSentNumber(tokenob.next().trim());
//                            Log.d("Wynikxyz=", "Licznik" + licznik + "-" + obSent.getSentNumber());
                            break;
                        case 11:
                            if (tokenob.hasNextInt()) {
                                obSent.setSentStatus(tokenob.nextInt());
                            } else {
                                String tym = tokenob.next().replace("}", "");
                                int wynik = Integer.parseInt(tym);
                                obSent.setSentStatus(wynik);
//                                Log.d("Wynikxyz=", "Licznik" + licznik + "-" + obSent.getSentStatus());
                            }


//                            Log.d("Wynikxyz=", "Licznik" + licznik + "-" + obSent.getSentStatus());
                            break;

                    }

                }
//                    robac.pl.room_euro4.model.InfoSent ob=     SQLTypeConverters.toStringInfoSent( data2.getValue().toString());
//                    Log.d("Wynikxyzl=",ob.toString());
                TablicaSent.put(obSent.getSentNumber(), obSent);
//                if (TablicaSent.size()==0||!TablicaSent.containsKey(obSent.getSentNumber())) {
//                    TablicaSent.put(obSent.getSentNumber(), obSent);
//                    Log.d("Wynik obSent=", "" + TablicaSent.get(obSent.getSentNumber()));
//
//                }
               // Log.d("Wynik obSent=", "" + TablicaSent.get(obSent.getSentNumber()));

            }




        }

        Set<Map.Entry<String,InfoSent>>set=TablicaSent.entrySet();

        Log.d("Wynik obSent=", "size="+TablicaSent.size());
        ListaSent.clear();

        int licznik=0;
        for (Map.Entry<String, InfoSent> me:set) {
            String key = me.getKey();
            InfoSent tym = me.getValue();
            Log.d("Wynik obSent=", "key="+key+"-"+tym);
            if(tym.getSentStatus()==2){
            ListaSent.add(licznik++,tym);}



//            if(!wynik) {ListaSent.add(tym);}
        }
        final int[] licz_poz = {0};
        ListaholderItem.clear();
io.reactivex.Observable<InfoSent> fromIterable = io.reactivex.Observable.fromIterable(ListaSent);
io.reactivex.disposables.Disposable listPrinter = fromIterable.subscribe(item ->  {ListaholderItem.add(new SentholderItem(String.valueOf(licz_poz[0] +1),item.getSentNumber()+"-"+item.getKodOdpadu(),item));
    licz_poz[0]++;});

        compositeDisposable.add(listPrinter);
        Log.d("Wynik ostateczny=", "********* size"+ListaSent.size());
//if(SentFragment.recyclerView!=null)
//{SentFragment.recyclerView.setAdapter(new MySentRecyclerViewAdapter(ListaholderItem));}


//        ListaholderItem.clear();
//        for (int i = 0; i <ListaSent.size() ; i++) {
//            Log.d("Wynik ostateczny=", "" + ListaSent.get(i));
//            ListaholderItem.add(new SentholderItem(String.valueOf(i+1),ListaSent.get(i).getSentNumber()+"-"+ListaSent.get(i).getKodOdpadu(),ListaSent.get(i)));
//        }

    }

synchronized     private void updateItems2(Iterable<DataSnapshot> children) {
        //  items.clear();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        Map<String, InfoSent> TablicaSent = new TreeMap<String, InfoSent>();




//    Log.d("Wynikxx=","Liczba="+data.getChildrenCount()+"-"+data.getKey() + " <=> " + data.get);
            int licznik = 0;


            for (DataSnapshot data2 : children) {
                InfoSent obSent = new InfoSent();
//                Log.d("Wynikxy=", "Liczba?=" + data.getChildrenCount() + "-" + data2.getKey() + " <=> " + data2.getValue());
                Scanner scanner = new Scanner(data2.getValue().toString());
                scanner.useDelimiter("=");

                licznik = 0;
                while (scanner.hasNext()) {
                    licznik++;
                    String wiersz = scanner.next();

                    if (licznik < 2) {
                        continue;
                    }


                    Scanner tokenob = new Scanner(wiersz);
                    tokenob.useDelimiter(",");
//                    Log.d("Wynikxyz=",tokenob.next()+"Licznik="+licznik);
                    switch (licznik) {
                        case 2:
//
                            if (tokenob.hasNextInt()) {

                                obSent.setNumerZleceniaSamochodowego(tokenob.nextLong());
//                                Log.d("Wynikxyz=", "Licznik" + licznik + "-" + obSent.getNumerZleceniaSamochodowego());
                            }

                            break;
                        case 3:

                            obSent.setKierowca(tokenob.next());
//                            Log.d("Wynikxyz=", "Licznik" + licznik + "-" + obSent.getKierowca());
                            break;
                        case 4:
                            if (tokenob.hasNextDouble()) {

                                obSent.setMasaOdpadu(tokenob.nextDouble());
//                                Log.d("Wynikxyz=", obSent.getMasaOdpadu().toString());
                            } else {
                                String tym = tokenob.next();
                                try {
                                    double masa = Double.parseDouble(tym);
                                    obSent.setMasaOdpadu(masa);
                                } catch (NumberFormatException ex) {
                                    tym.replace(".", ",");
                                    obSent.setMasaOdpadu(Double.parseDouble(tym));
                                }
//                                Log.d("Wynikxyzggg=", "Licznik" + licznik + "-" + obSent.getMasaOdpadu().toString());

                            }
                            break;
                        case 5:

                            if (tokenob.hasNextLong()) {
                                obSent.setNumerZleceniaMarketingowego(tokenob.nextLong());
                            }
//                            Log.d("Wynikxyz=", "Licznik" + licznik + "-" + obSent.getNumerZleceniaMarketingowego());
                            break;
                        case 6:
                            obSent.setSamochod(tokenob.next());
//                            Log.d("Wynikxyz=", "Licznik" + licznik + "-" + obSent.getSamochod());
                            break;
                        case 7:
                            obSent.setKodOdpadu(tokenob.next());
//                            Log.d("Wynikxyz=", "Licznik" + licznik + "-" + obSent.getKodOdpadu());
                            break;
                        case 8:
                            obSent.setNazwaKlienta(tokenob.next().trim());
//                            Log.d("Wynikxyz=", "Licznik" + licznik + "-" + obSent.getNazwaKlienta());
                            break;
                        case 9:
                            obSent.setKpoId(tokenob.next().trim());
//                            Log.d("Wynikxyz=", "Licznik" + licznik + "-" + obSent.getKpoId());
                            break;
                        case 10:
                            obSent.setSentNumber(tokenob.next().trim());
//                            Log.d("Wynikxyz=", "Licznik" + licznik + "-" + obSent.getSentNumber());
                            break;
                        case 11:
                            if (tokenob.hasNextInt()) {
                                obSent.setSentStatus(tokenob.nextInt());
                            } else {
                                String tym = tokenob.next().replace("}", "");
                                int wynik = Integer.parseInt(tym);
                                obSent.setSentStatus(wynik);
//                                Log.d("Wynikxyz=", "Licznik" + licznik + "-" + obSent.getSentStatus());
                            }


//                            Log.d("Wynikxyz=", "Licznik" + licznik + "-" + obSent.getSentStatus());
                            break;

                    }

                }
//                    robac.pl.room_euro4.model.InfoSent ob=     SQLTypeConverters.toStringInfoSent( data2.getValue().toString());
//                    Log.d("Wynikxyzl=",ob.toString());
                TablicaSent.put(obSent.getSentNumber(), obSent);
//                if (TablicaSent.size()==0||!TablicaSent.containsKey(obSent.getSentNumber())) {
//                    TablicaSent.put(obSent.getSentNumber(), obSent);
//                    Log.d("Wynik obSent=", "" + TablicaSent.get(obSent.getSentNumber()));
//
//                }
                // Log.d("Wynik obSent=", "" + TablicaSent.get(obSent.getSentNumber()));

            }






        Set<Map.Entry<String,InfoSent>>set=TablicaSent.entrySet();

        Log.d("Wynik obSent=", "size="+TablicaSent.size());
        ListaSent.clear();

        int licznik2=0;
        for (Map.Entry<String, InfoSent> me:set) {
            String key = me.getKey();
            InfoSent tym = me.getValue();
            Log.d("Wynik obSent=", "key="+key+"-"+tym);
            if(tym.getSentStatus()==2){
                ListaSent.add(licznik2++,tym);}



//            if(!wynik) {ListaSent.add(tym);}
        }
        final int[] licz_poz = {0};
        ListaholderItem.clear();
        io.reactivex.Observable<InfoSent> fromIterable = io.reactivex.Observable.fromIterable(ListaSent);
        io.reactivex.disposables.Disposable listPrinter = fromIterable.subscribe(item ->  {ListaholderItem.add(new SentholderItem(String.valueOf(licz_poz[0] +1),item.getSentNumber()+"-"+item.getKodOdpadu(),item));
            licz_poz[0]++;});

        compositeDisposable.add(listPrinter);
        Log.d("Wynik ostateczny=", "********* size"+ListaSent.size());
//if(SentFragment.recyclerView!=null)
//{SentFragment.recyclerView.setAdapter(new MySentRecyclerViewAdapter(ListaholderItem));}


//        ListaholderItem.clear();
//        for (int i = 0; i <ListaSent.size() ; i++) {
//            Log.d("Wynik ostateczny=", "" + ListaSent.get(i));
//            ListaholderItem.add(new SentholderItem(String.valueOf(i+1),ListaSent.get(i).getSentNumber()+"-"+ListaSent.get(i).getKodOdpadu(),ListaSent.get(i)));
//        }

    }

    private void requestHint() throws IntentSender.SendIntentException {
        HintRequest hintRequest = new HintRequest.Builder()
                .setHintPickerConfig(new CredentialPickerConfig.Builder()

                        .setShowCancelButton(false)
                        .build())
                .setPhoneNumberIdentifierSupported(true)

                .build();
        PendingIntent intent = Credentials.getClient(this).getHintPickerIntent(hintRequest);
        startIntentSenderForResult(intent.getIntentSender(),
                RESOLVE_HINT, null, 0, 0, 0);
    }

    private void requestPermission() {
        //this method is to request the runtime permission.
        int PERMISSION_REQUEST_CODE = 200;
        ActivityCompat.requestPermissions(this, new String[]{CAMERA, VIBRATE, SEND_SMS}, PERMISSION_REQUEST_CODE);
    }

    private boolean checkPermission() {
        // here we are checking two permission that is vibrate and camera which is granted by user and not.
        // if permission is granted then we are returning true otherwise false.
        int camera_permission = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int vibrate_permission = ContextCompat.checkSelfPermission(getApplicationContext(), VIBRATE);
        int send_sms = ContextCompat.checkSelfPermission(getApplicationContext(), SEND_SMS);


        return camera_permission == PackageManager.PERMISSION_GRANTED && vibrate_permission == PackageManager.PERMISSION_GRANTED && send_sms == PackageManager.PERMISSION_GRANTED;
    }

    private boolean askForPermission(String permission, Integer requestCode) {
        boolean permissions = false;
        if (ActivityCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {
                // mGpsTv.setText(R.string.we_need_permission);
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
                permissions = false;
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
                permissions = true;
            }
        }
        return permissions;
    }

    //rabbit
    private void setupConnectionFactory() {
//        String uri = "amqp://192.168.15.20";
        String uri = "amqp://" + RABBIT_MQ_SERVER_ADDRESS;
        try {
            factory.setAutomaticRecoveryEnabled(false);
            factory.setUri(uri);
            factory.setPort(Integer.parseInt(RABBIT_MQ_SERVER_PORT));
            factory.setUsername(RABBIT_MQ_SERVER_LOGIN);
            factory.setPassword(RABBIT_MQ_SERVER_PASSWORD);

        } catch (KeyManagementException | NoSuchAlgorithmException | URISyntaxException e1) {
            e1.printStackTrace();
        }
    }

    public void publishToAMQP() {
        publishThread = new Thread(new Runnable() {
            boolean wynik = true;

            @Override
            public void run() {
                while (true) {
                    try {
                        Connection connection = factory.newConnection();

                        Channel ch = connection.createChannel();
                        ch.confirmSelect();
                        AMQP.Queue.DeclareOk q = ch.queueDeclare("pojemnikipaterek", true, false, false, null);

                        while (true) {
                            Message message = queue.takeFirst();
                            try {
//                                Toast.makeText(MainActivity.this,q.getMessageCount(), Toast.LENGTH_SHORT).show();

                                // ch.basicPublish("amq.fanout", "chat", null, message.getBytes());

                                ch.basicPublish("", q.getQueue(), null, new SQLTypeConverters().toMessageString(message).getBytes());
                                Log.d("", "[s] " + message);
                                ch.waitForConfirmsOrDie();

                            } catch (Exception e) {
                                Log.d(TAG, "[f] " + message);
                                wynik = false;
                                queue.putFirst(message);
                                rabbitLoader = io.reactivex.rxjava3.core.Observable.create(emitter -> {

                                            emitter.onNext(binding.fab);// Symulacja pobierania danych

                                            // emitter.onNext(R.drawable.image_1080_720);
                                            //   emitter.onNext(R.drawable.logoutp);
                                            emitter.onComplete();

                                        }
                                ).subscribeOn(Schedulers.io()) // Operacje powyzej wykonaja sie w tle
                                        .observeOn(AndroidSchedulers.mainThread()) // Zmiana zdjecia wykona sie w watku glownym
                                        .subscribe(img -> binding.fab.setBackgroundColor(Color.BLACK));
                                throw e;
                            }
                            Log.d(TAG, "[Count]= " + q.getMessageCount());
                            //start a
                            rabbitLoader = io.reactivex.rxjava3.core.Observable.create(emitter -> {

                                        emitter.onNext(binding.fab);// Symulacja pobierania danych

                                        // emitter.onNext(R.drawable.image_1080_720);
                                        //   emitter.onNext(R.drawable.logoutp);
                                        emitter.onComplete();

                                    }
                            ).subscribeOn(Schedulers.io()) // Operacje powyzej wykonaja sie w tle
                                    .observeOn(AndroidSchedulers.mainThread()) // Zmiana zdjecia wykona sie w watku glownym
                                    .subscribe(img -> binding.fab.setBackgroundColor(Color.BLUE));

                            //stop
                            wynik = true;
                        }

                    } catch (InterruptedException e) {
                        break;
                    } catch (Exception e) {
                        Log.d("", "Connection broken: " + e.getClass().getName());
                        try {
                            Thread.sleep(500); //sleep and then try again
                        } catch (InterruptedException e1) {
                            break;
                        }
                    }
                }
            }
        });
        publishThread.start();
    }

    void publishMessage(Message message) {
        try {
            Log.d("publishMessage", "[q] " + message);
            queue.putLast(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void RabbitLooper() {
        Thread th = new Thread() {

            public void run() {
//                System.out.println("Start Looper...");
                Log.d(TAG,"Start czytanie Rabbit");
                //  if(th==null &&!(th.isAlive()))return;

                // Prepare looper
                Looper.prepare();

                // Register Queue listener hook
                MessageQueue queue = Looper.myQueue();
                queue.addIdleHandler(new MessageQueue.IdleHandler() {
                    int mReqCount = 0;

                    @Override
                    public boolean queueIdle() {
                        if (++mReqCount == 2) {
                            // Quit looper
                            Looper.myLooper().quit();
                            return false;
                        } else
                            return true;
                    }
                });

                // Show Toast- will be called when Looper.loop() starts
                subscribe(new Handler() {
                    @RequiresApi(api = Build.VERSION_CODES.R)
                    @Override
                    public void handleMessage(android.os.Message msg) {
                        String message = msg.getData().getString("msg");
                        Log.d(TAG, "message1= " + message);

                        GsonBuilder builder = new GsonBuilder();
                        builder.setVersion(2.0);

                        Gson gsonx = builder.create();
                        Answer ob = gsonx.fromJson(message, Answer.class);

//if (ob.getId()>0&&ob.getStatus().contains())
                        Log.d(TAG, "message2= ob " + ob.getId() + " :" + ob.getStatus());
                        Observable.just(messageDao).subscribeOn(Schedulers.io()).map(db -> {
                            Message ustawRabbitOk = new Message();
                            List<Message> rabbitOk = db.getSingle(ob.getId());

                            Log.d(TAG, "rabbitOk.size " + rabbitOk.size());
                            if (rabbitOk.size() != 1) {
                                Log.d(TAG, "Odebrano   Wiadomość3 = " + ob.getStatus() + "|" + ob.getStatus().trim().contains("sms"));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                    Log.d(TAG, "Odebrano   Wiadomość4 = " + ob.getStatus() + "|" + ob.getStatus().trim().contains("sms"));
                                    if (ob.getStatus().trim().contains("sms")) {
                                        Log.d(TAG, "Odebrano   Wiadomość5 = " + ob.getStatus() + "|" + ob.getStatus().trim().contains("sms"));
                                        Log.d(TAG, "sendSms() = ");
                                        sendSms();
                                    }
                                }
                            } else {

                                ustawRabbitOk = rabbitOk.get(0);
                                if (!ob.getStatus().equalsIgnoreCase("Ok")) {
                                } else {
                                    ustawRabbitOk.setRabbitMQ(true);
                                    disposables.add(

                                            messageDao.update(ustawRabbitOk)
                                                    .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                                                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                                                    .subscribe(
                                                            () -> {
                                                                //start  uaktualnij licznik

                                                                //stop

                                                                Log.d(TAG, "Odebrano   Wiadomość = " + rabbitOk.get(0).getQrcode());
                                                            },
                                                            error -> Log.e(TAG, "Couldn't Update  Wiadomość = " + error.toString())
                                                    )
                                    );
                                }
                            }

                            return ustawRabbitOk;
                        }).observeOn(AndroidSchedulers.mainThread()).subscribe(changeRabbit -> {




                                },
                                error -> {
                                    Log.e(TAG, "Couldn't update  " + error.getMessage());

                                }
                        );


                    }
                });
//                Toast.makeText(MainActivity.this, "Hey there!!",
//                        Toast.LENGTH_LONG).show();
                // Start looping Message Queue- Blocking call
                Looper.loop();
//                System.out.println("It appears after Looper.myLooper().quit()");

            }

        };
        th.start();
    }
    void subscribe(final Handler handler) {
        subscribeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Connection connection = factory.newConnection();
                        Channel channel = connection.createChannel();
//                        channel.basicQos(1);
                        channel.confirmSelect();
                        Log.d(TAG, "1 odpowiedź rabbit dla "+PhoneNumber);
                        AMQP.Queue.DeclareOk q = channel.queueDeclare(PhoneNumber.trim(), true, false, false, null);
//                        AMQP.Queue.DeclareOk q= channel.queueDeclare();
                        //        channel.queueBind( "amq.fanout",q.getQueue(), "chat");
//                        channel.queueBind(q.getQueue(), "amq.fanout", "chat");
                        Log.d(TAG, "2 odpowiedź rabbit dla "+PhoneNumber);
                        QueueingConsumer consumer = new QueueingConsumer(channel);

                        channel.basicConsume(q.getQueue(), true, consumer);
                        Log.d(TAG, "3odpowiedź rabbit dla "+PhoneNumber);
                        while (true) {
                            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                            String message = new String(delivery.getBody());
                            Log.d("odbrane", "[r] " + message);
                            android.os.Message msg = handler.obtainMessage();
                            Bundle bundle = new Bundle();
                            bundle.putString("msg", message);
                            msg.setData(bundle);
                            handler.sendMessage(msg);

                            Thread.sleep(200); //sleep and then try again


                        }
                    } catch (InterruptedException e) {
                        break;
                    } catch (Exception e1) {
                        Log.d("", "Connection broken: " + e1.getClass().getName());
                        try {
                            Thread.sleep(5000); //sleep and then try again
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                }
            }
        });
        subscribeThread.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(true);

        askForPermission(Manifest.permission.ACCESS_FINE_LOCATION, PERMISSION_ID);
        Observable<GPS> getGPS_ID = Observable.create(emitter -> {
                    emitter.onNext(updateGps());

                    emitter.onComplete();

                }
        );
        getGPS_ID.subscribe(v -> Log.d(TAG, "v.getLatitude: " + v.getLatitude() + "\t " + v.getLongitude()));
        getGPS_ID.subscribe(v -> obgps = v);
        //  getGPS_ID.subscribe(GPS::)
        binding.fab.setEnabled(GPS_OK);
        setupConnectionFactory();
        publishToAMQP();
//        database = FirebaseDatabase
//                .getInstance()
//                .getReference();
//
//        io.reactivex.Observable<DataSnapshot> dataSnapshotObservable = RxFirebaseDatabase
//                .dataChanges(database);
//        dataSnapshotObservable.subscribeOn(io.reactivex.schedulers.Schedulers.io());
//        dataSnapshotObservable.observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread());
//        firebase = dataSnapshotObservable
//
//                .subscribe(
//                        data -> updateItems(data.getChildren()),
//                        error -> Log.e(TAG, error.getMessage())
//                );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Log.d(TAG, "action_settings: ");
            sendSms(PhoneNumber+"\n"+(databasefirabase.getRoot()));
            return true;
        }
        if (id == R.id.action_settings2) {
            Log.d(TAG, "action_settings2: ");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == RESULT_OK) {
            // Obtain the phone number from the result
            Credential credentials = data.getParcelableExtra(Credential.EXTRA_KEY);
            //   EditText.setText(credentials.getId().substring(3)); //get the selected phone number
//Do what ever you want to do with your selected phone number here
            PhoneNumber = credentials.getId().substring(3);
//            Toast.makeText(MainActivity.this, PhoneNumber, Toast.LENGTH_LONG).show();


        } else if (requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == RESULT_CANCELED) {
            // *** No phone numbers available ***
            Toast.makeText(MainActivity.this, "Brak numeru Telefonu", Toast.LENGTH_LONG).show();
            System.exit(0);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.dialog_title)
                    .setMessage(R.string.dialog_message)
                    .setNeutralButton(R.string.dialog_button, (dialog, which) -> dialog.dismiss())
                    .setCancelable(false)
                    .setOnDismissListener(dialog -> this.finishAffinity())
                    .show();

            Toast.makeText(MainActivity.this, "Brak numeru Telefonu", Toast.LENGTH_LONG).show();
            System.exit(0);
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (publishThread != null) publishThread.interrupt();
        if (subscribeThread != null) subscribeThread.interrupt();
        if (rabbitLoader != null) rabbitLoader.dispose();

    }

    public GPS updateGps() {

        GPS obgps = new GPS(0d, 0d);
        GpsTracker gpsTracker = new GpsTracker(this);
        Location location = gpsTracker.getLocation();
        if (location != null) {
            obgps.setLatitude(location.getLatitude());
            obgps.setLongitude(location.getLongitude());
            Toast.makeText(MainActivity.this, "GPS\t" + obgps.getLatitude() + "\t" + obgps.getLongitude(), Toast.LENGTH_SHORT).show();
            GPS_OK = true;
            // fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(Firebase.this, R.color.w)));
            // mGpsTv.setText("GPS Lat = " + (lat) + "\n lon = " + (lon));
            binding.fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.teal_200)));
        } else {
            Toast.makeText(MainActivity.this, "GPS problem", Toast.LENGTH_SHORT).show();
            GPS_OK = false;
            binding.fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.white)));
            //    mGpsTv.setText("No Location :(");
        }
        binding.fab.setEnabled(GPS_OK);
        return obgps;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {

            boolean cameraaccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean vibrateaccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

            if (cameraaccepted
                    && vibrateaccepted) {
                Toast.makeText(this, "Permission granted..", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denined \n You cannot use app without providing permssion"
                        , Toast.LENGTH_SHORT).show();

            }
        }
        if (requestCode == PERMISSION_CODE) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.dialog_title)
                            .setMessage(R.string.dialog_message)
                            .setNeutralButton(R.string.dialog_button, (dialog, which) -> dialog.dismiss())
                            .setCancelable(false)
                            .setOnDismissListener(dialog -> this.finishAffinity())
                            .show();
                    break;
                }
            }
        }
    }


    @MainThread
    public void addCallback(@NonNull OnBackPressedCallback onBackPressedCallback) {
        getOnBackPressedDispatcher().addCallback(onBackPressedCallback);
    }


//    @NonNull
//    @MainThread
//    public Cancellable addCancellableCallback(@NonNull OnBackPressedCallback onBackPressedCallback) {
//        return getOnBackPressedDispatcher().addCancellableCallback(onBackPressedCallback);
//    }


    @MainThread
    @SuppressLint({"LambdaLast"})
    public void addCallback(@NonNull LifecycleOwner owner, @NonNull OnBackPressedCallback onBackPressedCallback) {
        getOnBackPressedDispatcher().addCallback(owner, onBackPressedCallback);
    }

    /**
     * Checks if there is at least one {@link OnBackPressedCallback#isEnabled enabled}
     * callback registered with this dispatcher.
     *
     * @return True if there is at least one enabled callback.
     */
    @MainThread
    public boolean hasEnabledCallbacks() {
        return getOnBackPressedDispatcher().hasEnabledCallbacks();
    }


    //    @MainThread
//    public void onBackPressed() {
//        getOnBackPressedDispatcher().onBackPressed();
//    }
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
//        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
        if (!(navController instanceof IOnBackPressed) || !((IOnBackPressed) navController).onBackPressed()) {
            super.onBackPressed();
            System.exit(0);
            Log.d(TAG, "onBackPressed=" + navController + ((IOnBackPressed) navController).onBackPressed());

        }


    }


}