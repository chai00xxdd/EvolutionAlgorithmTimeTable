package Math;

import java.util.function.Function;

public class HomoGraphicFunction implements Function<Double,Double> {

	private double a,b,c,d;
	
	 public HomoGraphicFunction(double a, double b,double c,double d) {
		this.a=a;
		this.b=b;
		this.c=c;
		this.d=d;
	}
	@Override
	public Double apply(Double x) {
		// TODO Auto-generated method stub
		Double result=(a*x+b);
		result /= (c*x+d);
		return result;
	}

}
