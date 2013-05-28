/**
 * 
 */
package funciones;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.ListIterator;

import resources.CustomException;
import resources.O;
import resources.math.Big;
import resources.math.Constantes.FuncionTrig;
import resources.math.Constantes.Tipo;
import resources.math.Interval;
import vectores.Matriz;
import vectores.SistemaEcuacionesLineales;

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
public class Funcion {
	
	/**
	 * Función que representa el cero
	 */
	public static Funcion ZERO = new Funcion(Termino.ZERO);
	
	private ArrayList<Termino> terminos;
	private Tipo tipo;
	
	private String generic;
	private String specific;
	private String toString;
	
	/**
	 * @return el Tipo
	 */
	public Tipo getTipoFuncion() {
		return tipo;
	}

	/**
	 * @param tipo Tipo 
	 */
	public void setTipoFuncion(Tipo tipo) {
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
		String s = "";
		toString = "";
		
		for (iterator = getTerminos().listIterator(); iterator.hasNext();) {
			Termino term = iterator.next();
			boolean positiveA = term.getA().signum()>=0;
			boolean indexIs0 = iterator.previousIndex()==0;
			g += (indexIs0?"":(positiveA?" + ":" - "))+ term.getGeneric();
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
		this.setTipoFuncion(t.getTipoFuncion());
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
		if(coefs[0]==null) throw new CustomException("A<sub>0</sub> = null");
		if(coefs[0].signum() != 0){
			alT.add(Termino.constante(coefs[0]));	
		}
		for(int i=1;i<=n;i++){
			if(coefs[i]==null) throw new CustomException("A<sub>"+i+"</sub> = null");
			if(coefs[i].signum() != 0){
				alT.add(Termino.monomio(i, coefs[i], null));	
			}
		}
		
		Funcion f = new Funcion(alT);
		f.setTipoFuncion(Tipo.POLINOMICA);
		return f;
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
		alT.add(Termino.trigonometrico(ft, coefA, coefB, null));
		Funcion f = new Funcion(alT);
		f.setTipoFuncion(Tipo.TRIGONOMETRICA);
		return f;
	}
	
	//suma
	/**
	 * @param t
	 * @return la suma de esta función y el término t
	 * @throws CustomException
	 */
	public Funcion sumar(Termino t)throws CustomException {
		Funcion tempF = copia();
		switch (t.getTipoFuncion()) {
		case CONSTANTE:
		case POLINOMICA:
			boolean added = false;
			ArrayList<Termino> alt = tempF.getTerminos();
			for (int i = 0; i < alt.size(); i++) {
				Termino tempT = alt.get(i);
				if(tempT.getGrado()==t.getGrado()){
					tempT = tempT.suma(t);
					alt.set(i, tempT);
					added = true;
				}
			}
			if (!added) {
				tempF.getTerminos().add(t);
			}
			break;

		default:
			//TODO soportar sumas de términos de tipos que no sean POLINOMIO
			throw new CustomException("tipo de suma no soportado todavía");
		}
		tempF.initGenEsp();
		return tempF;
	}
	
	/**
	 * @param f
	 * @return well this is obviuos now
	 * @throws CustomException
	 */
	public Funcion sumar(Funcion f){
		Funcion temp = copia();
		for (int i = 0; i < f.getTerminos().size(); i++) {
			try {
				temp = temp.sumar(f.getTerminos().get(i));
			} catch (CustomException e) {
				e.printStackTrace();
			}
		}
		return temp;
	}
	
	/**
	 * @param vF
	 * @return la función resultante de la suma de las funciones en el vector vF
	 */
	public static Funcion sumar(Funcion vF[]){
		Funcion sum = ZERO;
		for (int i = 0; i < vF.length; i++) {
			sum = sum.sumar(vF[i]);
		}
		return sum;
	}
	
	//resta
	//multiplicación
	/**
	 * @param multiplicando
	 * @return la función multiplicada por multiplicando
	 */
	public Funcion multiplica(BigDecimal multiplicando){
		ArrayList<Termino> alt = new ArrayList<Termino>();
		for (Termino termino : getTerminos()) {
			alt.add(termino.multiplica(multiplicando));
		}
		Funcion tempF = new Funcion(alt);
		return tempF;
	}

	//división?
	/**
	 * @return una copia de esta función
	 */
	public Funcion copia(){
		ArrayList<Termino> alT = new ArrayList<Termino>();
		for (Termino termino : getTerminos()) {
			alT.add(termino);
		}
		return new Funcion(alT);
	}
	
	/**
	 * Crea una función que representa (x + a)^n
	 * @param n el grado
	 * @param a la constante a 
	 * @param sign el signo del binomio
	 * @return (x+a)^n desarrollado
	 * @throws Exception 
	 */
	public static Funcion binomionN(int n, BigDecimal a){
		BigDecimal temps[] = new BigDecimal[n+1];
		for (int i = 0; i < temps.length; i++) {
			try {
				temps[i] = Big.combinatoria(n, i).multiply(a.pow(i));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		BigDecimal coefs[] = new BigDecimal[n+1];
		for (int i = 0; i < coefs.length; i++) {
			coefs[i] = temps[n-i];
		}
		
		try {
			return Funcion.polinomio(n, coefs);
		} catch (CustomException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @param ai el array con las constantes
	 * @return La función (x + a0)(x + a1)...(x + ai)...(x + an) desarrollada. 
	 * @throws CustomException
	 */
	public static Funcion polinomioProductorio(BigDecimal ai[]) throws CustomException {
		BigDecimal temps[] = new BigDecimal[ai.length+1];
		temps[0] = BigDecimal.ONE;
		for (int i = 1; i < temps.length; i++) {
			try {
				temps[i] = Big.sumaCombinaciones(ai, i);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		BigDecimal coefs[] = new BigDecimal[temps.length];
		for (int i = 0; i < coefs.length; i++) {
			coefs[i] = temps[temps.length-1-i];
		}
		return Funcion.polinomio(ai.length, coefs);
	}
	
	/**
	 * Crea una función a partir de un conjunto de puntos
	 * @param x puntos
	 * @param fx valor de la función en los puntos x
	 * @return una función polinómica aproximada a los puntos dados
	 * @throws Exception 
	 */
	public static Funcion aproximacionPolinomialLangrange(BigDecimal x[],
			BigDecimal fx[]) throws Exception {
		int numPuntos = x.length;
		if(numPuntos!=fx.length){	//Se verifica que los puntos estén completos
			throw CustomException.arrayIncompleto();
		}else{
			//Este vector corresponde a los polinomios formados 
			//por cada término de la sumatoria.
			Funcion polsLagr[] = new Funcion[numPuntos];
			for (int i = 0; i < numPuntos; i++) {
				//divisor = Productoria(Xi - Xj)
				BigDecimal divisor = Big.productoDiferencias(i, x);
				//F(Xi) y cada divisor no dependen de X, por lo tanto su división
				//es una constante que luego será multiplicada a cada polinomio
				BigDecimal fxi_PIdxi = fx[i].divide(divisor, 10, RoundingMode.HALF_UP);
				//(x-x0)(x-x1)...(x-xn)
				BigDecimal negativeXs[] = new BigDecimal[numPuntos];
				negativeXs = Big.removeElementAt(i, x);
				for (int j = 0; j < negativeXs.length; j++) {
					negativeXs[j] = negativeXs[j].negate();
				}
				polsLagr[i] = Funcion.polinomioProductorio(negativeXs);
				polsLagr[i] = polsLagr[i].multiplica(fxi_PIdxi);
				
			}
			
			return Funcion.sumar(polsLagr);
		}
		
	}
	
	/**
	 * @param x
	 * @param fx
	 * @return una función polinómica aproximada a los puntos dados
	 * @throws Exception
	 */
	public static Funcion aproximacionPolinomialNewton(BigDecimal x[],
			BigDecimal fx[]) throws Exception {
		int numPuntos = x.length;
		if(numPuntos!=fx.length){	//Se verifica que los puntos estén completos
			throw CustomException.arrayIncompleto();
		}else{
			//Se calculan todas las diferencias divididas
			BigDecimal dd[] = Big.listaDiferenciasDivididas(x, fx);
			//Se calculan cada una de las funciones
			Funcion[] parte = new Funcion[numPuntos];
			parte[0] = new Funcion(Termino.constante(dd[0]));
			for (int i = 1; i < parte.length; i++) {
				BigDecimal a[] = new BigDecimal[i];
				for (int j = 0; j < a.length;a[j] = x[j].negate(), j++);
				parte[i] = polinomioProductorio(a).multiplica(dd[i]);
			}
			
			return sumar(parte);
		}
	}
	
	/**
	 * Crea una función a partir de un conjunto de puntos
	 * @param x puntos
	 * @param fx valor de la función en los puntos x
	 * @return una función polinómica aproximada a los puntos dados
	 * @throws Exception 
	 */
	public static Funcion aproximacionPolinomialSimple(BigDecimal x[],
			BigDecimal fx[]) throws Exception {
		int numPuntos = x.length;
		if(numPuntos!=fx.length){	//Se verifica que los puntos estén completos
			throw CustomException.arrayIncompleto();
		}else{
			//Inicialización del SEL
			BigDecimal matriz[][] = new BigDecimal[numPuntos][numPuntos+1];
			for (int i = 0; i < numPuntos; i++) {
				for (int j = 0; j < numPuntos; j++) {
					matriz[i][j] = x[i].pow(j);
				}
				matriz[i][numPuntos] = fx[i];
			}
			SistemaEcuacionesLineales sel = new SistemaEcuacionesLineales(matriz);
			
			//Se resuelve el sistema por el método de Cramer
			Matriz coef = sel.metodoCramer();
			
			//Por último se pasa la matriz 1xn a un vector n
			BigDecimal coefs[] = new BigDecimal[numPuntos];
			for (int i = 0; i < coefs.length; i++) {
				coefs[i] = coef.getMatriz()[i][0].stripTrailingZeros();
			}
			
			//Finalmente se crea el polinomio.
			return Funcion.polinomio(numPuntos-1, coefs);
			
		}
	}
	
	/**
	 * @param ab
	 * @return un intervalo cuyo mínimo corresponde al valor de la función en ab.min
	 * y cuyo máximo corresponde al valor de la función en ab.máx
	 */
	public Interval valoresExtremos(Interval ab){
		BigDecimal fa = valorImagen(ab.min());
		BigDecimal fb = valorImagen(ab.max());
		Interval fafb = new Interval(fa, fb);
		return fafb;
	}
	
	/**
	 * @return la derivada de esta función
	 */
	public Funcion derivada(){
		ArrayList<Termino> alT = new ArrayList<Termino>();
		for (ListIterator<Termino> iterator = getTerminos().listIterator();
				iterator.hasNext();) {
			alT.add(iterator.next().derivada()); 
		}
		return new Funcion(alT);
	}

	/**
	 * @return la integral indefinida de esta función
	 */
	public Funcion integralIndef(){
		ArrayList<Termino> alT = new ArrayList<Termino>();
		for (ListIterator<Termino> iterator = getTerminos().listIterator();
				iterator.hasNext();) {
			alT.add(iterator.next().integralIndef()); 
		}
		return new Funcion(alT);
	}
	
	/**
	 * Calcula el valor de la integral de la función desde a hasta b por el
	 * método trapezoidal simple.
	 * @param ab intervalo de integración
	 * @param scale 
	 * @return el valor de la integral de la función desde a hasta b.
	 */
	public BigDecimal integracionTrapecioSimple(Interval ab){
		Interval fafb = valoresExtremos(ab);//Se obtiene fa y fb
		return ab.length().multiply(fafb.centre());
	}
	
	/**
	 * Calcula el valor de la integral de la función desde a hasta b por el
	 * método trapezoidal compuesto.
	 * @param ab intervalo de integración
	 * @param n subintervals
	 * @param scale 
	 * @return el valor de la integral de la función desde a hasta b.
	 */
	public BigDecimal integracionTrapecioCompuesto(Interval ab, int n){
		Interval fab = valoresExtremos(ab);//Se obtiene fa y fb
		//Se halla la distancia entre puntos
		BigDecimal h = ab.length().divide(BigDecimal.valueOf(n), 15, RoundingMode.HALF_UP);
		h = h.stripTrailingZeros();
		//Luego se hallan los puntos entre a y b y los valores de la función en estos
		BigDecimal x[] = new BigDecimal[n-1];
		BigDecimal fx[] = new BigDecimal[n-1];
		for (int i = 0; i < x.length; i++) {
			x[i] = ab.min().add(h.multiply(BigDecimal.valueOf(i+1)));
			fx[i] = valorImagen(x[i]).stripTrailingZeros();
		}
		//Se suman cada uno
		BigDecimal sumatfx = Big.suma(fx);
		//Luego se multiplica la suma por 2 y se añaden fa y fb 
		sumatfx = sumatfx.multiply(BigDecimal.valueOf(2)).add(fab.min()).add(fab.max());
		//El resultado se multiplica por h/2
		BigDecimal res = h.divide(BigDecimal.valueOf(2)).multiply(sumatfx);
		
		return res.stripTrailingZeros();
	}
	
	/**
	 * Calcula el valor de la integral de la función desde a hasta b por el
	 * método Simpson simple 1/3.
	 * @param ab intervalo de integración
	 * @param scale 
	 * @return el valor de la integral de la función desde a hasta b.
	 */
	public BigDecimal integracionSimpsonSimple1_3(Interval ab){
		Interval fab = valoresExtremos(ab);//Se obtiene fa y fb
		//Se halla h
		BigDecimal h = ab.centre().subtract(ab.min()).stripTrailingZeros();
		//Luego se halla x1 y f(x1)
		BigDecimal x1 = ab.centre().stripTrailingZeros();
		BigDecimal fx1 = valorImagen(x1);
		//Se suman cada uno
		BigDecimal sumatfx = fx1; 
		sumatfx = sumatfx.multiply(BigDecimal.valueOf(4)).add(fab.min()).add(fab.max());
		//El resultado se multiplica por h/3
		BigDecimal res = h.divide(BigDecimal.valueOf(3), 15, RoundingMode.HALF_UP);
		res = res.multiply(sumatfx);
		return res.stripTrailingZeros();
	}
	
	/**
	 * Calcula el valor de la integral de la función desde a hasta b por el
	 * método Simpson simple 3/8.
	 * @param ab intervalo de integración
	 * @param scale 
	 * @return el valor de la integral de la función desde a hasta b.
	 */
	public BigDecimal integracionSimpsonSimple3_8(Interval ab){
		Interval fab = valoresExtremos(ab);//Se obtiene fa y fb
		//Se halla la distancia entre puntos
		BigDecimal h = ab.length().divide(BigDecimal.valueOf(3), 15, RoundingMode.HALF_UP);
		h = h.stripTrailingZeros();
		//Luego se hallan los puntos entre a y b y los valores de la función en estos
		BigDecimal x1 = ab.min().add(h);
		BigDecimal x2 = ab.max().subtract(h);
		BigDecimal fx1 = valorImagen(x1);
		BigDecimal fx2 = valorImagen(x2);
		//Se suman cada uno
		BigDecimal sumatfx = fx1.add(fx2);
		//Luego se multiplica la suma por 2 y se añaden fa y fb 
		sumatfx = sumatfx.multiply(BigDecimal.valueOf(3)).add(fab.min()).add(fab.max());
		//El resultado se multiplica por h/2
		BigDecimal res = h.multiply(BigDecimal.valueOf(0.375)).multiply(sumatfx);
		return res.stripTrailingZeros();
	}
	
	/**
	 * Calcula el valor de la integral de la función desde a hasta b por el
	 * método Simpson compuesto.
	 * @param ab intervalo de integración
	 * @param n subintervals
	 * @param scale 
	 * @return el valor de la integral de la función desde a hasta b.
	 */
	public BigDecimal integracionSimpsonCompuesto(Interval ab, int n){
		n = (n%2==0)? n : n+1; //Se fuerza n como par
		Interval fab = valoresExtremos(ab);//Se obtiene fa y fb
		//Se halla la distancia entre puntos
		BigDecimal h = ab.length().divide(BigDecimal.valueOf(n), 15, RoundingMode.HALF_UP);
		h = h.stripTrailingZeros();
		//Luego se hallan los puntos entre a y b y los valores de la función en estos
		BigDecimal x[] = new BigDecimal[n-1];
		BigDecimal fx[] = new BigDecimal[n-1];
		for (int i = 0; i < x.length; i++) {
			x[i] = ab.min().add(h.multiply(BigDecimal.valueOf(i+1)));
			fx[i] = valorImagen(x[i]).stripTrailingZeros();
		}
		
		BigDecimal sumaimp = Big.sumaPosPares(fx);//Se suman los impares
		BigDecimal sumapar = Big.sumaPosImpares(fx);//Se suman los pares
		//Se multiplica sumaimp por 4
		BigDecimal sumatfx = sumaimp.multiply(BigDecimal.valueOf(4));
		//Se multiplica sumapar por 2 y se añade al acumulado
		sumatfx = sumatfx.add(sumapar.multiply(BigDecimal.valueOf(2)));
		//Luego se añaden fa y fb 
		sumatfx = sumatfx.add(fab.min()).add(fab.max());
		//El resultado se multiplica por h/2
		BigDecimal res = h.divide(BigDecimal.valueOf(3), 15, RoundingMode.HALF_UP);
		res = res.multiply(sumatfx);
		return res.stripTrailingZeros();
	}
	
	private Funcion mpfQx(){
		Termino t0 = getTerminos().get(0);
		System.out.println("T0: "+t0);
		int t0g = t0.getGrado();
		ArrayList<Termino> alF = new ArrayList<Termino>();
		for (int i = 1; i < getTerminos().size(); i++) {
			Termino ti = getTerminos().get(i).copia();
			int tempg = ti.getGrado();
			if(tempg<t0g+2) {
				alF.add(Termino.constante(ti.getA()));
			} else {
				alF.add(Termino.monomio(ti.getGrado()-t0g-1, ti.getA(), ti.getFuncInterna()));
			}
		}

		Funcion gx = new Funcion(alF);
		return gx;
	}
	
	/**
	 * @param tol tolerancia del error
	 * @param maxIt máximo número de iteraciones
	 * @param x0 punto inicial
	 * @return la raíz más cercana a x0 y el error
	 * @throws Exception 
	 */
	public BigDecimal[] metodoPuntoFijo(BigDecimal tol, int maxIt, BigDecimal x0)
			throws Exception{
		//Obtener g(x)
		Funcion qx = mpfQx();
		System.out.println("gx: "+qx);
		Termino t0 = getTerminos().get(0);
		BigDecimal err = null;
		boolean fin = false;	//Switch
		int k = 0;				//Índice de la iteración
		BigDecimal xr = BigDecimal.ZERO;
		while((!fin)&&(k<=maxIt)){
			System.out.println("x0 = "+x0);
			BigDecimal e = xr.subtract(x0).abs();	//Error inicial
			System.out.println("mpf err: "+e);		//TODO DETECT ERROR CHANGE
			
			xr = t0.getA().negate().divide(qx.valorImagen(x0),
					tol.scale()+3, RoundingMode.HALF_UP);
			System.out.println("xr = "+xr+"\n");
			if (e.compareTo(tol)<1) {	//Error igual o por debajo de la tolerancia?
				fin = true;
			}
			k++;	//Siguiente iteración
			//Asignación de variables para siguiente iteración
			BigDecimal temp = x0;
			x0 = xr;
			xr = temp;
			err = e;
		}
		
		if (fin) {
			O.pln("x = "+x0);
			return new BigDecimal[]{x0, err};
		} else {
			throw new Exception("No converge dentro del valor máximo de iteración");
		}
		
	}
	
	private boolean rootExistentialityCriterion(Interval ab){
		Interval fab = valoresExtremos(ab);
		BigDecimal ce = fab.min().multiply(fab.max());
		switch (ce.signum()) {
		case 0:
		case -1:
			return true;
		case 1:
		default:
			return false;
		}
	}
	
	/**
	 * @param tol tolerancia del error
	 * @param maxIt máximo número de iteraciones
	 * @param ab intervalo a evaluar
	 * @return la raíz dentro del intervalo [a,b] y el error
	 * @throws Exception
	 */
	public BigDecimal[] metodoBiseccion(BigDecimal tol, int maxIt, Interval ab)
			throws Exception {
		if (rootExistentialityCriterion(ab)) {
			BigDecimal err = null;
			boolean fin = false;	//Switch
			int k = 0;				//Índice de la iteración
			BigDecimal xa = BigDecimal.ZERO;
			while((!fin)&&(k<=maxIt)){
				BigDecimal xm = ab.centre(tol.scale()+3);	//valor medio
				if(xm.signum()==0){	//En caso de que el centro sea cero se debe
									//evitar convergencia inmediata
					xm = xm.add(tol.movePointRight(1));
				}
				BigDecimal e = xa.subtract(xm).abs();	//Error inicial
				if (e.compareTo(tol)<1) {	//Error igual o por debajo de la tolerancia?
					fin = true;
				}else{
					BigDecimal fxm = valorImagen(xm);
					if (fxm.signum()>0) {
						ab.setMax(xm);
					} else {
						ab.setMin(xm);
					}
				}
				xa = xm;
				k++;
				err = e;
			}
			
			if (fin) {
				O.pln("x = "+xa);
				return new BigDecimal[]{xa, err};
			} else {
				throw new Exception("No converge dentro del valor máximo de iteración");
			}
			
		} else {
			throw new Exception("No existe la raíz dentro del intervalo.");
		}
	}
	
	/**
	 * @param tol tolerancia del error
	 * @param maxIt máximo número de iteraciones
	 * @param x0 punto inicial
	 * @return la raíz más cercana a x0 y el error
	 * @throws Exception
	 */
	public BigDecimal[] metodoNewtonRaphson(BigDecimal tol, int maxIt,
			BigDecimal x0) throws Exception {
		BigDecimal err = null;
		boolean fin = false;	//Switch
		int k = 0;				//Índice de la iteración
		while((!fin)&&(k<=maxIt)){
			BigDecimal fx = valorImagen(x0);
			BigDecimal fpx = derivada().valorImagen(x0);
			BigDecimal fx_fpx = fx.divide(fpx, tol.scale()+3, RoundingMode.HALF_UP);
			BigDecimal xr = x0.subtract(fx_fpx);
			BigDecimal e = x0.subtract(xr).abs();	//Error inicial
			if (e.compareTo(tol)<1) {	//Error igual o por debajo de la tolerancia?
				fin = true;
			}
			BigDecimal temp = x0;
			x0 = xr;
			xr = temp;
			k++;
			err = e;
		}
		
		if (fin) {
			O.pln("x = "+x0);
			return new BigDecimal[]{x0, err};
		} else {
			throw new Exception("No converge dentro del valor máximo de iteración");
		}
	}
	
	/**
	 * @param tol tolerancia del error
	 * @param maxIt máximo número de iteraciones
	 * @param x0 punto inicial
	 * @param x1 punto secundario
	 * @return la raíz más cercana a x0 y x1 y el error
	 * @throws Exception
	 */
	public BigDecimal[] metodoSecante(BigDecimal tol, int maxIt, BigDecimal x0,
			BigDecimal x1) throws Exception {
		BigDecimal err = null;
		boolean fin = false;	//Switch
		int k = 0;				//Índice de la iteración
		while((!fin)&&(k<=maxIt)){
			BigDecimal fx0 = valorImagen(x0);
			BigDecimal fx1 = valorImagen(x1);
			BigDecimal x1_x0 = x1.subtract(x0);
			BigDecimal fx1_fx0 = fx1.subtract(fx0);
			BigDecimal fr = x1_x0.multiply(fx1).divide(fx1_fx0, tol.scale()+3, RoundingMode.HALF_UP);
			BigDecimal xr = x1.subtract(fr);
			BigDecimal e = x0.subtract(xr).abs();	//Error inicial
			if (e.compareTo(tol)<1) {	//Error igual o por debajo de la tolerancia?
				fin = true;
			}else{
				x0 = x1;
				x1 = xr;
				k++;
			}
			err = e;
		}
		
		if (fin) {
			O.pln("x = "+x0);
			return new BigDecimal[]{x0, err};
		} else {
			throw new Exception("No converge dentro del valor máximo de iteración");
		}
	}
	
	/**
	 * @param tol tolerancia del error
	 * @param maxIt máximo número de iteraciones
	 * @param ab intervalo a evaluar
	 * @return la raíz dentro del intervalo [a,b] y el error
	 * @throws Exception
	 */
	public BigDecimal[] metodoRegulaFalsi(BigDecimal tol, int maxIt, Interval ab)
			throws Exception {
		if (rootExistentialityCriterion(ab)) {
			BigDecimal err = null;
			boolean fin = false;	//Switch
			int k = 0;				//Índice de la iteración
			BigDecimal xa = BigDecimal.ZERO;
			while((!fin)&&(k<=maxIt)){
				BigDecimal fa = valorImagen(ab.min());
				BigDecimal fb = valorImagen(ab.max());
				BigDecimal fb_fa = fb.subtract(fa);
				BigDecimal fr = ab.length().multiply(fb).divide(fb_fa, tol.scale()+3, RoundingMode.HALF_UP);
				BigDecimal xr = ab.max().subtract(fr);
				BigDecimal e = xa.subtract(xr).abs();	//Error inicial
				if (e.compareTo(tol)<1) {	//Error igual o por debajo de la tolerancia?
					fin = true;
				}else{
					BigDecimal fxr = valorImagen(xr);
					if (fxr.signum()>1) {
						ab.setMax(xr);
					} else {
						ab.setMin(xr);
					}
					xa = xr;
					k++;
				}
				err = e;
			}
			
			if (fin) {
				O.pln("x = "+xa);
				return new BigDecimal[]{xa, err};
			} else {
				throw new Exception("No converge dentro del valor máximo de iteración");
			}
			
		} else {
			throw new Exception("No existe la raíz dentro del intervalo.");
		}
		
	}
	
}
