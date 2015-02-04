package core;

import static org.junit.Assert.*;

import org.junit.Test;

public class RoadTest {

	@Test
	public void test_adding_car_to_empty_single_lane_road_should_return_true()
	{
		Road r1 = new Road(1, 10);
		Car c = new Car();
		assertEquals(true, r1.addCar(c));
	}

	@Test
	public void test_adding_car_to_occupied_single_lane_road_should_return_false()
	{
		Road r1 = new Road(1, 5);
		Car c1 = new Car();
		Car c2 = new Car();
		
		assertEquals(true, r1.addCar(c1));
		assertEquals(false, r1.addCar(c2));
	}
	
	@Test
	public void test_adding_cars_to_dual_lane_road()
	{
		Road r1 = new Road(2,5);
		Car c1 = new Car();
		Car c2 = new Car();
		
		assertEquals(true, r1.addCar(c1));
		assertEquals(true, r1.addCar(c2));
	}
	
	@Test
	public void test_adding_cars_to_multi_lane_road()
	{
		int max_lanes = 5;
		Road r1 = new Road(max_lanes,10);
		for(int i = 0; i < max_lanes; i++)
		{
			Car c = new Car();
			assertEquals(true,r1.addCar(c));
		}
	}
	
	@Test
	public void test_adding_car_to_a_specific_lane()
	{
		Road r1 = new Road(5, 10);
		Car c1 = new Car();
		
		assertEquals(true, r1.addCar(c1, 2));
		assertEquals(1,r1.getCarLaneIndex(c1));
	}
	
	@Test
	public void test_adding_car_to_a_specific_lane_check_car_index()
	{
		Road r1 = new Road(5, 10);
		Car c1 = new Car();
		
		assertEquals(true, r1.addCar(c1, 2));
		assertEquals(0,r1.getCarNodeIndex(c1));
		//assertEquals(1,r1.getCarIndex(c1));
	}
	
	@Test
	public void test_moving_cars_on_a_single_lane_road()
	{
		Road r1 = new Road(1,5);
		Car c1 = new Car(2,0,10);
		Car c2 = new Car(1,0,10);
		r1.addCar(c1,1);
		r1.moveTraffic();
		assertEquals(0,r1.getCarLaneIndex(c1));
		assertEquals(2,r1.getCarNodeIndex(c1));
		
		r1.addCar(c2,1);
		r1.moveTraffic();
		assertEquals(0,r1.getCarLaneIndex(c1));
		assertEquals(4,r1.getCarNodeIndex(c1));
		
		assertEquals(0,r1.getCarLaneIndex(c2));
		assertEquals(1,r1.getCarNodeIndex(c2));
	}
	
	@Test
	public void test_moving_cars_on_a_multi_lane_road()
	{
		Road r1 = new Road(3, 5);
		Car c1 = new Car(2,0,10);
		Car c2 = new Car(1,0,10);
		r1.addCar(c1,1);
		r1.moveTraffic();
		assertEquals(0,r1.getCarLaneIndex(c1));
		assertEquals(2,r1.getCarNodeIndex(c1));
		
		r1.addCar(c2,2);
		r1.moveTraffic();
		assertEquals(0,r1.getCarLaneIndex(c1));
		assertEquals(4,r1.getCarNodeIndex(c1));
		
		assertEquals(1,r1.getCarLaneIndex(c2));
		assertEquals(1,r1.getCarNodeIndex(c2));
	}
}
