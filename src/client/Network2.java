package client;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import core.endpoints.EndPointException;
import core.network.Road;
import core.vehicle.Bus;
import core.vehicle.Car;
import core.vehicle.Vehicle;

public class Network2 extends JPanel{

	private static final long serialVersionUID = 7045702594780848905L;
	
	private Timer tm;
	private ActionListener actionListener;
	private Road r1;
	private List<Vehicle> vehicleList;
	private int length;
	private int carWidth = 20;
	private int vehicleHeight = 10;
	private int busWidth = 30;   

	public Network2() {

		super();
		length = 20;
		r1 = new Road(2,length);

		vehicleList = new ArrayList<Vehicle>();
		

		//AM > Every time the clock ticks move cars
		actionListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					r1.moveTraffic();
					Vehicle v = new Car();
					vehicleList.add(v);
					r1.addVehicle(v);
					repaint();
					
					Vehicle b = new Bus();
					vehicleList.add(b);
					r1.addVehicle(b);
					repaint();
				} catch (EndPointException e) {
					e.printStackTrace();
				}
			}
		};
		
		tm = new Timer(1000, actionListener);

	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		int panelWidth = (int) getSize().getWidth();
		int panelHeight = (int) getSize().getHeight();
		int roadHeight = 80;
		int destinationWidth = 20;
		
		//AM > Draw a straight road
		g.setColor(Color.GREEN);
		int roadStartX = 0 + destinationWidth;
		int roadStartY = panelHeight/2 - roadHeight/2;
		int roadWidth = panelWidth - destinationWidth*2;
		int roadEndX = roadStartX+roadWidth;
		int roadEndY = roadStartY;
 		g.fillRect(roadStartX, roadStartY, roadWidth, roadHeight);
 		
 		//AM > Draw a vertical road
 		g.setColor(Color.GREEN);
		int roadStartY_vertical = 0;
		int roadStartX_vertical = panelWidth/2-roadHeight/2;
		int roadWidth_vertical = roadHeight;
		int roadHeight_vertical=roadWidth;
		int roadEndY_vertical = roadStartX+roadWidth;
		int roadEndX_vertical = roadStartY;
 		g.fillRect(roadStartX_vertical, roadStartY_vertical, roadWidth_vertical, roadHeight_vertical);
		
		//AM > Draw destination A
 		int textOffsetX = 5;
		int textOffsetY = 5;
		g.setColor(Color.GRAY);
		g.fillRect(0,roadStartY, destinationWidth,roadHeight);
		g.setColor(Color.BLACK);
		g.drawString("A", destinationWidth/2 - textOffsetX, roadStartY + roadHeight/2 + textOffsetY);
		
		//AM > Draw destination B
		g.setColor(Color.GRAY);
		g.fillRect(roadEndX, roadEndY, destinationWidth, roadHeight);
		g.setColor(Color.BLACK);
		g.drawString("B", roadEndX +destinationWidth/2 - textOffsetX, roadStartY + roadHeight/2 + textOffsetY);
		
		//AM > Draw destination C
		g.setColor(Color.GRAY);
		g.fillRect(roadStartX_vertical,0,roadHeight ,destinationWidth);
		g.setColor(Color.BLACK);
		g.drawString("C",roadStartX_vertical + roadHeight/2, destinationWidth/2 + textOffsetX);
		
		
		//AM > Draw destination D
		g.setColor(Color.GRAY);
		g.fillRect(roadEndX_vertical, roadEndY_vertical, roadHeight,destinationWidth);
		g.setColor(Color.BLACK);
		g.drawString("D", roadEndX_vertical +destinationWidth/2 - textOffsetX, roadStartY_vertical + roadHeight_vertical/2 + textOffsetY);
		
		//AM > Draw road divider
		g.setColor(Color.WHITE);
		g.drawLine(roadStartX , panelHeight/2, roadEndX -1, panelHeight/2);
		
		//AM > Draw lane separators
		Graphics2D g2d = (Graphics2D) g.create();
		Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        g2d.setStroke(dashed);
        g2d.setColor(Color.WHITE);
        int upperLaneDividerY = panelHeight/2 - roadHeight/4;
        int lowerLaneDividerY = panelHeight/2 + roadHeight/4;
        g2d.drawLine(roadStartX, upperLaneDividerY, roadEndX -1, upperLaneDividerY);
        g2d.drawLine(roadStartX, lowerLaneDividerY, roadEndX -1, lowerLaneDividerY);
        
        //AM > Draw a center line between lane boundaries
        //g.setColor(Color.RED);
        //g.drawLine(roadStartX, upperLaneDividerY - roadHeight/8, roadEndX, upperLaneDividerY - roadHeight/8);
        //g.drawLine(roadStartX, panelHeight/2 - roadHeight/8, roadEndX, panelHeight/2 - roadHeight/8);
        
        //AM > Draw car on the network
        int blockWidth = (int)roadWidth/length;
        
        //For each vehicle on the road get its co-ordinates
        for(Vehicle v : vehicleList)
        {
        	//Random r = new Random();
        	//g.setColor(new Color(r.nextFloat(), r.nextFloat(), r.nextFloat()));
        	if(v instanceof Car){
        		g.setColor(Color.RED);
        	}
        	else if(v instanceof Bus){
        		g.setColor(Color.YELLOW);
        	}
        	//For each vehicle calculate its X and Y co-ordinates
            int carX = 0;
            int carY = 0;
            if(r1.getVehicleNodeIndex(v) != -1)
            {
            	carX = roadStartX + blockWidth*r1.getVehicleNodeIndex(v);
            	if(r1.getVehicleLaneIndex(v) == 0)
            		carY =  upperLaneDividerY - roadHeight/8 - vehicleHeight/2;
            	else
            		carY =  (panelHeight/2 - roadHeight/8) - vehicleHeight/2;
            	carWidth =  (int) (blockWidth*0.5);
            	busWidth = (int)(blockWidth*0.75);
            	if(v instanceof Car){
            		g.fillRect(carX,carY,carWidth, vehicleHeight);
            	}
            	else if(v instanceof Bus){
            		g.fillRect(carX,carY,busWidth, vehicleHeight);
            	}
            }   
        }
        tm.start();
	  }
	
}
