package integration.tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import service.Network;
import service.ReportGenerator;
import service.SimulationClock;
import service.TrafficSignalScheduler;
import core.endpoints.Destination;
import core.endpoints.EndPointException;
import core.network.Road;
import core.network.interfaces.InterfaceException;
import core.network.junction.Junction;
import core.network.junction.Junction.JUNCTION;
import core.network.junction.JunctionRouter;
import core.vehicle.Bus;
import core.vehicle.Car;
import core.vehicle.Vehicle;
import core.vehicle.VehicleException;

public class IntegrationTest {

	@Test
	public void test_junction_transfer_to_three_destinations() throws InterfaceException, EndPointException, VehicleException 
	{
		int number_of_lanes = 1;
		int lane_length = 3;
		Destination A = new Destination();
		Destination B = new Destination();
		Destination C = new Destination();
		Destination D = new Destination();
		
		Road r1 = new Road(number_of_lanes, lane_length);
		Road r2 = new Road(number_of_lanes, lane_length);
		Road r3 = new Road(number_of_lanes, lane_length);
		Road r4 = new Road(number_of_lanes, lane_length);
		
		Junction junc = new Junction();
		
		Vehicle v1 = new Car();
		Vehicle v2 = new Car();
		Vehicle v3 = new Car();
		
		
		 /* AM > Road-junction wiring
		 * 			  |B|
		 * 			   |
		 * 			   r2
		 * 			   |
		 * |A|--r1---|junc|---r3--|C|
		 * 			   |
		 * 			  r4
		 * 			   |
		 * 			  |D|
		 */
		r1.setSource(A);
		r1.setSink(junc, JUNCTION.WEST);
		r2.setSource(junc, JUNCTION.NORTH);
		r2.setSink(B);
		r3.setSource(junc, JUNCTION.EAST);
		r3.setSink(C);
		r4.setSource(junc, JUNCTION.SOUTH);
		r4.setSink(D);
		
		//AM > Setup routing table
		JunctionRouter juncRouter = new JunctionRouter();
		juncRouter.add(A, junc.getInterface(JUNCTION.WEST));
		juncRouter.add(B, junc.getInterface(JUNCTION.NORTH));
		juncRouter.add(C, junc.getInterface(JUNCTION.EAST));
		juncRouter.add(D,  junc.getInterface(JUNCTION.SOUTH));
		junc.setRoutingTable(juncRouter);
		
		//AM > Setup traffic signals
		junc.getInterface(JUNCTION.WEST).setSignalState(junc.getInterface(JUNCTION.EAST), true);
		junc.getInterface(JUNCTION.WEST).setSignalState(junc.getInterface(JUNCTION.NORTH), true);
		junc.getInterface(JUNCTION.WEST).setSignalState(junc.getInterface(JUNCTION.SOUTH), true);
		
		//AM > Setup vehicle destinations
		v1.setDestination(B);
		v2.setDestination(C);
		v3.setDestination(D);
		
		A.addVehicle(v1);
		A.addVehicle(v2);
		A.addVehicle(v3);
		
		for(int i = 0; i < 7; i++)
		{
			r1.moveTraffic();
			r2.moveTraffic();
			r3.moveTraffic();
			r4.moveTraffic();
		}
		
		assertEquals(1, B.getConsumedQueueLength());
		assertEquals(1, C.getConsumedQueueLength());
		assertEquals(1, D.getConsumedQueueLength());
	}

	@Test
	public void test_bidirectional_traffic_between_two_destinations() throws EndPointException, VehicleException
	{
		Destination A = new Destination();
		Destination B = new Destination();
		
		Road r1 = new Road(1, 3);
		Road r2 = new Road(1,4);
		
		
		 /* AM > Setup wiring
		 * |A|-->--r1-->--|B|
		 * |A|--<--r2--<--|B|
		 */
		r1.setSource(A);
		r1.setSink(B);
		r2.setSource(B);
		r2.setSink(A);
		
		//AM > Add 5 vehicles to A
		for(int i = 0; i < 5; i++)
			A.addVehicle(new Car());
		//AM > Add 3 vehicles to B with speed of 2
		for(int i = 0; i < 3; i++)
			B.addVehicle(new Car(2,0,2));
		
		for(int i = 0; i < 8; i++)
		{
			r1.moveTraffic();
			r2.moveTraffic();
		}
		
		assertEquals(3,A.getConsumedQueueLength());
		assertEquals(5,B.getConsumedQueueLength());
	}

	public void test_traffic_signals_should_direct_traffic() throws InterfaceException, VehicleException, EndPointException
	{
		int number_of_lanes = 1;
		int lane_length = 3;
		Destination A = new Destination();
		Destination B = new Destination();
		Destination C = new Destination();
		Destination D = new Destination();
		
		Road r1 = new Road(number_of_lanes, lane_length);
		Road r2 = new Road(number_of_lanes, lane_length);
		Road r3 = new Road(number_of_lanes, lane_length);
		Road r4 = new Road(number_of_lanes, lane_length);
		
		Junction junc = new Junction();
		
		Vehicle v1 = new Car();
		Vehicle v2 = new Car();
		Vehicle v3 = new Car();
		
		
		/* AM > Road-junction wiring
		 * 			  |B|
		 * 			   |
		 * 			   r2
		 * 			   |
		 * |A|--r1---|junc|---r3--|C|
		 * 			   |
		 * 			  r4
		 * 			   |
		 * 			  |D|
		 */
		r1.setSource(A);
		r1.setSink(junc, JUNCTION.WEST);
		r2.setSource(junc, JUNCTION.NORTH);
		r2.setSink(B);
		r3.setSource(junc, JUNCTION.EAST);
		r3.setSink(C);
		r4.setSource(junc, JUNCTION.SOUTH);
		r4.setSink(D);
		
		//AM > Setup routing table
		JunctionRouter juncRouter = new JunctionRouter();
		juncRouter.add(A, junc.getInterface(JUNCTION.WEST));
		juncRouter.add(B, junc.getInterface(JUNCTION.NORTH));
		juncRouter.add(C, junc.getInterface(JUNCTION.EAST));
		juncRouter.add(D,  junc.getInterface(JUNCTION.SOUTH));
		junc.setRoutingTable(juncRouter);
		
		//AM > Setup traffic signals
		junc.setSignalController();
		
		//AM > Setup vehicle destinations
		v1.setDestination(B);
		v2.setDestination(C);
		v3.setDestination(D);
		
		A.addVehicle(v1);
		A.addVehicle(v2);
		A.addVehicle(v3);
		
		for(int i = 0; i < 7; i++)
		{
			r1.moveTraffic();
			r2.moveTraffic();
			r3.moveTraffic();
			r4.moveTraffic();
			junc.getSignalController().changeSignals();
		}
		
		assertEquals(1, B.getConsumedQueueLength());
		assertEquals(1, C.getConsumedQueueLength());
		assertEquals(1, D.getConsumedQueueLength());
	}

	@Test
	public void test_bidirectional_traffic_using_simulation_clock() throws VehicleException, EndPointException, InterruptedException
	{
		Destination A = new Destination();
		Destination B = new Destination();
		
		Road r1 = new Road(1, 3);
		Road r2 = new Road(1,4);
		
		
		 /* AM > Setup wiring
		 * |A|-->--r1-->--|B|
		 * |A|--<--r2--<--|B|
		 */
		r1.setSource(A);
		r1.setSink(B);
		r2.setSource(B);
		r2.setSink(A);
		
		//AM > Add 5 vehicles to A
		for(int i = 0; i < 5; i++)
			A.addVehicle(new Car());
		//AM > Add 3 vehicles to B with speed of 2
		for(int i = 0; i < 3; i++)
			B.addVehicle(new Car(2,0,2));
		
		SimulationClock clock = SimulationClock.getInstance();
		clock.setInterval(100);
		clock.resetClock();
		A.setClock(clock);
		B.setClock(clock);
		
		Network network = new Network();
		network.addRoad(r1);
		network.addRoad(r2);
		clock.addObserver(network);
		
		clock.startClock();
		Thread.sleep(8*100);
		assertEquals(3,A.getConsumedQueueLength());
		assertEquals(5,B.getConsumedQueueLength());
	}
	
	@Test
	public void test_junction_transfer_using_simulation_clock() throws InterfaceException, VehicleException, InterruptedException
	{
		int number_of_lanes = 1;
		int lane_length = 3;
		
		Destination A = new Destination();
		Destination B = new Destination();
		Destination C = new Destination();
		Destination D = new Destination();
		
		Road r1 = new Road(number_of_lanes, lane_length);
		Road r2 = new Road(number_of_lanes, lane_length);
		Road r3 = new Road(number_of_lanes, lane_length);
		Road r4 = new Road(number_of_lanes, lane_length);
		
		Junction junc = new Junction();
		
		Vehicle v1 = new Car();
		Vehicle v2 = new Car();
		Vehicle v3 = new Car();
		
		
		 /* AM > Road-junction wiring
		 * 			  |B|
		 * 			   |
		 * 			   r2
		 * 			   |
		 * |A|--r1---|junc|---r3--|C|
		 * 			   |
		 * 			  r4
		 * 			   |
		 * 			  |D|
		 */
		r1.setSource(A);
		r1.setSink(junc, JUNCTION.WEST);
		r2.setSource(junc, JUNCTION.NORTH);
		r2.setSink(B);
		r3.setSource(junc, JUNCTION.EAST);
		r3.setSink(C);
		r4.setSource(junc, JUNCTION.SOUTH);
		r4.setSink(D);
		
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
		scheduler.setSignalInterval(5);
		scheduler.addSignalController(junc.getSignalController());
		
		//AM > Setup vehicle destinations
		v1.setDestination(B);
		v2.setDestination(C);
		v3.setDestination(D);
		
		A.addVehicle(v1);
		A.addVehicle(v2);
		A.addVehicle(v3);
		
		Network network = new Network();
		network.addRoad(r1);
		network.addRoad(r2);
		network.addRoad(r3);
		network.addRoad(r4);
		
		SimulationClock clock = SimulationClock.getInstance();
		A.setClock(clock);
		B.setClock(clock);
		C.setClock(clock);
		D.setClock(clock);
		
		clock.setInterval(100);
		clock.resetClock();
		
		clock.addObserver(network);
		clock.addObserver(scheduler);
		clock.startClock();
		
		Thread.sleep(10*1000);

		assertEquals(1, B.getConsumedQueueLength());
		assertEquals(1, C.getConsumedQueueLength());
		assertEquals(1, D.getConsumedQueueLength());
	}

	@Test
	public void test_four_way_junction_transfer() throws InterfaceException, VehicleException, InterruptedException
	{
		int number_of_lanes = 1;
		int lane_length = 10;
		
		SimulationClock clock = SimulationClock.getInstance();
		clock.setInterval(100);
		
		Destination A = new Destination();
		Destination B = new Destination();
		Destination C = new Destination();
		Destination D = new Destination();
		
		A.setClock(clock);
		B.setClock(clock);
		C.setClock(clock);
		D.setClock(clock);
		
		Junction junc = new Junction();
		Network network = new Network();
						
		
		 /* AM > Road-junction wiring
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
		scheduler.setSignalInterval(5);
		scheduler.addSignalController(junc.getSignalController());
		
		clock.addObserver(network);
		clock.addObserver(scheduler);
		
		Vehicle va_b = new Car();
		va_b.setDestination(B);
		A.addVehicle(va_b);
		Vehicle va_c = new Bus();
		va_c.setDestination(C);
		A.addVehicle(va_c);
		Vehicle va_d = new Car();
		va_d.setDestination(D);
		A.addVehicle(va_d);

		Vehicle vb_a = new Car();
		vb_a.setDestination(A);
		B.addVehicle(vb_a);
		Vehicle vb_c = new Bus();
		vb_c.setDestination(C);
		B.addVehicle(vb_c);
		Vehicle vb_d = new Car();
		vb_d.setDestination(D);
		B.addVehicle(vb_d);
		
		Vehicle vc_a = new Car();
		vc_a.setDestination(A);
		C.addVehicle(vc_a);
		Vehicle vc_b = new Car();
		vc_b.setDestination(B);
		C.addVehicle(vc_b);
		Vehicle vc_d = new Bus();
		vc_d.setDestination(D);
		C.addVehicle(vc_d);
		
		Vehicle vd_a = new Bus();
		vd_a.setDestination(A);
		D.addVehicle(vd_a);
		Vehicle vd_b = new Car();
		vd_b.setDestination(B);
		D.addVehicle(vd_b);
		Vehicle vd_c = new Car();
		vd_c.setDestination(C);
		D.addVehicle(vd_c);
		
		clock.resumeClock();
		clock.startClock();
		
		Thread.sleep(10*1000);

		assertEquals(3, A.getConsumedQueueLength());
		assertEquals(3, B.getConsumedQueueLength());
		assertEquals(3, C.getConsumedQueueLength());
		assertEquals(3, D.getConsumedQueueLength());
	}
	
	@Test
	public void test_junction_transfer_using_simulation_clock_and_generating_report() throws InterfaceException, VehicleException, InterruptedException, IOException{
		int number_of_lanes = 1;
		int lane_length = 10;
		
		SimulationClock clock = SimulationClock.getInstance();
		clock.setInterval(100);
		
		Destination A = new Destination("Athens");
		Destination B = new Destination("Bonitsa");
		Destination C = new Destination("Cesaloniki");
		Destination D = new Destination("Delfoi");
		
		A.setClock(clock);
		B.setClock(clock);
		C.setClock(clock);
		D.setClock(clock);
		
		Junction junc = new Junction();
		Network network = new Network();
						
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
		scheduler.setSignalInterval(5);
		scheduler.addSignalController(junc.getSignalController());
		
		clock.addObserver(network);
		clock.addObserver(scheduler);
		
		Vehicle va_b = new Car();
		va_b.setDestination(B);
		A.addVehicle(va_b);
		Vehicle va_c = new Bus();
		va_c.setDestination(C);
		A.addVehicle(va_c);
		Vehicle va_d = new Car();
		va_d.setDestination(D);
		A.addVehicle(va_d);

		Vehicle vb_a = new Car();
		vb_a.setDestination(A);
		B.addVehicle(vb_a);
		Vehicle vb_c = new Bus();
		vb_c.setDestination(C);
		B.addVehicle(vb_c);
		Vehicle vb_d = new Car();
		vb_d.setDestination(D);
		B.addVehicle(vb_d);
		
		Vehicle vc_a = new Car();
		vc_a.setDestination(A);
		C.addVehicle(vc_a);
		Vehicle vc_b = new Car();
		vc_b.setDestination(B);
		C.addVehicle(vc_b);
		Vehicle vc_d = new Bus();
		vc_d.setDestination(D);
		C.addVehicle(vc_d);
		
		Vehicle vd_a = new Bus();
		vd_a.setDestination(A);
		D.addVehicle(vd_a);
		Vehicle vd_b = new Car();
		vd_b.setDestination(B);
		D.addVehicle(vd_b);
		Vehicle vd_c = new Car();
		vd_c.setDestination(C);
		D.addVehicle(vd_c);
		
		clock.startClock();
		
		Thread.sleep(10*1000);

		assertEquals(3, A.getConsumedQueueLength());
		assertEquals(3, B.getConsumedQueueLength());
		assertEquals(3, C.getConsumedQueueLength());
		assertEquals(3, D.getConsumedQueueLength());
		
		ReportGenerator report= new ReportGenerator();
		report.addDestination(A);
		report.addDestination(B);
		report.addDestination(C);
		report.addDestination(D);
		
		String path="greek_traffic.txt";
		
		report.saveReport(path);
		
		File file = new File(path);
		
		BufferedReader br = new BufferedReader(new FileReader(file));
	 
		String line = null;

		List <String> lines=new ArrayList<String>();
		while ((line = br.readLine()) != null) {
			if(!lines.contains(line)){
				lines.add(line);
			}
		}
		br.close();
		String text=va_b.getStartTime()+";"+va_b.getEndTime()+";"+va_b.getSource().getLabel()+";"+va_b.getDestination().getLabel()+";"+"Car";
		assertEquals(true,lines.contains(text));
		
		text=vb_a.getStartTime()+";"+vb_a.getEndTime()+";"+vb_a.getSource().getLabel()+";"+vb_a.getDestination().getLabel()+";"+"Car";
		assertEquals(true,lines.contains(text));
		
		text=vc_d.getStartTime()+";"+vc_d.getEndTime()+";"+vc_d.getSource().getLabel()+";"+vc_d.getDestination().getLabel()+";"+"Bus";
		assertEquals(true,lines.contains(text));
		
		text=vd_a.getStartTime()+";"+vd_a.getEndTime()+";"+vd_a.getSource().getLabel()+";"+vd_a.getDestination().getLabel()+";"+"Bus";
		assertEquals(true,lines.contains(text));
		
		file.delete();
	}
}
