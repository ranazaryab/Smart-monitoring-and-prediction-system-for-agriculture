#include <AltSoftSerial.h>
#include "DHT.h"
#include <SoftwareSerial.h>
#define DHTPIN 3
#define DHTTYPE DHT11
#include <Wire.h>
#include <LiquidCrystal_I2C.h>
LiquidCrystal_I2C lcd(0x27, 16, 2);
SoftwareSerial espSerial(4, 5);
//SoftwareSerial mod(2, 3);

#define RE 6
#define DE 7


const byte nitro[] = {0x01, 0x03, 0x00, 0x1e, 0x00, 0x01, 0xe4, 0x0c};
const byte phos[] = {0x01, 0x03, 0x00, 0x1f, 0x00, 0x01, 0xb5, 0xcc};
const byte pota[] = {0x01, 0x03, 0x00, 0x20, 0x00, 0x01, 0x85, 0xc0};

byte values[11];
AltSoftSerial mod;

DHT dht(DHTPIN, DHTTYPE);

String str;
String str1;
String str2;
String str3;
String str4;
String str5;
String str6;
String str7;


void setup()
{
  Serial.begin(115200);
  espSerial.begin(115200);
  mod.begin(9600);
  pinMode(RE, OUTPUT);
  pinMode(DE, OUTPUT);
  digitalWrite(DE, LOW);
  digitalWrite(RE, LOW);
  dht.begin();
  pinMode(A0, INPUT);
  pinMode(A1, INPUT);
  pinMode(A2, INPUT);
  lcd.begin(16,2);
  lcd.backlight();
  lcd.clear();
  lcd.setCursor(3, 0);         // move cursor to   (0, 0)
  lcd.print("Welcome To");     // print message at (0, 0)
  lcd.setCursor(4, 1);         // move cursor to   (2, 1)
  lcd.print("SM & PA.");       // print message at (2, 1)

  delay(3000);

}

int a;
int b;
int c;
byte val1, val2, val3;

void loop()
{

  val1 = nitrogen();
  val2 = phosphorous();
  val3 = potassium();

  Serial.print("Nitrogen: ");
  Serial.print(val1);
  Serial.println(" mg/kg");
  Serial.print("Phosphorous: ");
  Serial.print(val2);
  Serial.println(" mg/kg");
  Serial.print("Potassium: ");
  Serial.print(val3);
  Serial.println(" mg/kg");


  lcd.clear();
  float h = dht.readHumidity();
  float t = dht.readTemperature();
  Serial.print("H: ");
  Serial.print(h);
  Serial.print("% ");
  Serial.print(" T: ");
  Serial.print(t);
  Serial.println("C");
  a = analogRead(A0);
  b = analogRead(A1);
  b = (( 100 - ( (b / 1023.00) * 100 ) ));
  c = analogRead(A2);
  c = (( 100 - ( (c / 1023.00) * 100 ) ));
  str1 = String("coming from arduino: ") + String("MQ135 = ") + String(a);
  str  = String("coming from arduino: ") + String("H = ") + String(h) + String("  T = ") + String(t);
  str2 = String("coming from arduino: ") + String("Soil Moisture = ") + String(b) + String("%");
  str3 = String("coming from arduino: ") + String("Rain = ") + String(c) + String("%");
  str4 = String("coming from arduino: ") + String("Nitrogen= ") + String(val1) + String("mg/kg");
  str5 = String("coming from arduino: ") + String("Phosphorous= ") + String(val2) + String("mg/kg");
  str6 = String("coming from arduino: ") + String("Potassium= ") + String(val3) + String("mg/kg");
  str7 = String(a) + "_" + String(h) + "_" + String(t)+ "_" + String(b)+ "_" + String(c)+ "_" + String(val1)+ "_" + String(val2)+ "_" + String(val3);
//  espSerial.println(str);
//  espSerial.println(str1);
//  espSerial.println(str2);
//  espSerial.println(str3);
//  espSerial.println(str4);
//  espSerial.println(str5);
//  espSerial.println(str6);
  espSerial.println(str7);


  
  lcd.setCursor(0, 0);
  lcd.print("H=");
  lcd.print(h);
  lcd.print("%");
  lcd.print("  R=");
  lcd.print(c);
  lcd.print("%");
  lcd.setCursor(0, 1);
  lcd.print("M=");
  lcd.print(b);
  lcd.print("%");
  lcd.print("      A=");
  lcd.print(a);
  
  delay(5500);
  
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("T=");
  lcd.print(t);
  lcd.print("%");
  lcd.print(" N=");
  lcd.print(val1);
  lcd.print("mg");
  lcd.setCursor(0, 1);
  lcd.print("P=");
  lcd.print(val2);
  lcd.print("mg");
  lcd.print("  K=");
  lcd.print(val3);
  lcd.print("mg");
  
  delay(5500);





  espSerial.println("    ");
}




byte nitrogen() {
  mod.flushInput();
 
  // switch RS-485 to transmit mode
  digitalWrite(DE, HIGH);
  digitalWrite(RE, HIGH);
  delay(1);
 
  // write out the message
  for (uint8_t i = 0; i < sizeof(nitro); i++ ) mod.write( nitro[i] );
 
  // wait for the transmission to complete
  mod.flush();
  
  // switching RS485 to receive mode
  digitalWrite(DE, LOW);
  digitalWrite(RE, LOW);
 
  // delay to allow response bytes to be received!
  delay(200);
 
  // read in the received bytes
  for (byte i = 0; i < 7; i++) {
    values[i] = mod.read();
    Serial.print(values[i], HEX);
    Serial.print(' ');
  }
  return values[4];
}
 
byte phosphorous() {
  mod.flushInput();
  digitalWrite(DE, HIGH);
  digitalWrite(RE, HIGH);
  delay(1);
  for (uint8_t i = 0; i < sizeof(phos); i++ ) mod.write( phos[i] );
  mod.flush();
  digitalWrite(DE, LOW);
  digitalWrite(RE, LOW);
// delay to allow response bytes to be received!
  delay(200);
  for (byte i = 0; i < 7; i++) {
    values[i] = mod.read();
    Serial.print(values[i], HEX);
    Serial.print(' ');
  }
  return values[4];
}
 
byte potassium() {
  mod.flushInput();
  digitalWrite(DE, HIGH);
  digitalWrite(RE, HIGH);
  delay(1);
  for (uint8_t i = 0; i < sizeof(pota); i++ ) mod.write( pota[i] );
  mod.flush();
  digitalWrite(DE, LOW);
  digitalWrite(RE, LOW);
// delay to allow response bytes to be received!
  delay(200);
  for (byte i = 0; i < 7; i++) {
    values[i] = mod.read();
    Serial.print(values[i], HEX);
    Serial.print(' ');
  }
  return values[4];
}
