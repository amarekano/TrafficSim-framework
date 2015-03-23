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
	private JPanel mapPanel;
	private JPanel controlPanel;
	
	JPanel cards;
	final static String MAP1PANEL = "MAP1PANEL";
	final static String MAP2PANEL = "MAP2PANEL";
	
	Map1 card1 = new Map1();
	
	Map2 card2 = new Map2();
	
	
	public Simulator() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(2,1));
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width =(int) screenSize.getWidth();
		int height =(int) screenSize.getHeight();
		
		setBounds(80, 20, (int)(width*0.5),(int) (height*0.5));
		mapPanel = new Map1();
		
		//mapPanel.setBounds(100, 100, this.getWidth(), (int)(this.getHeight()*0.6));
		mapPanel.setPreferredSize(new Dimension(this.getWidth(), (int)(this.getHeight()*0.8)));
		controlPanel = new ControlPanel();
		//setContentPane(mapPanel);
		mapPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		//Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(card1, MAP1PANEL);
        cards.add(card2, MAP2PANEL);
        cards.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		add(cards);
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
        network1.addActionListener(this);
        
        JMenuItem network2 = new JMenuItem("Network 2");
        mapsMenu.add(network2);
        network2.addActionListener(this);
        
        JMenuItem network3 = new JMenuItem("Network 3");
        mapsMenu.add(network3);
        network3.addActionListener(this);
        
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
		CardLayout cl = (CardLayout)(cards.getLayout());
		
		if(e.getActionCommand()=="Network 1"){
			cl.show(cards,MAP1PANEL);
		}
		else if(e.getActionCommand()=="Network 2"){
			cl.show(cards, MAP2PANEL);
		}
		else if(e.getActionCommand()=="Network 3"){
			
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
