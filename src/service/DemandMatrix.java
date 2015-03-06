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
	
	public double getDemand(Destination from, Destination to) throws DemandMatrixException
	{
		if(matrix.containsKey(from))
		{
			HashMap<Destination, Double> row = matrix.get(from);
			if(row.containsKey(to))
				return row.get(to);
			else
				throw new DemandMatrixException("Destination to does not exist in the matrix");
		}
		else
		{
			throw new DemandMatrixException("Destination from does not exist in the matrix");
		}
	}
	
	public void setDemand(Destination from, Destination to, double value) throws DemandMatrixException
	{
		if(from == to)
		{
			throw new DemandMatrixException("Cannot set demand between the same destination");
		}
		
		if(matrix.containsKey(from))
		{
			HashMap<Destination, Double> row = matrix.get(from);
			if(row.containsKey(to))
			{
				//AM > Minimum demand can be 0%
				if(value > 0.0)
				{
					//AM > Maximum demand allowed is 100%
					value = value > 1.0 ? 1.0 : value;
					row.put(to,value);
				}
			}
			else
				throw new DemandMatrixException("Destination to does not exist in the matrix");
		}
		else
		{
			throw new DemandMatrixException("Destination from does not exist in the matrix");
		}
	}
}
