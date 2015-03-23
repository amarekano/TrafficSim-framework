package client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Map2 extends JPanel{

	private static final long serialVersionUID = 7045702594780848905L;
	
	int x = 0, velX = 1;
	
	int y = 65, velY = 1;   

	public Map2() {
		// TODO Auto-generated constructor stub
		Junction jun=new Junction();
		jun.setVisible(true);
        add(jun);
	}
	
	public void paint(Graphics g) {
	    g.fillRect (0, this.getHeight()/2, this.getWidth(), 100);
	    g.fillRect(this.getWidth()/2, 0,100, this.getHeight());
	    
	    
	    int middle_of_horizontal_road=this.getHeight()/2+50;
	    int middle_of_vertical_road=this.getWidth()/2+50;
	    
	    g.setColor(Color.RED);
	    g.drawLine(0, middle_of_horizontal_road, this.getWidth(), middle_of_horizontal_road);
	    
	    g.drawLine(middle_of_vertical_road, 0, middle_of_vertical_road, this.getHeight());
	    
	    int y_horizontal_car=middle_of_horizontal_road-25;
	    
	    
	    g.setColor(Color.green);
        g.fillRect (x, y_horizontal_car, 20, 10);
       
        
        g.setColor(Color.orange);
        g.fillRect (248, y, 10, 20);
        
        g.fillOval(this.getWidth()/2, this.getHeight()/2, 100, 100);
        
        
        //jun.repaint();
        //Image im=jun.createImage(jun.getWidth(), jun.getHeight());
        //jun.getGraphics();
        
        //g.drawImage(im, 0,0,this.getWidth(),this.getHeight(),this);
	    
	 }
	
	public static void main(String[] a) {
	    JFrame window = new JFrame();
	    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    window.setBounds(30, 30, 300, 300);
	    window.getContentPane().add(new Map2());
	    window.setVisible(true);
	  }

}
