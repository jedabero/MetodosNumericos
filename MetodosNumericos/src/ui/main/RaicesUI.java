/**
 * 
 */
package ui.main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Jedabero
 *
 */
public class RaicesUI {

	/**
	 * 
	 */
	public RaicesUI(){
		CustomWindowAdapter wa = new CustomWindowAdapter();
		JFrame theWindow = new JFrame("TITULO");
		theWindow.setSize(500, 500);
		theWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel thePanel = new JPanel();
		
		JTextField jtf = new JTextField("Numero", 10);
		
		thePanel.add(jtf);
		
		theWindow.addWindowListener(wa);
		theWindow.add(thePanel);
		theWindow.setLocationRelativeTo(theWindow.getRootPane());
		theWindow.setVisible(true);
		theWindow.setName("RaicesUI");
	}
	
}
