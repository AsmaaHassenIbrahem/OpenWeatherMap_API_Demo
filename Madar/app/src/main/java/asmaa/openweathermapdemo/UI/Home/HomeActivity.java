package asmaa.openweathermapdemo.UI.Home;

import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.widget.ProgressView;

import java.util.ArrayList;

import asmaa.openweathermapdemo.Adapter.HomeAdapter;
import asmaa.openweathermapdemo.Data.Model.List;
import asmaa.openweathermapdemo.Injection.Injection;
import asmaa.openweathermapdemo.Listener.LocationListeners;
import asmaa.openweathermapdemo.Listener.OnHomeResult;
import asmaa.openweathermapdemo.R;
import asmaa.openweathermapdemo.UI.Map.MapsActivity;
import asmaa.openweathermapdemo.Utilities.ActionUtils;
import asmaa.openweathermapdemo.Utilities.ActivateGPS;

public class HomeActivity extends AppCompatActivity implements HomePresenter.HomeView, View.OnClickListener, LocationListeners {

    public static final  int REQUEST_CODE = 1;
    public static final String lat="lat";
    public static final String lng="lng";
    private RecyclerView recyclerView;

    private HomePresenter presenter;
    private HomeAdapter homeAdapter;
    private ArrayList<List> dataResult = new ArrayList<>();
    private Button btnMap;
    private TextView txtLat;
    private TextView txtLng;
    private ProgressView progressView;
    private ActivateGPS activateGPS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView= findViewById(R.id.my_recycler_view);
        progressView=findViewById(R.id.pv_load);
        activateGPS= new ActivateGPS(this);

        btnMap=findViewById(R.id.btnMap);
        btnMap.setOnClickListener(this);
        txtLat=findViewById(R.id.txtLat);
        txtLng=findViewById(R.id.txtLng);
        presenter = new HomePresenter(Injection.provideHomeRepository());
       activateGPS.getLocation(this);
    }

    @Override
    public void showProgress() {
        progressView.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {

        progressView.setVisibility(View.GONE);
    }

    @Override
    public void setData(ArrayList<List> listData) {
        dataResult.addAll(listData);
        homeAdapter = new HomeAdapter(dataResult);
        homeAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(homeAdapter);
    }

    @Override
    public void errorMsg() {
        ActionUtils.showToast(this, getResources().getString(R.string.api_error));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == REQUEST_CODE  && resultCode  == RESULT_OK) {

                dataResult.clear();
                txtLat.setText("Lat "+data.getStringExtra(lat));
                txtLng.setText("Lng "+data.getStringExtra(lng));
                presenter.setView(data.getStringExtra(lat), data.getStringExtra(lng), this);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));

            }
        } catch (Exception ex) {
            ActionUtils.showToast(this,ex.getMessage());
        }

    }

    private void switchToMap(){
        Intent mapIntent= new Intent(this,
                MapsActivity.class);
        startActivityForResult(mapIntent , REQUEST_CODE);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnMap:
                switchToMap();
                break;
        }
    }

    @Override
    public void onSuccessDetectLocation(Location location) {
       ////get current location to request with API
        presenter.setView(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onFailureDetectLocation() {

    }
}
