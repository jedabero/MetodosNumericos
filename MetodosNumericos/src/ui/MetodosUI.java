/**
 * 
 */
package ui;

import javax.swing.JFrame;

/**
 * @author Jedabero
 *
 */
public class MetodosUI extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6637458677520322115L;

	/**
	 * 
	 */
	public MetodosUI(){
		super("Métodos Numéricos");
		setSize(1000, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		setLocationRelativeTo(getRootPane());
		setVisible(true);
	}
	
	
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MetodosUI();
	}
	
}
