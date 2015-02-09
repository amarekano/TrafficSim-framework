package core.network.junction;

public class JunctionException extends Exception {
	private String message;
	
	public JunctionException(String message)
	{
		this.message = message;
	}
	
	public String getMessage()
	{
		return message;
	}
}
