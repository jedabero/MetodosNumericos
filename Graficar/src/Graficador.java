
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import UI.GraficadorUI;


/**
 * @author <a href="https://twitter.com/Jedabero" target="_blank">Jedabero</a>
 *
 */
public class Graficador {
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Locale defLocale = Locale.getDefault();
		Locale[] arrLocale = new Locale[2];
		arrLocale[0] = new Locale("es", "ES");
		arrLocale[1] = new Locale("en", "US");
		
		String[] langNames = new String[arrLocale.length];
		for(int i=0;i<langNames.length;i++){
			langNames[i] = arrLocale[i].getDisplayName();
		}
		
		ResourceBundle rb = ResourceBundle.getBundle("lang/lang", defLocale);
		
		Object code = JOptionPane.showInputDialog(null, rb.getString("selLang"),
				rb.getString("selLang"), JOptionPane.PLAIN_MESSAGE, null,
				langNames, langNames[0]);
		
		for(int i=0;i<langNames.length;i++){
			if(code==langNames[i]) code = arrLocale[i];
		}
		
		ResourceBundle currRB = (code!=null) ?
				ResourceBundle.getBundle("lang/lang", (Locale)code) : rb;
		
		@SuppressWarnings("unused")
		GraficadorUI gui = new GraficadorUI(currRB);
		
		//gui.salir();
		//voidmet();
	}
	
	
	

}
