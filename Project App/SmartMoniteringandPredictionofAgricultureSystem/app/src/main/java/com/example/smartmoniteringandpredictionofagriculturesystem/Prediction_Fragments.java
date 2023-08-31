package com.example.smartmoniteringandpredictionofagriculturesystem;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class Prediction_Fragments extends Fragment {

    CircularProgressBar nitrogen_progressbar, phosphorous_progressbar,potassium_progressbar,temp_progressbar,humidity_progressbar;
    TextView N_text, P_text,K_text, temp_text,humidity_text,tvLabel,label;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prediction__fragments, container, false);

        nitrogen_progressbar = (CircularProgressBar) view.findViewById(R.id.Nitrogen_ProgressBar) ;
        phosphorous_progressbar = (CircularProgressBar) view.findViewById(R.id.Phosphorus_ProgressBar) ;
        potassium_progressbar = (CircularProgressBar) view.findViewById(R.id.Potassium_ProgressBar) ;
        temp_progressbar = (CircularProgressBar) view.findViewById(R.id.PTemp_ProgressBar) ;
        humidity_progressbar = (CircularProgressBar) view.findViewById(R.id.PHumidity_ProgressBar) ;
        N_text = (TextView) view.findViewById(R.id.Nitrogen);
        P_text = (TextView) view.findViewById(R.id.Phosphorus);
        K_text = (TextView) view.findViewById(R.id.Potassium);
        temp_text = (TextView) view.findViewById(R.id.PTemperature);
        humidity_text = (TextView) view.findViewById(R.id.PHumidity);
        tvLabel= (TextView) view.findViewById(R.id.tvLabell);
        label= (TextView) view.findViewById(R.id.label);

        return view;
    }


    private void nitrogen_update(Integer value){
        nitrogen_progressbar.setProgress(value);
        N_text.setText(value+" %");
    }
    private void phosphorous_update(Integer value){
        phosphorous_progressbar.setProgress(value);
        P_text.setText(value+" %");
    }
    private void potassium_update(Integer value){
        potassium_progressbar.setProgress(value);
        K_text.setText(value+" %");
    }
    private void temp_update(Integer value){
        temp_progressbar.setProgress(value);
        temp_text.setText(value+" °C");
    }
    private void humidity_update(Integer value){
        humidity_progressbar.setProgress(value);
        humidity_text.setText(value+" %");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DataFromSensor data = new DataFromSensor();
        data=new Singleton().getInstance().getSensorData();
        nitrogen_update(data.nitrogen);
        phosphorous_update(data.phosphorus);
        potassium_update(data.potassium);
        temp_update(data.temperature);
        humidity_update(data.humidity);

        String id=data.nitrogen+","+data.phosphorus+","+data.potassium+","+data.humidity+","+data.temperature;

        FirebaseFirestore.getInstance().collection("PREDICTION")
                .document(id)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            tvLabel.setVisibility(View.VISIBLE);
                            label.setVisibility(View.VISIBLE);
                            tvLabel.setText(documentSnapshot.getString("CROP"));
                        }
                        else{
                            tvLabel.setVisibility(View.VISIBLE);
                            label.setVisibility(View.VISIBLE);
                            tvLabel.setText("Not Found.");
                        }
                    }
                });


    }
}