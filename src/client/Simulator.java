package client;
import java.awt.*;
import javax.swing.*;
import java.awt.Graphics2D;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import java.awt.Color;

import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;


import core.endpoints.EndPointException;
import core.network.Road;
import core.vehicle.Car;
import core.vehicle.Vehicle;

import java.awt.ScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.awt.geom.Line2D;


public class Simulator extends JFrame
{
	   

	private static final long serialVersionUID = 1L;
	private JPanel mapPanel;
	private JPanel controlPanel;
	
	
	public Simulator() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(2,1));
		setBounds(100, 100, 800, 600);
		mapPanel = new MapPanel();
		controlPanel = new ControlPanel();
		//setContentPane(mapPanel);
		mapPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		add(mapPanel);
		//mapPanel.setLayout(null);
		add(controlPanel);
		
		
		// Creates a menubar for a JFrame
        JMenuBar menuBar = new JMenuBar();
        
        // Add the menubar to the frame
        setJMenuBar(menuBar);
        
     // Define and add two drop down menu to the menubar
        JMenu mapsMenu = new JMenu("Maps");
        
        menuBar.add(mapsMenu);
        
        JMenuItem network1 = new JMenuItem("Network 1");
        mapsMenu.add(network1);
        
        JMenuItem network2 = new JMenuItem("Network 2");
        mapsMenu.add(network2);
        
        JMenuItem network3 = new JMenuItem("Network 3");
        mapsMenu.add(network3);
		
		
       
        setTitle("Traffic Simulator");
        setVisible(true);
	}
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Simulator frame = new Simulator();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	
	
    
}
