package com.example.smartmoniteringandpredictionofagriculturesystem;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splash extends AppCompatActivity {
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Intent serviceIntent=new Intent(Splash.this,SensorService.class);
        startService(serviceIntent);
        checkCurrentUser();
    }
    public void checkCurrentUser() {
        // [START check_current_user]
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(this, dashboard.class);
            startActivity(intent);
            finish();
        } else {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        sleep(3000);
                        Intent i =new Intent(Splash.this, MainActivity.class);
                        startActivity(i);
                        finish();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
        // [END check_current_user]
    }
}