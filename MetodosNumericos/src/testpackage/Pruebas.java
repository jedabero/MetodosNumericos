
package testpackage;

import java.math.BigDecimal;

import funciones.Funcion;
import funciones.Termino;

import resources.O;
import resources.math.Constantes.FuncionTrig;

/**
 *
 * @author jberdugo10
 */
public class Pruebas {
    
    /**
     * @param args the command line arguments
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        
    	BigDecimal a = BigDecimal.valueOf(2.1);
    	BigDecimal b = BigDecimal.valueOf(0.5).negate();
    	BigDecimal x = BigDecimal.valueOf(-1);
    	
    	Termino lista[] = new Termino[]{new Termino(), Termino.constante(a),
    			Termino.monomio(1, b), Termino.monomio(3, a),
    			Termino.trigonometrico(FuncionTrig.SIN, a, b),
    			Termino.trigonometrico(FuncionTrig.COS, a, b),
    			Termino.trigonometrico(FuncionTrig.TAN, a, b),
    			Termino.trigonometrico(FuncionTrig.SEC, a, b),
    			Termino.trigonometrico(FuncionTrig.CSC, a, b),
    			Termino.trigonometrico(FuncionTrig.COT, a, b),
    			Termino.exponencial(a, b),
    			Termino.logaritmo(a, b)};
    	
    	Funcion f = new Funcion(new java.util.ArrayList<Termino>(java.util.Arrays.asList(lista)));
    	O.pln(f);
    	
    	for (int i = 0; i < lista.length; i++) {
			Termino t = lista[i];
			O.pln(x+"|\t|"+t.valorImagen(x)+"|\t|"+t.getGeneric()+"|\t|"+t+"|\t"+t.getSpecific());
		}
    	
    }
}
