package asmaa.openweathermapdemo.Data.Repository;


/**
 * Created by asmaa on 05/10/2018.
 */

import asmaa.openweathermapdemo.Listener.OnHomeResult;

/**
 * interface repository to write the method will be use in repository to handle all repositories
 */
public interface HomeRepository {
    void getHomeData(String lat, String lon,OnHomeResult onHomeResult);
    }


