/**
 * 
 */
package other;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import resources.O;
import resources.math.Interval;
import ui.raices.RaicesUI;
import funciones.Funcion;
import grafica.JGrafica;


/**
 * @author Jedabero
 *
 */
public class CustomActionListener implements ActionListener {
	
	private RaicesUI rui;
	private Funcion funcion;
	private JFrame frmGrafica;
	
	/**
	 * @param rui
	 */
	public CustomActionListener(RaicesUI rui){
		this.rui = rui;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JButton btn = (JButton) e.getSource();
		switch (btn.getText().charAt(0)) {
		case 'C'://TODO "Crear Polinomio":
			funcion = rui.getFpnlFuncion().getFnc();
			rui.getLblEq().setText(funcion.getSpecific());
			rui.getMpnlMetodos().setFuncion(funcion);
			JOptionPane.showMessageDialog(null, "Función creada");//TODO
			break;
		case 'V'://TODO "Ver gráfica":
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
				JOptionPane.showMessageDialog(null, "Crea la función primero.");
			}
			break;
		default:
			O.pln(btn.getName());
			break;
		}
	}
	
	private void grafic(ArrayList<Funcion> alf, ArrayList<Color> alc){
		frmGrafica = new JFrame(""+funcion);
		frmGrafica.setSize(800, 400);
		JGrafica jg = new JGrafica(alf, alc, frmGrafica.getSize(),
				new Interval(BigDecimal.ONE.negate(), BigDecimal.ONE),
				new Interval(BigDecimal.ONE.negate(), BigDecimal.ONE));
		
		frmGrafica.add(jg);
		frmGrafica.setVisible(true);
		frmGrafica.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
}
