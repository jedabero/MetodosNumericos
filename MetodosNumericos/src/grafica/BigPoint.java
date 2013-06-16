/**
 * 
 */
package grafica;

import java.awt.Point;
import java.math.BigDecimal;

/**
 * @author Jedabero
 *
 */
public class BigDecimalPoint{
	
	BigDecimal x;
	BigDecimal y;
	
	/**
	 * Crea un punto en formato BigDecimal iniciado en (0,0)
	 */
	public BigDecimalPoint(){
		this.x = BigDecimal.ZERO;
		this.y = BigDecimal.ZERO;
	}
	
	/**
	 * Crea un punto en formato BigDecimal seg�n un punto en formato entero.
	 * @param p el punto a convertir
	 */
	public BigDecimalPoint(Point p){
		this.x = BigDecimal.valueOf(p.x);
		this.y = BigDecimal.valueOf(p.y);
	}
	
	/**
	 * Crea un punto en formato BigDecimal, para la representaci�n de el par
	 * ordenado de una funci�n.
	 * @param x
	 * @param y
	 */
	public BigDecimalPoint(BigDecimal x, BigDecimal y) {
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
