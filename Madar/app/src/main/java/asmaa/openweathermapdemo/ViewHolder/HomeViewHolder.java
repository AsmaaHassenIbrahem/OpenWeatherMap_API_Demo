package asmaa.openweathermapdemo.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import asmaa.openweathermapdemo.Data.Model.List;
import asmaa.openweathermapdemo.Listener.OnHomeResult;
import asmaa.openweathermapdemo.R;

import static java.lang.String.valueOf;


/**
 * Created by asmaa on 05/10/2018.
 */

public class HomeViewHolder extends RecyclerView.ViewHolder  {

    private Context context;
    private OnHomeResult homeListener;
    private View itemView;
    private TextView txt1;
    private TextView txt2;
    private TextView txt3;
    private ImageView img;
    private List homeModel;

    public HomeViewHolder(View itemView ,Context context) {
        super(itemView);
        this.context=context;
        this.itemView= itemView;
        initializeViews();

    }

    private void initializeViews() {
        txt1 = itemView.findViewById(R.id.tv1);
        txt2 = itemView.findViewById(R.id.tv2);
        txt3 = itemView.findViewById(R.id.tv3);

        img = itemView.findViewById(R.id.img);
    }


    public void setData(List data){
        this.homeModel=data;
        txt1.setText(homeModel.getWeather().get(0).getIcon());
        txt2.setText(homeModel.getWeather().get(0).getDescription());
        txt3.setText(homeModel.getWeather().get(0).getMain());
    }
}
