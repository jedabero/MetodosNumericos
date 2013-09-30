package ui.editors;

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
		Add.componente(this, texto, 1, 0, 3, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
	}
	
	/**
	 * Regresa el texto contenido en el JTextField.
	 * @return el texto
	 */
	public String getTexto() {
		return texto.getText();
	}
	
	/**
	 * 
	 */
	public void setTexto(String text) {
		texto.setText(text);
	}
	
	/**
	 * Regresa el texto contenido en el JLabel.
	 * Este puede entontrarse en formato <code>html</code>
	 * @return el texto en el JLabel
	 */
	public String getLabelText() {
		return label.getText();
	}
	
}
