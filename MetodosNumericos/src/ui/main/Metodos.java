/**
 * 
 */
package ui.main;

import javax.swing.JOptionPane;

import resources.O;

/**
 * @author Jedabero
 *
 */
public class Metodos {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String options[] = {
				"1. Raíces de un polinomio",
				"2. Solución de Sistemas de Ecuaciones Lineales",
				"3. Ajustes de polinomios",
				"4. Integración Numérica"};
		
		Object code = JOptionPane.showInputDialog(null, "Elija que desea hacer",
				"Que desea hacer?", JOptionPane.PLAIN_MESSAGE, null,
				options, options[0]);
		if (code==null) {
			code = "5";
		}
		
		int choice = Integer.parseInt(""+code.toString().charAt(0));
		
		switch (choice) {
		case 1:
			//TODO Raíces Polinómicas
			O.pln(code);
			
			break;
		case 2:
			//TODO Solución de Sistemas de Ecuaciones Lineales (SSEL).
			O.pln(code);
			break;
		case 3:
			//TODO Ajustes de polinomios
			O.pln(code);
			break;
		case 4:
			//TODO Integración Numérica
			O.pln(code);
			break;
		default:
			O.pln("But you didn't used me at all :(");
			break;
		}
		
	}

}
