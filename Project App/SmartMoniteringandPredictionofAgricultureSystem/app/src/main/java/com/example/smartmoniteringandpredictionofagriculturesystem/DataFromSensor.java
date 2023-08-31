package com.example.smartmoniteringandpredictionofagriculturesystem;

import java.util.Map;

public class DataFromSensor {
    int air=-1;
    int humidity=-1;
    int nitrogen=-1;
    int phosphorus=-1;
    int potassium=-1;
    int rain=-1;
    int temperature=-1;
    int soil=-1;


      DataFromSensor(Map<String,Integer> map){
          if(map.get("Air")!=null)
        this.air=map.get("Air");
          if(map.get("Humidity")!=null)
        this.humidity=map.get("Humidity");
          if(map.get("Nitrogen")!=null)
        this.nitrogen=map.get("Nitrogen");
          if(map.get("Phosphorous")!=null)
        this.phosphorus=map.get("Phosphorous");
          if(map.get("Potassium")!=null)
        this.potassium=map.get("Potassium");
          if(map.get("Rain")!=null)
        this.rain=map.get("Rain");
          if(map.get("Soil")!=null)
        this.soil=map.get("Soil");
          if(map.get("Temperature")!=null)
        this.temperature=map.get("Temperature");
    }

    public DataFromSensor() {

    }
}