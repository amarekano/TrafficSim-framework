package core.network.interfaces;

import java.util.HashMap;

public class TrafficSignal {

	private HashMap<Interface, Boolean> lights;
	
	public TrafficSignal(Interface leftTurn, Interface forward, Interface rightTurn) throws InterfaceException
	{
		lights = new HashMap<Interface, Boolean>();
		
		lights.put(leftTurn, true);
		lights.put(rightTurn, true);
		lights.put(forward, true);
		
		if(lights.size() < 3)
			throw new InterfaceException("Duplicate Interface detected");
	}
	
	public boolean getSignal(Interface face) throws InterfaceException
	{
		if(lights.containsKey(face))
		{
			return lights.get(face);
		}
		else
		{
			throw new InterfaceException("Unknown Interface");
		}
	}
	
	public void setSignal(Interface face, boolean state) throws InterfaceException
	{
		if(lights.containsKey(face))
		{
			lights.put(face, state);
		}
		else
		{
			throw new InterfaceException("Unknown Interface");
		}
	}
}
