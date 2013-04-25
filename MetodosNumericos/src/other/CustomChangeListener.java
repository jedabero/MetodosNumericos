/**
 * 
 */
package other;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
		switch (spnr.getName().charAt(0)) {//TODO fix this to 1.7!
		case 'g':
			grado = Integer.parseInt(spnr.getValue().toString());//(int)spnr.getValue();
			fp = new FuncionPanel(grado);
			rui.setFpnlFuncion(fp);
			break;
		default:
			break;
		}
		
	}
	

}
