/**
 * 
 */
package ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import other.CustomWindowAdapter;

import resources.CustomException;
import resources.O;
import ui.main.RaicesUI;
import ui.main.SSELUI;

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
				"1. Raíces de un polinomio",
				"2. Solución de Sistemas de Ecuaciones Lineales",
				"3. Ajustes de polinomios",
				"4. Integración Numérica",
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
			O.pln(code);
			new RaicesUI();
			break;
		case 2:
			O.pln(code);
			new SSELUI();
			break;
		case 3:
			//TODO Ajustes de polinomios
			O.pln(code);
			small();
			break;
		case 4:
			//TODO Integración Numérica
			O.pln(code);
			small();
			break;
		default:
			O.pln("But you didn't used me at all :(");
			break;
		}
		
	}
	
	/**
	 * @return an info frame
	 */
	public static JFrame small(){
		JFrame t = new JFrame("Información");
		t.setName("small");
		t.setSize(400, 200);
		t.add(new JLabel("Esta caracteristica no está implementada todavía.", 0));
		t.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		t.addWindowListener(new CustomWindowAdapter());
		t.setLocationRelativeTo(t.getRootPane());
		t.setVisible(true);
		return t;
	}

}
