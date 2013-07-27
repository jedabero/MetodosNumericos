/**
 * 
 */
package ui.aproxpol;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import resources.Add;
import resources.math.funciones.Funcion;

/**
 * @author Jedabero
 *
 */
public class AproxFunUI extends JPanel implements ActionListener, ChangeListener {
	
	/** */
	private static final long serialVersionUID = 714904529268363806L;
	
	private static final String strMetodos[] = {
		"Polinomio Simple", "Polinomio de Lagrange", "Polinomio de Newton"};
	private JSpinner spnrNumPuntos;
	private JScrollPane scpTable;
	private JTable tblPuntos;
	DefaultTableModel dm;
	private String[] headers = {"x", "f(x)"};
	private int numPuntos = 2;
	private JPanel pnlMetodos;
	private JButton btnObtenPol[];
	private JLabel lblPolinomio;
	
	/**
	 * 
	 */
	public AproxFunUI() {
		super(new GridBagLayout());
		
		initComponents();
		
		setName("AproxFunUI");
	}

	private void initComponents() {
		//Table settings
		JLabel lblNumPuntos = new JLabel("Número de puntos:");
		
		SpinnerNumberModel snmNumPun = new SpinnerNumberModel(2, 2, 25, 1);
		spnrNumPuntos = new JSpinner(snmNumPun);
		spnrNumPuntos.addChangeListener(this);
		
		Object p0[][] = new Object[2][2];
		dm = new DefaultTableModel(p0, headers);
		tblPuntos = new JTable(dm);
		scpTable = new JScrollPane();
		scpTable.setViewportView(tblPuntos);
		scpTable.setBorder(javax.swing.BorderFactory.createTitledBorder("Tabla Tabulada"));
		
		pnlMetodos = new JPanel(new GridBagLayout());
		pnlMetodos.setBorder(javax.swing.BorderFactory.createTitledBorder("M�todos de aproximaci�n"));
		
		btnObtenPol = new JButton[strMetodos.length];
		for(int i = 0; i<btnObtenPol.length; i++){
			btnObtenPol[i] = new JButton(strMetodos[i]);
			btnObtenPol[i].addActionListener(this);
		}
		
		lblPolinomio = new JLabel("Polinomio aproximado: ", JLabel.CENTER);
		
		//0 - Numero de puntos
		Add.componente(this, lblNumPuntos, 0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.NONE, "");
		Add.componente(this, spnrNumPuntos, 1, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "N�mero de puntos");
		
		//1 - Tabla
		Add.componente(this, scpTable, 0, 1, 3, 4, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		
		//5 - 0 - Bot�n obtenPol
		for (int i = 0; i < btnObtenPol.length; i++) {
			Add.componente(pnlMetodos, btnObtenPol[i], i, 0, 1, 1, 1.0, 1.0,
					GridBagConstraints.BOTH, "");
		}
		
		//5 - 1 - Label del Polinomio
		Add.componente(pnlMetodos, lblPolinomio, 0, 1, 3, 3, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		
		//5 - Panel de m�todos
		Add.componente(this, pnlMetodos, 0, 5, 3, 4, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		BigDecimal x[] = new BigDecimal[numPuntos];
		BigDecimal fx[] = new BigDecimal[numPuntos];
		for (int i = 0; i < numPuntos; i++) {
			x[i] = new BigDecimal(dm.getValueAt(i, 0).toString());
			fx[i] = new BigDecimal(dm.getValueAt(i, 1).toString());
		}
		
		Funcion f = null;
		
		JButton btn = (JButton) e.getSource();
		try {
			if(btn.equals(btnObtenPol[0])){
				f = Funcion.aproximacionPolinomialSimple(x, fx);
			}else if(btn.equals(btnObtenPol[1])){
				f = Funcion.aproximacionPolinomialLangrange(x, fx);
			}else if(btn.equals(btnObtenPol[2])){
				f = Funcion.aproximacionPolinomialNewton(x, fx);
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
		
		lblPolinomio.setText("<html>Polinomio aproximado: "+f.getSpecific()+"</html>");
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {//TODO fix this to 1.7!
		JSpinner spnr = (JSpinner) e.getSource();
		int np = Integer.parseInt(spnr.getValue().toString());//(int)spnr.getValue();
		boolean increase = np > numPuntos;
		if(increase){
			dm.addRow(new Object[2]);
		}else{
			dm.removeRow(np);
		}
		numPuntos = np;
		
	}
	
}
