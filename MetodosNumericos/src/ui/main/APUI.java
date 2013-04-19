/**
 * 
 */
package ui.main;

import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import other.CustomWindowAdapter;
import resources.CustomException;

/**
 * @author Jedabero
 *
 */
public class APUI {
	
	private JPanel thePanel;
	
	/**
	 * @throws CustomException
	 */
	public APUI() throws CustomException{
		CustomWindowAdapter wa = new CustomWindowAdapter();
		JFrame theWindow = new JFrame("Raices de un Polinomio");
		theWindow.setSize(500, 500);
		theWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		thePanel = new JPanel(new GridBagLayout());
		
		theWindow.addWindowListener(wa);
		theWindow.add(thePanel);
		theWindow.setLocationRelativeTo(theWindow.getRootPane());
		theWindow.setVisible(true);
		theWindow.setName("APUI");
	}
	
}
