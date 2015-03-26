package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import core.endpoints.Destination;
import service.TrafficSignalScheduler;

public class PolicyPanel extends JPanel implements ChangeListener,ActionListener, ItemListener {

	private static final long serialVersionUID = 6241308576167461723L;
	private Timer timer;
	private List<TrafficSignalScheduler> schedulers = new ArrayList<TrafficSignalScheduler>();
	JSlider interval_slider;
	JComboBox<Destination> destinationBox;
	List<Destination> destinations = new ArrayList<Destination>();
	JTextField maxVelocityTF;
	JTextField minVelocityTF;
	JTextField velocityProbTF;
	JTextField maxAccelerationTF;
	JTextField minAccelerationTF;
	JTextField accelerationProbTF;

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
		destinationBox = new JComboBox<Destination>();
		destinationBox.addItemListener(this);
		JLabel destinationLabel = new JLabel("Destination Selected");
		destinationSelector.add(destinationLabel);
		destinationSelector.add(destinationBox);
		profilePanel.add(destinationSelector);

		JLabel max = new JLabel("Max");
		JLabel min = new JLabel("Min");
		JLabel prob = new JLabel("Probability");

		//AM > Create controls to set the velocity profile
		JPanel velocityProfile = new JPanel(new FlowLayout());
		JLabel velocityLabel = new JLabel("Configure Velocity Profile");
		maxVelocityTF = new JTextField("Max velocity",4);
		maxVelocityTF.setInputVerifier(new MaxMinVerifier());
		minVelocityTF = new JTextField("Min veloctiy",4);
		minVelocityTF.setInputVerifier(new MaxMinVerifier());
		velocityProbTF = new JTextField("Profile probability",4);
		velocityProbTF.setInputVerifier(new ProbVerifier());
		JButton applyVelocityProfile = new JButton("Apply");
		applyVelocityProfile.setActionCommand("ApplyVelocity");
		applyVelocityProfile.addActionListener(this);
		velocityProfile.add(velocityLabel);
		velocityProfile.add(max);
		velocityProfile.add(maxVelocityTF);
		velocityProfile.add(min);
		velocityProfile.add(minVelocityTF);
		velocityProfile.add(prob);
		velocityProfile.add(velocityProbTF);
		velocityProfile.add(applyVelocityProfile);
		profilePanel.add(velocityProfile);

		JLabel max2 = new JLabel("Max");
		JLabel min2 = new JLabel("Min");
		JLabel prob2 = new JLabel("Probability");
		JPanel accelerationProfile = new JPanel(new FlowLayout());
		JLabel accelerationLabel = new JLabel("Configure Acceleration Profile");
		maxAccelerationTF = new JTextField("Max Acceleration",4);
		maxAccelerationTF.setInputVerifier(new MaxMinVerifier());
		minAccelerationTF = new JTextField("Min Acceleration",4);
		minAccelerationTF.setInputVerifier(new MaxMinVerifier());
		accelerationProbTF = new JTextField("Profile probability",4);
		accelerationProbTF.setInputVerifier(new ProbVerifier());
		JButton applyAccelerationProfile = new JButton("Apply");
		applyAccelerationProfile.setActionCommand("ApplyAcceleration");
		applyAccelerationProfile.addActionListener(this);
		accelerationProfile.add(accelerationLabel);
		accelerationProfile.add(max2);
		accelerationProfile.add(maxAccelerationTF);
		accelerationProfile.add(min2);
		accelerationProfile.add(minAccelerationTF);
		accelerationProfile.add(prob2);
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

	public void addDesitnation(Destination d)
	{
		if(!destinations.contains(d))
		{
			destinations.add(d);
			destinationBox.addItem(d);
			velocityProbTF.setText(String.valueOf(d.getVelocityProbability()));
			maxVelocityTF.setText(String.valueOf(d.getMaxVehicleVelocity()));
			minVelocityTF.setText(String.valueOf(d.getMinVehicleVelocity()));

			accelerationProbTF.setText(String.valueOf(d.getAccelerationProbability()));
			maxAccelerationTF.setText(String.valueOf(d.getMaxVehicleAcceleration()));
			minAccelerationTF.setText(String.valueOf(d.getMinVehicleAcceleration()));
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "ApplyAcceleration")
		{
			//AM > Find the selected destination
			Destination d = (Destination) destinationBox.getSelectedItem();

			d.setMaxVehicleAcceleration(Integer.parseInt(maxAccelerationTF.getText()));
			d.setMinVehicleAcceleration(Integer.parseInt(minAccelerationTF.getText()));
			d.setAccelerationProbability(Double.parseDouble(accelerationProbTF.getText()));

		}
		if(e.getActionCommand() == "ApplyVelocity")
		{
			//AM > Find the selected destination
			Destination d = (Destination) destinationBox.getSelectedItem();

			d.setMaxVehicleVelocity(Integer.parseInt(maxVelocityTF.getText()));
			d.setMinVehicleVelocity(Integer.parseInt(minVelocityTF.getText()));
			d.setVelocityProbability(Double.parseDouble(velocityProbTF.getText()));
		}
	}

	@Override
	public void itemStateChanged(ItemEvent event) {
		if (event.getStateChange() == ItemEvent.SELECTED) {
			Destination d = (Destination)event.getItem();
			velocityProbTF.setText(String.valueOf(d.getVelocityProbability()));
			maxVelocityTF.setText(String.valueOf(d.getMaxVehicleVelocity()));
			minVelocityTF.setText(String.valueOf(d.getMinVehicleVelocity()));

			accelerationProbTF.setText(String.valueOf(d.getAccelerationProbability()));
			maxAccelerationTF.setText(String.valueOf(d.getMaxVehicleAcceleration()));
			minAccelerationTF.setText(String.valueOf(d.getMinVehicleAcceleration()));
		}

	}
	
	class MaxMinVerifier extends InputVerifier {
	    @Override
	    public boolean verify(JComponent input) {
	        String text = ((JTextField) input).getText();
	        try {
	            int value = Integer.parseInt(text);
	            if(value >= 0)
	             return true;
	            else
	            	return false;
	        } catch (NumberFormatException e) {
	            return false;
	        }
	    }
	}
	    
	    class ProbVerifier extends InputVerifier {
		    @Override
		    public boolean verify(JComponent input) {
		        String text = ((JTextField) input).getText();
		        try {
		            double value = Double.parseDouble(text);
		            if(value >= 0 && value <= 1)
		             return true;
		            else
		            	return false;
		        } catch (NumberFormatException e) {
		            return false;
		        }
		    }
	    }
	
}
