/**
 * 
 */
package other;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import resources.CustomException;

import ui.raices.FuncionPanel;
import ui.raices.RaicesUI;

/**
 * @author Jedabero
 *
 */
public class CustomChangeListener implements ChangeListener {
	
	private RaicesUI rui;
	private FuncionPanel fp;
	private int grado;
	
	/**
	 * @param rui
	 */
	public CustomChangeListener(RaicesUI rui){
		this.rui = rui;
		fp = rui.getFpnlFuncion();
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		JSpinner spnr = (JSpinner) e.getSource();
		switch (spnr.getName()) {
		case "grado":
			grado = (int) spnr.getValue();
			try {
				fp = new FuncionPanel(grado);
			} catch (CustomException e1) {
				e1.printStackTrace();
			}
			rui.setFpnlFuncion(fp);
			break;
		default:
			break;
		}
		
	}
	

}
