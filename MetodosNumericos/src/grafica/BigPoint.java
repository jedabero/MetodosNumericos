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
public class BigPoint{
	
	private BigDecimal x;
	private BigDecimal y;
	
	/**
	 * Crea un punto en formato BigDecimal iniciado en (0,0)
	 */
	public BigPoint(){
		this.x = BigDecimal.ZERO;
		this.y = BigDecimal.ZERO;
	}
	
	/**
	 * Crea un punto en formato BigDecimal segun un punto en formato entero.
	 * @param p el punto a convertir
	 */
	public BigPoint(Point p){
		this.x = BigDecimal.valueOf(p.x);
		this.y = BigDecimal.valueOf(p.y);
	}
	
	/**
	 * Crea un punto en formato BigDecimal, para la representacion de el par
	 * ordenado de una funcion.
	 * @param x
	 * @param y
	 */
	public BigPoint(BigDecimal x, BigDecimal y) {
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
	
	public void move(BigDecimal x, BigDecimal y){
		this.x = x;
		this.y = y;
	}
	
	public void translate(BigDecimal dx, BigDecimal dy){
		this.x = this.x.add(dx);
		this.y = this.y.add(dy);
	}
	
	public BigDecimal distanceSq(BigDecimal x2, BigDecimal y2){
		
		return null;
	}
	
	public BigDecimal distanceSq(BigPoint bp2){
		return null;
	}
	
	public BigDecimal distance(BigDecimal x2, BigDecimal y2){
		
		return null;
	}
	
	public BigDecimal distance(BigPoint bp2){
		BigDecimal dSq = distanceSq(bp2);
		BigDecimal decimal;
		return null;
	}
	
	public String toString(){
		return "("+x+", "+y+")";
	}

}
