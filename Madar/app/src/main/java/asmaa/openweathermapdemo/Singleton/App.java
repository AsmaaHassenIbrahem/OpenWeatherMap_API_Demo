package asmaa.openweathermapdemo.Singleton;

import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import asmaa.openweathermapdemo.API.APIUrls;
import asmaa.openweathermapdemo.API.ApiEndpointInterface;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by asmaa on 05/10/2018.
 */

/**
 * singleton class to manage my app and don't create more objects from my connection api
 */
public class App {


    public static App App = null;
    private Retrofit retrofit;
    private ApiEndpointInterface apiEndpointInterface;
    private OkHttpClient.Builder httpClient;
    private Retrofit.Builder builder;
    private static Preferences preferencesClass;

    private App() {
        initialization();

    }

    public static synchronized App getInstance() {

        if (App == null) {
            App = new App();
        }
        return App;
    }

    private void initialization() {
        // to cache request

        httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
//                .cache(cache)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });


        builder = new Retrofit.Builder()
                .baseUrl(APIUrls.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.client(httpClient.build()).build();

        apiEndpointInterface = retrofit.create(ApiEndpointInterface.class);
    }

    public ApiEndpointInterface getApi() {
        if(apiEndpointInterface == null) {
            initialization();
        }
        return apiEndpointInterface;
    }


//    public Preferences getLocalSettings() {
//        if(preferencesClass == null) {
//            preferencesClass = new Preferences();
//        }
//        return preferencesClass;
//    }

}
