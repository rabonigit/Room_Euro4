package robac.pl.room_euro4;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.github.pwittchen.reactivewifi.ReactiveWifi;

import org.slf4j.Logger;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import robac.pl.room_euro4.databinding.FragmentFirstBinding;
import robac.pl.room_euro4.model.Message;

public class FirstFragment extends Fragment {

    int licznik = 255;
    private boolean wifichek = false;
    private FragmentFirstBinding binding;
    private Resources res;

    private Disposable wifiSubscription;
    private Disposable signalLevelSubscription;
    private Disposable supplicantSubscription;
    private Disposable wifiStateSubscription;
    private Disposable wifiInfoSubscription;


    @Override
    public void onPause() {
        super.onPause();
        binding.skanuj.setEnabled(MainActivity.GPS_OK);
        binding.info.setText("Info-" + MainActivity.Liczba_Poj_OK + ":  oczekujące" + MainActivity.Liczba_Poj_No);
        safelyUnsubscribe(wifiSubscription, signalLevelSubscription, supplicantSubscription,
                wifiInfoSubscription, wifiStateSubscription);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        binding.skanuj.setEnabled(MainActivity.GPS_OK);
        binding.info.setText("Info -" + MainActivity.PhoneNumber+"\n"+MainActivity.Liczba_Poj_OK + ":  oczekujące" + MainActivity.Liczba_Poj_No);

        return binding.getRoot();


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        res = getResources();
        binding.skanuj.setEnabled(MainActivity.GPS_OK);
        binding.info.setText("Info -" + MainActivity.PhoneNumber+"\n"+ MainActivity.Liczba_Poj_OK + ":  oczekujące" + MainActivity.Liczba_Poj_No);
        binding.listaPoj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavController navController = Navigation.findNavController(view);
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//                NavDirections action = ScanerQrCodeFragmentDirections.;

//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
                NavDirections action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(new Message()).setDomyslna(true);
                navController.navigate(action);
            }
        });

        binding.imageView.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                binding.info.setText("Info-"+ MainActivity.PhoneNumber+"\n" + MainActivity.Liczba_Poj_OK + ":  oczekujące" + MainActivity.Liczba_Poj_No);
//                wifi.setWifiEnabled(wifichek);

//                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
//
//                startActivity(intent);
//                wifiInfoSubscription = ReactiveWifi.observeWifiAccessPointChanges(view.getContext())
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(wifiInfo -> {
//                            Log.d("Wifi", " AccessPointChanges(wifiInfoFragment= " + wifiInfo);
//
//
//                            // do something with wifiInfo
//                        });
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more detailsSent.
                    return;
                }
//                ReactiveWifi.observeWifiAccessPoints(view.getContext())
//                        .subscribeOn(io.reactivex.schedulers.Schedulers.io())
//                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
//                        .take(10, TimeUnit.SECONDS)
//
//                        .subscribe(scanResults -> {
//
//                            for (ScanResult wykrywanie:scanResults
//                                 ) {
//                                Log.d("Wifi", "wifiInfoFragmentx= " + wykrywanie+"\n");
//                            }
//
//                        });
                wifiStateSubscription = ReactiveWifi.observeWifiStateChange(view.getContext())
                        .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribe(WifiState -> {
                            Log.d("Wifi", "WifiStateFragmentAPi= " + WifiState);
                            wifichek = WifiState.state == 3;
                            if (WifiState.state != 3) {
                                Drawable drawable = res.getDrawable(R.drawable.ic_wifi_off, view.getResources().newTheme());
                                binding.imageView.setImageDrawable(drawable);
                            } else {
                                Drawable drawable = res.getDrawable(R.drawable.ic_wifi, view.getResources().newTheme());
                                binding.imageView.setImageDrawable(drawable);


                            }
                            // do something with wifiInfo
                        });
                supplicantSubscription = ReactiveWifi.observeSupplicantState(view.getContext())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(supplicantState -> Log.d("ReactiveWifi",
                                "New supplicant state: " + supplicantState.toString()));
                wifiInfoSubscription = ReactiveWifi.observeWifiAccessPointChanges(view.getContext())
                        .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribe(wifiInfo -> {
                            Log.d("Wifi", "wifiInfoFragment= " + wifiInfo);
                            // do something with wifiInfo
                        });

                signalLevelSubscription = ReactiveWifi.observeWifiSignalLevel(view.getContext(), 100)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(observeWifiSignalLevel -> {

                            Log.d("Wifi", "Siła sygnału= " + observeWifiSignalLevel);
//binding.info.setText(observeWifiSignalLevel);

                            try {
                                if (observeWifiSignalLevel != null) {
                                    if (observeWifiSignalLevel > 75) {
                                        Drawable drawable = res.getDrawable(R.drawable.ic_wifi_100, view.getResources().newTheme());
                                        binding.imageView.setImageDrawable(drawable);
                                    } else if (observeWifiSignalLevel <= 75 && observeWifiSignalLevel > 50) {
                                        Drawable drawable = res.getDrawable(R.drawable.ic_wifi_75, view.getResources().newTheme());
                                        binding.imageView.setImageDrawable(drawable);
                                    } else if (observeWifiSignalLevel <= 50 && observeWifiSignalLevel > 45) {
                                        Drawable drawable = res.getDrawable(R.drawable.ic_wifi_50, view.getResources().newTheme());
                                        binding.imageView.setImageDrawable(drawable);
                                    } else if (observeWifiSignalLevel <= 45 && observeWifiSignalLevel > 20) {
                                        Drawable drawable = res.getDrawable(R.drawable.ic_wifi_25, view.getResources().newTheme());
                                        binding.imageView.setImageDrawable(drawable);
                                    } else {
                                        Drawable drawable = res.getDrawable(R.drawable.ic_wifi_0, view.getResources().newTheme());
                                        binding.imageView.setImageDrawable(drawable);
                                    }
                                }
                            } catch (java.lang.NullPointerException xx) {
                                Drawable drawable = res.getDrawable(R.drawable.ic_wifi_off, view.getResources().newTheme());
                                binding.imageView.setImageDrawable(drawable);
                            }
                            // do something with wifiInfo
                        });
            }
        });
        binding.skanuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.GPS_OK) {
                    NavHostFragment.findNavController(FirstFragment.this)
                            .navigate(R.id.action_FirstFragment_to_scanerQrCodeFragment);
                } else {
                    binding.skanuj.setEnabled(MainActivity.GPS_OK);
                }
            }
        });
        binding.sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_sentFragment);
                }catch(java.lang.IllegalArgumentException ex)
                {
                    Log.d("FirstFragment", ex.toString());
                  return;
                }
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.skanuj.setEnabled(MainActivity.GPS_OK);
        binding.info.setText("Info -" + MainActivity.PhoneNumber+"\n"+ MainActivity.Liczba_Poj_OK + ":  oczekujące" + MainActivity.Liczba_Poj_No);

        binding = null;
    }

    private Bitmap changeBitmapColor(int color) {

        Bitmap ob = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_wifi);
        Bitmap obm = Bitmap.createBitmap(ob.getWidth(), ob.getHeight(), Bitmap.Config.ARGB_8888);
        Bitmap overlay = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_wifi);
        Bitmap overlaym = Bitmap.createBitmap(overlay.getWidth(), overlay.getHeight(), Bitmap.Config.ARGB_8888);


        Canvas canvas = new Canvas(overlaym);
        Paint paint = new Paint();
        paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(ob, 0f, 0f, paint);
        canvas.drawBitmap(overlay, 0f, 0f, null);
        return overlaym;
    }

    private void safelyUnsubscribe(Disposable... subscriptions) {
        for (Disposable subscription : subscriptions) {
            if (subscription != null && !subscription.isDisposed()) {
                subscription.dispose();
            }
        }
    }
//wifi starts

    //wifi stop
}