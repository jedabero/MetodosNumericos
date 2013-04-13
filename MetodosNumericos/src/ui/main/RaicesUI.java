/**
 * 
 */
package ui.main;

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

import other.CustomWindowAdapter;
import other.FuncionPanel;

import resources.Add;
import resources.CustomException;

/**
 * @author Jedabero
 *
 */
public class RaicesUI {
	
	/**
	 * @throws CustomException 
	 * 
	 */
	public RaicesUI() throws CustomException{
		CustomWindowAdapter wa = new CustomWindowAdapter();
		JFrame theWindow = new JFrame("Raices de un Polinomio");
		theWindow.setSize(500, 500);
		theWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel thePanel = new JPanel(new GridBagLayout());
		
		//Polinomio
		int grad = 2;
		JLabel lblGradoPol = new JLabel("Grado de polinomio", JLabel.CENTER);
		SpinnerNumberModel snmGradoPol = new SpinnerNumberModel(grad, 1, 25, 1);
		JSpinner spnrGradoPol = new JSpinner(snmGradoPol);
		//Ingresar Polinomio
		FuncionPanel fp = new FuncionPanel(grad);
		JButton btnCreaPol = new JButton("Crear Polinomio");
		
		JSeparator sprtr1 = new JSeparator(JSeparator.HORIZONTAL);
		
		//Mostrar Polinomio
		JLabel lbl1 = new JLabel("lbl1", JLabel.CENTER);
		lbl1.setBorder(BorderFactory.createEtchedBorder());
		JButton btnEditar = new JButton("Editar polinomio");
		JButton btnVerGrafica = new JButton("Ver gráfica");
		
		JSeparator sprtr2 = new JSeparator(JSeparator.HORIZONTAL);
		
		//Tolerancia y maxIt
		JLabel lblTol = new JLabel("Tolerancia", JLabel.CENTER);
		JTextField txtTol = new JTextField("0.001", 8);
		JLabel lblIt = new JLabel("Iteraciones", JLabel.CENTER);
		SpinnerNumberModel snmIt = new SpinnerNumberModel(15, 1, 25, 1);
		JSpinner spnrIt = new JSpinner(snmIt);
		//Métodos
		JButton btnPuntoFijo = new JButton("<html>Punto<p>Fijo");
		JButton btnBiseccion = new JButton("Bisección");
		JButton btnNewtRaphs = new JButton("<html>Newton<p>Raphson");
		JButton btnMeSecante = new JButton("Secante");
		JButton btnReguFalsi = new JButton("<html>Regula<p>Falsi");
		//Resultado
		JLabel lblX = new JLabel("X =", JLabel.CENTER);
		
		//0 - Tamaño Polinomio
		Add.componente(thePanel, lblGradoPol, 			0, 0, 2, 1, 1.0, 1.0,
				GridBagConstraints.NONE, "");
		Add.componente(thePanel, spnrGradoPol, 			2, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "Grado del Polinomio");
		Add.componente(thePanel, btnCreaPol, 			4, 0, 1, 3, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(thePanel, fp, 					0, 1, 4, 2, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		//4 - Separator
		Add.componente(thePanel, sprtr1, 				0, 4, 5, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "");
		
		//5 - Mostrar Polinomio
		Add.componente(thePanel, lbl1, 					0, 5, 4, 2, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(thePanel, btnEditar, 			4, 5, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(thePanel, btnVerGrafica, 		4, 6, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		//7 - Separator
		Add.componente(thePanel, sprtr2, 				0, 7, 5, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "");
		
		//8 - Tolerancia y maxIt
		Add.componente(thePanel, lblTol, 				0, 8, 1, 1, 1.0, 1.0,
				GridBagConstraints.NONE, "");
		Add.componente(thePanel, txtTol, 				1, 8, 2, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "");
		Add.componente(thePanel, lblIt, 				3, 8, 1, 1, 1.0, 1.0,
				GridBagConstraints.NONE, "");
		Add.componente(thePanel, spnrIt, 				4, 8, 1, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "");
		
		//9 - Métodos
		Add.componente(thePanel, btnPuntoFijo, 			0, 9, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(thePanel, btnBiseccion, 			1, 9, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(thePanel, btnNewtRaphs,		 	2, 9, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(thePanel, btnMeSecante, 			3, 9, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(thePanel, btnReguFalsi, 			4, 9, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		//10 - Resultado
		Add.componente(thePanel, lblX,		 			2, 10, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		
		theWindow.addWindowListener(wa);
		theWindow.add(thePanel);
		theWindow.setLocationRelativeTo(theWindow.getRootPane());
		theWindow.setVisible(true);
		theWindow.setName("RaicesUI");
	}
	
}
