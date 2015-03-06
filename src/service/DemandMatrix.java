package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import core.endpoints.Destination;

public class DemandMatrix {

	private List<Destination> destinations;
	private HashMap<Destination, HashMap<Destination, Double>> matrix;
	
	public DemandMatrix()
	{
		destinations = new ArrayList<Destination>();
		matrix = new HashMap<Destination, HashMap<Destination, Double>>();
	}
	
	public void addDestination(Destination d)
	{
		if(!destinations.contains(d))
			destinations.add(d);
	}
	
	public int getDestinationCount()
	{
		return destinations.size();
	}
	
	public int getMatrixDimension()
	{
		return matrix.size();
	}
	
	public void initializeMatrix() throws DemandMatrixException
	{
		if(getDestinationCount() < 2)
		{
			throw new DemandMatrixException("Atleast two destinations are required to initialize the matrix");
		}
		for(Destination d1 : destinations)
		{
			HashMap<Destination,Double> row = new HashMap<Destination, Double>();
			for(Destination d2 : destinations)
			{
				row.put(d2, 0.0);
			}
			matrix.put(d1, row);
		}
	}
}
