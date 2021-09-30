package evolution.timetable.crossover;

public enum CrossOverName {
DayTimeOriented,
AspectOriented;

public static CrossOverName getByName(String name)
{
	CrossOverName theCrossOverToFind=null;
  	for(CrossOverName crossoverName:values())
  	{
  		if(crossoverName.toString().toUpperCase().equals(name.toUpperCase()))
  		{
  			theCrossOverToFind = crossoverName;
  			break;
  		}
  	}
  	return theCrossOverToFind;
}

}


