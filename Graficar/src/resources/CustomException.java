/**
 * 
 */
package resources;

/**
 * @author Jedabero
 *
 */
public class CustomException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1544282612458154520L;
	
	/**
	 * @param m
	 */
	private CustomException(String m){
		super(m);
	}
	
	/**
	 * Crea una excepción para cuando el grado es menor que 0.
	 * @return una excepción para cuando el grado es menor que 0
	 */
	public static CustomException gradoMenorQue1(){
		return new CustomException("Grado no puede ser menor que 1");
	}
	
	/**
	 * Crea una excepción para cuando el coeficiente es 0.
	 * @return una excepción para cuando el coeficiente es 0
	 */
	public static CustomException coefAeq0(){
		return new CustomException("Coeficiente A no puede ser cero (0)");
	}
	
	/**
	 * Crea una excepción para cuando el tipo de función es incorrecto.
	 * Esto es porque para esos tipos se necesitan datos diferentes.
	 * @return una excepción para cuando el tipo de función es incorrecto
	 */
	public static CustomException tipoIncorrecto(){
		return new CustomException("Tipo de función incorrecto; no hay " +
				"suficientes datos.");
	}
	
	/**
	 * @return una excepción cuando haya una contradicción de longitud de array
	 */
	public static CustomException arrayIncompleto(){
		return new CustomException("Al array le faltan datos para completar la" +
				"costruccion de la Función correctamente.");
	}
	
}
