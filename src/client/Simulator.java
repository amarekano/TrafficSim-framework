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


public class Simulator extends JFrame implements ActionListener 
{
	Timer tm = new Timer(10, this);
	int x = 40, velX = 1;
	
	int y = 65, velY = 1;    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private JComboBox comboBox;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Simulator frame = new Simulator();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	public void comboselect(){
		int d =comboBox.getSelectedIndex();
		if (d==1){
			Network2 s = new Network2();
			s.setVisible(true);
		}
		
		if (d==2){
			Network3 l =new Network3();
			l.setVisible(true);
		}
	}
	
	
	public Simulator() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Network 1", "Network 2", "Network 3"}));
		comboBox.setSelectedIndex(0);
		comboBox.setBounds(88, 0, 146, 27);
		contentPane.add(comboBox);
		
		JButton btnMaps = new JButton("Maps");
		btnMaps.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnMaps.setBounds(227, -1, 117, 29);
		contentPane.add(btnMaps);
		
		JPanel panel = new JPanel();
		panel.setBackground(UIManager.getColor("Button.background"));
		panel.setBorder(new LineBorder(new Color(128, 128, 128)));
		panel.setBounds(0, 444, 800, 134);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Traffic Light Interval");
		lblNewLabel.setBounds(45, 25, 144, 16);
		panel.add(lblNewLabel);
		
		JLabel lblTimeOfThe = new JLabel("Time of the Day");
		lblTimeOfThe.setBounds(45, 87, 100, 16);
		lblTimeOfThe.setVerticalAlignment(SwingConstants.BOTTOM);
		panel.add(lblTimeOfThe);
		
		JSlider slider = new JSlider();
		slider.setBounds(180, 19, 190, 29);
		panel.add(slider);
		
		textField = new JTextField();
		textField.setBounds(165, 81, 134, 28);
		panel.add(textField);
		textField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(534, 15, 231, 109);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		
		/*
		JLabel lblCarh = new JLabel("");
		lblCarh.setBounds(40, 233, 43, 42);
		lblCarh.setIcon(new ImageIcon("images/car2H.png"));
		contentPane.add(lblCarh);
		tm.start();
		
		JLabel lblLighth = new JLabel("");
		lblLighth.setBounds(233, 194, 20, 34);
		lblLighth.setIcon(new ImageIcon("images/lightg.png"));
		contentPane.add(lblLighth);
		*/
		
		
		JButton btnClock = new JButton("Clock");
		btnClock.setBounds(650, 6, 117, 29);
		contentPane.add(btnClock);
		
		ScrollPane scrollPane_1 = new ScrollPane();
		scrollPane_1.setBounds(660, 41, 100, 100);
		contentPane.add(scrollPane_1);
		
		JLabel lblA = new JLabel("A");
		lblA.setBounds(17, 233, 33, 16);
		contentPane.add(lblA);
		
		JLabel lblB = new JLabel("B");
		lblB.setBounds(631, 233, 33, 16);
		contentPane.add(lblB);
		
       
        setTitle("Traffic Simulator");
        setVisible(true);
	}
	
	public void paint(Graphics g) {
	    super.paint(g);
	    g.setColor(Color.lightGray);
	    g.drawLine(230, 60, 230, 450);
	    g.drawLine(280, 60, 280, 450);
	    g.drawLine(30, 245, 455, 245);
	    g.drawLine(30, 295, 455, 295);
	   
	    /**
	    g.setColor(Color.darkGray);
	    g.drawRect(230, 60, 52, 406); 
	    */
	    
	    Graphics2D g2d = (Graphics2D) g;

        float[] dash1 = {2f, 0f, 2f};
        
        BasicStroke bs1 = new BasicStroke(1, BasicStroke.CAP_BUTT, 
        	    BasicStroke.JOIN_ROUND, 1.0f, dash1, 2f );
        
       
        
        g.setColor(Color.gray);
        g.fillRect (230, 60, 52, 407);
        g.fillRect (30, 245,595, 55);
        
        
        g.setColor(Color.green);
        g.fillRect (x, 258, 20, 10);
        
        tm.start();
        
        g.setColor(Color.orange);
        g.fillRect (243, y, 10, 20);
        
        tm.start();
        
        /*// in Color.RED
        Color myColor = new Color(123, 111, 222);
        */
        g.setColor(Color.lightGray);
        
        g2d.setStroke(bs1);
        g2d.drawLine(30, 255, 625, 255);
        g2d.drawLine(270, 60, 270, 460);
        g2d.drawLine(240, 60, 240, 460);
        g2d.drawLine(30, 285, 625, 285);
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g2.draw(new Line2D.Float(255, 60, 255, 460));
        g2.draw(new Line2D.Float(30, 270, 625, 270));
        
        
        
	    Road r1 = new Road(1,10);
	    Vehicle v1 = new Car();
	    
	    r1.addVehicle(v1);
	    try {
			r1.moveTraffic();
		} catch (EndPointException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    r1.getVehicleLaneIndex(v1);
	    r1.getVehicleNodeIndex(v1);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (x < 40 || x > 590)
			velX = -velX;
			
		x = x + velX;
		repaint();
		
		if (y < 65 || y > 445)
			velY = -velY;
			
		y = y + velY;
		repaint();
	}

	
	
	
    
}
