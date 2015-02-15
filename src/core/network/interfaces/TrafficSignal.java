package core.network.interfaces;

import java.util.HashMap;

public class TrafficSignal {

	private HashMap<Interface, Boolean> lights;
	
	public TrafficSignal(Interface leftTurn, Interface forward, Interface rightTurn) 
	{
		lights = new HashMap<Interface, Boolean>();
		
		lights.put(leftTurn, false);
		lights.put(rightTurn, false);
		lights.put(forward, false);
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
