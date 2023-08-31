package com.example.smartmoniteringandpredictionofagriculturesystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forget_Password extends AppCompatActivity {
    private Button btn_forget;
    private EditText forget_email;
    private FirebaseAuth auth;
    public ProgressDialog login_progress;
    Connection_Receiver connection_receiver = new Connection_Receiver();

    public Forget_Password() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        auth = FirebaseAuth.getInstance();

        btn_forget = (Button) findViewById(R.id.forget_password);
        forget_email = (EditText) findViewById(R.id.email);
        login_progress = new ProgressDialog(this);

        btn_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String mail = forget_email.getText().toString();
                if(!mail.isEmpty()){
                    forget_email.setError(null);
                    login_progress.setTitle("Reset Password");
                    login_progress.setMessage("Please Wait ");
                    login_progress.setCanceledOnTouchOutside(false);
                    login_progress.show();
                    auth.sendPasswordResetEmail(mail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                        new AlertDialog.Builder(Forget_Password.this)
                                                .setIcon(R.drawable.ic_baseline_email_24)
                                                .setTitle("Check Email")
                                                .setMessage("Please Check Your Spam Email Thank You")
                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                    startActivity(new Intent(Forget_Password.this, MainActivity.class));
                                                    finish();
                                                    }
                                                }).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                        login_progress.hide();
                                        Toast.makeText(Forget_Password.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else{
                    forget_email.setError("Please Enter Email");
                }
            }
        });
    }
//    public boolean isConnected() {
//        try {
//            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
//            return (networkInfo != null && networkInfo.isConnected());
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//            return false;
//        }
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