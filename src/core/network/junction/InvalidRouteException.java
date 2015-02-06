package core.network.junction;

public class InvalidRouteException extends Exception {
	private String message;
	
	public InvalidRouteException(String message)
	{
		this.message = message;
	}
	
	public String getMessage()
	{
		return message;
	}
}
