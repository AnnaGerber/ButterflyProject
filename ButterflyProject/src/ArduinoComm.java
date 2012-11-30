//Based on ArduinoComm part of OpenEnergyMonitor.org project
/*
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

//Code builds upon this great example:
//http://www.csc.kth.se/utbildning/kth/kurser/DH2400/interak06/SerialWork.java

import java.io.*;
import java.util.TooManyListenersException;
import Pachube.*;
import Pachube.httpClient.HttpClient;
import Pachube.httpClient.HttpMethod;
import Pachube.httpClient.HttpRequest;
import Pachube.httpClient.HttpResponse;

//Load RXTX Library
import gnu.io.*;

class ArduinoComm implements SerialPortEventListener
{
   SerialPort mySerialPort;
   InputStream in;
   BufferedReader br;
   String cosmKey;
   boolean stop=false;
   public ArduinoComm(String cosmKey){
	   super();
	   this.cosmKey = cosmKey;
   }
   //This opens the communications port with the arduino
   public void start(String portName,int baudRate) {
      stop=false; 
      try {
         //Finds and opens the port
         CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(portName);
         mySerialPort = (SerialPort)portId.open("my_java_serial" + portName, 2000);

         //configure the port
         try {
            mySerialPort.setSerialPortParams(baudRate,
            mySerialPort.DATABITS_8,
            mySerialPort.STOPBITS_1,
            mySerialPort.PARITY_NONE);
         } 
         catch (UnsupportedCommOperationException e){
            System.out.println("Probably an unsupported speed");
         }

         //establish stream for reading from the port
         try {
            in = mySerialPort.getInputStream();
            //Buffered reader allows us to read a line.
            br = new BufferedReader(new InputStreamReader(in));
         } 
         catch (IOException e) { 
            System.out.println("couldn't get streams");
         }

         // we could read from "in" in a separate thread, but the API gives us events
         try {
            mySerialPort.addEventListener(this);
            mySerialPort.notifyOnDataAvailable(true);
            System.out.println("Event listener added");
         } 
         catch (TooManyListenersException e) {
            System.out.println("couldn't add listener");
         }
      }
      catch (Exception e) { 
         System.out.println("Port in Use: "+e);
      }
   }

   //Used to close the serial port
   public void closeSerialPort() {
      try {
         in.close();
         stop=true; 
         mySerialPort.close();
         System.out.println("Serial port closed");

      }
      catch (Exception e) {
    	  System.out.println(e);
      }
   }

   public void serialEvent(SerialPortEvent event) { 
	  
	   
	      //Reads in data while data is available
	      while (event.getEventType()== SerialPortEvent.DATA_AVAILABLE && stop==false) {
	    	try {
	        	// parse the CSV data and publish to cosm (formerly known as pachube)
	            String reading = br.readLine();
	        	System.out.println(reading);
	            String [] readingValues = reading.split(",");
	            double humidity = Float.parseFloat(readingValues[0]);
	            double temperature = Float.parseFloat(readingValues[1]);
	            double dewPoint = Float.parseFloat(readingValues[2]);
	            double dewPointFast = Float.parseFloat(readingValues[3]);
	            try {
		            Pachube p = new Pachube(this.cosmKey);
		            
		            Feed f = p.getFeed(83093);
		            
	                f.updateDatastream(0, humidity);
	                f.updateDatastream(1, temperature);
	                f.updateDatastream(2, dewPoint);
	                f.updateDatastream(3, dewPointFast);
	            } catch (PachubeException e2) {
	            	System.out.println(e2.errorMessage);
	            	
	                e2.printStackTrace();
	            }
	         } 
	         catch (IOException e) {
	         }
         
	      }
  
   }

}
