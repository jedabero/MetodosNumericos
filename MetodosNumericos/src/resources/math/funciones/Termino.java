/**
 * 
 */
package resources.math.funciones;

import java.math.BigDecimal;
import java.math.RoundingMode;

import resources.CustomException;
import resources.O;
import resources.math.Big;
import resources.math.Constantes.FuncionTrig;
import resources.math.Constantes.Tipo;
import resources.math.Interval;
import resources.math.M;

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
 * Es usada en la clase {@link Funcion} para definir cada un de los t�rminos.
 * <p>
 * Creada como redise�o de la clase {@link FuncionBase} antes usada para definir
 * funciones.
 * 
 * @author <a href="https://twitter.com/Jedabero" target="_blank">Jedabero</a>
 * @since 0.4
 */
public class Termino {
	
	/**
	 * T�rmino que representa el cero (0)
	 */
	public static Termino ZERO = Termino.constante(BigDecimal.ZERO);
	/**
	 * T�rmino que representa el uno (1)
	 */
	public static Termino ONE = Termino.constante(BigDecimal.ONE);
	
	private BigDecimal A;
	
	private BigDecimal B;
	
	private Tipo tipoFuncion;
	
	private FuncionTrig funTrig;
	private static boolean xInRadians = true;
	private static boolean xInDegrees = !xInRadians;
	
	private int grado;
	
	private Funcion funcInterna;
	private boolean hasIntern; 
	private boolean isInternMono; 
	
	private Termino multip;
	private boolean isMultip;
	
	private String generic;
	private String specific;

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
	public Tipo getTipoFuncion() {
		return tipoFuncion;
	}

	/**
	 * Modifica el actual tipo de funci�n.
	 * @param	tipoFuncion el nuevo tipo de funci�n.
	 */
	public void setTipoFuncion(Tipo tipoFuncion) {
		this.tipoFuncion = tipoFuncion;
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
	 * @return the funcInterna
	 */
	public Funcion getFuncInterna() {
		return funcInterna;
	}

	/**
	 * @param funcInterna the funcInterna to set
	 */
	public void setFuncInterna(Funcion funcInterna) {
		this.funcInterna = funcInterna;
	}

	/**
	 * @return the multip
	 */
	public Termino getMultip() {
		return multip;
	}

	/**
	 * @return the isMultip
	 */
	public boolean isMultip() {
		return isMultip;
	}

	/**
	 * @param multip the multip to set
	 */
	public void setMultip(Termino multip) {
		this.multip = multip;
	}

	/**
	 * Eval�a y regresa el valor del t�rmino.
	 * @param	x el valor de la variable independiente
	 * @return el valor evaluado.
	 */
	public BigDecimal valorImagen(BigDecimal x) {
		switch(this.tipoFuncion){
		case MONO:
			return x;
		case CONSTANTE:
			return getA();
		case POLINOMICA:
			return getA().multiply(getFuncInterna().valorImagen(x).pow(getGrado()));
		case TRIGONOMETRICA:
			return getA().multiply(M.calculaTrig(getFunTrig(),
					getFuncInterna().valorImagen(x).multiply(getB()),
					isXinRadians(), isXinDegrees()));
		case EXPONENCIAL:
			return getA().multiply(Big.exp(getFuncInterna().valorImagen(x).multiply(getB())));
		case LOGARITMICA:
			return getA().multiply(Big.ln(getFuncInterna().valorImagen(x).multiply(getB())));
		case RACIONAL:
			break;
		default: 
			return null;
		}
		return null;
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
	
	/** Inicializa la representaci�n general del t�rmino. */
	private void initGenericString(){
		String gS = "";
		Funcion fi = getFuncInterna();
		String fint = (hasIntern)?(isInternMono?"x":"("+fi.getGeneric()+")"):"null";
		
		switch(getTipoFuncion()){
		case MONO:
			gS += "x";
			break;
		case CONSTANTE:
			gS += "A";
			break;
		case POLINOMICA:
			int g = getGrado();
			switch(g){
			case 0: gS += "A"; break;
			case 1: gS += "A"+fint; break;
			default: gS += "A"+fint+"<sup>"+g+"</sup>"; break;
			}
			break;
		case TRIGONOMETRICA:
			FuncionTrig ft = getFunTrig();
			switch(ft){
			case SIN: gS += "Asin(B"+fint+")"; break;
			case COS: gS += "Acos(B"+fint+")"; break;
			case TAN: gS += "Atan(B"+fint+")"; break;
			case SEC: gS += "Asec(B"+fint+")"; break;
			case CSC: gS += "Acsc(B"+fint+")"; break;
			case COT: gS += "Acot(B"+fint+")"; break;
			default: gS += ""; break;
			}
			break;
		case EXPONENCIAL: gS += "Ae<sup>B"+fint+"</sup>"; break;
		case LOGARITMICA: gS += "Aln(B"+fint+")"; break;
		case RACIONAL: /* TODO RACIONAL */ break;
		default: break;
		}
		this.generic = gS;
	}
	
	/** Inicializa la representaci�n espec�fica del t�rmino. */
	private void initSpecificString(){
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
		
		Funcion fi = getFuncInterna();
		String fint = (hasIntern)?(isInternMono?"x":"("+fi.getSpecific()+")"):"null";
		
		if(!TermEq0||!TermEq1||!TermEqInf){
			if(signA==-1) sS +="- ";
			
			switch(getTipoFuncion()){
			case MONO:
				sS += "x";
				break;
			case CONSTANTE:
				sS += a.abs();
				break;
			case POLINOMICA:
				int g = getGrado();
				switch(g){
				case 0:
					sS += a.abs();
					break;
				case 1:
					if(!Aeq1) sS += a.abs();
					sS += fint;
					break;
				default:
					if(!Aeq1) sS += a.abs();
					sS += fint+"<sup>"+g+"</sup>";
					break;
				}
				break;
				
			case TRIGONOMETRICA:
				FuncionTrig ft = getFunTrig();
				if(!Beq0) {
					if(!Aeq1) sS += a.abs();
					
					sS += ft.toString().toLowerCase()+"(";
					
					if(signB==-1) sS +="-";
					if(!Beq1) sS += b.abs();
					sS += fint+")";
					break;
				} else {
					switch(ft){
					case SIN:
					case TAN:
						sS += "*0";
						break;
					case COS:
					case SEC:
						break;
					case CSC:
					case COT:
						sS += "*"+((signB==-1)?"-":"")+"Inf";
						break;
					default:
						break;
					}
				}
			case EXPONENCIAL:
				if(!Aeq1) sS += a.abs();
				sS += "e<sup>";
				if(signB==-1) sS +="-";
				if(!Beq1) sS += b.abs();
				sS += fint+"</sup>";
				break;
				
			case LOGARITMICA:
				if(!Aeq1) sS += a.abs();
				sS += "ln(";
				if(signB==-1) sS +="-";
				if(!Beq1) sS += b.abs();
				sS += fint+")";
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
	
	/** Inicializa la representaci�n espec�fica y general del t�rmino. */
	public void initGenEsp(){
		initGenericString();
		initSpecificString();
	}
	
	public String toString(){
		String tS = null;
		BigDecimal a = getA();
		BigDecimal b = getB();
		
		Funcion fi = getFuncInterna();
		String fint = (hasIntern)?(isInternMono?"x":"("+fi+")"):"null";
		
		switch(getTipoFuncion()){
		case MONO:
			tS = "x";
			break;
		case CONSTANTE:
			tS = ""+a;
			break;
		case POLINOMICA:
			tS = a+"*"+fint+"^"+getGrado();
			break;
		case TRIGONOMETRICA:
			tS = a+"*"+getFunTrig().toString().toLowerCase()+"("+b+"*"+fint+")";
			break;
		case EXPONENCIAL:
			tS = a+"*e^("+b+"*"+fint+")";
			break;
			
		case LOGARITMICA:
			tS = a+"*ln("+b+"*"+fint+")";
			break;
			
		case RACIONAL:
			break;
		default: break;
		}
		return tS;
	}
	
	/**
	 * Crea un t�rmino con unos par�metros predefinidos: f(x) = x
	 */
	public Termino(){
		A = BigDecimal.ONE;
		B = BigDecimal.ZERO;
		grado = 1;
		tipoFuncion = Tipo.MONO;
		funTrig = null;
		funcInterna = null;
		initGenEsp();
	}
	
	/**
	 * Crea un t�rmino con todos los par�metros.
	 * @param	a el coeficiente del t�rmino
	 * @param	b el coeficiente de la variable
	 * @param	f el tipo de funci�n
	 * @param	g el grado del t�rmino
	 * @param	ft el tipo de funci�n trigonom�trica
	 * @param	interna func�on interna (f(g(x)) 
	 * @param 	mult t�rmino que multiplica este t�rmino 
	 * @throws CustomException 
	 */
	public Termino(BigDecimal a, BigDecimal b, Tipo f, int g,
			FuncionTrig ft, Funcion interna, Termino mult) throws CustomException{
		A = a;
		B = b;
		tipoFuncion = f;
		grado = g;
		funTrig = ft;
		funcInterna = interna;
		multip = mult;
		if(mult != null){
			multip.isMultip = true;
		}
		hasIntern = funcInterna != null;
		isInternMono = (hasIntern)?(funcInterna.toString().compareTo("x") == 0):false;
		
		switch (tipoFuncion) {
		case MONO:
			
		case CONSTANTE:
			break;
		case POLINOMICA:
			if((g<1) || (g>999999999)){
				throw CustomException.gradoMenorQue1();
			}
			break;
		case TRIGONOMETRICA:
			switch(funTrig){
			case SIN:
			case COS:
			case TAN:
			case SEC:
				break;
			case CSC:
			case COT:
				if(b.signum()==0){
					throw CustomException.coeficienteIgual0(ft+"(0) = NaN");
				}
				break;
			default:
			}
			break;
		case EXPONENCIAL:
			break;
		case LOGARITMICA:
			if(b.signum()==0){
				throw CustomException.coeficienteIgual0("ln(0) = NaN");
			}
			break;
		case RACIONAL:
			break;
		case COMPUESTA:
			break;
		default:
			
			break;
		}
		
		initGenEsp();
	}
	
	/**
	 * Crea un termino constante cuya imagen siempre ser� el valor de coef.
	 * @param coef
	 * @return un termino constante
	 */
	public static Termino constante(BigDecimal coef){
		try{
			return new Termino(coef, BigDecimal.ZERO, Tipo.CONSTANTE, 0, null, null, null);
		}catch(CustomException ce){
			ce.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Crea un termino de tipo monomio
	 * @param grado	el grado del monomio
	 * @param coef	el coeficiente del t�rmino
	 * @param interna 
	 * @return un termino de tipo polin�mico de grado {@code grado}
	 */
	public static Termino monomio(int grado, BigDecimal coef, Funcion interna){
		try{
			if(interna != null){
				if (interna.getTipoFuncion().equals(Tipo.POLINOMICA)){
					throw CustomException.tipoIncorrecto();
				} else {
					return new Termino(coef, BigDecimal.ZERO, Tipo.POLINOMICA,
							grado, null, interna, null);
				}
			} else {
				Funcion mono = new Funcion(new Termino());
				return new Termino(coef, BigDecimal.ZERO, Tipo.POLINOMICA,
						grado, null, mono, null);
			}
		}catch(CustomException et){
			et.printStackTrace();
			O.pln(et.getMessage());
			return null;
		}
	}
	
	/**
	 * @param ft	el tipo de funci�n trigonom�trica
	 * @param coefA	el coeficiente del t�rmino
	 * @param coefB	el coeficiente que acompa�a a x
	 * @param interna 
	 * @return un termino de tipo trigonom�trico tipo {@code ft}
	 */
	public static Termino trigonometrico(FuncionTrig ft, BigDecimal coefA,
			BigDecimal coefB, Funcion interna){
		try{
			if(interna != null){
				return new Termino(coefA, coefB, Tipo.TRIGONOMETRICA,
						0, ft, interna, null);
			} else {
				Funcion mono = new Funcion(new Termino());
				return new Termino(coefA, coefB, Tipo.TRIGONOMETRICA, 0, ft,
						mono, null);
			}
		}catch(CustomException et){
			et.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @param coefA el coeficiente del t�rmino
	 * @param coefB el coeficiente que acompa�a a x
	 * @param interna 
	 * @return un termino de tipo exponencial
	 */
	public static Termino exponencial(BigDecimal coefA, BigDecimal coefB,
			Funcion interna){
		try{
			if(interna != null){
				return new Termino(coefA, coefB, Tipo.EXPONENCIAL, 0,
						null, interna, null);
			} else {
				Funcion mono = new Funcion(new Termino());
				return new Termino(coefA, coefB, Tipo.EXPONENCIAL, 0, null,
						mono, null);
			}
		}catch(CustomException et){
			et.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Crea un t�rmino de tipo logar�tmico con los par�metros dados.
	 * @param coefA el coeficiente del t�rmino
	 * @param coefB el coeficiente que acompa�a a x
	 * @param interna 
	 * @return un termino de tipo logar�tmico
	 */
	public static Termino logaritmo(BigDecimal coefA, BigDecimal coefB,
			Funcion interna){
		try{
			if(interna != null){
				return new Termino(coefA, coefB, Tipo.LOGARITMICA, 0,
						null, interna, null);
			} else {
				Funcion mono = new Funcion(new Termino());
				return new Termino(coefA, coefB, Tipo.LOGARITMICA, 0, null, mono, null);
			}
		}catch(CustomException et){
			String etm = et.getMessage();
			O.pln("Error al crear funci�n logar�tmica: "+etm);
			return null;
		}
	}
	
	/**
	 * @param t
	 * @return un t�rmino
	 * @throws CustomException si el termino t es diferente a este termino.
	 */
	public Termino suma(Termino t) throws CustomException {
		Termino temp = copia();
		if(getTipoFuncion().equals(t.getTipoFuncion())){
			switch (tipoFuncion) {
			case CONSTANTE:
				temp.actualiza(getA().add(t.getA()).stripTrailingZeros());
				break;
			case POLINOMICA:
				if(getGrado()==t.getGrado()){
					temp.actualiza(getA().add(t.getA()).stripTrailingZeros());
				}else{
					throw new CustomException("Termino a sumar de grado diferente.");
				}
				break;
			case TRIGONOMETRICA:
				if(getFunTrig().equals(t.getFunTrig())){
					temp.actualiza(getA().add(t.getA()).stripTrailingZeros());
				}else{
					throw new CustomException("Termino trigonometrico a sumar de tipo diferente.");
				}
				break;
			case EXPONENCIAL:
			case LOGARITMICA:
				if(getB().compareTo(t.getB())==0){
					temp.actualiza(getA().add(t.getA()).stripTrailingZeros());
				}else{
					throw new CustomException("Terminos incompatibles.");
				}
				break;
			case RACIONAL:
				break;
			default:
				
				break;
			}
			return temp;
		}else{
			throw CustomException.tipoIncorrecto();
		}
		
	}
	
	/**
	 * @param m
	 * @return un termino cuyo coeficiente a es multiplicado por m.
	 */
	public Termino multiplica(BigDecimal m){
		Termino t = copia();
		t.actualiza(getA().multiply(m).stripTrailingZeros());
		return t;
	}

	/*
	public Termino multiplicar(BigDecimal multiplicando){
		BigDecimal newA = getA().multiply(multiplicando);
		Termino multiplicado;
		return null;
	}
	*/
	
	/**
	 * @param m
	 * @return un termino multiplicado por otro
	 */
	public Termino multiplica(Termino m){
		Termino t = copia();
		//TODO MULTIPLICACION DE T�RMINOS!
		return t;
	}
	
	/**
	 * @return una copia de este termino
	 */
	public Termino copia(){
		Termino t = null;
		try {
			t = new Termino(getA(), getB(), getTipoFuncion(), getGrado(),
					getFunTrig(), getFuncInterna(), getMultip());
		} catch (CustomException e) {
			e.printStackTrace();
		}
		return t;
	}
	
	/**
	 * Actualiza todos los par�metros de la funci�n.
	 * @param a Constante A
	 * @param b Constante B
	 * @param tf Tipo de la funci�n
	 * @param g grado del polinomio, si es del tipo
	 * @param ft tipos de la funci�n trigonom�trica, si es del tipo
	 */
	private void actualiza(BigDecimal a, BigDecimal b, Tipo tf, int g,
			FuncionTrig ft){
		setA(a);
		setTipoFuncion(tf);
		switch (tf) {
		case POLINOMICA:
			setGrado(g);
			break;
		case TRIGONOMETRICA:
			setFunTrig(ft);
		default:
			setB(b);
			break;
		}
		
		initGenEsp();
		
	}
	
	/**
	 * Actualiza el par�metro de un t�rmino constante.
	 * @param a Coeficiente A
	 */
	public void actualiza(BigDecimal a){
		actualiza(a, getB(), getTipoFuncion(), getGrado(), getFunTrig());
	}
	
	/**
	 * Actualiza los par�metros del t�rmino polin�mico
	 * @param a Coeficiente A
	 * @param g Grado de la funci�n
	 */
	public void actualizaPol(BigDecimal a, int g){
		actualiza(a, getB(), getTipoFuncion(), g, getFunTrig());
	}
	
	/**
	 * Actualiza los par�metros del t�rmino trigonom�trico
	 * @param a coeficiente A
	 * @param b constante B
	 * @param ft tipo de la funci�n trigonom�trica
	 */
	public void actualizaTrig(BigDecimal a, BigDecimal b, FuncionTrig ft){
		actualiza(a, b, getTipoFuncion(), getGrado(), ft);
	}
	
	/**
	 * Actualiza las constantes A y B.
	 * @param a Coeficiente A
	 * @param b Constante B
	 */
	public void actualiza(BigDecimal a, BigDecimal b){
		actualiza(a, b, getTipoFuncion(), getGrado(), getFunTrig());
	}

	/**
	 * @param ab
	 * @return un intervalo cuyo m�nimo corresponde al valor de la funci�n en ab.min
	 * y cuyo m�ximo corresponde al valor de la funci�n en ab.m�x
	 */
	public Interval valoresExtremos(Interval ab){
		BigDecimal fa = valorImagen(ab.min());
		BigDecimal fb = valorImagen(ab.max());
		Interval fafb = new Interval(fa, fb);
		return fafb;
	}
	
	/**
	 * TODO derivada Trigonom�tricas, Logar�tmica, Racional y otras
	 * @return la derivada de este termino
	 */
	public Termino derivada(){
		switch (this.getTipoFuncion()) {
		case MONO:
			return Termino.ONE;
		case CONSTANTE:
			return Termino.ZERO;
		case POLINOMICA:
			BigDecimal ap = getA().multiply(BigDecimal.valueOf(getGrado()));
			int g = getGrado() - 1;
			switch (g) {
			case 0:
				return constante(ap);
			default:
				try {
					return monomio(g, ap, null);
				} catch (Exception e) {
					O.pln(e);
					return null;
				}
			}
		case TRIGONOMETRICA:
			BigDecimal at = getA().multiply(getB());
			FuncionTrig ft = null;
			switch (getFunTrig()) {
			case SIN:
				ft = FuncionTrig.COS;
				break;
			case COS:
				at = at.negate();
				ft = FuncionTrig.SIN;
				break;
			case TAN:
			case SEC:
			case CSC:
			case COT:
			default:
				break;
			}
			return trigonometrico(ft, at, getB(), null);
		case EXPONENCIAL:
			BigDecimal ae = getA().multiply(getB());
			return exponencial(ae, getB(), null);
		case LOGARITMICA:
		case RACIONAL:
		default: 
			return null;
		}
	}
	
	/** TODO integral Trigonom�tricas, Logar�tmica, Racional y otras
	 * @return la integral indefinida de este t�rmino
	 */
	public Termino integralIndef(){
		switch (getTipoFuncion()) {
		case CONSTANTE:
			return monomio(1, getA(), null);
		case POLINOMICA:
			int g = getGrado() + 1;
			BigDecimal a = getA().divide(BigDecimal.valueOf(g), 15, RoundingMode.UP);
			a = a.stripTrailingZeros();
			try {
				return monomio(g, a, null);
			} catch (Exception e) {
				O.pln(e);
				return null;
			}
			
		case TRIGONOMETRICA:
			BigDecimal at = getA().divide(getB(), 15, RoundingMode.UP);
			at = at.stripTrailingZeros();
			FuncionTrig ft = null;
			switch (getFunTrig()) {
			case SIN:
				at = at.negate();
				ft = FuncionTrig.COS;
				break;
			case COS:
				ft = FuncionTrig.SIN;
			case TAN:
			case SEC:
			case CSC:
			case COT:
			default:
				break;
			}
			return trigonometrico(ft, at, getB(), null);
		case EXPONENCIAL:
			BigDecimal ae = getA().divide(getB(), 15, RoundingMode.UP);
			ae = ae.stripTrailingZeros();
			return exponencial(ae, getB(), null);
		case LOGARITMICA:
		case RACIONAL:
		default: 
			return null;
		}
	}
	
	
	
	
	
}
