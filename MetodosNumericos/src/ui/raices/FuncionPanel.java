/**
 * 
 */
package ui.raices;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import resources.Add;
import resources.CustomException;
import resources.O;

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
			int xL = (2*i)%4 + ((i%2==0)? 0 : 2);
			int xT = (2*i +1)%4 + ((i%2==0)? 0 : 2);
			int y = (i/2);
			O.pln(xL+","+y);
			O.pln(xT+","+y);
			Add.componente(this, lblListCoefs.get(i),
					xL, y, 1, 1, 0.25, 1.0,
					GridBagConstraints.BOTH, "");
			Add.componente(this, txtListCoefs.get(i),
					xT, y, 3, 1, 1.0, 1.0,
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
			e.printStackTrace();
			e.printStackTrace();
		}
		
		return fnc;
	}
	
}
