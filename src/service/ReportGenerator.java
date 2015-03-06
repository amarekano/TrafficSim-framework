package service;

import java.util.List;

import core.endpoints.Destination;
import core.vehicle.Vehicle;

public class ReportGenerator {
	
	private List<Vehicle> consumed_vehicles;
	
	public ReportGenerator(){
		
	}
	
	public void generateReport(){
		
	}
	
	public void saveReport(){
		
	}
	
	public void addConsumedVehicles(Destination destination){
		List <Vehicle> vehicles=destination.getConsumedVehicle();
		for(Vehicle v:vehicles){
			if(!consumed_vehicles.contains(v)){
				consumed_vehicles.add(v);
			}	
		}
		
	}

}
