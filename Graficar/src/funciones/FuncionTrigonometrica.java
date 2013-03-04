/**
 * 
 */
package funciones;

import java.math.BigDecimal;

import resources.M;
import math.Big;

/**
 * Esta clase define las funciones trigonométricas.
 * @author <a href="https://twitter.com/Jedabero" target="_blank">Jedabero</a>
 * @deprecated desde la versión 0.4, por Funcion
 * @see Funcion
 * @since 0.2
 */
public class FuncionTrigonometrica extends FuncionBase {
	
	private boolean xInRadians = true;
	private boolean xInDegrees = false;

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipos(FuncionTrig[] tipo) {
		this.tipos = tipo;
	}
	
	/**
	 * @return the xInRadians
	 */
	public boolean isXinRadians() {
		return xInRadians;
	}

	/**
	 * @param xInRadians the xInRadians to set
	 */
	public void setXinRadians(boolean xInRadians) {
		this.xInRadians = xInRadians;
		this.xInDegrees = !xInRadians;
	}

	/**
	 * @return the xInDegrees
	 */
	public boolean isXinDegrees() {
		return xInDegrees;
	}

	/**
	 * @param xInDegrees the xInDegrees to set
	 */
	public void setXinDegrees(boolean xInDegrees) {
		this.xInDegrees = xInDegrees;
		this.xInRadians = !xInDegrees;
	}

	public void initFuncionStrings(){
		boolean lastAequals0 = (getA()[getTerminos()-1].signum()==0);
		boolean concuerda = getTerminos()==getTipos().length;
		
		while(lastAequals0||!concuerda){
			if(lastAequals0) setTerminos(getTerminos()-1);
			BigDecimal a[] = new BigDecimal[getTerminos()];
			BigDecimal b[] = new BigDecimal[getTerminos()];
			FuncionTrig types[] = new FuncionTrig[getTerminos()];
			for(int i=0;i<getTerminos();i++){
				a[i] = getA()[i];
				b[i] = getB()[i];
				types[i] = getTipos()[i];
			}
			setA(a);
			setB(b);
			setTipos(types);
			
			lastAequals0 = (getA()[getTerminos()-1].signum()==0);
			concuerda = getTerminos()==getTipos().length;
		}
		
		String e = "<html>";
		for(int i=0;i<getTerminos();i++){
			switch(i){
			case 0: e += "A<sub>"+i+"</sub>"; break;
			default: e += "+ A<sub>"+i+"</sub>"; break;
			}
			switch(getTipos()[i]){
			case SIN: e += "sen("; break;
			case COS: e += "cos("; break;
			case TAN: e += "tan("; break;
			case SEC: e += "sec("; break;
			case CSC: e += "csc("; break;
			case COT: e += "cot("; break;
			default: break;
			}
			
			e += "B<sub>"+i+"</sub>x) ";
		}
		e +="</html>";
		setEcuacion(e);
		
		String f = "<html> ";
		boolean allPrevAeq0 = true;
		for(int i=0;i<getTerminos();i++){
			BigDecimal Ai = getA()[i];
			BigDecimal Bi = getB()[i];
			FuncionTrig tip = getTipos()[i];
			int signAi = Ai.signum();
			boolean Aieq1 = Ai.abs().compareTo(BigDecimal.ONE)==0;
			int signBi = Bi.signum();
			boolean Bieq1 = Bi.abs().compareTo(BigDecimal.ONE)==0;
			boolean isZero = (signBi==0)&&((tip==FuncionTrig.SIN)||(tip==FuncionTrig.TAN));
			boolean isOne  = (signBi==0)&&((tip==FuncionTrig.COS)||(tip==FuncionTrig.SEC));
			boolean isInf  = (signBi==0)&&((tip==FuncionTrig.CSC)||(tip==FuncionTrig.COT));
			
			if(isInf){
				getB()[i] = BigDecimal.ONE.multiply(BigDecimal.valueOf(Big.randomSign()));
			}
			
			if(isZero) signAi=0;
			switch(signAi){
			case 0: break;
			case 1:
				switch(i){
				case 0: break;
				default: if(!allPrevAeq0) f += "+ "; break;
				}
				if(!Aieq1||isOne) f += Ai; 
				allPrevAeq0 = false; break;
			case -1:
				f += "-";
				if(!allPrevAeq0) f += " ";
				if(!Aieq1||isOne) f += Ai.abs();
				allPrevAeq0 = false; break;
			default: break;
			}
			
			if((signAi!=0)&&(signBi!=0)){
				switch(tip){
				case SIN: f += "sen("; break;
				case COS: f += "cos("; break;
				case TAN: f += "tan("; break;
				case SEC: f += "sec("; break;
				case CSC: f += "csc("; break;
				case COT: f += "cot("; break;
				default: break;
				}
				if(!Bieq1) f += Bi;
				f += "x) ";
			}else if(isOne){
				f += " ";//TODO trigonometric string construction
			}
		}
		f +="</html>";
		setFuncion(f);
		
	}
	
	protected void calculaY(){
		for(int i=0;i<getY().length;i++){
			for(int t=0;t<getTerminos();t++){
				BigDecimal res = getB()[t].multiply(getX()[i]);
				FuncionTrig tip = getTipos()[t];
				res = M.calculaTrig(tip, res, isXinRadians(), isXinDegrees());
				res = getA()[t].multiply(res);
				getY()[i] = getY()[i].add(res);
			}
		}
	}
	
	/**
	 * Constructor
	 * @param t
	 * @param tipos 
	 */
	public FuncionTrigonometrica(int t, FuncionTrig[] tipos) {
		super(TipoFuncion.TRIGONOMETRICA, t);
		setTipos(tipos);
		initB();
		initFuncionStrings();
	}
	
	/**
	 * @param a
	 * @param b
	 * @param xInRad
	 */
	public void update1(BigDecimal[]a, BigDecimal[] b, boolean xInRad){
		setA(a);	setB(b);
		initFuncionStrings();
		setXinRadians(xInRad);
		calculaY();
	}

}
