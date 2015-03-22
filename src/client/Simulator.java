package client;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;



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
