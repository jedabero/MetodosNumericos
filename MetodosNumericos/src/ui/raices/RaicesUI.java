/**
 * 
 */
package ui.raices;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import funciones.Funcion;

import other.CustomActionListener;
import other.CustomChangeListener;
import other.CustomWindowAdapter;

import resources.Add;
import resources.CustomException;

/**
 * @author Jedabero
 *
 */
public class RaicesUI {
	
	private JPanel thePanel;
	private FuncionPanel fpnlFuncion;
	private JLabel lblEq;
	private MetodosPanel mpnlMetodos; 
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
		
		initComponents();
		
		theWindow.addWindowListener(wa);
		theWindow.add(thePanel);
		theWindow.setLocationRelativeTo(theWindow.getRootPane());
		theWindow.setVisible(true);
		theWindow.setName("RaicesUI");
	}
	
	private void initComponents() throws CustomException{//Polinomio
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
		
		//Métodos
		mpnlMetodos = new MetodosPanel();
		
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
		//7 - MétodosPanel
		Add.componente(thePanel, mpnlMetodos, 			0, 7, 5, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		
		
		
		//Add ActionListeners
		CustomActionListener al = new CustomActionListener(this);
		btnCreaPol.addActionListener(al);
		btnVerGrafica.addActionListener(al);
		CustomChangeListener cl = new CustomChangeListener(this);
		spnrGradoPol.addChangeListener(cl);
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
	 * @return the mpnlMetodos
	 */
	public MetodosPanel getMpnlMetodos() {
		return mpnlMetodos;
	}

	/**
	 * @return the lblEq
	 */
	public JLabel getLblEq() {
		return lblEq;
	}
	
}
