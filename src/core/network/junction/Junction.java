package core.network.junction;

import core.endpoints.Destination;
import core.endpoints.JunctionEntry;
import core.endpoints.JunctionExit;
import core.network.interfaces.Interface;
import core.network.interfaces.InterfaceException;

public class Junction {
	private Interface west;
	private Interface east;
	private Interface north;
	private Interface south;
	
	private int enabledInterfaceCount;
	private JunctionRouter router;
	private TrafficSignalController signalController;
	
	public enum JUNCTION {WEST, EAST, NORTH, SOUTH};
	
	public Junction()
	{
		//AM > A Junction is created with all it's interfaces enabled
		west = new Interface();
		east = new Interface();
		south = new Interface();
		north = new Interface();
		
		enabledInterfaceCount = 4;
		
		//AM > Setup traffic signals
		west.configureSignal(north,east,south);
		east.configureSignal(south,west,north);
		south.configureSignal(west,north,east);
		north.configureSignal(east,south,west);
	}
	
	public void enableInterface(JUNCTION face) throws InterfaceException
	{
		Interface inf = getInterface(face);
		if(!inf.isEnabled())
		{
			inf.enableInterface();
			enabledInterfaceCount++;
		}
	}
	
	public void disableInterface(JUNCTION face) throws InterfaceException, JunctionException
	{	
		Interface inf = getInterface(face);
		if(inf.isEnabled())
		{
			inf.disableInterface();
			enabledInterfaceCount--;
		}
		
		if(enabledInterfaceCount < 2)
			throw new JunctionException("There needs to be a minimum of two enabled Inferfaces");
	}
	
	public int getEnabledInterfaceCount() {
		return enabledInterfaceCount;
	}

	public void setEnabledInterfaceCount(int enabledInterfaceCount) {
		this.enabledInterfaceCount = enabledInterfaceCount;
	}

	public Interface getInterface(JUNCTION face) throws InterfaceException
	{
		if(face == JUNCTION.EAST && east != null)
		{
			return east;
		}
		else if(face == JUNCTION.NORTH && north != null)
		{
			return north;
		}
		else if(face == JUNCTION.SOUTH && south != null)
		{
			return south;
		}
		else if(face == JUNCTION.WEST && west != null)
		{
			return west;
		}
		else
		{
			throw new InterfaceException("Invalid Interface selected or interface is disabled");
		}
	}
	
	public JunctionEntry getJunctionEntry(JUNCTION face) throws InterfaceException
	{
		JunctionEntry entry = getInterface(face).getEntry(); 
		if(entry.isConnected())
			throw new InterfaceException("Junction Entry has a Road connected");
		else
			return entry;
	}
	
	public JunctionExit getJunctionExit(JUNCTION face) throws InterfaceException
	{
		JunctionExit exit = getInterface(face).getExit();
		if(exit.isConnected())
			throw new InterfaceException("Junction Exit has a Road connected");
		else
		return exit;
	}

	public JunctionRouter getRoutingTable() {
		return router;
	}

	public void setRoutingTable(JunctionRouter router) {
		this.router = router;
	}
	
	public Interface getExitInterface(Destination dest) throws InvalidRouteException, JunctionException
	{
		if(router != null)
			return router.getExitInterface(dest);
		else
			throw new JunctionException("Routing Table not set");
	}
	
	//AM > is there a green signal from source to destination
	public boolean isExitGreen(Interface source, Interface dest) throws InterfaceException
	{
		return source.getSignalState(dest);
	}

	
	public TrafficSignalController getSignalController() {
		return signalController;
	}

	public void setSignalController() throws InterfaceException {
		this.signalController = new TrafficSignalController(this);
	}
}
