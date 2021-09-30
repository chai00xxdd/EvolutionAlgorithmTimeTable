package DTO;

import java.util.ArrayList;
import java.util.List;

import school.Leacture;

public class ShecduleDTO {
	
	private String name;
	private String id;
	private int days;
	private int hours;
	List<LeactureDTO> [] [] leactures;
	
	public ShecduleDTO (String name,String id,List<Leacture>[][] leacturesOfSomeone)
	{
		this.name = name;
		this.id = id;
		
		days = leacturesOfSomeone.length;
		hours = leacturesOfSomeone[0].length;
		
		leactures = createEmptyShecdule(days, hours);
		for(int day=0; day<days; day++)
		{
			for(int hour =0; hour<hours; hour++)
			{
				for(Leacture leacture : leacturesOfSomeone[day][hour])
				{
			        leactures[day][hour].add(new LeactureDTO(leacture));
				}
			}
		}
	}
	
	
	private List<LeactureDTO>[][]  createEmptyShecdule(int days,int hours)
	{
		List<LeactureDTO> [] [] emptyShecdule = new ArrayList [days][hours];
		for(int i=0; i<days; i++)
		{
			for(int j=0; j<hours; j++)
			{
				emptyShecdule [i][j]=new ArrayList<>();
			}
		}
		return emptyShecdule;
	}

}
