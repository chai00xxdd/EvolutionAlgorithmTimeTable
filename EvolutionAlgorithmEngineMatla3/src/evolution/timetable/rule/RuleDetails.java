package evolution.timetable.rule;

public class RuleDetails {
	
	private String name;
	private RuleType type;
	private String paremeters = "";
	
	
	
	
	
	public RuleDetails(String name, RuleType type) {
		super();
		this.name = name;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public RuleType getType() {
		return type;
	}
	public void setType(RuleType type) {
		this.type = type;
	}
	public String getParemeters() {
		return paremeters;
	}
	public void setParemeters(String paremeters) {
		this.paremeters = paremeters;
	}
	
	
	
	
	

}
