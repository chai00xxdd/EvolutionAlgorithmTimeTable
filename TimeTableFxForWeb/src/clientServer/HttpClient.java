package clientServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sun.corba.se.pept.transport.Connection;

import javafx.util.Pair;

public class HttpClient {
	
	private HttpMethod  DEFAULT_HTTP_METHOD = HttpMethod.GET;
	private URL serverUrl = null;
	private Map<String,String> parameters = new HashMap<>();
	private HttpMethod method = DEFAULT_HTTP_METHOD;
	private  HttpURLConnection connection = null;
	private String serverUrlString = null;
	private String cookieString = "";
	private static final String COOKIE = "Cookie";
	private static final String CHARSET = "UTF-8";
	private static final String LINE_FEED = "\r\n";
	private static final int DEFAULT_TIMEOUT = 0;
	private int timeOutInMs = DEFAULT_TIMEOUT;
	
    public HttpMethod getHttpMethod(){
      return method;
    }
    public void setHttpMethod(HttpMethod method)
    {
    	this.method = method;
    	
    }
    
    
    
    public int getTimeOutInMs() {
		return timeOutInMs;
	}
	public void setTimeOutInMs(int timeOutInMs) {
		if(timeOutInMs < 0)
		{
			return;
		}
		this.timeOutInMs = timeOutInMs;
	}
	Map<String, List<String>> getHeaderFields()
    {
    	return connection.getHeaderFields();
    }
    
    public void setCookies(CookieManager cookieManager)
    {
         List<HttpCookie> cookies =  cookieManager.getCookieStore().getCookies();
         if(cookies.size() > 0)
         {
        	 String cookieString = "";
        	 boolean first = true;
        	 for(HttpCookie cookie : cookies)
        	 {
        		 if(!first)
        		 {
        			 cookieString+=";";
        		 }
        		cookieString += cookie.toString().replace("[","").replace("]", "");
        		
        		first = false;
        		
        	 }
        	 this.cookieString = cookieString;
        	
         }
    }
   
	public HttpClient(String serverUrlString) throws MalformedURLException
	{
		new URL(serverUrlString);// check url is not malformed
		this.serverUrlString = serverUrlString;
		
		
	}
	
	public void addParameter(String parameter,String value)
	{
		parameters.put(parameter, value);
	}
	
	public void sendMultiPartsFile(String fieldName,File fileToUpload) throws Exception
	{
		if(connection != null)
		{
			connection.disconnect();
		}
		serverUrl = new URL(createFullUrlString());
		connection = (HttpURLConnection)serverUrl.openConnection();
		connection.setRequestMethod(method.toString());
		String boundary = "===" + System.currentTimeMillis() + "===";
		connection.setUseCaches(false);
		connection.setDoOutput(true); // indicates POST method
		connection.setDoInput(true);
		connection.setRequestProperty("Content-Type",
                "multipart/form-data; boundary=" + boundary);
		
		if(cookieString.equals("") == false)
		{
			//System.out.println(cookieString);
		  	 connection.setRequestProperty(COOKIE, cookieString);
		}
		
		OutputStream  outputStream = connection.getOutputStream();
		PrintWriter  writer = new PrintWriter(new OutputStreamWriter(outputStream, CHARSET),
                true);
		
		addFilePart(fieldName,fileToUpload,writer,boundary,outputStream);
		
		writer.append(LINE_FEED).flush();
        writer.append("--" + boundary + "--").append(LINE_FEED);
        writer.close();
        
        connection.getResponseCode(); //invoke the http request
    	
		if(getErrorInputStream() != null)
		{
			throw new ServerErrorException(getErrorString());
		}
        
        
	
	}
	
	 private void addFilePart(String fieldName, File uploadFile,PrintWriter writer,String boundary,OutputStream outputStream)
	            throws IOException {
		
	        String fileName = uploadFile.getName();
	        writer.append("--" + boundary).append(LINE_FEED);
	        writer.append(
	                "Content-Disposition: form-data; name=\"" + fieldName
	                        + "\"; filename=\"" + fileName + "\"")
	                .append(LINE_FEED);
	        writer.append(
	                "Content-Type: "
	                        + URLConnection.guessContentTypeFromName(fileName))
	                .append(LINE_FEED);
	        writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
	        writer.append(LINE_FEED);
	        writer.flush();
	 
	        FileInputStream inputStream = new FileInputStream(uploadFile);
	        byte[] buffer = new byte[4096];
	        int bytesRead = -1;
	        while ((bytesRead = inputStream.read(buffer)) != -1) {
	            outputStream.write(buffer, 0, bytesRead);
	        }
	        outputStream.flush();
	        inputStream.close();
	         
	        writer.append(LINE_FEED);
	        writer.flush();    
	    }
	
	public List<Pair<String,String>> getParameters()
	{
		List<Pair<String,String>> parametersList = new ArrayList<>();
		for(Entry<String,String> parameter : parameters.entrySet())
		{
			parametersList.add(new Pair(parameter.getKey(),parameter.getValue()));
		}
		
		return parametersList;
	}
	
	public void removeParameter(String paremter)
	{
		parameters.remove(paremter);
	}
	
	public int getResponseCode() throws IOException
	{
		return connection.getResponseCode();
	}
	
	
	
	public InputStream getResponseInputStream() throws IOException
	{
		
		return connection.getInputStream();
	}
	
	public InputStream getErrorInputStream()
	{
		return connection.getErrorStream();
	}
	
	public String getErrorString() throws IOException
	{
		BufferedReader responseReader = new BufferedReader(new InputStreamReader(getErrorInputStream()));
		return responseReader.readLine();
	}
	
	public String getResponseString() throws IOException
	{
		BufferedReader responseReader = new BufferedReader(new InputStreamReader(getResponseInputStream()));
		return responseReader.readLine();
		
	}
	
	
	
	private String createFullUrlString()
	{
		String fullUrl = serverUrlString;
		if(parameters.size() != 0)
		{
			fullUrl+="?";
			boolean first = true;
			for(Entry<String, String> parameter : parameters.entrySet())
			{
				if(!first)
				{
				
					fullUrl +="&";
				}
				
				fullUrl += parameter.getKey().replace(" ", "%20")+"="+parameter.getValue().replace(" ", "%20");
				first = false;
			}
		}
		
		return fullUrl;
	}
	
	
	
	public void sendRequest() throws ConnectException, ServerErrorException,IOException
	{
		
		if(connection != null)
		{
			connection.disconnect();
		}
		serverUrl = new URL(createFullUrlString());
		connection = (HttpURLConnection)serverUrl.openConnection();
		connection.setRequestMethod(method.toString());
		connection.setConnectTimeout(timeOutInMs);
	
		
		if(cookieString.equals("") == false)
		{
			//System.out.println(cookieString);
		  	 connection.setRequestProperty(COOKIE, cookieString);
		}
		
		connection.getResponseCode(); //invoke the http request
	
		if(getErrorInputStream() != null)
		{
			throw new ServerErrorException(getErrorString());
		}
		
		
		
	}

}
