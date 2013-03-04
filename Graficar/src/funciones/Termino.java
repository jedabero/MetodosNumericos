/**
 * 
 */
package funciones;

import java.math.BigDecimal;

import math.Big;

import resources.CustomException;
import resources.Constantes.TipoFuncion;
import resources.M;
import resources.Constantes.FuncionTrig;
import stream.O;

/**
 * La clase {@code Termino} define lo que es un t�rmino en una funci�n.
 * Cada t�rmino tiene los siguientes componentes:
 * <dl>
 * <dt>Coeficiente de t�rmino {@code A}
 * <dd>La constante que multiplica la funci�n espec�fica del t�rmino.
 * <dt>Una funci�n {@code F(x)}
 * <dd>Depender� del tipo de funci�n; sea un {@code monomio} de grado {@code n},
 * una funci�n {@code trigonom�trica}, {@code logar�tmica}, {@code exponencial},
 * etc.
 * <dt>Coeficiente de variable {@code B}
 * <dd>Una constante que multiplica la variable {@code x}.
 * <dt>Representaci�n general y espec�fica del t�rmino
 * <dd>General: {@code A*F(B*x)}<br>
 * Espec�fica: {@code 3*sin(2x)}
 * </dl>
 * <p>
 * Es usada en la clase {@code Funcion} para definir cada un de los t�rminos.
 * <p>
 * Creada como redise�o de la clase {@link FuncionBase} antes usada para definir
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
	 * Regresa el valor del coeficiente del t�rmino.
	 * @return coeficiente A
	 */
	public BigDecimal getA() {
		return A;
	}

	/**
	 * Modifica el valor del coeficiente del t�rmino.
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
	 * Regresa el tipo de funci�n de este t�rmino.
	 * @return el tipo de funci�n
	 */
	public TipoFuncion getFuncion() {
		return funcion;
	}

	/**
	 * Modifica el actual tipo de funci�n.
	 * @param	funcion el nuevo tipo de funci�n.
	 */
	public void setFuncion(TipoFuncion funcion) {
		this.funcion = funcion;
	}
	
	/**
	 * Regresa el tipo de funci�n de este t�rmino.
	 * @return el tipo de funci�n
	 */
	public FuncionTrig getFunTrig() {
		return funTrig;
	}

	/**
	 * Modifica el actual tipo de funci�n.
	 * @param	ft el nuevo tipo de funci�n.
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
	 * Regresa el grado de  la funci�n polin�mica.
	 * @return the grado
	 */
	public int getGrado() {
		return grado;
	}

	/**
	 * Modifica el grado de la funci�n polin�mica
	 * @param	grado el nuevo grado 
	 */
	public void setGrado(int grado) {
		this.grado = grado;
	}

	/**
	 * Eval�a y regresa el valor del t�rmino.
	 * @param	x el valor de la variable independiente
	 * @return el valor evaluado.
	 */
	public BigDecimal valorImagen(BigDecimal x) {
		switch(this.funcion){
		case CONSTANTE:
			return getA();
		case POLINOMICA:
			return valorImagenPolinomica(x, getGrado());
		case TRIGONOMETRICA:
			return valorImagenTrigonometrica(x, getFunTrig(), isXinRadians(), isXinDegrees());
		case EXPONENCIAL:
			return getA().multiply(Big.exp(x.multiply(getB())));
		case LOGARITMICA:
			return getA().multiply(Big.log(x.multiply(getB())));
		case RACIONAL:
			break;
		default: 
			return null;
		}
		return null;
	}
	
	/**
	 * Eval�a y regresa el valor de un t�rmino tipo polin�mico.
	 * @param	x el valor de la variable independiente
	 * @param	g el grado del monomio
	 * @return el valor evaluado.
	 */
	private BigDecimal valorImagenPolinomica(BigDecimal x, int g) {
		return getA().multiply(x.pow(g));
	}
	
	/**
	 * Eval�a y regresa el valor de un t�rmino tipo trigonom�trico.
	 * @param	x el valor de la variable independiente
	 * @param	ft el tipo de funci�n trigonom�trica
	 * @param	rad si {@code x} est� en radianes
	 * @param	deg si {@code x} est� en grados
	 * @return el valor evaluado.
	 */
	private BigDecimal valorImagenTrigonometrica(BigDecimal x, FuncionTrig ft,
			boolean rad, boolean deg) {
		return getA().multiply(M.calculaTrig(ft, x.multiply(getB()), rad, deg));
	}

	/**
	 * Regresa la representaci�n general del t�rmino.
	 * @return la representaci�n general
	 */
	public String getGeneric() {
		return generic;
	}

	/**
	 * Regresa la representaci�n espec�fica del t�rmino.
	 * @return la representaci�n espec�fica
	 */
	public String getSpecific() {
		return specific;
	}

	/** Inicializa la representaci�n espec�fica y general del t�rmino. */
	public void initGenEsp(){
		String gS = "";
		switch(getFuncion()){
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
			
			switch(getFuncion()){
			case CONSTANTE:
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
	 * Crea un t�rmino con los par�metros m�nimos.
	 * Arroja un error si el tipo es {@link TipoFuncion#POLINOMICA} o 
	 * {@link TipoFuncion#TRIGONOMETRICA}
	 * El valor resultante del t�rmino va a depender de el tipo de funci�n
	 * {@code f} ya que es este quien define como calcularlo.
	 * @param	a el coeficiente del t�rmino
	 * @param	b el coeficiente de la variable
	 * @param	f el tipo de funci�n
	 * @exception	Exception lanzado si el tipo de funci�n es no esperado
	 */
	private Termino(BigDecimal a, BigDecimal b, TipoFuncion f)
			throws CustomException {
		if(f.equals(TipoFuncion.POLINOMICA)||f.equals(TipoFuncion.TRIGONOMETRICA)){
			throw CustomException.tipoIncorrecto();
		}else if((a.signum()==0)&&(f!=TipoFuncion.CONSTANTE)){
			throw CustomException.coefAeq0();
		}else{
			setFuncion(f);
		}
		setA(a);  setB(b);
		initGenEsp();
	}
	
	/**
	 * Crea un t�rmino de tipo {@link TipoFuncion#POLINOMICA}.
	 * 
	 * @param	a el coeficiente del t�rmino
	 * @param	g el grado del t�rmino
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
		setFuncion(TipoFuncion.POLINOMICA);
		initGenEsp();
	}
	
	/**
	 * Crea un t�rmino del tipo {@link TipoFuncion#TRIGONOMETRICA}.
	 * @param	a el coeficiente del t�rmino
	 * @param	b el coeficiente de la variable
	 * @param	ft el tipo de funci�n trigonom�trica
	 */
	private Termino(BigDecimal a, BigDecimal b, FuncionTrig ft)
			throws CustomException {
		if(a.signum()==0){
			throw CustomException.coefAeq0();
		}
		setA(a); setB(b);
		setFuncion(TipoFuncion.TRIGONOMETRICA);
		setFunTrig(ft); 
		initGenEsp();
	}
	
	/**
	 * 
	 * @param coef
	 * @return una funci�n constante
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
	 * @param coef	el coeficiente del t�rmino
	 * @param pos	la posici�n en la funci�n
	 * @return un termino de tipo polin�mico de grado {@code grado}
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
	 * @param ft	el tipo de funci�n trigonom�trica
	 * @param coefA	el coeficiente del t�rmino
	 * @param coefB	el coeficiente que acompa�a a x
	 * @param pos	la posici�n en la funci�n
	 * @return un termino de tipo trigonom�trico tipo {@code ft}
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
	 * @param coefA el coeficiente del t�rmino
	 * @param coefB el coeficiente que acompa�a a x
	 * @param i		la posici�n en la funci�n
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
	 * Crea un t�rmino de tipo logar�tmico con los par�metros dados.
	 * @param coefA el coeficiente del t�rmino
	 * @param coefB el coeficiente que acompa�a a x
	 * @param i		la posici�n en la funci�n
	 * @return un termino de tipo logar�tmico
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
	
}
