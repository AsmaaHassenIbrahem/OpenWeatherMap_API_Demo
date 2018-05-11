package asmaa.openweathermapdemo.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import asmaa.openweathermapdemo.Data.Model.List;
import asmaa.openweathermapdemo.R;
import asmaa.openweathermapdemo.ViewHolder.HomeViewHolder;


/**
 * Created by asmaa on 05/10/2018.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeViewHolder>{


    private ArrayList<List>  data;
  //  private HomeListener homeListener;

    public HomeAdapter( ArrayList<List>  data /* HomeListener homeListener*/){
        this.data=data;
      //  this.homeListener = homeListener;
    }
    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_home, parent, false);
        return new HomeViewHolder(view,/*homeListener,*/parent.getContext());
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        Log.i("log:=====","log");
         holder.setData(data.get(position));
    }

    @Override
    public int getItemCount() {
        Log.i("sizee: ",""+data.size());
        return data.size();

    }
}
