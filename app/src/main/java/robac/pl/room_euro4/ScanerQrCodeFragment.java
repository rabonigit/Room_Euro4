package robac.pl.room_euro4;

import static android.content.Context.CAMERA_SERVICE;
import static robac.pl.room_euro4.MainActivity.messageDao;

import android.graphics.Color;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import robac.pl.room_euro4.Util.Util;
import robac.pl.room_euro4.databinding.FragmentScanerQrCodeBinding;
import robac.pl.room_euro4.model.Message;


public class ScanerQrCodeFragment extends Fragment {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    public FragmentScanerQrCodeBinding binding;
    boolean CameraPermission = true;
    private static final String TAG = "ScanerQrCodeFragment";
    private CodeScanner mCodeScanner;
    private Disposable ScanerLoader, ScanerLoaderDB;
    private Message message = new Message();
    private List<Message> messagedb;
    private List<Message> messages;
    NavController navController;
    CameraManager cameraManager;
    public ScanerQrCodeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//         cameraManager = (CameraManager) getContext().getSystemService(CAMERA_SERVICE);
//        try {
//            cameraManager.setTorchMode("0",true);
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }

//
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentScanerQrCodeBinding.inflate(inflater, container, false);
        CodeScannerView scannerView = binding.camview;

        mCodeScanner = new CodeScanner(getContext(), scannerView);


        if (CameraPermission) {

            scannerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navController= Navigation.findNavController(v);
                    mCodeScanner.startPreview();


                }
            });

            mCodeScanner.setDecodeCallback(new DecodeCallback() {
                @Override
                public void onDecoded(@NonNull @NotNull Result result) {
//                    if(!Util.getQrCode_Info(result.getText(), MainActivity.PhoneNumber).getQrcode().toString().equalsIgnoreCase("Brak")) {
                        io.reactivex.rxjava3.core.Observable.just(Util.getQrCode_Info(result.getText(), MainActivity.PhoneNumber)).subscribeOn(Schedulers.io()).map(db -> {

                            return db;
                        }).observeOn(AndroidSchedulers.mainThread()).subscribe(wynik -> {
                            binding.idTVscanned.setText(wynik.getQrcode());
                            binding.idTVscanned.setTextColor(wynik.getQrcode().equalsIgnoreCase("Brak") ? Color.RED : Color.BLACK);
                          if(!binding.idTVscanned.getText().toString().equalsIgnoreCase("Brak")){  NavDirections action = ScanerQrCodeFragmentDirections.actionScanerQrCodeFragmentToSecondFragment(wynik);
                            navController.navigate(action);}
                        });






                }
            });

        }
        return binding.getRoot();
//                inflater.inflate(R.layout.fragment_scaner_qr_code, container, false);
    }


    @Override
    public void onPause() {
        if (CameraPermission) {
            mCodeScanner.releaseResources();
        }
        super.onPause();
    }

    public void onStart(LayoutInflater inflater, ViewGroup container,
                        Bundle savedInstanceState) {
        binding = FragmentScanerQrCodeBinding.inflate(inflater, container, false);
        CodeScannerView scannerView = binding.camview;
        mCodeScanner = new CodeScanner(getContext(), scannerView);
        scannerView.isFlashButtonVisible();

        // mCodeScanner
        //started
//        scannerView.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                Log.d(TAG, "onKey= " +keyCode+"   "+event.getKeyCode());
//                return false;
//            }
//        });
//
//        scannerView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.d(TAG, "onKey= " +"   "+event.getButtonState());
//                return false;
//            }
//        });


        //stop

        if (CameraPermission) {

            scannerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    mCodeScanner.startPreview();

                }
            });

            mCodeScanner.setDecodeCallback(new DecodeCallback() {
                @Override
                public void onDecoded(@NonNull @NotNull Result result) {


                    io.reactivex.rxjava3.core.Observable.just(Util.getQrCode_Info(result.getText(), MainActivity.PhoneNumber)).subscribeOn(Schedulers.io()).map(db -> {

                        return db;
                    }).observeOn(AndroidSchedulers.mainThread()).subscribe(wynik -> {binding.idTVscanned.setText(wynik.getQrcode());
                        binding.idTVscanned.setTextColor(wynik.getQrcode().equalsIgnoreCase("Brak")?Color.RED:Color.BLACK);});

                    if(!binding.idTVscanned.getText().toString().equalsIgnoreCase("Brak"))
                    {
                        NavController navController= Navigation.findNavController(getView());
                        NavDirections action= ScanerQrCodeFragmentDirections.actionScanerQrCodeFragmentToSecondFragment(Util.getQrCode_Info(result.getText(), MainActivity.PhoneNumber));
                        navController.navigate(action);
                    }

//             n
//                    message=Util.getQrCode_Info(result.getText(), MainActivity.PhoneNumber);
//                    Log.d("Util1","getQrCode_Info="+message.getQrcode());
//                    if (!message.getQrcode().equalsIgnoreCase("Brak"))
//                    {
//                        io.reactivex.rxjava3.core.Observable.just(MainActivity.messageDao).subscribeOn(Schedulers.io()).map(db -> {
//                            db.insert(Util.getQrCode_Info(result.getText(), MainActivity.PhoneNumber));
//
//                            messagedb = db.getSingle(Util.getQrCode_Info(result.getText(), MainActivity.PhoneNumber).getId());
//
//
//                            return messagedb;
//                        }).observeOn(AndroidSchedulers.mainThread())
//                                .subscribe(wynik ->binding.idTVscanned.setText(wynik.get(0).getQrcode()),
//                                error -> Log.e(TAG, "Couldn't load Message = " + error.getMessage()));
//                    }
//                    ScanerLoader = getUserID.subscribe(v -> {
//                        out.set(v);
//
//                        if (!out.get().getQrcode().equalsIgnoreCase("Brak")) {
//                            io.reactivex.rxjava3.core.Observable.just(messageDao).subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io()).map(db -> {
//                                db.insert(Util.getQrCode_Info(out.get().getQrcode(), MainActivity.PhoneNumber));
//
//                                messages = db.getAll();
//
//
//                                return messages;
//                            }).observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread());
//
//                        }
//
//
//                        compositeDisposable.add(ScanerLoader);
//
//                    });
                }
            });

        }
        super.onStart();
    }
}



