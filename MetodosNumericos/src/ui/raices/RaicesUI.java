/**
 * 
 */
package ui.raices;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import other.CustomActionListener;
import other.CustomChangeListener;
import resources.Add;
import funciones.Funcion;

/**
 * @author Jedabero
 *
 */
public class RaicesUI extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5011837883282426768L;
	
	private FuncionPanel fpnlFuncion;
	private JLabel lblEq;
	private MetodosPanel mpnlMetodos; 
	private Funcion funcion;
	private int grad;
	/**
	 * 
	 */
	public RaicesUI() {
		super(new GridBagLayout());
		
		initComponents();
		
		setName("RaicesUI");
	}
	
	private void initComponents() {//Polinomio
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
		Add.componente(this, lblGradoPol, 			0, 0, 2, 1, 1.0, 1.0,
				GridBagConstraints.NONE, "");
		Add.componente(this, spnrGradoPol, 			2, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "Grado del Polinomio");
		Add.componente(this, btnCreaPol, 			3, 0, 2, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(this, fpnlFuncion, 			0, 1, 5, 2, 1.0, 1.0,
				GridBagConstraints.BOTH, "Edita los coeficientes de la función");
		//3 - Separator
		Add.componente(this, sprtr1, 				0, 3, 5, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "");
		
		//4 - Mostrar Polinomio
		Add.componente(this, lblEq, 				0, 4, 4, 2, 1.0, 1.0,
				GridBagConstraints.BOTH, "Función");
		Add.componente(this, btnVerGrafica, 		4, 4, 1, 2, 1.0, 1.0,
				GridBagConstraints.BOTH, "Vea la gráfica de está función en " +
						"una nueva ventana");
		//6 - Separator
		Add.componente(this, sprtr2, 				0, 6, 5, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "");
		//7 - MétodosPanel
		Add.componente(this, mpnlMetodos, 			0, 7, 5, 1, 1.0, 1.0,
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
		this.remove(this.fpnlFuncion);
		this.fpnlFuncion = fpnlFuncion;
		Add.componente(this, fpnlFuncion, 			0, 1, 5, 2, 1.0, 1.0,
				GridBagConstraints.BOTH, "Edita los coeficientes de la función");
		this.revalidate();
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
