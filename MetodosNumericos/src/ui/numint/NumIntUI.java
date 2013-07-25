/**
 * 
 */
package ui.numint;

import grafica.JGrafica;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import resources.Add;
import resources.O;
import resources.math.BigInterval;
import resources.math.funciones.Funcion;
import ui.EditaPolinomioPanel;

/**
 * @author Jedabero
 *
 */
public class NumIntUI extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1094301241514865205L;
	
	private JFrame frmGrafica;
	private EditaPolinomioPanel fpnl;
	private JLabel lblEq;
	private MetodosIntegracionPanel mpnl;
	private Funcion funcion;
	
	/**
	 * 
	 */
	public NumIntUI(){
		super(new GridBagLayout());
		
		initComponents();
		
		setName("NumIntUI");
	}
	
	
	private void initComponents() {
		//Ingresar Polinomio
		fpnl = new EditaPolinomioPanel();
		JButton btnCreaPol = new JButton("Crear Polinomio");
		
		JSeparator sprtr1 = new JSeparator(JSeparator.HORIZONTAL);
		
		lblEq = new JLabel("", JLabel.CENTER);
		lblEq.setBorder(BorderFactory.createEtchedBorder());
		JButton btnVerGrafica = new JButton("Ver gr�fica");
		
		JSeparator sprtr2 = new JSeparator(JSeparator.HORIZONTAL);
		
		//M�todos
		mpnl = new MetodosIntegracionPanel();
		
		//0 - Tama�o Polinomio
		
		Add.componente(this, btnCreaPol, 			3, 0, 2, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		Add.componente(this, fpnl, 			0, 1, 5, 2, 1.0, 1.0,
				GridBagConstraints.BOTH, "Edita los coeficientes de la funci�n");
		//3 - Separator
		Add.componente(this, sprtr1, 				0, 3, 5, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "");
		
		//4 - Mostrar Polinomio
		Add.componente(this, lblEq, 				0, 4, 4, 2, 1.0, 1.0,
				GridBagConstraints.BOTH, "Funci�n");
		Add.componente(this, btnVerGrafica, 		4, 4, 1, 2, 1.0, 1.0,
				GridBagConstraints.BOTH, "Vea la gr�fica de est� funci�n en " +
						"una nueva ventana");
		//6 - Separator
		Add.componente(this, sprtr2, 				0, 6, 5, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, "");
		//7 - M�todosPanel
		Add.componente(this, mpnl, 			0, 7, 5, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, "");
		
		
		
		//Add ActionListeners
		btnCreaPol.addActionListener(this);
		btnVerGrafica.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		switch (btn.getText().charAt(0)) {
		case 'C'://TODO "Crear Polinomio":
			funcion = fpnl.getPol();
			lblEq.setText("<html>"+funcion.getSpecific()+"</html>");
			mpnl.setFuncion(funcion);
			JOptionPane.showMessageDialog(null, "Funci�n creada");//TODO funci�n creada
			break;
		case 'V'://TODO "Ver gr�fica":
			if(funcion!=null){
				ArrayList<Color> alc = new ArrayList<Color>(1);
				alc.add(Color.BLUE);
				ArrayList<Funcion> alf = new ArrayList<Funcion>(1);
				alf.add(funcion);
				if(frmGrafica!=null){
					frmGrafica.dispose();
				}
				grafic(alf, alc);
			}else{
				JOptionPane.showMessageDialog(null, "Crea la funci�n primero.");
			}
			break;
		default:
			O.pln(btn.getName());
			break;
		}

	}
	
	private void grafic(ArrayList<Funcion> alf, ArrayList<Color> alc){
		frmGrafica = new JFrame(""+funcion);
		frmGrafica.setSize(800, 600);
		ArrayList<Boolean> b = new ArrayList<Boolean>();
		b.add(false);
		JGrafica jg = new JGrafica(alf, alc, b, frmGrafica.getSize(),
				new BigInterval(BigDecimal.ONE.negate(), BigDecimal.ONE),
				new BigInterval(BigDecimal.ONE.negate(), BigDecimal.ONE));
		jg.setBackground(Color.WHITE);
		jg.setMostrarAreaIntegral(true);
		jg.setRangeY(false);
		BigInterval ab = mpnl.getAb();
		if(ab != null){
			jg.setIntegralX(ab);
		}
		frmGrafica.add(jg);
		frmGrafica.setVisible(true);
		frmGrafica.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

}
