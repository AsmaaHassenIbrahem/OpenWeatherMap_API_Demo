package asmaa.openweathermapdemo.Listener;

import android.location.Location;

/**
 * Created by asmaa on 4/28/2016.
 */
public interface LocationListeners {
    void onSuccessDetectLocation(Location location);
    void onFailureDetectLocation();
}
