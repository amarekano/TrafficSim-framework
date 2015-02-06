package core.network.junction;

public class InvalidInterfaceException extends Exception {

	private String message;
	
	public InvalidInterfaceException(String string) {
		this.message = string;
	}
	
	public String getMessage()
	{
		return message;
	}
}
