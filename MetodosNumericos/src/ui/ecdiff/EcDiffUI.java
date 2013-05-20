/**
 * 
 */
package ui.ecdiff;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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
public class EcDiffUI extends JPanel implements ChangeListener, ActionListener,
		ItemListener{
	
	private static final long serialVersionUID = 3179335173657009527L;
	
	private JPanel pnlEdicion;
	
	private static final String strMetodos[] = new String[]{
		"Euler Simple", "Euler Simple Modificado", "Serie de Taylor de orden 2",
		"Runge-Kutta"};
	
	private JPanel pnlMetodos;
	private JButton btnFind[];
	
	private JPanel pnlParam;
	private JTextField txtX0;
	private JTextField txtXn;
	private JTextField txtY0;
	private SpinnerNumberModel snmNSubDiv;
	private JSpinner spnrN;
	
	private JLabel lblRes;
	
	/**
	 * 
	 */
	public EcDiffUI(){
		super(new GridBagLayout());
		
		initComponents();
		addComponents();
		
		setName("EcDiffUI");
	}

	private void initComponents() {
		pnlEdicion = new JPanel(new GridBagLayout());
		Add.componente(pnlEdicion, new JLabel("<html><center>" +
				"Resolución de ecuaciones ordinarias de primer orden" +
				"<table border=0 cellspacing=0 cellpadding=0 width=\"40%\" align=\"center\" class=\"equation\"><tr>"
				+"<td nowrap align=center><i>dy</i><hr noshade size=1><i>dx</i></td>"
				+"<td nowrap> &nbsp; &nbsp;=&nbsp; &nbsp; <i>Q(x) + P(x)y</i></td>"
				+"<td width=\"50%\"></td></tr></table>" +
				"</center></html>", JLabel.CENTER),
				0, 0, 2, 1, 1.0, 1.0, GridBagConstraints.NONE, "");
		
		
		pnlMetodos = new JPanel(new GridBagLayout());
		pnlMetodos.setBorder(javax.swing.BorderFactory.createTitledBorder("Métodos"));
		
		pnlParam = new JPanel(new GridBagLayout());
		txtX0 = new JTextField();
		txtXn = new JTextField();
		txtY0 = new JTextField();
		snmNSubDiv = new SpinnerNumberModel(5, 1, 1000, 1);
		spnrN = new JSpinner(snmNSubDiv);
		
		lblRes = new JLabel("", JLabel.CENTER);
		
		btnFind = new JButton[strMetodos.length];
		
	}
	
	private void addComponents() {
		for(int i = 0; i<btnFind.length; i++){
			btnFind[i] = new JButton(strMetodos[i]);
			btnFind[i].addActionListener(this);
			Add.componente(pnlMetodos, btnFind[i], i, 0, 1, 1, 1.0, 1.0,
					GridBagConstraints.BOTH, "");
		}
		
		Add.componente(this, pnlEdicion, 0, 0, 4, 1, 1.0, 1.0, GridBagConstraints.BOTH, "");
		
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

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}

}
