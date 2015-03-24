package client;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import service.ReportGenerator;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;



public class Simulator extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JPanel controlPanel;
	private JPanel mapPanel;
	
	private final String MAP1PANEL = "MAP1PANEL";
	private final String MAP2PANEL = "MAP2PANEL";
	
	private Network network1 = new Network1();
	private Network network2 = new Network2();
	
	
	public Simulator() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(2,1));
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width =(int) screenSize.getWidth();
		int height =(int) screenSize.getHeight();
		setBounds(20, 20, (int)(width*0.6),(int)(height*0.75));
		
	
		//controlPanel = new ControlPanel();
		//Create the panel that contains the "cards".
        mapPanel = new JPanel(new CardLayout());
        mapPanel.add(network2.getView(), MAP2PANEL);
        mapPanel.add(network1.getView(), MAP1PANEL);
        mapPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
        //Create the panel that contains 'cards' for map controls
        controlPanel = new JPanel(new CardLayout());
        controlPanel.add(network2.getControls(), MAP2PANEL);
        controlPanel.add(network2.getControls(), MAP1PANEL);
        
		add(mapPanel);
		add(controlPanel);
		
		// Creates a menubar for a JFrame
        JMenuBar menuBar = new JMenuBar();
        
        // Add the menubar to the frame
        setJMenuBar(menuBar);
        
        //Define and add two drop down menu to the menubar
        JMenu mapsMenu = new JMenu("Maps");
        
        menuBar.add(mapsMenu);
        
        JMenuItem network1 = new JMenuItem("Network 1");
        mapsMenu.add(network1);
        network1.addActionListener(this);
        
        JMenuItem network2 = new JMenuItem("Network 2");
        mapsMenu.add(network2);
        network2.addActionListener(this);
        
        
        JMenu reportMenu = new JMenu("Report");
        
        menuBar.add(reportMenu);
        
        JMenuItem save_report = new JMenuItem("Save report");
        reportMenu.add(save_report);
        save_report.addActionListener(this);
		
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


	@Override
	public void actionPerformed(ActionEvent e) {
		CardLayout view_cl = (CardLayout)(mapPanel.getLayout());
		CardLayout control_cl = (CardLayout)(controlPanel.getLayout());
		if(e.getActionCommand()=="Network 1"){
			view_cl.show(mapPanel,MAP1PANEL);
			//AM > Set controls for network1
			control_cl.show(controlPanel, MAP1PANEL);
		}
		else if(e.getActionCommand()=="Network 2"){
			view_cl.show(mapPanel, MAP2PANEL);
			//AM > Set controls for network2
			control_cl.show(controlPanel, MAP2PANEL);
		}
		else if(e.getActionCommand()=="Save report"){
			//Create a file chooser
			final JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(this);

	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            ReportGenerator myreport= new ReportGenerator();
				myreport.saveReport(file.getAbsolutePath()+".txt");
	        } else {
	        	System.out.println("Open command cancelled by user.");
	        }
			
			
		}
		
	}

	
	
	
    
}
