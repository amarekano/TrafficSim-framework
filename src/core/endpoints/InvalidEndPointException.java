package core.endpoints;

public class InvalidEndPointException extends Exception {
	private String message;
	
	public InvalidEndPointException(String message)
	{
		this.message = message;
	}
	
	public String getMessage()
	{
		return message;
	}
}
