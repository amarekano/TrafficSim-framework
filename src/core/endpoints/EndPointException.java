package core.endpoints;

public class EndPointException extends Exception {
	private String message;
	
	public EndPointException(String message)
	{
		this.message = message;
	}
	
	public String getMessage()
	{
		return message;
	}
}
