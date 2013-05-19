/**
 * 
 */
package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import resources.Add;
import resources.CustomException;
import funciones.Funcion;

/**
 * @author Jedabero
 *
 */
public class EditaPolinomioPanel extends JPanel implements ChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1282401764833016997L;
	
	private int nTerms;
	
	private SpinnerNumberModel snmGradoPol;
	private JSpinner spnrGradoPol;
	
	private JPanel pnlCoefs;
	private ArrayList<JLabel> lblListCoefs;
	private ArrayList<JTextField> txtListCoefs;
	
	private Funcion fnc;
	private BigDecimal[] coefs;
	
	/**
	 * @param grade
	 */
	public EditaPolinomioPanel() {
		super(new GridBagLayout());
		
		
		initComponents();
		addComponents();
		
		setBorder(javax.swing.BorderFactory.createTitledBorder("Edición de los " +
				"coeficientes del polinomio"));
	}
	
	private void initComponents() {
		nTerms = 4;
		
		snmGradoPol = new SpinnerNumberModel(nTerms-1, 1, 25, 1);
		spnrGradoPol = new JSpinner(snmGradoPol);
		spnrGradoPol.addChangeListener(this);
		
		pnlCoefs = new JPanel(new GridBagLayout());
		lblListCoefs = new ArrayList<JLabel>(nTerms);
		txtListCoefs  = new ArrayList<JTextField>(nTerms);
		
		for (int i = 0; i < nTerms; i++) {
			JLabel templbl = new JLabel("<html>A<sub>"+i+"</sub>= </html>", JLabel.RIGHT);
			lblListCoefs.add(templbl);
			JTextField temptxt = new JTextField();
			txtListCoefs.add(temptxt);
		}
		
		coefs = new BigDecimal[nTerms];
	}

	/**
	 * 
	 */
	public void addComponents(){
		Add.componente(this, new JLabel("Grado del polinomio", JLabel.CENTER),
				0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.NONE, "");
		Add.componente(this, spnrGradoPol, 1, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.NONE, "Grado del Polinomio");
		
		for (int i = 0; i < nTerms; i++) {
			int xL = (2*i)%4 + ((i%2==0)? 0 : 2);
			int xT = (2*i +1)%4 + ((i%2==0)? 0 : 2);
			int y = (i/2);
			
			Add.componente(pnlCoefs, lblListCoefs.get(i),
					xL, y, 1, 1, 0.25, 1.0,
					GridBagConstraints.BOTH, "");
			Add.componente(pnlCoefs, txtListCoefs.get(i),
					xT, y, 3, 1, 1.0, 1.0,
					GridBagConstraints.BOTH, "");
		}
		
		Add.componente(this, pnlCoefs, 0, 1, 2, 2, 1.0, 1.0,
				GridBagConstraints.BOTH, "Edita los coeficientes");
		
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
		}
		
		return fnc;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		//Integer.parseInt(spnr.getValue().toString()));(int)spnr.getValue();
	}
	
}
