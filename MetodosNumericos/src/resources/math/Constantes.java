/**
 * 
 */
package resources.math;

/**
 * @version %I%
 * @author <a href="https://twitter.com/Jedabero" target="_blank">Jedabero</a>
 *
 */
public interface Constantes {
	
	
	
	/**
	 * Grupo de funciones
	 * @author Jedabero
	 *
	 */
	public enum Tipo{
		/** Constante para determinar x como término interno. */
		MONO,
		/** Constante para determinar la función como una constante. */
		CONSTANTE,
		/** Constante para determinar la función como Polinómica. */
		POLINOMICA,
		/** Constante para determinar la función como Trigonométrica. */
		TRIGONOMETRICA,
		/** Constante para determinar la función como Exponencial. */
		EXPONENCIAL,
		/** Constante para determinar la función como Logarítmica. */
		LOGARITMICA,
		/** Constante para determinar la función como Racional. */
		RACIONAL,
		/** Constante para determinar la función como compuesta por otra. */
		COMPUESTA,
		/** */
		
	}
	
	/**
	 * Grupo de funciones trigonométricas.
	 * @author Jedabero
	 *
	 */
	public enum FuncionTrig{
		/** Constante para determinar Seno. */
		SIN,
		/** Constante para determinar Coseno. */
		COS,
		/** Constante para determinar Tangente. */
		TAN,
		/**  Constante para determinar Secante. */
		SEC,
		/** Constante para determinar Cosecante. */
		CSC,
		/** Constante para determinar Cotangente. */
		COT ;
		
	}
	
}
