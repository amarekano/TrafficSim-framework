package core.endpoints;

import java.util.List;

import core.network.Lane;

public class JunctionEntry extends EndPoint{
	private List<Lane> lanes;
	
	public List<Lane> getLanes()
	{
		return lanes;
	}
	
	public void setLanes(List<Lane> lanes)
	{
		this.lanes = lanes;
	}

	public boolean isConnected() {
		if(lanes != null)
			return true;
		else
			return false;
	}
}
