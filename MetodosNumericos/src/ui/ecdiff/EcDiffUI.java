/**
 * 
 */
package ui.ecdiff;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
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
	
	private JPanel pnlMetodos;
	private JButton btnFind[];
	
	/**
	 * 
	 */
	public EcDiffUI(){
		super(new GridBagLayout());
		
		initComponents();
		
		setName("EcDiffUI");
	}
	
	private void initComponents() {
		
		
		pnlMetodos = new JPanel(new GridBagLayout());
		pnlMetodos.setBorder(javax.swing.BorderFactory.createTitledBorder("Métodos"));
		
		btnFind = new JButton[strMetodos.length];
		for(int i = 0; i<btnFind.length; i++){
			btnFind[i] = new JButton(strMetodos[i]);
			btnFind[i].addActionListener(this);
			Add.componente(pnlMetodos, btnFind[i], i, 0, 1, 1, 1.0, 1.0,
					GridBagConstraints.BOTH, "");
		}
		
		add(pnlMetodos);
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
