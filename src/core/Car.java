package core;

public class Car {
	private int velocity;
	
	public Car()
	{
		this.velocity = 1;
	}
	
	public Car(int velocity)
	{
		if(velocity < 1)
			this.velocity = 1;
		else
			this.velocity = velocity;
	}
	
	public int getVelocity()
	{
		return velocity;
	}
	
	public void setVelocity(int velocity)
	{
		this.velocity = velocity;
	}
}
