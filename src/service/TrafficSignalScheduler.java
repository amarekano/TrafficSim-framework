package service;

import java.util.ArrayList;
import java.util.List;

import core.network.interfaces.InterfaceException;
import core.network.junction.TrafficSignalController;

public class TrafficSignalScheduler {
	private List<TrafficSignalController> controllers;
	private int signalInterval;
	private int timer;
	
	public TrafficSignalScheduler()
	{
		controllers = new ArrayList<TrafficSignalController>();
		//AM > default interval is 10 clock ticks
		signalInterval = 10;
		timer = 0;
	}
	
	public void addSignalController(TrafficSignalController controller)
	{
		if(!controllers.contains(controller))
			controllers.add(controller);
	}
	
	public void removeSignalController(TrafficSignalController controller)
	{
		if(controllers.contains(controller))
			controllers.remove(controller);
	}
	
	public long getSignalInterval() {
		return signalInterval;
	}

	public void setSignalInterval(int signalInterval) {
		this.signalInterval = signalInterval;
	}

	public void changeSignals() throws InterfaceException
	{
		for(TrafficSignalController sigCont: controllers)
		{
			sigCont.changeSignals();
		}
	}
}
