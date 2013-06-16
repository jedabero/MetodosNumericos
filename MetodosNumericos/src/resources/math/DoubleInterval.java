/**
 * 
 */
package resources.math;

/**
 * @author jedabero
 *
 */
public class DoubleInterval {

	private double max;
	private double min;
	
	public DoubleInterval(double min, double max){
		this.max = max;
		this.min = min;
	}
	
	public double length(){
		double l = max() - min();
		return l;
	}
	
	public double centre(){
		double c = (max+min)/2.0;
		return c;
	}
	
	public double[] points(int n){
		double h = step(n);
		double x[] = new double[n+1];
		x[0] = min();
		for(int i = 1; i < n; i++){
			x[i] = min() + h*i;
		}
		x[n] = max();
		return x;
	}
	
	public double step(int n) {
		double h = length()/n;
		return h;
	}

	public double max() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double min() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}
	
	public String toString(){
		return "("+min+", "+max+")";
	}
	
}
