package Utils;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Json.JsonWriter;
import Server.ServerManager;
import Server.TimeTableWrapper;
import ServerDTO.UserAlgorithmProgress;

public class ServerUtils {
	private static final int ErrorCode = 401;
	public static void sendError(HttpServletResponse response,String error) throws IOException
	{
		response.setStatus(ErrorCode);
		response.getWriter().println(error);
	}
	
	public static void sendErrors(HttpServletResponse response,List<String> errors) throws IOException
	{
		response.setStatus(ErrorCode);
		response.getWriter().println(new Gson().toJson(errors));
	
	}
	
	public static String getUserName(HttpServletRequest request)
	{
		return (String) request.getSession(false).getAttribute(Attributes.Username);
	}
	
	public static void sendErrorObject(HttpServletResponse response,Object errorObject) throws IOException
	{
		response.setStatus(ErrorCode);
		response.getWriter().println(new Gson().toJson(errorObject));
		
	}
	
	public static ServerManager getServerManager(ServletContext servletContext)
	{
		return (ServerManager) servletContext.getAttribute(Attributes.ServerManager);
	}
	
	public static void writeJson(HttpServletResponse response, Object objectToConvertToJson) throws IOException
	{
		response.setContentType(Attributes.Json);
		new JsonWriter(response.getWriter()).writeJson(objectToConvertToJson);
		
	}
	
	public static boolean isLoggedIn(HttpServletRequest request)
	{
		return request.getSession(false) != null;
	}

	public static String getProblemId(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return request.getParameter(Attributes.Id);
	}
	
	public static String getParameter(HttpServletRequest request)
	{
		return request.getParameter(Attributes.Parameter);
	}
	
	

}
