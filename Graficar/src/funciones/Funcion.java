/**
 * 
 */
package funciones;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.ListIterator;

import resources.Constantes.TipoFuncion;
import resources.CustomException;
import stream.O;

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
		Termino term;
		String g = "";
		String s = g;
		toString = g;
		for (iterator = getTerminos().listIterator(); iterator.hasNext();) {
			term = iterator.next();
			
			boolean positiveA = term.getA().signum()==1;
			boolean indexIs0 = iterator.previousIndex()==0;
			g += (indexIs0?"":(positiveA?" + ":" - ")) + term.getGeneric();
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
	 * @param grado
	 * @param coefs
	 * @return una función polinómica de grado {@code grado} con todos los
	 * términos
	 * @throws CustomException 
	 */
	public static Funcion polinomio(int grado, BigDecimal[] coefs)
			throws CustomException{
		if(coefs.length<=grado) throw CustomException.arrayIncompleto();
		ArrayList<Termino> alT = new ArrayList<Termino>();
		for(int i=0;i<=grado;i++){
			alT.add(Termino.polinomio(i, coefs[i]));
		}
		return new Funcion(alT);
	}
	
	/** @param args */
	public static void main(String[] args) {
		BigDecimal[] coefs = {BigDecimal.ONE.negate(),BigDecimal.ONE.negate(),BigDecimal.ONE};
		javax.swing.JFrame jf = new javax.swing.JFrame();
		javax.swing.JLabel jl = new javax.swing.JLabel();
		Funcion f = null;
		
		try {
			f = polinomio(2, coefs);
		} catch (Exception e) {
			O.pln("err: "+e.getMessage());
		}
		
		jl.setText("<html>"+f.getSpecific()+"<p>"+f.getGeneric()+"<p>"+f+"</html>");
		O.pln(f.valorImagen(BigDecimal.TEN));
		jf.add(jl);
		jf.setVisible(true);
		jf.setSize(300,300);
		jf.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
	}
	
}
