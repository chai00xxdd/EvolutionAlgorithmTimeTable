package school;

public class CourseRequirment {
	
	private int courseId;
	private String courseName;
	private int hoursRequired;
	
	
	public CourseRequirment(int courseId, String courseName, int hoursRequired) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.hoursRequired = hoursRequired;
	}
	public CourseRequirment(int id, String name, Integer value) {
		// TODO Auto-generated constructor stub
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public int getHoursRequired() {
		return hoursRequired;
	}
	public void setHoursRequired(int hoursRequired) {
		this.hoursRequired = hoursRequired;
	}
	
	

}
