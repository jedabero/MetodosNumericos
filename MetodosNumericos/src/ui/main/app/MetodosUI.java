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
import javax.swing.JPanel;

import resources.Add;
import ui.aproxpol.AproxFunUI;
import ui.raices.RaicesUI;
/**
 * @author Jedabero
 *
 */
public class MetodosUI extends JFrame implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6637458677520322115L;
	
	private static final String actions[] = {
		"Polinomios",
		"Sistemas de Ecuaciones Lineales",
		"Aproximacion Polinomial",
		"Integración Numérica",
		"",
		"Salir"};
	private static final String ttt[] = new String[]{
		"Grafica y obtén las raices de polinomios.",
		"Soluciona sistemas Ax = B",
		"Obtén la ecuación de los puntos una tabla tabulada",
		"Integración",
		"",""};
	private static final String iconsURL[] = {
			"/ui/img/Pol.png",
			"/ui/img/SEL.png",
			"/ui/img/AproxF.png",
			"/ui/img/Integration.png"};
	
	private JMenuBar mnbPrincipal = new JMenuBar();
	private JPanel pnlPrincipal = new JPanel(new GridBagLayout());
	
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
	}
	
	private void initPanel() {
		
		JLabel lblInitalText = new JLabel("<html>" +
				"<H1>GOOD DAY SIR. YOU MAY NOW CHOOSE YOUR NEXT ADVENTURE...</H1>" +
				"<center>srsly choose</center>" +
				"<center>what're you waitong for?</center>" +
				"<center>last line</center>" +
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
					ImageIO.read(getClass().getResource(iconsURL[3])),};
		}catch(Exception e){
			e.printStackTrace();
		}
		
		JButton btns[] = new JButton[actions.length];
		
		for (int i = 0; i < 4; i++) {
			btns[i] = new JButton(actions[i], new ImageIcon(icons[i]));
			btns[i].setVerticalTextPosition(3);//BOTTOM
			btns[i].setHorizontalTextPosition(0);//CENTER
			btns[i].setBackground(Color.WHITE);
			btns[i].addActionListener(this);
			Add.componente(pnlPrincipal, btns[i], i%2, i/2 +1, 1, 1, 0.1, 0.1,
					GridBagConstraints.NONE, ttt[i]);
		}
		
		
		
		
		add(pnlPrincipal);
	}

	private void initBarraMenu() {
		Add.menu(mnbPrincipal, "Acciones", 'A',
				new int[]{0, 0, 0, 0, 2, 0},
				actions,
				ttt,
				new ActionListener[]{this,this,this,this,null,this},
				null, null, null);
		Add.menu(mnbPrincipal, "Ayuda", 'Y',
				new int[]{0, 2, 1},
				new String[]{
				"Información",
				"",
				"Que hacer?"},
				new String[]{
				"Información sobre esta aplicación.",
				"", ""},
				new ActionListener[]{this,null,this},
				new String[][]{{},{},{"PolRoots","SSEL","AproxFunc", "Integra"}},
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
			System.out.println("polinomio");
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

		}
		
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MetodosUI();
	}
	
}
