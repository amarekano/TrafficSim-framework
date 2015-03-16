package service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
	private static final String SEMICOLON_DELIMETER = ";";
	
	public ReportGenerator(){
		destinations=new ArrayList<Destination>();
		consumed_vehicles=new ArrayList<Vehicle>();
	}

	public void saveReport(String path){
		FileWriter fileWriter = null;
		
		try {
			fileWriter = new FileWriter(path,true);

			//Write the CSV file header

			fileWriter.append(""+new Timestamp((new Date().getTime())));
			fileWriter.write(System.getProperty("line.separator"));
			fileWriter.append(FILE_HEADER.toString());
			
			//Add a new line separator after the header
			fileWriter.write(System.getProperty("line.separator"));
			
			for(int i=0;i<destinations.size();i++){
				consumed_vehicles=destinations.get(i).getConsumedVehicle();
				
				//Write a new student object list to the CSV file
				for (Vehicle v : consumed_vehicles) {
					fileWriter.append(String.valueOf(v.getStartTime()));
					fileWriter.append(SEMICOLON_DELIMETER);
					fileWriter.append(String.valueOf(v.getEndTime()));
					fileWriter.append(SEMICOLON_DELIMETER);
					fileWriter.append(String.valueOf(v.getSource().getLabel()));
					fileWriter.append(SEMICOLON_DELIMETER);
					fileWriter.append(String.valueOf(v.getDestination().getLabel()));
					fileWriter.append(SEMICOLON_DELIMETER);
					if(consumed_vehicles.get(0) instanceof Car){
						fileWriter.append("Car");
					}
					else if(consumed_vehicles.get(0) instanceof Bus){
						fileWriter.append("Bus");
					}
					fileWriter.write(System.getProperty("line.separator"));
				}
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
