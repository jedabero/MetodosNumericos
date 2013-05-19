package funciones;

import java.math.BigDecimal;
import java.math.RoundingMode;

import resources.math.Constantes;
/**
 * Esta clase contiene la definici�n de una funci�n expl�cita b�sica.
 * Contiene las propiedades comunes que se encuentran en la mayor�a de las
 * funciones: variable dependiente <b>y</b>, variable independiente <b>x</b>,
 * un <b>intervalo</b> o dominio de la funci�n para evaluarla, un <b>paso</b> o
 * diferencia entre cada <b>x</b>, una cantidad de <b>t�rminos</b>, un
 * coeficiente <b>A</b> para cada t�rmino, y un conjunto de Strings que definir�n:
 * la <b>ecuaci�n</b> gen�rica (ej: mx + b), la <b>funci�n</b> (ej: 4x + 3) y el
 * <b>tipo de funci�n</b>.
 * @since 0.1
 * @deprecated desde la versi�n 0.4, por {@link Funcion}
 * @see Funcion
 * @author <a href="https://twitter.com/Jedabero" target="_blank">Jedabero</a>
 *
 */
public class FuncionBase implements Constantes{
	/** La ecuaci�n gen�rica de la funci�n. */
	private String ecuacion;
	/** La ecuaci�n expl�cita de la funci�n. */
	private String funcion;
	/** El tipo de funci�n.  */
	private Tipo tipo;
	/** Cantidad de ecuaciones creadas. */
	private static int numeroEcuacion = 0;
	
	protected int grado;
	
	/** N�mero de t�rminos en la ecuaci�n. */
	private int terminos;
	/** Array con los limites en los que ser� evaluado la funci�n. */
	private static BigDecimal intervalo[];
	/** La longitud de paso. */
	private static BigDecimal paso;
	private static BigDecimal pasoMin;
	private static BigDecimal pasoMax;
	/** Array con los valores de x. */
	private BigDecimal x[];
	/** Array con los valores de y. */
	private BigDecimal y[];
	/** Array con las constantes de cada t�rmino. */
	protected BigDecimal A[];
	/** Array con las constantes que multiplican a cada x */
	protected BigDecimal B[];
	
	/**
	 * Regresa un String con la ecuaci�n de la funci�n
	 * @return la ecuaci�n.
	 */
	public String getEcuacion() {
		return ecuacion;
	}

	/**
	 * Modifica la ecuaci�n de la funci�n con una nueva.
	 * @param e la ecuaci�n nueva.
	 */
	public void setEcuacion(String e) {
		this.ecuacion = e;
	}

	/**
	 * Regresa el nombre de la funci�n.
	 * @return el nombre de la funci�n.
	 */
	public String getFuncion() {
		return funcion;
	}

	/**
	 * Modifica el nombre de la funci�n.
	 * @param f el nuevo nombre de la funci�n
	 */
	public void setFuncion(String f) {
		this.funcion = f;
	}

	/**
	 * Regresa el n�mero correspondiente con el tipo de funci�n.
	 * @return the tipo
	 */
	public Tipo getTipoFuncion() {
		return tipo;
	}

	/**
	 * Modifica el tipo de funci�n.
	 * @param tipo el tipo de funci�n.
	 */
	public void setTipoFuncion(Tipo tipo) {
		this.tipo = tipo;
	}

	/**
	 * Regresa el n�mero de ecuaciones creadas hasta el punto de llamado.
	 * @return the numEc
	 */
	public static int getNumeroEcuacion() {
		return numeroEcuacion;
	}

	/** 
	 * Regresa el n�mero de t�rminos en la ecuaci�n.
	 * @return the t�rminos
	 */
	public int getTerminos() {
		return terminos;
	}

	/** 
	 * Modifica el n�mero de t�rminos en la ecuaci�n.
	 * @param t el n�mero de t�rminos en la ecuaci�n.
	 */
	public void setTerminos(int t) {
		terminos = t;
	}
	
	/**
	 * Regresa el intervalo en el que la funci�n es evaluada.
	 * @return the intervalo
	 */
	public static BigDecimal[] getIntervalo() {
		return intervalo;
	}

	/**
	 * Modifica el intervalo en el que se eval�a la funci�n.
	 * Se debe llamar cada vez que se imponen nuevos intervalos
	 * @param interval el intervalo nuevo
	 */
	public static void setIntervalo(BigDecimal[] interval) {
		intervalo = interval;
		initMinMaxPaso();
	}

	/**
	 * Regresa el valor del paso.
	 * @return the paso
	 */
	public static BigDecimal getPaso() {
		return paso;
	}
	
	/**
	 * Regresa el valor m�nimo del paso.
	 * @return el paso m�nimo posible.
	 */
	public static BigDecimal getPasoMin() {
		return pasoMin;
	}
	
	/**
	 * Regresa el valor m�ximo del paso.
	 * @return el paso m�ximo posible.
	 */
	public static BigDecimal getPasoMax() {
		return pasoMax;
	}

	/**
	 * Modifica el valor del paso
	 * @param p the paso to set
	 */
	public static void setPaso(BigDecimal p) {
		if(p.compareTo(getPasoMax())==1){
			paso = getPasoMax();
		}else if(p.compareTo(getPasoMin())==-1){
			paso = getPasoMin();
		}else{
			paso = p;
		}
	}
	
	/**
	 * 
	 */
	public static void initMinMaxPaso(){
		BigDecimal intervaloDif = getIntervalo()[1].subtract(getIntervalo()[0]);
		pasoMax = intervaloDif.divide(BigDecimal.valueOf(4));
		pasoMin = intervaloDif.divide(BigDecimal.valueOf(10000));
	}
	
	/**
	 * Regresa un array con los valores de x.
	 * @return el array de x
	 */
	public BigDecimal[] getX() {
		return x;
	}

	/**
	 * Modificaci�n y combinaci�n de los m�todos setX() y setY().
	 * Inicializa el array de valores de x de acuerdo al paso y a los intervalos. 
	 * Inicializa el array del valores y al valor de BigDecima.ONE uno (1)
	 * Se debe llamar cada vez que se modifica el paso o los intervalos. 
	 */
	public void initXAndYArrays() {
		int l = getIntervalo()[1].subtract(getIntervalo()[0]).divide(paso, 5,
				RoundingMode.HALF_UP).intValue();
		x = new BigDecimal[l + 1];
		for(int i=0;i<x.length;i++){
			x[i] = getIntervalo()[0].add(paso.multiply(BigDecimal.valueOf(i)));
		}
		
		y = new BigDecimal[l + 1];
		for(int i=0;i<y.length;i++) y[i]=BigDecimal.ZERO;
		
	}

	/**
	 * Regresa un array con los valores de y
	 * @return el array de y
	 */
	public BigDecimal[] getY() {
		return y;
	}
	
	/**
	 * Calcula el valor de cada una de las <b>y</b> de acuerdo a la ecuaci�n.
	 */
	protected void calculaY(){}
	
	/**
	 * Regresa un array con los coeficientes de cada t�rmino en la funci�n.
	 * @return el array de coeficientes.
	 */
	public BigDecimal[] getA() {
		return A;
	}
	
	/**
	 * Modifica el array de coeficientes de la ecuaci�n.
	 * En caso de que el nuevo array no concuerde con el grado de la ecuaci�n
	 * se reinicia con initA().
	 * @param a el array con las nuevos coeficientes.
	 */
	public void setA(BigDecimal a[]){
		if(a.length==getTerminos()){
			this.A = a;
		}else{
			System.out.println("A Array did not match the grade length.");
			initA();
		}
	}

	/**
	 * Inicia el arreglo que contiene los coeficientes.
	 * Todos los coeficientes quedan con el valor BigDecimal.ONE uno (1).
	 */
	public void initA() {
		A = new BigDecimal[getTerminos()];
		for(int i=0;i<A.length;i++){
			A[i] = BigDecimal.ONE;
		}
	}
	/**
	 * Regresa un array con los coeficientes B.
	 * @return the b
	 */
	public BigDecimal[] getB() {
		return B;
	}

	/**
	 * Modifica el array de coeficientes B de la ecuaci�n.
	 * @param b 
	 */
	public void setB(BigDecimal b[]) {
		if(b.length==getTerminos()){
			this.B = b;
		}else{
			System.out.println("B Array did not match the grade length.");
			initB();
		}
	}
	/**
	 * Inicia el arreglo que contiene los coeficientes B.
	 * Todos los coeficientes quedan con el valor BigDecimal.ONE uno (1).
	 */
	public void initB() {
		B = new BigDecimal[getTerminos()];
		for(int i=0;i<B.length;i++){
			B[i] = BigDecimal.ONE;
		}
	}
	
	/**
	 * Inicializa los Strings de la funci�n y de la ecuaci�n.
	 */
	protected void initFuncionStrings(){}
	
	protected FuncionTrig[] tipos;

	/**
	 * @return the tipo
	 */
	public FuncionTrig[] getTipos() {
		return tipos;
	}
	
	/**
	 * Constructor para una funci�n b�sica.
	 * @param tipo el tipo de funci�n
	 * @param t el n�mero de t�rminos en la ecuaci�n.
	 */
	public FuncionBase(Tipo tipo, int t){
		setTipoFuncion(tipo);
		setTerminos(t);
		initA();
		initB();
		initXAndYArrays();
		
		numeroEcuacion++;
	}
	
	/**
	 * M�todo para actualizar los par�metros de la ecuaci�n.
	 * @param p el paso
	 * @param t t�rminos
	 * @param interval el intervalo.
	 * @param a array de constantes de cada t�rmino
	 * @param b array de constantes de x
	 * @param tipo 
	 */
	public void update(BigDecimal p, int t, BigDecimal[] interval,
			BigDecimal[] a, BigDecimal[] b, Tipo tipo){
		setTipoFuncion(tipo);
		setIntervalo(interval);
		setPaso(p);
		setTerminos(t);
		setA(a); setB(b);
		try{
			initFuncionStrings();
		}catch(Exception ex){
			System.out.println(ex.toString()+" (FuncionBase.java:300)");
		}
		initXAndYArrays();
		calculaY();
	}
	
	/**
	 * @param p
	 * @param interval
	 */
	public void updatePasoIntervalo(BigDecimal p, BigDecimal[] interval){
		update(p, getTerminos(), interval, getA(), getB(), getTipoFuncion());
	}
	
	/**
	 * @param t
	 * @param a
	 * @param b 
	 */
	public void updateConstantesTerminos(int t, BigDecimal[] a, BigDecimal[] b){
		update(getPaso(), t, getIntervalo(), a, b, getTipoFuncion());
	}
	
	/**
	 * @param tipo
	 */
	public void updateTipo(Tipo tipo){
		update(getPaso(), getTerminos(), getIntervalo(), getA(), getB(), tipo);
	}
	
	
	/**
	 * @return el grado de  la funci�n polin�mica.
	 */
	public int getGrado() {
		return (getTerminos()-1);
	}

	/**Modifica el grado de la funci�n polin�mica
	 * @param g
	 */
	public void setGrado(int g) {
		setTerminos(g+1);
	}
	
}
