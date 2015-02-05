package core.vehicle.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import core.network.Lane;
import core.vehicle.Car;

public class MaxVelocityTest {

	@Test
	public void test_car_should_not_exceed_its_max_velocity()
	{
		Car c1 = new Car(1,2,4);
		Lane lane = new Lane(9);
		
		lane.addVehicle(c1);
		lane.moveVehicles();
		lane.moveVehicles();
		assertEquals(4, c1.getVelocity());
		assertEquals(true, lane.toString().equalsIgnoreCase("000000010"));
	}
	
	@Test
	public void test_car_max_velocity_constraint_with_multiple_cars()
	{
		Car c1 = new Car(2,0,2);
		Car c2 = new Car(1,4,5);
		
		Lane lane = new Lane(10);
		
		lane.addVehicle(c1);
		lane.moveVehicles();
		lane.addVehicle(c2);
		lane.moveVehicles();
		assertEquals(3, c2.getVelocity());
		
	}

}
