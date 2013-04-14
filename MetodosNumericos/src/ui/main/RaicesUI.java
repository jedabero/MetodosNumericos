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

import funciones.Funcion;

import other.CustomActionListener;
import other.CustomChangeListener;
import other.CustomWindowAdapter;
import other.FuncionPanel;

import resources.Add;
import resources.CustomException;

/**
 * @author Jedabero
 *
 */
public class RaicesUI {
	
	private FuncionPanel fpnlFuncion;
	private JPanel thePanel;
	private JLabel lblEq;
	private JLabel lblX;
	private JTextField txtX0A;
	private JTextField txtX1B;
	private Funcion funcion;
	private int grad;
	/**
	 * @throws CustomException 
	 * 
	 */
	public RaicesUI() throws CustomException{
		CustomWindowAdapter wa = new CustomWindowAdapter();
		JFrame theWindow = new JFrame("Raices de un Polinomio");
		theWindow.setSize(500, 500);
		theWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		thePanel = new JPanel(new GridBagLayout());
		
		//Polinomio
		grad = 2;
		JLabel lblGradoPol = new JLabel("Grado de polinomio", JLabel.CENTER);
		SpinnerNumberModel snmGradoPol = new SpinnerNumberModel(grad, 1, 25, 1);
		JSpinner spnrGradoPol = new JSpinner(snmGradoPol);
		spnrGradoPol.setName("grado");
		//Ingresar Polinomio
		fpnlFuncion = new FuncionPanel(grad);
		JButton btnCreaPol = new JButton("Crear Polinomio");
		
		JSeparator sprtr1 = new JSeparator(JSeparator.HORIZONTAL);
		
		//Mostrar Polinomio
		funcion = fpnlFuncion.getFnc();
		lblEq = new JLabel(funcion.getSpecific(), JLabel.CENTER);
		lblEq.setBorder(BorderFactory.createEtchedBorder());
		JButton btnVerGrafica = new JButton("Ver gráfica");
		
		JSeparator sprtr2 = new JSeparator(JSeparator.HORIZONTAL);
		
		//X0, X1, Intervalo [a, b]
		JLabel lblInterv = new JLabel("Puntos iniciales ó Intervalo [a, b]");
		JLabel lblX0A = new JLabel("<html>X<sub>0</sub> | A</hmtl>");
		JLabel lblX1B = new JLabel("<html>X<sub>1</sub> | B</hmtl>");
		txtX0A = new JTextField("0");
		txtX1B = new JTextField("1");
		//Tolerancia y maxIt
		JLabel lblTol = new JLabel("Tolerancia", JLabel.CENTER);
		JTextField txtTol = new JTextField("0.001");
		JLabel lblIt = new JLabel("Iteraciones", JLabel.CENTER);
		SpinnerNumberModel snmIt = new SpinnerNumberModel(15, 1, 25, 1);
		JSpinner spnrIt = new JSpinner(snmIt);
		spnrIt.setName("iter");
		//Métodos
		JButton btnPuntoFijo = new JButton("<html>Punto<p>Fijo");
		JButton btnBiseccion = new JButton("Bisección");
		JButton btnNewtRaphs = new JButton("<html>Newton<p>Raphson");
		JButton btnMeSecante = new JButton("Secante");
		JButton btnReguFalsi = new JButton("<html>Regula<p>Falsi");
		//Resultado
		lblX = new JLabel("X =", JLabel.CENTER);
		
		//0 - Tamaño Polinomio
		Add.componente(thePanel, fpnlFuncion, 			0, 0, 5, 2, 1.0, 1.0,
				GridBagConstraints.BOTH, "Edita los coeficientes de la función");
		Add.componente(thePanel, lblGradoPol, 			0, 2, 2, 1, 1.0, 1.0,
				GridBagConstraints.NONE, "");
		Add.componente(thePanel, spnrGradoPol, 			2, 2, 1, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "Grado del Polinomio");
		Add.componente(thePanel, btnCreaPol, 			3, 2, 2, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		//3 - Separator
		Add.componente(thePanel, sprtr1, 				0, 3, 5, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "");
		
		//4 - Mostrar Polinomio
		Add.componente(thePanel, lblEq, 				0, 4, 4, 2, 1.0, 1.0,
				GridBagConstraints.BOTH, "Función");
		Add.componente(thePanel, btnVerGrafica, 		4, 4, 1, 2, 1.0, 1.0,
				GridBagConstraints.BOTH, "Vea la gráfica de está función en " +
						"una nueva ventana");
		//6 - Separator
		Add.componente(thePanel, sprtr2, 				0, 6, 5, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "");
		//7 - X0, X1, Intervalo [a, b]
		Add.componente(thePanel, lblInterv, 			0, 7, 5, 1, 1.0, 1.0,
				GridBagConstraints.NONE, "");
		Add.componente(thePanel, lblX0A, 				0, 8, 1, 1, 1.0, 1.0,
				GridBagConstraints.NONE, "");
		Add.componente(thePanel, txtX0A, 				1, 8, 1, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "");
		Add.componente(thePanel, lblX1B, 				3, 8, 1, 1, 1.0, 1.0,
				GridBagConstraints.NONE, "");
		Add.componente(thePanel, txtX1B, 				4, 8, 1, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "");
		//9 - Tolerancia y maxIt
		Add.componente(thePanel, lblTol, 				0, 9, 1, 1, 1.0, 1.0,
				GridBagConstraints.NONE, "");
		Add.componente(thePanel, txtTol, 				1, 9, 2, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "Tolerancia");
		Add.componente(thePanel, lblIt, 				3, 9, 1, 1, 1.0, 1.0,
				GridBagConstraints.NONE, "");
		Add.componente(thePanel, spnrIt, 				4, 9, 1, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "Máximo número de iteraciones");
		
		//10 - Métodos
		Add.componente(thePanel, btnPuntoFijo, 			0, 10, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(thePanel, btnBiseccion, 			1, 10, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(thePanel, btnNewtRaphs,		 	2, 10, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(thePanel, btnMeSecante, 			3, 10, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(thePanel, btnReguFalsi, 			4, 10, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		//10 - Resultado
		Add.componente(thePanel, lblX,		 			0, 11, 5, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		
		//Add ActionListeners
		CustomActionListener al = new CustomActionListener(this);
		btnCreaPol.addActionListener(al);
		btnVerGrafica.addActionListener(al);
		btnPuntoFijo.addActionListener(al);
		btnBiseccion.addActionListener(al);
		btnNewtRaphs.addActionListener(al);
		btnMeSecante.addActionListener(al);
		btnReguFalsi.addActionListener(al);
		
		CustomChangeListener cl = new CustomChangeListener(this);
		spnrGradoPol.addChangeListener(cl);
		spnrIt.addChangeListener(cl);
		
		
		theWindow.addWindowListener(wa);
		theWindow.add(thePanel);
		theWindow.setLocationRelativeTo(theWindow.getRootPane());
		theWindow.setVisible(true);
		theWindow.setName("RaicesUI");
	}

	/**
	 * @return the fpnlFuncion
	 */
	public FuncionPanel getFpnlFuncion() {
		return fpnlFuncion;
	}

	/**
	 * @param fpnlFuncion the fpnlFuncion to set
	 */
	public void setFpnlFuncion(FuncionPanel fpnlFuncion) {
		thePanel.remove(this.fpnlFuncion);
		this.fpnlFuncion = fpnlFuncion;
		Add.componente(thePanel, fpnlFuncion, 			0, 0, 5, 2, 1.0, 1.0,
				GridBagConstraints.BOTH, "Edita los coeficientes de la función");
		thePanel.revalidate();
	}

	/**
	 * @return the lblEq
	 */
	public JLabel getLblEq() {
		return lblEq;
	}

	/**
	 * @return the lblX
	 */
	public JLabel getLblX() {
		return lblX;
	}

	/**
	 * @return the txtX0A
	 */
	public JTextField getTxtX0A() {
		return txtX0A;
	}

	/**
	 * @return the txtX1B
	 */
	public JTextField getTxtX1B() {
		return txtX1B;
	}
	
	
	
}
