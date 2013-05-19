/**
 * 
 */
package ui.ecdiff;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import resources.Add;

/**
 * @author Jedabero
 *
 */
public class EcDiffUI extends JPanel implements ChangeListener, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3179335173657009527L;
	
	private static final String strMetodos[] = new String[]{
		"Euler Simple", "Euler Simple Modificado", "Serie de Taylor de orden 2",
		"Runge-Kutta"};
	
	private JPanel pnlMetodos = new JPanel(new GridBagLayout());
	private JButton btnFind[];
	
	private JPanel pnlParam = new JPanel(new GridBagLayout());
	private JTextField txtX0 = new JTextField();
	private JTextField txtXn = new JTextField();
	private JTextField txtY0 = new JTextField();
	private SpinnerNumberModel snmNSubDiv = new SpinnerNumberModel(5, 1, 1000, 1);
	private JSpinner spnrN = new JSpinner(snmNSubDiv);
	
	private JLabel lblRes = new JLabel("sdasdas", JLabel.CENTER);
	
	/**
	 * 
	 */
	public EcDiffUI(){
		super(new GridBagLayout());
		
		initComponents();
		
		setName("EcDiffUI");
	}
	
	private void initComponents() {
		
		
		pnlMetodos.setBorder(javax.swing.BorderFactory.createTitledBorder("Métodos"));
		
		btnFind = new JButton[strMetodos.length];
		for(int i = 0; i<btnFind.length; i++){
			btnFind[i] = new JButton(strMetodos[i]);
			btnFind[i].addActionListener(this);
			Add.componente(pnlMetodos, btnFind[i], i, 0, 1, 1, 1.0, 1.0,
					GridBagConstraints.BOTH, "");
		}
		
		Add.componente(pnlParam, new JLabel("<html>x<sub>0</sub></html>", JLabel.CENTER),
				0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.NONE, "");
		Add.componente(pnlParam, txtX0, 1, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlParam, new JLabel("<html>x<sub>n</sub></html>", JLabel.CENTER),
				2, 1, 1, 1, 1.0, 1.0, GridBagConstraints.NONE, "");
		Add.componente(pnlParam, txtXn, 3, 1, 1, 1, 1, 1,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlParam, new JLabel("<html>y<sub>0</sub></html>", JLabel.CENTER),
				0, 2, 1, 1, 1, 1, GridBagConstraints.NONE, "");
		Add.componente(pnlParam, txtY0, 1, 2, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlParam, new JLabel("Divisiones", JLabel.CENTER),
				2, 2, 1, 1, 1, 1, GridBagConstraints.NONE, "");
		Add.componente(pnlParam, spnrN, 3, 2, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		
		Add.componente(pnlMetodos, pnlParam, 0, 1, 4, 2, 1.0, 1.0, GridBagConstraints.BOTH, "");
		Add.componente(pnlMetodos, lblRes, 0, 3, 4, 1, 1.0, 1.0, GridBagConstraints.BOTH, "");
		
		//TODO
		Add.componente(this, pnlMetodos, 0, 3, 4, 1, 1.0, 1.0, GridBagConstraints.BOTH, "");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub

	}

}
