/**
 * 
 */
package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

import resources.Add;
import resources.CustomException;
import resources.O;
import resources.math.funciones.Funcion;

import ui.editors.CoeficientePanel;
import ui.editors.EditaFuncion;

/**
 * @author Jedabero
 *
 */
public class EditaPolinomioPanel extends JPanel implements EditaFuncion {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1282401764833016997L;
	
	private int grado;
	
	private SpinnerNumberModel snmGradoPol;
	private JSpinner spnrGradoPol;
	
	private ArrayList<CoeficientePanel> listCoefs;
	
	private Funcion fnc;
	private BigDecimal[] coefs;
	
	/**
	 */
	public EditaPolinomioPanel() {
		super(new GridBagLayout());
		
		init();
		addComponents();
		
		setBorder(BorderFactory.createTitledBorder("Edición del polinomio"));
	}
	
	/**
	 * @param title
	 */
	public EditaPolinomioPanel(String title) {
		super(new GridBagLayout());
		
		init();
		addComponents();
		
		setBorder(BorderFactory.createTitledBorder(title));
	}
	
	@Override
	public void init() {
		grado = 3;
		
		snmGradoPol = new SpinnerNumberModel(grado, 1, 25, 1);
		spnrGradoPol = new JSpinner(snmGradoPol);
		spnrGradoPol.addChangeListener(this);
		
		listCoefs = new ArrayList<CoeficientePanel>(grado+1);
		
		for (int i = 0; i <= grado; i++) {
			CoeficientePanel tempcp = new CoeficientePanel("<html>A<sub>"+i+"</sub>= </html>");
			listCoefs.add(tempcp);
		}
		
		coefs = new BigDecimal[grado+1];
	}

	/**
	 * 
	 */
	@Override
	public void addComponents(){
		Add.componente(this, new JLabel("Grado del polinomio", JLabel.CENTER),
				0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.NONE, "");
		Add.componente(this, spnrGradoPol, 1, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.NONE, "Grado del Polinomio");
		
		layoutCoefsPanel();
		
		Add.componente(this, pnlCoefs, 0, 1, 2, 2, 1.0, 1.0,
				GridBagConstraints.BOTH, "Edita los coeficientes");
		
	}
	
	@Override
	public void layoutCoefsPanel(){
		pnlCoefs.removeAll();
		pnlCoefs.revalidate();
		for (int i = 0; i < listCoefs.size(); i++) {
			int x = i%4;
			int y = 2*(i/4);
			
			Add.componente(pnlCoefs, listCoefs.get(i),
					x, y, 1, 1, 1.0, 1.0,
					GridBagConstraints.BOTH, "");
		}

		pnlCoefs.repaint();
		revalidate();
	}
	
	/**
	 * @return la funci&oacute;n
	 */
	@Override
	public Funcion getFuncion() {
		coefs = new BigDecimal[grado+1];
		CoeficientePanel coefPanel;
		boolean noErrors = true;
		for (int i = 0; i <= grado && noErrors; i++) {
			coefPanel = listCoefs.get(i);
			String text = coefPanel.getTexto();
			BigDecimal tempBD = null;
			try {
				tempBD = new BigDecimal(text);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(pnlCoefs,
						"<html>Coeficiente A<sub>"+i+"</sub>: "+(text.isEmpty()?"vacio":text)+"</html>",
						"Error", JOptionPane.WARNING_MESSAGE);
				noErrors = false;
			}
			coefs[i] = tempBD;
		}
		if(noErrors){
			try {
				O.pln(java.util.Arrays.toString(coefs));
				fnc = Funcion.polinomio(grado, coefs);
			} catch (CustomException e) {}
		}else {
			fnc = null;
		}
		
		return fnc;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		int g = Integer.parseInt(spnrGradoPol.getValue().toString());//(int)spnr.getValue();
		
		if(grado<g) {
			CoeficientePanel tempcp = new CoeficientePanel("<html>A<sub>"+g+"</sub>= </html>");
			listCoefs.add(tempcp);
		} else if(grado>g) {
			listCoefs.remove(listCoefs.size()-1);
		}
		layoutCoefsPanel();
		grado = g;
	}
	
}
