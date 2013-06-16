/**
 * 
 */
package grafica;

import java.math.BigDecimal;

import resources.math.Big;
import resources.math.BigInterval;
import resources.math.funciones.Funcion;

/**
 * @author jedabero
 *
 */
public class GrupoPuntosFuncion {
	
	private Funcion funcion;
	private int subint;
	private BigInterval X;
	private BigInterval Y;
	private BigPoint[] puntos;
	
	
	public GrupoPuntosFuncion(Funcion f, BigInterval x, int n){
		this.funcion = f;
		this.X = x;
		this.subint = n;
		puntos = new BigPoint[n+1];
		
		calculos();
		
	}

	private void calculos() {
		BigDecimal px[] = X.conjuntoPuntos(subint);
		BigDecimal y[] = new BigDecimal[subint+1];
		for (int i = 0; i <= subint; i++) {
			y[i] = funcion.valorImagen(px[i]);
			puntos[i] = new BigPoint(px[i], y[i]);
		}
		Y = new BigInterval(Big.min(y), Big.max(y));
	}

	public BigInterval getY() {
		return Y;
	}

}
