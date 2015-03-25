package service;

import java.io.FileWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import core.endpoints.Destination;
import core.vehicle.Bus;
import core.vehicle.Car;
import core.vehicle.Vehicle;

public class ReportGenerator {
	
	private List<Destination> destinations;
	private List<Vehicle> consumed_vehicles;
	
	private static final String FILE_HEADER = "Start Time;End Time;Source;Destination;Type";
	
	public ReportGenerator(){
		destinations=new ArrayList<Destination>();
		consumed_vehicles=new ArrayList<Vehicle>();
	}

	public void saveReport(String path){
		FileWriter fileWriter = null;
		
		try {
			fileWriter = new FileWriter(path);

			//Write the CSV file header

			fileWriter.append(""+new Timestamp((new Date().getTime())));
			fileWriter.write(System.getProperty("line.separator"));
			fileWriter.append(FILE_HEADER.toString());
			
			//Add a new line separator after the header
			fileWriter.write(System.getProperty("line.separator"));
			for(Destination d : destinations)
			{
			
				consumed_vehicles.addAll(d.getConsumedVehicles());
				d.clearConsumedQueue();
			}
				//Write a new student object list to the CSV file
				for (Vehicle v : consumed_vehicles) {
					String line = "";
					if(v instanceof Car){
					//	fileWriter.append("Car");
						line =  String.format("%s;%s;%s;%s;%s\n",v.getStartTime(),v.getEndTime(),v.getSource().getLabel(),v.getDestination().getLabel(),"Car");
					}
					else if(v instanceof Bus){
						//fileWriter.append("Bus");
						line =  String.format("%s;%s;%s;%s;%s\n",v.getStartTime(),v.getEndTime(),v.getSource().getLabel(),v.getDestination().getLabel(),"Bus");
					}
					fileWriter.write(line);
				}
			fileWriter.flush();
			fileWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void addDestination(Destination destination){
		if(!destinations.contains(destination)){
			destinations.add(destination);
		}
	}
	
	public int getConsumedVehiclesLength(){
		return consumed_vehicles.size();
	}

}
