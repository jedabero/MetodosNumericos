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
	
	private BigInterval intX;	//The x interval for the integration area
	
	private BigPoint[] puntos;	//El array con los puntos
	
	private boolean isSeparatePoints;	//Determina si la grafica es punteada o no.
	
	/**
	 * Crea una g&aacute;fica gu&iacute;ada por los puntos.
	 * @param bp los puntos
	 * @param separate indica si se grafican solo los puntos o como l&iacute;nea.
	 * @param x intervalo x
	 * @param y intervalo y
	 */
	public GraficaFuncion(BigPoint[] bp, boolean separate,
			BigInterval x, BigInterval y, BigInterval intX){
		this.isSeparatePoints = separate;
		this.puntos = bp;
		this.X = x;
		this.Y = y;
		this.intX = (intX == null) ? X : intX;
	}
	
	public Shape getGraficaFuncion(Point coord, Dimension dim, int xAxis,
			boolean withIntegral){
		Path2D p2d = new Path2D.Double();
		boolean isPointFirst = true;
		this.intX = (intX == null) ? X : intX;
		Point prevP = null;
		for (BigPoint currBP : puntos) {
			BigDecimal xnum = currBP.x().subtract(X.min());
			BigDecimal xdiv = xnum.divide(X.length(), 5, RoundingMode.HALF_UP);
			int x = (int)(dim.width*(xdiv.doubleValue()));
			
			BigDecimal ynum = currBP.y().subtract(Y.min());
			BigDecimal ydiv = ynum.divide(Y.length(), 5, RoundingMode.HALF_UP);
			int y = (int)(dim.height*(1-ydiv.doubleValue()));
			
			Point p = new Point(x,y);
			p.translate(coord.x, coord.y);
			
			Point intP = new Point();
			boolean pointInsideOfIntegral =
					(currBP.x().compareTo(intX.min())>=0)&&(currBP.x().compareTo(intX.max())<=0);	
			if(pointInsideOfIntegral){
				intP = new Point(x,y);
				intP.translate(coord.x, coord.y);
				
				if(intP.y<coord.y){
					intP.y = coord.y;
				}else if(intP.y>coord.y+dim.height){
					intP.y = coord.y+dim.height;
				}
			}
			
			if((p.y<coord.y)||(p.y>coord.y+dim.height)){
				isPointFirst = true;
			} else {
				if (isPointFirst) {
					p2d.moveTo(p.x, p.y);
					isPointFirst = false;
				} else {
					
					if(withIntegral&&pointInsideOfIntegral){
						p2d.lineTo(prevP.x, xAxis);
						p2d.lineTo(intP.x, xAxis);
					}
					
					if(isSeparatePoints){
						p2d.lineTo(prevP.x+1, prevP.y+1);
					}
					
					p2d.lineTo(p.x, p.y);
				}
			}
			prevP = p;
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
