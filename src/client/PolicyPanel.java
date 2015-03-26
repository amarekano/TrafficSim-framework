package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import service.TrafficSignalScheduler;

public class PolicyPanel extends JPanel implements ChangeListener {

	private static final long serialVersionUID = 6241308576167461723L;
	private Timer timer;
	private List<TrafficSignalScheduler> schedulers = new ArrayList<TrafficSignalScheduler>();
	JSlider interval_slider;
	
	public PolicyPanel() {
		super();
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setLayout(new GridLayout(3,1));
		
		interval_slider = new JSlider();
		interval_slider = new JSlider();
	    interval_slider.setPaintTicks(true);
	    interval_slider.setPaintLabels(true);
	    interval_slider.setMaximum(50);
	    interval_slider.setMinimum(0);
	    interval_slider.setMajorTickSpacing(10);
	    interval_slider.setMinorTickSpacing(5);
	    interval_slider.setPreferredSize(new Dimension(7,5));
	    interval_slider.addChangeListener(this);
	    interval_slider.setName("lights");
	    interval_slider.setEnabled(false);
	   // add(interval_slider);
	    
	    JPanel interval_panel = new JPanel(new GridLayout(2, 1));
	    interval_panel.add(new JLabel("Traffic Light Interval (clock ticks)"));
	    interval_panel.add(interval_slider);
	    add(interval_panel);
	   
	    JSlider clock_interval_slider = new JSlider();
	    clock_interval_slider = new JSlider();
	    clock_interval_slider.setPaintTicks(true);
	    clock_interval_slider.setPaintLabels(true);
	    clock_interval_slider.setMaximum(5000);
	    clock_interval_slider.setMinimum(0);
	    clock_interval_slider.setMajorTickSpacing(500);
	    clock_interval_slider.setMinorTickSpacing(50);
	    clock_interval_slider.addChangeListener(this);
	    java.util.Hashtable<Integer,JLabel> labelTable = new java.util.Hashtable<Integer,JLabel>();
	    labelTable.put(new Integer(0), new JLabel("0.1"));
	    labelTable.put(new Integer(500), new JLabel("0.5"));
	    labelTable.put(new Integer(1000), new JLabel("1.0"));
	    labelTable.put(new Integer(2000), new JLabel("2.0"));
	    labelTable.put(new Integer(3500), new JLabel("3.5"));
	    labelTable.put(new Integer(5000), new JLabel("5.0"));
	    clock_interval_slider.setLabelTable( labelTable );
	    clock_interval_slider.setName("clock");
	    
	    JPanel clock_interval_panel = new JPanel(new GridLayout(2,1));
	    clock_interval_panel.add(new JLabel("Clock Interval (seconds)"));
	    clock_interval_panel.add(clock_interval_slider);
	    add(clock_interval_panel);
	    
	    //AM > Add controls to set acceleration and velocity profiles at destinations
	    JPanel profilePanel = new JPanel(new GridLayout(3,1));
	    //AM >  Create controls to select destinations
	    JPanel destinationSelector = new JPanel(new FlowLayout());
	    JComboBox<String> destinationBox = new JComboBox<String>();
	    JLabel destinationLabel = new JLabel("Destination Selected");
	    destinationSelector.add(destinationLabel);
	    destinationSelector.add(destinationBox);
	    profilePanel.add(destinationSelector);
	    
	    
	    //AM > Create controls to set the velocity profile
	    JPanel velocityProfile = new JPanel(new FlowLayout());
	    JLabel velocityLabel = new JLabel("Configure Velocity Profile");
	    JTextField maxVelocityTF = new JTextField("Max velocity");
	    JTextField minVelocityTF = new JTextField("Min veloctiy");
	    JTextField velocityProbTF = new JTextField("Profile probability");
	    JButton applyVelocityProfile = new JButton("Apply");
	    velocityProfile.add(velocityLabel);
	    velocityProfile.add(maxVelocityTF);
	    velocityProfile.add(minVelocityTF);
	    velocityProfile.add(velocityProbTF);
	    velocityProfile.add(applyVelocityProfile);
	    profilePanel.add(velocityProfile);
	    
	    JPanel accelerationProfile = new JPanel(new FlowLayout());
	    JLabel accelerationLabel = new JLabel("Configure Acceleration Profile");
	    JTextField maxAccelerationTF = new JTextField("Max Acceleration");
	    JTextField minAccelerationTF = new JTextField("Min Acceleration");
	    JTextField accelerationProbTF = new JTextField("Profile probability");
	    JButton applyAccelerationProfile = new JButton("Apply");
	    accelerationProfile.add(accelerationLabel);
	    accelerationProfile.add(maxAccelerationTF);
	    accelerationProfile.add(minAccelerationTF);
	    accelerationProfile.add(accelerationProbTF);
	    accelerationProfile.add(applyAccelerationProfile);
	    profilePanel.add(accelerationProfile);
	    
	  /*  //AM > Create a slider to configure driver behaviour
	    JPanel driverBehaviour = new JPanel(new GridLayout(2,1));
	    JLabel behaviourLabel = new JLabel("Driver Behaviour");
	    JSlider behaviourSlider = new JSlider();
	    behaviourSlider.setPaintTicks(true);
	    behaviourSlider.setPaintLabels(true);
	    behaviourSlider.setMaximum(100);
	    behaviourSlider.setMinimum(0);
	    behaviourSlider.setMajorTickSpacing(50);
	    behaviourSlider.setMinorTickSpacing(10);
	    behaviourSlider.addChangeListener(this);
	    java.util.Hashtable<Integer,JLabel> labelTable2 = new java.util.Hashtable<Integer,JLabel>();
	    labelTable2.put(new Integer(0), new JLabel("Reckless"));
	    labelTable2.put(new Integer(25), new JLabel("Aggressive"));
	    labelTable2.put(new Integer(50), new JLabel("Cruise Control"));
	    labelTable2.put(new Integer(75), new JLabel("Cautious"));
	    labelTable2.put(new Integer(100), new JLabel("Extremely Cautious"));
	    behaviourSlider.setLabelTable( labelTable2 );
	    behaviourSlider.setName("behaviour");
	    driverBehaviour.add(behaviourLabel);
	    driverBehaviour.add(behaviourSlider);
	    profilePanel.add(driverBehaviour);
	    gbc.gridy = 2;*/
	    add(profilePanel);
	}

	public void setClockTimer(Timer tm)
	{
		this.timer = tm;
	}
	
	public void addLightScheduler(TrafficSignalScheduler scheduler)
	{
		schedulers.add(scheduler);
		if(schedulers.size() > 0)
			interval_slider.setEnabled(true);
	}
	
	
	@Override
	public void stateChanged(ChangeEvent e) {
	    JSlider source = (JSlider) e.getSource();
        if(source.getName().equalsIgnoreCase("clock"))
        {
        	if(!source.getValueIsAdjusting())
        	{
        	  timer.setDelay(source.getValue() <= 100 ? 100: source.getValue());
        	}	
        }
        if(source.getName().equalsIgnoreCase("lights"))
        {
        	if(!source.getValueIsAdjusting())
        	{
        		for(TrafficSignalScheduler scheduler: schedulers)
        		{
        			scheduler.setSignalInterval(source.getValue() < 1 ? 1 : source.getValue());
        		}
        	}
        }
	}


}
