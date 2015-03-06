package service.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import core.endpoints.Destination;
import core.vehicle.Car;
import core.vehicle.VehicleException;
import service.DemandMatrix;
import service.DemandMatrixException;

public class DemandMatrixTest {

	@Test
	public void test_should_allow_addition_of_destinations() {
		DemandMatrix dm = new DemandMatrix();
		
		dm.addDestination(new Destination());
		dm.addDestination(new Destination());
		
		assertEquals(2, dm.getDestinationCount());
	}
	
	@Test
	public void test_should_create_a_nxn_matrix_from_destinations_in_added() throws DemandMatrixException
	{
		DemandMatrix dm = new DemandMatrix();
		
		dm.addDestination(new Destination());
		dm.addDestination(new Destination());
		dm.addDestination(new Destination());
		
		dm.initializeMatrix();
		
		assertEquals(3, dm.getMatrixDimension());
	}
	
	@Test(expected=DemandMatrixException.class)
	public void test_should_have_atleast_two_destinations_added_to_initialize_matrix() throws DemandMatrixException
	{
		DemandMatrix dm = new DemandMatrix();
		dm.addDestination(new Destination());
		
		dm.initializeMatrix();
	}
	
	@Test
	public void test_should_allow_retriving_demand_between_two_destinations() throws DemandMatrixException
	{
		DemandMatrix dm = new DemandMatrix();
		Destination A = new Destination();
		Destination B = new Destination();
		
		dm.addDestination(A);
		dm.addDestination(B);
		
		dm.initializeMatrix();
		
		assertEquals(0.0, dm.getDemand(A,B),0.0);
		assertEquals(0.0, dm.getDemand(B,B),0.0);
		assertEquals(0.0, dm.getDemand(B,A),0.0);
	}
	
	@Test
	public void test_should_allow_setting_demand_between_two_destinations() throws DemandMatrixException
	{
		DemandMatrix dm = new DemandMatrix();
		Destination A = new Destination();
		Destination B = new Destination();
		
		dm.addDestination(A);
		dm.addDestination(B);
		
		dm.initializeMatrix();
		
		dm.setDemand(A,B,0.5);
		dm.setDemand(B,A,0.7);
		
		assertEquals(0.5, dm.getDemand(A,B),0.0);
		assertEquals(0.0, dm.getDemand(B,B),0.0);
		assertEquals(0.7, dm.getDemand(B,A),0.0);
	}
	
	@Test(expected=DemandMatrixException.class)
	public void test_should_not_all_setting_demand_for_the_same_source_and_destination() throws DemandMatrixException
	{
		DemandMatrix dm = new DemandMatrix();
		Destination A = new Destination();
		Destination B = new Destination();
		
		dm.addDestination(B);
		dm.addDestination(A);
		
		dm.initializeMatrix();
		
		dm.setDemand(A, A, 0.5);
	}
	
	@Test
	public void test_max_and_min_values_of_demand() throws DemandMatrixException
	{
		DemandMatrix dm = new DemandMatrix();
		Destination A = new Destination();
		Destination B = new Destination();
		Destination C = new Destination();
		
		dm.addDestination(A);
		dm.addDestination(B);
		dm.addDestination(C);
		
		dm.initializeMatrix();
		
		dm.setDemand(A,B,99);
		dm.setDemand(B,A,-12);
		dm.setDemand(A,C, 0.6);
		
		assertEquals(1, dm.getDemand(A,B),0.0);
		assertEquals(0, dm.getDemand(B,A),0.0);
		assertEquals(0.6, dm.getDemand(A, C),0.0);
	}
	
	@Test
	public void test_should_generate_vehicles_at_destinations_based_on_demand() throws DemandMatrixException, InstantiationException, IllegalAccessException, VehicleException
	{
		DemandMatrix dm = new DemandMatrix();
		Destination A = new Destination();
		Destination B = new Destination();
		
		dm.addDestination(A);
		dm.addDestination(B);
		
		dm.initializeMatrix();
		
		dm.setVehicleType(Car.class);
		
		dm.setDemand(A, B, 0.66);
		dm.setDemand(B, A, 1);
		
		for(int i = 0; i < 10; i++)
		{
			dm.generateVehicles();
		}
		
		assertEquals(10, B.getWaitingQueueLength());
		assertEquals(true, 5 <= A.getWaitingQueueLength());
	}
	
	@Test
	public void test_multiple_calls_to_initialize_matrix_should_not_wipe_out_data() throws DemandMatrixException
	{
		DemandMatrix dm = new DemandMatrix();
		Destination A = new Destination();
		Destination B = new Destination();
		
		dm.addDestination(A);
		dm.addDestination(B);
		
		dm.initializeMatrix();
		
		dm.setDemand(A, B, 0.6);
		dm.setDemand(B, A, 0.05);
		
		dm.initializeMatrix();
		
		assertEquals(0.6, dm.getDemand(A, B),0.0);
		assertEquals(0.05, dm.getDemand(B,A),0.0);
	}
}
