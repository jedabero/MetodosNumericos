package resources.math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import resources.O;


/**
 * @author <a href="https://twitter.com/Jedabero" target="_blank">Jedabero</a>
 *
 */
public final class Big {
	
	/**
	 * A constant holding the largest positive finite value of type double as a
	 * BigDecimal
	 */
	public static final BigDecimal DOUBLE_MAX = BigDecimal.valueOf(Double.MAX_VALUE);
	
	/**
	 * A constant holding the largest positive finite value of type double as a
	 * BigDecimal
	 */
	public static final BigDecimal INT_MAX = BigDecimal.valueOf(Integer.MAX_VALUE);
	/**
	 * The <code>double</code> value that is closer than any other to
     * <i>pi</i>, the ratio of the circumference of a circle to its
     * diameter.
	 */
	public static final BigDecimal PI = BigDecimal.valueOf(Math.PI);
	
	/**
	 * Half of {@link Big#PI}
	 */
	public static final BigDecimal PI_2 = BigDecimal.valueOf(Math2.PI_2);
	
	/**
	 * Double of {@link Big#PI}
	 */
	public static final BigDecimal TAU = BigDecimal.valueOf(Math2.TAU);

	/**
	 * The value of 2
	 */
	public static final BigDecimal TWO = new BigDecimal(2);
	
	/**
	 * The value of 3
	 */
	public static final BigDecimal THREE = new BigDecimal(3);
	
	/**
	 * Convierte el n&uacute;mero al entero m&aacute;s cercano, dependiendo si
	 * es un m&aacute;ximo o no.
	 * @param	bd	n&uacute;mero a convertir.
	 * @param	isMax	si es un m&aacute;ximo debe ser true, false para un
	 * m&iacute;nimo.
	 * @return	El n&uacute;mero convertido en m&aacute;ximo
	 */
	public static BigDecimal compareToInt(BigDecimal bd, boolean isMax){
		int bdToInt = bd.intValue();	//Se convierte a entero.
		//Se revierte a BigDecimal para comparaci&oacute;n.
		BigDecimal intBD = BigDecimal.valueOf(bdToInt);
		int cR = bd.compareTo(intBD);	//Se guarda el resultado de la comparaci&oacute;n.
		switch(cR){
		case 0:	//Se deja igual.
			break;
		default://Para los otros casos (-1 y 1), se hace el cambio a entero
				//dependiendo si es m&aacute;ximo o es m&aacute;nimo.
			if(isMax){
				bd = intBD.add(BigDecimal.ONE);
			}else if(!isMax){
				bd = intBD.subtract(BigDecimal.ONE);
			}
			break;
		}
		return bd;
	}//Fin de compareToInt
	
	
	/**
	 * @param v
	 * @return suma de los elementos del vector
	 */
	public static BigDecimal suma(BigDecimal v[]) {
		BigDecimal suma = BigDecimal.ZERO;
		for (int i = 0; i < v.length; i++) {
			suma = suma.add(v[i]);
		}
		return suma;
	}
	
	/**
	 * @param v
	 * @return suma de los elementos en posiciones impares del vector
	 */
	public static BigDecimal sumaPosImpares(BigDecimal v[]) {
		BigDecimal suma = BigDecimal.ZERO;
		for (int i = 0; i < v.length; i++) {
			if(i%2==1){
				suma = suma.add(v[i]);
			}
		}
		return suma;
	}
	
	/**
	 * @param v
	 * @return suma de los elementos en posiciones pares del vector
	 */
	public static BigDecimal sumaPosPares(BigDecimal v[]) {
		BigDecimal suma = BigDecimal.ZERO;
		for (int i = 0; i < v.length; i++) {
			if(i%2==0){
				suma = suma.add(v[i]);
			}
		}
		return suma;
	}
	
	/**
	 * @param v
	 * @return la multiplicacion de todos los elementos de v
	 */
	public static BigDecimal multiplica(BigDecimal v[]){
		BigDecimal prod = BigDecimal.ONE;
		for (int i = 0; i < v.length; i++) {
			prod = prod.multiply(v[i]);
		}
		return prod;
	}
	
	/**
	 * @param i
	 * @param v
	 * @return Pro(xi - xj)
	 */
	public static BigDecimal productoDiferencias(int i, BigDecimal v[]) {
		BigDecimal prod = BigDecimal.ONE;
		for (int j = 0; j < v.length; j++) {
			if (j!=i) {
				prod = prod.multiply(v[i].subtract(v[j]));
			}
		}
		return prod;
	}
	
	/**
	 * Regresa el valor m&aacute;ximo de un array de BigDecimal
	 * @param	a el array a evaluar
	 * @return	el valor m&aacute;ximo dentro de {@code a}.
	 */
	public static BigDecimal max(BigDecimal a[]){
		BigDecimal max = a[0];
		for(BigDecimal i: a){
			if((max.compareTo(i))==-1) max = i;
		}
		return max;
	}
	
	/**
	 * Regresa el valor m&iacute;nimo de un array de BigDecimal
	 * @param	a el array a evaluar
	 * @return	el valor m&iacute;nimo dentro de {@code a}.
	 */
	public static BigDecimal min(BigDecimal a[]){
		BigDecimal min = a[0];
		for(BigDecimal i: a){
			if((min.compareTo(i))==1) min = i;
		}
		return min;
	}
	
	/**
	 * Calcula el seno trigonom&eacute;trico del &aacute;ngulo
	 * @see		Math2#sin(double)
	 * @param	a el &aacute;ngulo en radianes
	 * @return	el seno de {@code a}
	 */
	public static BigDecimal sin(BigDecimal a) {
		BigDecimal reducedA = a.remainder(TAU);
		double redA = reducedA.doubleValue();
		BigDecimal sin = new BigDecimal(Double.toString(Math.sin(redA)));
		return sin.stripTrailingZeros();
	}
	
	/**
	 * Calcula el coseno trigonom&eacute;trico del &aacute;ngulo
	 * @see		Math2#cos(double)
	 * @param	a el &aacute;ngulo en radianes
	 * @return	el coseno de {@code a}
	 */
	public static BigDecimal cos(BigDecimal a) {
		BigDecimal reducedA = a.remainder(TAU);
		double redA = reducedA.doubleValue();
		BigDecimal cos = new BigDecimal(Double.toString(Math.cos(redA)));
		return cos.stripTrailingZeros();
	}
	
	/**
	 * Calcula la tangente trigonom&eacute;trico del &aacute;ngulo
	 * @see		Math2#tan(double)
	 * @param	a el &aacute;ngulo en radianes
	 * @return	la tangente de {@code a}
	 */
	public static BigDecimal tan(BigDecimal a) {
		BigDecimal reducedA = a.remainder(TAU);
		double redA = reducedA.doubleValue();
		BigDecimal tan = new BigDecimal(Double.toString(Math.tan(redA)));
		return tan.stripTrailingZeros();
	}
	
	/**
	 * Calcula la secante trigonom&eacute;trica del &aacute;ngulo
	 * @param	a el &aacute;ngulo en radianes
	 * @return	la secante de {@code a}
	 */
	public static BigDecimal sec(BigDecimal a) {
		
		try{
			return BigDecimal.ONE.divide(cos(a), 16, RoundingMode.HALF_UP).stripTrailingZeros();
		}catch(ArithmeticException ae){
			O.pln("sec("+a+") = 1"+ae.getMessage());
			return BigDecimal.ZERO;
		}
	}
	
	/**
	 * Calcula la cosecante trigonom&eacute;trica del &aacute;ngulo
	 * @param	a el &aacute;ngulo en radianes
	 * @return	la cosecante de {@code a}
	 */
	public static BigDecimal csc(BigDecimal a) {
		try{
			return BigDecimal.ONE.divide(sin(a), 16, RoundingMode.HALF_UP).stripTrailingZeros();
		}catch(ArithmeticException ae){
			O.pln("csc("+a+") = 1"+ae.getMessage());
			return BigDecimal.ZERO;
		}
	}
	
	/**
	 * Calcula la cotangente trigonom&eacute;trico del &aacute;ngulo
	 * @param	a el &aacute;ngulo en radianes
	 * @return	la cotangente de {@code a}
	 */
	public static BigDecimal cot(BigDecimal a) {
		try{
			return BigDecimal.ONE.divide(tan(a), 16, RoundingMode.HALF_UP);
		}catch(ArithmeticException ae){
			O.pln("cot("+a+") = 1"+ae.getMessage());
			return BigDecimal.ZERO;
		}
	}
	
	/**
	 * Calcula el seno trigonom&eacute;trico del &aacute;ngulo
	 * @see		Math2#sin(double)
	 * @param	a el &aacute;ngulo en radianes
	 * @return	el seno de {@code a}
	 */
	public static BigDecimal asin(BigDecimal a) {
		double aD = a.doubleValue();
		BigDecimal asin = new BigDecimal(Double.toString(Math.asin(aD)));
		return asin.stripTrailingZeros();
	}
	
	/**
	 * Calcula el coseno trigonom&eacute;trico del &aacute;ngulo
	 * @see		Math2#cos(double)
	 * @param	a el &aacute;ngulo en radianes
	 * @return	el coseno de {@code a}
	 */
	public static BigDecimal acos(BigDecimal a) {
		double aD = a.doubleValue();
		BigDecimal acos = new BigDecimal(Double.toString(Math.acos(aD)));
		return acos.stripTrailingZeros();
	}
	
	/**
	 * Calcula la tangente trigonom&eacute;trico del &aacute;ngulo
	 * @see		Math2#tan(double)
	 * @param	a el &aacute;ngulo en radianes
	 * @return	la tangente de {@code a}
	 */
	public static BigDecimal atan(BigDecimal a) {
		double aD = a.doubleValue();
		BigDecimal atan = new BigDecimal(Double.toString(Math.atan(aD)));
		return atan.stripTrailingZeros();
	}
	
	/**
	 * Calcula la secante trigonom&eacute;trica del &aacute;ngulo
	 * @param	a el &aacute;ngulo en radianes
	 * @return	la secante de {@code a}
	 */
	public static BigDecimal asec(BigDecimal a) {//TODO asec
		
		try{
			BigDecimal x = BigDecimal.ONE.divide(a, 16, RoundingMode.HALF_UP).stripTrailingZeros();
			return acos(x);
		}catch(ArithmeticException ae){
			O.pln("asec("+a+") = "+ae.getMessage());
			return BigDecimal.ZERO;
		}
	}
	
	/**
	 * Calcula la cosecante trigonom&eacute;trica del &aacute;ngulo
	 * @param	a el &aacute;ngulo en radianes
	 * @return	la cosecante de {@code a}
	 */
	public static BigDecimal acsc(BigDecimal a) {//TODO acsc
		try{
			BigDecimal x = BigDecimal.ONE.divide(a, 16, RoundingMode.HALF_UP).stripTrailingZeros();
			return asin(x);
		}catch(ArithmeticException ae){
			O.pln("acsc("+a+") = "+ae.getMessage());
			return BigDecimal.ZERO;
		}
	}
	
	/**
	 * Calcula la cotangente trigonom&eacute;trico del &aacute;ngulo
	 * @param	a el &aacute;ngulo en radianes
	 * @return	la cotangente de {@code a}
	 */
	public static BigDecimal acot(BigDecimal a) {//TODO acot
		try{
			BigDecimal x = BigDecimal.ONE.divide(a, 16, RoundingMode.HALF_UP);
			return atan(x);
		}catch(ArithmeticException ae){
			O.pln("acot("+a+") = "+ae.getMessage());
			return BigDecimal.ZERO;
		}
	}
	
	/**
	 * @see		Math2#toRadians(double)
	 * @param	a el &aacute;ngulo en grados
	 * @return	el &aacute;ngulo {@code a} en radianes 
	 */
	public static BigDecimal toRadians(BigDecimal a){
		return BigDecimal.valueOf(Math.toRadians(a.doubleValue()));
	}
	
	/**
	 * @see		Math2#toDegrees(double)
	 * @param	a el &aacute;ngulo en radianes
	 * @return	el &aacute;ngulo {@code a} en grados 
	 */
	public static BigDecimal toDegrees(BigDecimal a){
		return BigDecimal.valueOf(Math.toDegrees(a.doubleValue()));
	}
	
	/**
	 * Potenciaci&oacute;n la base elevada al exp.
	 * @param base la base
	 * @param exp el exponente
	 * @return base<sup>exp</sup>
	 */
	public static BigDecimal pow(BigDecimal base, BigDecimal exp){
		double b = base.doubleValue();
		double x = exp.doubleValue();
		if(Double.isInfinite(Math.pow(b, x))){
			BigDecimal n = logB(DOUBLE_MAX, base);
			BigDecimal x2 = exp.subtract(n);
			BigDecimal expX2 = pow(base, x2);
			return pow(base, n).multiply(expX2);
		}else{
			BigDecimal ex = new BigDecimal(Double.toString(Math.pow(b,x)));
			return (ex.stripTrailingZeros());
		}
	}
	
	/**
	 * Regresa el logaritmo de n en base base 
	 * @param base la base
	 * @param x el n&uacute;mero
	 * @return regresa log<sub>b</sub>x
	 */
	public static BigDecimal logB(BigDecimal x, BigDecimal base){
		BigDecimal num = ln(x);
		BigDecimal den = ln(base);
		BigDecimal res = num.divide(den, 10, RoundingMode.HALF_EVEN).stripTrailingZeros();
		return res;
	}
	
	/**
	 * Regresa el numero de Euler elevado a la potencia del valor del {@code BigDecimal}
	 * @see Math2#exp(double)
	 * @param	x el exponente a elevar {@code e}.
	 * @return e<sup>x</sup>, en el que e es la base de los logaritmos naturales
	 */
	public static BigDecimal exp(BigDecimal x){
		if(Double.isInfinite(Math.exp(x.doubleValue()))){
			BigDecimal n = ln(DOUBLE_MAX);
			BigDecimal x2 = x.subtract(n);
			BigDecimal expX2 = exp(x2);
			return exp(n).multiply(expX2);
		}else{
			double aD = x.doubleValue();
			BigDecimal exp = new BigDecimal(Double.toString(Math.exp(aD)));
			return (exp.stripTrailingZeros());
		}
	}
	
	/**
	 * Regresa el logaritmo natural (base {@code e}) del valor del {@code BigDecimal}
	 * @see Math2#log(double)
	 * @param	x un valor
	 * @return ln {@code x}, logaritmo natural de {@code x}
	 */
	public static BigDecimal ln(BigDecimal x){
		if(x.signum()<=0){
			O.pln("ln("+x+") = -Infinity");
			return BigDecimal.ZERO;
		}else if(Double.isInfinite(x.doubleValue())){
			BigDecimal n = x.divide(DOUBLE_MAX);
			return (ln(n).add(ln(DOUBLE_MAX)));
		}else{
			double l = Math.log(x.doubleValue());
			BigDecimal log = new BigDecimal(Double.toString(l));
			return (log.stripTrailingZeros());
		}
	}
	
	/**
	 * Regresa la ra&iacute;z cuadrada positiva de a
	 * @param a el n&uacute;mero
	 * @return la ra&iacute;z cuadrada positiva de a
	 * @throws Exception 
	 */
	public static BigDecimal sqrt(double a) throws Exception{
		if(Double.isInfinite(a)){
			throw new Exception("n&uacute;mero infinito");
		}else if(Double.isNaN(a)){
			throw new Exception("n&uacute;mero indeterminado");
		}else if(a<0){
			throw new Exception("n&uacute;mero negativo");
		}else{
			BigDecimal sqrt = new BigDecimal(Double.toString(Math.sqrt(a)));
			return (sqrt.stripTrailingZeros());
		}
	}
	
	/**
	 * Regresa la raiz cuadrada positiva de a
	 * @param a el numero
	 * @return la raiz cuadrada positiva de a
	 * @throws Exception 
	 */
	public static BigDecimal sqrt(BigDecimal a) throws Exception{
		if(a.signum()==-1){
			throw new Exception("Numero negativo");
		}else{
			return sqrt(a, new BigDecimal("1e-15"));
		}
	}
	
	/**
	 * Regresa la raiz cuadrada positiva de a
	 * @param a el numero
	 * @return la raiz cuadrada positiva de a
	 * @throws Exception 
	 */
	public static BigDecimal sqrt(BigDecimal a, BigDecimal tol) throws Exception{
		if(a.signum()==-1){
			throw new Exception("Numero negativo");
		}else{
			tol = tol.stripTrailingZeros();
			BigDecimal x0 = a.divide(TWO);
			boolean endLoop = false;
			while(!endLoop){
				BigDecimal s_x0 = a.divide(x0, tol.scale(), RoundingMode.HALF_EVEN);
				BigDecimal x1 = x0.add(s_x0).divide(TWO, tol.scale(), RoundingMode.HALF_EVEN);
				BigDecimal err = x0.subtract(x1).abs();
				if(err.compareTo(tol)<1){
					endLoop = true;
				}
				x0 = x1;
			}
			return x0.stripTrailingZeros();
		}
	}
	
	/**
	 * Regresa la raiz cubica positiva de a
	 * @param a el numero
	 * @return la raiz cubica positiva de a
	 * @throws Exception 
	 */
	public static BigDecimal cbrt(double a) throws Exception{
		if(Double.isInfinite(a)){
			throw new Exception("Numero infinito");
		}else if(Double.isNaN(a)){
			throw new Exception("Numero indeterminado");
		}else{
			BigDecimal cbrt = new BigDecimal(Double.toString(Math.cbrt(a)));
			return (cbrt.stripTrailingZeros());
		}
	}
	
	/**
	 * Regresa la raiz cubica positiva de a
	 * @param a el numero
	 * @return la raiz cubica positiva de a
	 * @throws Exception 
	 */
	public static BigDecimal cbrt(BigDecimal a) throws Exception{
		return cbrt(a, new BigDecimal("1e-15"));
	}
	
	/**
	 * Regresa la raiz cubica positiva de a
	 * @param a el numero
	 * @return la raiz cubica positiva de a
	 * @throws Exception 
	 */
	public static BigDecimal cbrt(BigDecimal a, BigDecimal tol) throws Exception{
		tol = tol.stripTrailingZeros();
		BigDecimal x0 = a.divide(THREE, tol.scale(), RoundingMode.HALF_EVEN);
		boolean endLoop = false;
		while(!endLoop){
			BigDecimal s_x0_2 = a.divide(x0.pow(2), tol.scale(), RoundingMode.HALF_EVEN);
			BigDecimal x1 = TWO.multiply(x0).add(s_x0_2).divide(THREE,
					tol.scale(), RoundingMode.HALF_EVEN);
			BigDecimal err = x0.subtract(x1).abs();
			if(err.compareTo(tol)<1){
				endLoop = true;
			}
			x0 = x1;
		}
		return x0.stripTrailingZeros();
	}
	

	/**
	 * Regresa la raiz enesima de a
	 * @param a el numero
	 * @return la raiz cubica de a
	 * @throws Exception 
	 */
	public static BigDecimal nRoot(int n, BigDecimal a) throws Exception{
		return nRoot(n, a, new BigDecimal("1e-15"));
	}
	
	/**
	 * Regresa la raiz enesima de a
	 * @param a el numero
	 * @return la raiz enesima de a
	 * @throws Exception 
	 */
	public static BigDecimal nRoot(int n, BigDecimal a, BigDecimal tol) throws Exception{
		if((n%2==0)&&(a.signum()==-1)){
			throw new Exception("Raiz par. Numero negativo");
		}else{
			tol = tol.stripTrailingZeros();
			BigDecimal bdn = new BigDecimal(n);
			BigDecimal bdnm1 = new BigDecimal(n-1);
			BigDecimal x0 = a.divide(bdn, tol.scale(), RoundingMode.HALF_EVEN);
			boolean endLoop = false;
			while(!endLoop){
				BigDecimal div = a.divide(x0.pow(n-1), tol.scale(), RoundingMode.HALF_EVEN);
				BigDecimal x1 = bdnm1.multiply(x0).add(div).divide(bdn, tol.scale(),
						RoundingMode.HALF_EVEN);
				BigDecimal err = x0.subtract(x1).abs();
				if(err.compareTo(tol)<1){
					endLoop = true;
				}
				x0 = x1;
			}
			return x0.stripTrailingZeros();
		}
	}
	
	/**
	 * Regresa el factorial del n&uacute;mero n
	 * @param n el n&uacute;mero
	 * @return n! el factorial de n en BigDecimal
	 * @throws Exception si n est&aacute; fuera del rango 0 < n < 1676
	 */
	public static BigDecimal factorial(int n) throws Exception{
		BigDecimal a = BigDecimal.ONE;
		if(n<0){
			throw new Exception("No hay factorial para enteros negativos");
		}else{
			for(int i=1;i<n+1;i++) a = a.multiply(new BigDecimal(""+i));
		}
		
		return a;
	}
	
	/**
	 * 
	 * @param n
	 * @param k
	 * @return nCk
	 * @throws Exception 
	 */
	public static BigDecimal combinatoria(int n, int k) throws Exception{
		if(n<k){
			throw new Exception(n+"<"+k);
		}else{
			BigDecimal fn = factorial(n);
			BigDecimal fk = factorial(k);
			int n_k = n - k;
			BigDecimal fn_k = factorial(n_k);
			return fn.divide(fk.multiply(fn_k));
		}
	}
	
	/**
	 * @param v
	 * @param k
	 * @return una lista con las conbinaciones en k de los elementos del vector v
	 * @throws Exception
	 */
	public static BigDecimal[] listaCombinaciones(BigDecimal v[], int k) throws Exception{
		BigDecimal numEl = BigDecimal.ZERO;
		try {
			numEl = combinatoria(v.length, k);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (numEl.compareTo(INT_MAX)==1) {
			throw new Exception("DEMASIADAS COMBINACIONES");
		}
		
		ArrayList<BigDecimal> temp = new ArrayList<BigDecimal>();
		
		int poss[] = new int[k];
		poss[0] = 0;
		if (k>1) {
			for (int i = 1; i < k; i++) {
				poss[i] = i;
			}
		}
		
		addCombinaciones(poss, k-1, k, v, temp);
		
		return (BigDecimal[]) temp.toArray(new BigDecimal[0]);
	}
	
	private static void addCombinaciones(int[] poss, int posACambiar, int k,
			BigDecimal[] v, ArrayList<BigDecimal> temp) {
		int numElem = v.length;
		
		BigDecimal prod = BigDecimal.ONE;
		for (int i = 0; i < poss.length; i++) {
			prod = prod.multiply(v[poss[i]]);
		}
		temp.add(prod);
		
		poss[posACambiar]++;
		
		if (poss[posACambiar]<numElem) { 
			addCombinaciones(poss, posACambiar, k, v, temp);
		} else {
			int prevPosACambiar = posACambiar - 1;
			
			if (prevPosACambiar>=0) {
				poss[prevPosACambiar]++;
				poss[posACambiar] = poss[posACambiar - 1] +1;
				
				if (poss[prevPosACambiar]<(numElem-1)) { 
					addCombinaciones(poss, posACambiar, k, v, temp);
				} else {
					boolean exit = false;
					if (prevPosACambiar!=0) {
						while((poss[prevPosACambiar]>=numElem-1)||(exit&&(prevPosACambiar>0))){
							prevPosACambiar--;
							poss[prevPosACambiar]++;
							for (int i = prevPosACambiar+1; i < k; i++) {
								poss[i] = poss[i-1] +1;
								exit = (poss[i]==numElem);
							}
						}
						if (!exit) {
							addCombinaciones(poss, posACambiar, k, v, temp);
						}
					}
				}
				
			}
			
		}
		
	}
	
	/**
	 * @param v
	 * @param k
	 * @return la suma de las combinaciones en k de los elementos del vector v
	 * @throws Exception 
	 */
	public static BigDecimal sumaCombinaciones(BigDecimal v[], int k) throws Exception{
		BigDecimal t[] = listaCombinaciones(v, k);
		return suma(t);
	}
	
	
	/**
	 * @param n
	 * @param k
	 * @return nPk
	 * @throws Exception
	 */
	public static BigDecimal permutacion(int n, int k) throws Exception{
		if(n<k){
			throw new Exception(n+"<"+k);
		}else{
			BigDecimal fn = factorial(n);
			int n_k = n - k;
			BigDecimal fn_k = factorial(n_k);
			return fn.divide(fn_k);
		}
	}
	
	/**
	 * @param i
	 * @param v
	 * @return an array with length v.length-1 and without the element at i.
	 */
	public static BigDecimal[] removeElementAt(int i, BigDecimal v[]){
		ArrayList<BigDecimal> temp = new ArrayList<BigDecimal>(v.length-1);
		for (int j = 0; j < v.length; j++) {
			if(j!=i){
				temp.add(v[j]);
			}
		}
		return (BigDecimal[])temp.toArray(new BigDecimal[0]);
	}
	
	/**
	 * @param x
	 * @param y
	 * @return una lista con las diferencias divididas definidas
	 */
	public static BigDecimal[] listaDiferenciasDivididas(BigDecimal x[],
			BigDecimal y[]){
		BigDecimal diffDivs[] = new BigDecimal[x.length];
		for (int i = 0; i < diffDivs.length; i++) {
			int pos[] = new int[i+1];
			pos[0] = 0;
			if(i>0){
				for (int j = 1; j < pos.length; pos[j] = j, j++);
			}
			diffDivs[i] = diferenciaDividida(pos, x, y);
		}
		
		return diffDivs;
	}
	
	private static BigDecimal diferenciaDividida(int[] pos, BigDecimal[] x,
			BigDecimal[] y) {
		if (pos.length==1) {
			return y[pos[0]];
		} else {
			
			BigDecimal xDifference = x[pos[pos.length-1]].subtract(x[pos[0]]);
			
			int pos1[] = new int[pos.length-1];
			int pos0[] = new int[pos.length-1];
			for (int i = 0; i < pos.length-1; i++) {
				pos1[i] = pos[i+1];
				pos0[i] = pos[i];
			}
			
			BigDecimal Y1 = diferenciaDividida(pos1, x, y);
			BigDecimal Y0 = diferenciaDividida(pos0, x, y);
			
			BigDecimal yDifference = Y1.subtract(Y0);
			
			return yDifference.divide(xDifference, 10, RoundingMode.HALF_UP);
			
		}
	}
	
	/**
	 * @param n el numero
	 * @return el exponente
	 */
	public static int getExponent(BigDecimal n){
		return n.precision() - n.scale() - 1;
	}
	
}