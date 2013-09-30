/**
 * 
 */
package ui.editors;

import java.awt.event.ItemListener;

import javax.swing.event.ChangeListener;

/**
 * @author jedabero
 *
 */
public interface Editors extends ChangeListener, ItemListener {
	
	public void init();
	
	public void addComponents();
	
}
