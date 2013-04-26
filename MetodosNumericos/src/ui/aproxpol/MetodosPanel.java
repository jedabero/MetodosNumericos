/**
 * 
 */
package ui.aproxpol;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import funciones.Funcion;

/**
 * @author Jedabero
 *
 */
public class MetodosPanel extends JPanel implements ItemListener, ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3368266880335331013L;
	private static final String strMetodos[] = {
		"Polinomio Simple", "Polinomio de Lagrange",
		"Newton-Raphson", "Secante", "Regula-Falsi"};
	
	private JButton btnCalcula;
	private JComboBox<String> listaMetodos;
	private JLabel lblPol;
	
	private Funcion funcion;
	/**
	 * 
	 */
	public MetodosPanel(){
		super(new GridBagLayout());
		
		listaMetodos = new JComboBox<String>(strMetodos);
		listaMetodos.addItemListener(this);
		
		lblPol = new JLabel();
		lblPol.getIconTextGap();
		
		btnCalcula = new JButton("AjustarPolinomio");
		btnCalcula.addActionListener(this);
	}
	

	/**
	 * @param funcion la función
	 */
	public void setFuncion(Funcion funcion) {
		this.funcion = funcion;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		funcion.derivada();
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}

}
