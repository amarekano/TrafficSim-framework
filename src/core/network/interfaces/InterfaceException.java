package core.network.interfaces;

public class InterfaceException extends Exception {

	private String message;
	
	public InterfaceException(String string) {
		this.message = string;
	}
	
	public String getMessage()
	{
		return message;
	}
}
