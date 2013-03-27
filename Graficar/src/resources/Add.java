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
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

/**
 * @author Jedabero
 *
 */
public final class Add {
	
	/**
	 * Añade componentes al panel con un {@code GridBagLayout}.
	 * @param gbl	El componente con el GridBagLayout.
	 * @param jc	El componente a ser añadido.
	 * @param x		La posición en columnas del componente. La primera es 0.
	 * @param y 	La posición en filas del componente. La primera es 0.
	 * @param w		El número de columnas que ocupa el componente.
	 * @param h 	El número de filas que ocupa el componente,
	 * @param wx	La proporción de ancho del componente.
	 * @param wy	La proporción de alto del componente.
	 * @param f 	Valor que determina como redimensionar el componente.
	 * @param toolTip Texto a mostrar en el ToolTip del componente.
	 */
	public static void componente(Container gbl, JComponent jc,
			int x, int y, int w, int h, double wx, double wy, int f,
			String toolTip){
		jc.setToolTipText(toolTip);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x; gbc.gridy = y;
		gbc.gridwidth = w; gbc.gridheight = h;
		gbc.weightx = wx; gbc.weighty = wy;
		gbc.fill = f;
		gbl.add(jc, gbc);
	}
	
	/**
	 * Agrega un item de menú al menú especificado.
	 * @param menu	menu al que se va a agregar el item.
	 * @param texto	texto que llevará el item.
	 * @param ttt 	ToolTipText
	 * @param al	listener que se ejecutará al seleccionar el item.
	 * @param mn	letra mnemotécnica
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
	 * Agrega un item de menú al menú especificado con un conjunto de submenús.
	 * @param menu		menu al que se va a agregar el item.
	 * @param texto		texto que llevará el item.
	 * @param ttt		ToolTipText
	 * @param al 		listener que se ejecutará al seleccionar el item.
	 * @param mn		letra mnemotécnica
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
	 * Añade componentes a un {@code DesktopPane}, con una ubicación y tamaños
	 * específicos. 
	 * @param jdp	el DesktopPane al que se añadirán componentes.
	 * @param jc	el componente a ser añadido.
	 * @param x		ubicación en pixeles de derecha a izquierda.
	 * @param y 	ubicación en pixeles de arriba a abajo.
	 * @param w 	ancho que ocupará el componente.
	 * @param h 	alto que ocupará el componente.
	 */
	public static void aDeskPane(JDesktopPane jdp, JComponent jc,
			int x, int y, int w, int h){
		jc.setBounds(x, y, w, h);
		jdp.add(jc);
	}
	
}
