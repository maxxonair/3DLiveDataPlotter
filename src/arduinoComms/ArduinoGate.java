package arduinoComms;

import java.util.Scanner;

import com.fazecast.jSerialComm.SerialPort;


public class ArduinoGate {
	
	private SerialPort port ;
	
	public ArduinoGate() {
		this.port = initializePort();
	}
		
	private SerialPort initializePort() {
	    // determine which serial port to use
	    SerialPort ports[] = SerialPort.getCommPorts();
	    System.out.println("Select a port:");
	    int i = 1;
	    for(SerialPort port : ports) {
	            System.out.println(i++ + ". " + port.getSystemPortName());
	    }
	    @SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
	    int chosenPort = s.nextInt();
	
	    // open and configure the port
	    SerialPort port = ports[chosenPort - 1];
	    if(port.openPort()) {
	            System.out.println("Successfully opened the port.");
	    } else {
	            System.out.println("Unable to open the port.");
	    }
	    port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
	    return port;
	}
	
	

	public SerialPort getPort() {
		return port;
	}

	public static void main(String[] main ) {
		ArduinoGate arduinoGate = new ArduinoGate();
       
        // enter into an infinite loop that reads from the port and updates the GUI
        @SuppressWarnings("resource")
		Scanner data = new Scanner(arduinoGate.getPort().getInputStream());
        while(data.hasNextLine()) {
                try{
                	System.out.println(data.nextLine());
                }catch(Exception e){
                	
                }
        }
	}
}
