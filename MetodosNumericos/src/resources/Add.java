/**
 * 
 */
package resources;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

/**
 * @author Jedabero
 *
 */
public final class Add {
	
	/**
	 * Anade componentes al panel con un {@code GridBagLayout}.
	 * @param gbl	El componente con el GridBagLayout.
	 * @param jc	El componente a ser anadido.
	 * @param x		La posicion en columnas del componente. La primera es 0.
	 * @param y 	La posicion en filas del componente. La primera es 0.
	 * @param w		El numero de columnas que ocupa el componente.
	 * @param h 	El numero de filas que ocupa el componente,
	 * @param wx	La proporcion de ancho del componente.
	 * @param wy	La proporcion de alto del componente.
	 * @param f 	Valor que determina como redimensionar el componente.
	 * @param toolTip Texto a mostrar en el ToolTip del componente.
	 */
	public static void componente(Container gbl, JComponent jc,
			int x, int y, int w, int h, double wx, double wy, int f,
			String toolTip){
		if (!toolTip.isEmpty()) {
			jc.setToolTipText(toolTip);
		}
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x; gbc.gridy = y;
		gbc.gridwidth = w; gbc.gridheight = h;
		gbc.weightx = wx; gbc.weighty = wy;
		gbc.fill = f;
		gbl.add(jc, gbc);
	}
	
	/**
	 * @param jmb		barra de menu
	 * @param texto		texto del menu
	 * @param mn		mnemonic del menu
	 * @param types		tipos de item (item(0) o submenu(1))
	 * @param subTextos	textos de los items
	 * @param subTTT	ToolTipText de los items
	 * @param als		actionListeners de los items
	 * @param mns		mnemonics de los items
	 * @param subItems	textos de los subItems
	 * @param states	estados de cada item
	 * @param subTypes	tipos de cada item
	 */
	public static void menu(JMenuBar jmb, String texto, char mn, int types[],
			String subTextos[], String subTTT[], ActionListener als[],
			String subItems[][], boolean states[][],
			char subTypes[][]){

		JMenu jmiTemp = new JMenu(texto);
		jmiTemp.setMnemonic(mn);
		
		char mns[] = new char[subTextos.length];
		for (int i = 0; i < mns.length; i++) {
			mns[i] = ' ';
		}
		
		for (int i = 0; i < subTextos.length; i++) {
			
			String name = subTextos[i].toUpperCase();
			
			for (int j = 0; j < name.length(); j++) {
				char mnem = name.charAt(j);
				if (!isCinV(mns, mnem)) {
					mns[i] = mnem;
					break;
				}
			}
			
			switch (types[i]) {
			case 0:
				menuItem(jmiTemp, subTextos[i], subTTT[i], als[i], mns[i]);
				break;
			case 1:
				subMenu(jmiTemp, subTextos[i], subTTT[i], als[i], mns[i],
						subItems[i], states[i], subTypes[i]);
				break;
			default:
				jmiTemp.addSeparator();
				break;
			}
			
		}
		
		jmb.add(jmiTemp);
		
	}
	
	private static boolean isCinV(char v[], char c){
		for (int j = 0; j < v.length; j++) {
			if(c==v[j]){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Agrega un item de menu al menu especificado.
	 * @param menu	menu al que se va a agregar el item.
	 * @param texto	texto que lleva el item.
	 * @param ttt 	ToolTipText
	 * @param al	listener que se ejecuta al seleccionar el item.
	 * @param mn	letra mnemotecnica
	 */
	public static void menuItem(JMenu menu, String texto, String ttt,
			ActionListener al, char mn){
		JMenuItem temp = new JMenuItem(texto);
		temp.setToolTipText(ttt);
		temp.setMnemonic(mn);
		temp.addActionListener(al);
		menu.add(temp);
	}
	
	/**
	 * Agrega un item de menu al menu especificado con un conjunto de submenus.
	 * @param menu		menu al que se va a agregar el item.
	 * @param texto		texto que lleva el item.
	 * @param ttt		ToolTipText
	 * @param al 		listener que se ejecuta al seleccionar el item.
	 * @param mn		letra mnemotecnica
	 * @param subItems	array con los nombres de cada item
	 * @param states	array con los estados de cada item
	 * @param types		array con los tipo de cada item
	 */
	public static void subMenu(JMenu menu, String texto, String ttt,
			ActionListener al, char mn, String[] subItems, boolean[] states,
			char[] types){
		JMenu temp = new JMenu(texto);
		temp.setToolTipText(ttt);
		temp.setMnemonic(mn);
		ButtonGroup grupo = new ButtonGroup();
		String nextName = "0";
		for(int i=0;i<subItems.length;i++){
			String name = subItems[i];
			char mnem = name.charAt(0); 
			if(mnem==nextName.charAt(0)) mnem = name.charAt(1);
			switch(types[i]){
			case 'R':
				JRadioButtonMenuItem jrbmi = new JRadioButtonMenuItem(name);
				jrbmi.setMnemonic(mnem);
				jrbmi.setSelected(states[i]);
				jrbmi.addActionListener(al);
				grupo.add(jrbmi);
				temp.add(jrbmi);
				break;
			case 'C':
				JCheckBoxMenuItem jcbmi = new JCheckBoxMenuItem(name);
				jcbmi.setMnemonic(mnem);
				jcbmi.setSelected(states[i]);
				jcbmi.addActionListener(al);
				temp.add(jcbmi);
				break;
			default:
				JMenuItem jmi = new JMenuItem(name);
				jmi.setMnemonic(mnem);
				jmi.addActionListener(al);
				temp.add(jmi);
				break;
			}
			
			nextName = name;
		}
		menu.add(temp);
	}
	
	/**
	 * Anade componentes a un {@code DesktopPane}, con una ubicacion y tamanos
	 * especificos. 
	 * @param jdp	el DesktopPane al que se anadiran componentes.
	 * @param jc	el componente a ser anadido.
	 * @param x		ubicacion en pixeles de derecha a izquierda.
	 * @param y 	ubicacion en pixeles de arriba a abajo.
	 * @param w 	ancho que ocupa el componente.
	 * @param h 	alto que ocupa el componente.
	 */
	public static void aDeskPane(JDesktopPane jdp, JComponent jc,
			int x, int y, int w, int h){
		jc.setBounds(x, y, w, h);
		jdp.add(jc);
	}
	
}
