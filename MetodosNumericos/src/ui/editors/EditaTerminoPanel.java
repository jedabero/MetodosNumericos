/**
 * 
 */
package ui.editors;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import resources.Add;
import resources.math.Constantes.FuncionTrig;
import resources.math.Constantes.Tipo;
import resources.math.funciones.Termino;

/**
 * @author jedabero
 *
 */
public class EditaTerminoPanel extends JPanel implements ItemListener, ChangeListener {

	private Tipo tipo;
	private JComboBox<Tipo> listaTipos;
	private FuncionTrig tipoTrig;
	private JComboBox<FuncionTrig> listaTipoTrig;
	
	private int index;
	
	private int grado;
	private SpinnerNumberModel snmGrado;
	private JSpinner spnrGrado;
	
	private CoeficientePanel cpA;
	private BigDecimal coefA;
	private CoeficientePanel cpB;
	private BigDecimal coefB;
	
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
		super(new GridBagLayout());
		this.index = index;
		this.tipo = tipo;
		init();
	}

	private void init() {
		cpA = new CoeficientePanel("<html>A"+(index>=0?("<sub>"+index+"</sub>"):"")+"= </html>");
		initLista();
		switch (tipo) {
		case POLINOMICA:
			snmGrado = new SpinnerNumberModel(grado, 1, 25, 1);
			spnrGrado = new JSpinner(snmGrado);
			spnrGrado.addChangeListener(this);
			Add.componente(this, new JLabel("Grado ",JLabel.CENTER),
					0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.NONE, "");
			
			break;

		case TRIGONOMETRICA:
			cpB = new CoeficientePanel("<html>B"+(index>=0?("<sub>"+index+"</sub>"):"")+"= </html>");
			initListaTrig();
			break;
		
		default:
			cpB = new CoeficientePanel("<html>B"+(index>=0?("<sub>"+index+"</sub>"):"")+"= </html>");
			break;
		}
	}
	
	private void initLista() {
		listaTipos.addItem(Tipo.CONSTANTE);
		listaTipos.addItem(Tipo.POLINOMICA);
		listaTipos.addItem(Tipo.TRIGONOMETRICA);
		listaTipos.addItem(Tipo.EXPONENCIAL);
		listaTipos.addItem(Tipo.LOGARITMICA);
		listaTipos.setSelectedItem(tipo);
	}
	
	private void initListaTrig() {
		listaTipoTrig.addItem(FuncionTrig.SIN);
		listaTipoTrig.addItem(FuncionTrig.COS);
		listaTipoTrig.addItem(FuncionTrig.TAN);
		listaTipoTrig.addItem(FuncionTrig.SEC);
		listaTipoTrig.addItem(FuncionTrig.CSC);
		listaTipoTrig.addItem(FuncionTrig.COT);
		listaTipoTrig.setSelectedItem(tipoTrig);
	}

	
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
