package core.network.tests;

import static org.junit.Assert.*;
import org.junit.Test;

import core.network.Lane;
import core.vehicle.Car;
import core.vehicle.Vehicle;

public class LaneTest {

	@Test
	public void test_adding_car_to_lane() {
		Vehicle v = new Car();
		Lane lane = new Lane(2);
		assertEquals(true, lane.addVehicle(v));
	}

	@Test
	public void test_adding_car_to_occupied_lane()
	{
		Vehicle c1 = new Car();
		Vehicle c2 = new Car();
		Lane lane = new Lane(2);
		assertEquals(true, lane.addVehicle(c1));
		assertEquals(false, lane.addVehicle(c2));
	}
	
	@Test
	public void test_lane_state_on_adding_cars() {
		Vehicle c1 = new Car();
		Lane lane = new Lane(2);
		lane.addVehicle(c1);
		assertEquals(true, lane.toString().equalsIgnoreCase("10"));
	}
	
	@Test
	public void test_lane_state_on_moving_car() {
		Car c1 = new Car();
		Lane lane = new Lane(2);
		lane.addVehicle(c1);
		lane.moveVehicles();
		assertEquals(true, lane.toString().equalsIgnoreCase("01"));
	}
	
	@Test
	public void test_car_leaving_the_lane()
	{
		Car c1 = new Car();
		Lane lane = new Lane(2);
		lane.addVehicle(c1);
		assertEquals(true, lane.toString().equalsIgnoreCase("10"));
		lane.moveVehicles();
		assertEquals(true,lane.toString().equalsIgnoreCase("01"));
		lane.moveVehicles();
		assertEquals(true, lane.toString().equalsIgnoreCase("00"));
	}
	
	@Test
	public void test_lane_state_on_moving_car_with_variable_velocity()
	{
		Car c1 = new Car(3,0,3);
		Lane lane = new Lane(5);
		lane.addVehicle(c1);
		assertEquals(true, lane.toString().equalsIgnoreCase("10000"));
		lane.moveVehicles();
		assertEquals(true, lane.toString().equalsIgnoreCase("00010"));
	}
	
	@Test
	public void test_car_with_variable_velocity_leaving_the_lane()
	{
		Car c1 = new Car(5,0,5);
		Lane lane = new Lane(3);
		lane.addVehicle(c1);
		assertEquals(true, lane.toString().equalsIgnoreCase("100"));
		lane.moveVehicles();
		assertEquals(true, lane.toString().equalsIgnoreCase("000"));
	}
	
	@Test
	public void test_lane_state_with_multiple_cars()
	{
		Car c1 = new Car(2,0,2);
		Car c2 = new Car(1,0,1);
		Lane lane = new Lane(7);
		lane.addVehicle(c1);
		lane.moveVehicles();
		assertEquals(true, lane.toString().equalsIgnoreCase("0010000"));
		assertEquals(true, lane.addVehicle(c2));
		lane.moveVehicles();
		assertEquals(true, lane.toString().equalsIgnoreCase("0100100"));
		lane.moveVehicles();
		assertEquals(true, lane.toString().equalsIgnoreCase("0010001"));
	}
	
	@Test
	public void test_multiple_cars_leaving_lane()
	{
		Car c1 = new Car(2,0,2);
		Car c2 = new Car(1,0,2);
		Lane lane = new Lane(4);
		lane.addVehicle(c1);
		lane.moveVehicles();
		lane.addVehicle(c2);
		
		assertEquals(true, lane.toString().equalsIgnoreCase("1010"));
		lane.moveVehicles();
		assertEquals(true, lane.toString().equalsIgnoreCase("0100"));
		lane.moveVehicles();
		lane.moveVehicles();
		assertEquals(true, lane.toString().equalsIgnoreCase("0001"));
		lane.moveVehicles();
		assertEquals(true, lane.toString().equalsIgnoreCase("0000"));
	}
	
	@Test
	public void test_car_following()
	{
		Car c1 = new Car(2,0,2);
		Car c2 = new Car(4,0,4);
		
		Lane lane = new Lane(5);
		lane.addVehicle(c1);
		lane.moveVehicles();
		lane.addVehicle(c2);
		assertEquals(true, lane.toString().equalsIgnoreCase("10100"));
		lane.moveVehicles();
		assertEquals(true,lane.toString().equalsIgnoreCase("00011"));
		assertEquals(3, c2.getVelocity());
	}
	
	@Test
	public void test_car_following_when_trailing_car_has_velocity_greater_than_lane_length()
	{
		Car c1 = new Car(2,0,2);
		Car c2 = new Car(20,0,20);
		
		Lane lane = new Lane(5);
		lane.addVehicle(c1);
		lane.moveVehicles();
		lane.addVehicle(c2);
		assertEquals(true, lane.toString().equalsIgnoreCase("10100"));
		lane.moveVehicles();
		assertEquals(true,lane.toString().equalsIgnoreCase("00011"));
		assertEquals(3, c2.getVelocity());
	}
}
