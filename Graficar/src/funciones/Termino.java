/**
 * 
 */
package funciones;

import java.math.BigDecimal;
import java.util.ArrayList;


import resources.Big;
import resources.CustomException;
import resources.O;
import resources.Constantes.TipoFuncion;
import resources.M;
import resources.Constantes.FuncionTrig;

/**
 * La clase {@code Termino} define lo que es un término en una función.
 * Cada término tiene los siguientes componentes:
 * <dl>
 * <dt>Coeficiente de término {@code A}
 * <dd>La constante que multiplica la función específica del término.
 * <dt>Una función {@code F(x)}
 * <dd>Dependerá del tipo de función; sea un {@code monomio} de grado {@code n},
 * una función {@code trigonométrica}, {@code logarítmica}, {@code exponencial},
 * etc.
 * <dt>Coeficiente de variable {@code B}
 * <dd>Una constante que multiplica la variable {@code x}.
 * <dt>Representación general y específica del término
 * <dd>General: {@code A*F(B*x)}<br>
 * Específica: {@code 3*sin(2x)}
 * </dl>
 * <p>
 * Es usada en la clase {@link Funcion} para definir cada un de los términos.
 * <p>
 * Creada como rediseño de la clase {@link FuncionBase} antes usada para definir
 * funciones.
 * 
 * @author <a href="https://twitter.com/Jedabero" target="_blank">Jedabero</a>
 * @since 0.4
 */
public class Termino {
	
	private BigDecimal A;
	
	private BigDecimal B;
	
	private TipoFuncion funcion;
	
	private FuncionTrig funTrig;
	private static boolean xInRadians = true;
	private static boolean xInDegrees = !xInRadians;
	
	private int grado;
	
	private String generic;
	private String specific;
	private String toString;

	/**
	 * Regresa el valor del coeficiente del término.
	 * @return coeficiente A
	 */
	public BigDecimal getA() {
		return A;
	}

	/**
	 * Modifica el valor del coeficiente del término.
	 * @param	a el nuevo valor del coeficiente.
	 */
	public void setA(BigDecimal a) {
		A = a;
	}

	/**
	 * Regresa el valor del coeficiente de la variable.
	 * @return el valor del coeficiente
	 */
	public BigDecimal getB() {
		return B;
	}

	/**
	 * Modifica valor del coeficiente de la variable.
	 * @param	b el nuevo valor del coeficiente
	 */
	public void setB(BigDecimal b) {
		B = b;
	}
	
	/**
	 * Regresa el tipo de función de este término.
	 * @return el tipo de función
	 */
	public TipoFuncion getTipoFuncion() {
		return funcion;
	}

	/**
	 * Modifica el actual tipo de función.
	 * @param	funcion el nuevo tipo de función.
	 */
	public void setTipoFuncion(TipoFuncion funcion) {
		this.funcion = funcion;
	}
	
	/**
	 * Regresa el tipo de función de este término.
	 * @return el tipo de función
	 */
	public FuncionTrig getFunTrig() {
		return funTrig;
	}

	/**
	 * Modifica el actual tipo de función.
	 * @param	ft el nuevo tipo de función.
	 */
	public void setFunTrig(FuncionTrig ft) {
		this.funTrig = ft;
	}
	
	/**
	 * @return the xInRadians
	 */
	public static boolean isXinRadians() {
		return xInRadians;
	}

	/**
	 * @param xInRad the xInRadians to set
	 */
	public static void setXinRadians(boolean xInRad) {
		xInRadians = xInRad;
		xInDegrees = !xInRad;
	}

	/**
	 * @return the xInDegrees
	 */
	public static boolean isXinDegrees() {
		return xInDegrees;
	}

	/**
	 * @param xInDeg the xInDegrees to set
	 */
	public void setXinDegrees(boolean xInDeg) {
		xInDegrees = xInDeg;
		xInRadians = !xInDeg;
	}
	
	/**
	 * Regresa el grado de  la función polinómica.
	 * @return the grado
	 */
	public int getGrado() {
		return grado;
	}

	/**
	 * Modifica el grado de la función polinómica
	 * @param	grado el nuevo grado 
	 */
	public void setGrado(int grado) {
		this.grado = grado;
	}

	/**
	 * Evalúa y regresa el valor del término.
	 * @param	x el valor de la variable independiente
	 * @return el valor evaluado.
	 */
	public BigDecimal valorImagen(BigDecimal x) {
		switch(this.funcion){
		case CONSTANTE:
			return getA();
		case POLINOMICA:
			return getA().multiply(x.pow(getGrado()));
		case TRIGONOMETRICA:
			return getA().multiply(M.calculaTrig(getFunTrig(),
					x.multiply(getB()), isXinRadians(), isXinDegrees()));
		case EXPONENCIAL:
			return getA().multiply(Big.exp(x.multiply(getB())));
		case LOGARITMICA:
			return getA().multiply(Big.ln(x.multiply(getB())));
		case RACIONAL:
			break;
		default: 
			return null;
		}
		return null;
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

	/** Inicializa la representación específica y general del término. */
	public void initGenEsp(){
		String gS = "";
		switch(getTipoFuncion()){
		case CONSTANTE:
			gS += "A";
			break;
		case POLINOMICA:
			int g = getGrado();
			switch(g){
			case 0: gS += "A"; break;
			case 1: gS += "Ax"; break;
			default: gS += "Ax<sup>"+g+"</sup>"; break;
			}
			break;
		case TRIGONOMETRICA:
			FuncionTrig ft = getFunTrig();
			switch(ft){
			case SIN: gS += "Asin(Bx)"; break;
			case COS: gS += "Acos(Bx)"; break;
			case TAN: gS += "Atan(Bx)"; break;
			case SEC: gS += "Asec(Bx)"; break;
			case CSC: gS += "Acsc(Bx)"; break;
			case COT: gS += "Acot(Bx)"; break;
			default: gS += ""; break;
			}
			break;
		case EXPONENCIAL: gS += "Ae<sup>Bx</sup>"; break;
		case LOGARITMICA: gS += "Aln(Bx)"; break;
		case RACIONAL: /* TODO RACIONAL */ break;
		default: break;
		}
		this.generic = gS;
		
		String sS = "";
		BigDecimal a = getA();
		int signA = a.signum();
		boolean Aeq1 = a.abs().compareTo(BigDecimal.ONE)==0;
		boolean Aeq0 = signA==0;
		BigDecimal b = getB();
		int signB = b.signum();
		boolean Beq1 = b.abs().compareTo(BigDecimal.ONE)==0;
		boolean Beq0 = signB==0;
		boolean TermEq0 = Aeq0;
		boolean TermEq1 = false;
		boolean TermEqInf = false;
		
		if(!TermEq0||!TermEq1||!TermEqInf){
			if(signA==-1) sS +="- ";
			
			switch(getTipoFuncion()){
			case CONSTANTE:
				toString = ""+a;
				sS += a;
				break;
			case POLINOMICA:
				int g = getGrado();
				toString = a+"*x^"+g;
				switch(g){
				case 0:
					sS += a.abs();
					break;
				case 1:
					if(!Aeq1) sS += a.abs();
					sS += "x";
					break;
				default:
					if(!Aeq1) sS += a.abs();
					sS += "x<sup>"+g+"</sup>";
					break;
				}
				break;
				
			case TRIGONOMETRICA:
				FuncionTrig ft = getFunTrig();
				toString = a+"*"+ft+"("+b+"*x)";
				if(!Beq0){
					if(!Aeq1) sS += a.abs();
					
					switch(ft){
					case SIN:
						sS += "sin(";
						break;
					case COS:
						sS += "cos(";
						break;
					case TAN:
						sS += "tan(";
						break;
					case SEC:
						sS += "sec(";
						break;
					case CSC:
						sS += "csc(";
						break;
					case COT:
						sS += "cot(";
						break;
					default:
						sS += "";
						break;
					}
					if(signB==-1) sS +="-";
					if(!Beq1) sS += b.abs();
					sS += "x)";
					break;
				}else{
					switch(ft){
					case SIN:
					case TAN:
						break;
					case COS:
					case SEC:
						break;
					case CSC:
					case COT:
						break;
					default:
					}
				}
			case EXPONENCIAL:
				toString = a+"*e^("+b+"*x)";
				if(!Aeq1) sS += a.abs();
				sS += "e<sup>";
				if(signB==-1) sS +="-";
				if(!Beq1) sS += b.abs();
				sS += "x</sup>";
				break;
				
			case LOGARITMICA:
				toString = a+"*ln("+b+"*x)";
				if(!Aeq1) sS += a.abs();
				sS += "ln(";
				if(signB==-1) sS +="-";
				if(!Beq1) sS += b.abs();
				sS += "x)";
				break;
				
			case RACIONAL:
				break;
			default: break;
			}
		}else if(TermEq0){
			sS += "0";
		}else if(TermEq1){
			sS += "1";
		}else if(TermEqInf){
			sS += "Inf";
		}
		
		this.specific = sS;
	}
	
	
	
	public String toString(){
		return toString;
	}
	
	/**
	 * Crea un término con los parámetros mínimos.
	 * Arroja un error si el tipo es {@link TipoFuncion#POLINOMICA} o 
	 * {@link TipoFuncion#TRIGONOMETRICA}
	 * El valor resultante del término va a depender de el tipo de función
	 * {@code f} ya que es este quien define como calcularlo.
	 * @param	a el coeficiente del término
	 * @param	b el coeficiente de la variable
	 * @param	f el tipo de función
	 * @exception	Exception lanzado si el tipo de función es no esperado
	 */
	private Termino(BigDecimal a, BigDecimal b, TipoFuncion f)
			throws CustomException {
		if(f.equals(TipoFuncion.POLINOMICA)||f.equals(TipoFuncion.TRIGONOMETRICA)){
			throw CustomException.tipoIncorrecto();
		}else if(f.equals(TipoFuncion.CONSTANTE)){
			setTipoFuncion(f);
		}else if((a.signum()==0)&&(f!=TipoFuncion.CONSTANTE)){
			throw CustomException.coefAeq0();
		}else{
			setTipoFuncion(f);
		}
		setA(a);  setB(b);
		initGenEsp();
	}
	
	/**
	 * Crea un término de tipo {@link TipoFuncion#POLINOMICA}.
	 * 
	 * @param	a el coeficiente del término
	 * @param	g el grado del término
	 */
	private Termino(BigDecimal a, int g) throws CustomException {
		if(g<1 || g>999999999){
			throw CustomException.gradoMenorQue1();
		}else if(a.signum()==0){
			throw CustomException.coefAeq0();
		}else{
			setGrado(g);
		}
		setA(a);
		setB(BigDecimal.ONE);
		setTipoFuncion(TipoFuncion.POLINOMICA);
		initGenEsp();
	}
	
	/**
	 * Crea un término del tipo {@link TipoFuncion#TRIGONOMETRICA}.
	 * @param	a el coeficiente del término
	 * @param	b el coeficiente de la variable
	 * @param	ft el tipo de función trigonométrica
	 */
	private Termino(BigDecimal a, BigDecimal b, FuncionTrig ft)
			throws CustomException {
		if(a.signum()==0){
			throw CustomException.coefAeq0();
		}
		setA(a); setB(b);
		setTipoFuncion(TipoFuncion.TRIGONOMETRICA);
		setFunTrig(ft); 
		initGenEsp();
	}
	
	/**
	 * 
	 * @param coef
	 * @return una función constante
	 */
	public static Termino constante(BigDecimal coef){
		Termino t;
		try{
			t = new Termino(coef, BigDecimal.ZERO, TipoFuncion.CONSTANTE);
		}catch(CustomException ce){
			t = constante(BigDecimal.ONE);
		}
		return t;
	}
	
	/**
	 * @param grado	el grado del monomio
	 * @param coef	el coeficiente del término
	 * @param pos	la posición en la función
	 * @return un termino de tipo polinómico de grado {@code grado}
	 */
	public static Termino polinomio(int grado, BigDecimal coef){
		Termino t = null;
		try{
			t = new Termino(coef, grado);
		}catch(CustomException et){
			String etm = et.getMessage();
			O.pln("err:? "+etm);
			if(etm.equals(CustomException.gradoMenorQue1().getMessage())){
				t = polinomio(1, coef);
			}else if(etm.equals(CustomException.coefAeq0().getMessage())){
				t = polinomio(grado, BigDecimal.ONE);
			}
		}
		return t;
	}
	
	/**
	 * @param ft	el tipo de función trigonométrica
	 * @param coefA	el coeficiente del término
	 * @param coefB	el coeficiente que acompaña a x
	 * @param pos	la posición en la función
	 * @return un termino de tipo trigonométrico tipo {@code ft}
	 */
	public static Termino trigonometrico(FuncionTrig ft, BigDecimal coefA,
			BigDecimal coefB){
		Termino t = null;
		try{
			t =  new Termino(coefA, coefB, ft);
			return t;
		}catch(CustomException et){
			String etm = et.getMessage();
			O.pln("err: "+etm);
			t = trigonometrico(ft, BigDecimal.ONE, coefB);
		}
		return t;
	}
	
	/**
	 * @param coefA el coeficiente del término
	 * @param coefB el coeficiente que acompaña a x
	 * @param i		la posición en la función
	 * @return un termino de tipo exponencial
	 */
	public static Termino exponencial(BigDecimal coefA, BigDecimal coefB){
		Termino t = null;
		try{
			t =  new Termino(coefA, coefB, TipoFuncion.EXPONENCIAL);
			return t;
		}catch(CustomException et){
			String etm = et.getMessage();
			O.pln("err: "+etm);
			t = exponencial(BigDecimal.ONE, coefB);
		}
		return t;
	}
	
	/**
	 * Crea un término de tipo logarítmico con los parámetros dados.
	 * @param coefA el coeficiente del término
	 * @param coefB el coeficiente que acompaña a x
	 * @param i		la posición en la función
	 * @return un termino de tipo logarítmico
	 */
	public static Termino logaritmo(BigDecimal coefA, BigDecimal coefB){
		Termino t = null;
		try{
			t =  new Termino(coefA, coefB, TipoFuncion.LOGARITMICA);
			return t;
		}catch(CustomException et){
			String etm = et.getMessage();
			O.pln("err: "+etm);
			t = logaritmo(BigDecimal.ONE, coefB);
		}
		return t;
	}
	
	/**
	 * @return la derivada de este termino
	 */
	public Termino derivada(){
		switch (this.getTipoFuncion()) {
		case CONSTANTE:
			return Termino.constante(BigDecimal.ZERO);
		case POLINOMICA:
			BigDecimal a = getA().multiply(BigDecimal.valueOf(getGrado()));
			int g = getGrado() - 1;
			switch (g) {
			case 0:
				return Termino.constante(a);
			default:
				try {
					return polinomio(g, a);
				} catch (Exception e) {
					O.pln(e);
					return null;
				}
			}
			
		case TRIGONOMETRICA:
			return null;
		case EXPONENCIAL:
			return null;
		case LOGARITMICA:
			return null;
		case RACIONAL:
			break;
		default: 
			return null;
		}
		return null;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BigDecimal coef[] = {BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ONE};
		Termino t = Termino.polinomio(4, BigDecimal.ONE);	//t^4
		Termino dt = t.derivada();							//4t^3
		Termino ddt = dt.derivada();						//12t^2
		Termino dddt = ddt.derivada();						//24t
		Termino ddddt = dddt.derivada();					//24
		Termino dddddt = ddddt.derivada();					//0
		
		ArrayList<Termino> alT = new ArrayList<Termino>();
		alT.add(t);
		alT.add(dt);
		alT.add(ddt);
		alT.add(dddt);
		alT.add(ddddt);
		alT.add(dddddt);
		
		Funcion g = new Funcion(alT);
		O.pln(g);
		O.pln(g.derivada());
		
		Funcion f = null;
		try {
			f = Funcion.polinomio(2, coef);
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		O.pln(f);
		O.pln(f.derivada());
		
	}
	
}
