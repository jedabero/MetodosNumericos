package grafica;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.math.BigDecimal;

import resources.math.Big;
import resources.math.BigInterval;
import resources.math.funciones.Funcion;

public class GraficaFuncion {

	private int subIntervals;
	private BigInterval X;		//The x Interval
	private BigInterval Y;		//The y Interval
	private BigPoint[] puntos;	//El array con los puntos
	private Funcion funcion;	//La funcion
	
	private boolean isPoints;	//Determina si la grafica es punteada o no. 
	
	/**
	 * Crea la gr&aacute;fica de una función dentro de un intervalo x y de
	 * acuerdo a un numero de subdivisiones. 
	 * @param f	la funci&oacute;n
	 * @param x	el intervalo
	 * @param subInt las subdivisiones
	 */
	public GraficaFuncion(Funcion f, BigInterval x, int subInt, BigInterval y){
		this.isPoints = false;
		this.funcion = f;
		this.X = x;
		this.Y = y;
		this.subIntervals = subInt;
		puntos = new BigPoint[subInt + 1];
		
		calculos();
	}
	
	/**
	 * Crea una g&aacute;fica de puntos individuales
	 * @param bp
	 */
	public GraficaFuncion(BigPoint[] bp){
		this.isPoints = true;
		this.puntos = bp;
		initPuntos();
	}
	
	private void initPuntos(){
		BigDecimal maxX = puntos[0].x();
		BigDecimal minX = puntos[0].x();
		BigDecimal maxY = puntos[0].y();
		BigDecimal minY = puntos[0].y();
		
		for (int i = 1; i < puntos.length; i++) {
			BigPoint p = puntos[i];
			if(maxX.compareTo(p.x()) == -1) maxX = p.x();
			if(minX.compareTo(p.x()) == 1) minX = p.x();
			if(maxY.compareTo(p.y()) == -1) maxY = p.y();
			if(minY.compareTo(p.y()) == 1) minY = p.y();
		}
		this.X = new BigInterval(minX, maxX);
		this.Y = new BigInterval(minY, maxY);
	}
	
	private void calculos() {
		BigDecimal px[] = X.conjuntoPuntos(subIntervals);
		BigDecimal y[] = new BigDecimal[subIntervals + 1];
		for (int i = 0; i <= subIntervals; i++) {
			y[i] = funcion.valorImagen(px[i]);
			puntos[i] = new BigPoint(px[i], y[i]);
		}
		//Y = new BigInterval(Big.min(y), Big.max(y));
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
