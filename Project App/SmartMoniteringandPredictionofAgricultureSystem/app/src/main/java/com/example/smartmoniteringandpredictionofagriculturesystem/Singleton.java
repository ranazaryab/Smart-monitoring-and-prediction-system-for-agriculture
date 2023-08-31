package com.example.smartmoniteringandpredictionofagriculturesystem;

import java.util.ArrayList;

public class Singleton {
    private static Singleton instance;
    private DataFromSensor sensorData;
    ArrayList<DatabaseModel> database = new ArrayList<>();

    Singleton getInstance(){
        if (instance==null)
            instance=new Singleton();

        return instance;
    }

    public DataFromSensor getSensorData() {
        if (sensorData==null)
           return new DataFromSensor();
        else
            return sensorData;
    }

    public void setSensorData(DataFromSensor sensorData) {
        this.sensorData = sensorData;
    }

    public ArrayList<DatabaseModel> getDatabase() {
        return database;
    }

    public void setDatabase(ArrayList<DatabaseModel> database) {
        this.database = database;
    }
}
