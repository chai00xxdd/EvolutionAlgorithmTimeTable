package Utils;

import java.util.Scanner;

public class NumbersUtils {
	
  public static int getUnsignedIntByForce(String errorMessage)
  {
	  int unsignedNumber = 0;
	  Scanner scanner= new Scanner(System.in);
	  boolean validNumber=false;
	  while(!validNumber)
	  {
		  try
		  {
		  //String unsignedNumberString = scanner.nextLine();
		  unsignedNumber=scanner.nextInt();
		  validNumber=unsignedNumber>0;
		  }
		  catch(Exception e)
		  {
			  
			  scanner=new Scanner(System.in);
		  }
		  if(!validNumber)
		  {
			  if(errorMessage!="")
			  {
			 System.out.println(errorMessage);
			  }
		  }
	  }
	  
	  return unsignedNumber;
  }
  public static int getNumber() throws Exception
  {
	 
	  
	  int number = 0;
	  Scanner scanner= new Scanner(System.in);
	  String numberString=scanner.nextLine();
	  number=Integer.parseInt(numberString);
	  
	  return number;
	 
	 
	  
  }
  public static int getUnsignedInt() throws Exception
  {
	  int unsignedNumber = 0;
	  Scanner scanner= new Scanner(System.in);
	  String numberString=scanner.nextLine();
	  unsignedNumber=Integer.parseInt(numberString);
	  if(unsignedNumber<=0)
	  {
		  throw new Exception("input is not unsigned number");
	  }
	  
	  return unsignedNumber;
  }
  public static double getDouble() throws Exception
  {
	  
	  double number = 0;
	  Scanner scanner= new Scanner(System.in);
	  String numberString=scanner.nextLine();
	  number=Double.parseDouble(numberString);
	  
	  return number;
	  
  }
  
	public static double mapOneRangeToAnother(double sourceNumber, double range1Start, double range1End, double toRangeStart, double toRangeEnd, int decimalPrecision ) {
	    double deltaA = range1End - range1Start;
	    double deltaB = toRangeEnd - toRangeStart;
	    double scale  = deltaB / deltaA;
	    double negA   = -1 * range1Start;
	    double offset = (negA * scale) + toRangeStart;
	    double finalNumber = (sourceNumber * scale) + offset;
	    int calcScale = (int) Math.pow(10, decimalPrecision);
	    return (double) Math.round(finalNumber * calcScale) / calcScale;
	}
}
