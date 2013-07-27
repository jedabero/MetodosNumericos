/**
 * 
 */
package resources;

/**
 * @author Jedabero
 * @since 0.4
 */
public class CustomException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1544282612458154520L;
	
	/**
	 * @param m
	 */
	public CustomException(String m){
		super(m);
	}
	
	/**
	 * Crea una excepci&oacute;n para cuando el grado es menor que 0.
	 * @return una excepci&oacute;n para cuando el grado es menor que 0
	 */
	public static CustomException gradoMenorQue1(){
		return new CustomException("Grado no puede ser menor que 1");
	}
	
	/**
	 * Crea una excepci&oacute;n para cuando el coeficiente es 0.
	 * @param m mensaje adicional
	 * @return una excepci&oacute;n para cuando el coeficiente es 0
	 */
	public static CustomException coeficienteIgual0(String m){
		return new CustomException("Coeficiente no puede ser cero (0): "+m);
	}
	
	/**
	 * Crea una excepci&oacute;n para cuando el tipo de funci&oacute;n es incorrecto.
	 * Esto es porque para esos tipos se necesitan datos diferentes.
	 * @return una excepci&oacute;n para cuando el tipo de funci&oacute;n es incorrecto
	 */
	public static CustomException tipoIncorrecto(){
		return new CustomException("Tipo de funci&oacute;n incorrecto; no hay " +
				"suficientes datos.");
	}
	
	/**
	 * @return una excepci&oacute;n cuando haya una contradicci&oacute;n de longitud de array
	 */
	public static CustomException arrayIncompleto(){
		return new CustomException("Al array le faltan datos para completar la" +
				"costruccion de la Funci&oacute;n correctamente.");
	}
	
}
