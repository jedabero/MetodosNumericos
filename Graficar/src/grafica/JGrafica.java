/**
 * 
 */
package grafica;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
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
import funciones.Termino;

import resources.BigDecimalCoord;
import resources.Constantes.FuncionTrig;
import resources.CoordenadasGraficasMIA;
import resources.CustomException;
import resources.Interval;
import stream.O;

/**
 * Cualquier instancia de esta clase dibujará todas las funciones que le sean
 * pasadas.
 * @author Jedabero
 * @since 0.4
 */
public class JGrafica extends JPanel {
	
	/** Degenarated serialVersionUID for this child of JPanel */
	private static final long serialVersionUID = -2267792839289943254L;
	
	private CoordenadasGraficasMIA cgMIA;	//Custom MouseInputAdapter
	
	private Point gCoords;	//Inside graphic starting coordinates.
	private Dimension gDim;	//Inside graphic dimensions.
	
	private BigDecimal step;//Step to separate the values of x
	private int numeroPuntos;		//number of divisions TODO
	private Interval X;		//The x Interval
	private Interval Y;		//The y Interval
	//The ArrayList that contains the arrays of coordenates of the functions.
	private ArrayList<BigDecimalCoord[]> alfCoords = new ArrayList<BigDecimalCoord[]>();
	
	private ArrayList<Funcion> funcionList;	//The list of functions
	
	private ArrayList<Color> colorList;	//The list of colours
	
	private boolean divPrin = true;	//paint main divisions?
	private boolean divSec = false;	//paint secondary divisions?
	private boolean etiquetas = true;	//paint axis numbers?
	
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
	 * @return el intervalo X
	 */
	public Interval getXinterval(){
		return X;
	}
	/**
	 * @return el intervalo Y
	 */
	public Interval getYinterval(){
		return Y;
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
	 * @param funcionList lista de funciones
	 * @param dim Dimensión de la gráfica
	 * @param colorList lista de colores
	 * @param xInterval Intervalo de los valores de x
	 * 
	 */
	public JGrafica(ArrayList<Funcion> funcionList, ArrayList<Color> colorList,
			Dimension dim, Interval xInterval){
		setSize(dim);
		this.funcionList = funcionList;
		this.colorList = colorList;
		this.X = xInterval;
		step = BigDecimal.valueOf(0.01);//TODO arreglos respecto al paso
		updateCoordsDim();
		calculos();
		
		cgMIA = new CoordenadasGraficasMIA(this, getXinterval(), getYinterval());
		addMouseListener(cgMIA);
		addMouseMotionListener(cgMIA);
		
	}
	
	private void calculos(){
		
		BigDecimal d;
		d = X.length().divide(step, RoundingMode.HALF_UP);
		numeroPuntos = d.intValue() + 1;//divisions
		
		//Arrays to stores the max and min values of Y
		BigDecimal[] maxy = new BigDecimal[funcionList.size()];
		BigDecimal[] miny = new BigDecimal[funcionList.size()];
		
		ListIterator<Funcion> lif;//ITERATOR
		for (lif = funcionList.listIterator(); lif.hasNext();) {
			Funcion f = lif.next();//current function
			BigDecimal x;//the x and y bdpoints to set
			BigDecimal[] y = new BigDecimal[numeroPuntos];
			BigDecimalCoord[] fCoords = new BigDecimalCoord[numeroPuntos];//the bdcoords to set
			for(int i=0;i<fCoords.length;i++){
				x = X.min().add(step.multiply(BigDecimal.valueOf(i)));//x value
				if(x.compareTo(X.max())==1) x = X.max();
				y[i] = f.valorImagen(x);// y value
				fCoords[i] = new BigDecimalCoord(x, y[i]);//setting bdcoords O.pln(fCoords[i]);
			}
			alfCoords.add(fCoords);
			
			maxy[lif.previousIndex()] = Big.max(y);
			miny[lif.previousIndex()] = Big.min(y);
		}
		BigDecimal maxY = Big.max(maxy);
		BigDecimal minY = Big.min(miny);
		
		if(minY.compareTo(maxY)==0){
			int l = minY.signum();
			switch(l){
			case 1:
				maxY = maxY.multiply(BigDecimal.valueOf(2));
				minY = maxY.negate();
				break;
			case -1:
				minY = minY.multiply(BigDecimal.valueOf(2));
				maxY = minY.negate();
				break;
			case 0:
			default:
				maxY = BigDecimal.ONE;
				minY = BigDecimal.ONE.negate();
				break;
			}
		}
		
		Y = new Interval(minY, maxY);
		
	}
	
	/**
	 * 
	 * @param g el contexto gráfico del componente en el que se dibuja
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		
		updateCoordsDim();
		
		dibujarEjesDivisionesYEtiquetas(g2d, divPrin, divSec, etiquetas);
		
		//Draw the functions
		g2d.setStroke(new BasicStroke(3));
		ListIterator<BigDecimalCoord[]> libdc;//Coordinates iterator
		for (libdc = alfCoords.listIterator(); libdc.hasNext();) {
			BigDecimalCoord[] bdcArr = libdc.next();//current bdCoord array
			g2d.setColor(colorList.get(libdc.previousIndex()));
			g2d.draw(polylineShape(changeCoordToPoint(bdcArr)));
		}
		
	}
	
	private ArrayList<Point> changeCoordToPoint(BigDecimalCoord[] bdcArr){
		ArrayList<Point> aLpP = new ArrayList<Point>();
		for(int i=0;i<bdcArr.length;i++){
			BigDecimal xnum = bdcArr[i].x().subtract(X.min());
			BigDecimal xdiv = xnum.divide(X.length(), 5, RoundingMode.HALF_UP);
			int x = (int)(gDim.width*(xdiv.doubleValue()));
			BigDecimal ynum = bdcArr[i].y().subtract(Y.min());
			BigDecimal ydiv = ynum.divide(Y.length(), 5, RoundingMode.HALF_UP);
			int y = (int)(gDim.height*(1-ydiv.doubleValue()));
			Point p = new Point(x,y);
			p.translate(gCoords.x, gCoords.y);
			aLpP.add(p);
		}
		return aLpP;
	}
	
	private Shape polylineShape(ArrayList<Point> alP){
		Path2D p2d = new Path2D.Double();
		ListIterator<Point> iterator;
		boolean isPointFirst = true;
		
		for (iterator = alP.listIterator(); iterator.hasNext();) {
			Point currentPoint = iterator.next();
			
			if(currentPoint==null){
				//O.pln(iterator.previousIndex()+""+currentPoint);
				isPointFirst = true;
			}else{
				//O.pln(iterator.previousIndex()+currentPoint.toString().substring(14));
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
	
	private void dibujarEjesDivisionesYEtiquetas(Graphics2D g2D, boolean divP,
			boolean divSec, boolean et) {
		FontMetrics fm = g2D.getFontMetrics();
		BigDecimal bdxf = X.min().negate().divide(X.length(), 5, RoundingMode.HALF_EVEN);
		int yAxis = (int)(gDim.width*(bdxf.doubleValue())) + gCoords.x;
		BigDecimal bdyf = Y.min().negate().divide(Y.length(), 5, RoundingMode.HALF_EVEN);
		int xAxis = (int)(gDim.height*(1-bdyf.doubleValue())) + gCoords.y;
		
		BigDecimal bdNumPuntos = BigDecimal.valueOf(numeroPuntos-1);
		BigDecimal yStep = Y.length().divide(bdNumPuntos, 5, RoundingMode.HALF_UP);
		for(int i=0;i<numeroPuntos;i++){
			BigDecimal bdi = BigDecimal.valueOf(i);
			
			BigDecimal x1 = bdi.multiply(step);
			BigDecimal xdiv = x1.divide(X.length(), 5, RoundingMode.HALF_UP);
			int x = (int)(gDim.width*(xdiv.doubleValue()));
			
			BigDecimal y1 = bdi.multiply(yStep);
			BigDecimal ydiv = y1.divide(Y.length(), 5, RoundingMode.HALF_UP);
			int y = (int)(gDim.height*(1-ydiv.doubleValue()));
			
			String xS = ""+X.min().add(x1).stripTrailingZeros();
			String yS = ""+Y.min().add(y1).setScale(3, RoundingMode.DOWN).stripTrailingZeros().toPlainString();
			
			//TODO make this shat selectable
			int s = 2;
			int p = 10;
			
			if((i%s==0)&&!(i%p==0)&&divSec){
				g2D.setColor(new Color(200, 200, 200));
				g2D.drawLine(gCoords.x+x, gCoords.y, gCoords.x+x, gCoords.y+gDim.height);
				g2D.drawLine(gCoords.x, gCoords.y+y, gCoords.x+gDim.width, gCoords.y+y);
				if(et){
					g2D.setColor(new Color(200, 0, 0));
					int posxS = gCoords.x+x-(fm.stringWidth(xS)/2);
					g2D.drawString(""+xS, posxS, xAxis);
					int posyS = gCoords.y+y+(fm.stringWidth(yS)/4);
					g2D.drawString(""+yS, yAxis, posyS);
				}
			}
			
			if((i%p==0)&&divP){
				g2D.setColor(new Color(150, 150, 150));
				g2D.drawLine(gCoords.x+x, gCoords.y, gCoords.x+x, gCoords.y+gDim.height);
				g2D.drawLine(gCoords.x, gCoords.y+y, gCoords.x+gDim.width, gCoords.y+y);
				if(et){
					g2D.setColor(new Color(200, 0, 0));
					int posxS = gCoords.x+x-(fm.stringWidth(xS)/2);
					g2D.drawString(""+xS, posxS, xAxis);
					int posyS = gCoords.y+y+(fm.stringWidth(yS)/4);
					g2D.drawString(""+yS, yAxis, posyS);
				}
			}
			
		}
		
		g2D.setColor(Color.BLACK);
		g2D.drawRect(gCoords.x, gCoords.y, gDim.width, gDim.height);
		g2D.drawLine(yAxis, gCoords.y, yAxis, gCoords.y+gDim.height);//Y axis
		g2D.drawLine(gCoords.x, xAxis, gCoords.x+gDim.width, xAxis);
	}
	
	/**
	 * Actualiza las dimensiones de la gráfica
	 */
	public void updateCoordsDim(){
		int w = this.getWidth();
		int h = this.getHeight();
		gCoords = new Point((int)(0.05*w), (int)(0.05*h));
		gDim = new Dimension((int)(0.9*w), (int)(0.9*h));
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//DEL this method
		//init gui
		javax.swing.JFrame jsJF = new javax.swing.JFrame("graphic TEST!");
		jsJF.setSize(500, 500);
		jsJF.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		//init Lista de Funciones
		BigDecimal[] coefs = {BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ONE};
		ArrayList<Funcion> alf = new ArrayList<Funcion>();
		alf.add(new Funcion(Termino.constante(BigDecimal.ONE)));
		alf.add(Funcion.trigonometrica(FuncionTrig.SIN, coefs[0], coefs[1]));
		try {
			alf.add(Funcion.polinomio(2, coefs));
		} catch (CustomException e) {
			e.printStackTrace();
		}
		//init Lista de Colores
		ArrayList<Color> colores = new ArrayList<Color>();
		for (Funcion funcion : alf) {
			O.pln(funcion.getSpecific());
			colores.add(new Color(((int)(25.6*Math.random()))*10,
					((int)(25.6*Math.random()))*10,
					((int)(25.6*Math.random()))*10));
		}
		//init jGrafica
		Interval in = new Interval(BigDecimal.ONE.negate(), BigDecimal.ONE);
		JGrafica jG = new JGrafica(alf, colores, jsJF.getSize(), in);
		
		//other stuff
		jsJF.getContentPane().add(jG);
		jsJF.setVisible(true);
		
		try{
			Thread.sleep(3000);
			File f = new File("gráfica.png");
			
			JFileChooser jfc = new JFileChooser();
			jfc.setSelectedFile(f);
			File deskDir = new File(System.getProperty("user.home"));
			jfc.setCurrentDirectory(deskDir);
			jfc.setMultiSelectionEnabled(false);
			jfc.setFileFilter(new FileNameExtensionFilter("Imagenes", "png"));
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
