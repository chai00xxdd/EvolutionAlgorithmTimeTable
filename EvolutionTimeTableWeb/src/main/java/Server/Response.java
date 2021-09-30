package Server;

import java.util.List;

public class Response {
	
	private String error = "";
	private Object data = null;
	
	public static Response createErrorReponse(String error)
	{
		Response errorResponse = new  Response(null);
		errorResponse.setError(error);
		
		return errorResponse;
	}
	
	public Response(Object object)
	{
		this.data = object;
	}

	public Response(String error, Object data) {
		super();
		this.error = error;
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
	
	

}
