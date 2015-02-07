package core.network.interfaces;

import core.endpoints.InterfaceEntry;
import core.endpoints.InterfaceExit;

public class Interface {
	
	private InterfaceExit exit;
	private InterfaceEntry entry;
	private boolean enabled;
	private TrafficSignal signals;
	
	public Interface() 
	{
		//AM > Enable the interface
		this.enabled = true;
	}
	
	public void setSignals(Interface leftTurn, Interface forward, Interface rightTurn) throws InvalidInterfaceException
	{
		//AM > Setup traffic lights
		signals = new TrafficSignal(leftTurn, forward, rightTurn);
	}
	public InterfaceEntry getEntry() {
		return entry;
	}

	public InterfaceExit getExit() {
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

	public boolean isExitGreen(Interface dest) throws InvalidInterfaceException {
		return signals.getSignal(dest);
	}
}
