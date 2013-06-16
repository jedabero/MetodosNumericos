package ui.numint;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import resources.Add;
import resources.O;
import resources.math.BigInterval;
import ui.main.app.MetodosUI;
import resources.math.funciones.Funcion;

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
	
	private static final String strMetodos[] = new String[]{
		"Trapezoidal Simple", "Trapezoidal Compuesto", "Simpson Simple 1/3",
		"Simpson Simple 3/8", "Simpson Compuesto"};
	private int currMethod = 0;
	
	private JButton btnIntegrar;
	private JComboBox<String> listaMetodos;
	private JLabel lblA;
	private JLabel lblB;
	private JLabel lblN;
	private JLabel lblRes;
	private JTextField txtA;
	private JTextField txtB;
	private JTextField txtRes;	
	private JSpinner spnrN;
	
	private Funcion funcion;
	private BigInterval ab;
	
	/**
	 * 
	 */
	public MetodosIntegracionPanel(){
		super(new GridBagLayout());
		
		listaMetodos = new JComboBox<String>(strMetodos);
		listaMetodos.addItemListener(this);
		
		lblA = new JLabel("Limite inferior", JLabel.CENTER);
		lblB = new JLabel("Limite superior", JLabel.CENTER);
		txtA = new JTextField();
		txtB = new JTextField();
		
		lblN = new JLabel("sub-intervalos", JLabel.CENTER);
		SpinnerNumberModel snmN = new SpinnerNumberModel(1, 1, 1000, 1);
		spnrN = new JSpinner(snmN);
		
		btnIntegrar = new JButton("Integrar");
		btnIntegrar.addActionListener(this);
		
		lblRes = new JLabel("Resultado =    ", JLabel.RIGHT);
		txtRes = new JTextField();
		txtRes.setEditable(false);
		
		Add.componente(this, listaMetodos, 0, 0, 8, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "Selecciona un m�todo.");
		Add.componente(this, lblA, 0, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.NONE, "");
		Add.componente(this, txtA, 1, 1, 2, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "Limite inferior.");
		Add.componente(this, lblB, 3, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.NONE, "");
		Add.componente(this, txtB, 4, 1, 2, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "Limite superior.");
		Add.componente(this, lblN, 6, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.NONE, "");
		Add.componente(this, spnrN, 7, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "N�mero de subintervalos.");
		Add.componente(this, lblRes, 0, 2, 2, 1, 1.0, 1.0,
				GridBagConstraints.NONE, "");
		Add.componente(this, txtRes, 2, 2, 4, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "Resultado de la integral.");
		Add.componente(this, btnIntegrar, 6, 2, 2, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "Integra");
		
		setBorder(javax.swing.BorderFactory.createTitledBorder("M�todos de Integraci�n"));
	}
	
	/**
	 * @param funcion la funci�n
	 */
	public void setFuncion(Funcion funcion) {
		this.funcion = funcion;
	}
	
	/**
	 * @return the ab
	 */
	public BigInterval getAb() {
		String sA = txtA.getText();
		String sB = txtB.getText();
		if(sA.isEmpty()||sB.isEmpty()){
			ab = null;
		}else{
			BigDecimal A = new BigDecimal(sA);
			BigDecimal B = new BigDecimal(sB);
			ab = new BigInterval(A, B);
		}
		return ab;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		for (int i = 0; i < strMetodos.length; i++) {
			if(e.getItem().toString().equals(strMetodos[i])){
				currMethod = i;
				break;
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String sN = spnrN.getValue().toString();
		int n = Integer.parseInt(sN);//(int)spnrN.getValue();
		
		String sA = txtA.getText();
		String sB = txtB.getText();
		BigDecimal A = new BigDecimal(sA);
		BigDecimal B = new BigDecimal(sB);
		ab = new BigInterval(A, B);
		
		BigDecimal result;
		switch (currMethod) {
		case 0:
			result = funcion.integracionTrapecioSimple(ab);
			break;
		case 1:
			result = funcion.integracionTrapecioCompuesto(ab, n);
			break;
		case 2:
			result = funcion.integracionSimpsonSimple1_3(ab);
			break;
		case 3:
			result = funcion.integracionSimpsonSimple3_8(ab);
			break;
		case 4:
			result = funcion.integracionSimpsonCompuesto(ab, n);
			break;
		default:
			O.pln(e.getSource().toString());
			result=null;
			break;
		}
		
		int scale = MetodosUI.getResultScale();
		result = result.setScale(scale, RoundingMode.HALF_UP);
		txtRes.setText(result.stripTrailingZeros().toString());
		
	}

}
