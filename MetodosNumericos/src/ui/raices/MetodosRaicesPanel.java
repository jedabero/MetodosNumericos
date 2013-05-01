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
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import resources.Add;
import resources.math.Interval;
import resources.O;
import funciones.Funcion;

/**
 * @author Jedabero
 *
 */
public class MetodosRaicesPanel extends JPanel implements ItemListener, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6392349745120763796L;
	
	private static final String strMetodos[] = {"Punto Fijo", "Bissección",
		"Newton-Raphson", "Secante", "Regula-Falsi"};
	private int currMethod = 0;
	
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
	 * 
	 */
	public MetodosRaicesPanel(){
		super(new GridBagLayout());
		
		listaMetodos = new JComboBox<String>(strMetodos);
		listaMetodos.addItemListener(this);
		
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
		btnFind.addActionListener(this);
		
		lblX = new JLabel("X =", JLabel.CENTER);
		txtX = new JTextField();
		
		initComponents();
	}

	private void initComponents() {
		//0 - Lista para escoger lo que se verá.
		Add.componente(this, listaMetodos, 0, 0, 3, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "Selecciona un método.");
		//1 - Panel con el método seleccionado
		initPanelPF();
		//2 - Sección para mostrar el resultado.
		Add.componente(this, lblX, 0, 2, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(this, txtX, 1, 2, 1, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "");
		Add.componente(this, btnFind, 2, 2, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "Encuetre la raíz por el método seleccionado.");
	}
	
	private void initPanelPF(){
		//0, 1 - Punto inicial
		lblX0A.setText("<html>X<sub>0</sub></hmtl>");
		Add.componente(pnlPF, lblX0A, 0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlPF, txtX0A, 1, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "Punto inicial.");
		//2, 3 - Tolerancia
		Add.componente(pnlPF, lblTol, 2, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlPF, txtTol, 3, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "Tolerancia.");
		//4, 5 - Interaciones
		Add.componente(pnlPF, lblIt, 4, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlPF, spnrIt, 5, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "Máximo número de iteraciones");
		
		//Añadir al panel principal
		Add.componente(this, pnlPF, 0, 1, 3, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
	}

	private void initPanelB(){
		//0 - Intervalo AB
		lblX0A.setText("A");
		Add.componente(pnlB, lblX0A, 0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlB, txtX0A, 1, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "Limite inferior.");
		lblX1B.setText("B");
		Add.componente(pnlB, lblX1B, 2, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlB, txtX1B, 3, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "Limite superior.");
		//1 - Tolerancia e Iteraciones
		Add.componente(pnlB, lblTol, 0, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlB, txtTol, 1, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "Tolerancia.");
		
		Add.componente(pnlB, lblIt, 2, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlB, spnrIt, 3, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "Máximo número de iteraciones");
		
		//Añadir al panel principal
		Add.componente(this, pnlB, 0, 1, 3, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
	}
	private void initPanelNR(){
		//0, 1 - Punto Inicial
		lblX0A.setText("<html>X<sub>0</sub></hmtl>");
		Add.componente(pnlNR, lblX0A, 0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlNR, txtX0A, 1, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "Punto inicial.");
		//2, 3 - Tolerancia
		Add.componente(pnlNR, lblTol, 2, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlNR, txtTol, 3, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "Tolerancia.");
		//4, 5 - Iteraciones
		Add.componente(pnlNR, lblIt, 4, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlNR, spnrIt, 5, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "Máximo número de iteraciones");
		
		//Añadir al panel principal
		Add.componente(this, pnlNR, 0, 1, 3, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
	}
	private void initPanelS(){
		//0 - Puntos Iniciales
		lblX0A.setText("<html>X<sub>0</sub></hmtl>");
		Add.componente(pnlS, lblX0A, 0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlS, txtX0A, 1, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "Punto inicial.");
		lblX1B.setText("<html>X<sub>1</sub></hmtl>");
		Add.componente(pnlS, lblX1B, 2, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlS, txtX1B, 3, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "Punto inicial.");
		//1 - Tolerancia e Iteraciones
		Add.componente(pnlS, lblTol, 0, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlS, txtTol, 1, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "Tolerancia.");
		
		Add.componente(pnlS, lblIt, 2, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlPF, spnrIt, 3, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "Máximo número de iteraciones");
		
		//Añadir al panel principal
		Add.componente(this, pnlS, 0, 1, 3, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
	}
	private void initPanelRF(){
		//0 - Intervalo AB
		lblX0A.setText("A");
		Add.componente(pnlRF, lblX0A, 0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlRF, txtX0A, 1, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "Limite izquierdo.");
		lblX1B.setText("B");
		Add.componente(pnlRF, lblX1B, 2, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlRF, txtX1B, 3, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "Limite derecho.");
		//1 - Tolerancia e Iteraciones
		Add.componente(pnlRF, lblTol, 0, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlRF, txtTol, 1, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "Tolerancia.");
		
		Add.componente(pnlRF, lblIt, 2, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlRF, spnrIt, 3, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "Máximo número de iteraciones");
		
		//Añadir al panel principal
		Add.componente(this, pnlRF, 0, 1, 3, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
	}
	
	/**
	 * @param funcion la función
	 */
	public void setFuncion(Funcion funcion) {
		this.funcion = funcion;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED){
			System.out.println("metodoanterior: "+currMethod+"."+strMetodos[currMethod]);
			//Remover panel actual
			switch (currMethod) {
			case 0:
				this.remove(pnlPF);
				break;
			case 1:
				this.remove(pnlB);
				break;
			case 2:
				this.remove(pnlNR);
				break;
			case 3:
				this.remove(pnlS);
				break;
			case 4:
				this.remove(pnlRF);
				break;
			default:
				break;
			}
			
			String strm = e.getItem().toString();
			int m = 0;
			for (int i = 0; i < strMetodos.length; i++) {
				if(strm.equals(strMetodos[i])){
					m = i;
					break;
				}
			}

			System.out.println("metodoseleccionado: "+m+"."+strm);
			
			currMethod = m;
			
			switch (m) {
			case 0:
				initPanelPF();
				break;
			case 1:
				initPanelB();
				break;
			case 2:
				initPanelNR();
				break;
			case 3:
				initPanelS();
				break;
			case 4:
				initPanelRF();
				break;
			default:
				break;
			}
		}
		this.revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String sTol = txtTol.getText();
		BigDecimal tol = new BigDecimal(sTol);
		int maxIt = Integer.parseInt(spnrIt.getValue().toString());//(int)spnrIt.getValue();
		String sX0A = txtX0A.getText();
		String sX1B = txtX1B.getText();
		BigDecimal x0A = new BigDecimal(sX0A);
		BigDecimal x1B = new BigDecimal(sX1B);
		Interval ab = new Interval(x0A, x1B);
		
		String xResult = "";
		
		switch (currMethod) {
		case 0:
			try {
				xResult += funcion.metodoPuntoFijo(tol, maxIt, x0A);
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
			break;
		case 1:
			try {
				xResult += funcion.metodoBiseccion(tol, maxIt, ab);
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
			break;
		case 2:
			try {
				xResult += funcion.metodoNewtonRaphson(tol, maxIt, x0A);
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
			break;
		case 3:
			try {
				xResult += funcion.metodoSecante(tol, maxIt, x0A, x1B);
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
			break;
		case 4:
			try {
				xResult += funcion.metodoRegulaFalsi(tol, maxIt, ab);
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
			break;
		default:
			O.pln(e.getSource().toString());
			break;
		}
		
		txtX.setText(xResult);
		
	}
	
}
