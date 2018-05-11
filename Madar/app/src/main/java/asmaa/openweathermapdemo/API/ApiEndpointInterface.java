package asmaa.openweathermapdemo.API;

/**
 * Created by asmaa on 05/10/2018.
 */

import asmaa.openweathermapdemo.Data.Model.ForecastData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * interface to all retrofit requests , type of method request and parameters or request body
 * and handle this with model classes to parse json response to pojo classes
 */
public interface ApiEndpointInterface {

    @GET(APIUrls.LIST_DATA)
    Call<ForecastData> getData(@Query("lat") String lat,
                               @Query("lon") String lon,
                               @Query("cnt") String cnt,
                               @Query("appid") String appid);

}
