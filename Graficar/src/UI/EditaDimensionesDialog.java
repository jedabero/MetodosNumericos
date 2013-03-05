/**
 * 
 */
package UI;

import grafica.JGrafica;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import components.Add;

/**
 * @author <a href="https://twitter.com/Jedabero" target="_blank">Jedabero</a>
 *
 */
public class EditaDimensionesDialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3935940317582610581L;
	
	private JLabel anchoLabel;
	private JTextField anchoText;
	private JLabel altoLabel;
	private JTextField altoText;
	private JButton listo;
	private ActionListener listoAL;
	private JDesktopPane thejdp;
	private JGrafica jlg;
	private Dimension dim = new Dimension();
	
	/**
	 * @param mainWindow
	 * @param grafica
	 */
	public EditaDimensionesDialog(JFrame mainWindow, JGrafica grafica){
		super(mainWindow, "Edita Dimensiones", true);
		jlg = grafica;
		anchoLabel = new JLabel("Ancho: ");
		anchoText = new JTextField(""+jlg.getWidth());
		altoLabel = new JLabel("Alto: ");
		altoText = new JTextField(""+jlg.getHeight());
		listoAL = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int ancho;
				try{
					ancho = Integer.parseInt(anchoText.getText());
				}catch(Exception ex){
					ancho = jlg.getWidth();
					anchoText.setText(""+ancho);
				}
				
				int alto;
				try{
					alto = Integer.parseInt(altoText.getText());
				}catch(Exception ex){
					alto = jlg.getHeight();
					altoText.setText(""+alto);
				}
				
				dim.setSize(ancho, alto);
				
				dispose();
			}
		};
		listo = new JButton("Listo");
		listo.addActionListener(listoAL);
		thejdp = new JDesktopPane();
		Add.aDeskPane(thejdp, anchoLabel, 28, 10, 100, 30);
		Add.aDeskPane(thejdp, anchoText, 153, 10, 100, 30);
		Add.aDeskPane(thejdp, altoLabel, 28, 45, 100, 30);
		Add.aDeskPane(thejdp, altoText, 153, 45, 100, 30);
		Add.aDeskPane(thejdp, listo, 28, 80, 100, 30);
		thejdp.setBackground(new Color(238, 238, 238));
		add(thejdp);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setSize(280+5,200);
		setLocationRelativeTo(mainWindow);
		setVisible(true);
	}

	/**
	 * @return Regresa la dimensión propuesta por el usuario.
	 */
	public Dimension getDimension() {return dim;}
	
}
