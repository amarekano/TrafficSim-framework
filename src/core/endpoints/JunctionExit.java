package core.endpoints;

import java.util.List;

import core.network.Lane;

public class JunctionExit extends EndPoint {
	private List<Lane> lanes;
	
	public List<Lane> getLanes()
	{
		return lanes;
	}
	
	public void setLanes(List<Lane> lanes)
	{
		this.lanes = lanes;
	}
}
