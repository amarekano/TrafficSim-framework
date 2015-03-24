package service.tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import core.endpoints.Destination;
import core.network.Road;
import core.network.interfaces.InterfaceException;
import core.network.junction.Junction;
import core.network.junction.JunctionRouter;
import core.network.junction.Junction.JUNCTION;
import core.vehicle.Bus;
import core.vehicle.Car;
import core.vehicle.Vehicle;
import core.vehicle.VehicleException;
import service.RoadNetwork;
import service.ReportGenerator;
import service.SimulationClock;
import service.TrafficSignalScheduler;

public class ReportGeneratorTest {

	@Test
	public void test_allow_adding_consumed_vehicles() throws VehicleException {
		/*ReportGenerator report_generator= new ReportGenerator();
		Destination destinationA= new Destination();
		Destination destinationB= new Destination();
		
		Car car1=new Car();
		Car car2=new Car();
		Car car3=new Car();
		destinationA.consumeVehicle(car1);
		destinationA.consumeVehicle(car2);
		destinationA.consumeVehicle(car3);
		
		Car car4=new Car();
		Car car5=new Car();
		destinationB.consumeVehicle(car4);
		destinationB.consumeVehicle(car5);
		
		report_generator.addDestination(destinationA);
		report_generator.addDestination(destinationB);
		
		assertEquals(5,report_generator.getConsumedVehiclesLength());
		
		*/
	}
	
	
	@Test
	public void test_generating_report() throws VehicleException, IOException
	{
		Destination A = new Destination("A");
		Destination B = new Destination("B");
		
		Vehicle v1= new Car();
		Vehicle v2= new Bus();
		
		v1.setSource(A);
		v1.setDestination(B);
		
		v1.setStartTime(0);
		v1.setEndTime(40);
		
		v2.setSource(B);
		v2.setDestination(A);
		
		v2.setStartTime(0);
		v2.setEndTime(55);
		
		B.consumeVehicle(v1);
		A.consumeVehicle(v2);
		
		
		ReportGenerator report= new ReportGenerator();
		report.addDestination(A);
		report.addDestination(B);
		
		String path= "C:\\Users\\Nathalie\\Desktop\\report.txt";
		
		report.saveReport(path);
		
		File file = new File(path);
		
		assertEquals(true,file.exists());
		assertEquals(true,file.length()>0);
		
		BufferedReader br = new BufferedReader(new FileReader(file));
	 
		String line = null;

		List <String> lines=new ArrayList<String>();
		while ((line = br.readLine()) != null) {
			if(!lines.contains(line)){
				lines.add(line);
			}
		}
		br.close();
		assertEquals(true,lines.contains("0;40;A;B;Car"));
		assertEquals(true,lines.contains("0;55;B;A;Bus"));
		
		file.delete();


	}
	
	@Test
	public void test_appending_reports() throws VehicleException, IOException{
		Destination A = new Destination("A");
		Destination B = new Destination("B");
		
		Vehicle v1= new Car();
		Vehicle v2= new Bus();
		
		v1.setSource(A);
		v1.setDestination(B);
		
		v1.setStartTime(0);
		v1.setEndTime(40);
		
		v2.setSource(B);
		v2.setDestination(A);
		
		v2.setStartTime(0);
		v2.setEndTime(55);
		
		B.consumeVehicle(v1);
		A.consumeVehicle(v2);
		
		
		ReportGenerator report= new ReportGenerator();
		report.addDestination(A);
		report.addDestination(B);
		
		String path= "C:\\Users\\Nathalie\\Desktop\\report.txt";
		
		report.saveReport(path);
		
		Destination C = new Destination("C");
		Destination D = new Destination("D");
		
		Vehicle v3= new Car();
		
		v3.setSource(C);
		v3.setDestination(D);
		
		v3.setStartTime(0);
		v3.setEndTime(60);
		C.consumeVehicle(v3);
		report.addDestination(C);
		report.addDestination(D);
		
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
		assertEquals(true,lines.contains("0;40;A;B;Car"));
		assertEquals(true,lines.contains("0;55;B;A;Bus"));
		assertEquals(true,lines.contains("0;60;C;D;Car"));
		
		file.delete();

	}
}
