package service.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import core.endpoints.Destination;
import core.vehicle.Car;
import core.vehicle.VehicleException;
import service.ReportGenerator;

public class ReportGeneratorTest {

	@Test
	public void test_allow_adding_consumed_vehicles() throws VehicleException {
		ReportGenerator report_generator= new ReportGenerator();
		Destination destinationA= new Destination();
		
		Car car1=new Car();
		destinationA.consumeVehicle(car1);//3 cars add
		
		report_generator.addConsumedVehicles(destinationA);
		/*
		assertEquals(num of cars,report_generator.getTotalVehicles);
		*/
		
	}


}
