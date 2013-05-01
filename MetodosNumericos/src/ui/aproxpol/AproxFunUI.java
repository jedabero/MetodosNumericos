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
import funciones.Funcion;

/**
 * @author Jedabero
 *
 */
public class AproxFunUI extends JPanel implements ActionListener, ChangeListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 714904529268363806L;
	
	private static final String strMetodos[] = {
		"Polinomio Simple", "Polinomio de Lagrange"};
	private JSpinner spnrNumPuntos;
	private JScrollPane scpTable;
	private JTable tblPuntos;
	DefaultTableModel dm;
	private String[] headers = {"x", "f(x)"};
	private int numPuntos = 2;
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
		
		BigDecimal p0[][] = {
				{BigDecimal.ZERO, BigDecimal.ONE},
				{BigDecimal.ONE, BigDecimal.TEN}};
		dm = new DefaultTableModel(p0, headers);
		tblPuntos = new JTable(dm);
		scpTable = new JScrollPane();
		scpTable.setViewportView(tblPuntos);
		
		btnObtenPol = new JButton[strMetodos.length];
		for(int i = 0; i<btnObtenPol.length; i++){
			btnObtenPol[i] = new JButton(strMetodos[i]);
			btnObtenPol[i].addActionListener(this);
		}
		
		lblPolinomio = new JLabel("");
		
		//0 - Numero de puntos
		Add.componente(this, lblNumPuntos, 0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.NONE, "");
		Add.componente(this, spnrNumPuntos, 1, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "Número de puntos");
		
		//1 - Tabla
		Add.componente(this, scpTable, 0, 1, 2, 5, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		
		//6 - Botón obtenPol
		for (int i = 0; i < btnObtenPol.length; i++) {
			Add.componente(this, btnObtenPol[i], i, 6, 1, 1, 1.0, 1.0,
					GridBagConstraints.BOTH, "");
		}
		
		//7 - Label del Polinomio
		Add.componente(this, lblPolinomio, 0, 7, 2, 1, 1.0, 1.0,
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
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
		
		lblPolinomio.setText(f.getSpecific());
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {//TODO fix this to 1.7!
		JSpinner spnr = (JSpinner) e.getSource();
		int np = Integer.parseInt(spnr.getValue().toString());//(int)spnr.getValue();
		
		Object p[][] = new Object[np][2];
		for (int i = 0; i < numPuntos; i++) {
			for (int j = 0; j < 2; j++) {
				p[i][j] = new BigDecimal(dm.getValueAt(i, j).toString());
			}
		}
		p[np-1][0] = BigDecimal.ONE;
		p[np-1][1] = BigDecimal.TEN;
		numPuntos = np;
		
		dm.setDataVector(p, headers);
		tblPuntos.setModel(dm);
	}
	
}
