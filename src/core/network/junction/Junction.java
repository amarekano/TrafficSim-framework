package core.network.junction;

import core.endpoints.Destination;
import core.endpoints.JunctionEntry;
import core.endpoints.JunctionExit;

public class Junction {
	private Interface west;
	private Interface east;
	private Interface north;
	private Interface south;
	
	private int enabledInterfaceCount;
	private JunctionRouter router;
	
	public enum JUNCTION {WEST, EAST, NORTH, SOUTH};
	
	public Junction()
	{
		//AM > A Junction is created with all it's interfaces enabled
		west = new Interface();
		east = new Interface();
		south = new Interface();
		north = new Interface();
		enabledInterfaceCount = 4;
	}
	
	public void enableInterface(JUNCTION face) throws InvalidInterfaceException
	{
		if(face == JUNCTION.EAST)
		{
			if(east == null)
			{
				east = new Interface();
				enabledInterfaceCount++;
			}
		}
		else if(face == JUNCTION.WEST)
		{
			if(west == null)
			{
				west = new Interface();
				enabledInterfaceCount++;
			}
		}
		else if(face == JUNCTION.NORTH)
		{
			if(north == null)
			{
				north = new Interface();
				enabledInterfaceCount++;
			}
		}
		else if(face == JUNCTION.SOUTH)
		{
			if(south == null)
			{
				south = new Interface();
				enabledInterfaceCount++;
			}
		}
		else
		{
			//AM > Invalid interface selected. Throw invalid interface exception
			throw new InvalidInterfaceException("Invalid Interface selected");
		}
	}
	
	public void disableInterface(JUNCTION face) throws InvalidInterfaceException, InvalidJunctionConfigurationException
	{	
		if(face == JUNCTION.EAST)
		{
			if(east != null)
			{
			east = null;
			enabledInterfaceCount--;
			}
		}
		else if(face == JUNCTION.WEST) 
		{
			if(west != null)
			{
				west = null;
				enabledInterfaceCount--;
			}
		}
		else if(face == JUNCTION.NORTH)
		{
			if(north != null)
			{
				north = null;
				enabledInterfaceCount--;
			}
		}
		else if(face == JUNCTION.SOUTH)
		{
			if(south != null)
			{
				south = null;
				enabledInterfaceCount--;
			}
		}
		else
		{
			//AM > Invalid interface selected. Error handling required
			throw new InvalidInterfaceException("Invalid Interface selected");
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
	
	public JunctionEntry getJunctionEntry(JUNCTION face) throws InvalidInterfaceException
	{
		return getInterface(face).getEntry();
	}
	
	public JunctionExit getJunctionExit(JUNCTION face) throws InvalidInterfaceException
	{
		return getInterface(face).getExit();
	}

	
	public JunctionRouter getRoutingTable() {
		return router;
	}
	

	public void setRoutingTable(JunctionRouter router) {
		this.router = router;
	}
	
	public JunctionExit getExit(Destination source, Destination dest) throws InvalidRouteException
	{
		if(source == dest)
			throw new InvalidRouteException("Source and Destination are the same");
		
		return router.getExit(dest);
	}
}
