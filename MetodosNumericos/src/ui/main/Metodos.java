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
				"1. Ra�ces de un polinomio",
				"2. Soluci�n de Sistemas de Ecuaciones Lineales",
				"3. Ajustes de polinomios",
				"4. Integraci�n Num�rica"};
		
		Object code = JOptionPane.showInputDialog(null, "Elija que desea hacer",
				"Que desea hacer?", JOptionPane.PLAIN_MESSAGE, null,
				options, options[0]);
		if (code==null) {
			code = "5";
		}
		
		int choice = Integer.parseInt(""+code.toString().charAt(0));
		
		switch (choice) {
		case 1:
			//TODO Ra�ces Polin�micas
			O.pln(code);
			
			break;
		case 2:
			//TODO Soluci�n de Sistemas de Ecuaciones Lineales (SSEL).
			O.pln(code);
			break;
		case 3:
			//TODO Ajustes de polinomios
			O.pln(code);
			break;
		case 4:
			//TODO Integraci�n Num�rica
			O.pln(code);
			break;
		default:
			O.pln("But you didn't used me at all :(");
			break;
		}
		
	}

}
