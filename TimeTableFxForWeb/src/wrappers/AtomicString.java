package wrappers;

public class AtomicString {
	
	private String string;
	public AtomicString(String string)
	{
		this.string = string;
	}
	
	public synchronized String getString() {return string;}
	public synchronized void setString(String string) {this.string = string;}

}
