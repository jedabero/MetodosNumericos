/**
 * 
 */
package other;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import resources.CustomException;
import ui.Metodos;

/**
 * @author Jedabero
 *
 */
public class CustomWindowAdapter extends WindowAdapter {
	//Just maybe
	public void windowOpened(WindowEvent e) {}
	
	public void windowClosing(WindowEvent e) {
		javax.swing.JFrame a = (javax.swing.JFrame)e.getSource();
		a.dispose();
		String args[] = {a.getName()};
		
		try {
			Metodos.main(args);
		} catch (CustomException e1) {
			// TODO try/catch
			e1.printStackTrace();
		}
		
	}
	
	public void windowClosed(WindowEvent e) {}
	
    public void windowIconified(WindowEvent e) {}
    
    public void windowDeiconified(WindowEvent e) {}
    
    public void windowActivated(WindowEvent e) {}
    
    public void windowDeactivated(WindowEvent e) {}
    
	//Just maybe
    public void windowStateChanged(WindowEvent e) {}
    
    //Just maybe
    public void windowGainedFocus(WindowEvent e) {}
    
    //Just maybe
    public void windowLostFocus(WindowEvent e) {}
}