/**
 * 
 */
package resources;

import java.awt.Point;
import java.math.BigDecimal;

/**
 * @author Jedabero
 *
 */
public class BigDecimalCoord{
	
	BigDecimal x;
	BigDecimal y;
	
	/**
	 * Crea un punto en formato BigDecimal iniciado en (0,0)
	 */
	public BigDecimalCoord(){
		this.x = BigDecimal.ZERO;
		this.y = BigDecimal.ZERO;
	}
	
	/**
	 * Crea un punto en formato BigDecimal según un punto en formato int.
	 * @param p el punto a convertir
	 */
	public BigDecimalCoord(Point p){
		this.x = BigDecimal.valueOf(p.x);
		this.y = BigDecimal.valueOf(p.y);
	}
	
	/**
	 * Crea un punto en formato BigDecimal, para la representación de el par
	 * ordenado de una función.
	 * @param x
	 * @param y
	 */
	public BigDecimalCoord(BigDecimal x, BigDecimal y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * @return x
	 */
	public BigDecimal x(){
		return x;
	}
	/**
	 * @return y
	 */
	public BigDecimal y(){
		return y;
	}
	
	public String toString(){
		return "("+x+", "+y+")";
	}

}
