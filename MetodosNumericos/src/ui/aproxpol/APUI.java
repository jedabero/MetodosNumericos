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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import metodosnumericos.Matriz;
import metodosnumericos.SistemaEcuacionesLineales;
import other.CustomWindowAdapter;
import resources.Add;
import resources.CustomException;
import funciones.Funcion;

/**
 * @author Jedabero
 *
 */
public class APUI implements ActionListener, ChangeListener{
	
	private JPanel thePanel;
	private JSpinner spnrNumPuntos;
	private JScrollPane scpTable;
	private JTable tblPuntos;
	DefaultTableModel dm;
	private String[] headers = {"x", "f(x)"};
	private int numPuntos = 2;
	private JButton btnObtenPol;
	private SistemaEcuacionesLineales sel;
	private JLabel lblPolinomio;
	
	/**
	 * 
	 */
	public APUI() {
		CustomWindowAdapter wa = new CustomWindowAdapter();
		JFrame theWindow = new JFrame("Ajuste de puntos a un Polinomio");
		theWindow.setSize(500, 500);
		theWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		thePanel = new JPanel(new GridBagLayout());
		
		initComponents();
		
		theWindow.addWindowListener(wa);
		theWindow.add(thePanel);
		theWindow.setLocationRelativeTo(theWindow.getRootPane());
		theWindow.setVisible(true);
		theWindow.setName("APUI");
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
		
		btnObtenPol = new JButton("Obten Polinomio");
		btnObtenPol.addActionListener(this);
		
		lblPolinomio = new JLabel("");
		
		//0 - Numero de puntos
		Add.componente(thePanel, lblNumPuntos, 0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.NONE, "");
		Add.componente(thePanel, spnrNumPuntos, 1, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "Número de puntos");
		
		//1 - Tabla
		Add.componente(thePanel, scpTable, 0, 1, 2, 5, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		
		//6 - Botón obtenPol
		Add.componente(thePanel, btnObtenPol, 0, 6, 2, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		
		//7 - Label del Polinomio
		Add.componente(thePanel, lblPolinomio, 0, 7, 2, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		BigDecimal matriz[][] = new BigDecimal[numPuntos][numPuntos+1];
		for (int i = 0; i < matriz.length; i++) {
			BigDecimal xi = new BigDecimal(dm.getValueAt(i, 0).toString());
			for (int j = 0; j < matriz.length; j++) {
				matriz[i][j] = xi.pow(j);
			}
			matriz[i][numPuntos] = new BigDecimal(dm.getValueAt(i, 1).toString());
		}
		sel = new SistemaEcuacionesLineales(matriz);
		
		Matriz coef;
		try {
			coef = sel.metodoCramer();
			coef.imprimirMatriz("Res");
		} catch (Exception e1) {
			e1.printStackTrace();
			coef = new Matriz(matriz);
		}
		
		BigDecimal coefs[] = new BigDecimal[numPuntos];
		for (int i = 0; i < coefs.length; i++) {
			coefs[i] = coef.getMatriz()[i][0].stripTrailingZeros();
			System.out.println(coefs[i]);
		}
		
		Funcion f = null;
		try {
			f = Funcion.polinomio(numPuntos-1, coefs);
		} catch (CustomException e1) {
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
