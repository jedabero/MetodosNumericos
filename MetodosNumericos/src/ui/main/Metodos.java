/**
 * 
 */
package ui.main;

import javax.swing.JOptionPane;

import resources.CustomException;
import resources.O;

/**
 * @author Jedabero
 *
 */
public class Metodos {

	/**
	 * @param args
	 * @throws CustomException 
	 */
	public static void main(String[] args) throws CustomException {
		if (args==null) {
		}else if(args.length>0){
			O.pln(args[0]);//TODO something with args, I'll figure it out later.
		}
		
		String options[] = {
				"1. Ra�ces de un polinomio",
				"2. Soluci�n de Sistemas de Ecuaciones Lineales",
				"3. Ajustes de polinomios",
				"4. Integraci�n Num�rica",
				"5. Salir"};
		
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
			new RaicesUI();
			break;
		case 2:
			//TODO Soluci�n de Sistemas de Ecuaciones Lineales (SSEL).
			O.pln(code);
			new SSELUI();
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
