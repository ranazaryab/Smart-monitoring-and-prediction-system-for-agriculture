package com.example.smartmoniteringandpredictionofagriculturesystem;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.errorprone.annotations.Var;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Recommendation_Fragments extends Fragment {

    Button result;
    TextInputLayout recommend_var;
    TextView label1, nitro_label, phos_label, potass_label, temp_label, humi_label,nitrogen, phosphorous, potassium, temp, humidity;
    public ProgressDialog login_progress;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recommendation__fragments, container, false);

        result = (Button) view.findViewById(R.id.result);
        recommend_var = (TextInputLayout) view.findViewById(R.id.crop_recommend_layout);
        label1 = (TextView) view.findViewById(R.id.label1);
        nitro_label = (TextView) view.findViewById(R.id.Nitrogen_label);
        phos_label = (TextView) view.findViewById(R.id.Phosphorus_label);
        potass_label = (TextView) view.findViewById(R.id.Potassium_label);
        temp_label = (TextView) view.findViewById(R.id.Temp_label);
        humi_label = (TextView) view.findViewById(R.id.humidity_label);
        nitrogen = (TextView) view.findViewById(R.id.Nitrogen_recommendation);
        phosphorous = (TextView) view.findViewById(R.id.Phosphorus_recommendation);
        potassium = (TextView) view.findViewById(R.id.Potassium_recommendation);
        temp = (TextView) view.findViewById(R.id.Temp_recommendation);
        humidity = (TextView) view.findViewById(R.id.humidity_recommendation);

        return view;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String croptext = recommend_var.getEditText().getText().toString();
                ArrayList<DatabaseModel> data = new Singleton().getInstance().getDatabase();
                if (!croptext.isEmpty()) {
                    login_progress = new ProgressDialog(requireContext());
                    login_progress.setTitle("Searching");
                    login_progress.setMessage("Please Wait ");
                    login_progress.setCanceledOnTouchOutside(false);
                    login_progress.show();
                    recommend_var.setError(null);
                    recommend_var.setErrorEnabled(false);
                    DatabaseModel suggessions = new DatabaseModel();
                    for (DatabaseModel db : data) {
                        if (db.crop.equals(croptext)) {
                            suggessions = db;
                            break;
                        }
                    }
                    if (suggessions.crop != "") {
                        String[] List = suggessions.id.split(",");
                        login_progress.dismiss();
                        label1.setVisibility(View.VISIBLE);
                        nitro_label.setVisibility(View.VISIBLE);
                        nitrogen.setVisibility(View.VISIBLE);
                        nitrogen.setText(List[0]);
                        phos_label.setVisibility(View.VISIBLE);
                        phosphorous.setVisibility(View.VISIBLE);
                        phosphorous.setText(List[1]);
                        potass_label.setVisibility(View.VISIBLE);
                        potassium.setVisibility(View.VISIBLE);
                        potassium.setText(List[2]);
                        temp_label.setVisibility(View.VISIBLE);
                        temp.setVisibility(View.VISIBLE);
                        temp.setText(List[3]);
                        humi_label.setVisibility(View.VISIBLE);
                        humidity.setVisibility(View.VISIBLE);
                        humidity.setText(List[4]);
                    }
                    else
                    {
                        login_progress.dismiss();
                        Toast.makeText(requireContext(),"No data found",Toast.LENGTH_LONG).show();
                    }

                } else {
                    recommend_var.setError("Please enter Crop");
                }
            }
        });
    }
}
