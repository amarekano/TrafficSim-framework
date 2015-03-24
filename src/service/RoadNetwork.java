package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import core.endpoints.EndPointException;
import core.network.Road;

public class RoadNetwork implements Observer {
	
	private List<Road> roads;
	
	public RoadNetwork() {
		this.roads = new ArrayList<Road>();
	}
	
	public void addRoad(Road r) {
		if(!roads.contains(r)){
			roads.add(r);
		}
	}

	@Override
	public void update(Observable clock, Object arg1) {
		for(Road r : roads)
			{
				try {
					r.moveTraffic();
				} catch (EndPointException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
}
