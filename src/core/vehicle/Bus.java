package core.vehicle;

public class Bus extends Vehicle 
{
	private int length;
	
	public Bus() {
		super();
		this.length=2;
	}
	
	public Bus(int velocity, int acceleration, int max_velocity) {
		//NC > for busses the length is 2
		super(velocity, acceleration, max_velocity);
		this.length=2;
		
	}
	
	public int getLength()
	{
		return length;
	}
	
}
