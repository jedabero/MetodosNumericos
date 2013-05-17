/**
 * 
 */
package edos;

import java.math.BigDecimal;
import java.math.RoundingMode;

import resources.CustomException;
import resources.math.Big;
import resources.math.Constantes.Tipo;
import resources.math.Interval;

import funciones.Funcion;
import funciones.Termino;

/**
 * @author Jedabero
 *
 */
public class EcuacionDiferencialOrden1 {
	
	private Funcion Px;
	private Funcion Qx;
	
	private Funcion dPx;
	private Funcion dQx;

	private String generic;
	private String specific;
	private String toString;
	
	/**
	 * @return the P(x)
	 */
	public Funcion getPx() {
		return Px;
	}

	/**
	 * @return the Q(x)
	 */
	public Funcion getQx() {
		return Qx;
	}

	/**
	 * @return the generic string
	 */
	public String getGeneric() {
		return generic;
	}

	/**
	 * @return the specific string
	 */
	public String getSpecific() {
		return specific;
	}

	/**
	 * @param px the function P(x) to set
	 */
	public void setPx(Funcion px) {
		Px = px;
	}

	/**
	 * @param qx the function Q(x) to set
	 */
	public void setQx(Funcion qx) {
		Qx = qx;
	}

	private void initStrings() {
		String init = "dy/dx = ";
		String gS = init;
		String sS = init;
		toString = init;
		

		gS += Qx.getGeneric();
		sS += Qx.getSpecific();
		toString += Qx;
		
		switch (Px.getTerminos().size()) {
		case 0:
			break;
		case 1:
			Termino t0 = Px.getTerminos().get(0);
			boolean Aeq1 = (t0.getA().compareTo(BigDecimal.ONE) == 0);
			boolean TeqK = (t0.getTipoFuncion() == Tipo.CONSTANTE);
			System.out.println(Aeq1+":"+TeqK);
			if(!(Aeq1&&TeqK)){
				gS += " + "+Px.getGeneric()+"y";
				sS += " + "+Px.getSpecific()+"y";
				toString += " + "+Px+"y";
			}else{
				gS += " + y";
				sS += " + y";
				toString += " + y";
			}
			break;
		default:
			gS += " + ("+Px.getGeneric()+")y";
			sS += " + ("+Px.getSpecific()+")y";
			toString += " + ("+Px+")y";
			break;
		}
		
		generic = gS;
		specific = sS;
	}
	
	@Override
	public String toString(){
		return toString;
	}
	
	/**
	 * @param px
	 * @param qx
	 */
	public EcuacionDiferencialOrden1(Funcion px, Funcion qx) {
		
		Px = px;
		Qx = qx;
		
		dPx = Px.derivada();
		dQx = Qx.derivada();
		
		initStrings();
	}
	
	/**
	 * @param x
	 * @param y
	 * @return el valor de dy/dx
	 */
	public BigDecimal valor(BigDecimal x, BigDecimal y){
		BigDecimal res = y.multiply(Px.valorImagen(x)).add(Qx.valorImagen(x));
		return res;
	}
	
	/**
	 * @param x
	 * @param y
	 * @return el valor de d<sup>2</sup>y/dx<sup>2</sup>
	 */
	public BigDecimal valorDerivada(BigDecimal x, BigDecimal y){
		BigDecimal PxVal = Px.valorImagen(x);
		BigDecimal PxDY = PxVal.multiply(valor(x, y));
		BigDecimal dPxVal = dPx.valorImagen(x);
		BigDecimal dPxY = dPxVal.multiply(y);
		BigDecimal dQxVal = dQx.valorImagen(x);
		BigDecimal acum = dPxY.add(dQxVal).add(PxDY);
		return acum;
	}
	
	/**
	 * @param X
	 * @param y0
	 * @param n
	 * @return el valor de y(X.max()) por el método de Euler Simple
	 */
	public BigDecimal metodoEulerSimple(Interval X, BigDecimal y0, int n) {
		BigDecimal h = X.step(n);
		BigDecimal xn[] = X.conjuntoPuntos(n);
		BigDecimal yn = y0;
		
		for (int i = 0; i < n; i++) {
			BigDecimal dyn = valor(xn[i], yn);
			BigDecimal hdy = h.multiply(dyn);
			yn = yn.add(hdy).stripTrailingZeros();
		}
		
		return yn;
	}
	
	/**
	 * @param X
	 * @param y0
	 * @param n
	 * @return el valor de y(X.max()) por el método de Euler Simple Modificado
	 */
	public BigDecimal metodoEulerSimpleModificado(Interval X, BigDecimal y0, int n) {
		BigDecimal h = X.step(n);
		BigDecimal xn[] = X.conjuntoPuntos(n);
		BigDecimal yn = y0;
		
		for (int i = 0; i < n; i++) {
			BigDecimal k1 = valor(xn[i], yn);
			BigDecimal xnh = xn[i].add(h);
			BigDecimal un = yn.add(h.multiply(k1)); 
			BigDecimal k2 = valor(xnh, un);
			yn = yn.add(h.divide(Big.TWO).multiply(k1.add(k2))).stripTrailingZeros();
		}
		
		return yn;
	}
	
	/**
	 * @param X
	 * @param y0
	 * @param n
	 * @return el valor de y(X.max()) por el método con las Serie de Taylor
	 * hasta el tercer término.
	 */
	public BigDecimal metodoSeriesTaylor3(Interval X, BigDecimal y0, int n) {
		BigDecimal h = X.step(n);
		BigDecimal xn[] = X.conjuntoPuntos(n);
		BigDecimal yn = y0;
		
		for (int i = 0; i < n; i++) {
			BigDecimal dyn = valor(xn[i], yn);
			BigDecimal hdy = h.multiply(dyn);
			BigDecimal ddyn = valorDerivada(xn[i], yn);
			BigDecimal h2_2 = h.pow(2).divide(Big.TWO);
			BigDecimal t = h2_2.multiply(ddyn);
			yn = yn.add(hdy).add(t).stripTrailingZeros();
		}
		
		return yn;
	}
	
	/**
	 * @param X
	 * @param y0
	 * @param n
	 * @return el valor de y(X.max()) por el método de Runge-Kutta
	 */
	public BigDecimal metodoRungeKutta(Interval X, BigDecimal y0, int n) {
		BigDecimal h = X.step(n);
		BigDecimal h_2 = h.divide(Big.TWO);
		BigDecimal h_6 = h.divide(BigDecimal.valueOf(6), 15, RoundingMode.HALF_UP);
		BigDecimal xn[] = X.conjuntoPuntos(n);
		BigDecimal yn = y0;
		
		for (int i = 0; i < n; i++) {
			BigDecimal k1 = valor(xn[i], yn);
			BigDecimal k2 = valor(xn[i].add(h_2), yn.add(k1.multiply(h_2)));
			BigDecimal k3 = valor(xn[i].add(h_2), yn.add(k2.multiply(h_2)));
			BigDecimal k4 = valor(xn[i+1], yn.add(k3.multiply(h)));
			BigDecimal k = k1.add(k2.add(k3).multiply(Big.TWO)).add(k4);
			yn = yn.add(h_6.multiply(k)).stripTrailingZeros();
		}
		
		return yn;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args){
		try {
			Funcion px = Funcion.polinomio(0,
					new BigDecimal[]{BigDecimal.ONE});
			Funcion qx = Funcion.polinomio(1,
					new BigDecimal[]{BigDecimal.valueOf(0), BigDecimal.valueOf(1)});
			EcuacionDiferencialOrden1 edo1 = new EcuacionDiferencialOrden1(px, qx);
			System.out.println(edo1);
			System.out.println(edo1.getSpecific());
			Interval X = new Interval(BigDecimal.ZERO, BigDecimal.ONE);
			BigDecimal y0 = BigDecimal.valueOf(1);
			BigDecimal res = edo1.metodoEulerSimple(X, y0, 10);
			BigDecimal resm = edo1.metodoEulerSimpleModificado(X, y0, 10);
			BigDecimal rst3 = edo1.metodoSeriesTaylor3(X, y0, 10);
			BigDecimal rrk = edo1.metodoRungeKutta(X, y0, 10);
			System.out.println("yn="+res);
			System.out.println("yn="+resm);
			System.out.println("yn="+rst3);
			System.out.println("yn="+rrk);
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
