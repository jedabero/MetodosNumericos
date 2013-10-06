/**
 * 
 */
package ui.editors;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

import resources.Add;
import resources.math.funciones.Termino;

/**
 * @author jedabero
 * 
 */
public class MonomioPanel extends JPanel implements Editors {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7166446275028262369L;

	private int index;

	private int grado;
	private SpinnerNumberModel snmGrado;
	private JSpinner spnrGrado;

	private CoeficientePanel cpA;
	private BigDecimal coefA;

	private Termino termino;

	/**
	 * Crea un panel para editar un termino tipo polinomico. Por deafult el
	 * grado es 1 y el coeficiente es 0.
	 */
	public MonomioPanel() {
		this(1);
	}

	/**
	 * Crea un panel para editar un termino tipo polinomico de grado g. Por
	 * deafult el coeficiente es 0.
	 * 
	 * @param g
	 *            grado del termino
	 */
	public MonomioPanel(int g) {
		this(g, BigDecimal.ZERO);
	}

	/**
	 * Crea un panel para editar un termino tipo polinomico de grado g y
	 * coeficiente a
	 * 
	 * @param g
	 *            grado del termino
	 * @param a
	 *            coeficiente
	 */
	public MonomioPanel(int g, BigDecimal a) {
		this(g, a, -1);
	}

	/**
	 * 
	 * @param g
	 * @param a
	 * @param i
	 */
	public MonomioPanel(int g, BigDecimal a, int i) {
		super(new GridBagLayout());
		this.grado = g < 1 ? 1 : g;
		this.coefA = a;
		this.index = i;

		init();
		addComponents();
	}

	@Override
	public void init() {
		snmGrado = new SpinnerNumberModel(grado, 1, 25, 1);
		spnrGrado = new JSpinner(snmGrado);
		spnrGrado.addChangeListener(this);

		cpA = new CoeficientePanel("<html>A"
				+ (index >= 0 ? ("<sub>" + index + "</sub>") : "")
				+ "= </html>");
		cpA.setTexto(coefA.toString());
	}

	@Override
	public void addComponents() {
		Add.componente(this, new JLabel("Grado:", JLabel.CENTER), 0, 0, 1, 1,
				0.0, 1.0, GridBagConstraints.NONE, "");
		Add.componente(this, spnrGrado, 1, 0, 2, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(this, cpA, 0, 1, 3, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "Coeficiente A");
	}

	@Override
	public Termino getTermino() {
		String textA = cpA.getTexto();
		try {
			coefA = new BigDecimal(textA);
		} catch (Exception e) {
			String msgstr = "<html>Coeficiente A"
					+ (index >= 0 ? ("<sub>" + index + "</sub>") : "") + ": "
					+ (textA.isEmpty() ? "vacio" : textA) + "</html>";
			JOptionPane.showMessageDialog(this, msgstr, "Error",
					JOptionPane.WARNING_MESSAGE);
			coefA = null;
		}

		termino = coefA == null ? null : Termino.monomio(grado, coefA, null);

		return termino;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		grado = Integer.parseInt(spnrGrado.getValue().toString());// (int)spnr.getValue();
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
	}

}
