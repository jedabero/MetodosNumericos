package UI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import components.Add;
import custom.LangResource;

import resources.Constantes.TipoFuncion;
import resources.Constantes.FuncionTrig;
import stream.O;

import funciones.FuncionBase;
import funciones.FuncionPolinomica;
import funciones.FuncionTrigonometrica;
import grafica.JLabelGrafica;

/**
 * Esta clase crea una ventana de dialogo en la que se mostrarán los
 * parámetros de la función a crear o editar.
 * @author <a href="https://twitter.com/Jedabero" target="_blank">Jedabero</a>
 *
 */@SuppressWarnings("deprecation")
public final class EditaFuncionDialog extends JDialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3935940317582610579L;
	/**
	 * 
	 */
	public static final int AÑADIR = 0;
	/**
	 * 
	 */
	public static final int EDITAR = 1;
	
	private JToolBar opciones;
	private JLabelGrafica gf;
	private int index;
	private int accion;
	private FuncionBase tempFuncion;
	private JCheckBox jrb;
	private ArrayList<FuncionBase> arrListFB;
	private ArrayList<JCheckBox> arrListJRB;
	private ArrayList<Color> arrListColor;
	private ItemListener itemL;
	private Color c;
	
	private TipoFuncion tipoFunOr;
	private int numTermOr;
	private BigDecimal constOr[];
	
	private JPanel ecuacion = new JPanel();
	private JPanel propiedades = new JPanel();
	private JPanel panelGrid = new JPanel(new GridBagLayout());
	
	private JLabel labelTipoFuncion;
	private JComboBox dropTipoFuncion;
	private ItemListener selecTipoIL;
	
	private JLabel labelTerminos;
	private JTextField textTerminos;
	private JButton actualiza = new JButton();
	private ActionListener actualizaAL;
	
	private JLabel labelA[];
	private ArrayList<JTextField> textA;
	private JLabel labelB[];
	private ArrayList<JTextField> textB;
	private JComboBox dropTipoFuncTrig;
	private ArrayList<JComboBox> arrListDropFuncTrig;
	
	private JLabel labelFuncion;
	private JLabel labelEcuacion;
	private JButton listoBoton = new JButton();
	private ActionListener listoAL;

	private JButton cerrarBoton = new JButton();
	private ActionListener cerrarAL;
	
	private LangResource l;
	
	/**
	 * @param ventana	Parent
	 * @param jpJCB		toolbar
	 * @param g			gráfica
	 * @param posicion	posición de la función
	 * @param fb		Función actual
	 * @param alFB		lista de funciones
	 * @param alJRB		lista de check boxes
	 * @param alColor	lista de colores
	 * @param iL		item listener
	 * @param ACCION	acción correspondiente
	 * @param rs 		lenguaje
	 */
	public EditaFuncionDialog(JFrame ventana,
			JToolBar	jpJCB,
			JLabelGrafica	 g,
			int	posicion,
			FuncionBase		fb, 
			ArrayList<FuncionBase>	alFB,
			ArrayList<JCheckBox>	alJRB,
			ArrayList<Color>	alColor,
			ItemListener	iL,
			int ACCION,
			ResourceBundle rs){
		super(ventana, true);
		l = new LangResource(rs);
		opciones	= jpJCB;
		this.gf		= g;
		index		= posicion;
		arrListFB	= alFB;
		arrListJRB	= alJRB;
		arrListColor= alColor;
		itemL 		= iL;
		accion		= ACCION;
		tempFuncion = fb;
		tipoFunOr	= fb.getTipoFuncion();
		numTermOr	= fb.getTerminos();
		constOr		= fb.getA();
		O.p(tipoFunOr+" ("+constOr.length+", "+fb.getA().length+") ");
		O.pln("(EditaFuncionDialog.java:134)");//ERR error output
		
		setTitle(l.s("editFTitle"));
		actualiza.setText(l.s("updateBttn"));
		listoBoton.setText(l.s("doneBttn"));
		cerrarBoton.setText(l.s("cancelBttn"));
		
		c = new Color(((int)(25.6*Math.random()))*10,
				((int)(25.6*Math.random()))*10,
				((int)(25.6*Math.random()))*10);
		
		initListeners();
		
		labelTipoFuncion = new JLabel(l.s("fTypeL"));
		dropTipoFuncion = new JComboBox();//TODO añadir tipos de función.
		//for(Funcion f : Funcion.values()) dropTipoFuncion.addItem(f);
		dropTipoFuncion.addItem(TipoFuncion.POLINOMICA);
		dropTipoFuncion.addItem(TipoFuncion.TRIGONOMETRICA);
		dropTipoFuncion.setSelectedItem(tipoFunOr);
		dropTipoFuncion.addItemListener(selecTipoIL);
		
		dropTipoFuncTrig = new JComboBox();
		for(FuncionTrig ft : FuncionTrig.values()) dropTipoFuncTrig.addItem(ft);
		
		switch(tempFuncion.getTipoFuncion()){
		case POLINOMICA:
			labelTerminos = new JLabel(l.s("polTermL"));
			textTerminos = new JTextField(""+tempFuncion.getGrado());
			propiedades.add(labelTerminos);
			propiedades.add(textTerminos);
			break;
		case TRIGONOMETRICA:
		case EXPONENCIAL:
		case LOGARITMICA:
		default:
			labelTerminos = new JLabel(l.s("defTermL"));
			textTerminos = new JTextField(""+tempFuncion.getTerminos()); 
			propiedades.add(labelTerminos);
			propiedades.add(textTerminos);
			break;
		case RACIONAL:
			//TODO RACIONAL
			break;
		}
		
		textTerminos.addActionListener(actualizaAL);
		actualiza.addActionListener(actualizaAL);
		propiedades.add(actualiza);
		
		setPanelEcuacion();
		labelFuncion = new JLabel(tempFuncion.getFuncion());
		labelEcuacion = new JLabel(tempFuncion.getEcuacion());
		listoBoton.addActionListener(listoAL);
		cerrarBoton.addActionListener(cerrarAL);
		
		Add.componente(panelGrid, labelTipoFuncion, 0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, null);
		Add.componente(panelGrid, dropTipoFuncion, 2, 0, 2, 1, 1.0, 1.0,
				GridBagConstraints.HORIZONTAL, l.s("droplistTTT"));
		Add.componente(panelGrid, propiedades, 0, 1, 4, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, l.s("pPanelTTT"));
		Add.componente(panelGrid, ecuacion, 0, 2, 4, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, l.s("ecPanelTTT"));
		Add.componente(panelGrid, labelFuncion, 0, 3, 4, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, l.s("funcLabelTTT"));
		Add.componente(panelGrid, labelEcuacion, 0, 4, 4, 1, 1.0, 1.0,
				GridBagConstraints.BOTH, l.s("ecLabelTTT"));
		Add.componente(panelGrid, listoBoton, 0, 5, 2, 1, 0.0, 0.0,
				GridBagConstraints.BOTH, l.s("doneBttnTTT"));
		Add.componente(panelGrid, cerrarBoton, 2, 5, 2, 1, 0.0, 0.0,
				GridBagConstraints.BOTH, l.s("cancelBttnTTT"));
		
		add(panelGrid);
		
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setSize(300, 500);
		setLocationRelativeTo(ventana);
		((JComponent) getContentPane()).setOpaque(true);
		setVisible(true);
	} 
	
	private void setPanelEcuacion() {
		ecuacion.removeAll();
		labelA = new JLabel[tempFuncion.getTerminos()];
		labelB = new JLabel[tempFuncion.getTerminos()];
		textA = new ArrayList<JTextField>();
		textB = new ArrayList<JTextField>();
		arrListDropFuncTrig = new ArrayList<JComboBox>();
		int l = labelA.length;
		int y = l%2==0 ? l/2 : l/2 + 1;
		if(l==3) y=3;
		switch(tempFuncion.getTipoFuncion()){
		case POLINOMICA:
			if(l==3) y=3; ecuacion.setLayout(new GridLayout(y,4));
			for(int n = 0;n<tempFuncion.getTerminos();n++){
				labelA[n] = new JLabel("<html>A<sub>"+n+"</sub> :\t");
				labelA[n].setHorizontalAlignment(SwingConstants.RIGHT);
				textA.add(n, new JTextField(""+tempFuncion.getA()[n]));
				ecuacion.add(labelA[n]);
				ecuacion.add(textA.get(n));
			}
			break;
		case TRIGONOMETRICA:
			ecuacion.setLayout(new GridLayout(y*3,4));
			for(int n = 0;n<tempFuncion.getTerminos();n++){
				labelA[n] = new JLabel("<html>A<sub>"+n+"</sub> :\t");
				labelB[n] = new JLabel("<html>B<sub>"+n+"</sub> :\t");
				labelA[n].setHorizontalAlignment(SwingConstants.RIGHT);
				labelB[n].setHorizontalAlignment(SwingConstants.RIGHT);
				textA.add(n, new JTextField(""+tempFuncion.getA()[n]));
				textB.add(n, new JTextField(""+tempFuncion.getB()[n]));
				JComboBox jcb = dropTipoFuncTrig;
				jcb.addItemListener(selecTipoIL);
				jcb.setSelectedItem(tempFuncion.getTipos()[n]);
				arrListDropFuncTrig.add(n, jcb);
				ecuacion.add(labelA[n]); ecuacion.add(textA.get(n));
				ecuacion.add(labelB[n]); ecuacion.add(textB.get(n));
				ecuacion.add(arrListDropFuncTrig.get(n));
			}
			break;
		case EXPONENCIAL:
		case LOGARITMICA:
		default:
			break;
		case RACIONAL:
			//TODO RACIONAL
			break;
		}
	}
	
	/**
	 * 
	 */
	private void metodoProceso(){
		
		int terminos = 0;
		try{
			terminos = Integer.parseInt(textTerminos.getText());
			if(terminos<0) terminos = 0;
		}catch(Exception ex){;
		O.pln(ex.toString()+" (EditaFuncionDialog.java:217)");//REVISELINE
			JOptionPane.showMessageDialog(getParent(), 
					l.s("errDialog1")+ex.getMessage(),
					l.s("inputError"), JOptionPane.ERROR_MESSAGE);
			terminos = tempFuncion.getTerminos();
		}
		
		switch(tempFuncion.getTipoFuncion()){
		case POLINOMICA:
			tempFuncion.setGrado(terminos);
			break;
		case TRIGONOMETRICA:
		case EXPONENCIAL:
		case LOGARITMICA:
		default:
			tempFuncion.setTerminos(terminos);
			break;
		case RACIONAL:
			//TODO RACIONAL
			break;
		}
		
		textTerminos.setText(""+terminos);
		
		BigDecimal A[] = new BigDecimal[tempFuncion.getTerminos()];
		for(int n=0;n<A.length;n++){
			try{
				A[n] = new BigDecimal(textA.get(n).getText());
			}catch(IndexOutOfBoundsException ioobe){
				O.pln(ioobe.toString()+" (EditaFuncionDialog.java:250)");//REVISELINE
				A[n] = BigDecimal.ONE;
				textA.add(new JTextField(""+A[n]));
			}catch(NumberFormatException nfe){
				O.pln(nfe.toString()+" (EditaFuncionDialog.java:250)");//REVISELINE
				JOptionPane.showMessageDialog(getParent(),
						l.s("errDialog2")+n+"= "+textA.get(n).getText()+
						" : "+l.s("errDialog3")+": =1",
						l.s("inputError"), JOptionPane.ERROR_MESSAGE);
				A[n] = BigDecimal.ONE;
				textA.add(new JTextField(""+A[n]));
			}
		}
		tempFuncion.setA(A);
		
		BigDecimal B[] = new BigDecimal[tempFuncion.getTerminos()];
		for(int n=0;n<B.length;n++){
			try{
				B[n] = new BigDecimal(textB.get(n).getText());
			}catch(IndexOutOfBoundsException ioobe){
				O.pln(ioobe.toString()+" (EditaFuncionDialog.java:311)");//REVISELINE
				B[n] = BigDecimal.ONE;
				textB.add(new JTextField(""+B[n]));
			}catch(NumberFormatException nfe){
				O.pln(nfe.toString()+" (EditaFuncionDialog.java:311)");//REVISELINE
				JOptionPane.showMessageDialog(getParent(),
						l.s("errDialog2")+n+"= "+textB.get(n).getText()+
						" : "+l.s("errDialog3")+": =1",
						l.s("inputError"), JOptionPane.ERROR_MESSAGE);
				B[n] = BigDecimal.ONE;
				textB.add(new JTextField(""+B[n]));
			}
		}
		tempFuncion.setB(B);
		
		switch(tempFuncion.getTipoFuncion()){
		case POLINOMICA:
			tempFuncion.updateConstantesTerminos(terminos+1, A, B);
			break;
		case TRIGONOMETRICA:
		case EXPONENCIAL:
		case LOGARITMICA:
		default:
			tempFuncion.updateConstantesTerminos(terminos, A, B);
			break;
		case RACIONAL:
			break;
		}
		opciones.validate();
		labelFuncion.setText(tempFuncion.getFuncion());
		labelEcuacion.setText(tempFuncion.getEcuacion());
		setPanelEcuacion();
	}
	
	private void initListeners(){
		
		actualizaAL = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				setEnabled(false);
				metodoProceso();
				setCursor(null);
				setEnabled(true);
				O.pln("end of actualizaAL");
			}
		};
		
		listoAL = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				actualizaAL.actionPerformed(e);
				switch(accion){
				case AÑADIR:
					arrListFB.add(index, tempFuncion);
					arrListColor.add(index, c);
					jrb = new JCheckBox(tempFuncion.getFuncion());
					jrb.setForeground(c);
					jrb.addItemListener(itemL);
					jrb.setText(tempFuncion.getFuncion());
					opciones.remove(jrb);
					opciones.add(jrb);
					arrListJRB.add(index, jrb);
					break;
				case EDITAR:
					arrListFB.set(index, tempFuncion);
					jrb = arrListJRB.get(index);
					jrb.setForeground(arrListColor.get(index));
					jrb.addItemListener(itemL);
					jrb.setText(tempFuncion.getFuncion());
					jrb.setSelected(false);
					opciones.remove(jrb);
					opciones.add(jrb);
					arrListJRB.set(index, jrb);
					break;
				default:
					break;
				}
				
				gf.actualizaLista(arrListFB);
				gf.actualizaLista(arrListColor);
				gf.pintaGrafica();
				
				dispose();
			}
		};
		
		cerrarAL = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				switch(accion){
				case AÑADIR:
					break;
				case EDITAR:
					O.pln(numTermOr+" (EditaFuncionDialog.java:344)");//REVISELINE
					switch(tempFuncion.getTipoFuncion()){
					case POLINOMICA://TODO you still have no idea do you?
						((FuncionPolinomica)tempFuncion).update(
								FuncionBase.getPaso(),
								FuncionBase.getIntervalo(),
								numTermOr-1, constOr);
						break;
					case TRIGONOMETRICA:
					case EXPONENCIAL:
					case LOGARITMICA:
					default:
						//TODO RACIONAL + DEFAULT
						break;
					case RACIONAL:
						break;
					}
					
					arrListFB.set(index, tempFuncion);
					jrb = arrListJRB.get(index);
					jrb.setForeground(arrListColor.get(index));
					jrb.addItemListener(itemL);
					jrb.setText(tempFuncion.getFuncion());
					jrb.setSelected(false);
					opciones.remove(jrb);
					opciones.add(jrb);
					arrListJRB.set(index, jrb);
					break;
				default:
					break;
				}
				dispose();
			}
		};
		
		selecTipoIL = new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				if(e.getSource().equals(dropTipoFuncion)){
					System.out.println("droporpdor");
					TipoFuncion tempF = (TipoFuncion)e.getItem();
					if(e.getStateChange() == ItemEvent.SELECTED){
						BigDecimal[] a = tempFuncion.getA();
						BigDecimal[] b = tempFuncion.getB();
						switch(tempF){
						case POLINOMICA:
							labelTerminos.setText(l.s("polTermL"));
							tempFuncion = new FuncionPolinomica(tempFuncion.getGrado());
							tempFuncion.update(FuncionBase.getPaso(),
									tempFuncion.getTerminos(),
									FuncionBase.getIntervalo(), a, b, tempF);
							break;
						case TRIGONOMETRICA:
							labelTerminos.setText(l.s("defTermL"));
							FuncionTrig tp[] = new FuncionTrig[tempFuncion.getTerminos()];
							for(int i=0; i<tp.length;i++) tp[i] = FuncionTrig.SIN;
							tempFuncion = new FuncionTrigonometrica(
									tempFuncion.getTerminos(), tp);
							tempFuncion.update(FuncionBase.getPaso(),
									tempFuncion.getTerminos(),
									FuncionBase.getIntervalo(), a, b, tempF);
							break;
						case EXPONENCIAL:
						case LOGARITMICA:
						default:
							//TODO RACIONAL + DEFAULT
							break;
						case RACIONAL:
							break;
						}
						metodoProceso();
						
					}
				}else if(e.getSource().equals(dropTipoFuncTrig)){
					System.out.println("dfbdfjugsdfgdjfgfxdfgjdxfgch");
				}
				
			
				if(e.getStateChange() == ItemEvent.DESELECTED){
					O.pln("Deseleccionado: "+e.getItem());
				}
				
			}
		};
	}
	
}
