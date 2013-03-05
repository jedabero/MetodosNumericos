/*
 * %W% %E%
 */ 

package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.JCheckBox;
//import javax.swing.JComboBox;
import javax.swing.AbstractButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
//import javax.swing.JRadioButton;
import javax.swing.JToolBar;
import javax.swing.event.MouseInputAdapter;
import javax.swing.filechooser.FileNameExtensionFilter;

import components.Add;
import custom.LangResource;
import funciones.Funcion;
import grafica.JGrafica;

import resources.Interval;
import resources.M;
import stream.O;

/**
 * 
 * @author <a href="https://twitter.com/Jedabero" target="_blank">Jedabero</a>
 * @since 0.1
 */

public class GraficadorUI{
	
	private static final int HEIGHT=700;
	private static final int WIDTH=1000;
	private static final String ver= "InDev.3.69";//VER Versión
	
	private JFrame mainWindow;
	private ItemListener il;
	private ActionListener añadirFuncionAL, editarFuncionAL, quitarFuncionAL,
	graficaAL, guardarAL, salirAL, menuAyudaAL, cambiaColorAL;
	
	private ComponentAdapter ca;
	
	private JPanel panelGrid;
	
	private ArrayList<Funcion> listaFunciones;
	private JGrafica grafica;
	private Interval xInterval;
	private ArrayList<Color> colores;
	
	private ArrayList<JCheckBox> funcionJRB;
	
	private JToolBar toolBarOpciones;
	
	private JMenuBar barraMenu;
	
	LangResource l;
	
	/**
	 * @param rs El resource bundle que proveerá de lenguaje al programa
	 * 
	 */
	public GraficadorUI(ResourceBundle rs){
		l = new LangResource(rs);
		
		mainWindow = new JFrame(l.s("t")+" "+ver);
		mainWindow.setSize(WIDTH, HEIGHT);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initListeners();
		
		listaFunciones = new ArrayList<Funcion>();
		xInterval = new Interval(BigDecimal.valueOf(-1), BigDecimal.valueOf(1));
		//TODO PASO GLOBAL INIT
		
		Funcion f = M.funcionRandom();
		listaFunciones.add(f);
		
		colores = new ArrayList<Color>();
		Color fg = new Color(((int)(25.6*Math.random()))*10,
				((int)(25.6*Math.random()))*10,
				((int)(25.6*Math.random()))*10);
		colores.add(fg);

		funcionJRB = new ArrayList<JCheckBox>();
		JCheckBox jrb = new JCheckBox(f.getSpecific());
		jrb.setForeground(fg);
		jrb.addItemListener(il);
		funcionJRB.add(jrb);
		
		grafica = new JGrafica(listaFunciones, colores, new Dimension(500, 500),
				xInterval);
		
		panelGrid = new JPanel(new GridBagLayout());
		
		toolBarOpciones = new JToolBar(l.s("tToolBar"));
		mainWindow.getContentPane().add(toolBarOpciones, BorderLayout.NORTH);
		toolBarOpciones.add(jrb);
		
		Add.componente(panelGrid, grafica, 0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.CENTER, l.s("g"));
		
		barraMenu = new JMenuBar();
		
		//Archivo
		JMenu menuArchivo = new JMenu(l.s("mArch"));
		menuArchivo.setMnemonic('A');
		Add.menuItem(menuArchivo, l.s("mArchNew"), l.s("mArchNewTTT"),
				null, 'N');
		Add.menuItem(menuArchivo, l.s("mArchOpen"), l.s("mArchOpenTTT"),
				null, 'A');
		String[] guardarSubItems = {l.s("mArchSave1"), l.s("mArchSave2"),
				l.s("mArchSave3"), l.s("mArchSave4")};
		Add.subMenu(menuArchivo, l.s("mArchSave"), l.s("mArchSaveTTT"),
				guardarAL, 'G', guardarSubItems, null,
				new char[guardarSubItems.length]);
		Add.menuItem(menuArchivo, l.s("mArchExit"), l.s("mArchExitTTT"),
				salirAL, 'S');
		barraMenu.add(menuArchivo);
		
		//Editar
		JMenu menuEditar = new JMenu(l.s("mEdit"));
		menuEditar.setMnemonic('E');
		Add.menuItem(menuEditar, l.s("mEditAdd"), l.s("mEditAddTTT"),
				añadirFuncionAL, 'A');
		Add.menuItem(menuEditar, l.s("mEditEdit"), l.s("mEditEditTTT"),
				editarFuncionAL, 'E');
		Add.menuItem(menuEditar, l.s("mEditRem"), l.s("mEditRemTTT"),
				quitarFuncionAL, 'Q');
		menuEditar.addSeparator();
		Add.menuItem(menuEditar, l.s("mEditCol"), l.s("mEditColTTT"),
				cambiaColorAL, 'C');
		barraMenu.add(menuEditar);
		
		//Gráfica
		JMenu menuGrafica = new JMenu(l.s("mGraf"));
		menuGrafica.setMnemonic('G');
		Add.menuItem(menuGrafica, l.s("mGrafInt"), l.s("mGrafIntTTT"),
				graficaAL, 'X');
		String[] intervaloSubItems =
			{l.s("mGrafRange1"), l.s("mGrafRange2"), l.s("mGrafRange3")};
		char[] interItemsTypes = {'R','R', 'R'};
		boolean[] interItemsStates = {!grafica.isMaxYInt(), grafica.isMaxYInt(), grafica.isRangeSetted()};
		Add.subMenu(menuGrafica, l.s("mGrafRange"), l.s("mGrafRangeTTT"),
				graficaAL, 'I',
				intervaloSubItems, interItemsStates, interItemsTypes);
		String[] gridSubItems = {l.s("mGrafDiv1"), l.s("mGrafDiv2")};
		char[] gridItemsTypes = {'C', 'C'};
		boolean[] gridItemsStates = {grafica.isDivPrin(), grafica.isDivSec()};
		Add.subMenu(menuGrafica, l.s("mGrafDiv"), l.s("mGrafDivTTT"),
				graficaAL, 'D', gridSubItems, gridItemsStates, gridItemsTypes);
		String[] etiqSubItems = {l.s("mGrafEje1"), l.s("mGrafEje2")};
		char[] etiqItemsTypes = {'R', 'R'};
		boolean[] etiqItemsStates = {grafica.isEtiquetas(), !grafica.isEtiquetas()};
		Add.subMenu(menuGrafica, l.s("mGrafEje"), l.s("mGrafEjeTTT"),
				graficaAL, 'E', etiqSubItems, etiqItemsStates, etiqItemsTypes);
		barraMenu.add(menuGrafica);
		
		//Ayuda
		JMenu menuAyuda = new JMenu(l.s("mHelp"));
		menuAyuda.setMnemonic('y');
		Add.menuItem(menuAyuda, l.s("mHelpCont"), l.s("mHelpContTTT"),
				menuAyudaAL, 'C');
		menuAyuda.addSeparator();
		Add.menuItem(menuAyuda, l.s("mHelpAbout"), l.s("mHelpAboutTTT"),
				menuAyudaAL, 'A');
		barraMenu.add(menuAyuda);
		
		mainWindow.setJMenuBar(barraMenu);
		
		mainWindow.add(panelGrid);
		mainWindow.addComponentListener(ca);
		
		mainWindow.setVisible(true);
	}
	
	/** Inicializa los listeners. */
	private void initListeners(){
		il = new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent ie) {
				//TODO yet have no idea....
				ListIterator<JCheckBox> i = null;
				
				if(ie.getStateChange() == ItemEvent.SELECTED){
					for(i = funcionJRB.listIterator();i.hasNext();){
						if(ie.getSource().equals(i.next())){
							funcionJRB.get(i.previousIndex()).setBackground(
									new Color(200, 200, 200));
							System.out.println(i.previousIndex());
						}
					}
				}
			
				if(ie.getStateChange() == ItemEvent.DESELECTED){
					for(i = funcionJRB.listIterator();i.hasNext();){
						if(ie.getSource().equals(i.next())){
							funcionJRB.get(i.previousIndex()).setBackground(
									new Color(238, 238, 238));
							System.out.println(i.previousIndex());
						}
					}
				}
			}
		};
		
		añadirFuncionAL = new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				if(listaFunciones.size()<5){
					Funcion tempF = M.funcionRandom();
					new EditaFuncionDialog(mainWindow,
									toolBarOpciones, grafica,
									listaFunciones.size(), tempF,
									listaFunciones, funcionJRB, colores, il,
									EditaFuncionDialog.AÑADIR, l);
				}else{
					JOptionPane.showMessageDialog(toolBarOpciones,
							l.s("addMDialog1")+"\n"+l.s("addMDialog2"),
							l.s("MDialogTitle")+listaFunciones.size(),
							JOptionPane.WARNING_MESSAGE);
				}
			}
		};
		
		editarFuncionAL = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				boolean selected = false;
				for(ListIterator<JCheckBox> i = funcionJRB.listIterator();
						i.hasNext();){
					JCheckBox tempjcb = i.next();
					Funcion tempf = listaFunciones.get(i.previousIndex());
					if(tempjcb.isSelected()){
						selected = true;
						new EditaFuncionDialog(mainWindow,
								toolBarOpciones, grafica, i.previousIndex(),
								tempf,
								listaFunciones, funcionJRB, colores, il,
								EditaFuncionDialog.EDITAR, l);
						
					}
				}
				if(!selected){
					JOptionPane.showMessageDialog(mainWindow,
							l.s("editMDialog"), l.s("editMDialogTitle"),
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		};
		
		quitarFuncionAL = new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				for(ListIterator<JCheckBox> i = funcionJRB.listIterator();
						i.hasNext();){
					if(i.next().isSelected()){
						if(listaFunciones.size()>1){
							listaFunciones.remove(i.previousIndex());
							colores.remove(i.previousIndex());
							toolBarOpciones.remove(funcionJRB.get(i.previousIndex()));
							i.remove();	
						}else{
							JOptionPane.showMessageDialog(toolBarOpciones,
									l.s("remMDialog1")+"\n"+l.s("remMDialog2"),
									l.s("MDialogTitle")+listaFunciones.size(),
									JOptionPane.WARNING_MESSAGE);
						}
					}
				}
				
				toolBarOpciones.repaint();
				toolBarOpciones.validate();
				grafica.actualizaLista(listaFunciones, colores);
				grafica.repaint();
			}
		};
		
		graficaAL = new ActionListener(){
			//TODO uh?
			public void actionPerformed(ActionEvent e){
				AbstractButton ab = (AbstractButton) e.getSource();
				String strObj = ab.getText();
				BigDecimal r[] = {grafica.getMinY(), grafica.getMaxY()};
				if(strObj.equals(l.s("mGrafRange1"))){
					grafica.actualizaMaximos(false);
					grafica.setRange(false, r);
				}else if(strObj.equals(l.s("mGrafRange2"))){
					grafica.actualizaMaximos(true);
					grafica.setRange(false, r);
				}else if(strObj.equals(l.s("mGrafRange3"))){//TODO uh?
					EditaIntervaloDialog eiyd = new EditaIntervaloDialog(
							mainWindow, listaFunciones, grafica, 'Y');
					eiyd.getIntervalo();//TODO uh?
				}else if(strObj.equals(l.s("mGrafDiv1"))){
					if(ab.isSelected()){
						grafica.dibujaDivPrin(true);
					}else{
						grafica.dibujaDivPrin(false);
					}
				}else if(strObj.equals(l.s("mGrafDiv2"))){
					if(ab.isSelected()){
						grafica.dibujaDivSec(true);
					}else{
						grafica.dibujaDivSec(false);
					}
				}else if(strObj.equals(l.s("mGrafInt"))){
					EditaIntervaloDialog eixd =new EditaIntervaloDialog(
							mainWindow, listaFunciones, grafica, 'X');
					intervaloGlobal = eixd.getIntervalo();
					pasoGlobal = eixd.getPaso();
				}else if(strObj.equals(l.s("mGrafEje1"))){
					grafica.dibujaEtiquetas(true);
				}else if(strObj.equals(l.s("mGrafEje2"))){
					grafica.dibujaEtiquetas(false);
				}
				
			}
		};
		
		guardarAL = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// TODO Saving stuff
				AbstractButton ab = (AbstractButton) e.getSource();
				String strObj = ab.getText();
				
				JGrafica jg = new JGrafica(listaFunciones, colores,
						grafica.getWidth(), grafica.getHeight(),
						grafica.isDivPrin(), grafica.isDivSec(),
						grafica.isMaxYInt(), grafica.isEtiquetas(),
						grafica.isRangeSetted());
				
				if(strObj.equals(l.s("mArchSave2"))){
					jg.setSize(500, 500);
				}else if(strObj.equals(l.s("mArchSave3"))){
					jg.setSize(1000, 1000);
				}else if(strObj.equals(l.s("mArchSave4"))){
					EditaDimensionesDialog edd = new EditaDimensionesDialog(
							mainWindow, grafica);
					jg.setSize(edd.getDimension());
				}else{}
				
				jg.repaint();
				
				try{
					File f = new File("gráfica.png");
					
					JFileChooser jfc = new JFileChooser();
					jfc.setSelectedFile(f);
					File deskDir = new File(System.getProperty("user.home"));
					jfc.setCurrentDirectory(deskDir);
					jfc.setMultiSelectionEnabled(false);
					jfc.setFileFilter(new FileNameExtensionFilter("Imagenes", "png"));
					jfc.showSaveDialog(mainWindow);
					StringTokenizer st = new StringTokenizer(
							jfc.getSelectedFile().toString(),".");
					st.nextToken();
					if(!st.hasMoreTokens()){
						f = new File(jfc.getSelectedFile()+".png");
					}else{
						f = jfc.getSelectedFile();
					}
					ImageIO.write(jg.getGrafica(), "png", f);
				}catch (IOException ioe){
					O.pln("Error de escritura");
				}catch (NullPointerException npe){
					O.pln("Archivo no seleccionado");
				}catch (Exception e2){
					O.pln("Error deseconocido");
					e2.printStackTrace();
				}
			}
		};
		
		cambiaColorAL = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				boolean selected = false;
				int p = 0;
				for(ListIterator<JCheckBox> i = funcionJRB.listIterator();
						i.hasNext();){
					JCheckBox tempjcb = i.next();
					p = i.previousIndex();
					if(tempjcb.isSelected()){
						selected = true;
						colores.set(p, JColorChooser.showDialog(mainWindow,
								tempjcb.getText(),
								colores.get(p)));
						tempjcb.setForeground(colores.get(p));
						grafica.repaint();
						}
				}
				if(!selected){
					JOptionPane.showMessageDialog(mainWindow,
							l.s("editMDialog"), l.s("editMDialogTitle"),
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		};
		
		salirAL = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// TODO You might wanna do something before exiting
				mainWindow.dispose();
			}
		};
		
		menuAyudaAL = new ActionListener(){
			//TODO Help menu not finished
			public void actionPerformed(ActionEvent e) {
				JMenuItem item = (JMenuItem) e.getSource();
				if(item.getText().equals(l.s("mHelpCont"))){
					JOptionPane.showMessageDialog(mainWindow,
							l.s("mHelpCont"),
							l.s("mHelpCont"),
							JOptionPane.INFORMATION_MESSAGE);
				}else if(item.getText().equals(l.s("mHelpAbout"))){
					JOptionPane.showMessageDialog(mainWindow,
							l.s("mHelpAbout1")+ver+"\n"+l.s("mHelpAbout2"),
							l.s("mHelpAbout"), JOptionPane.INFORMATION_MESSAGE);
				}
			}
		};
		
		ca = new ComponentAdapter(){
			@Override
			public void componentResized(ComponentEvent ce) {
				grafica.setSize(panelGrid.getSize());
			}
			@Override
			public void componentMoved(ComponentEvent e) {}
			@Override
			public void componentShown(ComponentEvent e) {}
			@Override
			public void componentHidden(ComponentEvent e) {}
		};
	}
	
	/** Termina el programa forzado. */
	public void salir(){ mainWindow.dispose();}
	
}
