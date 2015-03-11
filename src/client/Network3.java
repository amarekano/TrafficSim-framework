package client;
import java.awt.*;

import javax.swing.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.EventQueue;

import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.geom.Line2D;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.Timer;


public class Network3 extends JFrame implements ActionListener {
	
	Timer tm = new Timer(10, this);
	int x = 40, velX = 1;
	
	int y = 65, velY = 1;   

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Network3 frame = new Network3();
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
	public Network3() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Network 3"}));
		comboBox.setSelectedIndex(0);
		comboBox.setBounds(88, 0, 146, 27);
		contentPane.add(comboBox);
       
        setTitle("Traffic Simulator");
        setVisible(true);
	}
	
	public void paint(Graphics g) {
	    super.paint(g);
	   
	    g.setColor(Color.gray);
        g.fillRect (200, 60, 60, 530);
        g.fillRect (30, 220,650, 60);
        g.fillRect(420, 60, 60, 530);
        g.fillRect(30, 420, 650, 60);
        
        Graphics2D g2d = (Graphics2D) g;

        float[] dash1 = {2f, 0f, 2f};
        
        BasicStroke bs1 = new BasicStroke(1, BasicStroke.CAP_BUTT, 
        	    BasicStroke.JOIN_ROUND, 1.0f, dash1, 2f );
        
        g.setColor(Color.lightGray);
        
        g2d.setStroke(bs1);
        g2d.drawLine(30, 235, 680, 235);
        g2d.drawLine(30, 265, 680, 265);
        g2d.drawLine(245, 60, 245, 588);
        g2d.drawLine(215, 60, 215, 588);
        g2d.drawLine(30, 435, 680, 435);
        g2d.drawLine(30, 465, 680, 465);
        g2d.drawLine(435, 60, 435, 588);
        g2d.drawLine(465, 60, 465, 588);
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g2.draw(new Line2D.Float(230, 60, 230, 588));
        g2.draw(new Line2D.Float(450, 60, 450, 588));
        g2.draw(new Line2D.Float(30, 250, 678, 250));
        g2.draw(new Line2D.Float(30, 450, 678, 450));
	    
        
        g.setColor(Color.green);
        g.fillRect (x, 252, 20, 10);
       
        
        g.setColor(Color.orange);
        g.fillRect (248, y, 10, 20);
        
        g.setColor(Color.orange);
        g.fillRect (453, y, 10, 20);
        
        g.setColor(Color.CYAN);
        g.fillRect(x, 223, 20, 10);
        
        g.setColor(Color.blue);
        g.fillRect(x, 438, 20, 10);
        tm.start();
	}

	public void actionPerformed1(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (x < 40 || x > 640)
			velX = -velX;
			
		x = x + velX;
		repaint();
		
		if (y < 65 || y > 550)
			velY = -velY;
			
		y = y + velY;
		repaint();
	}

	

}
