/**
 * 
 */
package resources.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Jedabero
 * @since 0.4
 */
public class BigInterval {

	private BigDecimal max;
	private BigDecimal min;

	/**
	 * Crea un intervalo cerrado de longitud finita
	 * 
	 * @param min
	 *            el minimo valor del intervalo
	 * @param max
	 *            el maximo valor del intervalo
	 */
	public BigInterval(BigDecimal min, BigDecimal max) {
		this.max = max;
		this.min = min;
	}

	/**
	 * 
	 * @return distancia entre el maximo y el minimo
	 */
	public BigDecimal length() {
		return max.subtract(min);
	}

	/**
	 * @return el valor del centro de este intervalo
	 */
	public BigDecimal centre() {
		return max.add(min).divide(BigDecimal.valueOf(2), max.scale() + 2,
				RoundingMode.HALF_UP);
	}

	/**
	 * @param scale
	 *            scale
	 * @return el valor del centro de este intervalo
	 */
	public BigDecimal centre(int scale) {
		return max.add(min).divide(BigDecimal.valueOf(2), scale,
				RoundingMode.HALF_UP);
	}

	/**
	 * @param n
	 *            numero de sub-intervalos
	 * @return el tamano del paso segun un numero de sub-intervalos
	 */
	public BigDecimal step(int n) {
		BigDecimal h = length().divide(BigDecimal.valueOf(n), 15,
				RoundingMode.HALF_UP);
		return h.stripTrailingZeros();
	}

	/**
	 * @param n
	 *            numero de sub-intervalos
	 * @return un conjunto de n + 1 puntos tal que x<sub>i</sub> = min + i*h
	 */
	public BigDecimal[] conjuntoPuntos(int n) {
		BigDecimal h = step(n);
		BigDecimal x[] = new BigDecimal[n + 1];
		x[0] = min();
		for (int i = 1; i < x.length - 1; i++) {
			x[i] = min().add(h.multiply(BigDecimal.valueOf(i)));
		}
		x[n] = max();
		return x;
	}

	/**
	 * @return el minimo valor del intervalo
	 */
	public BigDecimal min() {
		return min;
	}

	/**
	 * @param m
	 */
	public void setMin(BigDecimal m) {
		this.min = m;
	}

	/**
	 * @return el maximo valor del intervalo
	 */
	public BigDecimal max() {
		return max;
	}

	/**
	 * @param m
	 */
	public void setMax(BigDecimal m) {
		this.max = m;
	}

	public String toString() {
		return "(" + min + ", " + max + ")";
	}

}
