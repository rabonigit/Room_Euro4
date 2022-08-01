package robac.pl.room_euro4.GPS;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.core.content.ContextCompat;

public class GpsTracker implements LocationListener {

    private Context context;

    public GpsTracker(Context context) {
        this.context = context;
    }

    public Location getLocation() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("GPS", "no permission");
            return null;
        }
        Location location;
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGpsEnabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isGpsEnabled) {
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 100, this);
            location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return location;
        } else {
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 6000, 1000, this);
            location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            return location;
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
