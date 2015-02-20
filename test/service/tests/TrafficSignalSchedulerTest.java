package service.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import service.TrafficSignalScheduler;
import service.clock.Clock;
import service.clock.SimulationClock;
import core.network.interfaces.InterfaceException;
import core.network.junction.Junction;
import core.network.junction.TrafficSignalController;
import core.network.junction.Junction.JUNCTION;

public class TrafficSignalSchedulerTest {

	@Test
	public void test_client_should_be_able_to_set_the_signal_interval() 
	{
		TrafficSignalScheduler scheduler = new TrafficSignalScheduler();
		scheduler.setSignalInterval(2000);
		
		assertEquals(2000, scheduler.getSignalInterval());
	}
	
	@Test
	public void test_signals_should_change_after_the_set_interval() throws InterfaceException, InterruptedException
	{
		SimulationClock clock = SimulationClock.getInstance();
		Clock clk = new Clock();
		clock.resetClock();
		TrafficSignalScheduler scheduler = new TrafficSignalScheduler();
		scheduler.setSignalInterval(10);
		clock.addObserver(scheduler);
		
		Junction junc = new Junction();
		TrafficSignalController signalController = new TrafficSignalController(junc);
		scheduler.addSignalController(signalController);
		
		assertEquals(0,signalController.getCycle());
		
		clk.start();
		Thread.sleep(10000);
		clk.suspend();
		
		assertEquals(1, signalController.getCycle());
		
		clk.resume();
		Thread.sleep(10000);
		assertEquals(2, signalController.getCycle());
		clk.terminate();
	}

}
