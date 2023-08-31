package com.example.smartmoniteringandpredictionofagriculturesystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.Person;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class dashboard extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView home_menu, credential_menu, monitor_menu, prediction_menu, recommendation_menu, logout_menu;
    SwipeRefreshLayout swipeRefreshLayout;
    Connection_Receiver connection_receiver = new Connection_Receiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        home_menu = findViewById(R.id.home_menu);
        credential_menu = findViewById(R.id.credential_menu);
        monitor_menu = findViewById(R.id.monitoring_menu);
        prediction_menu = findViewById(R.id.prediction_menu);
        recommendation_menu = findViewById(R.id.recommendation_menu);
        logout_menu = findViewById(R.id.logout_menu);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_bar);
        toolbar = findViewById(R.id.toolbar);


//        setSupportActionBar(toolbar);
//        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
//
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeRefreshLayout.setRefreshing(true);
//                refresh();
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.navigation_open,R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        // Card View Fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.layout,new Cardviews_Fragments()).commit();
//        navigationView.setNavigationItemSelectedListener(this);

            home_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    home_menu();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            });
            monitor_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    monitor_menu();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            });
            prediction_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prediction_menu();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            });
            recommendation_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recommendation_menu();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            });
            credential_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    credential_menu();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            });
            logout_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logout_menu();
                }
            });
        }
//    }

    private void refresh(){
        getSupportFragmentManager().beginTransaction().replace(R.id.layout,new Monitering_Fragment()).addToBackStack(null).commit();
    }

    public void home_menu(){
        getSupportFragmentManager().beginTransaction().replace(R.id.layout,new Cardviews_Fragments()).commit();
    }
    public void monitor_menu(){
        getSupportFragmentManager().beginTransaction().replace(R.id.layout,new Monitering_Fragment()).addToBackStack(null).commit();
    }
    public void prediction_menu(){
        getSupportFragmentManager().beginTransaction().replace(R.id.layout,new Prediction_Fragments()).addToBackStack(null).commit();
    }
    public void recommendation_menu(){
        getSupportFragmentManager().beginTransaction().replace(R.id.layout,new Recommendation_Fragments()).addToBackStack(null).commit();
    }
    public void credential_menu(){
        getSupportFragmentManager().beginTransaction().replace(R.id.layout,new Credentials_Fragments()).addToBackStack(null).commit();
    }
    public void logout_menu(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }




//    @Override
//    public void onCard_viewFragmentFinish(Cardviews_Fragments cardviews_fragments) {
////        getSupportFragmentManager().beginTransaction().replace(R.id.layout,new Monitering_Fragment()).commit();
//    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.home_menu:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.layout,new Cardviews_Fragments()).commit();
//                        overridePendingTransition(0,0);
//                        break;
//                    case R.id.credential_menu:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.layout,new Credentials_Fragments()).commit();
//                        overridePendingTransition(0,0);
//                        break;
//                    case R.id.monitoring_menu:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.layout,new Monitering_Fragment()).commit();
//                        overridePendingTransition(0,0);
//                        break;
//                    case R.id.prediction_menu:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.layout,new Prediction_Fragments()).commit();
//                        overridePendingTransition(0,0);
//                        break;
//                    case R.id.logout_menu:
//                        FirebaseAuth.getInstance().signOut();
//                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                        finish();
//                        break;
//                    default:
//                        Toast.makeText(dashboard.this, "An issue", Toast.LENGTH_SHORT).show();
//                }
//                drawerLayout.closeDrawer(GravityCompat.START);
//                return true;
//    }
    @Override
    public void onStart(){
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connection_receiver, filter);
     super.onStart();
    }
    @Override
    public void onStop(){
        unregisterReceiver(connection_receiver);
        super.onStop();
    }

}