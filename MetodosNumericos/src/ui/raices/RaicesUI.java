/**
 * 
 */
package ui.raices;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import resources.Add;
import resources.O;
import resources.math.Interval;
import funciones.Funcion;
import grafica.JGrafica;

/**
 * @author Jedabero
 *
 */
public class RaicesUI extends JPanel  implements ActionListener, ChangeListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5011837883282426768L;
	private JFrame frmGrafica;
	
	private FuncionPanel fpnlFuncion;
	private JLabel lblEq;
	private MetodosRaicesPanel mpnlMetodos; 
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
		//funcion = fpnlFuncion.getFnc(); TODO funcion init
		lblEq = new JLabel("", JLabel.CENTER);
		lblEq.setBorder(BorderFactory.createEtchedBorder());
		JButton btnVerGrafica = new JButton("Ver gráfica");
		
		JSeparator sprtr2 = new JSeparator(JSeparator.HORIZONTAL);
		
		//Métodos
		mpnlMetodos = new MetodosRaicesPanel();
		
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
		btnCreaPol.addActionListener(this);
		btnVerGrafica.addActionListener(this);
		spnrGradoPol.addChangeListener(this);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSpinner spnr = (JSpinner) e.getSource();
		remove(fpnlFuncion);
		fpnlFuncion = new FuncionPanel(Integer.parseInt(spnr.getValue().toString()));//(int)spnr.getValue();
		Add.componente(this, fpnlFuncion, 			0, 1, 5, 2, 1.0, 1.0,
				GridBagConstraints.BOTH, "Edita los coeficientes de la función");
		revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		switch (btn.getText().charAt(0)) {
		case 'C'://TODO "Crear Polinomio":
			funcion = fpnlFuncion.getFnc();
			lblEq.setText(funcion.getSpecific());
			mpnlMetodos.setFuncion(funcion);
			JOptionPane.showMessageDialog(null, "Función creada");//TODO función creada
			break;
		case 'V'://TODO "Ver gráfica":
			if(funcion!=null){
				ArrayList<Color> alc = new ArrayList<Color>(1);
				alc.add(Color.BLUE);
				ArrayList<Funcion> alf = new ArrayList<Funcion>(1);
				alf.add(funcion);
				if(frmGrafica!=null){
					frmGrafica.dispose();
				}
				grafic(alf, alc);
			}else{
				JOptionPane.showMessageDialog(null, "Crea la función primero.");
			}
			break;
		default:
			O.pln(btn.getName());
			break;
		}
		
	}
	
	private void grafic(ArrayList<Funcion> alf, ArrayList<Color> alc){
		frmGrafica = new JFrame(""+funcion);
		frmGrafica.setSize(800, 400);
		JGrafica jg = new JGrafica(alf, alc, frmGrafica.getSize(),
				new Interval(BigDecimal.ONE.negate(), BigDecimal.ONE),
				new Interval(BigDecimal.ONE.negate(), BigDecimal.ONE));
		jg.setBackground(Color.WHITE);
		frmGrafica.add(jg);
		frmGrafica.setVisible(true);
		frmGrafica.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
