package core.network.junction;

public class InvalidJunctionConfigurationException extends Exception {
	private String message;
	
	public InvalidJunctionConfigurationException(String message)
	{
		this.message = message;
	}
	
	public String getMessage()
	{
		return message;
	}
}
