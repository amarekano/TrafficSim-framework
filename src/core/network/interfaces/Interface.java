package core.network.interfaces;

import core.endpoints.JunctionEntry;
import core.endpoints.JunctionExit;

public class Interface
{
	
	private JunctionExit exit;
	private JunctionEntry entry;
	private boolean enabled;
	private TrafficSignal signals;
	
	public Interface() 
	{
		//AM > Enable the interface
		this.enabled = true;
		exit = new JunctionExit();
		entry = new JunctionEntry();
	}
	
	public void configureSignal(Interface leftTurn, Interface forward, Interface rightTurn)
	{
		//AM > Setup traffic lights
		signals = new TrafficSignal(leftTurn, forward, rightTurn);
	}
	public JunctionEntry getEntry() {
		return entry;
	}

	public JunctionExit getExit() {
		return exit;
	}
	
	public void enableInterface() {
		this.enabled = true;
	}

	public void disableInterface() {
		this.enabled = false;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public boolean getSignalState(Interface exitInterface) throws InterfaceException {
		return signals.getSignal(exitInterface);
	}

	public void setSignalState(Interface exitInterface, boolean state) throws InterfaceException
	{
		signals.setSignal(exitInterface, state);
	}

	public TrafficSignal getSignals() {
		return signals;
	}

	public void setSignals(TrafficSignal signals) {
		this.signals = signals;
	}
}
