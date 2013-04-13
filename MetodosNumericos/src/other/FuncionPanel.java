/**
 * 
 */
package other;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import resources.Add;
import resources.CustomException;

import funciones.Funcion;

/**
 * @author Jedabero
 *
 */
public class FuncionPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1282401764833016997L;
	private int nTerms;
	private ArrayList<JLabel> lblListCoefs;
	private ArrayList<JTextField> txtListCoefs;
	private Funcion fnc;
	private BigDecimal[] coefs;
	
	/**
	 * @param grade
	 * @throws CustomException 
	 */
	public FuncionPanel(int grade) throws CustomException {
		super(new GridBagLayout());
		nTerms = grade+1;
		lblListCoefs = new ArrayList<JLabel>(nTerms);
		txtListCoefs = new ArrayList<JTextField>(nTerms);
		coefs = new BigDecimal[nTerms];
		
		for (int i = 0; i < nTerms; i++) {
			coefs[i] = BigDecimal.ONE;
			JLabel templbl = new JLabel("<html>A<sub>"+i+"</sub>= </html>", JLabel.RIGHT);
			lblListCoefs.add(templbl);
			JTextField temptxt = new JTextField(coefs[i].toString());
			txtListCoefs.add(temptxt);
		}
		
		fnc = Funcion.polinomio(grade, coefs);
		
		addComponents();
		
	}
	
	/**
	 * 
	 */
	public void addComponents(){
		
		for (int i = 0; i < nTerms; i++) {
			Add.componente(this, lblListCoefs.get(i), 0, i, 1, 1, 1.0, 1.0,
					GridBagConstraints.BOTH, "");
		}
		
		for (int i = 0; i < nTerms; i++) {
			Add.componente(this, txtListCoefs.get(i), 1, i, 1, 1, 1.0, 1.0,
					GridBagConstraints.BOTH, "");
		}
		
	}
	
	
	/**
	 * @return la función
	 */
	public Funcion getFnc() {
		
		for (int i = 0; i < nTerms; i++) {
			JTextField txtTemp = txtListCoefs.get(i);
			String text = txtTemp.getText();
			BigDecimal tempBD;
			try {
				tempBD = new BigDecimal(text);
			} catch (Exception e) {
				tempBD = BigDecimal.ONE;
				txtTemp.setText(tempBD.toString());
			}
			coefs[i] = tempBD;
		}
		
		try {
			fnc = Funcion.polinomio(nTerms-1, coefs);
		} catch (CustomException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
		return fnc;
	}
	
}
