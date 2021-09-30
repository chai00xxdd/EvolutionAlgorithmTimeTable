package Utils;

import java.util.ArrayList;
import java.util.List;

public class TemplateFunctions {

	public static <T> T getRandomElement(T...elements)
	{
		int randomElementIndex=(int) (Math.random()*elements.length);
		return elements[randomElementIndex];
	}
	public static <T> T getRandomElement(List<T> elements)
	{
		if(elements.size()==0)
		{
			
			return null;
		}
		int randomElementIndex=(int) (Math.random()*elements.size());
		return elements.get(randomElementIndex);
	}
	public static <T> List<T> getRandomDiffrentElements(List<T>elements,int howMany)
	{
		List<T> elementsCopy =new ArrayList<>(elements);
		List<T> randomElements= new ArrayList<>();
		
		for(int i=0; i<howMany&&elementsCopy.size()>0; i++)
		{
			int randomIndex= (int)(Math.random()*elementsCopy.size());
			randomElements.add(elementsCopy.get(randomIndex));
			elementsCopy.remove(randomIndex);
		}
		
		return randomElements;
	}

	
	
}
