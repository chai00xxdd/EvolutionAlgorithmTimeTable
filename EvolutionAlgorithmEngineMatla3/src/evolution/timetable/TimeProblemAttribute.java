package evolution.timetable;

public class TimeProblemAttribute {

	private String attribute;
	private String value;
	
	
	public TimeProblemAttribute(String attribute, String value) {
		super();
		this.attribute = attribute;
		this.value = value;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
