ButterflyProject
================

Exploring the fundamental interconnectedness of all things

Developed for the eResearch Australasia 2012 Developer Challenge

## Build instructions


### Sensor unit

You will need an Arduino (I used a Nano 328) with a USB cable, a DHT11 temperature/humidity sensor and some hookup wire

See http://arduino.cc/playground/Main/DHT11Lib for more information about the DHT11 sensor

* Connect the sensor unit to the Arduino (sensor pin 1 to arduino 5V, sensor data (pin 2) to arduino pin D2, sensor pin 4 to ground). * Copy the contents of the arduino folder into your Arduino source folder (e.g. ~/Documents/Arduino on Mac)
* Open the temperaturesensor sketch and upload to your Arduino (make sure you select the correct port and board within the Arduino IDE).

### Uploader

* Import ButterflyProject into eclipse
* You may need to update the build path for the RXTXcomm.jar library
* Update the properties file to include your cosm api key and to match the port for your arduino
* Run with the VM args similar to the following (you may need to change the library path to locate the rxtxSerial jni library):

`-Djava.library.path=/Applications/Arduino.app/Contents/Resources/Java -d32`