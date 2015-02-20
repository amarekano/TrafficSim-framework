package core.network.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import core.network.interfaces.InterfaceException;
import core.network.interfaces.TrafficSignal;
import core.network.junction.Junction;
import core.network.junction.Junction.JUNCTION;
import core.network.junction.TrafficSignalController;

public class TrafficSignalControllerTest {

	@Test
	public void test_when_a_signal_controller_is_added_to_a_junction_all_signals_are_red() throws InterfaceException 
	{
		Junction junc = new Junction();
		TrafficSignalController signalController = new TrafficSignalController(junc);
		
		assertEquals(false, signalController.getWestSignal().getSignal(junc.getInterface(JUNCTION.EAST)));
		assertEquals(false, signalController.getWestSignal().getSignal(junc.getInterface(JUNCTION.NORTH)));
		assertEquals(false, signalController.getWestSignal().getSignal(junc.getInterface(JUNCTION.SOUTH)));
		
		assertEquals(false, signalController.getNorthSignal().getSignal(junc.getInterface(JUNCTION.EAST)));
		assertEquals(false, signalController.getNorthSignal().getSignal(junc.getInterface(JUNCTION.WEST)));
		assertEquals(false, signalController.getNorthSignal().getSignal(junc.getInterface(JUNCTION.SOUTH)));
	}
	
	@Test
	public void test_signal_controller_cycle_sequence_zero() throws InterfaceException
	{
		Junction junc = new Junction();
		TrafficSignalController signalController = new TrafficSignalController(junc);
		
		signalController.setCycle(0);
		signalController.changeSignals();
		
		//AM > Check the east signals
		TrafficSignal signal = junc.getInterface(JUNCTION.EAST).getSignals();
		assertEquals(true,signal.getSignal(junc.getInterface(JUNCTION.WEST)));
		assertEquals(true,signal.getSignal(junc.getInterface(JUNCTION.SOUTH)));
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.NORTH)));
		
		//AM > Check the south signals
		signal = junc.getInterface(JUNCTION.SOUTH).getSignals();
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.NORTH)));
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.WEST)));
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.EAST)));
		
		//AM > Check the west signals
		signal = junc.getInterface(JUNCTION.WEST).getSignals();
		assertEquals(true,signal.getSignal(junc.getInterface(JUNCTION.NORTH)));
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.SOUTH)));
		assertEquals(true,signal.getSignal(junc.getInterface(JUNCTION.EAST)));
		
		//AM > Check the north signals
		signal = junc.getInterface(JUNCTION.NORTH).getSignals();
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.WEST)));
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.SOUTH)));
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.EAST)));
		
		assertEquals(1,signalController.getCycle());
	}
	
	@Test
	public void test_signal_controller_cycle_sequence_one() throws InterfaceException
	{
		Junction junc = new Junction();
		TrafficSignalController signalController = new TrafficSignalController(junc);
		
		signalController.setCycle(1);
		signalController.changeSignals();
		
		//AM > Check the east signals
		TrafficSignal signal = junc.getInterface(JUNCTION.EAST).getSignals();
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.WEST)));
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.SOUTH)));
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.NORTH)));
		
		//AM > Check the south signals
		signal = junc.getInterface(JUNCTION.SOUTH).getSignals();
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.NORTH)));
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.WEST)));
		assertEquals(true,signal.getSignal(junc.getInterface(JUNCTION.EAST)));
		
		//AM > Check the west signals
		signal = junc.getInterface(JUNCTION.WEST).getSignals();
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.NORTH)));
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.SOUTH)));
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.EAST)));
		
		//AM > Check the north signals
		signal = junc.getInterface(JUNCTION.NORTH).getSignals();
		assertEquals(true,signal.getSignal(junc.getInterface(JUNCTION.WEST)));
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.SOUTH)));
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.EAST)));
		
		assertEquals(2,signalController.getCycle());
	}
	
	@Test
	public void test_signal_controller_cycle_sequence_two() throws InterfaceException
	{
		Junction junc = new Junction();
		TrafficSignalController signalController = new TrafficSignalController(junc);
		
		signalController.setCycle(2);
		signalController.changeSignals();
		
		//AM > Check the east signals
		TrafficSignal signal = junc.getInterface(JUNCTION.EAST).getSignals();
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.WEST)));
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.SOUTH)));
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.NORTH)));
		
		//AM > Check the south signals
		signal = junc.getInterface(JUNCTION.SOUTH).getSignals();
		assertEquals(true,signal.getSignal(junc.getInterface(JUNCTION.NORTH)));
		assertEquals(true,signal.getSignal(junc.getInterface(JUNCTION.WEST)));
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.EAST)));
		
		//AM > Check the west signals
		signal = junc.getInterface(JUNCTION.WEST).getSignals();
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.NORTH)));
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.SOUTH)));
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.EAST)));
		
		//AM > Check the north signals
		signal = junc.getInterface(JUNCTION.NORTH).getSignals();
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.WEST)));
		assertEquals(true,signal.getSignal(junc.getInterface(JUNCTION.SOUTH)));
		assertEquals(true,signal.getSignal(junc.getInterface(JUNCTION.EAST)));
		
		assertEquals(3,signalController.getCycle());
	}
	
	@Test
	public void test_signal_controller_cycle_sequence_three() throws InterfaceException
	{
		Junction junc = new Junction();
		TrafficSignalController signalController = new TrafficSignalController(junc);
		
		signalController.setCycle(3);
		signalController.changeSignals();
		
		//AM > Check the east signals
		TrafficSignal signal = junc.getInterface(JUNCTION.EAST).getSignals();
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.WEST)));
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.SOUTH)));
		assertEquals(true,signal.getSignal(junc.getInterface(JUNCTION.NORTH)));
		
		//AM > Check the south signals
		signal = junc.getInterface(JUNCTION.SOUTH).getSignals();
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.NORTH)));
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.WEST)));
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.EAST)));
		
		//AM > Check the west signals
		signal = junc.getInterface(JUNCTION.WEST).getSignals();
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.NORTH)));
		assertEquals(true,signal.getSignal(junc.getInterface(JUNCTION.SOUTH)));
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.EAST)));
		
		//AM > Check the north signals
		signal = junc.getInterface(JUNCTION.NORTH).getSignals();
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.WEST)));
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.SOUTH)));
		assertEquals(false,signal.getSignal(junc.getInterface(JUNCTION.EAST)));
		
		assertEquals(0,signalController.getCycle());
	}
	
	@Test
	public void test_cycle_should_be_in_the_range_of_zero_to_three() throws InterfaceException
	{
		TrafficSignalController sigCont = new TrafficSignalController(new Junction());
		sigCont.setCycle(101);
		
		assertEquals(1, sigCont.getCycle());
		
		sigCont.setCycle(-30);
		assertEquals(0, sigCont.getCycle());
	}
}
