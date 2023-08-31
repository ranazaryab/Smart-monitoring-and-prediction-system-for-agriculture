package com.example.smartmoniteringandpredictionofagriculturesystem;

import android.app.Person;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class Cardviews_Fragments extends Fragment{


    CardView monitoring, prediction, recommendation, credential;
    Button logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_cardviews__fragments, container, false);
        monitoring = (CardView) view.findViewById(R.id.monitoring);
        prediction = (CardView) view.findViewById(R.id.prediction);
        recommendation = (CardView) view.findViewById(R.id.recommendation);
        credential = (CardView) view.findViewById(R.id.credential);
        logout = (Button) view.findViewById(R.id.logout);
        monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(), "moniter", Toast.LENGTH_SHORT).show();
                monitor();
            }
        });
        prediction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prediction();
            }
        });
        recommendation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                recommendation();
            }
        });
        credential.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                credential();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
            }
        });


        return view;
    }
    public void monitor(){
                Monitering_Fragment monitor = new Monitering_Fragment();
                FragmentTransaction monitor_transaction = getActivity().getSupportFragmentManager().beginTransaction();
                monitor_transaction.replace(R.id.layout,monitor).addToBackStack(null).commit();
    }
    public void prediction(){
        Prediction_Fragments prediction = new Prediction_Fragments();
        FragmentTransaction prediction_transaction = getActivity().getSupportFragmentManager().beginTransaction();
        prediction_transaction.replace(R.id.layout,prediction).addToBackStack(null).commit();
    }
    public void recommendation(){
        Recommendation_Fragments recommendation = new Recommendation_Fragments();
        FragmentTransaction recommand_transaction = getActivity().getSupportFragmentManager().beginTransaction();
        recommand_transaction.replace(R.id.layout,recommendation).addToBackStack(null).commit();
    }
    public void credential(){
        Credentials_Fragments credential = new Credentials_Fragments();
        FragmentTransaction credential_transaction = getActivity().getSupportFragmentManager().beginTransaction();
        credential_transaction.replace(R.id.layout,credential).addToBackStack(null).commit();
    }



}