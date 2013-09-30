/**
 * 
 */
package ui.editors;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;

import resources.Add;
import resources.math.Constantes.FuncionTrig;
import resources.math.funciones.Termino;

/**
 * @author jedabero
 *
 */
public class FuncTrigPanel extends JPanel implements Editors {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7580284096836385180L;
	
	private FuncionTrig tipoTrig;
	private JComboBox<FuncionTrig> listaTipoTrig;

	private int index;
	
	private CoeficientePanel cpA;
	private BigDecimal coefA;
	private CoeficientePanel cpB;
	private BigDecimal coefB;
	
	private Termino termino;
	
	/**
	 * 
	 */
	public FuncTrigPanel() {
		this(FuncionTrig.SIN);
	}
	
	/**
	 * 
	 * @param ft
	 */
	public FuncTrigPanel(FuncionTrig ft) {
		this(ft, BigDecimal.ZERO, BigDecimal.ZERO);
	}

	/**
	 * 
	 * @param ft
	 * @param a
	 * @param b
	 */
	public FuncTrigPanel(FuncionTrig ft, BigDecimal a, BigDecimal b) {
		this(ft, a, b, -1);
	}
	
	/**
	 * 
	 * @param ft
	 * @param b
	 * @param i
	 */
	public FuncTrigPanel(FuncionTrig ft, BigDecimal a, BigDecimal b, int i) {
		super(new GridBagLayout());
		this.tipoTrig = ft;
		this.coefA = a;
		this.coefB = b;
		this.index = i;
		
		init();
		addComponents();
	}
	
	@Override
	public void init() {
		listaTipoTrig = new JComboBox<FuncionTrig>();
		listaTipoTrig.addItem(FuncionTrig.SIN);
		listaTipoTrig.addItem(FuncionTrig.COS);
		listaTipoTrig.addItem(FuncionTrig.TAN);
		listaTipoTrig.addItem(FuncionTrig.SEC);
		listaTipoTrig.addItem(FuncionTrig.CSC);
		listaTipoTrig.addItem(FuncionTrig.COT);
		listaTipoTrig.setSelectedItem(tipoTrig);
		listaTipoTrig.addItemListener(this);
		
		cpA = new CoeficientePanel("<html>A"+(index>=0?("<sub>"+index+"</sub>"):"")+"= </html>");
		cpA.setTexto(coefA.toString());
		cpB = new CoeficientePanel("<html>B"+(index>=0?("<sub>"+index+"</sub>"):"")+"= </html>");
		cpB.setTexto(coefB.toString());
		
		termino = Termino.trigonometrico(tipoTrig, coefA, coefB, null);
	}
	
	@Override
	public void addComponents() {
		Add.componente(this, new JLabel("Funcion:",JLabel.CENTER),
				0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.NONE, "");
		Add.componente(this, listaTipoTrig,
				1, 0, 3, 1, 1.0, 1.0, GridBagConstraints.BOTH, "");
		Add.componente(this, cpA,
				0, 1, 2, 1, 1.0, 1.0, GridBagConstraints.BOTH, "Coeficiente A");
		Add.componente(this, cpB,
				2, 1, 2, 1, 1.0, 1.0, GridBagConstraints.BOTH, "Coeficiente B");
	}
	
	public Termino getTermino() {
		String textA = cpA.getTexto();
		try {
			coefA = new BigDecimal(textA);
		} catch (Exception e) {
			String msgstr = "<html>Coeficiente A" + (index>=0?("<sub>"+index+"</sub>"):"")
					+ ": "+(textA.isEmpty()?"vacio":textA) + "</html>"; 
			JOptionPane.showMessageDialog(this, msgstr, "Error", JOptionPane.WARNING_MESSAGE);
			coefA = null;
		}
		
		String textB = cpB.getTexto();
		try {
			coefB = new BigDecimal(textB);
		} catch (Exception e) {
			String msgstr = "<html>Coeficiente B" + (index>=0?("<sub>"+index+"</sub>"):"")
					+ ": "+(textB.isEmpty()?"vacio":textB) + "</html>"; 
			JOptionPane.showMessageDialog(this, msgstr, "Error", JOptionPane.WARNING_MESSAGE);
			coefB = null;
		}
		
		termino = (coefB==null||coefA==null) ? null : Termino.trigonometrico(tipoTrig, coefA, coefB, null);
		
		return termino;
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange()== ItemEvent.SELECTED) {
			tipoTrig = (FuncionTrig)e.getItem();
		}
	}
	
}
