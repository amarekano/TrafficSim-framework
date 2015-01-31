package core;

public class Car {
	private int velocity;
	private int acceleration;
	
	public int getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(int acceleration) {
		this.acceleration = acceleration;
	}

	public Car()
	{
		this.velocity = 1;
		this.acceleration = 0;
	}
	
	public Car(int velocity)
	{
		if(velocity < 1)
		{
			this.velocity = 1;
			
		}
		else
		{
			this.velocity = velocity;
		}
		this.acceleration = 0;
	}
	
	public Car(int velocity, int acceleration)
	{
		if(velocity < 1)
		{
			this.velocity = 1;
			this.acceleration = 0;
		}
		else
		{
			this.velocity = velocity;
			this.acceleration = acceleration;
		}
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
