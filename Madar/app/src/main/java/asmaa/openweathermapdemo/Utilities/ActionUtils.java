package asmaa.openweathermapdemo.Utilities;

import android.content.Context;

import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.io.IOException;

import java.util.List;
import java.util.Locale;

/**
 * Created by asmaa on 05/10/2018.
 */

public class ActionUtils {

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}