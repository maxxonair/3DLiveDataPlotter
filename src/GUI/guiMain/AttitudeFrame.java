package GUI.guiMain;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

import GUI.javaFx.AttitudeFx;
import GUI.utils.Quaternion;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

public class AttitudeFrame {

	private JPanel mainPanel;
	
	 private static AttitudeFx spaceShipView;
	 
	 private static Quaternion quatTemp;
	 
	 private String objectFilePath;
	 private double modelScale; 
	
	public AttitudeFrame(String objectFilePath, double modelScale) {
		quatTemp = new Quaternion(1,0,0,0);
		this.modelScale = modelScale; 
        spaceShipView = new AttitudeFx();
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.addComponentListener(new ResizeListener() {
			@Override
		    public void componentResized(ComponentEvent e) {
		        //System.out.println(mainPanel.getHeight()+"|"+mainPanel.getWidth());
		        spaceShipView.updateFrameSize( mainPanel.getWidth(),  mainPanel.getHeight());
		    }
		});
		mainPanel.setSize(450,450);
		
		this.objectFilePath = objectFilePath;
		
		createSpaceshipView();
	}
	
	public void createSpaceshipView() {
        final JFXPanel fxPanel = new JFXPanel();
        fxPanel.setSize(450,450);
        mainPanel.add(fxPanel,BorderLayout.CENTER);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	try {
            	spaceShipView.start(fxPanel, objectFilePath, modelScale);
            	
            	} catch (Exception runExcp){
            		System.out.println(runExcp);
            		System.out.println("Creating javaFx failed.");
            	}
            	
            }
       });
      
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}
	
	public static Quaternion getQuatTemp() {
		return quatTemp;
	}

	public static void setQuatTemp(Quaternion quatTemp) {
		AttitudeFrame.quatTemp = quatTemp;
		spaceShipView.modelRotation(quatTemp);
	}

	public void refresh() {
		mainPanel.removeAll();
        final JFXPanel fxPanel = new JFXPanel();
        mainPanel.add(fxPanel, BorderLayout.CENTER);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	//spaceShipView.model.getChildren().removeAll();
            //spaceShipView.coordinateSystem.getChildren().removeAll();
            	spaceShipView.start(fxPanel, objectFilePath, modelScale);
            }
       });
      
	}

	public AttitudeFx getSpaceShipView() {
		return spaceShipView;
	}
	
	
}
class ResizeListener extends ComponentAdapter {
    public void componentResized(ComponentEvent e) {
        // Recalculate the variable you mentioned
    }
    
    
}
