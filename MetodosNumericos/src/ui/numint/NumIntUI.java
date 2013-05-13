/**
 * 
 */
package ui.numint;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ui.raices.FuncionPanel;
import funciones.Funcion;

/**
 * @author Jedabero
 *
 */
public class NumIntUI extends JPanel implements ActionListener, ChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1094301241514865205L;
	
	private FuncionPanel fpnl;
	private JLabel lblEq;
	private Funcion funcion;
	
	
	/**
	 * 
	 */
	public NumIntUI(){
		super(new GridBagLayout());
		
		initComponents();
		
		setName("NumIntUI");
	}
	
	
	private void initComponents() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
