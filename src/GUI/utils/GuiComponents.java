package GUI.utils;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JSlider;

public class GuiComponents {
	public static Font smallFont			  = new Font("Verdana", Font.LAYOUT_LEFT_TO_RIGHT, 10);
	public static JSlider getGuiSlider(Font font, int length, int low , int high) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL);
        
        slider.setSize(length,40);
        slider.setMaximum(high);
        slider.setMinimum(low);
        slider.setMajorTickSpacing((high-low)/18);
        slider.setPaintTicks(true);
        slider.setBackground(Color.WHITE);
        slider.setForeground(Color.BLACK);
        slider.setPaintLabels(false);


		return slider;
	}
	
	public static JSlider getGuiSliderSpeed(Font font, int length, int low, int midval, int high, Color foregroundColor, Color backgroundColor) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL);
        
        slider.setSize(length,40);
        slider.setMaximum(high);
        slider.setMinimum(low);
        slider.setMajorTickSpacing((high-low)/10);
        slider.setPaintTicks(true);
        slider.setBackground(Color.WHITE);
        slider.setForeground(Color.BLACK);
       // slider.setPaintLabels(true);
 
        slider.setValue(8);
        
		return slider;
	}
	

}	
