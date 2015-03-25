package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import core.network.interfaces.InterfaceException;
import core.network.junction.TrafficSignalController;

public class TrafficSignalScheduler implements Observer {
	private List<TrafficSignalController> controllers;
	private int signalInterval;
	
	public TrafficSignalScheduler()
	{
		controllers = new ArrayList<TrafficSignalController>();
		//AM > default interval is 10 clock ticks
		signalInterval = 10;
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
	
	public int getCycle()
	{
		return controllers.get(0).getCycle();
	}
	
	@Override
	public void update(Observable obs, Object obj)
	{
		SimulationClock clock = (SimulationClock) obs;
		if(clock.getTime() % signalInterval == 0)
		{
			try {
				changeSignals();
			} catch (InterfaceException e) {
				e.printStackTrace();
			}
		}
	}
}
