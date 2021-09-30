package Utils;
import java.util.Collections;
import java.util.List;



public class Entity   {
	protected String name;
	protected int id;
	public Entity(int id,String name)
	{
		
		this.name=name;
		this.id=id;
	}
	public Entity(Entity other)
	{
		
		this.name=other.name;
		this.id=other.id;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	public static void sortById(List<? extends Entity>entities)
	{
		Collections.sort(entities,(entity1,entity2)->entity1.getId()-entity2.getId());
	}
	@Override
	public String toString() {
		return "[id="+getId()+",name="+getName()+"]";
	}
   
	
	
	

}
