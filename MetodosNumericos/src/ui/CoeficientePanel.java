package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import resources.Add;

/**
 * @author Jedabero
 */
public class CoeficientePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2691382835933422081L;

	private JLabel label;
	private JTextField texto;
	
	public CoeficientePanel(String text) {
		super(new GridBagLayout());
		label = new JLabel(text, JLabel.RIGHT);
		Add.componente(this, label, 0, 0, 1, 1, 0.25, 1.0,
				GridBagConstraints.BOTH, "");
		
		texto = new JTextField();
		Add.componente(this, texto, 0, 1, 3, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
	}

}
