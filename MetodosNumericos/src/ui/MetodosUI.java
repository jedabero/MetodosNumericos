/**
 * 
 */
package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;

import other.Add;

/**
 * @author Jedabero
 *
 */
public class MetodosUI extends JFrame implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6637458677520322115L;
	
	private JMenuBar mnbPrincipal = new JMenuBar();
	
	/**
	 * 
	 */
	public MetodosUI(){
		super("M�todos Num�ricos");
		setSize(1000, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initBarraMenu();
		
		
		setLocationRelativeTo(getRootPane());
		setVisible(true);
	}
	
	private void initBarraMenu() {
		Add.menu(mnbPrincipal, "Acciones", 'A',
				new int[]{0, 0, 0, 0, 2, 0},
				new String[]{
				"Polinomios",
				"Sistemas de Ecuaciones Lineales",
				"Aproximacion Polinomial",
				"Integraci�n Num�rica",
				"",
				"Salir"},
				new String[]{
				"Grafica y obt�n las raices de polinomios.",
				"Soluciona sistemas Ax = B",
				"Obt�n la ecuaci�n de los puntos una tabla tabulada",
				"Integraci�n",
				"",""},
				new ActionListener[]{this,this,this,this,null,this},
				null, null, null);
		Add.menu(mnbPrincipal, "Ayuda", 'Y',
				new int[]{0, 2, 1},
				new String[]{
				"Informaci�n",
				"",
				"Que hacer?"},
				new String[]{
				"Informaci�n sobre esta aplicaci�n.",
				"", ""},
				new ActionListener[]{this,null,this},
				new String[][]{{},{},{"Hi","there","halp","me", "ok", "bye"}},
				new boolean[][]{{},{},{false, true, false, true, false}},
				new char[][]{{},{},{'R','R','R','C','C',' '}});
		setJMenuBar(mnbPrincipal);
	}





	@Override
	public void actionPerformed(ActionEvent e) {
		String action = ((JMenuItem) e.getSource()).getText();
		
		if (action.equals("Salir")) {
			dispose();
		} else {

		}
		
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MetodosUI();
	}
	
}
