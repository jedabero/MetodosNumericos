package UI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import components.Add;

import funciones.FuncionBase;
import grafica.JLabelGrafica;

/**
 * @author <a href="https://twitter.com/Jedabero" target="_blank">Jedabero</a>
 *
 */
public class EditaIntervaloDialog extends JDialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3935940317582610580L;
	
	private ArrayList<FuncionBase>  arrListFB;
	private JLabelGrafica gf;
	
	private BigDecimal minimoOr;
	private JLabel minimoLabel;
	private JTextField minimoText;
	private BigDecimal maximoOr;
	private JLabel maximoLabel;
	private JTextField maximoText;
	private BigDecimal pasoOr;
	private JLabel labelPaso;
	private JTextField textPaso;
	private BigDecimal[] intervalOr;
	private JButton actualiza;
	private JButton listo;
	private ActionListener listoAL;
	private JButton cancelar;
	private ActionListener cancelarAL;
	private char XorY;
	
	/**
	 * @param ventana
	 * @param alFB
	 * @param jlg
	 * @param XORY
	 */
	public EditaIntervaloDialog(JFrame ventana, ArrayList<FuncionBase> alFB,
			JLabelGrafica jlg, char XORY){
		super(ventana, "Edita Intervalo", true);
		pasoOr		= FuncionBase.getPaso();
		arrListFB	= alFB;
		gf			= jlg;
		XorY		= XORY;
		switch(XorY){
		case 'X': intervalOr = FuncionBase.getIntervalo();	break;
		case 'Y': intervalOr = JLabelGrafica.getRange();	break;
		default: break;
		}
		minimoOr 	= intervalOr[0];
		maximoOr	= intervalOr[1];
		
		initListeners();
		
		if(XorY=='X'){
			labelPaso = new JLabel("Paso: ");
			textPaso = new JTextField(""+FuncionBase.getPaso());
		}
		
		minimoLabel = new JLabel(XorY+" mínimo:");
		minimoText = new JTextField(""+minimoOr);
		
		maximoLabel = new JLabel(XorY+" máximo:");
		maximoText = new JTextField(""+maximoOr);
		
		actualiza = new JButton("Actualiza");
		actualiza.addActionListener(listoAL);
		listo = new JButton("Listo");
		listo.addActionListener(listoAL);
		cancelar = new JButton("Cancelar");
		cancelar.addActionListener(cancelarAL);
		
		JDesktopPane thejdp = new JDesktopPane();
		if(XorY=='X'){
			Add.aDeskPane(thejdp, labelPaso, 28, 10, 100, 30);
			Add.aDeskPane(thejdp, textPaso, 153, 10, 100, 30);
			Add.aDeskPane(thejdp, minimoLabel, 28, 45, 100, 30);
			Add.aDeskPane(thejdp, minimoText, 153, 45, 100, 30);
			Add.aDeskPane(thejdp, maximoLabel, 28, 80, 100, 30);
			Add.aDeskPane(thejdp, maximoText, 153, 80, 100, 30);
			Add.aDeskPane(thejdp, actualiza, 10, 115, 80, 30);
			Add.aDeskPane(thejdp, listo, 95, 115, 80, 30);
			Add.aDeskPane(thejdp, cancelar, 180, 115, 80, 30);
			setSize(280+5,200);
		}else if(XorY=='Y'){
			Add.aDeskPane(thejdp, minimoLabel, 28, 10, 100, 30);
			Add.aDeskPane(thejdp, minimoText, 153, 10, 100, 30);
			Add.aDeskPane(thejdp, maximoLabel, 28, 45, 100, 30);
			Add.aDeskPane(thejdp, maximoText, 153, 45, 100, 30);
			Add.aDeskPane(thejdp, actualiza, 10, 80, 80, 30);
			Add.aDeskPane(thejdp, listo, 95, 80, 80, 30);
			Add.aDeskPane(thejdp, cancelar, 180, 80, 80, 30);
			setSize(280+5,165);
		}
		
		thejdp.setBackground(new Color(238, 238, 238));
		add(thejdp);
		
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(ventana);
		setVisible(true);
	}
	
	/**
	 * @return regresa el valor del paso
	 */
	public BigDecimal getPaso(){
		return pasoOr;
	}
	
	/**
	 * @return regresa el intervalo
	 */
	public BigDecimal[] getIntervalo(){
		return intervalOr;
	}
	
	private void initListeners() {
		
		listoAL = new  ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// TODO EditInterval DONE
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				setEnabled(false);
				metodo();
				setCursor(null);
				setEnabled(true);
				if(e.getSource().equals(listo)){
					pasoOr = FuncionBase.getPaso();
					intervalOr = FuncionBase.getIntervalo();
					dispose();
				}
				
			}
		};
		
		cancelarAL = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// TODO EditInterval CANCEL
				switch(XorY){
				case 'X':
					ListIterator<FuncionBase> i;
					for(i = arrListFB.listIterator();i.hasNext();){
						FuncionBase fb = i.next();
						fb.updatePasoIntervalo(pasoOr, intervalOr);
					}
					break;
				case 'Y':
					gf.setRange(gf.isRangeSetted(), intervalOr);
					break;
				default: break;
				}
				dispose();
				gf.pintaGrafica();
			}
		};
		
	}
	
	private void metodo(){
		
		BigDecimal paso = FuncionBase.getPaso();
		switch(XorY){
		case 'X':
			try{
				paso = new BigDecimal(textPaso.getText());
				if(paso.compareTo(BigDecimal.ZERO)==0){
					paso = BigDecimal.valueOf(0.01);
				}else if(paso.compareTo(FuncionBase.getPasoMax())==1){
					paso = FuncionBase.getPasoMax();
				}else if(paso.compareTo(FuncionBase.getPasoMin())==-1){
					paso = FuncionBase.getPasoMin();
				}
			}catch(Exception ex){
				System.out.println(ex.toString()+" (EditaIntervaloDialog.java:142)");//REVISELINE
				JOptionPane.showMessageDialog(getParent(),
						"Paso: "+ex.getMessage(),
						"Error de input", JOptionPane.ERROR_MESSAGE);
				paso = FuncionBase.getPaso();
			}
			FuncionBase.initMinMaxPaso();
			textPaso.setText(""+paso);
		default: break;
		}
		
		BigDecimal min;
		try{
			min = new BigDecimal(minimoText.getText());
		}catch(Exception ex){
			System.out.println(ex.toString()+" (EditaIntervaloDialog.java:161)");//REVISELINE
			JOptionPane.showMessageDialog(getParent(),
					"Valor de mínimo: "+ex.getMessage(),
					"Error de input", JOptionPane.ERROR_MESSAGE);
			min = FuncionBase.getIntervalo()[0];
		}
		
		BigDecimal max;
		try{
			max = new BigDecimal(maximoText.getText());
		}catch(Exception ex){
			System.out.println(ex.toString()+" (EditaIntervaloDialog.java:172)");//REVISELINE
			JOptionPane.showMessageDialog(getParent(),
					"Valor de máximo: "+ex.getMessage(),
					"Error de input", JOptionPane.ERROR_MESSAGE);
			max = FuncionBase.getIntervalo()[1];
		}
		minimoText.setText(""+min);
		maximoText.setText(""+max);
		
		BigDecimal[] nuevoIntervalo = {min, max};
		switch(XorY){
		case 'X':
			for(ListIterator<FuncionBase> i = arrListFB.listIterator(); i.hasNext();){
				FuncionBase fb = i.next();
				fb.updatePasoIntervalo(paso, nuevoIntervalo);
			}
			break;
		case 'Y':
			gf.setRange(true, nuevoIntervalo);
			break;
		default: break;
		}
		
		gf.pintaGrafica();
		
	}
	
}
