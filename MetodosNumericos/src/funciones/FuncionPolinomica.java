package funciones;

import java.math.BigDecimal;

/**
 * Esta clase define un tipo de funci�n especifico: las polin�micas.</br>
 * Son de la forma:</br>
 * &#8721;<i>a<sub>i</sub>x<sup>i</sup></i>
 * desde i = 0 hasta n, donde n es el grado de la funci�n.
 * @author <a href="https://twitter.com/Jedabero" target="_blank">Jedabero</a>
 * @since 0.1
 * @deprecated desde la versi�n 0.4, por Funcion
 * @see Funcion
 */

public class FuncionPolinomica extends FuncionBase{
	
	private int grado;
	
	/**
	 * Regresa el grado de  la funci�n polin�mica.
	 * @return the grado
	 */
	public int getGrado() {
		return grado;
	}

	/**
	 * Modifica el grado de la funci�n polin�mica
	 * @param grado the grado to set
	 */
	public void setGrado(int grado) {
		this.grado = grado;
		setTerminos(grado+1);
	}
	
	
	
	/**
	 * Inicializa los Strings de la funci�n y de la ecuaci�n.
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
	 * Calcula el valor de cada una de las <b>y</b> de acuerdo a la ecuaci�n.
	 */
	protected void calculaY(){
		for(int i=0;i<getY().length;i++){
			for(int j=0;j<getGrado()+1;j++){
				getY()[i] = getY()[i].add(getA()[j].multiply(getX()[i].pow(j)));
			}
		}
	}
	
	/**
	 * Constructor para cualquier funci�n polin�mica de grado <b>g</b>.
	 * @param g el grado de la ecuaci�n.
	 */
	public FuncionPolinomica(int g) {
		super(Tipo.POLINOMICA, g+1);
		setGrado(g);
		initFuncionStrings();
	}
	
	/**
	 * M�todo para actualizar los par�metros de la ecuaci�n polin�mica.
	 * @param p el paso.
	 * @param g el grado de la ecuaci�n.
	 * @param interval el intervalo nuevo
	 * @param a array con los coeficientes de la ecuaci�n.
	 */
	public void update(BigDecimal p, BigDecimal[] interval, int g,
			BigDecimal[] a){
		setGrado(g);
		super.update(p, g+1, interval, a, getB(), Tipo.POLINOMICA);
	}
	
}
