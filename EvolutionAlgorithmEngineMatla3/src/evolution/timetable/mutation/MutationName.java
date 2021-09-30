package evolution.timetable.mutation;

public enum MutationName {

	Sizer,
	Flipping;
	
public static MutationName getByName(String name)
{
	for(MutationName mutationName : values())
	{
		if(mutationName.toString().toUpperCase().equals(name.toUpperCase()))
		{
			return mutationName;
		}
	}
	return null;
}
	
}
