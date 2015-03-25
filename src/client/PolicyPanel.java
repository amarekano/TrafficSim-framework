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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PolicyPanel extends JPanel implements ChangeListener {

	private static final long serialVersionUID = 6241308576167461723L;

	public PolicyPanel() {
		super();
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		//setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		setLayout(new GridLayout(3,1));
		
		
		JSlider interval_slider = new JSlider();
		interval_slider = new JSlider();
	    interval_slider.setPaintTicks(true);
	    interval_slider.setPaintLabels(true);
	    interval_slider.setMaximum(50);
	    interval_slider.setMinimum(0);
	    interval_slider.setMajorTickSpacing(10);
	    interval_slider.setMinorTickSpacing(5);
	    interval_slider.setPreferredSize(new Dimension(7,5));
	    interval_slider.addChangeListener(this);
	    interval_slider.setName("funky 1");
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
	    velocity_slider.addChangeListener(this);
	    velocity_slider.setName("funky 2");
	   // add(interval_slider);
	    
	    JPanel velocity_panel = new JPanel(new GridLayout(2,1));
	    velocity_panel.add(new JLabel("Max Velocity"));
	    velocity_panel.add(velocity_slider);
	    
	    
	    add(velocity_panel);
	    
	    
	    
	    JSlider clock_interval_slider = new JSlider();
	    clock_interval_slider = new JSlider();
	    clock_interval_slider.setPaintTicks(true);
	    clock_interval_slider.setPaintLabels(true);
	    clock_interval_slider.setMaximum(60);
	    clock_interval_slider.setMinimum(0);
	    clock_interval_slider.setMajorTickSpacing(10);
	    clock_interval_slider.setMinorTickSpacing(5);
	    clock_interval_slider.addChangeListener(this);
	    clock_interval_slider.setName("funky 3");
	   // add(interval_slider);
	    
	    JPanel clock_interval_panel = new JPanel(new GridLayout(2,1));
	    clock_interval_panel.add(new JLabel("Clock Interval"));
	    clock_interval_panel.add(clock_interval_slider);
	    
	    
	    add(clock_interval_panel);
	    
	    
	    
	    
	    
	    
	    
	    
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		// update text field when the slider value changes
        JSlider source = (JSlider) e.getSource();
        System.out.println("" + source.getValue());
        System.out.println("name" + source.getName());
        //textField.setText();
		
	}


}
