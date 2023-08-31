package com.example.smartmoniteringandpredictionofagriculturesystem;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.AppCompatButton;

public class Connection_Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(!isConnected(context)){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View layout_dialog = LayoutInflater.from(context).inflate(R.layout.check_internet_dialog, null);
            builder.setView(layout_dialog);
            AppCompatButton btnRetry = layout_dialog.findViewById(R.id.retry);

            //Show Dialog

            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.setCancelable(false);
            dialog.getWindow().setGravity(Gravity.CENTER);
          btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    onReceive(context,intent);
                }
            });
        }
    }

    public boolean isConnected(Context context){
//        try{
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(cm != null){
                NetworkInfo[] networkInfo = cm.getAllNetworkInfo();
                if( networkInfo != null){
                    for(int i=0; i<networkInfo.length; i++){
                        if(networkInfo[i].getState() == NetworkInfo.State.CONNECTED){
                            return true;
                        }
                    }
                }
            }
//        }
//        catch (NullPointerException e){
//            e.printStackTrace();
            return false;
//        }
    }

}
