/**
 * 
 */
package resources.math;

/**
 * @author jedabero
 * 
 */
public class Interval {

	private int max;
	private int min;

	public Interval(int min, int max) {
		this.max = max;
		this.min = min;
	}

	public int length() {
		int l = getMax() - getMin();
		return l;
	}

	public int centre() {
		int c = (max + min) / 2;
		return c;
	}

	public double realCentre() {
		double c = (max + min) / 2.0;
		return c;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public String toString() {
		return "(" + min + ", " + max + ")";
	}

}
