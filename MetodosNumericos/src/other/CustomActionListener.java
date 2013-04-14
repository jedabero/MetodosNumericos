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

import funciones.Funcion;
import grafica.JGrafica;

import resources.Interval;
import resources.O;
import ui.main.RaicesUI;


/**
 * @author Jedabero
 *
 */
public class CustomActionListener implements ActionListener {
	
	private RaicesUI rui;
	private Funcion funcion;
	private JFrame jf;
	
	/**
	 * @param rui
	 */
	public CustomActionListener(RaicesUI rui){
		this.rui = rui;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JButton btn = (JButton) e.getSource();
		switch (btn.getText()) {
		case "Crear Polinomio":
			funcion = rui.getFpnlFuncion().getFnc();
			rui.getLbl1().setText(funcion.getSpecific());
			JOptionPane.showMessageDialog(null, "Función creada");
			break;
		case "Ver gráfica":
			if(funcion!=null){
				ArrayList<Color> alc = new ArrayList<Color>(1);
				alc.add(Color.BLUE);
				ArrayList<Funcion> alf = new ArrayList<Funcion>(1);
				alf.add(funcion);
				if(jf!=null){
					jf.dispose();
				}
				grafic(alf, alc);
			}else{
				JOptionPane.showMessageDialog(null, "Crea la función primero.");
			}
			break;
		case "<html>Punto<p>Fijo":
			rui.getLblX().setText("X = ");
			break;
		case "Bisección":
			O.pln();
			break;
		case "<html>Newton<p>Raphson":
			O.pln();
			break;
		case "Secante":
			O.pln();
			break;
		case "<html>Regula<p>Falsi":
			O.pln();
			break;
		default:
			O.pln(btn.getName());
			break;
		}
	}
	
	private void grafic(ArrayList<Funcion> alf, ArrayList<Color> alc){
		jf = new JFrame(""+funcion);
		jf.setSize(800, 400);
		JGrafica jg = new JGrafica(alf, alc, jf.getSize(),
				new Interval(BigDecimal.ONE.negate(), BigDecimal.ONE),
				new Interval(BigDecimal.ONE.negate(), BigDecimal.ONE));
		
		jf.add(jg);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
}
