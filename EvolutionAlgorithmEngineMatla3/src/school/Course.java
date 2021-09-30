package school;

import Utils.Entity;

public class Course extends Entity {

public Course(int id,String name) {
	super(id,name);
}
public Course(Course other)
{
	
	super(other.id,other.name);

}




}
