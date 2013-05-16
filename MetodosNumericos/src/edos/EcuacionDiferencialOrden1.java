/**
 * 
 */
package edos;

import java.math.BigDecimal;

import resources.CustomException;

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
		

		gS += Qx.getGeneric()+" + ";
		sS += Qx.getSpecific()+" + ";
		toString += Qx+" + ";
		
		if(Px.getTerminos().size() == 1){
			if(Px.getTerminos().get(0).getA().compareTo(BigDecimal.ONE) != 0){
				gS += Px.getGeneric();
				sS += Px.getSpecific();
				toString += Px;
			}
		}else{
			gS += "("+Px.getGeneric()+")";
			sS += "("+Px.getSpecific()+")";
			toString += "("+Px+")";
		}
		
		toString += "y";
		generic = gS + "y";
		specific = sS + "y";
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
			Funcion px = Funcion.polinomio(0, new BigDecimal[]{BigDecimal.ONE});
			Funcion qx = Funcion.polinomio(1, new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ONE});
			EcuacionDiferencialOrden1 edo1 = new EcuacionDiferencialOrden1(px, qx);
			System.out.println(edo1.getGeneric());
			System.out.println(edo1);
			System.out.println(edo1.getSpecific());
			javax.swing.JFrame asjf = new javax.swing.JFrame("testst");
			asjf.setVisible(true);
			asjf.add(new javax.swing.JLabel("<html>"+edo1.getSpecific()+"</html>"));
			asjf.setSize(500, 200);
			asjf.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
			
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
