/**
 * 
 */
package ui.ssel;

import other.CustomWindowAdapter;
import main.MetodosMatrices;

/**
 * @author Jedabero
 *
 */
public class SSELUI {
	
	/**
	 * 
	 */
	public SSELUI(){
		CustomWindowAdapter wa = new CustomWindowAdapter();
		MetodosMatrices mm = new MetodosMatrices();
		mm.addWindowListener(wa);
		mm.setVisible(true);
		mm.setName("SSELUI");
	}
	
}
