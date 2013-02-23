package resources;


import java.math.BigDecimal;

import math.Big;

import resources.Constantes.TipoFuncion;
import resources.Constantes.FuncionTrig;

import funciones.FuncionBase;
import funciones.FuncionPolinomica;
import funciones.FuncionTrigonometrica;

/**
 * @author <a href="https://twitter.com/Jedabero" target="_blank">Jedabero</a>
 * 
 */
public final class M {
	
	/**
	 * Crea una función al azar con parámetros aleatorios
	 * @return	la función aleatoria.
	 */
	public static FuncionBase funcionRandom(){
		int randTerminos = (int)(4*Math.random())+2;
		int randTipo = (int)(2*Math.random());
		System.out.println(randTipo);
		
		BigDecimal constA[] = new BigDecimal[randTerminos];
		BigDecimal constB[] = new BigDecimal[randTerminos];
		FuncionTrig tp[] = new FuncionTrig[randTerminos];
		
		BigDecimal r;
		for(int i=0;i<randTerminos;i++){
			r = BigDecimal.valueOf(3*Math.random()*Big.randomSign()).setScale(2, 2);
			constA[i] = r;
			r = BigDecimal.valueOf(3*Math.random()*Big.randomSign()).setScale(2, 2);
			constB[i] = r;
			tp[i] = FuncionTrig.values()[(int)(6*Math.random())];
		}
		
		switch(randTipo){
		case 0:
			FuncionPolinomica fp = new FuncionPolinomica(randTerminos-1);
			fp.update(FuncionBase.getPaso(), FuncionBase.getIntervalo(), fp.getGrado(), constA);
			return fp;
		case 1:
			FuncionTrigonometrica ft = new FuncionTrigonometrica(randTerminos, tp);
			ft.update1(constA, constB, true);
			return ft;
		case 2:
		case 3:
		case 4:
		default:
			FuncionBase fb = new FuncionBase(TipoFuncion.values()[randTipo], randTerminos);
			fb.updateConstantesTerminos(randTerminos, constA, constB);
			return fb;
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
			case SENO:
				r = Big.sin(bd); break;
			case COSENO:
				r = Big.cos(bd); break;
			case TANGENTE:
				r = Big.tan(bd); break;
			case SECANTE:
				r = Big.sec(bd); break;
			case COSECANTE:
				r = Big.csc(bd); break;
			case COTANGENTE:
				r = Big.cot(bd); break;
			default: break;
			}
		}else if(deg){
			switch(ft){
			case SENO:
				r = Big.sin(Big.toRadians(bd)); break;
			case COSENO:
				r = Big.cos(Big.toRadians(bd)); break;
			case TANGENTE:
				r = Big.tan(Big.toRadians(bd)); break;
			case SECANTE:
				r = Big.sec(Big.toRadians(bd)); break;
			case COSECANTE:
				r = Big.csc(Big.toRadians(bd)); break;
			case COTANGENTE:
				r = Big.cot(Big.toRadians(bd)); break;
			default: break;
			}
		}
		return r;
	}
	
}
