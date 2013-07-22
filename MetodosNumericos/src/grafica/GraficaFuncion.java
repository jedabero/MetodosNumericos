package grafica;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.math.BigDecimal;

import resources.math.BigInterval;

public class GraficaFuncion {

	private BigInterval X;		//The x Interval
	private BigInterval Y;		//The y Interval
	private BigPoint[] puntos;	//El array con los puntos
	
	private boolean isSeparatePoints;	//Determina si la grafica es punteada o no.
	
	/**
	 * Crea una g&aacute;fica gu&iacute;ada por los puntos.
	 * @param bp los puntos
	 * @param separate indica si se grafican solo los puntos o como l&iacute;nea.
	 * @param x intervalo x
	 * @param y intervalo y
	 */
	public GraficaFuncion(BigPoint[] bp, boolean separate, BigInterval x, BigInterval y){
		this.isSeparatePoints = separate;
		this.puntos = bp;
		this.X = x;
		this.Y = y;
	}
	
	public Shape getGraficaFuncion(Point coord, Dimension dim){
		Path2D p2d = new Path2D.Double();
		boolean isPointFirst = true;
		
		//BigPoint prevP = null;
		for (BigPoint currP : puntos) {
			//BigDecimal xa =
			
			
			/*if(currP==null){
				//O.pln(iterator.previousIndex()+""+currentPoint);
				isPointFirst = true;
			}else{
				//O.pln(iterator.previousIndex()+currentPoint.toString().substring(14));
				if(isPointFirst){
					p2d.moveTo(currP.x, currP.y);
					isPointFirst = false;
				}else{
					p2d.lineTo(currP.x, currP.y);
				}
			}*/
		}
		
		return p2d;
	}
	
	public BigInterval getX() {
		return X;
	}
	
	public BigInterval getY() {
		return Y;
	}

	public BigPoint[] getPuntos() {
		return puntos;
	}
}
