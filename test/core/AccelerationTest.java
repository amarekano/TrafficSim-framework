package core;

import static org.junit.Assert.*;

import org.junit.Test;

public class AccelerationTest {

	@Test
	public void test_car_acceleration()
	{
		Car c1 = new Car(1,2,20);
		Lane lane = new Lane(9);
		lane.addCar(c1);
		assertEquals(true, lane.toString().equalsIgnoreCase("100000000"));
		lane.moveCars();
		assertEquals(true, lane.toString().equalsIgnoreCase("000100000"));
		lane.moveCars();
		assertEquals(true, lane.toString().equalsIgnoreCase("000000001"));
		lane.moveCars();
		assertEquals(true, lane.toString().equalsIgnoreCase("000000000"));
	}
	
	@Test
	public void test_car_acceleration_with_multiple_cars()
	{
		Car c1 = new Car(1,2,20);
		Car c2 = new Car(1,1,20);
		Lane lane = new Lane(12);
		lane.addCar(c1);
		lane.moveCars();
		lane.addCar(c2);
		lane.moveCars();
		
		assertEquals(true, lane.toString().equalsIgnoreCase("001000001000"));
		assertEquals(5,c1.getVelocity());
		assertEquals(2, c2.getVelocity());
		lane.moveCars();
		assertEquals(true, lane.toString().equalsIgnoreCase("000001000000"));
		assertEquals(3, c2.getVelocity());
	}
	
	@Test
	public void test_car_following_when_cars_have_acceleration()
	{
		Car c1 = new Car(2,0,2);
		Car c2 = new Car(2,2,20);
		Lane lane = new Lane(5);
		lane.addCar(c1);
		lane.moveCars();
		lane.addCar(c2);
		
		lane.moveCars();
		assertEquals(true, lane.toString().equalsIgnoreCase("00011"));
		assertEquals(3, c2.getVelocity());
	}
}
