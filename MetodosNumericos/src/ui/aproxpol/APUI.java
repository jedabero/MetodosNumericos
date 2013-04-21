/**
 * 
 */
package ui.aproxpol;

import grafica.JGrafica;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

import other.CustomWindowAdapter;
import resources.Add;
import resources.CustomException;

/**
 * @author Jedabero
 *
 */
public class APUI {
	
	private JPanel thePanel;
	private JSpinner spnrNumPuntos;
	private JScrollPane scpTable;
	private JTable tblPuntos;
	private String[] headers = {"x", "f(x)"};
	//private JGrafica grafica;
	
	/**
	 * @throws CustomException
	 */
	public APUI() throws CustomException {
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
		theWindow.setName("APUI");
	}

	private void initComponents() {
		//Table settings
		JLabel lblNumPuntos = new JLabel("Número de puntos:");
		lblNumPuntos.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		SpinnerNumberModel snmNumPun = new SpinnerNumberModel(2, 2, 25, 1);
		spnrNumPuntos = new JSpinner(snmNumPun);
		spnrNumPuntos.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		BigDecimal p0[][] = {{BigDecimal.ZERO, BigDecimal.ONE},{BigDecimal.ONE, BigDecimal.TEN}};
		DefaultTableModel dm = new DefaultTableModel(p0, headers);
		tblPuntos = new JTable(dm);
		scpTable = new JScrollPane();
		scpTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		scpTable.setViewportView(tblPuntos);
		
		/*/Grafica
		grafica = new JGrafica(funcionList, colorList, dim, xInterval, yInterval);*/
		JPanel pnlTemp = new JPanel(new GridBagLayout());
		pnlTemp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JLabel lblTemp = new JLabel("ECUACIÓN");
		lblTemp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		
		//0 - Numero de puntos
		Add.componente(thePanel, lblNumPuntos, 0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.NONE, "");
		Add.componente(thePanel, spnrNumPuntos, 1, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "Número de puntos");
		
		Add.componente(thePanel, pnlTemp, 2, 0, 3, 5, 1.0, 1.0,
				GridBagConstraints.BOTH, "EMPTYPANEL");
		Add.componente(thePanel, lblTemp, 2, 5, 3, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "EMPTYLABEL");
		
		//1 - Tabla
		Add.componente(thePanel, scpTable, 0, 1, 2, 5, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		
		
		//TODO DELETE THIS v
		JLabel lblTemp1 = new JLabel("1");
		lblTemp1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		Add.componente(thePanel, lblTemp1, 0, 6, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		JLabel lblTemp2 = new JLabel("2");
		lblTemp2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		Add.componente(thePanel, lblTemp2, 1, 6, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		JLabel lblTemp3 = new JLabel("3");
		lblTemp3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		Add.componente(thePanel, lblTemp3, 2, 6, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		JLabel lblTemp4 = new JLabel("4");
		lblTemp4.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		Add.componente(thePanel, lblTemp4, 3, 6, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		JLabel lblTemp5 = new JLabel("5");
		lblTemp5.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		Add.componente(thePanel, lblTemp5, 4, 6, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
	}
	
}
