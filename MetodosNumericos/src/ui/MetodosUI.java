/**
 * 
 */
package ui;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import resources.Add;

/**
 * @author Jedabero
 *
 */
public class MetodosUI extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6637458677520322115L;
	
	private JMenuBar mnbPrincipal = new JMenuBar();
	
	/**
	 * 
	 */
	public MetodosUI(){
		super("Métodos Numéricos");
		setSize(1000, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initBarraMenu();
		
		
		setLocationRelativeTo(getRootPane());
		setVisible(true);
	}
	
	private void initBarraMenu() {
		
		//Acciones
		JMenu menuAcciones = new JMenu("Acciones...");
		menuAcciones.setMnemonic('A');
		Add.menuItem(menuAcciones, "Polinomios", "Grafica y obtén las raices de un polinomio", null, 'P');
		
		
		
	}






	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MetodosUI();
	}
	
}
