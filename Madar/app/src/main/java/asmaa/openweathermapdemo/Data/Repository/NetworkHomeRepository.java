package asmaa.openweathermapdemo.Data.Repository;

import android.util.Log;

import java.lang.ref.PhantomReference;
import java.util.ArrayList;

import asmaa.openweathermapdemo.API.APIUrls;
import asmaa.openweathermapdemo.API.ApiEndpointInterface;
import asmaa.openweathermapdemo.Data.Model.ForecastData;
import asmaa.openweathermapdemo.Data.Model.List;
import asmaa.openweathermapdemo.Listener.OnHomeResult;
import asmaa.openweathermapdemo.Singleton.App;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by asmaa on 05/10/2018.
 */

/**
 * repository class to request api and get data
 */
public class NetworkHomeRepository implements HomeRepository {

    private ApiEndpointInterface mApi = App.getInstance().getApi();
    private ArrayList<List>data;
    private static final String SUCCESS_CODE="200";
    private static final String FAILURE_CODE="401";

    @Override
    public void getHomeData(String lat, String lon, final OnHomeResult onHomeResult) {
        Call<ForecastData> call;
        call = mApi.getData(lat ,lon,"3", APIUrls.APPID);
        call.clone().enqueue(new Callback<ForecastData>() {
            @Override
            public void onResponse(Call<ForecastData> call, Response<ForecastData> response) {

                data = new ArrayList<>();
                if (response.body()!=null){
                    if (response.body().getCod().equals(SUCCESS_CODE)) {
                        for (int i = 0; i < response.body().getList().size(); i++) {
                            data.add(response.body().getList().get(i));
                        }
                        onHomeResult.onSuccess(data);
                    }
                    if (response.body().getCod().equals(FAILURE_CODE)){
                        onHomeResult.onFailure();

                    }
                }
            }

            @Override
            public void onFailure(Call<ForecastData> call, Throwable t) {

                Log.e("error: ",t.getMessage());
                onHomeResult.onFailure();
            }
        });



    }
}
