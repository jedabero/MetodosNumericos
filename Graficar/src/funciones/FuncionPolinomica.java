package funciones;

import java.math.BigDecimal;

/**
 * Esta clase define un tipo de función especifico: las polinómicas.</br>
 * Son de la forma:</br>
 * &#8721;<i>a<sub>i</sub>x<sup>i</sup></i>
 * desde i = 0 hasta n, donde n es el grado de la función.
 * @author <a href="https://twitter.com/Jedabero" target="_blank">Jedabero</a>
 *
 */

public class FuncionPolinomica extends FuncionBase{
	
	private int grado;
	
	/**
	 * Regresa el grado de  la función polinómica.
	 * @return the grado
	 */
	public int getGrado() {
		return grado;
	}

	/**
	 * Modifica el grado de la función polinómica
	 * @param grado the grado to set
	 */
	public void setGrado(int grado) {
		this.grado = grado;
		setTerminos(grado+1);
	}
	
	
	
	/**
	 * Inicializa los Strings de la función y de la ecuación.
	 */
	public void initFuncionStrings(){
		boolean lastAequals0 = (getA()[getGrado()].signum()==0);
		
		while(lastAequals0&&(getGrado()>0)){
			if(getGrado()>0) setGrado(getGrado()-1);
			BigDecimal a[] = new BigDecimal[getTerminos()];
			
			for(int i=0;i<a.length;i++) a[i] = getA()[i];
			
			setA(a);
			lastAequals0 = (getA()[getGrado()].signum()==0);
		}
		
		String e = "<html>";
		for(int i=0;i<getTerminos();i++){
			switch(i){
			case 0: e += "A<sub>0</sub> "; break;
			case 1: e += "+ A<sub>1</sub>x "; break;
			default: e += "+ A<sub>"+i+"</sub>x<sup>"+i+"</sup> "; break;
			}
		}
		e +="</html>";
		setEcuacion(e);
		
		String f = "<html>";
		boolean prevAeq0 = false;
		boolean allPrevAeq0 = true;
		for(int i=0;i<getTerminos();i++){
			BigDecimal a = getA()[i];
			int signAi  = a.signum();
			boolean Aieq1 = a.abs().compareTo(BigDecimal.ONE)==0;
			boolean currAeq0 = (signAi==0);
			
			switch(signAi){
			case 0:
				currAeq0 = true;
				break;
			case 1:
				switch(i){
				case 0:
					f += a+" ";
					break;
				case 1:
					if(!prevAeq0) f += "+ ";
					if(!Aieq1) f += a;
					f += "x ";
					break;
				default:
					if(!allPrevAeq0) f += "+ ";
					if(!Aieq1) f += a;
					f += "x<sup>"+i+"</sup> ";
					break;
				}
				allPrevAeq0 = false; break;
			case -1:
				f += "-";
				switch(i){
				case 0:
					f += a.abs()+" ";
					break;
				case 1:
					if(!prevAeq0) f += " "; 
					if(!Aieq1) f += a.abs();
					f += "x ";
					break;
				default:
					if(!allPrevAeq0) f += " "; 
					if(!Aieq1) f += a.abs();
					f += "x<sup>"+i+"</sup> ";
					break;
				}
				allPrevAeq0 = false; break;
			default: break;
			}
			prevAeq0 = currAeq0;
		}
		f +="</html>";
		setFuncion(f);
	}
	
	/**
	 * Calcula el valor de cada una de las <b>y</b> de acuerdo a la ecuación.
	 */
	protected void calculaY(){
		for(int i=0;i<getY().length;i++){
			for(int j=0;j<getGrado()+1;j++){
				getY()[i] = getY()[i].add(getA()[j].multiply(getX()[i].pow(j)));
			}
		}
	}
	
	/**
	 * Constructor para cualquier función polinómica de grado <b>g</b>.
	 * @param g el grado de la ecuación.
	 */
	public FuncionPolinomica(int g) {
		super(TipoFuncion.POLINOMICA, g+1);
		setGrado(g);
		initFuncionStrings();
	}
	
	/**
	 * Método para actualizar los parámetros de la ecuación polinómica.
	 * @param p el paso.
	 * @param g el grado de la ecuación.
	 * @param interval el intervalo nuevo
	 * @param a array con los coeficientes de la ecuación.
	 */
	public void update(BigDecimal p, BigDecimal[] interval, int g,
			BigDecimal[] a){
		setGrado(g);
		super.update(p, g+1, interval, a, getB(), TipoFuncion.POLINOMICA);
	}
	
}
