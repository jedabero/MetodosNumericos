/**
 * 
 */
package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import resources.Add;
import resources.CustomException;
import resources.O;
import resources.math.funciones.Funcion;

/**
 * @author Jedabero
 *
 */
public class EditaPolinomioPanel extends JPanel implements ChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1282401764833016997L;
	
	private int grado;
	
	private SpinnerNumberModel snmGradoPol;
	private JSpinner spnrGradoPol;
	
	private JPanel pnlCoefs;
	private ArrayList<JLabel> lblListCoefs;
	private ArrayList<JTextField> txtListCoefs;
	
	private Funcion fnc;
	private BigDecimal[] coefs;
	
	/**
	 */
	public EditaPolinomioPanel() {
		super(new GridBagLayout());
		
		initComponents();
		addComponents();
		
		setBorder(javax.swing.BorderFactory.createTitledBorder("Edici�n del "
				+"polinomio"));
	}
	
	/**
	 * @param title
	 */
	public EditaPolinomioPanel(String title) {
		super(new GridBagLayout());
		
		initComponents();
		addComponents();
		
		setBorder(javax.swing.BorderFactory.createTitledBorder(title));
	}
	
	private void initComponents() {
		grado = 3;
		
		snmGradoPol = new SpinnerNumberModel(grado, 1, 25, 1);
		spnrGradoPol = new JSpinner(snmGradoPol);
		spnrGradoPol.addChangeListener(this);
		
		pnlCoefs = new JPanel(new GridBagLayout());
		lblListCoefs = new ArrayList<JLabel>(grado+1);
		txtListCoefs  = new ArrayList<JTextField>(grado);
		
		for (int i = 0; i <= grado; i++) {
			JLabel templbl = new JLabel("<html>A<sub>"+i+"</sub>= </html>", JLabel.RIGHT);
			lblListCoefs.add(templbl);
			JTextField temptxt = new JTextField();
			txtListCoefs.add(temptxt);
		}
		
		coefs = new BigDecimal[grado+1];
	}

	/**
	 * 
	 */
	public void addComponents(){
		Add.componente(this, new JLabel("Grado del polinomio", JLabel.CENTER),
				0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.NONE, "");
		Add.componente(this, spnrGradoPol, 1, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.NONE, "Grado del Polinomio");
		
		layoutCoefsPanel();
		
		Add.componente(this, pnlCoefs, 0, 1, 2, 2, 1.0, 1.0,
				GridBagConstraints.BOTH, "Edita los coeficientes");
		
	}
	
	private void layoutCoefsPanel(){
		pnlCoefs.removeAll();
		pnlCoefs.revalidate();
		for (int i = 0; i < lblListCoefs.size(); i++) {
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

		pnlCoefs.repaint();
		revalidate();
	}
	
	/**
	 * @return la funci�n
	 */
	public Funcion getPol() {
		coefs = new BigDecimal[grado+1];
		JTextField txtTemp;
		for (int i = 0; i <= grado; i++) {
			txtTemp = txtListCoefs.get(i);
			String text = txtTemp.getText();
			BigDecimal tempBD = null;
			try {
				tempBD = new BigDecimal(text);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(pnlCoefs,
						"<html>Coeficiente A<sub>"+i+"</sub>: "+(text.isEmpty()?"vac�o":text)+"</html>",
						"Error", JOptionPane.WARNING_MESSAGE);
			}
			coefs[i] = tempBD;
		}
		
		try {
			O.pln(java.util.Arrays.toString(coefs));
			fnc = Funcion.polinomio(grado, coefs);
		} catch (CustomException e) {}
		
		return fnc;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		int g = Integer.parseInt(spnrGradoPol.getValue().toString());//(int)spnr.getValue();
		
		if(grado<g) {
			JLabel templbl = new JLabel("<html>A<sub>"+g+"</sub>= </html>", JLabel.RIGHT);
			lblListCoefs.add(templbl);
			JTextField temptxt = new JTextField();
			txtListCoefs.add(temptxt);
		} else if(grado>g) {
			lblListCoefs.remove(lblListCoefs.size()-1);
			txtListCoefs.remove(txtListCoefs.size()-1);
		}
		layoutCoefsPanel();
		grado = g;
	}
	
}
