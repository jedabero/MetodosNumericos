/**
 * 
 */
package ui.raices;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import funciones.Funcion;

import resources.Add;

/**
 * @author Jedabero
 *
 */
public class MetodosPanel extends JPanel implements ItemListener, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6392349745120763796L;
	
	private JButton btnFind;
	private JComboBox<String> listaMetodos;
	private JLabel lblX0A;
	private JLabel lblX1B;
	private JLabel lblTol;
	private JLabel lblIt;
	private JLabel lblX;
	private JPanel pnlPF;
	private JPanel pnlB;
	private JPanel pnlNR;
	private JPanel pnlS;
	private JPanel pnlRF;
	private JSpinner spnrIt;
	private JTextField txtX0A;
	private JTextField txtX1B;
	private JTextField txtTol;
	private JTextField txtX;
	
	private Funcion funcion;
	
	/**
	 * @param f la función a sacar raíz
	 */
	public MetodosPanel(Funcion f){
		super(new GridBagLayout());
		funcion = f;
		
		String strMetodos[] = {"Punto Fijo", "Bissección", "Newton-Raphson",
				"Secante", "Regula-Falsi"};
		
		listaMetodos = new JComboBox<String>(strMetodos);

		pnlPF = new JPanel(new GridBagLayout());
		pnlB = new JPanel(new GridBagLayout());
		pnlNR = new JPanel(new GridBagLayout());
		pnlS = new JPanel(new GridBagLayout());
		pnlRF = new JPanel(new GridBagLayout());
		
		lblX0A = new JLabel();
		lblX1B = new JLabel();
		txtX0A = new JTextField("0");
		txtX1B = new JTextField("1");
		
		lblTol = new JLabel("Tolerancia", JLabel.CENTER);
		txtTol = new JTextField("0.001");
		lblIt = new JLabel("Iteraciones", JLabel.CENTER);
		SpinnerNumberModel snmIt = new SpinnerNumberModel(15, 1, 25, 1);
		spnrIt = new JSpinner(snmIt);
		
		btnFind = new JButton("Encontrar Raíz");
		
		lblX = new JLabel("X =", JLabel.CENTER);
		txtX = new JTextField();
		
		initComponents();
	}

	private void initComponents() {
		//0 - Lista para escoger lo que se verá.
		Add.componente(this, listaMetodos, 0, 0, 2, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "Selecciona un método.");
		//1 - Panel con el método seleccionado
		initPanelPF();
		//2 - Sección para mostrar el resultado.
		Add.componente(this, lblX, 0, 2, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(this, txtX, 2, 2, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
	}
	
	private void initPanelPF(){
		lblX0A.setText("<html>X<sub>0</sub></hmtl>");
		Add.componente(pnlPF, lblX0A, 0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlPF, txtX0A, 1, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "Punto inicial.");
		
		Add.componente(pnlPF, lblTol, 2, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlPF, txtTol, 3, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "Tolerancia.");
		
		Add.componente(pnlPF, lblIt, 4, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlPF, spnrIt, 5, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "Máximo número de iteraciones");
		
		Add.componente(this, pnlPF, 0, 1, 2, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
	}
	
	private void initPanelB(){
		lblX0A.setText("A");
		Add.componente(pnlPF, lblX0A, 0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlPF, txtX0A, 1, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "Limite inferior.");
		lblX1B.setText("B");
		Add.componente(pnlPF, lblX1B, 2, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlPF, txtX1B, 3, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "Limite superior.");
		
		Add.componente(pnlPF, lblTol, 0, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlPF, txtTol, 1, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "Tolerancia.");
		
		Add.componente(pnlPF, lblIt, 2, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlPF, spnrIt, 3, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "Máximo número de iteraciones");
		
		Add.componente(this, pnlB, 0, 1, 2, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
	}
	private void initPanelNR(){
		lblX0A.setText("<html>X<sub>0</sub></hmtl>");
		Add.componente(pnlPF, lblX0A, 0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlPF, txtX0A, 1, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "Punto inicial.");
		
		Add.componente(pnlPF, lblTol, 2, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlPF, txtTol, 3, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "Tolerancia.");
		
		Add.componente(pnlPF, lblIt, 4, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlPF, spnrIt, 5, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "Máximo número de iteraciones");
		
		Add.componente(this, pnlNR, 0, 1, 2, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
	}
	private void initPanelS(){
		lblX0A.setText("<html>X<sub>0</sub></hmtl>");
		Add.componente(pnlPF, lblX0A, 0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlPF, txtX0A, 1, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "Punto inicial.");
		lblX1B.setText("<html>X<sub>1</sub></hmtl>");
		Add.componente(pnlPF, lblX1B, 2, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlPF, txtX1B, 3, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "Punto inicial.");
		
		Add.componente(pnlPF, lblTol, 0, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlPF, txtTol, 1, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "Tolerancia.");
		
		Add.componente(pnlPF, lblIt, 2, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlPF, spnrIt, 3, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "Máximo número de iteraciones");
		
		Add.componente(this, pnlS, 0, 1, 2, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
	}
	private void initPanelRF(){
		lblX0A.setText("A");
		Add.componente(pnlPF, lblX0A, 0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlPF, txtX0A, 1, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "Limite izquierdo.");
		lblX1B.setText("B");
		Add.componente(pnlPF, lblX1B, 2, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlPF, txtX1B, 3, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "Limite derecho.");
		
		Add.componente(pnlPF, lblTol, 0, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlPF, txtTol, 1, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "Tolerancia.");
		
		Add.componente(pnlPF, lblIt, 2, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlPF, spnrIt, 3, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "Máximo número de iteraciones");
		
		Add.componente(this, pnlRF, 0, 1, 2, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
