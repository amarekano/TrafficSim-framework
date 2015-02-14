package core.network.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import core.network.Lane;
import core.network.Lane.LANE;
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
		assertEquals(0,lane.getVehicleIndex(c1));
	}
	
	@Test
	public void test_lane_state_on_moving_car() {
		Car c1 = new Car();
		Lane lane = new Lane(2);
		lane.addVehicle(c1);
		lane.moveVehicles();
		assertEquals(1,lane.getVehicleIndex(c1));
	}
	
	@Test
	public void test_car_leaving_the_lane()
	{
		Car c1 = new Car();
		Lane lane = new Lane(2);
		lane.addVehicle(c1);
		assertEquals(0,lane.getVehicleIndex(c1));
		lane.moveVehicles();
		assertEquals(1,lane.getVehicleIndex(c1));
		lane.moveVehicles();
		assertEquals(-1,lane.getVehicleIndex(c1));
	}
	
	@Test
	public void test_lane_state_on_moving_car_with_variable_velocity()
	{
		Car c1 = new Car(3,0,3);
		Lane lane = new Lane(5);
		lane.addVehicle(c1);
		assertEquals(0,lane.getVehicleIndex(c1));
		lane.moveVehicles();
		assertEquals(3,lane.getVehicleIndex(c1));
	}
	
	@Test
	public void test_car_with_variable_velocity_leaving_the_lane()
	{
		Car c1 = new Car(5,0,5);
		Lane lane = new Lane(3);
		lane.addVehicle(c1);
		assertEquals(0,lane.getVehicleIndex(c1));
		lane.moveVehicles();
		assertEquals(-1,lane.getVehicleIndex(c1));
	}
	
	@Test
	public void test_lane_state_with_multiple_cars()
	{
		Car c1 = new Car(2,0,2);
		Car c2 = new Car(1,0,1);
		Lane lane = new Lane(7);
		lane.addVehicle(c1);
		lane.moveVehicles();
		assertEquals(2,lane.getVehicleIndex(c1));
		assertEquals(true, lane.addVehicle(c2));
		lane.moveVehicles();
		assertEquals(4,lane.getVehicleIndex(c1));
		assertEquals(1,lane.getVehicleIndex(c2));
		lane.moveVehicles();
		assertEquals(6,lane.getVehicleIndex(c1));
		assertEquals(2,lane.getVehicleIndex(c2));
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
		
		assertEquals(2,lane.getVehicleIndex(c1));
		assertEquals(0,lane.getVehicleIndex(c2));
		lane.moveVehicles();
		assertEquals(-1,lane.getVehicleIndex(c1));
		assertEquals(1,lane.getVehicleIndex(c2));
		lane.moveVehicles();
		lane.moveVehicles();
		assertEquals(-1,lane.getVehicleIndex(c1));
		assertEquals(3,lane.getVehicleIndex(c2));
		lane.moveVehicles();
		assertEquals(-1,lane.getVehicleIndex(c1));
		assertEquals(-1,lane.getVehicleIndex(c2));
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
		
		assertEquals(2,lane.getVehicleIndex(c1));
		assertEquals(0,lane.getVehicleIndex(c2));
		lane.moveVehicles();
		
		assertEquals(4,lane.getVehicleIndex(c1));
		assertEquals(3,lane.getVehicleIndex(c2));
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
		assertEquals(2,lane.getVehicleIndex(c1));
		assertEquals(0,lane.getVehicleIndex(c2));
		lane.moveVehicles();
		assertEquals(4,lane.getVehicleIndex(c1));
		assertEquals(3,lane.getVehicleIndex(c2));
		assertEquals(3, c2.getVelocity());
	}
	
	@Test
	public void test_moving_vehicle_on_an_empty_lane_should_return_an_empty_list()
	{
		Lane lane = new Lane(5);
		List<Vehicle> exiting_vehicles = new ArrayList<Vehicle>();
		
		exiting_vehicles.addAll(lane.moveVehicles());
		
		assertEquals(0, exiting_vehicles.size());
		
	}
	
	@Test
	public void test_move_vehicles_returns_a_list_of_exiting_vehicles()
	{
		Lane lane = new Lane(5);
		Vehicle v1 = new Car();
		Vehicle v2 = new Car(2,0,2);
		Vehicle v3 = new Car(4,0,4);
		
		List<Vehicle> exiting_vehicles = new ArrayList<Vehicle>();
		
		lane.addVehicle(v3);
		exiting_vehicles.addAll(lane.moveVehicles());
		assertEquals(0, exiting_vehicles.size());
		lane.addVehicle(v2);
		exiting_vehicles.addAll(lane.moveVehicles());
		assertEquals(1, exiting_vehicles.size());
		lane.addVehicle(v1);
		lane.moveVehicles();
		exiting_vehicles.addAll(lane.moveVehicles());
		assertEquals(2, exiting_vehicles.size());
		for(int i = 0; i < 2; i++)
			lane.moveVehicles();
		exiting_vehicles.addAll(lane.moveVehicles());
		assertEquals(3, exiting_vehicles.size());
	}
	
	@Test
	public void test_exited_vehicle_exists_in_list()
	{
		Lane lane = new Lane(2);
		Vehicle v1 = new Car();
		Vehicle v2 = new Car();
		
		List<Vehicle> exiting_vehicles = new ArrayList<Vehicle>();
		
		lane.addVehicle(v1);
		for(int i = 0; i < 5; i++)
		{
			exiting_vehicles.addAll(lane.moveVehicles());
			if(i == 2)
				lane.addVehicle(v2);
		}
		
		assertEquals(2, exiting_vehicles.size());
		assertEquals(true,exiting_vehicles.contains(v1));
		assertEquals(true,exiting_vehicles.contains(v2));
	}
	
	@Test
	public void test_cars_exit_lane_when_laneState_is_move()
	{
		Lane lane = new Lane(4);
		Vehicle v1 = new Car(2,0,2);
		Vehicle v2 = new Car(1,1,4);
		
		List<Vehicle> exiting_vehicles = new ArrayList<Vehicle>();
		
		lane.addVehicle(v1);
		for(int i = 0; i < 5; i++)
		{
			exiting_vehicles.addAll(lane.moveVehicles());
			if(i == 1)
				lane.addVehicle(v2);
		}
		assertEquals(true, lane.getState() == LANE.MOVE);
		assertEquals(2, exiting_vehicles.size());
	}
	
	@Test
	public void test_cars_wait_at_end_of_lane_when_laneState_is_wait()
	{
		Lane lane = new Lane(10);
		Vehicle v1 = new Car(2,0,2);
		Vehicle v2 = new Car(1,1,4);
		Vehicle v3 = new Car(2,2,10);
		
		List<Vehicle> exiting_vehicles = new ArrayList<Vehicle>();
		
		lane.setState(LANE.WAIT);
		lane.addVehicle(v1);
		for(int i = 0; i < 15; i++)
		{
			exiting_vehicles.addAll(lane.moveVehicles());
			if(i == 1)
				lane.addVehicle(v2);
			if(i == 5)
				lane.addVehicle(v3);
			
		}
		
		assertEquals(true, lane.getState() == LANE.WAIT);
		assertEquals(0, exiting_vehicles.size());
		assertEquals(9, lane.getVehicleIndex(v1));
		assertEquals(8, lane.getVehicleIndex(v2));
		assertEquals(7,lane.getVehicleIndex(v3));
	}

	@Test
	public void test_adding_cars_to_full_lane_in_waiting_state()
	{
		Lane lane = new Lane(2);
		lane.setState(LANE.WAIT);
		
		Vehicle v1 = new Car();
		Vehicle v2 = new Car();
		Vehicle v3 = new Car();
		
		lane.addVehicle(v1);
		lane.moveVehicles();
		lane.addVehicle(v2);
		lane.moveVehicles();
		
		assertEquals(false, lane.addVehicle(v3));
	}
	
	@Test
	public void test_changing_lane_state_from_wait_to_move()
	{
		Lane lane = new Lane(2);
		lane.setState(LANE.WAIT);
		
		List<Vehicle> exitingVehicles = new ArrayList<Vehicle>();
		
		lane.addVehicle(new Car());
		lane.moveVehicles();
		lane.addVehicle(new Car());
		lane.moveVehicles();
		lane.addVehicle(new Car());
		for(int i = 0; i < 3; i++)
		{
			exitingVehicles.addAll(lane.moveVehicles());
		}
		assertEquals(true, lane.getState() == LANE.WAIT);
		assertEquals(0, exitingVehicles.size());
		
		lane.setState(LANE.MOVE);
		for(int i =0; i < 3; i++)
		{
			exitingVehicles.addAll(lane.moveVehicles());
		}
		
		assertEquals(true, lane.getState() == LANE.MOVE);
		assertEquals(2, exitingVehicles.size());
	}
	
	@Test
	public void test_transfering_vehicles_when_transfer_lanes_is_null()
	{
		Lane srcLane = new Lane(2);
		Vehicle v1 = new Car();
		
		srcLane.setState(LANE.TRANSFER);
		srcLane.addVehicle(v1);
		
		for(int i = 0; i < 3; i++)
			srcLane.moveVehicles();
		
		assertEquals(1, srcLane.getVehicleIndex(v1));
	}
	
	@Test
	public void test_transfering_vehicle_to_single_lane()
	{
		Lane lane = new Lane(2);
		List<Lane> transferLane = new ArrayList<Lane>();
		Lane destLane = new Lane(2);
		
		Vehicle v1 = new Car();
		lane.addVehicle(v1);
		
		lane.setTransferLanes(transferLane);
		lane.setState(LANE.TRANSFER);
		
		destLane.setState(LANE.WAIT);
		transferLane.add(destLane);
		
		for(int i = 0; i < 5; i++)
			lane.moveVehicles();
		
		assertEquals(0, destLane.getVehicleIndex(v1));
	}
	
	@Test
	public void test_transfering_vehicles_to_a_full_lane()
	{
		Lane srcLane = new Lane(2);
		Lane destLane = new Lane(2);
		List<Lane> transferLanes = new ArrayList<Lane>();
		transferLanes.add(destLane);
		
		Vehicle v1 = new Car();
		Vehicle v2 = new Car(2,0,2);
		Vehicle v3 = new Car();
		
		srcLane.addVehicle(v1);
		srcLane.setState(LANE.TRANSFER);
		srcLane.setTransferLanes(transferLanes);
		
		//AM > Fill the destination lane
		destLane.setState(LANE.WAIT);
		destLane.addVehicle(v2);
		destLane.moveVehicles();
		destLane.addVehicle(v3);
		
		for(int i = 0; i < 3; i++)
			srcLane.moveVehicles();
		
		assertEquals(1, srcLane.getVehicleIndex(v1));
		assertEquals(0, destLane.getVehicleIndex(v3));
		assertEquals(1, destLane.getVehicleIndex(v2));
	}
	
	@Test
	public void test_transfering_vehicles_to_multiple_lanes()
	{
		Lane srcLane = new Lane(2);
		List<Lane> transferLanes = new ArrayList<Lane>();
		Lane destLane1 = new Lane(1);
		Lane destLane2 = new Lane(1);
		destLane1.setState(LANE.WAIT);
		destLane2.setState(LANE.WAIT);
		transferLanes.add(destLane1);
		transferLanes.add(destLane2);
		
		srcLane.setState(LANE.TRANSFER);
		srcLane.setTransferLanes(transferLanes);
		
		Vehicle v1 = new Car();
		Vehicle v2 = new Car();
		Vehicle v3 = new Car();
		
		srcLane.addVehicle(v1);
		for(int i = 0; i < 7; i++)
		{
			srcLane.moveVehicles();
			if(i == 1)
				srcLane.addVehicle(v2);
			if(i == 2)
				srcLane.addVehicle(v3);
		}
		
		assertEquals(0, transferLanes.get(0).getVehicleIndex(v1));
		assertEquals(0, transferLanes.get(1).getVehicleIndex(v2));
		assertEquals(1, srcLane.getVehicleIndex(v3));
	}
	
}
