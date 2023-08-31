package com.example.smartmoniteringandpredictionofagriculturesystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Button sign_in;
    TextInputLayout user_var, pass_var;
    TextView forget_pass;
    public ProgressDialog login_progress;
    private FirebaseAuth mAuth;

    Connection_Receiver connection_receiver = new Connection_Receiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sign_in = (Button) findViewById(R.id.sign_in);
        user_var = (TextInputLayout) findViewById(R.id.userlayout);
        pass_var = (TextInputLayout) findViewById(R.id.passlayout);
        forget_pass = (TextView) findViewById(R.id.forget_password);
        login_progress = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();


        //Start login code
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = user_var.getEditText().getText().toString();
                final String pass = pass_var.getEditText().getText().toString();
                if (!name.isEmpty()) {
                    user_var.setError(null);
                    user_var.setErrorEnabled(false);
                    if (!pass.isEmpty()) {
                        pass_var.setError(null);
                        pass_var.setErrorEnabled(false);
                        login_progress.setTitle("Logging In");
                        login_progress.setMessage("Please Wait ");
                        login_progress.setCanceledOnTouchOutside(false);
                        login_progress.show();
                        mAuth.signInWithEmailAndPassword(name, pass)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Intent intent = new Intent(MainActivity.this, dashboard.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                           login_progress.hide();
                                            new AlertDialog.Builder(MainActivity.this)
                                                    .setIcon(R.drawable.ic_baseline_block_24)
                                                    .setTitle("Wrong Credentials")
                                                    .setMessage("Please Check Your Username and Password Thank You")
                                                    .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            dialogInterface.cancel();
                                                            login_progress.hide();
                                                        }
                                                    }).show();
                                    }
                                });
                    } else {
                        pass_var.setError("Please enter password");
                    }
                } else {
                    user_var.setError("Please enter Username");
                }
            }
        });
        // End login code

    }

    public void forget_password(View view) {
        Intent intent = new Intent(this, Forget_Password.class);
        startActivity(intent);
    }

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

    @Override
    public void onPause(){
        super.onPause();
        pass_var.getEditText().getText().clear();
    }
}