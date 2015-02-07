package core.network.junction;

import core.endpoints.Destination;
import core.endpoints.InterfaceEntry;
import core.endpoints.InterfaceExit;
import core.network.interfaces.Interface;
import core.network.interfaces.InvalidInterfaceException;

public class Junction {
	private Interface west;
	private Interface east;
	private Interface north;
	private Interface south;
	
	private int enabledInterfaceCount;
	private JunctionRouter router;
	
	public enum JUNCTION {WEST, EAST, NORTH, SOUTH};
	
	public Junction() throws InvalidInterfaceException
	{
		//AM > A Junction is created with all it's interfaces enabled
		west = new Interface();
		east = new Interface();
		south = new Interface();
		north = new Interface();
		
		enabledInterfaceCount = 4;
		
		//AM > Setup traffic signals
		west.setSignals(north,east,south);
		east.setSignals(south,west,north);
		south.setSignals(west,north,east);
		north.setSignals(east,south,west);
	}
	
	public void enableInterface(JUNCTION face) throws InvalidInterfaceException
	{
		Interface inf = getInterface(face);
		if(!inf.isEnabled())
		{
			inf.enableInterface();
			enabledInterfaceCount++;
		}
	}
	
	public void disableInterface(JUNCTION face) throws InvalidInterfaceException, InvalidJunctionConfigurationException
	{	
		Interface inf = getInterface(face);
		if(inf.isEnabled())
		{
			inf.disableInterface();
			enabledInterfaceCount--;
		}
		
		if(enabledInterfaceCount < 2)
			throw new InvalidJunctionConfigurationException("There needs to be a minimum of two enabled Inferfaces");
	}
	
	public int getEnabledInterfaceCount() {
		return enabledInterfaceCount;
	}

	public void setEnabledInterfaceCount(int enabledInterfaceCount) {
		this.enabledInterfaceCount = enabledInterfaceCount;
	}

	public Interface getInterface(JUNCTION face) throws InvalidInterfaceException
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
			throw new InvalidInterfaceException("Invalid Interface selected or interface is disabled");
		}
	}
	
	public InterfaceEntry getJunctionEntry(JUNCTION face) throws InvalidInterfaceException
	{
		return getInterface(face).getEntry();
	}
	
	public InterfaceExit getJunctionExit(JUNCTION face) throws InvalidInterfaceException
	{
		return getInterface(face).getExit();
	}

	
	public JunctionRouter getRoutingTable() {
		return router;
	}
	

	public void setRoutingTable(JunctionRouter router) {
		this.router = router;
	}
	
	public InterfaceExit getExit(Destination source, Destination dest) throws InvalidRouteException
	{
		if(source == dest)
			throw new InvalidRouteException("Source and Destination are the same");
		
		return router.getExit(dest);
	}
	
	//AM > is there a green signal from source to destination
	public boolean isExitGreen(Interface source, Interface dest) throws InvalidInterfaceException
	{
		return source.isExitGreen(dest);
	}
}
