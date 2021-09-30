package school;

public class RawLeacture {
	
	private int day;
	private int hour;
	private String teacherId;
	private String classId;
	private String subjectId;
	
	
	public RawLeacture()
	{
		
	}
	public RawLeacture(Leacture leacture)
	{
		setDay(leacture.getDay());
		setHour(leacture.getHour());
		setTeacherId(leacture.getTeacher().getId()+"");
		setClassId(leacture.getClassToTeach().getId()+"");
		setSubjectId(leacture.getCourse().getId()+"");
		
	}
	
	public RawLeacture(int day, int hour, String teacherId, String classId, String subjectId) {
		super();
		this.day = day;
		this.hour = hour;
		this.teacherId = teacherId;
		this.classId = classId;
		this.subjectId = subjectId;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	
	

}
