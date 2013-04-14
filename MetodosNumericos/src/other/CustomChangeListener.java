/**
 * 
 */
package other;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import resources.CustomException;
import resources.O;

import ui.main.RaicesUI;

/**
 * @author Jedabero
 *
 */
public class CustomChangeListener implements ChangeListener {
	
	private RaicesUI rui;
	private FuncionPanel fp;
	private int grado;
	private int it;
	
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
			O.pln("aasasd");
			grado = (int) spnr.getValue();
			try {
				fp = new FuncionPanel(grado);
			} catch (CustomException e1) {
				e1.printStackTrace();
			}
			rui.setFpnlFuncion(fp);
			break;
		case "iter":
			it = (int) spnr.getValue();
			O.pln("Iteraciones: "+it);
			break;
		default:
			break;
		}
		
	}
	

}
