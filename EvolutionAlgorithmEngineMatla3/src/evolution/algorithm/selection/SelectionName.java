package evolution.algorithm.selection;

import javax.naming.NameNotFoundException;

public enum SelectionName {
  Truncation,
  RouletteWheel,
  Tournemant;
	
public static SelectionName getByName(String name) 
{
	for(SelectionName selectionName : values())
	{
		if(selectionName.toString().toUpperCase().equals(name.toUpperCase()))
		{
			return selectionName;
		}
	}
	
	return null;
}

}
