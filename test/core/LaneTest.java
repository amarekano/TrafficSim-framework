package core;

import static org.junit.Assert.*;

import org.junit.Test;

public class LaneTest {

	@Test
	public void test_adding_car_to_lane() {
		Car c = new Car();
		Lane lane = new Lane(2);
		assertEquals(true, lane.addCar(c));
	}

	@Test
	public void test_adding_car_to_occupied_lane()
	{
		Car c1 = new Car();
		Car c2 = new Car();
		Lane lane = new Lane(2);
		assertEquals(true, lane.addCar(c1));
		assertEquals(false, lane.addCar(c2));
	}
	
	@Test
	public void test_lane_state_on_adding_cars() {
		Car c1 = new Car();
		Lane lane = new Lane(2);
		lane.addCar(c1);
		assertEquals(true, lane.toString().equalsIgnoreCase("10"));
	}
	
	@Test
	public void test_lane_state_on_moving_car() {
		Car c1 = new Car();
		Lane lane = new Lane(2);
		lane.addCar(c1);
		lane.moveCars();
		assertEquals(true, lane.toString().equalsIgnoreCase("01"));
	}
	
	@Test
	public void test_car_leaving_the_lane()
	{
		Car c1 = new Car();
		Lane lane = new Lane(2);
		lane.addCar(c1);
		assertEquals(true, lane.toString().equalsIgnoreCase("10"));
		lane.moveCars();
		assertEquals(true,lane.toString().equalsIgnoreCase("01"));
		lane.moveCars();
		assertEquals(true, lane.toString().equalsIgnoreCase("00"));
	}
	
	@Test
	public void test_lane_state_on_moving_car_with_variable_velocity()
	{
		Car c1 = new Car(3);
		Lane lane = new Lane(5);
		lane.addCar(c1);
		assertEquals(true, lane.toString().equalsIgnoreCase("10000"));
		lane.moveCars();
		assertEquals(true, lane.toString().equalsIgnoreCase("00010"));
	}
	
	@Test
	public void test_car_with_variable_velocity_leaving_the_lane()
	{
		Car c1 = new Car(5);
		Lane lane = new Lane(3);
		lane.addCar(c1);
		assertEquals(true, lane.toString().equalsIgnoreCase("100"));
		lane.moveCars();
		assertEquals(true, lane.toString().equalsIgnoreCase("000"));
	}
	
	@Test
	public void test_lane_state_with_multiple_cars()
	{
		Car c1 = new Car(2);
		Car c2 = new Car(1);
		Lane lane = new Lane(7);
		lane.addCar(c1);
		lane.moveCars();
		assertEquals(true, lane.toString().equalsIgnoreCase("0010000"));
		assertEquals(true, lane.addCar(c2));
		lane.moveCars();
		assertEquals(true, lane.toString().equalsIgnoreCase("0100100"));
		lane.moveCars();
		assertEquals(true, lane.toString().equalsIgnoreCase("0010001"));
	}
	
	@Test
	public void test_multiple_cars_leaving_lane()
	{
		Car c1 = new Car(2);
		Car c2 = new Car(1);
		Lane lane = new Lane(4);
		lane.addCar(c1);
		lane.moveCars();
		lane.addCar(c2);
		
		assertEquals(true, lane.toString().equalsIgnoreCase("1010"));
		lane.moveCars();
		assertEquals(true, lane.toString().equalsIgnoreCase("0100"));
		lane.moveCars();
		lane.moveCars();
		assertEquals(true, lane.toString().equalsIgnoreCase("0001"));
		lane.moveCars();
		assertEquals(true, lane.toString().equalsIgnoreCase("0000"));
	}
	
	@Test
	public void test_car_following()
	{
		Car c1 = new Car(2);
		Car c2 = new Car(4);
		
		Lane lane = new Lane(5);
		lane.addCar(c1);
		lane.moveCars();
		lane.addCar(c2);
		assertEquals(true, lane.toString().equalsIgnoreCase("10100"));
		lane.moveCars();
		assertEquals(true,lane.toString().equalsIgnoreCase("00011"));
		assertEquals(3, c2.getVelocity());
	}
	
	@Test
	public void test_car_following_when_trailing_car_has_velocity_greater_than_lane_length()
	{
		Car c1 = new Car(2);
		Car c2 = new Car(20);
		
		Lane lane = new Lane(5);
		lane.addCar(c1);
		lane.moveCars();
		lane.addCar(c2);
		assertEquals(true, lane.toString().equalsIgnoreCase("10100"));
		lane.moveCars();
		assertEquals(true,lane.toString().equalsIgnoreCase("00011"));
		assertEquals(3, c2.getVelocity());
	}
}
