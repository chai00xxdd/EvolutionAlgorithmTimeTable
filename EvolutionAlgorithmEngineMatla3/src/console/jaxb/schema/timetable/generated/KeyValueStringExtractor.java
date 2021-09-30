package console.jaxb.schema.timetable.generated;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;



public class KeyValueStringExtractor {
	
	
	Map<String,String>keyValueMap=new HashMap<>();
	public KeyValueStringExtractor()
	{
		
	}
	public KeyValueStringExtractor(String keyValueString)
	{
		extractKeyAndValue(keyValueString);
	}
	public KeyValueStringExtractor(Collection<String>keyValueStrings)
	{
		extractKeyAndValue(keyValueStrings);
	}
	
	public void clear()
	{
		keyValueMap.clear();
	}
	public void extractKeyAndValue(String keyValueString)
	{
		if(keyValueString==null)
			return;
		String[] splitByEqualSign=keyValueString.split("[=,]");
		for(int i=0; i<splitByEqualSign.length-1; i+=2)
		{
			String key=splitByEqualSign[i].toUpperCase();
			String value=splitByEqualSign[i+1];
			keyValueMap.put(key,value);
		}
		
	}
	public void extractKeyAndValue(Collection<String> keyValueStrings)
	{
	   for(String keyValueString:keyValueStrings)
	   {
		  // System.out.println(keyValueString);
		   extractKeyAndValue(keyValueString);
	   }
	}

	
	public String getValue(String key)
	{
		
		return keyValueMap.get(key.toUpperCase());
	}
	
	public  Set<String> getKeys(){
		return keyValueMap.keySet();
	}
	public Collection<String> getValues(){
		return keyValueMap.values();
	}
	
	
	

}
