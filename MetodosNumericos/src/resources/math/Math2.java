/**
 * 
 */
package resources.math;

/**
 * @author Jedabero
 * 
 */
public final class Math2 {

	/**
	 * Half of {@link Math#PI}
	 */
	public static final double PI_2 = Math.PI / 2.0;

	/**
	 * Double of {@link Math#PI}
	 */
	public static final double TAU = 2 * Math.PI;

	/**
	 * Da un valor aleatorio entre -1 y 1
	 * 
	 * @return un entero que puede ser -1, 0 o 1
	 */
	public static int randomSign() {
		int toInt = (int) (2 - 4 * Math.random());
		return toInt == 0 ? 1 : toInt;
	}

	/**
	 * Regresa el valor m�ximo de un array de int
	 * 
	 * @param a
	 *            el array a evaluar
	 * @return el valor m�ximo dentro de {@code a}.
	 */
	public static int max(int a[]) {
		int max = a[0];
		for (int i : a) {
			if (max <= i)
				max = i;
		}
		return max;
	}

	/**
	 * Regresa el valor m�nimo de un array de int
	 * 
	 * @param a
	 *            el array a evaluar
	 * @return el valor m�nimo dentro de {@code a}.
	 */
	public static int min(int a[]) {
		int min = a[0];
		for (int i : a) {
			if (min >= i)
				min = i;
		}
		return min;
	}

	/**
	 * Regresa el factorial del n�mero n
	 * 
	 * @param n
	 *            el n�mero
	 * @return n! el factorial de n en entero
	 * @throws Exception
	 *             si n est� fuera del rango 0 < n < 20
	 */
	public static long factorial(int n) throws Exception {
		long a = 1L;
		if (n < 0) {
			throw new Exception("No hay factorial para enteros negativos");
		} else if (n > 20) {
			throw new Exception("Factorial de " + n + " es muy grande: > "
					+ Long.MAX_VALUE + ". Utilice BigFactorial");
		} else {
			for (int i = 1; i < n + 1; i++)
				a *= i;
		}

		return a;
	}

	/**
	 * @param n
	 * @param k
	 * @return nCk
	 * @throws Exception
	 */
	public static long combinatoria(int n, int k) throws Exception {
		if (n < k) {
			throw new Exception(n + "<" + k);
		} else {
			long fn = factorial(n);
			long fk = factorial(k);
			int n_k = n - k;
			long fn_k = factorial(n_k);
			return fn / (fn_k * fk);
		}
	}

	/**
	 * @param n
	 * @param k
	 * @return nPk
	 * @throws Exception
	 */
	public static long permutacion(int n, int k) throws Exception {
		if (n < k) {
			throw new Exception(n + "<" + k);
		} else {
			long fn = factorial(n);
			int n_k = n - k;
			long fn_k = factorial(n_k);
			return fn / (fn_k);
		}
	}

}
