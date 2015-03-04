package service.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import service.TrafficSignalScheduler;
import service.clock.SimulationClock;
import core.network.interfaces.InterfaceException;
import core.network.junction.Junction;
import core.network.junction.TrafficSignalController;

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
		clock.resetClock();
		TrafficSignalScheduler scheduler = new TrafficSignalScheduler();
		scheduler.setSignalInterval(10);
		clock.addObserver(scheduler);
		clock.resetClock();
		clock.setInterval(1000);
		clock.startClock();
		Junction junc = new Junction();
		TrafficSignalController signalController = new TrafficSignalController(junc);
		scheduler.addSignalController(signalController);
		
		assertEquals(0,signalController.getCycle());
		
		Thread.sleep(10000);
		clock.pauseClock();
		
		assertEquals(1, signalController.getCycle());
		
		clock.resumeClock();
		Thread.sleep(10000);
		assertEquals(2, signalController.getCycle());
	}

}
