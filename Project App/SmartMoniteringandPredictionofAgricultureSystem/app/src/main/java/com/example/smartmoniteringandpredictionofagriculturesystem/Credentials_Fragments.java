package com.example.smartmoniteringandpredictionofagriculturesystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Credentials_Fragments extends Fragment{

    private FirebaseAuth auth;
    private FirebaseUser user;
    Button change_pass;
    TextInputLayout email,old_pass,new_pass;
    ProgressDialog loadingDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_credentials__fragments, container, false);
        change_pass = (Button) view.findViewById(R.id.change_pass);
        email = (TextInputLayout) view.findViewById(R.id.userlayout);
        old_pass = (TextInputLayout) view.findViewById(R.id.oldpasslayout);
        new_pass = (TextInputLayout) view.findViewById(R.id.newpass_layout);
        loadingDialog = new ProgressDialog(getActivity());
        auth = FirebaseAuth.getInstance();

        // Get Instance from Dashboard Activity
        user = auth.getInstance().getCurrentUser();
        if (user != null) {

            String user_email = user.getEmail();
            email.getEditText().setText(user_email);
        }
        // End Instance code

        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String oldpass = old_pass.getEditText().getText().toString();
                final String newpass = new_pass.getEditText().getText().toString();
                if(!oldpass.isEmpty()){
                    old_pass.setError(null);
                    old_pass.setErrorEnabled(false);
                    if(!newpass.isEmpty()){
                        new_pass.setError(null);
                        new_pass.setErrorEnabled(false);
                        loadingDialog.setTitle("Credential");
                        loadingDialog.setMessage("Changing password...");
                        loadingDialog.show();
                        String currentEmail = user.getEmail();
                        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldpass);
                        user.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {

                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            user.updatePassword(newpass)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(getContext(), "Password was changed successfully", Toast.LENGTH_LONG).show();
                                                                FirebaseAuth.getInstance().signOut();
                                                                startActivity(new Intent(getContext(), MainActivity.class));
                                                                getActivity().finish();
                                                            }
                                                            loadingDialog.dismiss();
                                                        }
                                                    });
                                        } else {
                                            Toast.makeText(getActivity(), "Authentication failed, wrong password", Toast.LENGTH_LONG).show();
                                            loadingDialog.dismiss();
                                        }
                                    }
                                });
                    }
                    else {
                        new_pass.setError("Please Enter New Password");
                    }
                }
                else {
                    old_pass.setError("Please Enter Old Password");
                }
            }
        });
        return view;
    }
}