package core.network.junction;

import java.util.HashMap;

import core.endpoints.Destination;
import core.endpoints.JunctionExit;

public class JunctionRouter {

	private HashMap<Destination,Interface> map;
	
	public JunctionRouter()
	{
		map = new HashMap<Destination,Interface>();
	}
	
	public void add(Destination d, Interface face)
	{
		if(d != null && face != null)
		{
			map.put(d, face);
		}
	}

	public JunctionExit getExit(Destination dest) throws InvalidRouteException {
		Interface inf = map.get(dest);
		if(inf == null)
		{
			throw new InvalidRouteException("Destination does not exist");
		}
		return inf.getExit();
	}
}
