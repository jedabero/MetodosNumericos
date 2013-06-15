
package testpackage;

import java.math.BigDecimal;

import resources.math.funciones.Funcion;
import resources.math.funciones.Termino;

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
        
    	BigDecimal a = BigDecimal.valueOf(1);
    	BigDecimal b = BigDecimal.valueOf(2);
    	BigDecimal x = BigDecimal.valueOf(1);
    	
    	Funcion fint = Funcion.binomionN(2, a);
    	Funcion fint2 = Funcion.trigonometrica(FuncionTrig.SIN, a, a);
    	O.pln("test func int= "+fint);
    	Termino lista[] = new Termino[]{new Termino(), Termino.constante(a),
    			Termino.monomio(1, b, null), Termino.monomio(2, a, null),
    			Termino.trigonometrico(FuncionTrig.SIN, a, b, fint),
    			Termino.trigonometrico(FuncionTrig.COS, a, b, fint2),
    			Termino.trigonometrico(FuncionTrig.TAN, a, b, fint),
    			Termino.trigonometrico(FuncionTrig.SEC, a, b, fint2),
    			Termino.trigonometrico(FuncionTrig.CSC, a, b, fint),
    			Termino.trigonometrico(FuncionTrig.COT, a, b, fint2),
    			Termino.exponencial(a, b, fint),
    			Termino.logaritmo(a, b, fint)};
    	
    	for (int i = 0; i < lista.length; i++) {
			Termino t = lista[i];
			O.pln(x+"|\t|"+t.valorImagen(x)+"|\t|"+t.getGeneric()+"|\t|"+t+"|\t"+t.getSpecific());
		}
    	
    	javax.swing.JFrame jf = new javax.swing.JFrame("tests");
    	jf.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    	jf.setSize(400, 400);
    	
    	String lblS = "<html>f1(x) = "+fint.getSpecific()+"<br />";
    	lblS += "f2(x) = "+fint2.getSpecific()+"<br />";
    	for (int i = 0; i < lista.length; i++) {
			lblS += "F<sub>"+i+"</sub> : "+lista[i].getSpecific()+"<br />";
		}
    	lblS += "<sub>1<sup>2<sub>3<sup>4</sup>3</sub>2</sup>1</sub>";
    	jf.add(new javax.swing.JLabel(lblS + "</html>", 0));
    	jf.setVisible(true);
    	
    }
}
