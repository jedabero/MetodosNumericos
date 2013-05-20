/**
 * 
 */
package ui.main.app;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import resources.Add;
import resources.O;
import ui.aproxpol.AproxFunUI;
import ui.ecdiff.EcDiffUI;
import ui.numint.NumIntUI;
import ui.raices.RaicesUI;
import ui.ssel.JPanelSSEL;
/**
 * @author Jedabero
 *
 */
public class MetodosUI extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 6637458677520322115L;
	private int resultScale = 5;
	
	private static final String actions[] = {
		"Raíces de Polinomios",
		"Solución de Sistemas de Ecuaciones Lineales",
		"Aproximación Polinomial",
		"Integración Numérica",
		"Ecuaciones diferenciales",
		"",
		"Salir"};
	private static final String ttt[] = new String[]{
		"Grafica y obtén las raíces de polinomios. (Alt + A, R)",
		"Soluciona sistemas Ax = B. (Alt + A, S)",
		"Obtén un polinomio que aproxima los puntos una tabla tabulada. (Alt + A, A)",
		"Métodos de integración numérica. (Alt + A, I)",
		"Resolución de ecuaciones diferenciales ordinarias de primer orden.",
		" ","Terminar la aplicación. (Alt + A, L)"};
	private static final String iconsURL[] = {
			"/ui/img/Pol.png",
			"/ui/img/SEL.png",
			"/ui/img/AproxF.png",
			"/ui/img/Integration.png",
			"/ui/img/ecdiff.png"};
	
	private JMenuBar mnbPrincipal;
	private JPanel pnlPrincipal;
	
	/**
	 * 
	 */
	public MetodosUI(){
		super("Métodos Numéricos");
		setSize(1000, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initBarraMenu();
		initPanel();
		
		setLocationRelativeTo(getRootPane());
		setVisible(true);
		
		try{
			setIconImage(
					new ImageIcon(getClass().getResource(iconsURL[0])).getImage());
			}catch(Exception e){
				O.pln("FILE NOT FOUND");
				setIconImage(null);
			}//end of try/catch
		
	}
	
	private void initPanel() {
		pnlPrincipal = new JPanel(new GridBagLayout());
		
		JLabel lblInitalText = new JLabel("<html>" +
				"<H1><center>Elija el tema que desea realizar</center>.</H1>" +
				"<center>Puede hacerlo presionando alguno de los botones abajo</center>" +
				"<center>También puede hacerlo en el menú de Acciones. (Alt + A)</center>" +
				"</html>", JLabel.CENTER);
		lblInitalText.setBorder(BorderFactory.createEtchedBorder());
		Add.componente(pnlPrincipal, lblInitalText, 0, 0, 4, 1, 1, 1,
				GridBagConstraints.BOTH, "");
		
		Image icons[] = null;
		try{
			icons = new Image[] {
					ImageIO.read(getClass().getResource(iconsURL[0])),
					ImageIO.read(getClass().getResource(iconsURL[1])),
					ImageIO.read(getClass().getResource(iconsURL[2])),
					ImageIO.read(getClass().getResource(iconsURL[3])),
					ImageIO.read(getClass().getResource(iconsURL[4])),};
		}catch(Exception e){
			e.printStackTrace();
		}
		
		JButton btns[] = new JButton[actions.length];
		
		for (int i = 0; i < 5; i++) {
			btns[i] = new JButton(actions[i], new ImageIcon(icons[i]));
			btns[i].setVerticalTextPosition(3);//BOTTOM
			btns[i].setHorizontalTextPosition(0);//CENTER
			btns[i].setBackground(Color.WHITE);
			btns[i].addActionListener(this);
			Add.componente(pnlPrincipal, btns[i], i%3, i/3 +1, 1, 1, 0.1, 0.1,
					GridBagConstraints.NONE, ttt[i]);
		}
		
		add(pnlPrincipal);
	}

	private void initBarraMenu() {
		mnbPrincipal = new JMenuBar();
		
		Add.menu(mnbPrincipal, "Acciones", 'A',
				new int[]{0, 0, 0, 0, 0, 2, 0},
				actions,
				ttt,
				new ActionListener[]{this,this,this,this,this,null,this},
				null, null, null);
		
		Add.menu(mnbPrincipal, "Opciones", 'O', new int[]{0},
				new String[]{"Cifras Decimales"},
				new String[]{"Cambia el número de decimales de los resultados"},
				new ActionListener[]{this}, null, null, null);
		
		Add.menu(mnbPrincipal, "Ayuda", 'Y',
				new int[]{0, 2, 0},
				new String[]{
				"Información",
				"",
				"Que hacer?"},
				new String[]{
				"Información sobre esta aplicación.",
				"", ""},
				new ActionListener[]{this,null,this},
				null,
				new boolean[][]{{},{},{false, true, false, true}},
				new char[][]{{},{},{' ',' ',' ',' ',' '}});
		
		setJMenuBar(mnbPrincipal);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = "";
		if(e.getSource() instanceof JMenuItem){
			action = ((JMenuItem) e.getSource()).getText();
		}else if(e.getSource() instanceof JButton){
			action = ((JButton) e.getSource()).getText();
		}
		
		
		if (action.equals(actions[5])) {
			dispose();
		} else if (action.equals(actions[0])) {//POLROOTS
			pnlPrincipal.removeAll();
			pnlPrincipal.revalidate();
			RaicesUI rui = new RaicesUI();
			rui.setBorder(BorderFactory.createEtchedBorder());
			Add.componente(pnlPrincipal, rui, 0, 1, 1, 1, 1, 1,
					GridBagConstraints.BOTH, "");
		} else if (action.equals(actions[1])) {//SSEL
			pnlPrincipal.removeAll();
			pnlPrincipal.revalidate();
			JPanelSSEL ssel = new JPanelSSEL();
			ssel.setBorder(BorderFactory.createEtchedBorder());
			Add.componente(pnlPrincipal, ssel, 0, 1, 1, 1, 1, 1,
					GridBagConstraints.BOTH, "");
		} else if (action.equals(actions[2])) {//APROXFUNC
			pnlPrincipal.removeAll();
			pnlPrincipal.revalidate();
			AproxFunUI aproxFunUI = new AproxFunUI();
			aproxFunUI.setBorder(BorderFactory.createEtchedBorder());
			Add.componente(pnlPrincipal, aproxFunUI, 0, 1, 1, 1, 1, 1,
					GridBagConstraints.BOTH, "");
		} else if (action.equals(actions[3])) {//NUMERICINTEGRATION
			pnlPrincipal.removeAll();
			pnlPrincipal.revalidate();
			NumIntUI numIntUI = new NumIntUI();
			numIntUI.setBorder(BorderFactory.createEtchedBorder());
			Add.componente(pnlPrincipal, numIntUI, 0, 1, 1, 1, 1, 1,
					GridBagConstraints.BOTH, "");
		}else if (action.equals(actions[4])) {//NUMERICINTEGRATION
			pnlPrincipal.removeAll();
			pnlPrincipal.revalidate();
			EcDiffUI ecDiffUI = new EcDiffUI();
			ecDiffUI.setBorder(BorderFactory.createEtchedBorder());
			Add.componente(pnlPrincipal, ecDiffUI, 0, 1, 1, 1, 1, 1,
					GridBagConstraints.BOTH, "");
		}else if(action.equals("Cifras Decimales")){
			SpinnerNumberModel sModel = new SpinnerNumberModel(resultScale, 0, 30, 1);
			JSpinner jspn = new JSpinner(sModel);
			JOptionPane.showMessageDialog(this, jspn,
					"Indique el número de decimales a mostrar.",
					JOptionPane.PLAIN_MESSAGE);
			setResultScale(Integer.parseInt(jspn.getValue().toString()));
		}
		
	}
	
	/**
	 * @return the resultScale
	 */
	public int getResultScale() {
		return resultScale;
	}

	/**
	 * @param resultScale the resultScale to set
	 */
	public void setResultScale(int resultScale) {
		this.resultScale = resultScale;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MetodosUI();
	}
	
}
