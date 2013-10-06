/**
 * 
 */
package ui.editors;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

import resources.Add;
import resources.math.funciones.Funcion;

/**
 * @author jedabero
 * 
 */
public class EditaFuncionPanel extends JPanel implements EditaFuncion {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7479522047337871362L;

	protected int numTerminos;

	protected SpinnerNumberModel snmNumTerminos;
	protected JSpinner spnrNumTerminos;

	public JPanel pnlCoefs = new JPanel(new GridBagLayout());

	protected ArrayList<CoeficientePanel> listCoefsA;

	protected Funcion fnc;

	/**
	 * 
	 */
	public EditaFuncionPanel() {
		this("Edición de la Función");
	}

	/**
	 * @param title
	 */
	public EditaFuncionPanel(String title) {
		super(new GridBagLayout());

		init();
		addComponents();

		setBorder(BorderFactory.createTitledBorder(title));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ui.EditaFuncion#init()
	 */
	@Override
	public void init() {
		numTerminos = 4;

		snmNumTerminos = new SpinnerNumberModel(numTerminos, 1, 25, 1);
		spnrNumTerminos = new JSpinner(snmNumTerminos);
		spnrNumTerminos.addChangeListener(this);

		listCoefsA = new ArrayList<CoeficientePanel>(numTerminos);

		for (int i = 0; i < numTerminos; i++) {
			CoeficientePanel tempcp = new CoeficientePanel("<html>A<sub>" + i
					+ "</sub>= </html>");
			listCoefsA.add(tempcp);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ui.EditaFuncion#addComponents()
	 */
	@Override
	public void addComponents() {
		Add.componente(this, new JLabel("Numero de Terminos", JLabel.CENTER),
				0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.NONE, "");
		Add.componente(this, spnrNumTerminos, 1, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.NONE, "Numero de Terminos");

		layoutCoefsPanel();

		Add.componente(this, pnlCoefs, 0, 1, 2, 2, 1.0, 1.0,
				GridBagConstraints.BOTH, "Edita los coeficientes");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ui.EditaFuncion#layoutCoefsPanel()
	 */
	@Override
	public void layoutCoefsPanel() {
		pnlCoefs.removeAll();
		pnlCoefs.revalidate();
		for (int i = 0; i < listCoefsA.size(); i++) {
			int x = i % 4;
			int y1 = i / 4;

			Add.componente(pnlCoefs, listCoefsA.get(i), x, y1, 1, 1, 1.0, 1.0,
					GridBagConstraints.BOTH, "");

		}

		pnlCoefs.repaint();
		revalidate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ui.EditaFuncion#getFuncion()
	 */
	@Override
	public Funcion getFuncion() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ui.EditaFuncion#stateChanged(javax.swing.event.ChangeEvent)
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub

	}

}
