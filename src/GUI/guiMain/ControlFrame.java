package GUI.guiMain;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GUI.javaFx.AttitudeFx;
import GUI.utils.Mathbox;
import GUI.utils.Quaternion;
import javafx.application.Platform;

public class ControlFrame {

	private JPanel mainPanel;
	
	public static Font smallFont			  = new Font("Verdana", Font.LAYOUT_LEFT_TO_RIGHT, 10);
	private Quaternion qN;		// Normalized quaternion vector 
	
	JLabel nqw;
	JLabel nqy;
	JLabel nqx;
	JLabel nqz ;
	
	 JLabel labelEuler1 ;
	 JLabel labelEuler2 ;
	 JLabel labelEuler3 ;
	 
	   private Color backgroundColor;
	   private Color labelColor;
	 
	    public static DecimalFormat numberFormat =  new DecimalFormat("##.###");
	    public static DecimalFormat quatFormat =  new DecimalFormat("#.#####");
	
	AttitudeFx aFrame;
	AttitudeFrame attitudeFrame;

	public ControlFrame(AttitudeFrame frame) {
		this.aFrame = frame.getSpaceShipView();
		this.attitudeFrame = frame; 
		
	     labelColor = new Color(220,220,220);   
	   	 backgroundColor = new Color(41,41,41);

		qN = new Quaternion(1,0,0,0);
		
		int globalX = 120;
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBackground(backgroundColor);
		mainPanel.setPreferredSize(new Dimension(globalX,450));
		
		
		JPanel indicatorNumberPanel = new JPanel();
		indicatorNumberPanel.setLayout(new GridLayout(8,1));
		indicatorNumberPanel.setSize(new Dimension(globalX,250));
		indicatorNumberPanel.setBackground(backgroundColor);
		
		 nqw = new JLabel("qw= "+qN.w);
		 nqw.setForeground(labelColor);
		 nqx = new JLabel("qx= "+qN.x);
		 nqx.setForeground(labelColor);
		 nqy = new JLabel("qy= "+qN.y);
		 nqy.setForeground(labelColor);
		 nqz = new JLabel("qz= "+qN.z);
		 nqz.setForeground(labelColor);
		 
		  labelEuler1 = new JLabel("");
		  labelEuler2 = new JLabel("");
		  labelEuler3 = new JLabel("");
		  
		  labelEuler1.setForeground(labelColor);
		  labelEuler2.setForeground(labelColor);
		  labelEuler3.setForeground(labelColor);

		 indicatorNumberPanel.add(nqw);
		 indicatorNumberPanel.add(nqx);
		 indicatorNumberPanel.add(nqy);
		 indicatorNumberPanel.add(nqz);
		 
		 indicatorNumberPanel.add(labelEuler1);
		 indicatorNumberPanel.add(labelEuler2);
		 indicatorNumberPanel.add(labelEuler3);
		 
	     mainPanel.add(indicatorNumberPanel, BorderLayout.CENTER);
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public void updateIndicators(Quaternion qNn){
		qN = qNn;
		try {
			 nqw.setText("qw= "+quatFormat.format(qN.w));
			 nqx.setText("qx= "+quatFormat.format(qN.x));
			 nqy.setText("qy= "+quatFormat.format(qN.y));
			 nqz.setText("qz= "+quatFormat.format(qN.z));

			double[][] qVec = {{qN.w},{qN.x},{qN.y},{qN.z}};
			double[][] EulerAngle = Mathbox.Quaternions2Euler(qVec);
			labelEuler1.setText("E1[deg]= "+numberFormat.format(Math.toDegrees(EulerAngle[0][0])));
			labelEuler2.setText("E2[deg]= "+numberFormat.format(Math.toDegrees(EulerAngle[1][0])));
			labelEuler3.setText("E3[deg]= "+numberFormat.format(Math.toDegrees(EulerAngle[2][0])));
		} catch (Exception panelExcp) {
			System.out.println("Updating indicators failed. "+panelExcp);
		}
		
		
		try {
			Platform.runLater(new Runnable() {
	
				@Override
				public void run() {
					aFrame.modelRotation(qN);	
				}
			
			});
		} catch (Exception aFramExp) {
			System.out.println("Updating 3D failed. "+aFramExp);
		}

	}

	public Quaternion getqN() {
		return qN;
	}

}
