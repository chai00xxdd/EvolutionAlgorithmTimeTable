package Json;

import java.io.PrintWriter;

import com.google.gson.Gson;

public class JsonWriter {

	private PrintWriter writer;
	public JsonWriter(PrintWriter writer)
	{
		this.writer =writer;
	}
	
	public void writeJson(Object object)
	{
		writer.println(new Gson().toJson(object));
	}
}
