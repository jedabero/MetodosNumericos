/**
 * 
 */
package edos;

import java.math.BigDecimal;

import resources.CustomException;
import resources.math.Constantes.FuncionTrig;
import resources.math.Constantes.Tipo;

import funciones.Funcion;
import funciones.Termino;

/**
 * @author Jedabero
 *
 */
public class EcuacionDiferencialOrden1 {
	
	private Funcion Px;
	private Funcion Qx;
	

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
	 * @param args
	 */
	public static void main(String[] args){
		try {
			Funcion px = Funcion.trigonometrica(FuncionTrig.COS, BigDecimal.valueOf(1), BigDecimal.ONE);
			Funcion qx = Funcion.polinomio(2,
					new BigDecimal[]{BigDecimal.valueOf(1.1), BigDecimal.valueOf(1.2), BigDecimal.valueOf(1.3)});
			EcuacionDiferencialOrden1 edo1 = new EcuacionDiferencialOrden1(px, qx);
			System.out.println(edo1);
			
			System.out.println(edo1.valor(BigDecimal.ONE, BigDecimal.TEN));
			
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
