package evolution.timetable.crossover;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import evolution.timetable.TimeTableSolution;
import school.Leacture;

public enum AspectOrientation {
 CLASS {
	@Override
public List<List<Leacture>> orientation(TimeTableSolution solution) {
		
		List<List<Leacture>>aspectedLeactures=createListOfListsOfLeactures(solution.getTeachers().size());
		List<Leacture> leactures=solution.getLeactures();
		
		Collections.sort(leactures,new Comparator<Leacture>() {

			@Override
			public int compare(Leacture o1, Leacture o2) {
				// TODO Auto-generated method stub
				return o1.getClassToTeach().getId()-o2.getClassToTeach().getId();
			}
		});
		for(Leacture leacture:leactures)
		{
			int classLeacturesIndex=leacture.getClassToTeach().getId()-1;
			aspectedLeactures.get(classLeacturesIndex).add(leacture);
		}
		return aspectedLeactures;
	}
},
 TEACHER {
	@Override
	public List<List<Leacture>> orientation(TimeTableSolution solution) {
		
		List<List<Leacture>>aspectedLeactures=createListOfListsOfLeactures(solution.getTeachers().size());
		List<Leacture> leactures=solution.getLeactures();
		Collections.sort(leactures,new Comparator<Leacture>() {

			@Override
			public int compare(Leacture o1, Leacture o2) {
				// TODO Auto-generated method stub
				return o1.getTeacher().getId()-o2.getTeacher().getId();
			}
		});
		for(Leacture leacture:leactures)
		{
			int teacherLeacturesIndex=leacture.getTeacher().getId()-1;
			aspectedLeactures.get(teacherLeacturesIndex).add(leacture);
		}
		return aspectedLeactures;
		
	}
};
 
 private static Map<String,AspectOrientation> aspectsMap = new HashMap<>();
 static
 {
	 for(AspectOrientation aspect : values())
	 {
		 aspectsMap.put(aspect.name(), aspect);
	 }
 }
 
 public abstract List<List<Leacture>> orientation(TimeTableSolution solution);
 
 protected List<List<Leacture>> createListOfListsOfLeactures(int size)
 {
	 List<List<Leacture>> emptyAspectedLeactures=new ArrayList<>();
	 for(int i=0; i<size; i++)
	 {
		 emptyAspectedLeactures.add(new ArrayList<Leacture>());
	 }
	 return emptyAspectedLeactures;
 }
 
 public static AspectOrientation getAspectByName(String aspectName)
 {
	 return aspectsMap.get(aspectName.toUpperCase());
 }
 
 
}
