package grafica;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.math.BigDecimal;
import java.math.RoundingMode;

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
			BigDecimal xnum = currP.x().subtract(X.min());
			BigDecimal xdiv = xnum.divide(X.length(), 5, RoundingMode.HALF_UP);
			int x = (int)(dim.width*(xdiv.doubleValue()));
			
			BigDecimal ynum = currP.y().subtract(Y.min());
			BigDecimal ydiv = ynum.divide(Y.length(), 5, RoundingMode.HALF_UP);
			int y = (int)(dim.height*(1-ydiv.doubleValue()));
			
			Point p = new Point(x,y);
			p.translate(coord.x, coord.y);
			if((p.y<coord.y)||(p.y>coord.y+dim.height)){
				isPointFirst = true;
			} else {
				if (isPointFirst) {
					p2d.moveTo(p.x, p.y);
					isPointFirst = false;
				} else {
					p2d.lineTo(p.x, p.y);
				}
			}
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
