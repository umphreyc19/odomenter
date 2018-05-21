package com.example.zx.odomenter;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class OdometerService extends Service {
    private final IBinder binder = new OdometerBinder();
    private static double distanceInMeters;
    private static Location lastLocation;

    public class OdometerBinder extends Binder {
        OdometerService getOdometer() {
            return OdometerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (lastLocation == null) {
                    lastLocation = location;
                }
                distanceInMeters += location.distanceTo(lastLocation);
                lastLocation = location;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, listener);
    }
    public double getMiles() {
        return this.distanceInMeters / 1609.344;
    }
}
