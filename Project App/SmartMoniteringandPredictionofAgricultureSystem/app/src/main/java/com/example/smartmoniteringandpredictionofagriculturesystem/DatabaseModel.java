package com.example.smartmoniteringandpredictionofagriculturesystem;

import java.util.Map;

public class DatabaseModel{
    String id="";
    String crop="";

    DatabaseModel(){
        id="";
        crop="";
    }
    DatabaseModel(Map<String, Object> map, String id){
        this.id=id;
        this.crop=map.get("CROP").toString();
    }
}