package GUI.guiMain;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Scanner;

import javax.swing.JPanel;

import GUI.realTimePlot.RealTimePlotElement;
import GUI.utils.Quaternion;
import arduinoComms.ArduinoGate;

public class PlotFrame {
	private JPanel mainPanel;
	
	public static Font smallFont			  = new Font("Verdana", Font.LAYOUT_LEFT_TO_RIGHT, 10);

	private Quaternion qN;		// Normalized quaternion vector 
	private ControlFrame contrlFrame ; 
	private ArduinoGate arduinoGate;
	private RealTimePlotElement realtimePlotElement;
	
	private boolean packageReadOut = true; 
	
	private String arduinoDataDelimiter = " "; 		// Tab delimited data 
	private boolean arduinoListener=true;

	public PlotFrame(ArduinoGate arduinoGate,  ControlFrame contrlFrame) {
		this.contrlFrame = contrlFrame;
		this.arduinoGate = arduinoGate;
		
		qN = new Quaternion(1,0,0,0);
		
		int globalX = 1200;
		int globalY = 250;
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setPreferredSize(new Dimension(globalX,globalY));
		  
		 if(arduinoListener) {
           ThreadArduinoRead t1=new ThreadArduinoRead();  
           t1.start();
		 }
		 
		 realtimePlotElement = new RealTimePlotElement();
		 mainPanel.add(realtimePlotElement.createPlotElement(), BorderLayout.CENTER);
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}
	

	public ArduinoGate getArduinoGate() {
		return arduinoGate;
	}
	
	private Quaternion processArduinoData(String inputLine) {
		Object[] tokens = inputLine.split(arduinoDataDelimiter);
		return new Quaternion(Double.parseDouble(""+tokens[0]),
							  Double.parseDouble(""+tokens[1]),
							  Double.parseDouble(""+tokens[2]),
							  Double.parseDouble(""+tokens[3]));
	}
	
	class ThreadArduinoRead extends Thread{  
		public void run(){  
		System.out.println("Thread ReadingStream is running..."); 
        @SuppressWarnings("resource")
		Scanner data = new Scanner(arduinoGate.getPort().getInputStream());
        while(data.hasNextLine()) {
                try{
                		if(packageReadOut) {
                			System.out.println(data.nextLine());
                		}
                		qN = processArduinoData(data.nextLine());
	                		try {
	                	    realtimePlotElement.updateChart(qN);
	                		}  catch (Exception plotExcp) {
	                			System.err.println(plotExcp);
	                		}
	                		contrlFrame.updateIndicators(qN);
                }catch(Exception e){
                		System.out.println("Error: Fetching data package failed. "+e);
                }
                try {
					Thread.sleep(50);
				} catch (Exception e) {
					e.printStackTrace();
				}
        }
		} 
	}
}
