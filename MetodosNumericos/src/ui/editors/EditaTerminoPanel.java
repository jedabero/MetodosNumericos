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
import resources.math.Constantes.Tipo;
import resources.math.funciones.Termino;

/**
 * @author jedabero
 *
 */
public class EditaTerminoPanel extends JPanel implements Editors {

	private Tipo tipo;
	private JComboBox<Tipo> listaTipos;
	
	private int index;
	
	private CoeficientePanel cpA;
	private BigDecimal coefA;
	private CoeficientePanel cpB;
	private BigDecimal coefB;
	
	private JPanel termPanel = new JPanel(new GridBagLayout());
	
	private Termino termino;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7836520899017987399L;

	/**
	 * 
	 */
	public EditaTerminoPanel() {
		this(Tipo.POLINOMICA);
	}
	
	/**
	 * 
	 */
	public EditaTerminoPanel(Tipo tipo) {
		this(-1, tipo);
	}
	
	/**
	 * 
	 */
	public EditaTerminoPanel(int index, Tipo tipo) {
		this(index, tipo, BigDecimal.ZERO, BigDecimal.ZERO);
	}

	public EditaTerminoPanel(int index, Tipo tipo, BigDecimal a, BigDecimal b) {
		super(new GridBagLayout());
		this.index = index;
		this.tipo = tipo;
		this.coefA = a;
		this.coefB = b;
		init();
		addComponents();
	}
	
	@Override
	public void init() {
		initLista();
		
	}

	@Override
	public void addComponents() {
		Add.componente(this, new JLabel("Tipo:",JLabel.CENTER), 0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(this, listaTipos, 1, 0, 2, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(this, termPanel, 0, 1, 3, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
	}
	
	private void initLista() {
		listaTipos = new JComboBox<Tipo>();
		listaTipos.addItem(Tipo.CONSTANTE);
		listaTipos.addItem(Tipo.POLINOMICA);
		listaTipos.addItem(Tipo.TRIGONOMETRICA);
		listaTipos.addItem(Tipo.EXPONENCIAL);
		listaTipos.addItem(Tipo.LOGARITMICA);
		listaTipos.setSelectedItem(tipo);
		listaTipos.addItemListener(this);
	}
	
	@Override
	public Termino getTermino() {
		// TODO Auto-generated method stub
		return termino;
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange()== ItemEvent.SELECTED) {
			tipo = (Tipo) e.getItem();
			System.out.println(tipo);
			
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String args[]){
		javax.swing.JFrame jf = new javax.swing.JFrame("Tests");
		jf.setSize(200,100);
		jf.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		
		EditaTerminoPanel etp = new EditaTerminoPanel();
		jf.add(etp);
		jf.setVisible(true);
	}
	
}
