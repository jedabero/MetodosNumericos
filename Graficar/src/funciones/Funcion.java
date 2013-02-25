/**
 * 
 */
package funciones;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.ListIterator;

import resources.Constantes.FuncionTrig;
import resources.Constantes.TipoFuncion;
import resources.CustomException;

/**
 * La clase {@code Funcion} define una función explícita de la forma
 * {@code y = F(x)}. 
 * Todas las propiedades de esta función dependen de las propiedades de los
 * {@code términos}. Por lo tanto es posible obtener una función polinómica de
 * grado n, o una simple función trigonométrica, exponencial o logarítmica, o
 * una función con términos combinados.
 * @author Jedabero
 *
 */
public class Funcion{
	
	private ArrayList<Termino> terminos;
	private TipoFuncion tipo;
	
	private String generic;
	private String specific;
	private String toString;
	
	/**
	 * @return el TipoFuncion
	 */
	public TipoFuncion getTipo() {
		return tipo;
	}

	/**
	 * @param tipo TipoFuncion 
	 */
	public void setTipo(TipoFuncion tipo) {
		this.tipo = tipo;
	}

	/**
	 * Regresa la representación general del término.
	 * @return la representación general
	 */
	public String getGeneric() {
		return generic;
	}

	/**
	 * Regresa la representación específica del término.
	 * @return la representación específica
	 */
	public String getSpecific() {
		return specific;
	}

	/**
	 * @return lista de términos
	 */
	public ArrayList<Termino> getTerminos() {
		return terminos;
	}

	/**
	 * @param terminos la lista de términos
	 */
	public void setTerminos(ArrayList<Termino> terminos) {
		this.terminos = terminos;
	}
	
	/**
	 * Evalúa y regresa el valor de la función.
	 * @param x el valor de la variable independiente
	 * @return el valor evaluado
	 */
	public BigDecimal valorImagen(BigDecimal x){
		ListIterator<Termino> iterator;
		Termino term;
		BigDecimal y = new BigDecimal(0);
		for (iterator = getTerminos().listIterator(); iterator.hasNext();) {
			term = iterator.next();
			y = y.add(term.valorImagen(x));
		}
		return y;
	}
	
	/** Inicializa la representación específica y general del término. */
	public void initGenEsp(){
		ListIterator<Termino> iterator;
		String g = "";
		String s = g;
		toString = g;
		
		for (iterator = getTerminos().listIterator(); iterator.hasNext();) {
			Termino term = iterator.next();
			boolean positiveA = term.getA().signum()==1;
			boolean indexIs0 = iterator.previousIndex()==0;
			g += (indexIs0?"":(positiveA?" + ":" - "))+"- "+ term.getGeneric();
			s += (indexIs0?"":(positiveA?" + ":" ")) + term.getSpecific();
			toString += (indexIs0?"":" + ") + term;
		}
		
		this.generic = g;
		this.specific = s;
	}
	
	public String toString(){
		return toString;
	}
	
	/**
	 * @param lt la lista de términos que crea la función
	 * 
	 */
	public Funcion(ArrayList<Termino> lt){
		this.setTerminos(lt);
		initGenEsp();
	}
	
	/**
	 * @param t el término que crea la función singular
	 * 
	 */
	public Funcion(Termino t){
		ArrayList<Termino> alT = new ArrayList<Termino>();
		alT.add(t);
		this.setTerminos(alT);
		initGenEsp();
	}
	
	/**
	 * Crea un función polinómica de grado {@code grado} con todos los términos
	 * 
	 * @param n el {@code grado} de la función
	 * @param coefs el array con los coeficientes
	 * @return una función polinómica de grado {@code n} con todos los
	 * términos
	 * @throws CustomException 
	 */
	public static Funcion polinomio(int n, BigDecimal[] coefs)
			throws CustomException{
		if(coefs.length<=n) throw CustomException.arrayIncompleto();
		ArrayList<Termino> alT = new ArrayList<Termino>();
		alT.add(Termino.constante(coefs[0]));
		for(int i=1;i<=n;i++){
			alT.add(Termino.polinomio(i, coefs[i]));
		}
		return new Funcion(alT);
	}
	
	/**
	 * Crea un función trigonométrica de tipo {@code ft}
	 * @param ft 
	 * @param coefA 
	 * @param coefB 
	 * @return una función trigonométrica de tipo {@code ft}
	 */
	public static Funcion trigonometrica(FuncionTrig ft, BigDecimal coefA,
			BigDecimal coefB){
		ArrayList<Termino> alT = new ArrayList<Termino>();
		alT.add(Termino.trigonometrico(ft, coefA, coefB));
		return new Funcion(alT);
	}
	
}
