ButterflyProject
================

Exploring the fundamental interconnectedness of all things

This project was developed over the space of a day for the developer challenge at eResearch Australasia 2012. See http://butterflyproject.info for more information. Most of this code has been developed from existing open source code, see the source code for links and credits.


## Build instructions


### Sensor unit

You will need an Arduino (I used a Nano 328) with a USB cable, a DHT11 temperature/humidity sensor and some hookup wire

See http://arduino.cc/playground/Main/DHT11Lib for more information about the DHT11 sensor

* Connect the sensor unit to the Arduino (sensor pin 1 to arduino 5V, sensor data (pin 2) to arduino pin D2, sensor pin 4 to ground). * Copy the contents of the arduino folder into your Arduino source folder (e.g. ~/Documents/Arduino on Mac)
* Open the temperaturesensor sketch and upload to your Arduino (make sure you select the correct port and board within the Arduino IDE).

Note: if you have access to an ethernet or wifi shield for your Arduino, COSM provides sample code for publishing the sensor data directly from the Arduino, so you could skip using the Java app.

### Uploader

* Import ButterflyProject into eclipse
* You may need to update the build path for the RXTXcomm.jar library
* Update the properties file to include your cosm api key and to match the port for your arduino

When running SensorApp, you will need to set VM arguments to point to the Arduino rxtxSerial jni lib and to run on 32bit architecture, e.g. for Mac OSX with the standard Arduino install location:

`-Djava.library.path=/Applications/Arduino.app/Contents/Resources/Java -d32`