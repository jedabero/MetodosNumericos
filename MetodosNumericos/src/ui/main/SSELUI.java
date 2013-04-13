/**
 * 
 */
package ui.main;

import metodosnumericos.MetodosMatrices;

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
