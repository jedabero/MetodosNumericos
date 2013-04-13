/**
 * 
 */
package ui.main;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import resources.Add;

/**
 * @author Jedabero
 *
 */
public class RaicesUI {

	/**
	 * 
	 */
	public RaicesUI(){
		CustomWindowAdapter wa = new CustomWindowAdapter();
		JFrame theWindow = new JFrame("Raices de un Polinomio");
		theWindow.setSize(500, 500);
		theWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel thePanel = new JPanel(new GridBagLayout());
		
		//Tamaño polinomio
		JLabel lblGradoPol = new JLabel("Grado de polinomio", JLabel.CENTER);
		SpinnerNumberModel snmGradoPol = new SpinnerNumberModel(1, 1, 25, 1);
		JSpinner spnrGradoPol = new JSpinner(snmGradoPol);
		JButton btnCreaPol = new JButton("Crea Polinomio");
		JSeparator sprtr1 = new JSeparator(JSeparator.HORIZONTAL);
		Add.componente(thePanel, lblGradoPol, 	0, 0, 2, 2, 1.0, 1.0,
				GridBagConstraints.NONE, "");
		Add.componente(thePanel, spnrGradoPol, 	2, 0, 1, 2, 1.0, 1.0,
				GridBagConstraints.NONE, "Grado del Polinomio");
		Add.componente(thePanel, btnCreaPol, 	3, 0, 2, 2, 1.0, 1.0,
				GridBagConstraints.NONE, "");
		Add.componente(thePanel, sprtr1, 		0, 2, 5, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "");
		
		//Mostrar Polinomio
		JLabel lbl1 = new JLabel("lbl1", JLabel.CENTER);
		lbl1.setBorder(BorderFactory.createEtchedBorder());
		JButton btnVerGrafica = new JButton("Ver gráfica");
		JSeparator sprtr2 = new JSeparator(JSeparator.HORIZONTAL);
		Add.componente(thePanel, lbl1, 			0, 3, 4, 2, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(thePanel, btnVerGrafica, 4, 3, 1, 2, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(thePanel, sprtr2, 		0, 5, 5, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "");
		
		//Metodos ui
		JLabel lblTol = new JLabel("Tolerancia", JLabel.CENTER);
		JTextField txtTol = new JTextField("0.001", 8);
		JLabel lblIt = new JLabel("Iteraciones", JLabel.CENTER);
		SpinnerNumberModel snmIt = new SpinnerNumberModel(1, 1, 25, 1);
		JSpinner spnrIt = new JSpinner(snmIt);
		Add.componente(thePanel, lblTol, 		0, 6, 1, 1, 1.0, 1.0,
				GridBagConstraints.NONE, "");
		Add.componente(thePanel, txtTol, 		1, 6, 2, 1, 1.0, 1.0,
				GridBagConstraints.NONE, "");
		Add.componente(thePanel, lblIt, 		3, 6, 1, 1, 1.0, 1.0,
				GridBagConstraints.NONE, "");
		Add.componente(thePanel, spnrIt, 		4, 6, 1, 1, 1.0, 1.0,
				GridBagConstraints.NONE, "");
		
		JButton btnPuntoFijo = new JButton("<html>Punto<p>Fijo");
		JButton btnBiseccion = new JButton("Bisección");
		JButton btnNewtRaphs = new JButton("<html>Newton<p>Raphson");
		JButton btnMeSecante = new JButton("Secante");
		JButton btnReguFalsi = new JButton("<html>Regula<p>Falsi");
		Add.componente(thePanel, btnPuntoFijo, 		0, 7, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(thePanel, btnBiseccion, 		1, 7, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(thePanel, btnNewtRaphs, 		2, 7, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(thePanel, btnMeSecante, 		3, 7, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(thePanel, btnReguFalsi, 		4, 7, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		
		JLabel lblX = new JLabel("X =", JLabel.CENTER);
		Add.componente(thePanel, lblX,		 		2, 8, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		
		theWindow.addWindowListener(wa);
		theWindow.add(thePanel);
		theWindow.setLocationRelativeTo(theWindow.getRootPane());
		theWindow.setVisible(true);
		theWindow.setName("RaicesUI");
	}
	
}
