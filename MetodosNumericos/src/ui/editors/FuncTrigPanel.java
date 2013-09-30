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
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;

import resources.Add;
import resources.math.Constantes.FuncionTrig;

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
	
	private CoeficientePanel cpB;
	private BigDecimal coefB;
	
	/**
	 * 
	 */
	public FuncTrigPanel() {
		this(FuncionTrig.SIN);
	}
	
	/**
	 * 
	 */
	public FuncTrigPanel(FuncionTrig ft) {
		this(ft,BigDecimal.ZERO);
	}

	/**
	 * 
	 */
	public FuncTrigPanel(FuncionTrig ft, BigDecimal b) {
		this(ft, b, -1);
	}
	
	/**
	 * 
	 */
	public FuncTrigPanel(FuncionTrig ft, BigDecimal b, int i) {
		super(new GridBagLayout());
		this.tipoTrig = ft;
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
		
		cpB = new CoeficientePanel("<html>B"+(index>=0?("<sub>"+index+"</sub>"):"")+"= </html>");
		cpB.setTexto(coefB.toString());
	}
	
	@Override
	public void addComponents() {
		System.out.println("YES");
		Add.componente(this, new JLabel("Funcion",JLabel.CENTER),
				0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.NONE, "");
		Add.componente(this, listaTipoTrig,
				1, 0, 3, 1, 1.0, 1.0, GridBagConstraints.BOTH, "");
		Add.componente(this, cpB,
				0, 1, 4, 1, 1.0, 1.0, GridBagConstraints.BOTH, "");
		System.out.println("YES");
	}

	public static void main(String args[]) {
		javax.swing.JFrame jf = new javax.swing.JFrame("FuncTrig Test");
		jf.add(new FuncTrigPanel());
		jf.setSize(300, 100);
		jf.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
	}
	
	
	
	@Override
	public void stateChanged(ChangeEvent e) {
		
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange()== ItemEvent.SELECTED) {
			tipoTrig = (FuncionTrig)e.getItem();
		}
		
	}
	
}
