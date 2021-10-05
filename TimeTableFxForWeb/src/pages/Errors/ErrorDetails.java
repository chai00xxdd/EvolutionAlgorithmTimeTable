package pages.Errors;

public class ErrorDetails {

	private int errorNumber;
	private String error;
	public ErrorDetails(int errorNumber, String error) {
		super();
		this.errorNumber = errorNumber;
		this.error = error;
	}
	public int getErrorNumber() {
		return errorNumber;
	}
	public void setErrorNumber(int errorNumber) {
		this.errorNumber = errorNumber;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	
}
