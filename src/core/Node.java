package core;

public class Node {

	private boolean isOccupied;
	private Car car;
	
	public Node()
	{
		isOccupied = false;
		car = null;
	}

	public boolean isOccupied() {
		return isOccupied;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}
}
