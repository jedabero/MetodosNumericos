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
		/** Constante para determinar x como t�rmino interno. */
		MONO,
		/** Constante para determinar la funci�n como una constante. */
		CONSTANTE,
		/** Constante para determinar la funci�n como Polin�mica. */
		POLINOMICA,
		/** Constante para determinar la funci�n como Trigonom�trica. */
		TRIGONOMETRICA,
		/** Constante para determinar la funci�n como Exponencial. */
		EXPONENCIAL,
		/** Constante para determinar la funci�n como Logar�tmica. */
		LOGARITMICA,
		/** Constante para determinar la funci�n como Racional. */
		RACIONAL,
		/** Constante para determinar la funci�n como compuesta por otra. */
		COMPUESTA,
		/** */
		
	}
	
	/**
	 * Grupo de funciones trigonom�tricas.
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
