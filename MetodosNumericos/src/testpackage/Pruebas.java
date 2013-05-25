
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
        
    	BigDecimal a = BigDecimal.valueOf(0.1);
    	BigDecimal b = BigDecimal.valueOf(0.1);
    	BigDecimal x = BigDecimal.valueOf(1);
    	
    	Funcion fi = new Funcion(Termino.trigonometrico(FuncionTrig.SIN, a, b, null));
    	O.pln("test func int= "+fi);
    	Termino lista[] = new Termino[]{new Termino(), Termino.constante(a),
    			Termino.monomio(1, b, null), Termino.monomio(2, a, fi),
    			Termino.trigonometrico(FuncionTrig.SIN, a, b, fi),
    			Termino.trigonometrico(FuncionTrig.COS, a, b, null),
    			Termino.trigonometrico(FuncionTrig.TAN, a, b, null),
    			Termino.trigonometrico(FuncionTrig.SEC, a, b, null),
    			Termino.trigonometrico(FuncionTrig.CSC, a, b, null),
    			Termino.trigonometrico(FuncionTrig.COT, a, b, null),
    			Termino.exponencial(a, b, null),
    			Termino.logaritmo(a, b, null)};
    	O.pln(lista[0]);
    	O.pln(lista[0].getGeneric());
    	O.pln(lista[0].getSpecific());
    	O.pln(lista[3]);
    	O.pln(lista[3].getGeneric());
    	O.pln(lista[3].getSpecific());
    	Funcion f = new Funcion(new java.util.ArrayList<Termino>(java.util.Arrays.asList(lista)));
    	O.pln(f);
    	
    	for (int i = 0; i < lista.length; i++) {
			Termino t = lista[i];
			O.pln(x+"|\t|"+t.valorImagen(x)+"|\t|"+t.getGeneric()+"|\t|"+t+"|\t"+t.getSpecific());
		}
    	
    }
}
