package client;

import java.awt.Color;
import java.awt.Dimension;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
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
		//setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
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
	    
	    JPanel interval_panel = new JPanel(new GridLayout(2,1));
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
