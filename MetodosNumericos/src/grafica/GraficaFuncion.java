package grafica;

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
	
	private void calculos() {
		BigDecimal px[] = X.conjuntoPuntos(subIntervals);
		BigDecimal y[] = new BigDecimal[subIntervals + 1];
		for (int i = 0; i <= subIntervals; i++) {
			y[i] = funcion.valorImagen(px[i]);
			puntos[i] = new BigPoint(px[i], y[i]);
		}
		Y = new BigInterval(Big.min(y), Big.max(y));
	}
	
	public BigInterval getY() {
		return Y;
	}

	public BigPoint[] getPuntos() {
		return puntos;
	}
}
