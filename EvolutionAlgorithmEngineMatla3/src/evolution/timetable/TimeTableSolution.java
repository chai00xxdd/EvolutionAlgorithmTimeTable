package evolution.timetable;

import java.util.ArrayList;
import java.util.List;

import Utils.TemplateFunctions;
import evolution.algorithm.DNA;
import evolution.algorithm.DnaComponent;
import evolution.timetable.rule.Rule;
import evolution.timetable.rule.RuleResult;
import school.Course;
import school.Leacture;
import school.RawLeacture;
import school.School;
import school.SchoolClass;
import school.Teacher;

public class TimeTableSolution implements DNA
{
	private double fitness=0;
	private List<Teacher>teachers=new ArrayList<>();
	private List<Course> courses=new ArrayList<>();
	private List<SchoolClass> classes=new ArrayList<>();
	private List<Rule> rules=new ArrayList<>();
	private List<Leacture> leactures=new ArrayList<>();
	private List<RuleResult> rulesResults = new ArrayList<>();
	private int days;
	private int hours;
	private int howManyLeacturesNeeded=0;
	private double maximumScorePossibleFromRules=0;
	private double averageHardRules=0;
	private double averageSoftRules=0;
	public TimeTableSolution() {}
	public TimeTableSolution(TimeTableSolution other)
	{
		
		this(other.days,other.hours,new ArrayList<>(other.getTeachers()),new ArrayList<>(other.getCourses()),
				new ArrayList<>(other.getClasses()),new ArrayList<>(other.getRules()));
		this.fitness=other.fitness;
		this.leactures=new ArrayList<>(other.leactures);
		this.howManyLeacturesNeeded=other.howManyLeacturesNeeded;
			
	}
	

	public TimeTableSolution(int days,int hours,List<Teacher> teachers,List<Course> courses,List<SchoolClass> classes,List<Rule> rules)
	{
		
		this.teachers=teachers;
		this.courses=courses;
		this.classes=classes;
		this.rules=rules;
		this.days=days;
		this.hours=hours;
		for(SchoolClass sclass: classes)
		{
			for(Integer howManyHoursOfLeacturesSclassNeeds : sclass.getCoursHoursMap().values())
			{
				howManyLeacturesNeeded+=howManyHoursOfLeacturesSclassNeeds;
			}
		}
	}
	public TimeTableSolution(School school,List<Rule> rules,int days,int hours)
	{
       this(days,hours,school.getTeachers(),school.getCourses(),school.getClasses(),rules);
	}
	public double calculateFitness()
	{
		 
		//fitness= new RulesCalculator().calculate(this);
		fitness=calc2();
		return fitness;
				                            
	}
	private double calc2()
	{
		fitness=0;
		averageHardRules=averageSoftRules=0;
		maximumScorePossibleFromRules=0;
		if(rules.size()==0)
		{
			fitness=1;
			return fitness;
		}
		rulesResults.clear();
		int hardRulesCounter=0;
		int softRulesCounter=0;
		for(Rule rule:rules)
		{
			
			RuleResult ruleResult= rule.applyOnDNA(this);
			ruleResult.setName(rule.toString());
			ruleResult.setType(rule.getType());
			rulesResults.add(ruleResult);
			fitness+=ruleResult.getWeightedScore();
			maximumScorePossibleFromRules+=ruleResult.getWeight();
			switch(rule.getType())
			{
			case HARD:
				hardRulesCounter++;
				averageHardRules+=ruleResult.getScore();
				break;
			case SOFT:
				softRulesCounter++;
				averageSoftRules+=ruleResult.getScore();
				break;
			}
		}
		
		if(softRulesCounter!=0)
		{
			averageSoftRules/=softRulesCounter;
		}
		if(hardRulesCounter!=0)
		{
			averageHardRules/=hardRulesCounter;
		}
		double dividByMaxScore=maximumScorePossibleFromRules;
		if(maximumScorePossibleFromRules==0)
			
		{
			dividByMaxScore+=1;
		}
		fitness/=dividByMaxScore;
		
		for(RuleResult ruleResult : rulesResults)
		{
			ruleResult.setEffectOnFitness(ruleResult.getWeightedScore()/dividByMaxScore);
			ruleResult.setMaxEffectOnFitness(ruleResult.getWeight()/dividByMaxScore);
		}
		
		return fitness;
	}
	
	

	//@Override
	public int compareTo(TimeTableSolution o) {
		// TODO Auto-generated method stub
		if(fitness<o.fitness)
			return -1;
		else if(fitness==o.fitness)
			return 0;
		return 1;
	}
	public TimeTableSolution shallowCloneWithOutLeactures ()
	{
		return new TimeTableSolution(days,hours,teachers,courses,classes,rules);
		
	}
	public Leacture createNotNullRandomLeacture()
	{
		int day=(int) (Math.random()*days);
		int hour=(int) (Math.random()*hours);
		Teacher randomTeacher= TemplateFunctions.getRandomElement(teachers);
		Course randomCourse= TemplateFunctions.getRandomElement(courses);
		SchoolClass randomClass= TemplateFunctions.getRandomElement(classes);
		return new Leacture(randomTeacher, randomCourse, randomClass, day, hour);
	}
	public TimeTableSolution createRandomDNA()
	{
		TimeTableSolution randomDna=new TimeTableSolution(days,hours,teachers, courses, classes, rules);
		int howManyLeacturesToAdd=(int)(Math.random()*howManyLeacturesNeeded)+1;
		howManyLeacturesToAdd=howManyLeacturesNeeded;
		
		for(int i=0; i<howManyLeacturesToAdd; i++)
		{
			randomDna.getLeactures().add(createNotNullRandomLeacture());
			
		}
		return randomDna;
	}
	public List<Leacture> getLeactures() {
		return leactures;
	}
	public void setLeactures(List<Leacture> leactures) {
		this.leactures = leactures;
	}
	public double getFitness() {
		return fitness;
	}
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	public List<Teacher> getTeachers() {
		return new ArrayList<>(teachers);
	}
	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}
	public List<Course> getCourses() {
		return new ArrayList<>(courses);
	}
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	public List<SchoolClass> getClasses() {
		return new ArrayList<>(classes);
	}
	public void setClasses(List<SchoolClass> classes) {
		this.classes = classes;
	}
	public List<Rule> getRules() {
		return new ArrayList<>(rules);
	}
	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public int getHours() {
		return hours;
	}
	
	public List<RuleResult> getRulesResults() {
		return new ArrayList<>(rulesResults);
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public double getMaximumScorePossibleFromRules() {
		return maximumScorePossibleFromRules;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<DnaComponent> getChromoson() {
		return(List<DnaComponent>)(List<? extends DnaComponent>)leactures;
		
	}
	@Override
	public DNA duplicate() {
		 TimeTableSolution duplicateThis=new TimeTableSolution(days,hours,teachers,courses,classes,rules);
		 for(Leacture leacture:leactures)
		 {
			 duplicateThis.getLeactures().add(leacture.shallowClone());
		 }
		 duplicateThis.rulesResults= new ArrayList<>(rulesResults);
		 duplicateThis.setFitness(fitness);
		 duplicateThis.maximumScorePossibleFromRules=maximumScorePossibleFromRules;
		 duplicateThis.averageHardRules=averageHardRules;
		 duplicateThis.averageSoftRules=averageSoftRules;
		 return duplicateThis;
				 
	}
	@Override
	public DNA createEmptyDNA() {
		// TODO Auto-generated method stub
		return shallowCloneWithOutLeactures();
		
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + days;
		result = prime * result + hours;
		result = prime * result + ((leactures == null) ? 0 : leactures.hashCode());
		return result;
	}
	
	public List<Leacture>[][] getTeacherShecdule(Teacher teacher)
	{
		List<Leacture>[][] teacherShecdule=createEmptyShecdule();
		for(Leacture leacture:getLeactures())
		{
			int day=leacture.getDay();
			int hour=leacture.getHour();
			if(leacture.getTeacher().equals(teacher))
			{
				teacherShecdule[day][hour].add(leacture);
			}
		}
		
		return teacherShecdule;
	}
	
	public List<Leacture>[][]getCourseShecdule(Course course)
	{
		List<Leacture>[][] courseShecdule=createEmptyShecdule();
		for(Leacture leacture:getLeactures())
		{
			int day=leacture.getDay();
			int hour=leacture.getHour();
			if(leacture.getCourse().equals(course))
			{
				courseShecdule[day][hour].add(leacture);
			}
		}
		
		return courseShecdule;
	}
	
	public List<Leacture>[][] getClassShecdule(SchoolClass sclass)
	{
		List<Leacture>[][]  classShecdule=createEmptyShecdule();
		for(Leacture leacture:getLeactures())
		{
			int day=leacture.getDay();
			int hour=leacture.getHour();
			if(leacture.getClassToTeach().equals(sclass))
			{
				classShecdule[day][hour].add(leacture);
			}
		}
		
		return classShecdule;
	}
	private List<Leacture> [] [] createEmptyShecdule()
	{
		List<Leacture> [] [] emptyShecdule = new ArrayList [days][hours];
		for(int i=0; i<days; i++)
		{
			for(int j=0; j<hours; j++)
			{
				emptyShecdule [i][j]=new ArrayList<>();
			}
		}
		return emptyShecdule;
	}
	
	public List<Leacture>[][] getLeactuersShecdule()
	{
		List<Leacture>[][]leacturesShecdule= createEmptyShecdule();
		for(Leacture leacture:leactures)
		{
			int day=leacture.getDay();
			int hour=leacture.getHour();
			leacturesShecdule[day][hour].add(leacture);
		}
		
		return leacturesShecdule;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TimeTableSolution other = (TimeTableSolution) obj;
		if (days != other.days)
			return false;
		if (hours != other.hours)
			return false;
		if (leactures == null) {
			if (other.leactures != null)
				return false;
		} else if (!leactures.equals(other.leactures))
			return false;
		return true;
	}
	public double getAverageHardRules() {
		return averageHardRules;
	}
	public double getAverageSoftRules() {
		return averageSoftRules;
	}
	public int getHowManyLeacturesNeeded() {
		return howManyLeacturesNeeded;
	}
	
	public Teacher getTeacherById(int teacherId)
	{
		try
		{
			return teachers.stream().filter(teacher -> teacher.getId() == teacherId).findFirst().get();
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	public SchoolClass getClassById(int classId)
	{
		try
		{
			return classes.stream().filter(sclass -> sclass.getId() == classId).findFirst().get();
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	
	
	
	
	
}
	
	
	
	
