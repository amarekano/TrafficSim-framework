package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import core.endpoints.Destination;
import core.vehicle.Vehicle;
import core.vehicle.VehicleException;

public class DemandMatrix implements Observer {

	private List<Destination> destinations;
	private HashMap<Destination, HashMap<Destination, Double>> matrix;
	private Class<?> vehicleType;
	
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
			if(!matrix.containsKey(d1))
			{
				HashMap<Destination,Double> row = new HashMap<Destination, Double>();
				for(Destination d2 : destinations)
				{
					if(!row.containsKey(d2))
					{
						row.put(d2, 0.0);
					}
				}
				matrix.put(d1, row);
			}
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

	public void setVehicleType(Class<?> type)
	{
		vehicleType = type;
	}
	
	public Class<?> getVehicleType()
	{
		return vehicleType;
	}
	
	public void generateVehicles() throws InstantiationException, IllegalAccessException, VehicleException
	{
		for(Destination from : matrix.keySet())
		{
			HashMap<Destination, Double> row = matrix.get(from);
			for(Destination to : row.keySet())
			{
				if(from != to)
				{
					if(new Random().nextDouble() <= row.get(to))
					{
						//AM > Generate vehicle
						Vehicle v = (Vehicle) vehicleType.newInstance();
						//v.setAcceleration(1);
						//v.setMax_velocity(10);
						v.setSource(from);
						v.setDestination(to);
						from.addVehicle(v);
					}
				}
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof SimulationClock)
		{
			try {
				generateVehicles();
			} catch (InstantiationException | IllegalAccessException
					| VehicleException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public List<Destination> getDestinations(){
		return destinations;
	}
}
