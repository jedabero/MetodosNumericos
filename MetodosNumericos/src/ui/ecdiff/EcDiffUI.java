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
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import resources.Add;
import resources.math.BigInterval;
import resources.math.edos.EcuacionDiferencialOrden1;
import resources.math.funciones.Funcion;
import ui.EditaPolinomioPanel;
import ui.main.app.MetodosUI;

/**
 * @author Jedabero
 *
 */
public class EcDiffUI extends JPanel implements ChangeListener, ActionListener,
		ItemListener{
	
	private static final long serialVersionUID = 3179335173657009527L;
	
	private EcuacionDiferencialOrden1 edo1;
	
	private JPanel pnlEdicion;
	private EditaPolinomioPanel eppQx;
	private EditaPolinomioPanel eppPx;
	
	private JLabel lblEc;
	private JButton btnCreaEc;
	
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
		eppQx = new EditaPolinomioPanel("Edici�n de Q(x)");
		eppPx = new EditaPolinomioPanel("Edici�n de P(x)");
		
		btnCreaEc = new JButton("Crea la ecuaci�n");
		btnCreaEc.addActionListener(this);
		lblEc = new JLabel();
		
		pnlMetodos = new JPanel(new GridBagLayout());
		pnlMetodos.setBorder(javax.swing.BorderFactory.createTitledBorder("M�todos"));
		
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
		
		//0 - 0 - Etiqueta de ecuaci�n general de las edos
		Add.componente(pnlEdicion, new JLabel("<html><center>" +
				"Resoluci�n de ecuaciones ordinarias de primer orden" +
				"<table border=0 cellspacing=0 cellpadding=0 width=\"40%\" align=\"center\" class=\"equation\"><tr>"
				+"<td nowrap align=center><i>dy</i><hr noshade size=1><i>dx</i></td>"
				+"<td nowrap> &nbsp; &nbsp;=&nbsp; &nbsp; <i>Q(x) + P(x)y</i></td>"
				+"<td width=\"50%\"></td></tr></table>" +
				"</center></html>", JLabel.CENTER),
				0, 0, 4, 1, 1.0, 1.0, GridBagConstraints.NONE, "");
		
		//0 - 1 - EditaPolinomioPanels
		Add.componente(pnlEdicion, eppQx, 0, 1, 2, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlEdicion, eppPx, 2, 1, 2, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		//0 - Panel Edici�n
		Add.componente(this, pnlEdicion, 0, 0, 4, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		
		//1 - label y bot�n ecuaci�n
		Add.componente(this, lblEc, 0, 1, 3, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "Ecuaci�n diferencial");
		Add.componente(this, btnCreaEc, 3, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		
		//2 - Separator
		Add.componente(this, new JSeparator(JSeparator.HORIZONTAL),
				0, 2, 4, 1, 1.0, 1.0, GridBagConstraints.HORIZONTAL, "");
		
		//3 - 0 - Botones m�todos
		for(int i = 0; i<btnFind.length; i++){
			btnFind[i] = new JButton(strMetodos[i]);
			btnFind[i].addActionListener(this);
			Add.componente(pnlMetodos, btnFind[i], i, 0, 1, 1, 1.0, 1.0,
					GridBagConstraints.BOTH, "");
		}
		
		//3 - 1 - 0 - X0 y Xn
		Add.componente(pnlParam, new JLabel("<html>x<sub>0</sub></html>", JLabel.CENTER),
				0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.NONE, "");
		Add.componente(pnlParam, txtX0, 1, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlParam, new JLabel("<html>x<sub>n</sub></html>", JLabel.CENTER),
				2, 0, 1, 1, 1.0, 1.0, GridBagConstraints.NONE, "");
		Add.componente(pnlParam, txtXn, 3, 0, 1, 1, 1, 1,
				GridBagConstraints.BOTH, "");
		//3 - 1 - 1 - Y0 y subdivs
		Add.componente(pnlParam, new JLabel("<html>y<sub>0</sub></html>", JLabel.CENTER),
				0, 1, 1, 1, 1, 1, GridBagConstraints.NONE, "");
		Add.componente(pnlParam, txtY0, 1, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(pnlParam, new JLabel("Subdivisiones", JLabel.CENTER),
				2, 1, 1, 1, 1, 1, GridBagConstraints.NONE, "");
		Add.componente(pnlParam, spnrN, 3, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		//3 - 1 - Panel de par�metros
		Add.componente(pnlMetodos, pnlParam, 0, 1, 4, 2, 1.0, 1.0, GridBagConstraints.BOTH, "");
		//3 - 3 - etiqueta resultado
		Add.componente(pnlMetodos, lblRes, 0, 3, 4, 1, 1.0, 1.0, GridBagConstraints.BOTH, "");
		//3 - panel m�todos
		Add.componente(this, pnlMetodos, 0, 3, 4, 1, 1.0, 1.0, GridBagConstraints.BOTH, "");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		
		BigDecimal res = null;
		
		if (btn.equals(btnCreaEc)) {
			Funcion Px = eppPx.getFuncion();
			Funcion Qx = eppQx.getFuncion();
			edo1 = new EcuacionDiferencialOrden1(Px, Qx);
			lblEc.setText("<html>"+edo1.getSpecific()+"</html>");
		} else if (btn.equals(btnFind[0])) {
			Object o[] = getParams();
			res = edo1.metodoEulerSimple((BigInterval)o[0], (BigDecimal)o[1], Integer.parseInt(o[2].toString()));
		} else if(btn.equals(btnFind[1])){
			Object o[] = getParams();
			res = edo1.metodoEulerSimpleModificado((BigInterval)o[0], (BigDecimal)o[1], Integer.parseInt(o[2].toString()));
		} else if(btn.equals(btnFind[2])){
			Object o[] = getParams();
			res = edo1.metodoSeriesTaylorOrden2((BigInterval)o[0], (BigDecimal)o[1], Integer.parseInt(o[2].toString()));
		} else if(btn.equals(btnFind[3])){
			Object o[] = getParams();
			res = edo1.metodoRungeKutta((BigInterval)o[0], (BigDecimal)o[1], Integer.parseInt(o[2].toString()));
		}
		
		int scale = MetodosUI.getResultScale();
		//try {
			res = res.setScale(scale, RoundingMode.HALF_UP);
			lblRes.setText("y("+txtXn.getText()+") = "+res.stripTrailingZeros().toString());
		//} catch (Exception e2) {
			// TODO: handle exception
		//}
	}
	
	private Object[] getParams(){
		Object[] obj = new Object[3];
		obj[0] = new BigInterval(new BigDecimal(txtX0.getText()), new BigDecimal(txtXn.getText()));
		obj[1] = new BigDecimal(txtY0.getText());
		obj[2] = Integer.parseInt(spnrN.getValue().toString());//(int)spnrIt.getValue();
		return obj;
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
