package core.network.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import core.endpoints.Destination;
import core.endpoints.JunctionExit;
import core.network.junction.InvalidInterfaceException;
import core.network.junction.InvalidRouteException;
import core.network.junction.Junction;
import core.network.junction.Junction.JUNCTION;
import core.network.junction.InvalidJunctionConfigurationException;
import core.network.junction.JunctionRouter;

public class JunctionTest {

	@Test(expected=InvalidJunctionConfigurationException.class)
	public void test_junction_with_less_than_two_enabled_interfaces() throws InvalidInterfaceException, InvalidJunctionConfigurationException
	{
		Junction junction = new Junction();
		junction.disableInterface(JUNCTION.EAST);
		junction.disableInterface(JUNCTION.WEST);
		junction.disableInterface(JUNCTION.NORTH);
		junction.disableInterface(JUNCTION.SOUTH);
	}
	
	@Test
	public void test_junction_with_three_enabled_interfaces() throws InvalidInterfaceException
	{
		try
		{
			Junction junction = new Junction();
			junction.disableInterface(JUNCTION.EAST);
		}
		catch(InvalidJunctionConfigurationException e)
		{
			fail();
		}
	}
	
	@Test
	public void test_enabled_interface_count_when_enabling_disabling_interfaces() throws InvalidInterfaceException, InvalidJunctionConfigurationException
	{
		Junction junction = new Junction();
		junction.disableInterface(JUNCTION.NORTH);
		junction.disableInterface(JUNCTION.NORTH);
		junction.disableInterface(JUNCTION.SOUTH);
		junction.enableInterface(JUNCTION.NORTH);
		junction.enableInterface(JUNCTION.SOUTH);
		
		assertEquals(4, junction.getEnabledInterfaceCount());
	}
	
	@Test
	public void test_junction_entries_should_route_vehicles_to_the_correct_exit() throws InvalidInterfaceException, InvalidRouteException
	{
		Junction junction = new Junction();
		Destination A = new Destination();
		Destination B = new Destination();
		Destination C = new Destination();
		Destination D = new Destination();
		
		//AM > Setup routing
		JunctionRouter table = new JunctionRouter();
		table.add(A, junction.getInterface(JUNCTION.WEST));
		table.add(B, junction.getInterface(JUNCTION.EAST));
		table.add(C, junction.getInterface(JUNCTION.SOUTH));
		table.add(D, junction.getInterface(JUNCTION.WEST));
		junction.setRoutingTable(table);
		
		JunctionExit actualExit = junction.getExit(A,B);
		JunctionExit expectedExit = junction.getJunctionExit(JUNCTION.EAST);
		assertEquals(expectedExit,actualExit);
		
		actualExit = junction.getExit(C, A);
		expectedExit = junction.getJunctionExit(JUNCTION.WEST);
		assertEquals(expectedExit,actualExit);
		
		actualExit = junction.getExit(C, D);
		expectedExit = junction.getJunctionExit(JUNCTION.WEST);
		assertEquals(expectedExit,actualExit);
	}
	
	@Test(expected=InvalidRouteException.class)
	public void test_junction_routing_to_unregistered_destination() throws InvalidInterfaceException, InvalidRouteException
	{
		Junction junction = new Junction();
		Destination A = new Destination();
		Destination B = new Destination();
		
		JunctionRouter table = new JunctionRouter();
		table.add(B, junction.getInterface(JUNCTION.EAST));
		junction.setRoutingTable(table);
		
		junction.getExit(B, A);
	}
	
}
