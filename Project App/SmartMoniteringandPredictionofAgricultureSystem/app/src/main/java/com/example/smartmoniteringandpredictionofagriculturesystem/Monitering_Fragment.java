package com.example.smartmoniteringandpredictionofagriculturesystem;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class Monitering_Fragment extends Fragment {

    CircularProgressBar temp_progressbar, humidity_progressbar,soil_progressbar;/*air_progressbar, rain_progressbar*/
    TextView temp_text, humidity_text,soil_text, air_text, rain_text;
    int value=0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_monitering_, container, false);

        temp_progressbar = (CircularProgressBar) view.findViewById(R.id.temperature_ProgressBar) ;
        humidity_progressbar = (CircularProgressBar) view.findViewById(R.id.humidity_ProgressBar) ;
        soil_progressbar = (CircularProgressBar) view.findViewById(R.id.Moisture_ProgressBar) ;
/*      air_progressbar = (CircularProgressBar) view.findViewById(R.id.Air_ProgressBar) ;
        rain_progressbar = (CircularProgressBar) view.findViewById(R.id.Rain_ProgressBar);*/
        temp_text = (TextView) view.findViewById(R.id.temperature);
        humidity_text = (TextView) view.findViewById(R.id.humidity);
        soil_text = (TextView) view.findViewById(R.id.Moisture);
        air_text = (TextView) view.findViewById(R.id.Air);
        rain_text = (TextView) view.findViewById(R.id.Rain);

        return view;
    }
    private void temp_update(Integer value){
        temp_progressbar.setProgress(value);
        temp_text.setText(value+" °C");
    }
    private void humidity_update(Integer value){
        humidity_progressbar.setProgress(value);
        humidity_text.setText(value+" %");
    }
    private void soil_update(Integer value){
        soil_progressbar.setProgress(value);
        soil_text.setText(value+" %");
    }
    private void air_update(Integer value){
        //air_progressbar.setProgress(value);
        if(value > 600)
        air_text.setText("Air is Polluted");
        else{
            air_text.setText("Air is Fresh");
        }
    }
    private void rain_update(Integer value){
        //rain_progressbar.setProgress(value);
        if(value > 10)
        rain_text.setText("It is Raining");
        else{
            rain_text.setText("It is not Raining");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DataFromSensor data = new DataFromSensor();
        data=new Singleton().getInstance().getSensorData();
        temp_update(data.temperature);
        humidity_update(data.humidity);
        soil_update(data.soil);
        air_update(data.air);
        rain_update(data.rain);
    }

}