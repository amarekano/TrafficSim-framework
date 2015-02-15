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
	
	public enum JUNCTION {WEST, EAST, NORTH, SOUTH};
	
	public Junction() throws InterfaceException
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
		return getInterface(face).getEntry();
	}
	
	public JunctionExit getJunctionExit(JUNCTION face) throws InterfaceException
	{
		return getInterface(face).getExit();
	}

	public JunctionRouter getRoutingTable() {
		return router;
	}

	public void setRoutingTable(JunctionRouter router) {
		this.router = router;
	}
	
	public Interface getExitInterface(Destination dest) throws InvalidRouteException
	{
		return router.getExitInterface(dest);
	}
	
	//AM > is there a green signal from source to destination
	public boolean isExitGreen(Interface source, Interface dest) throws InterfaceException
	{
		return source.getSignal(dest);
	}
}
