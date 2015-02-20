package service.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import service.TrafficSignalScheduler;
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
	public void test_signals_should_change_after_the_set_interval()
	{
		
	}

}
