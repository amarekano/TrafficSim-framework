package core.network.interfaces;

import core.endpoints.EndPoint;
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
	}
	
	public void configureSignal(Interface leftTurn, Interface forward, Interface rightTurn) throws InterfaceException
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

	public boolean isExitGreen(Interface dest) throws InterfaceException {
		return signals.getSignal(dest);
	}
}
