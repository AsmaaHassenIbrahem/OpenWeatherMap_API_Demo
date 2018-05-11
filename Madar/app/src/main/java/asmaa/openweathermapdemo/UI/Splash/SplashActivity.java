package asmaa.openweathermapdemo.UI.Splash;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;

import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import asmaa.openweathermapdemo.Listener.LocationListeners;
import asmaa.openweathermapdemo.R;
import asmaa.openweathermapdemo.UI.Home.HomeActivity;
import asmaa.openweathermapdemo.Utilities.ActionUtils;
import asmaa.openweathermapdemo.Utilities.ActivateGPS;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity implements LocationListeners {


  //  static final Integer LOCATION = 0x1;
    static final Integer GPS_SETTINGS = 0x7;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private Intent intent;
    private Handler handler;
    private Handler mHandler;

   private GoogleApiClient client;
   private LocationRequest mLocationRequest;
   private PendingResult<LocationSettingsResult> result;

    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };
    private ActivateGPS activateGPS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        checkPlayServices();

        client = new GoogleApiClient.Builder(this)
                .addApi(AppIndex.API)
                .addApi(LocationServices.API)
                .build();
        activateGPS = new ActivateGPS(SplashActivity.this);
        mHandler = new Handler();

        handler = new Handler();

        if (!canAccessLocation() || !hasPermission(INITIAL_PERMS[0])) {
          askForPermission(Manifest.permission.ACCESS_FINE_LOCATION,1);
        } else {
            if (activateGPS.checkGPS()) {
                detectNextScreen();
            } else {
           askForPermission(Manifest.permission.ACCESS_FINE_LOCATION,1);
                ActionUtils.showToast(this,"plz close App");
            }
        }

    }

    private void detectNextScreen() {
        handler.postDelayed(new Runnable() {
            public void run() {

                    intent = new Intent(SplashActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
            }
        }, 3000);
    }

    private boolean canAccessLocation() {
        return (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }

    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, perm));
    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
                activateGPS.getLocation(SplashActivity.this);
            } else {

                ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
                activateGPS.getLocation(SplashActivity.this);

            }
        } else {
            askForGPS();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                //Location
                case 1:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        if (activateGPS.checkGPS()) {
                            detectNextScreen();
                        } else {
                          finish();
                          ActionUtils.showToast(this,"plz close App");
                        }
                    } else {
                        finish();
                    }
                  return;

            }
        }else {
            finish();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        client.connect();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!activateGPS.checkGPS()) {
            mStatusChecker.run();
        } else {
            if (hasPermission(INITIAL_PERMS[0])) {
                detectNextScreen();
                mHandler.removeCallbacks(mStatusChecker);
            }
        }
    }

    private void askForGPS(){
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);
        result = LocationServices.SettingsApi.checkLocationSettings(client, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        activateGPS.getLocation(SplashActivity.this);
                        detectNextScreen();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        activateGPS.getLocation(SplashActivity.this);
                        try {
                            status.startResolutionForResult(SplashActivity.this, GPS_SETTINGS);
                            activateGPS.getLocation(SplashActivity.this);
                        } catch (IntentSender.SendIntentException e) {
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        activateGPS.getLocation(SplashActivity.this);
                        break;
                }
            }
        });
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            checkPlayServices();
            if (!activateGPS.checkGPS() ) {
                mHandler.postDelayed(mStatusChecker, 5000);
            } else {
                mHandler.removeCallbacks(mStatusChecker);
                detectNextScreen();
            }
        }
    };
    @Override
    public void onSuccessDetectLocation(Location location) {
        Log.i("dddddd", location.getLatitude()+"");
    }

    @Override
    public void onFailureDetectLocation() {
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                ActionUtils.showToast(this, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
}
