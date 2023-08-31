#include "FirebaseESP8266.h"  // Install Firebase ESP8266 library
#include <ESP8266WiFi.h>
#include <String>
#include <sstream>

#define FIREBASE_HOST "smart-monitoring-and-pre-f4c80-default-rtdb.firebaseio.com"
#define FIREBASE_AUTH "84vI3QazV4YV2s2XMkRgnSqb49sEeHuSbG8FJXQ9"
#define WIFI_SSID "PTCL-BB"
#define WIFI_PASSWORD "zabi1234"

////int led = D5;     // Connect LED to D5
FirebaseData firebaseData;
FirebaseData Data1;
FirebaseData Data2;
FirebaseData Data3;
FirebaseData Data4;

FirebaseJson json;




void setup()
{


  Serial.begin(115200);


  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to Wi-Fi");
  while (WiFi.status() != WL_CONNECTED)
  {
    Serial.print(".");
    delay(300);
  }
  Serial.println();
  Serial.print("Connected with IP: ");
  Serial.println(WiFi.localIP());
  Serial.println();
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  Firebase.reconnectWiFi(true);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }

}

String str;
String MQ135, Humidity, Temperature, Soil_Moisture, Rain_Sensor, Nitrogen, Phosphorous, Potassium;
int a, h, t, s, r, n, p, k;
String new_str = "", filtered_data = "";
int T = 0, L = 0, N = 0, tim = 0;

String _filter_data()
{
  for (int i = 0; i < str.length(); i++)
  {
    if (str[i] != '_')
    {
      filtered_data += str[i];
    }
    else
    {
      for (int j = i + 1; j < str.length(); j++)
      {
        new_str += str[j];
      }
      break;
    }
  }
  return filtered_data;
}



void neutral_values()
{
  str = new_str;
  filtered_data = "";
  new_str = "";
}



void loop() {
  if (Serial.available()) {
    Serial.write(Serial.read());
  }
  if (Serial.available() > 0)
  {
    delay(100);
    while (Serial.available() > 0)
    {
      str = Serial.readString();

      MQ135 = _filter_data();
      a = MQ135.toInt();
      neutral_values();

      Humidity = _filter_data();
      h = Humidity.toInt();
      Serial.print(h);
      neutral_values();

      Temperature = _filter_data();
      t = Temperature.toInt();
      neutral_values();

      Soil_Moisture = _filter_data();
      s = Soil_Moisture.toInt();
      neutral_values();

      Rain_Sensor = _filter_data();
      r = Rain_Sensor.toInt();
      neutral_values();

      Nitrogen = _filter_data();
      n = Nitrogen.toInt();
      neutral_values();

      Phosphorous = _filter_data();
      p = Phosphorous.toInt();
      neutral_values();

      Potassium = _filter_data();
      k = Potassium.toInt();
      neutral_values();

    }
  }
  Firebase.setInt(Data1, "Air", (a));
  Firebase.setInt(Data1, "Humidity", (h));
  Firebase.setInt(Data1, "Temperature", (t));
  Firebase.setInt(Data1, "Soil", (s));
  Firebase.setInt(Data1, "Rain", (r));
  Firebase.setInt(Data1, "Nitrogen", (n));
  Firebase.setInt(Data1, "Phosphorous", (p));
  Firebase.setInt(Data1, "Potassium", (k));
  Serial.print(" ");
  delay(100);
}
