package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;

import service.SimulationClock;

public class ClockPanel extends JPanel {

	private static final long serialVersionUID = -2166709692460369850L;
	SimulationClock simClock;
	JLabel clockLabel;
	Timer tm = new Timer(0,null);
	ActionListener clockLabelListener;
	
	public ClockPanel() {
		super();
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		clockLabel = new JLabel("--", SwingConstants.CENTER);
		clockLabel.setPreferredSize(new Dimension(75,50));
		clockLabel.setBorder(BorderFactory.createTitledBorder("Clock"));
		add(clockLabel);
		
		JButton start =new JButton("Start");
		JButton stop =new JButton("Stop");
		JButton save = new JButton("Save Report");
		
		add(start);
		add(stop);
		add(save);
		
		clockLabelListener = new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				clockLabel.setText(""+simClock.getTime());
				repaint();
			}
		};
		
		ActionListener clockButtonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand() == "Start")
				{
					tm.start();
				}
				if(e.getActionCommand() == "Stop")
				{
					tm.stop();
				}
			}
		};
		
		start.addActionListener(clockButtonListener);
		stop.addActionListener(clockButtonListener);
	}
	
	public void setClock(Timer tm, SimulationClock simClock)
	{
		this.tm = tm;
		this.tm.addActionListener(clockLabelListener);
		this.simClock = simClock;
	}

}
