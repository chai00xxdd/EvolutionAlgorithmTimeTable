package console.jaxb.schema.timetable;

import java.util.List;
import java.util.Map;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import Utils.Entity;
import console.jaxb.schema.timetable.generated.*;
import evolution.timetable.TimeTableProblem;
import evolution.timetable.rule.*;
import school.*;



public class XmlApiTimeTable {
	private static final String END_OF_XML_FILE=".xml";
	private static final int MINIMUM_LENGTH_OF_XML_FILE=5;
	private List<String >allErrors=new ArrayList<String>();
	private final static String JAXB_XML_GAME_PACKAGE_NAME = "console.jaxb.schema.timetable.generated";
	private int minimumPopulationSize=1;
	private int hardRuleWeight=0;
   ETTDescriptor timeTableDescriptor=null;
	public XmlApiTimeTable () {
		//default
	}
	
	public XmlApiTimeTable(int minimumPopulationSize) {
		super();
		this.minimumPopulationSize = minimumPopulationSize;
	}
	
	private TimeTableProblem getTimeTableProblem(ETTTimeTable timeTableProblemData)
	{
		int days= timeTableDescriptor.getETTTimeTable().getDays();
		int hours=timeTableDescriptor.getETTTimeTable().getHours();
		if(days<=0)
		{
		allErrors.add("days must be positive!!!");	
		}
		if(hours<=0)
		{
			allErrors.add("hours must be positive!!!!");
		}
		School school=createSchoolFromTimeTable(timeTableProblemData);
		
		List<Rule> rules= getRules(timeTableProblemData.getETTRules());
		
		return  new TimeTableProblem(school,days,hours,rules,hardRuleWeight);
	}
	public TimeTableProblem loadTimeTableProblem(String xmlFileName) throws Exception
	{
		TimeTableProblem timeTableProblem=null;
		checkValidationOfXmlFileName(xmlFileName);
		InputStream inputStream =null;
		
		try
		{
			
		 inputStream = new FileInputStream(xmlFileName);
		timeTableDescriptor=deserializeFrom(inputStream);
		 timeTableProblem=getTimeTableProblem(timeTableDescriptor.getETTTimeTable());
	
		}
		
		catch(FileNotFoundException e)
		{
			throw new Exception("file "+xmlFileName+" doesnt exists!");
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			
			throw new Exception("failed to deserialize file!!!");
		}
		catch(NumberFormatException e)
		{
			
			allErrors.add("one of the configuration value errors "+e.getMessage());
			
		}
		finally {
			if(inputStream != null)
				inputStream.close();
		}
		
		if(allErrors.size()>0)
		{
			
			throw new Exception("unvalid xml file!!!");
		}
		
		
		return timeTableProblem;
	}

	private School createSchoolFromTimeTable(ETTTimeTable timeTable)
	{
		
		List<Course> courses= getCourses(timeTable.getETTSubjects());
		List<Teacher> teachers=getTeachers(timeTable.getETTTeachers(),courses);
		List<SchoolClass> classes= getClasses(timeTable.getETTClasses(), courses);
		return new School(courses, teachers, classes);
	  
	}
	private List<Rule> getRules(ETTRules rulesData)
	{
		
		try
		{
		 hardRuleWeight=rulesData.getHardRulesWeight();
		if(hardRuleWeight<0||hardRuleWeight>100)
			allErrors.add("hard-rules-weight must be integer in range [0,100]");
		}
		catch(Exception notANumber)
		{
			allErrors.add("hard-rules-weight must be integer in range [0,100]");
		}
		double softRuleWeight=100-hardRuleWeight;
		Map<String,Integer> rulesCountingMap=new HashMap<>();
		List<Rule>rules=new ArrayList<Rule>();
		for(ETTRule ettRule : rulesData.getETTRule())
		{
			RuleType ruleType=null;
			Integer conuntOfRule= rulesCountingMap.getOrDefault(ettRule.getETTRuleId(),0);
			conuntOfRule++;
			 if(conuntOfRule==2)//first duplication encountred
				{
					allErrors.add("rule "+ettRule.getETTRuleId()+" is duplicated");
					
				}
			 rulesCountingMap.put(ettRule.getETTRuleId(), conuntOfRule);
						
			for(RuleType type : RuleType.values())
			{
				if(type.name().equals(ettRule.getType().toUpperCase()))
				{
					ruleType=type;
					break;
				}
			}
			if(ruleType==null)
			{
				allErrors.add(ettRule.getType()+" is unvalid rule type need to be soft/hard");
			}
			double ruleWeight = ruleType==RuleType.HARD?hardRuleWeight:softRuleWeight;
			String ruleNameString=ettRule.getETTRuleId();
			RuleName ruleName = RuleName.getRuleByName(ruleNameString);
			if(ruleName != null)
			{
			switch(ruleName)
			{
			case TEACHER_IS_HUMAN:
				rules.add(new TeacherIsHumanRule(ruleWeight, ruleType));
				break;
			case SINGULARITY:
				rules.add(new SingualrityRule(ruleWeight, ruleType));
				break;
			case KNOWLEDGEABLE:
				rules.add(new KnoledgableRule(ruleWeight, ruleType));
				break;
			case SATISFACTORY:
				rules.add(new SatisFactory(ruleWeight,ruleType));
				break;
			case DAYOFFTEACHER:
				rules.add(new DayOffTeacherRule(ruleWeight,ruleType));
				break;
			case SEQUENTIALITY:
				rules.add(getSequentalityRule(ettRule, softRuleWeight, ruleType));
				break;
			case DayOffClass:
				rules.add(new DayOffClassRule(ruleWeight, ruleType));
				break;
			case WorkingHoursPreference:
				rules.add(new WorkingHoursPreferenceRule(ruleWeight,ruleType));
				break;
			default:
				allErrors.add("ruleName exists but no implemntion for rule class");
					break;
			}
			}
			else
			{
				allErrors.add("rule name "+ruleNameString+" doesnt supported!!!");
			}
			
		}
		
		return rules;
	}
	private Rule getSequentalityRule(ETTRule ruleData,double ruleWeight,RuleType ruleType)
	{
		String configruationString=ruleData.getETTConfiguration();
		int totalHoursSubjectCanBeTeachedInARow=0;
		if(configruationString==null)
		{
			configruationString="";
		}
		 KeyValueStringExtractor configuration=new KeyValueStringExtractor(configruationString);
		 String totalHoursSubjectCanBeTeachedInARowString=configuration.getValue("totalhours");
		 if(totalHoursSubjectCanBeTeachedInARowString==null)
		 {
			 allErrors.add("Sequantality Rule missing TotalHours configuration");
		 }
		 else
		 {
			 try
			 {
				 totalHoursSubjectCanBeTeachedInARow=Integer.parseInt(totalHoursSubjectCanBeTeachedInARowString);
				
				 if(totalHoursSubjectCanBeTeachedInARow<=0)
				 {
					 allErrors.add("Sequantality Rule TotalHours value must be  atlist 1 (integer)");
				 }
			 }
			 catch(Exception e)
			 {
				 allErrors.add("Sequantality Rule TotalHours value must be  atlist 1 (integer)");
			 }
		 }
		  
		return new SequentialityRule(ruleWeight,ruleType,totalHoursSubjectCanBeTeachedInARow);
		
	}
	private List<Course> getCourses(ETTSubjects subjects)
	{
		if(subjects.getETTSubject().size() ==0)
		{
			allErrors.add("there are no subjects/courses in the file!!!");
		}
					
		List<Course> courses=new ArrayList<>();
		for(ETTSubject subject : subjects.getETTSubject())
		{
				
			int id= subject.getId();
		    String name= subject.getName();
		    courses.add(new Course(id, name));
			
		}
	  
	  if(!isEntitiesAscendingOrderStartWith(courses,1))
		  allErrors.add("courses id's must be sequence of positive integers start with 1");
		return courses;
	}
	private List<SchoolClass> getClasses(ETTClasses classesData,List<Course> courses)
	{
		int hours=timeTableDescriptor.getETTTimeTable().getDays();
		int days=  timeTableDescriptor.getETTTimeTable().getHours();
		int maximumHoursToStudyForClass=days*hours;
		List<SchoolClass> classes=new ArrayList<>();
		if(classesData.getETTClass().size()==0)
		{
			allErrors.add("there are no classes in the file!!!");
		}
		for(ETTClass sclass : classesData.getETTClass())
		{
			int classId=sclass.getId();
			String className=sclass.getETTName();
			SchoolClass newClass=new SchoolClass(className,classId);
			boolean badHourExists=false;
			int totalHoursToStudyForClass=0;
		   for(ETTStudy studyRequirment: sclass.getETTRequirements().getETTStudy())
		   {
			   
			   if(studyRequirment.getHours()<1)
			   {
				  badHourExists=true;
			   }
			   boolean courseRequestedExists=false;
			   for(Course course:courses)
			   {
				   if(course.getId()==studyRequirment.getSubjectId())
				   {
					   courseRequestedExists=true;
					   boolean newRequirment= newClass.getCoursHoursMap().get(studyRequirment.getSubjectId())==null;
					   if(newRequirment)
					   {
						   newClass.getCoursHoursMap().put(course,studyRequirment.getHours());
						   totalHoursToStudyForClass+=studyRequirment.getHours();
						   break;
					   }
					   else
					   {
						   allErrors.add("class "+classId+" have dulpicate requirments of course "+course.getId());
					   }
					  
				   }
				  
				  
			   }
			   if(!courseRequestedExists)
			   {
				   allErrors.add("class "+classId+" requested course with id="
			                    +studyRequirment.getSubjectId()+" which doesnt exists");
			   }
		   }
		   if(totalHoursToStudyForClass>maximumHoursToStudyForClass && maximumHoursToStudyForClass>0)
		   {
			   allErrors.add("class "+classId+" total study hours = "
			   		+totalHoursToStudyForClass+ " is to much!!! the limit is days*hours="+days*hours);
		   }
		   classes.add(newClass);
		   
		   if(badHourExists)
			{
              allErrors.add("not all study hour requirments of class "+newClass.getId()+" are positive!!!");
			}
		}
		 if(!isEntitiesAscendingOrderStartWith(classes,1))
			  allErrors.add("classes id's must be sequence of positive integers start with 1");
		return classes;
	}
	private List<Teacher> getTeachers(ETTTeachers teachersData,List<Course> courses)
	{
		List<Teacher>teachers=new ArrayList<>();
		if(teachersData.getETTTeacher().size()==0)
		{
			allErrors.add("there are no teachers in the file");
		}
		for(ETTTeacher teacher : teachersData.getETTTeacher())
		{
			
			String teacherName= teacher.getETTName();
			int teacherPreferdWorkingHoursInAWeek = teacher.getETTWorkingHours();
			int maximumHoursToTeach = timeTableDescriptor.getETTTimeTable().getDays()*timeTableDescriptor.getETTTimeTable().getHours();
			if(teacherPreferdWorkingHoursInAWeek <0 || teacherPreferdWorkingHoursInAWeek > maximumHoursToTeach )
			{
				allErrors.add("teacher with id "+teacher.getId()+" prefred hours must be atlist 0 and <= "+maximumHoursToTeach);
			}
			List<Course> coursesThatTeacherCanTeach=new ArrayList<Course>();
			for (ETTTeaches courseToTeach : teacher.getETTTeaching().getETTTeaches())
			{
				int courseId= courseToTeach.getSubjectId();
				boolean foundCourse=false;
				for(Course course:courses)
				{
					if(course.getId()==courseId)
					{
					  foundCourse=true;
					  coursesThatTeacherCanTeach.add(course);
					}
				}
				if(!foundCourse)
				{
					allErrors.add("teahcer "+teacher.getId()+" have course with id "+courseId+" that doesnt exists");
				}
			}
			teachers.add(new Teacher(teacher.getId(),teacherName,coursesThatTeacherCanTeach, teacherPreferdWorkingHoursInAWeek));
		}
		 if(!isEntitiesAscendingOrderStartWith(teachers,1))
			  allErrors.add("teachers id's must be sequence of positive integers start with 1");
		return teachers;
	}
	
	public static void checkValidationOfXmlFileName(String xmlFileName) throws Exception
	{
		int xmlFileNameLength=xmlFileName.length();
		if(xmlFileNameLength<MINIMUM_LENGTH_OF_XML_FILE )
		{
			throw new Exception("unvalid xml file name ");
			
		}
		String endOfFileName= xmlFileName.substring(xmlFileNameLength-END_OF_XML_FILE.length(),xmlFileNameLength);

		if(!endOfFileName.equals(END_OF_XML_FILE))
		{
			throw new Exception("xml file name must end with "+END_OF_XML_FILE);
		}
	}
	 private  ETTDescriptor deserializeFrom(InputStream in) throws JAXBException {
	        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
	        Unmarshaller u = jc.createUnmarshaller();
	        return (ETTDescriptor) u.unmarshal(in);
	    }
	public final List<String> getAllErrors() {
		return allErrors;
	}
	private boolean isEntitiesAscendingOrderStartWith(List<? extends Entity>entitiesList,int startWithId)
	{
		
		List<Entity> entities= (List<Entity>) entitiesList;
		if(entities.size()==0)
			return true;
	   Entity.sortById(entities);
	   if(entities.get(0).getId()!=startWithId)
		   return false;
	   for(int i=0; i<entities.size()-1; i++)
	   {
		  if(entities.get(i).getId()+1!=entities.get(i+1).getId())
			  return false;
	   }
	   return true;
	   
	}
	public int getMinimumPopulationSize() {
		return minimumPopulationSize;
	}
	public void setMinimumPopulationSize(int minimumPopulationSize) {
		this.minimumPopulationSize = minimumPopulationSize;
	}
	
	private void checkConfiguration()
	{
		if(timeTableDescriptor!= null)
		{
			
			if(timeTableDescriptor.getETTTimeTable() == null)
			{
				allErrors.add("ETTTimeTable is missing!!! ");
			}
			else
			{
				ETTTimeTable problem =timeTableDescriptor.getETTTimeTable();
				if(problem.getETTClasses()==null)
				{
					allErrors.add("ETTClasses is missing!!!");
				}
				if(problem.getETTTeachers()==null)
				{
					allErrors.add("ETTTeachers is missing!!!");
				}
				if(problem.getETTSubjects()==null)
				{
					allErrors.add("ETTSubjects is missing!!!");
				}
				
			}
			
		}
		
	}

	public TimeTableProblem loadTimeTableProblem(InputStream inputStream) throws Exception {
		
		
		TimeTableProblem timeTableProblem =null;
		try
		{
			
			timeTableDescriptor=deserializeFrom(inputStream);
			 timeTableProblem=getTimeTableProblem(timeTableDescriptor.getETTTimeTable());
		
		}
			
			catch (JAXBException e) {
				// TODO Auto-generated catch block
				
				throw new Exception("failed to deserialize file!!!");
			}
			catch(NumberFormatException e)
			{
				
				allErrors.add("one of the configuration value errors "+e.getMessage());
				
			}
			finally {
				if(inputStream != null)
					inputStream.close();
			}
			
			if(allErrors.size()>0)
			{
				
				throw new Exception("unvalid xml file!!!");
			}
			
			
			return timeTableProblem;
	}
	
	
	
  

}
