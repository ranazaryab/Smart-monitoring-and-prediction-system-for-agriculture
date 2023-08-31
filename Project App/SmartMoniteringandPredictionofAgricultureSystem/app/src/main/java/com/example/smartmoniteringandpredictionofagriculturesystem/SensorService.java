package com.example.smartmoniteringandpredictionofagriculturesystem;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class SensorService extends Service {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    ArrayList<DatabaseModel> database = new ArrayList<>();
    

    public SensorService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //start the foreground service
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            startForeground(1, new Notification());

        FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(getBaseContext(), "DATA UPDATED", Toast.LENGTH_SHORT).show();
                if (snapshot.exists()) {
                    Map<String, Integer> map = new HashMap();
                    snapshot.getChildren().forEach(new Consumer<DataSnapshot>() {
                        @Override
                        public void accept(DataSnapshot dataSnapshot) {
                            map.put(dataSnapshot.getKey(), dataSnapshot.getValue(Integer.class));
                        }
                    });
                    DataFromSensor data = new DataFromSensor(map);
                    Singleton singleton = new Singleton().getInstance();
                    singleton.setSensorData(data);
                    if (data.soil <= 25) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            sendNoti();
                        }
                    }
                    else if(data.soil > 25){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            sendNotifi();
                        }
                    }
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseFirestore.getInstance()
                .collection("PREDICTION")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        database= new ArrayList<>();
                        queryDocumentSnapshots.getDocuments().forEach(new Consumer<DocumentSnapshot>() {
                            @Override
                            public void accept(DocumentSnapshot documentSnapshot) {
                                database.add(new DatabaseModel(documentSnapshot.getData(),documentSnapshot.getId()));
                            }
                        });

                        new Singleton().getInstance().setDatabase(database);
                    }

                });

    }


    //For Build versions higher than Android Oreo, we launch
    // a foreground service in a different way. This is due to the newly
    // implemented strict notification rules, which require us to identify
    // our own notification channel in order to view them correctly.
    @RequiresApi(Build.VERSION_CODES.O)
    private void startMyOwnForeground() {
        String NOTIFICATION_CHANNEL_ID = "example.permanence";
        String channelName = "Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_MIN);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle("Monitoring Land Condition.")
                .setContentText("System is Running")

                //this is important, otherwise the notification will show the way
                //you want i.e. it will show some default notification
                .setSmallIcon(R.drawable.ic_launcher_foreground)

                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);
    }


    @Override
    public void onDestroy() {

        //create an Intent to call the Broadcast receiver
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, ReactivateService.class);
        this.sendBroadcast(broadcastIntent);
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void sendNoti() {
        //Define Notification Manager
        String NOTIFICATION_CHANNEL_ID = "low.service";
        String channelName = "Background service";
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_MIN);
        AudioAttributes att = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build();
        chan.setSound(soundUri, att);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder
                .setContentTitle("Water Level is Low.")
                .setContentText("Need irrigation..")

                //this is important, otherwise the notification will show the way
                //you want i.e. it will show some default notification
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setSound(soundUri)
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .build();
        manager.notify(100, notificationBuilder.build());

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    void sendNotifi() {
        //Define Notification Manager
        String NOTIFICATION_CHANNEL_ID = "high.service";
        String channelName = "Background service";
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_MIN);
        AudioAttributes att = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build();
        chan.setSound(soundUri, att);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder
                .setContentTitle("Water Level is Good.")
                .setContentText("Stop irrigation..")

                //this is important, otherwise the notification will show the way
                //you want i.e. it will show some default notification
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setSound(soundUri)
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .build();
        manager.notify(100, notificationBuilder.build());

    }

}