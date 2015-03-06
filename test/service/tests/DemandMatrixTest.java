package service.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import core.endpoints.Destination;
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
		
		dm.initializeMatrix();
		
		assertEquals(2, dm.getMatrixDimension());
	}
	
	@Test(expected=DemandMatrixException.class)
	public void test_should_have_atleast_two_destinations_added_to_initialize_matrix() throws DemandMatrixException
	{
		DemandMatrix dm = new DemandMatrix();
		dm.addDestination(new Destination());
		
		dm.initializeMatrix();
	}
}
