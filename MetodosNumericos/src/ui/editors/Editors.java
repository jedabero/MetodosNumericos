/**
 * 
 */
package ui.editors;

import java.awt.event.ItemListener;

import javax.swing.event.ChangeListener;

import resources.math.funciones.Termino;

/**
 * @author jedabero
 *
 */
public interface Editors extends ChangeListener, ItemListener {
	
	public void init();
	
	public void addComponents();
	
	public Termino getTermino();
}
