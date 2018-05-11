package asmaa.openweathermapdemo.UI.Home;

/**
 * Created by asmaa on 05/10/2018.
 */

import android.util.Log;

import java.util.ArrayList;

import asmaa.openweathermapdemo.Data.Model.List;
import asmaa.openweathermapdemo.Data.Repository.HomeRepository;
import asmaa.openweathermapdemo.Listener.OnHomeResult;

/**
 * presenter class to get data from repository class and handle show this data in view
 */

public class HomePresenter {

    private ArrayList<List> data = new ArrayList<>();

    private HomeRepository homeRepository;
    HomeView view;

    public HomePresenter(HomeRepository homeRepository){
        this.homeRepository =homeRepository;
    }

    public void setView(String lat, String lon,HomeView view) {
        this.view =view;

        Log.i("here ","here");
        if (data.size()== 0) {
            view.showProgress();
            getHomeData(lat,lon);
            Log.i("here ","here2");

        } else {
            view.hideProgress();
            view.setData(data);
            Log.i("here ","here3");

        }
    }

    public void getHomeData(String lat, String lon){
        homeRepository.getHomeData(lat,lon,new OnHomeResult() {
            @Override
            public void onSuccess(ArrayList<List> listData) {
                view.hideProgress();
                Log.i("presenter:==== ",""+listData.size());
                data.addAll(listData);
                view.setData(data);
            }

            @Override
            public void onFailure() {

                view.errorMsg();

            }
        });

    }

    public interface HomeView{
        void showProgress();
        void hideProgress();
        void setData(ArrayList<List> listData);
        void errorMsg();


    }
}
