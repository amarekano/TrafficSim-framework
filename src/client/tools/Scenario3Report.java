package client.tools;

import service.DemandMatrix;
import service.ReportGenerator;
import service.RoadNetwork;
import service.SimulationClock;
import service.TrafficSignalScheduler;
import core.endpoints.Destination;
import core.network.Road;
import core.network.junction.Junction;
import core.network.junction.JunctionRouter;
import core.network.junction.Junction.JUNCTION;
import core.vehicle.Car;

public class Scenario3Report {

public static void main(String[] args) {
		
		try
		{
		System.out.println("Simulation started");	
		int number_of_lanes = 1;
		int lane_length = 10;
		
		SimulationClock clock = SimulationClock.getInstance();
		clock.setInterval(1000);
		
		Destination A = new Destination("Athens");
		Destination B = new Destination("Bonitsa");
		Destination C = new Destination("Cesaloniki");
		Destination D = new Destination("Delfoi");
		
		A.setClock(clock);
		B.setClock(clock);
		C.setClock(clock);
		D.setClock(clock);
		
		Junction junc = new Junction();
		RoadNetwork network = new RoadNetwork();
						
		/*
		 * AM > Road-junction wiring
		 * 			   |B|
		 *              ^
		 * 			    |
		 * 			    |
		 * 				\/
		 *   |A|<----->|junc|<----->|C|
		 *              ^
		 * 			    |
		 * 			    |
		 *              \/
		 * 			   |D|
		 */
		
		//AM > Roads from A, B, C and D to the junction
		Road ra_j = new Road(number_of_lanes, lane_length);
		ra_j.setSource(A);
		ra_j.setSink(junc,JUNCTION.WEST);
		network.addRoad(ra_j);
		
		Road rb_j = new Road(number_of_lanes, lane_length);
		rb_j.setSource(B);
		rb_j.setSink(junc, JUNCTION.NORTH);
		network.addRoad(rb_j);
		
		Road rc_j = new Road(number_of_lanes, lane_length);
		rc_j.setSource(C);
		rc_j.setSink(junc, JUNCTION.EAST);
		network.addRoad(rc_j);
		
		Road rd_j = new Road(number_of_lanes, lane_length);
		rd_j.setSource(D);
		rd_j.setSink(junc, JUNCTION.SOUTH);
		network.addRoad(rd_j);
		
		//AM > Roads from the Junction to A, B, C and D
		Road rj_a = new Road(number_of_lanes, lane_length);
		rj_a.setSink(A);
		rj_a.setSource(junc,JUNCTION.WEST);
		network.addRoad(rj_a);
		
		Road rj_b = new Road(number_of_lanes, lane_length);
		rj_b.setSink(B);
		rj_b.setSource(junc, JUNCTION.NORTH);
		network.addRoad(rj_b);
		
		Road rj_c = new Road(number_of_lanes, lane_length);
		rj_c.setSink(C);
		rj_c.setSource(junc, JUNCTION.EAST);
		network.addRoad(rj_c);
		
		Road rj_d = new Road(number_of_lanes, lane_length);
		rj_d.setSink(D);
		rj_d.setSource(junc, JUNCTION.SOUTH);
		network.addRoad(rj_d);
		
		
		//AM > Setup routing table
		JunctionRouter juncRouter = new JunctionRouter();
		juncRouter.add(A, junc.getInterface(JUNCTION.WEST));
		juncRouter.add(B, junc.getInterface(JUNCTION.NORTH));
		juncRouter.add(C, junc.getInterface(JUNCTION.EAST));
		juncRouter.add(D,  junc.getInterface(JUNCTION.SOUTH));
		junc.setRoutingTable(juncRouter);
		
		//AM > Setup signal scheduler
		junc.setSignalController();
		TrafficSignalScheduler scheduler = new TrafficSignalScheduler();
		scheduler.setSignalInterval(10);
		scheduler.addSignalController(junc.getSignalController());
		DemandMatrix dm = new DemandMatrix();
		dm.addDestination(A);
		dm.addDestination(B);
		dm.addDestination(C);
		dm.addDestination(D);
		dm.initializeMatrix();
		dm.setVehicleType(Car.class);
		
		dm.setDemand(A, B, 0.5);
		dm.setDemand(A, C, 0.1);
		dm.setDemand(A, D, 0.3);
		
		dm.setDemand(B, A, 0.3);
		dm.setDemand(B, C, 0.1);
		dm.setDemand(B, D, 0.3);
		
		dm.setDemand(C, B, 0.3);
		dm.setDemand(C, A, 0.3);
		dm.setDemand(C, D, 0.3);
		
		dm.setDemand(D, B, 0.5);
		dm.setDemand(D, C, 0.1);
		dm.setDemand(D, A, 0.3);
		
		clock.addObserver(dm);
		clock.addObserver(network);
		clock.addObserver(scheduler);
		
		clock.startClock();
		System.out.println("Running scenario 3");
		Thread.sleep(3*60*1000);
		
		clock.pauseClock();
		
		ReportGenerator report= new ReportGenerator();
		report.addDestination(A);
		report.addDestination(B);
		report.addDestination(C);
		report.addDestination(D);
		
		String path="Scenario3_report.txt";
		
		report.saveReport(path);
		System.out.println("Simulation ended");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
