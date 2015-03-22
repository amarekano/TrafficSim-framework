package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class PolicyPanel extends JPanel {

	private static final long serialVersionUID = 6241308576167461723L;

	public PolicyPanel() {
		super();
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		//setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		setLayout(new GridLayout(3,1));
		
		JLabel clock_label=new JLabel("Clock: ");
		JLabel clock_time=new JLabel("00");
		JButton start= new JButton();
		
		JSlider interval_slider = new JSlider();
		interval_slider = new JSlider();
	    interval_slider.setPaintTicks(true);
	    interval_slider.setPaintLabels(true);
	    interval_slider.setMaximum(50);
	    interval_slider.setMinimum(0);
	    interval_slider.setMajorTickSpacing(10);
	    interval_slider.setMinorTickSpacing(5);
	    interval_slider.setPreferredSize(new Dimension(7,5));
	   // add(interval_slider);
	    
	    JPanel interval_panel = new JPanel(new GridLayout(2,1));
	    interval_panel.add(new JLabel("Traffic Light Interval"));
	    interval_panel.add(interval_slider);
	    
	    
	    add(interval_panel);
	    
	    
	    JSlider velocity_slider = new JSlider();
		velocity_slider = new JSlider();
	    velocity_slider.setPaintTicks(true);
	    velocity_slider.setPaintLabels(true);
	    velocity_slider.setMaximum(50);
	    velocity_slider.setMinimum(0);
	    velocity_slider.setMajorTickSpacing(10);
	    velocity_slider.setMinorTickSpacing(5);
	   // add(interval_slider);
	    
	    JPanel velocity_panel = new JPanel(new GridLayout(2,1));
	    velocity_panel.add(new JLabel("Max Velocity"));
	    velocity_panel.add(velocity_slider);
	    
	    
	    add(velocity_panel);
	    
	    // from 0 to 9, in 1.0 steps start value 5  
	    SpinnerNumberModel model1 = new SpinnerNumberModel(5, 1, 60, 1); 
	    JSpinner spinner = new JSpinner(model1);
	    ((DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);
	    

	    JLabel clock_interval_label = new JLabel("Clock Interval");
	    JPanel clock_panel = new JPanel(new GridLayout(2,1));
	    clock_panel.add(clock_interval_label);
	    clock_panel.add(spinner);
	    add(clock_panel);
		
	}


}
