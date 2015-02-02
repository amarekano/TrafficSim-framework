package core;

import static org.junit.Assert.*;

import org.junit.Test;

public class MaxVelocityTest {

	@Test
	public void test_car_should_not_exceed_its_max_velocity()
	{
		Car c1 = new Car(1,2,4);
		Lane lane = new Lane(9);
		
		lane.addCar(c1);
		lane.moveCars();
		lane.moveCars();
		assertEquals(4, c1.getVelocity());
		assertEquals(true, lane.toString().equalsIgnoreCase("000000010"));
	}
	
	@Test
	public void test_car_max_velocity_constraint_with_multiple_cars()
	{
		Car c1 = new Car(2,0,2);
		Car c2 = new Car(1,4,5);
		
		Lane lane = new Lane(10);
		
		lane.addCar(c1);
		lane.moveCars();
		lane.addCar(c2);
		lane.moveCars();
		assertEquals(3, c2.getVelocity());
		
	}

}
