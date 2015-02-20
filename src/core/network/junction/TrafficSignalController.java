package core.network.junction;

import core.network.interfaces.InterfaceException;
import core.network.interfaces.TrafficSignal;
import core.network.junction.Junction.JUNCTION;

public class TrafficSignalController {

	private TrafficSignal westSignal;
	private TrafficSignal northSignal;
	private TrafficSignal eastSignal;
	private TrafficSignal southSignal;
	private Junction junction;
	private int cycle;
	
	public TrafficSignalController(Junction junc) throws InterfaceException
	{
		this.junction = junc;
		westSignal = junc.getInterface(JUNCTION.WEST).getSignals();
		northSignal = junc.getInterface(JUNCTION.NORTH).getSignals();
		southSignal= junc.getInterface(JUNCTION.SOUTH).getSignals();
		eastSignal = junc.getInterface(JUNCTION.EAST).getSignals();
		cycle = 0;
	}
	
	public void changeSignals() throws InterfaceException
	{
		setWestSignal();
		setNorthSignal();
		setEastSignal();
		setSouthSignal();
		
		//AM > Change the cycle each time the function is called
		cycle = (cycle + 1) % 4;
	}
	
	public void setWestSignal() throws InterfaceException
	{
		if(cycle == 0)
		{
		westSignal.setSignal(junction.getInterface(JUNCTION.NORTH), true);
		westSignal.setSignal(junction.getInterface(JUNCTION.EAST), true);
		westSignal.setSignal(junction.getInterface(JUNCTION.SOUTH), false);
		}
		else if(cycle == 1 || cycle == 2)
		{
			westSignal.setSignal(junction.getInterface(JUNCTION.NORTH), false);
			westSignal.setSignal(junction.getInterface(JUNCTION.EAST), false);
			westSignal.setSignal(junction.getInterface(JUNCTION.SOUTH), false);	
		}
		else if(cycle == 3)
		{
			westSignal.setSignal(junction.getInterface(JUNCTION.NORTH), false);
			westSignal.setSignal(junction.getInterface(JUNCTION.EAST), false);
			westSignal.setSignal(junction.getInterface(JUNCTION.SOUTH), true);
		}
	}
	
	public void setNorthSignal() throws InterfaceException
	{
		if(cycle == 0 || cycle == 3)
		{
		northSignal.setSignal(junction.getInterface(JUNCTION.WEST), false);
		northSignal.setSignal(junction.getInterface(JUNCTION.SOUTH), false);
		northSignal.setSignal(junction.getInterface(JUNCTION.EAST), false);
		}
		else if(cycle == 1)
		{
			northSignal.setSignal(junction.getInterface(JUNCTION.WEST), true);
			northSignal.setSignal(junction.getInterface(JUNCTION.SOUTH), false);
			northSignal.setSignal(junction.getInterface(JUNCTION.EAST), false);
		}
		else if(cycle == 2)
		{
			northSignal.setSignal(junction.getInterface(JUNCTION.WEST), false);
			northSignal.setSignal(junction.getInterface(JUNCTION.SOUTH), true);
			northSignal.setSignal(junction.getInterface(JUNCTION.EAST), true);	
		}
	}
	
	public void setEastSignal() throws InterfaceException
	{
		if(cycle == 0)
		{
		eastSignal.setSignal(junction.getInterface(JUNCTION.NORTH), false);
		eastSignal.setSignal(junction.getInterface(JUNCTION.WEST), true);
		eastSignal.setSignal(junction.getInterface(JUNCTION.SOUTH), true);
		}
		else if(cycle == 1 || cycle == 2)
		{
			eastSignal.setSignal(junction.getInterface(JUNCTION.NORTH), false);
			eastSignal.setSignal(junction.getInterface(JUNCTION.WEST), false);
			eastSignal.setSignal(junction.getInterface(JUNCTION.SOUTH), false);
		}
		else if(cycle == 3)
		{
			eastSignal.setSignal(junction.getInterface(JUNCTION.NORTH), true);
			eastSignal.setSignal(junction.getInterface(JUNCTION.WEST), false);
			eastSignal.setSignal(junction.getInterface(JUNCTION.SOUTH), false);
		}
	}
	
	public void setSouthSignal() throws InterfaceException
	{
		if(cycle == 0 || cycle == 3)
		{
		southSignal.setSignal(junction.getInterface(JUNCTION.WEST),false);
		southSignal.setSignal(junction.getInterface(JUNCTION.NORTH),false);
		southSignal.setSignal(junction.getInterface(JUNCTION.EAST),false);
		}
		else if(cycle == 1)
		{
			southSignal.setSignal(junction.getInterface(JUNCTION.WEST),false);
			southSignal.setSignal(junction.getInterface(JUNCTION.NORTH),false);
			southSignal.setSignal(junction.getInterface(JUNCTION.EAST),true);
		}
		else if(cycle == 2)
		{
			southSignal.setSignal(junction.getInterface(JUNCTION.WEST),true);
			southSignal.setSignal(junction.getInterface(JUNCTION.NORTH),true);
			southSignal.setSignal(junction.getInterface(JUNCTION.EAST),false);
		}		
	}

	public TrafficSignal getWestSignal() {
		return westSignal;
	}

	public TrafficSignal getNorthSignal() {
		return northSignal;
	}

	public TrafficSignal getEastSignal() {
		return eastSignal;
	}

	public TrafficSignal getSouthSignal() {
		return southSignal;
	}

	public int getCycle() {
		return cycle;
	}

	public void setCycle(int cycle) {
		this.cycle = cycle < 0 ? 0 : cycle % 4;
	}
}
