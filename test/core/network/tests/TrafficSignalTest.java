package core.network.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import core.network.interfaces.Interface;
import core.network.interfaces.InterfaceException;
import core.network.interfaces.TrafficSignal;

public class TrafficSignalTest {

	Interface west;
	Interface east;
	Interface south;
	Interface north;
	
	@Before
	public void initialize() throws InterfaceException
	{		
		west = new Interface();
		east = new Interface();
		south = new Interface();
		north = new Interface();
	}
	
	@Test
	public void test_on_creating_a_traffic_signal_all_lights_are_red() throws InterfaceException
	{
		TrafficSignal signal = new TrafficSignal(north, south, east);
		assertEquals(false, signal.getSignal(east));
		assertEquals(false, signal.getSignal(north));
		assertEquals(false, signal.getSignal(south));
	}
	
	@Test(expected=InterfaceException.class)
	public void test_for_unknown_interface_signal() throws InterfaceException
	{
		TrafficSignal signal = new TrafficSignal(north, south, east);
		signal.getSignal(west);
	}
	
	@Test(expected=InterfaceException.class)
	public void test_attempting_to_add_the_same_interface_twice() throws InterfaceException
	{
		TrafficSignal signal = new TrafficSignal(north,south,south);
	}
	
	@Test
	public void test_signal_should_allow_setting_of_lights() throws InterfaceException
	{
		TrafficSignal signal = new TrafficSignal(north, south, east);
		assertEquals(false, signal.getSignal(north));
		signal.setSignal(north, true);
		assertEquals(true, signal.getSignal(north));
		signal.setSignal(south, true);
		assertEquals(true,signal.getSignal(south));
		signal.setSignal(north, false);
		assertEquals(false, signal.getSignal(north));
	}
}
