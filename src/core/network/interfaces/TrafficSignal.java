package core.network.interfaces;

import java.util.HashMap;

public class TrafficSignal {

	private HashMap<Interface, Boolean> lights;
	
	public TrafficSignal(Interface leftTurn, Interface forward, Interface rightTurn) throws InvalidInterfaceException
	{
		lights = new HashMap<Interface, Boolean>();
		
		lights.put(leftTurn, false);
		lights.put(rightTurn, false);
		lights.put(forward, false);
		
		if(lights.size() < 3)
			throw new InvalidInterfaceException("Duplicate Interface detected");
	}
	
	public boolean getSignal(Interface face) throws InvalidInterfaceException
	{
		if(lights.containsKey(face))
		{
			return lights.get(face);
		}
		else
		{
			throw new InvalidInterfaceException("Unknown Interface");
		}
	}
	
	public void setSignal(Interface face, boolean state) throws InvalidInterfaceException
	{
		if(lights.containsKey(face))
		{
			lights.put(face, state);
		}
		else
		{
			throw new InvalidInterfaceException("Unknown Interface");
		}
	}
}
