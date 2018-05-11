package asmaa.openweathermapdemo.Listener;

import java.util.ArrayList;

import asmaa.openweathermapdemo.Data.Model.List;

/**
 * Created by asmaa on 05/10/2018.
 */

public interface OnHomeResult {
    void onSuccess(ArrayList<List> listData);
    void onFailure();
}
