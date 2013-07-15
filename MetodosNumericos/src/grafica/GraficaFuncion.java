package grafica;

import java.awt.Dimension;
import java.awt.Shape;
import java.math.BigDecimal;

import resources.math.Big;
import resources.math.BigInterval;
import resources.math.funciones.Funcion;

public class GraficaFuncion {

	private int subIntervals;
	private BigInterval X;		//The x Interval
	private BigInterval Y;		//The y Interval
	private BigPoint[] puntos;
	private Funcion funcion;	//La funcion
	
	public GraficaFuncion(Funcion f, BigInterval x, int subInt){
		this.funcion = f;
		this.X = x;
		this.subIntervals = subInt;
		puntos = new BigPoint[subInt + 1];
		
		calculos();
	}
	
	public GraficaFuncion(BigPoint[] bp){
		BigDecimal maxX = bp[0].x();
		BigDecimal minX = bp[0].x();
		BigDecimal maxY = bp[0].y();
		BigDecimal minY = bp[0].y();
		
		for (int i = 1; i < bp.length; i++) {
			BigPoint p = bp[i];
			if(maxX.compareTo(p.x()) == -1) maxX = p.x();
			if(minX.compareTo(p.x()) == 1) minX = p.x();
			if(maxY.compareTo(p.y()) == -1) maxY = p.y();
			if(minY.compareTo(p.y()) == 1) minY = p.y();
		}
		this.puntos = bp;
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
		Y = new BigInterval(Big.min(y), Big.max(y));
	}
	
	public Shape getGraficaFuncion(Dimension dim){
		
		
		
		return null;
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
