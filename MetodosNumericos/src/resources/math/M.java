package resources.math;


import java.math.BigDecimal;
import java.util.Random;

import resources.CustomException;
import resources.O;
import resources.math.Constantes.FuncionTrig;
import resources.math.Constantes.Tipo;
import funciones.Funcion;
import funciones.Termino;

/**
 * @author <a href="https://twitter.com/Jedabero" target="_blank">Jedabero</a>
 * 
 */
public final class M {
	
	/**
	 * Crea una función al azar con parámetros aleatorios
	 * @return	la función aleatoria.
	 */
	public static Funcion funcionRandom(){
		Random rand = new Random();
		int randTerminos = (int)(4*rand.nextDouble())+2;
		int randTipo = (int)(5*rand.nextDouble());//TODO racional
		Tipo randTF = Tipo.values()[randTipo];
		O.pln(randTipo);
		
		BigDecimal constA[] = new BigDecimal[randTerminos];
		BigDecimal constB[] = new BigDecimal[randTerminos];
		FuncionTrig tp[] = new FuncionTrig[randTerminos];
		
		BigDecimal r;
		for(int i=0;i<randTerminos;i++){
			r = BigDecimal.valueOf(3*Math.random()*Math2.randomSign()).setScale(2, 2);
			constA[i] = r;
			r = BigDecimal.valueOf(3*Math.random()*Math2.randomSign()).setScale(2, 2);
			constB[i] = r;
			tp[i] = FuncionTrig.values()[(int)(6*Math.random())];
		}
		
		Funcion p = null;
		switch(randTF){
		case CONSTANTE:
			p = new Funcion(Termino.constante(constA[0]));
			return p;
		case POLINOMICA:
			try {
				p = Funcion.polinomio(randTerminos-1, constA);
			} catch (CustomException e) {
				e.printStackTrace();
			}
			return p;
		case TRIGONOMETRICA:
			p = Funcion.trigonometrica(tp[0], constA[0], constB[0]);
			return p;
		case EXPONENCIAL:
			p = new Funcion(Termino.exponencial(constA[0], constB[0], null));
			return p;
		case LOGARITMICA:
			p = new Funcion(Termino.logaritmo(constA[0], constB[0], null));
			return p;
		case RACIONAL:
		default:
			return p;
		}
	}
	
	/**
	 * Calcula
	 * @param	ft función trigonométrica
	 * @param	bd ángulo
	 * @param	rad está en radianes?
	 * @param	deg está en grados?
	 * @return	el resultado
	 */
	public static BigDecimal calculaTrig(FuncionTrig ft, BigDecimal bd,
			boolean rad, boolean deg){
		BigDecimal r = bd;
		if(rad){
			switch(ft){
			case SIN:
				r = Big.sin(bd); break;
			case COS:
				r = Big.cos(bd); break;
			case TAN:
				r = Big.tan(bd); break;
			case SEC:
				r = Big.sec(bd); break;
			case CSC:
				r = Big.csc(bd); break;
			case COT:
				r = Big.cot(bd); break;
			default: break;
			}
		}else if(deg){
			switch(ft){
			case SIN:
				r = Big.sin(Big.toRadians(bd)); break;
			case COS:
				r = Big.cos(Big.toRadians(bd)); break;
			case TAN:
				r = Big.tan(Big.toRadians(bd)); break;
			case SEC:
				r = Big.sec(Big.toRadians(bd)); break;
			case CSC:
				r = Big.csc(Big.toRadians(bd)); break;
			case COT:
				r = Big.cot(Big.toRadians(bd)); break;
			default: break;
			}
		}
		return r;
	}
	
}
