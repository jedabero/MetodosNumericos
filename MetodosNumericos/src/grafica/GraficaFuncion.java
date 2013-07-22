package grafica;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.math.BigDecimal;

import resources.math.BigInterval;
import resources.math.funciones.Funcion;

public class GraficaFuncion {

	private int subIntervals;
	private BigInterval X;		//The x Interval
	private BigInterval Y;		//The y Interval
	private BigPoint[] puntos;	//El array con los puntos
	private Funcion funcion;	//La funcion
	
	private boolean isSeparatePoints;	//Determina si la grafica es punteada o no.
	
	/**
	 * Crea una g&aacute;fica gu&iacute;ada por los puntos.
	 * @param bp indica si se grafican solo los puntos o como l&iacute;nea.
	 */
	public GraficaFuncion(BigPoint[] bp, boolean separate){
		this.isSeparatePoints = separate;
		this.puntos = bp;
	}
	
	public Shape getGraficaFuncion(Point coord, Dimension dim){
		Path2D p2d = new Path2D.Double();
		boolean isPointFirst = true;
		
		for (BigPoint bp : puntos) {
			//BigDecimal xa =
			
			
			
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
