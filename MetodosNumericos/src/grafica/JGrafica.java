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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.JPanel;

import resources.O;
import resources.math.Big;
import resources.math.Interval;
import funciones.Funcion;

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
	
	private int yAxis;
	private int xAxis;
	
	private BigDecimal step;//Step to separate the values of x
	private int numeroPuntos = 201;	//Number of max divisions
	private Interval X;		//The x Interval
	private Interval Y;		//The y Interval
	private Interval integralX;//TODO The x interval of integration
	
	//The ArrayList that contains the arrays of coordinates of the functions.
	private ArrayList<BigDecimalPoint[]> alfCoords;
	
	private ArrayList<Funcion> funcionList;	//The list of functions
	
	private ArrayList<Color> colorList;	//The list of colours
	
	private boolean divPrin = true;	//paint main divisions?
	private boolean divSec = false;	//paint secondary divisions?
	private boolean etiquetas = true;	//paint axis numbers?
	private boolean rangeY = false;	//TODO create an interval for this or smth
	
	private boolean mostrarAreaIntegral = false;
	
	/**
	 * @return regresa si las divisiones principales están dibujadas
	 */
	public boolean isDivPrin(){return divPrin;}
	/**
	 * @return regresa si las divisiones secundarias están dibujadas
	 */
	public boolean isDivSec(){return divSec;}
	/**
	 * @return regresa si está dibujado en un rango especifico
	 */
	public boolean isYranged(){return rangeY;}
	/**
	 * @return regresa si las etiquetas de eje están dibujadas
	 */
	public boolean isEtiquetas(){return etiquetas;}
	
	/**
	 * @return the rangeY
	 */
	public boolean isRangeY() {
		return rangeY;
	}
	
	/**
	 * @return the mostrarAreaIntegral
	 */
	public boolean isMostrarAreaIntegral() {
		return mostrarAreaIntegral;
	}
	
	/**
	 * @param rangeY the rangeY to set
	 */
	public void setRangeY(boolean rangeY) {
		this.rangeY = rangeY;
	}
	
	/**
	 * @param integralX the integralX to set
	 */
	public void setIntegralX(Interval integralX) {
		this.integralX = integralX;
	}
	
	/**
	 * @param mostrarAreaIntegral the mostrarAreaIntegral to set
	 */
	public void setMostrarAreaIntegral(boolean mostrarAreaIntegral) {
		this.mostrarAreaIntegral = mostrarAreaIntegral;
	}
	
	/**
	 * @return the gCoords
	 */
	public Point getgCoords() {return gCoords;}

	/**
	 * @return the gDim
	 */
	public Dimension getgDim() {return gDim;}
	
	/**
	 * @return el intervalo X
	 */
	public Interval getXinterval(){return X;}
	/**
	 * @return el intervalo Y
	 */
	public Interval getYinterval(){return Y;}
	
	/**
	 * @return el paso
	 */
	public BigDecimal getStep(){return step;}
	
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
	
	private void calculos(){
		alfCoords = new ArrayList<BigDecimalPoint[]>();
		
		BigDecimal numP = new BigDecimal(Integer.toString(numeroPuntos-1));
		step = X.length().divide(numP, 10, RoundingMode.HALF_UP).stripTrailingZeros();
		O.pln("Paso: "+step);
		
		//Arrays to stores the max and min values of Y
		BigDecimal[] maxy = new BigDecimal[funcionList.size()];
		BigDecimal[] miny = new BigDecimal[funcionList.size()];
		
		ListIterator<Funcion> lif;//ITERATOR
		for (lif = funcionList.listIterator(); lif.hasNext();) {
			Funcion f = lif.next();//current function
			int index = lif.previousIndex();
			BigDecimal x;//the x and y bdpoints to set
			BigDecimal[] y = new BigDecimal[numeroPuntos];
			BigDecimalPoint[] fCoords = new BigDecimalPoint[numeroPuntos];//the bdcoords to set
			for(int i=0;i<fCoords.length;i++){
				x = X.min().add(step.multiply(BigDecimal.valueOf(i)));//x value
				if(x.compareTo(X.max())==1) x = X.max();
				y[i] = f.valorImagen(x);// y value
				fCoords[i] = new BigDecimalPoint(x, y[i]);//setting bdcoords O.pln(fCoords[i]);
			}
			
			alfCoords.add(index, fCoords);
			
			maxy[index] = Big.max(y);
			miny[index] = Big.min(y);
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
		
		if(rangeY){//TODO range setting
			
			minY = Y.min();
			maxY = Y.max();
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
		g2d.setStroke(new BasicStroke(2));
		ListIterator<BigDecimalPoint[]> libdc;//Coordinates iterator
		for (libdc = alfCoords.listIterator(); libdc.hasNext();) {
			BigDecimalPoint[] bdcArr = libdc.next();//current bdCoord array
			int index = libdc.previousIndex();
			g2d.setColor(colorList.get(index));
			ArrayList<Point> alp = changeCoordToPoint(bdcArr);
			g2d.draw(polylineShape(alp));
			if(mostrarAreaIntegral){
				ArrayList<Point> alpai = changeCoordToPointAreaIntegral(bdcArr);
				g2d.draw(polylineShapeAreaIntegral(alpai));
			}
			
		}
		
	}
	
	private ArrayList<Point> changeCoordToPoint(BigDecimalPoint[] bdcArr){
		ArrayList<Point> aLpP = new ArrayList<Point>();
		for(int i=0;i<bdcArr.length;i++){
			BigDecimalPoint cord = bdcArr[i];
			BigDecimal xnum = cord.x().subtract(X.min());
			BigDecimal xdiv = xnum.divide(X.length(), 5, RoundingMode.HALF_UP);
			int x = (int)(gDim.width*(xdiv.doubleValue()));
			BigDecimal ynum = cord.y().subtract(Y.min());
			BigDecimal ydiv = ynum.divide(Y.length(), 5, RoundingMode.HALF_UP);
			int y = (int)(gDim.height*(1-ydiv.doubleValue()));
			
			
			Point p = new Point(x,y);
			p.translate(gCoords.x, gCoords.y);
			
			boolean pOutOfScope = p.y<gCoords.y||p.y>gCoords.y+gDim.height;
			if(pOutOfScope){
				p = null;
			}
			
			aLpP.add(p);
		}
		return aLpP;
	}
	
	private ArrayList<Point> changeCoordToPointAreaIntegral(BigDecimalPoint[] bdcArr){
		ArrayList<Point> aLpP = new ArrayList<Point>();
		if(integralX==null){
			integralX = X;
		}
		
		for(int i=0;i<bdcArr.length;i++){
			BigDecimalPoint cord = bdcArr[i];
			if((cord.x.compareTo(integralX.min())>=0)&&(cord.x.compareTo(integralX.max())<=0)){
				BigDecimal xnum = cord.x().subtract(X.min());
				BigDecimal xdiv = xnum.divide(X.length(), 5, RoundingMode.HALF_UP);
				int x = (int)(gDim.width*(xdiv.doubleValue()));
				BigDecimal ynum = cord.y().subtract(Y.min());
				BigDecimal ydiv = ynum.divide(Y.length(), 5, RoundingMode.HALF_UP);
				int y = (int)(gDim.height*(1-ydiv.doubleValue()));
				
				Point p = new Point(x,y);
				p.translate(gCoords.x, gCoords.y);
				
				if(p.y<gCoords.y){
					p.y = gCoords.y;
				}else if(p.y>gCoords.y+gDim.height){
					p.y = gCoords.y+gDim.height;
				}
				
				aLpP.add(p);
			}
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
	
	private Shape polylineShapeAreaIntegral(ArrayList<Point> alP){
		Path2D p2d = new Path2D.Double();
		ListIterator<Point> iterator;
		boolean isPointFirst = true;
		Point prevPoint = null;
		for (iterator = alP.listIterator(); iterator.hasNext();) {
			Point currentPoint = iterator.next();
			
			if(currentPoint==null){
				isPointFirst = true;
			}else{
				if(isPointFirst){
					p2d.moveTo(currentPoint.x, currentPoint.y);
					isPointFirst = false;
				}else{
					p2d.lineTo(prevPoint.x, xAxis);
					p2d.lineTo(currentPoint.x, xAxis);
					p2d.lineTo(currentPoint.x, currentPoint.y);
				}
			}
			
			prevPoint = currentPoint;
		}
		
		return p2d;
	}
	
	private void dibujarEjesDivisionesYEtiquetas(Graphics2D g2D, boolean divP,
			boolean divSec, boolean et) {
		FontMetrics fm = g2D.getFontMetrics();
		BigDecimal bdxf = X.min().negate().divide(X.length(), 5, RoundingMode.HALF_EVEN);
		yAxis = (int)(gDim.width*(bdxf.doubleValue())) + gCoords.x;
		yAxis = (yAxis<gCoords.x)?gCoords.x:yAxis;
		yAxis = (yAxis>gCoords.x+gDim.width)?gCoords.x+gDim.width:yAxis;
		BigDecimal bdyf = Y.min().negate().divide(Y.length(), 5, RoundingMode.HALF_EVEN);
		xAxis = (int)(gDim.height*(1-bdyf.doubleValue())) + gCoords.y;
		xAxis = (xAxis<gCoords.y)?gCoords.y:xAxis;
		xAxis = (xAxis>gCoords.y+gDim.height)?gCoords.y+gDim.height:xAxis;
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
			
			String xS = X.min().add(x1).setScale(3, RoundingMode.HALF_EVEN).stripTrailingZeros().toPlainString();
			String yS = Y.min().add(y1).setScale(3, RoundingMode.HALF_EVEN).stripTrailingZeros().toPlainString();
			
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
			
			if((i%p==0)){
				if(divPrin){
					g2D.setColor(new Color(150, 150, 150));
					g2D.drawLine(gCoords.x+x, gCoords.y, gCoords.x+x, gCoords.y+gDim.height);
					g2D.drawLine(gCoords.x, gCoords.y+y, gCoords.x+gDim.width, gCoords.y+y);
				}
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
	 * 
	 */
	public void updateGrafica(){
		updateCoordsDim();
		calculos();
		cgMIA.updateIntervals(X, Y);
		repaint();
	}
	
	/**
	 * @param x 
	 * @param y 
	 * 
	 */
	public void updateIntervals(Interval x, Interval y){
		this.X = x;
		this.Y = y;
		updateGrafica();
	}
	
	/**
	 * Método que determina si las divisiones principales se dibujan o no.
	 * @param pg
	 */
	public void dibujaDivPrin(boolean pg){
		this.divPrin = pg;
		repaint();
	}
	
	/**
	 * Método que determina si las divisiones secundarias se dibujan o no.
	 * @param sg
	 */
	public void dibujaDivSec(boolean sg){
		this.divSec = sg;
		repaint();
	}
	
	/**
	 * Método que determina si las etiquetas de eje se dibujan o no.
	 * @param et
	 */
	public void dibujaEtiquetas(boolean et){
		this.etiquetas = et;
		repaint();
	}
	
	/**
	 * Método que actualiza las funciones o los colores respectivos.
	 * @param alf 
	 * @param alc 
	 */
	public void actualizaLista(ArrayList<Funcion> alf, ArrayList<Color> alc){
		this.funcionList = alf;
		this.colorList = alc;
	}
	
	/**
	 * Crea una Gráfica
	 * @param funcionList lista de funciones
	 * @param dim Dimensión de la gráfica
	 * @param colorList lista de colores
	 * @param xInterval Intervalo de los valores de x
	 * @param yInterval Intervalo de los valores de y iniciales
	 * 
	 */
	public JGrafica(ArrayList<Funcion> funcionList, ArrayList<Color> colorList,
			Dimension dim, Interval xInterval, Interval yInterval){
		setSize(dim);
		this.funcionList = funcionList;
		this.colorList = colorList;
		this.X = xInterval;
		this.Y = yInterval;
		step = BigDecimal.valueOf(0.01);//TODO arreglos respecto al paso
		updateCoordsDim();
		calculos();
		
		cgMIA = new CoordenadasGraficasMIA(this, getXinterval(), getYinterval());
		addMouseListener(cgMIA);
		addMouseWheelListener(cgMIA);
		addMouseMotionListener(cgMIA);
		
	}
	
}
