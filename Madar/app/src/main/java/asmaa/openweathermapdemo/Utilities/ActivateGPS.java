package asmaa.openweathermapdemo.Utilities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.List;

import asmaa.openweathermapdemo.Listener.LocationListeners;
import asmaa.openweathermapdemo.R;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by asmaa on 05/10/2018.
 */
public class ActivateGPS  implements LocationListener {

    private LocationListener locationListener;
    public Location location;
    public double latitude;
    public double longitude;
    protected LocationManager locationManager;
    public boolean isGPSEnabled = false;
    private Location l;
    private Location TODO;
    private Context context;

    public ActivateGPS(Context context) {
        this.locationListener = this;
        this.context=context;
    }

    public boolean checkGPS() {
       // this.context = context;
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
       // locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isGPSEnabled;
    }

    public void getLocation(final LocationListeners locationListeners) {

        if (checkGPS() == true) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (canAccessLocation()) {
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                        if (location == null) {
                            if (locationManager != null) {
//                            location = getLastKnownLocation();
                                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                if (location != null) {
                                    if (location.getLatitude() != 0.0 && location.getLongitude() != 0.0) {
                                        latitude = location.getLatitude();
                                        longitude = location.getLongitude();
                                        locationListeners.onSuccessDetectLocation(location);
                                    } else {
                                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                                        if (locationManager != null) {
                                            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                                            if (location != null) {
                                                if (location.getLatitude() != 0.0 && location.getLongitude() != 0.0) {
                                                    latitude = location.getLatitude();
                                                    longitude = location.getLongitude();
                                                    locationListeners.onSuccessDetectLocation(location);
                                                }
                                            }
                                            locationManager.removeUpdates(locationListener);
                                        }
                                    }
                                } else {
                                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                                    if (locationManager != null) {
                                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                                        if (location != null) {
                                            if (location.getLatitude() != 0.0 && location.getLongitude() != 0.0) {
                                                latitude = location.getLatitude();
                                                longitude = location.getLongitude();
                                                locationListeners.onSuccessDetectLocation(location);
                                            }
                                        }
                                        locationManager.removeUpdates(locationListener);
                                    } else {
                                        locationListeners.onFailureDetectLocation();
                                    }
                                }
                            }
                        } else {
                            if (location.getLatitude() != 0.0 && location.getLongitude() != 0.0) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                locationListeners.onSuccessDetectLocation(location);
                            }
                        }
                    } else {

                    }

                }
            }, 1000);

        } else {
            ActionUtils.showToast(context, "GPS is not Enabled");
        }
    }

    private Location getLastKnownLocation() {
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (canAccessLocation()) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return TODO;
                }
                    l = locationManager.getLastKnownLocation(provider);
            } else {
                ActionUtils.showToast(context, context.getResources().getString(R.string.gps_error));
            }

            if (l == null) {
                continue;
            }
            if (bestLocation == null
                    || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        if (bestLocation == null) {
            return null;
        }
        return bestLocation;
    }

    public void getLocationWIFI() {
        if (canAccessLocation()) {
            if (isGPSEnabled == true) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                    locationManager.removeUpdates(locationListener);
                }
            } else {
                ActionUtils.showToast(context, "GPS is not Enabled");
            }
        }else{
            ActionUtils.showToast(context, context.getResources().getString(R.string.gps_error));
        }

    }

    public String getLat() {
        return String.valueOf(latitude);
    }

    public String getLng() {
        return String.valueOf(longitude);
    }

    @Override
    public void onLocationChanged(Location location) {

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

    private boolean canAccessLocation() {
        return (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context, perm));
    }


}
