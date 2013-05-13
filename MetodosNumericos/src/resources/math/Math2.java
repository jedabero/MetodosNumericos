/**
 * 
 */
package resources.math;

/**
 * @author Jedabero
 *
 */
public final class Math2 {
	
	/**
	 * Half of {@link Math#PI}
	 */
	public static final double PI_2 = Math.PI/2.0;
	
	/**
	 * Double of {@link Math#PI}
	 */
	public static final double TAU = 2*Math.PI;
	

	/**
	 * Da un valor aleatorio entre -1 y 1
	 * @return	un entero que puede ser -1, 0 o 1
	 */
	public static int randomSign(){
		int toInt = (int)(2 - 4*Math.random());
		return toInt==0 ? 1 : toInt;
	}
	
}
