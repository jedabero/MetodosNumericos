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
	private JFrame frmGrafica;
	
	/**
	 * @param rui
	 */
	public CustomActionListener(RaicesUI rui){
		this.rui = rui;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String sTol = rui.getTxtTol().getText();
		BigDecimal tol = new BigDecimal(sTol);
		int maxIt = rui.getMaxIt();
		String sX0A = rui.getTxtX0A().getText();
		String sX1B = rui.getTxtX1B().getText();
		BigDecimal x0A = new BigDecimal(sX0A);
		BigDecimal x1B = new BigDecimal(sX1B);
		Interval ab = new Interval(x0A, x1B);
		
		String xResult = "X = ";
		
		JButton btn = (JButton) e.getSource();
		switch (btn.getText()) {
		case "Crear Polinomio":
			funcion = rui.getFpnlFuncion().getFnc();
			rui.getLblEq().setText(funcion.getSpecific());
			JOptionPane.showMessageDialog(null, "Función creada");
			break;
		case "Ver gráfica":
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
		case "<html>Punto<p>Fijo":
			try {
				xResult += funcion.metodoPuntoFijo(tol, maxIt, x0A).stripTrailingZeros();
				rui.getLblX().setText(xResult);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		case "Bisección":
			try {
				xResult += funcion.metodoBiseccion(tol, maxIt, ab).stripTrailingZeros();
				rui.getLblX().setText(xResult);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		case "<html>Newton<p>Raphson":
			try {
				xResult += funcion.metodoNewtonRaphson(tol, maxIt, x0A).stripTrailingZeros();
				rui.getLblX().setText(xResult);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		case "Secante":
			try {
				xResult += funcion.metodoSecante(tol, maxIt, x0A, x1B).stripTrailingZeros();
				rui.getLblX().setText(xResult);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		case "<html>Regula<p>Falsi":
			try {
				xResult += funcion.metodoRegulaFalsi(tol, maxIt, ab).stripTrailingZeros();
				rui.getLblX().setText(xResult);
			} catch (Exception e1) {
				e1.printStackTrace();
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
