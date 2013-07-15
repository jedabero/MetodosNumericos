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
		/** Constante para determinar x como termino interno. */
		MONO,
		/** Constante para determinar la funcion como una constante. */
		CONSTANTE,
		/** Constante para determinar la funcion como Polinomica. */
		POLINOMICA,
		/** Constante para determinar la funcion como Trigonometrica. */
		TRIGONOMETRICA,
		/** Constante para determinar la funcion como Exponencial. */
		EXPONENCIAL,
		/** Constante para determinar la funcion como Logaritmica. */
		LOGARITMICA,
		/** Constante para determinar la funcion como Racional. */
		RACIONAL,
		/** Constante para determinar la funcion como compuesta por otra. */
		COMPUESTA,
		/** */
		
	}
	
	/**
	 * Grupo de funciones trigonometricas.
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
