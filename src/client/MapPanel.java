package client;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;

import javax.swing.JPanel;
import javax.swing.Timer;

public class MapPanel extends JPanel {

	private static final long serialVersionUID = -5133592834815202789L;
	
	int x = 40, velX = 1;
	int y = 65, velY = 1; 

	public MapPanel() {
		super();
	}

	public MapPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
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
        
        ActionListener carAnimation= new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (x < 40 || x > 590){
					velX = -velX;
				}
        		
        		x = x + velX;
        		repaint();
        		
        		if (y < 65 || y > 445){
        			velY = -velY;
        		}
        		
        		y = y + velY;
        		repaint();
				
			}
        	
        };
        
        Timer tm = new Timer(500, carAnimation);
    	
    	
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
        

	}
	
	

}
