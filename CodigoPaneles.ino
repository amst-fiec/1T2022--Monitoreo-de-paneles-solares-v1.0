#include <IOXhop_FirebaseStream.h>
#include <IOXhop_FirebaseESP32.h>                               //Se importan las librerías para conectar con firebase

#include <WiFi.h>

#define FIREBASE_HOST "amstg2-default-rtdb.firebaseio.com"          //Nombre del host en firebase
#define FIREBASE_AUTH "eB6JwvnvdzYrSsQwyYAWumpRK4oXcLwJAauWMOKz"  //clave de autenticación de firebase
#define WIFI_SSID "NETLIFE-BAMBOO"               
#define WIFI_PASSWORD "REYBAMBOO"

#define sensorLuz1 A0                             //Se configura este puerto para hacer la lectura del valor del LDR 1
#define sensorLuz2 A3                             //Se configura este puerto para hacer la lectura del valor del LDR 2
#define sensorCarga A6   

#include <ESP32Servo.h>

int pos = 90;

int diferencia;

boolean mismaPosicion = 0;
boolean derecha = 0;
boolean izquierda = 0;

boolean cambioAlaDerecha = 0;
boolean cambioAlaIzquierda = 0;

String angulo = "";


Servo servoMotor;
 
void setup() {
  
  Serial.begin(9600);                             // Iniciamos el monitor serie para mostrar el resultado

  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);           //Se configura la red WiFi con los datos de la red Wifi del Hogar                          
  Serial.print("Connecting to ");
  Serial.print(WIFI_SSID);
  while (WiFi.status() != WL_CONNECTED) {         //Verifica que haya conexión para poder continuar
    Serial.print(".");
    delay(200);
  }
    
  Serial.println();                               //No quitar, conexion wifi

  Serial.print("Connected to ");                  //No quitar, conexion wifi
  Serial.println(WIFI_SSID);                      //No quitar, conexion wifi
  Serial.print("IP Address is : ");               //No quitar, conexion wifi
  Serial.println(WiFi.localIP());                 //Muestra la IP Address asignada
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);   // Se realiza la conexión con la base de datos enviando los datos del Host y la Key
  
  servoMotor.setPeriodHertz(50); 
  servoMotor.attach(13); // Iniciamos el servo para que empiece a trabajar con el pin 13
  servoMotor.write(87);      // Desplazamos a la posición 90º
    
  delay(1000);              // Esperamos 1 segundo

}
 
void loop() {
    angulo = Firebase.getString("/Usuario/jomoran/paneles/posicion");
    Serial.println(angulo);

    int carga = analogRead(sensorCarga);
    
    Serial.print("La carga es: ");
    Serial.println(carga);
    if (carga>300){
          Firebase.setString("/Usuario/jomoran/paneles/estado","Cargando");  
          Firebase.setString("/Usuario/jomoran/paneles/voltaje","Máximo");    
    }else{
          Firebase.setString("/Usuario/jomoran/paneles/estado","No cargando");      
          Firebase.setString("/Usuario/jomoran/paneles/voltaje","Mínimo");    
    }
        
        
    int medida1 = analogRead(sensorLuz1);
    Serial.println(medida1);
    int medida2 = analogRead(sensorLuz2);
    Serial.println(medida2);
    //delay(500);

    diferencia = medida1-medida2;

    //delay(1000);
    
    if(diferencia>0 && abs(diferencia)<300){
      derecha = 0;
      izquierda = 0;
      Serial.println("La diferencia es mayor a cero y menor que 100, a la derecha");
    }else if(diferencia>0 && abs(diferencia)>300){
  
      if(izquierda == 0 && derecha == 0){
         cambioAlaDerecha = 1;
         derecha = 1;
      }else{
        cambioAlaDerecha = 0;
      }
      
    }                                                                                                                   

    
    if(diferencia<0 && abs(diferencia)<300){
      izquierda  = 0;   //esta a la izquierda
      derecha = 0;
      Serial.println("La diferencia es menor a cero y menor que 100, a la izquierda");
    }else if(diferencia<0 && abs(diferencia)>300){

      if(derecha == 0  && izquierda == 0){
         cambioAlaIzquierda = 1;
         izquierda = 1;
      }else{
        cambioAlaIzquierda = 0;
      }
     
    }
                                                                //boolean cambioAlaDerecha = 0;
                                                                //boolean cambioAlaIzquierda = 0;
    delay(1000);

        if(derecha && !izquierda){                         //&& !izquierda      //aqui se lee la activación de la puerta y se abre o se cierra según las lecturas
          
          Serial.print("Cambio a la Derecha ");
          Serial.println(cambioAlaDerecha);
          
          if(cambioAlaDerecha){
              for(pos=87; pos>=42; pos-=1){
                  servoMotor.write(pos);      // Desplazamos a la posición 45º
                  delay(30);
                  cambioAlaDerecha = 0;
              }
              Firebase.setString("/Usuario/jomoran/paneles/posicion","Este");
              Serial.println(angulo);
          }    
              Serial.print("Derecha = ");
              Serial.println(derecha);
              Serial.print("Izquierda = ");
              Serial.println(izquierda);

              Serial.println("Entro y se fue a la derecha");
              
              //delay(1000);
        
        }else if(izquierda && !derecha){                   // && izquierda
          
          Serial.print("Cambio a la izquierda ");
          Serial.println(cambioAlaIzquierda);
          
          if(cambioAlaIzquierda){
              for(pos=87; pos<=132; pos+=1){
                  servoMotor.write(pos);      // Desplazamos a la posición 135º
                  delay(30);
                  cambioAlaIzquierda = 0;
              }    
              Firebase.setString("/Usuario/jomoran/paneles/posicion","Oeste");
              Serial.println(angulo);
          }
              Serial.print("Derecha = ");
              Serial.println(derecha);
              Serial.print("Izquierda = ");
              Serial.println(izquierda);
              
              Serial.println("Entro y se fue a la izquierda");
              
              //delay(1000);
        }else if (izquierda == derecha){
              servoMotor.write(87);      // Desplazamos a la posición 0º
              izquierda = 0;
              derecha = 0;
              Firebase.setString("/Usuario/jomoran/paneles/posicion","Horizontal");
              Serial.println(angulo);

        }
     delay(1000);
     Serial.print("Derecha vale = ");
     Serial.println(derecha);

     Serial.print("Izquierda vale = ");
     Serial.println(izquierda);
     
     //delay(1000);
  
}
