package ui.numint;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import resources.math.Interval;

import funciones.Funcion;

/**
 * @author Jedabero
 *
 */
public class MetodosIntegracionPanel extends JPanel implements ActionListener,
		ItemListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2673246938861102403L;
	
	private Funcion funcion;

	private JTextField txtA;

	private JTextField txtB;

	private JSpinner spnrIt;
	
	/**
	 * 
	 */
	public MetodosIntegracionPanel(){
		super(new GridBagLayout());
		
	}
	
	/**
	 * @param funcion la función
	 */
	public void setFuncion(Funcion funcion) {
		this.funcion = funcion;
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String sN = spnrIt.getValue().toString();
		int n = Integer.parseInt(sN);//(int)spnrIt.getValue();
		
		String sA = txtA.getText();
		String sB = txtB.getText();
		BigDecimal A = new BigDecimal(sA);
		BigDecimal B = new BigDecimal(sB);
		Interval ab = new Interval(A, B);
		
		funcion.integracionTrapecioSimple(ab);

	}

}
