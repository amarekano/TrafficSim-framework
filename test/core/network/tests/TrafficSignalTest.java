package core.network.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import core.network.interfaces.Interface;
import core.network.interfaces.InvalidInterfaceException;
import core.network.interfaces.TrafficSignal;

public class TrafficSignalTest {

	Interface west;
	Interface east;
	Interface south;
	Interface north;
	
	@Before
	public void initialize() throws InvalidInterfaceException
	{		
		west = new Interface();
		east = new Interface();
		south = new Interface();
		north = new Interface();
	}
	
	@Test
	public void test_on_creating_a_traffic_signal_all_lights_are_red() throws InvalidInterfaceException
	{
		TrafficSignal signal = new TrafficSignal(north, south, east);
		assertEquals(false, signal.getSignal(east));
		assertEquals(false, signal.getSignal(north));
		assertEquals(false, signal.getSignal(south));
	}
	
	@Test(expected=InvalidInterfaceException.class)
	public void test_for_unknown_interface_signal() throws InvalidInterfaceException
	{
		TrafficSignal signal = new TrafficSignal(north, south, east);
		signal.getSignal(west);
	}
	
	@Test(expected=InvalidInterfaceException.class)
	public void test_attempting_to_add_the_same_interface_twice() throws InvalidInterfaceException
	{
		TrafficSignal signal = new TrafficSignal(north,south,south);
	}

}
