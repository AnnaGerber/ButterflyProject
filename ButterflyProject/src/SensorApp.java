import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

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

public class SensorApp
{

public static ArduinoComm sensorArduino;

   public static void main(String args[]) 
   {
	  try {
	  Properties props = new Properties();
	  //System.setProperty("java.net.useSystemProxies", "true");
	  FileInputStream propsFileInputStream = new FileInputStream(new File("sensorconfig.properties"));
	  props.load(propsFileInputStream);
	  String cosmKey = props.getProperty("cosmkey");
	  String usbPort = props.getProperty("usbport");
          sensorArduino = new ArduinoComm(cosmKey);
          //Start the arduino connection - usb port - baud rate
          sensorArduino.start(usbPort,9600);
	  } catch (Exception e) {
		  System.out.println(e.getMessage());
	  }
   }

}
