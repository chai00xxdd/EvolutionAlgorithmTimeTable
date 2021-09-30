package evolution.timetable.mutation;


import Utils.TemplateFunctions;
import evolution.timetable.TimeTableSolution;
import school.Course;
import school.Leacture;
import school.SchoolClass;
import school.Teacher;

public enum FlippingComponent {
	
DAY {
	@Override
	public void flip(TimeTableSolution dna, Leacture leacture) {
		
		int randomDay=(int)(Math.random()*dna.getDays());
		leacture.setDay(randomDay);
		
	}
},
HOUR {
	@Override
	public void flip(TimeTableSolution dna, Leacture leacture) {
		
		int randomHour=(int)(Math.random()*dna.getHours());
		leacture.setHour(randomHour);
	}
},
CLASS {
	@Override
	public void flip(TimeTableSolution dna, Leacture leacture) {
		
		 SchoolClass randomClass= TemplateFunctions.getRandomElement(dna.getClasses());
		 leacture.setClassToTeach(randomClass);
	}
},
TEACHER {
	@Override
	public void flip(TimeTableSolution dna, Leacture leacture) {
		
		 Teacher randomTeacher= TemplateFunctions.getRandomElement(dna.getTeachers());
		 leacture.setTeacher(randomTeacher);
		
	}
},
SUBJECT {
	@Override
	public void flip(TimeTableSolution dna,Leacture leacture) {
		
		 Course randomCourse= TemplateFunctions.getRandomElement(dna.getCourses());
		 leacture.setCourse(randomCourse);
		
	}
};

	public abstract void flip(TimeTableSolution dna,Leacture leacture);
	public static FlippingComponent getFlippingComponentByChar(String charString)
	{
		FlippingComponent flipingComponent=null;
		charString=charString.toUpperCase();
		switch(charString)
		{
		case "H":
			flipingComponent=HOUR;
			break;
		case "D":
			flipingComponent=DAY;
			break;
		case "T":
			flipingComponent=TEACHER;
			break;
		case "C":
			flipingComponent=CLASS;
			break;
		case "S":
			flipingComponent=SUBJECT;
			break;
			
			default:
				break;
			  
		}
		return flipingComponent;
	}
	
}
