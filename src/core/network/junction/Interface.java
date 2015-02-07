package core.network.junction;

import core.endpoints.JunctionEntry;
import core.endpoints.JunctionExit;

public class Interface {
	private JunctionExit exit;
	private JunctionEntry entry;
	
	public Interface()
	{
		exit = new JunctionExit();
		entry = new JunctionEntry();
	}
	
	public JunctionEntry getEntry()
	{
		return entry;
	}
	
	public JunctionExit getExit()
	{
		return exit;
	}
}
