/**
 * 
 */
package ui.editors;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import resources.math.funciones.Funcion;

/**
 * @author Jedabero
 * 
 */
public interface EditaFuncion extends ChangeListener {

	public void init();

	public void addComponents();

	public void layoutCoefsPanel();

	public Funcion getFuncion();

	@Override
	public void stateChanged(ChangeEvent e);

}
