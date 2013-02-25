/**
 * 
 */
package grafica;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import math.Big;

import funciones.Funcion;

import resources.BigDecimalCoord;
import resources.Constantes.FuncionTrig;
import resources.CoordenadasGraficasMIA;
import resources.CustomException;
import resources.Interval;
import stream.O;

/**
 * @author Jedabero
 *
 */
public class JGrafica extends JPanel {

	/** */
	private static final long serialVersionUID = -2267792839289943254L;
	
	private CoordenadasGraficasMIA cgMIA;	//Custom MouseInputAdapter
	
	private Point gCoords; //Inside graphic starting coordinates.
	private Dimension gDim;//Inside graphic dimensions.
	
	private BigDecimal step;
	private int divs;
	private Interval X;
	private Interval Y;
	
	private ArrayList<BigDecimalCoord[]> alfCoords =
			new ArrayList<BigDecimalCoord[]>();
	
	/**
	 * @return the gCoords
	 */
	public Point getgCoords() {
		return gCoords;
	}

	/**
	 * @return the gDim
	 */
	public Dimension getgDim() {
		return gDim;
	}

	/**
	 * @return la imagen del panel
	 */
	public BufferedImage getGrafica(){
		BufferedImage bi = new BufferedImage(getWidth(), getHeight(),
				BufferedImage.TYPE_INT_RGB);
		Graphics2D gbi = bi.createGraphics();
		paintComponent(gbi);
		return bi;
	}
	
	/**
	 * Crea una Gráfica
	 * @param arrLF lista de funciones
	 * @param dim Dimensión de la gráfica
	 * 
	 */
	public JGrafica(ArrayList<Funcion> arrLF, Dimension dim){
		setSize(dim);
		
		step = BigDecimal.valueOf(0.01);//TODO arreglos respecto al paso
		updateCoordsDim();
		calculos(arrLF);
		
		cgMIA = new CoordenadasGraficasMIA(this);
		addMouseListener(cgMIA);
		addMouseMotionListener(cgMIA);
		
	}
	
	private void calculos(ArrayList<Funcion> arrLF){
		X = new Interval(BigDecimal.ONE.negate(), BigDecimal.ONE);//init Xinterval
		BigDecimal d;
		d = X.getLength().divide(step, RoundingMode.HALF_UP);
		divs = d.intValue() + 1;//divisions
		
		//Arrays to stores the max and min values of Y
		BigDecimal[] maxy = new BigDecimal[arrLF.size()];
		BigDecimal[] miny = new BigDecimal[arrLF.size()];
		
		ListIterator<Funcion> lif;//ITERATOR
		for (lif = arrLF.listIterator(); lif.hasNext();) {
			Funcion f = lif.next();//current function
			BigDecimal x;//the x and y bdpoints to set
			BigDecimal[] y = new BigDecimal[divs];
			BigDecimalCoord[] fCoords = new BigDecimalCoord[divs];//the bdcoords to set
			for(int i=0;i<fCoords.length;i++){
				x = X.min().add(step.multiply(BigDecimal.valueOf(i)));//x value
				if(x.compareTo(X.max())==1) x = X.max();
				y[i] = f.valorImagen(x);// y value
				fCoords[i] = new BigDecimalCoord(x, y[i]);//setting bdcoords
			}
			alfCoords.add(fCoords);
			
			maxy[lif.previousIndex()] = Big.max(y);
			miny[lif.previousIndex()] = Big.min(y);
		}
		Y = new Interval(Big.min(miny), Big.max(maxy));
		O.pln("x="+X);O.pln("y="+Y);
	}
	
	/**
	 * 
	 * @param g el contexto gráfico del componente en el que se dibuja
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		
		updateCoordsDim();
		
		ArrayList<Point> aLpP = new ArrayList<Point>();
		for(int i=0;i<51;i++){
			Point p = new Point((2*i*i)/9,9*i);
			p.translate(gCoords.x, gCoords.y);
			aLpP.add((i%5==0)? null : p);
		}
		g2d.draw(polylineShape(aLpP));
		
		dibujarDivisiones(g2d, true, true);
		dibujarEjes(g2d);
		dibujarEtiquetas(true);
		
	}
	
	private Shape polylineShape(ArrayList<Point> alP){
		Path2D p2d = new Path2D.Double();
		ListIterator<Point> iterator;
		boolean isPointFirst = true;
		
		for (iterator = alP.listIterator(); iterator.hasNext();) {
			Point currentPoint = iterator.next();
			boolean isPointNull = (currentPoint==null);
			
			if(isPointNull){
				O.pln(iterator.previousIndex()+""+currentPoint);
				isPointFirst = true;
			}else{
				O.pln(iterator.previousIndex()+currentPoint.toString().substring(14));
				if(isPointFirst){
					p2d.moveTo(currentPoint.x, currentPoint.y);
					isPointFirst = false;
				}else{
					p2d.lineTo(currentPoint.x, currentPoint.y);
				}
			}
			
		}
		
		return p2d;
	}
	
	private void dibujarDivisiones(Graphics2D g2D, boolean divP, boolean divSec) {
		// TODO Divisiones
		
	}
	
	private void dibujarEjes(Graphics2D g2D) {
		g2D.setColor(Color.BLACK);
		g2D.drawRect(gCoords.x, gCoords.y, gDim.width, gDim.height);
		//TODO Ejes
	}

	private void dibujarEtiquetas(boolean et) {
		// TODO Etiquetas
		
	}
	
	/**
	 * Actualiza las dimensiones de la gráfica
	 */
	public void updateCoordsDim(){
		int w = this.getWidth(); int h = this.getHeight();
		gCoords = new Point((int)(0.05*w), (int)(0.05*h));
		gDim = new Dimension((int)(0.9*w), (int)(0.9*h));
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//DEL this method
		javax.swing.JFrame jsJF = new javax.swing.JFrame("graphic TEST!");
		jsJF.setSize(500, 500);
		jsJF.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		BigDecimal[] coefs = {BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN};
		ArrayList<Funcion> alf = new ArrayList<Funcion>();
		try {
			alf.add(Funcion.polinomio(2, coefs));
			alf.add(Funcion.trigonometrica(FuncionTrig.SIN, coefs[0], coefs[1]));
		} catch (CustomException e) {
			e.printStackTrace();
		}
		JGrafica jG = new JGrafica(alf, jsJF.getSize());
		jsJF.getContentPane().add(jG);
		jsJF.setVisible(true);
		
		try{
			Thread.sleep(3000);
			File f = new File("gráfica.png");
			
			JFileChooser jfc = new JFileChooser();
			jfc.setSelectedFile(f);
			File deskDir = new File(System.getProperty("user.dir"));
			jfc.setCurrentDirectory(deskDir);
			jfc.setMultiSelectionEnabled(false);
			jfc.setFileFilter(new FileNameExtensionFilter("Imagenes",
					"png"));
			jfc.showSaveDialog(jsJF);
			StringTokenizer st = new StringTokenizer(
					jfc.getSelectedFile().toString(),".");
			st.nextToken();
			if(!st.hasMoreTokens()){
				f = new File(jfc.getSelectedFile()+".png");
			}else{
				f = jfc.getSelectedFile();
			}
			ImageIO.write(jG.getGrafica(), "png", f);
		}catch (IOException ioe){
			O.pln("Error de escritura");
		}catch (NullPointerException npe){
			O.pln("Archivo no seleccionado");
		}catch (Exception e2){
			O.pln("Error deseconocido");
			e2.printStackTrace();
		}
	}

}
