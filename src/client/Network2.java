package client;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.Image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import client.Renderer;
import service.DemandMatrix;
import service.DemandMatrixException;
import service.ReportGenerator;
import service.RoadNetwork;
import service.SimulationClock;
import service.TrafficSignalScheduler;
import core.endpoints.Destination;
import core.network.Road;
import core.network.interfaces.InterfaceException;
import core.network.junction.Junction;
import core.network.junction.JunctionRouter;
import core.network.junction.Junction.JUNCTION;
import core.vehicle.Bus;
import core.vehicle.Car;
import core.vehicle.Vehicle;

public class Network2 extends Network
{

	int counter;
	Timer timer;
	
	private JPanel view;
	private ControlPanel controls;
	
	private Destination A;
	private Destination B;
	private Destination C;
	private Destination D;
	private SimulationClock clock;
	private Junction junc;
	
	Road ra_j;
	Road rb_j;
	Road rc_j;
	Road rd_j;
	
	Road rj_a;
	Road rj_b;
	Road rj_c;
	Road rj_d;
	
	RoadNetwork roadNetwork; 
	
	JunctionRouter juncRouter;
	TrafficSignalScheduler scheduler;
	DemandMatrix dm_cars;
	DemandMatrix dm_buses;
	
	private List<Vehicle> vehicleList;
	private int hcarWidth = 10;
	private int hvehicleHeight = 10;
	private int hbusWidth = 15;
	int number_of_lanes = 2;
	int lane_length = 10;
	
	private int vcarHeight =5;
	private int vbusHeight = 0;
	private int vvehicleWidth=10;
	
	public Network2() {
		super();
		ActionListener actionListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				view.repaint();
				clock.incrementClock();
			}
		};
		
		timer = new Timer(1000, actionListener);
	
		clock = new SimulationClock();
		
		counter=0;
		
		//AM > Setup the destinations
		A = new Destination("A");
		B = new Destination("B");
		C = new Destination("C");
		D = new Destination("D");
		
		A.setClock(clock);
		B.setClock(clock);
		C.setClock(clock);
		D.setClock(clock);
		
		dm_cars = new DemandMatrix();
		dm_cars.addDestination(A);
		dm_cars.addDestination(B);
		dm_cars.addDestination(C);
		dm_cars.addDestination(D);
		try {
			dm_cars.initializeMatrix();
			dm_cars.setVehicleType(Car.class);
			dm_cars.setDemand(A, B, 0.2);
			
			dm_cars.setDemand(A, C, 1);
			
			dm_cars.setDemand(A, D, 1);
			
		} catch (DemandMatrixException e) {
			e.printStackTrace();
		}
		
		dm_buses = new DemandMatrix();
		dm_buses.addDestination(A);
		dm_buses.addDestination(B);
		dm_buses.addDestination(C);
		dm_buses.addDestination(D);
		try {
			dm_buses.initializeMatrix();
			dm_buses.setVehicleType(Bus.class);
			dm_buses.setDemand(A, B, 1.0);
			
			dm_buses.setDemand(B, A, 0.9);
			
			dm_buses.setDemand(C, D, 0.1);
			dm_buses.setDemand(D, C, 0.6);
			
		} catch (DemandMatrixException e) {
			e.printStackTrace();
		}
		
		junc = new Junction();
		roadNetwork = new RoadNetwork();
		
		try {
			ra_j = new Road(number_of_lanes, lane_length);
			ra_j.setSource(A);
			ra_j.setSink(junc,JUNCTION.WEST);
			roadNetwork.addRoad(ra_j);
			
			rb_j = new Road(number_of_lanes, lane_length);
			rb_j.setSource(B);
			rb_j.setSink(junc, JUNCTION.EAST);
			roadNetwork.addRoad(rb_j);
			
			rc_j = new Road(number_of_lanes, lane_length);
			rc_j.setSource(C);
			rc_j.setSink(junc, JUNCTION.NORTH);
			roadNetwork.addRoad(rc_j);
			
			rd_j = new Road(number_of_lanes, lane_length);
			rd_j.setSource(D);
			rd_j.setSink(junc, JUNCTION.SOUTH);
			roadNetwork.addRoad(rd_j);
			
			rj_a = new Road(number_of_lanes, lane_length);
			rj_a.setSink(A);
			rj_a.setSource(junc,JUNCTION.WEST);
			roadNetwork.addRoad(rj_a);
			
			rj_b = new Road(number_of_lanes, lane_length);
			rj_b.setSink(B);
			rj_b.setSource(junc, JUNCTION.EAST);
			roadNetwork.addRoad(rj_b);
			
			rj_c = new Road(number_of_lanes, lane_length);
			rj_c.setSink(C);
			rj_c.setSource(junc, JUNCTION.NORTH);
			roadNetwork.addRoad(rj_c);
			
			rj_d = new Road(number_of_lanes, lane_length);
			rj_d.setSink(D);
			rj_d.setSource(junc, JUNCTION.SOUTH);
			roadNetwork.addRoad(rj_d);
			
			juncRouter = new JunctionRouter();
			juncRouter.add(A, junc.getInterface(JUNCTION.WEST));
			juncRouter.add(C, junc.getInterface(JUNCTION.NORTH));
			juncRouter.add(B, junc.getInterface(JUNCTION.EAST));
			juncRouter.add(D,  junc.getInterface(JUNCTION.SOUTH));
			junc.setRoutingTable(juncRouter);
			
			junc.setSignalController();
			scheduler = new TrafficSignalScheduler();
			scheduler.setSignalInterval(10);
			scheduler.addSignalController(junc.getSignalController());
			
		} catch (InterfaceException e) {
			e.printStackTrace();
		}
		
		
		clock.addObserver(scheduler);
		clock.addObserver(roadNetwork);
		clock.addObserver(dm_cars);
		clock.addObserver(dm_buses);
		
		controls = new ControlPanel(timer,clock);
		controls.setDemandMatrixCars(dm_cars);
		controls.setDemandMatrixBuses(dm_buses);
		controls.addTrafficScheduler(scheduler);
		controls.addDestinations(A);
		controls.addDestinations(B);
		controls.addDestinations(C);
		controls.addDestinations(D);
		
		
		ReportGenerator generator = new ReportGenerator();
		generator.addDestination(A);
		generator.addDestination(B);
		generator.addDestination(C);
		generator.addDestination(D);
		controls.setReportGenerator(generator);
		
		vehicleList = new ArrayList<Vehicle>();
		
		
		view = new JPanel()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);

				int panelWidth = (int) getSize().getWidth();
				int panelHeight = (int) getSize().getHeight();
				int hroadHeight = 60;
				int hdestinationWidth = 20;
				int vdestinationHeight = 20;
				int vdestinationWidth = 60;
				
				//AM > Draw a horizontal road from A to junction
				g.setColor(Color.BLACK);
				int hra_jStartX = 0 + hdestinationWidth;
				int hra_jStartY = panelHeight/2 - hroadHeight/2;
				int hra_jWidth = panelWidth/2 - 2*hdestinationWidth;
				int hra_jEndX = hra_jStartX+hra_jWidth;
				int hra_jEndY = hra_jStartY;
				Renderer.renderRoad(g, "A", "", hra_jStartX, hra_jStartY, hra_jWidth, hroadHeight, Renderer.Direction.EAST);
				
		 		//AM > Draw a vertical road form C to junction
		 		g.setColor(Color.BLACK);
				int vrc_jStartY = 0 + vdestinationHeight;
				int vrc_jStartX = panelWidth/2-hroadHeight/2;
				int vrc_jWidth = vdestinationWidth;
				int vrc_jHeight= panelHeight/2 - hroadHeight/2 - vdestinationHeight;
				int vrc_jEndY = vrc_jStartY + vrc_jHeight;
				int vrc_jEndX = vrc_jStartX;
				Renderer.renderRoad(g, "", "C", vrc_jStartX, vrc_jStartY, vrc_jHeight, hroadHeight, Renderer.Direction.SOUTH);
		 		
		 		//AM > Draw vertical road from Junction to D
		 		g.setColor(Color.BLACK);
				int vrj_dStartY = panelHeight/2 + hroadHeight/2;
				int vrj_dStartX = panelWidth/2-hroadHeight/2;
				int vrj_dWidth = vdestinationWidth;
				int vrj_dHeight= panelHeight/2 - hroadHeight/2 -vdestinationHeight;
				int vrj_dEndY = vrj_dStartY + vrj_dHeight;
				int vrj_dEndX = vrj_dStartX;
				Renderer.renderRoad(g, "", "D", vrj_dStartX, vrj_dStartY, vrj_dHeight, hroadHeight, Renderer.Direction.SOUTH);
		 		
		 		//AM > Draw a horizontal road from junction to B
				g.setColor(Color.BLACK);
				int hrj_bStartX = panelWidth/2 + vrc_jWidth/2;
				int hrj_bStartY = panelHeight/2 - hroadHeight/2;
				int hrj_bWidth = panelWidth/2 - hdestinationWidth - vrc_jWidth/2;
				int hrj_bEndX = hrj_bStartX+hrj_bWidth;
				int hrj_bEndY = hrj_bStartY;
				Renderer.renderRoad(g, "", "B", hrj_bStartX, hrj_bStartY, hrj_bWidth, hroadHeight, Renderer.Direction.EAST);
		 		
				//AM > Draw destination A
		 		int textOffsetX = 5;
				int textOffsetY = 5;
				g.setColor(Color.GRAY);
				g.fillRect(0,hra_jStartY, hdestinationWidth,hroadHeight);
				g.setColor(Color.BLACK);
				g.drawString("A", hdestinationWidth/2 - textOffsetX, hra_jStartY + hroadHeight/2 + textOffsetY);
				
				//AM > Draw destination B
				g.setColor(Color.GRAY);
				g.fillRect(hrj_bEndX, hrj_bEndY, hdestinationWidth, hroadHeight);
				g.setColor(Color.BLACK);
				g.drawString("B", hrj_bEndX +hdestinationWidth/2 - textOffsetX, hrj_bStartY + hroadHeight/2 + textOffsetY);
				
				//AM > Draw destination C
				g.setColor(Color.GRAY);
				g.fillRect(vrc_jStartX,0,vrc_jWidth ,vdestinationHeight);
				g.setColor(Color.BLACK);
				g.drawString("C",vrc_jStartX + hroadHeight/2 - textOffsetX, hdestinationWidth/2 + textOffsetY);
				
				
				//AM > Draw destination D
				g.setColor(Color.GRAY);
				g.fillRect(vrj_dEndX, vrj_dEndY, vrj_dWidth,vdestinationHeight);
				g.setColor(Color.BLACK);
				textOffsetX = 5;
				textOffsetY = 5;
				g.drawString("D", vrj_dEndX +vrj_dWidth/2 - textOffsetX, vrj_dEndY + vdestinationHeight/2 + textOffsetY);
			        int upperLaneDividerY = panelHeight/2 - hroadHeight/4;
			        int lowerLaneDividerY = panelHeight/2 + hroadHeight/4;
			        int leftLaneDividerX = panelWidth/2 - vrc_jWidth/4;
			        int rightLaneDividerX = panelWidth/2 + vrc_jWidth/4;
			        int blockWidth= (int)(hra_jWidth/lane_length);
			        vehicleList = ra_j.getVehiclesOnRoad();
		        
		        //AM > Draw junction box
		        Image img = new ImageIcon(getClass().getResource("res/cycle"+scheduler.getCycle()+".png")).getImage();
		        g.drawImage(img,panelWidth/2 - vrc_jWidth/2, panelHeight/2 - hroadHeight/2, vrc_jWidth, hroadHeight, this);
		      
		        //AM > Debug: Draw block width
		        //g.setColor(Color.CYAN);
		        //g.drawRect(hra_jStartX, hra_jStartY, blockWidth, hra_jWidth/16);
		        
		        //AM > Draw vehicles going from A to Junction
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
		            if(ra_j.getVehicleNodeIndex(v) != -1)
		            {
		            	carX = hra_jStartX + blockWidth*ra_j.getVehicleNodeIndex(v);
		            	if(ra_j.getVehicleLaneIndex(v) == 0)
		            		carY =  upperLaneDividerY - hroadHeight/8 - hvehicleHeight/2;
		            	else
		            		carY =  (panelHeight/2 - hroadHeight/8) - hvehicleHeight/2;
		            	hcarWidth =  (int) (blockWidth*0.25);
		            	hbusWidth = (int)(blockWidth*0.41);
		            	if(v instanceof Car){
		            		g.fillRect(carX,carY,hcarWidth, hvehicleHeight);
		            	}
		            	else if(v instanceof Bus){
		            		g.fillRect(carX,carY,hbusWidth, hvehicleHeight);
		            	}
		            }   
		        }
		        
		        //AM > Drawing vehicles between Junction and B
				blockWidth= (int)(hrj_bWidth/lane_length);
		        vehicleList = rj_b.getVehiclesOnRoad();
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
		            if(rj_b.getVehicleNodeIndex(v) != -1)
		            {
		            	carX = hrj_bStartX + blockWidth*rj_b.getVehicleNodeIndex(v);
		            	if(rj_b.getVehicleLaneIndex(v) == 0)
		            		carY =  upperLaneDividerY - hroadHeight/8 - hvehicleHeight/2;
		            	else
		            		carY =  (panelHeight/2 - hroadHeight/8) - hvehicleHeight/2;
		            	hcarWidth =  (int) (blockWidth*0.25);
		            	hbusWidth = (int)(blockWidth*0.41);
		            	if(v instanceof Car){
		            		g.fillRect(carX,carY,hcarWidth, hvehicleHeight);
		            	}
		            	else if(v instanceof Bus){
		            		g.fillRect(carX,carY,hbusWidth, hvehicleHeight);
		            	}
		            }   
		        }
		        
		        //AM > Draw vehicles from B to junction
		        vehicleList = rb_j.getVehiclesOnRoad();
		        for(Vehicle v : vehicleList)
		        {
		        	if(v instanceof Car){
		        		g.setColor(Color.RED);
		        	}
		        	else if(v instanceof Bus){
		        		g.setColor(Color.YELLOW);
		        	}
		        	//For each vehicle calculate its X and Y co-ordinates
		            int carX = 0;
		            int carY = 0;
		            if(rb_j.getVehicleNodeIndex(v) != -1)
		            {	
		            	carX = hrj_bEndX - blockWidth*rb_j.getVehicleNodeIndex(v) - hcarWidth;
		            	if(rb_j.getVehicleLaneIndex(v) == 0)
		            		carY =  upperLaneDividerY - hroadHeight/8 - hvehicleHeight/2+ hroadHeight/2;
		            	else
		            		carY =  (panelHeight/2 - hroadHeight/8) - hvehicleHeight/2+ hroadHeight/2;
		            	hcarWidth =  (int) (blockWidth*0.25);
		            	hbusWidth = (int)(blockWidth*0.41);
		            	if(v instanceof Car){
		            		g.fillRect(carX,carY,hcarWidth, hvehicleHeight);
		            	}
		            	else if(v instanceof Bus){
		            		g.fillRect(carX,carY,hbusWidth, hvehicleHeight);
		            	}
		            }   
		        }
		        
		        //AM > Draw vehicles from Junction to A
		        vehicleList = rj_a.getVehiclesOnRoad();
		        for(Vehicle v : vehicleList)
		        {
		        	if(v instanceof Car){
		        		g.setColor(Color.RED);
		        	}
		        	else if(v instanceof Bus){
		        		g.setColor(Color.YELLOW);
		        	}
		        	//For each vehicle calculate its X and Y co-ordinates
		            int carX = 0;
		            int carY = 0;
		            if(rj_a.getVehicleNodeIndex(v) != -1)
		            {
		            	carX = hra_jEndX - blockWidth*rj_a.getVehicleNodeIndex(v) - hcarWidth;
		            	if(rj_a.getVehicleLaneIndex(v) == 0)
		            		carY =  upperLaneDividerY - hroadHeight/8 - hvehicleHeight/2+ hroadHeight/2;
		            	else
		            		carY =  (panelHeight/2 - hroadHeight/8) - hvehicleHeight/2+ hroadHeight/2;
		            	hcarWidth =  (int) (blockWidth*0.25);
		            	hbusWidth = (int)(blockWidth*0.41);
		            	if(v instanceof Car){
		            		g.fillRect(carX,carY,hcarWidth, hvehicleHeight);
		            	}
		            	else if(v instanceof Bus){
		            		g.fillRect(carX,carY,hbusWidth, hvehicleHeight);
		            	}
		            }
		        }
		        
		        //AM > debug: Draw a center line between lane boundaries
		        //g.setColor(Color.RED);
		        //AM > Lane 0
		        //g.drawLine(rightLaneDividerX - vdestinationWidth/8,vrc_jStartY, rightLaneDividerX - vdestinationWidth/8,vrc_jEndY );
		        //AM > Lane 1
		        //g.drawLine(rightLaneDividerX + vdestinationWidth/8,vrc_jStartY, rightLaneDividerX + vdestinationWidth/8, vrc_jEndY);
		        
		        //AM Draw vehicles from C to Junction
		        int vblockHeight = vrc_jHeight/lane_length;
		        
		        vehicleList = rc_j.getVehiclesOnRoad();
		        for(Vehicle v : vehicleList)
		        {
		        	if(v instanceof Car){
		        		g.setColor(Color.RED);
		        	}
		        	else if(v instanceof Bus){
		        		g.setColor(Color.YELLOW);
		        	}
		        	//For each vehicle calculate its X and Y co-ordinates
		            int carX = 0;
		            int carY = 0;
		            if(rc_j.getVehicleNodeIndex(v) != -1)
		            {
		            	carY = vrc_jStartY + vblockHeight*rc_j.getVehicleNodeIndex(v);
		            	if(rc_j.getVehicleLaneIndex(v) == 0)
		            		carX =  rightLaneDividerX - vdestinationWidth/8 - vvehicleWidth/2;
		            	else
		            		carX =  rightLaneDividerX + vdestinationWidth/8 - vvehicleWidth/2;
		            	vcarHeight =  (int) (vblockHeight*0.5);
		            	vbusHeight = (int)(vblockHeight*0.9);
		            	if(v instanceof Car){
		            		g.fillRect(carX,carY,vvehicleWidth, vcarHeight);
		            	}
		            	else if(v instanceof Bus){
		            		g.fillRect(carX,carY,vvehicleWidth, vbusHeight);
		            	}
		            }
		        }
		        
		        //AM > Draw vehicles from Junction to D
		        vblockHeight = vrj_dHeight/lane_length;
		        vehicleList = rj_d.getVehiclesOnRoad();
		        for(Vehicle v : vehicleList)
		        {
		        	if(v instanceof Car){
		        		g.setColor(Color.RED);
		        	}
		        	else if(v instanceof Bus){
		        		g.setColor(Color.YELLOW);
		        	}
		        	//For each vehicle calculate its X and Y co-ordinates
		            int carX = 0;
		            int carY = 0;
		            if(rj_d.getVehicleNodeIndex(v) != -1)
		            {
		            	carY = vrj_dStartY + vblockHeight*rj_d.getVehicleNodeIndex(v);
		            	if(rj_d.getVehicleLaneIndex(v) == 0)
		            		carX =  rightLaneDividerX - vdestinationWidth/8 - vvehicleWidth/2;
		            	else
		            		carX =  rightLaneDividerX + vdestinationWidth/8 - vvehicleWidth/2;
		            	vcarHeight =  (int) (vblockHeight*0.5);
		            	vbusHeight = (int)(vblockHeight*0.9);
		            	if(v instanceof Car){
		            		g.fillRect(carX,carY,vvehicleWidth, vcarHeight);
		            	}
		            	else if(v instanceof Bus){
		            		g.fillRect(carX,carY,vvehicleWidth, vbusHeight);
		            	}
		            }
		        }
		        
		      //AM > Draw vehicles from D to Junction
		        vblockHeight = vrj_dHeight/lane_length;
		        vehicleList = rd_j.getVehiclesOnRoad();
		        for(Vehicle v : vehicleList)
		        {
		        	if(v instanceof Car){
		        		g.setColor(Color.RED);
		        	}
		        	else if(v instanceof Bus){
		        		g.setColor(Color.YELLOW);
		        	}
		        	//For each vehicle calculate its X and Y co-ordinates
		            int carX = 0;
		            int carY = 0;
		            if(rd_j.getVehicleNodeIndex(v) != -1)
		            {
		            	carY = vrj_dEndY - vblockHeight*rd_j.getVehicleNodeIndex(v) - vcarHeight;
		            	if(rd_j.getVehicleLaneIndex(v) == 0)
		            		carX =  leftLaneDividerX - vdestinationWidth/8 - vvehicleWidth/2;
		            	else
		            		carX =  leftLaneDividerX + vdestinationWidth/8 - vvehicleWidth/2;
		            	vcarHeight =  (int)(vblockHeight*0.5);
		            	vbusHeight = (int)(vblockHeight*0.9);
		            	if(v instanceof Car){
		            		g.fillRect(carX,carY,vvehicleWidth, vcarHeight);
		            	}
		            	else if(v instanceof Bus){
		            		g.fillRect(carX,carY,vvehicleWidth, vbusHeight);
		            	}
		            }
		        }
		        
		      //AM > Draw vehicles from Junction to C
		        vblockHeight = vrj_dHeight/lane_length;
		        vehicleList = rj_c.getVehiclesOnRoad();
		        for(Vehicle v : vehicleList)
		        {
		        	if(v instanceof Car){
		        		g.setColor(Color.RED);
		        	}
		        	else if(v instanceof Bus){
		        		g.setColor(Color.YELLOW);
		        	}
		        	//For each vehicle calculate its X and Y co-ordinates
		            int carX = 0;
		            int carY = 0;
		            if(rj_c.getVehicleNodeIndex(v) != -1)
		            {
		            	carY = vrc_jEndY - vblockHeight*rj_c.getVehicleNodeIndex(v) - vcarHeight;
		            	if(rj_c.getVehicleLaneIndex(v) == 0)
		            		carX =  leftLaneDividerX - vdestinationWidth/8 - vvehicleWidth/2;
		            	else
		            		carX =  leftLaneDividerX + vdestinationWidth/8 - vvehicleWidth/2;
		            	vcarHeight =  (int) (vblockHeight*0.5);
		            	vbusHeight = (int)(vblockHeight*0.9);
		            	if(v instanceof Car){
		            		g.fillRect(carX,carY,vvehicleWidth, vcarHeight);
		            	}
		            	else if(v instanceof Bus){
		            		g.fillRect(carX,carY,vvehicleWidth, vbusHeight);
		            	}
		            }
		        }
		        Image legend = new ImageIcon(getClass().getResource("res/legend.png")).getImage();

				g.drawImage(legend, 0, 0, null);
			}
		};
	}
	
	@Override
	public JPanel getView() {
		// TODO Auto-generated method stub
		return view;
	}

	@Override
	public JPanel getControls() {
		// TODO Auto-generated method stub
		return controls;
	}
}
