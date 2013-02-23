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
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import resources.CoordenadasGraficasMIA;
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
	
	private Value X;
	private Value Y;
	
	private class Value {
		BigDecimal max;
		BigDecimal min;
		private Value(BigDecimal max, BigDecimal min){
			this.max = max;
			this.min = min;
		}
	}
	
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
	 * @param dim Dimensión de la gráfica
	 * 
	 */
	public JGrafica(Dimension dim){
		setSize(dim);
		
		updateCoordsDim();
		
		cgMIA = new CoordenadasGraficasMIA(this);
		addMouseListener(cgMIA);
		addMouseMotionListener(cgMIA);
		
	}
	
	/**
	 * 
	 * @param g el contexto gráfico del componente en el que se dibuja
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		
		ArrayList<Point> aLpP = new ArrayList<Point>();
		for(int i=0;i<51;i++){
			aLpP.add((i%5==0)? null : new Point((2*i*i)/9,9*i));
		}
		O.pln(aLpP);
		g2d.draw(polylineShape(aLpP));
		updateCoordsDim();
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
				O.pln(currentPoint+""+iterator.previousIndex());
				isPointFirst = true;
			}else{
				O.pln(currentPoint.toString().substring(14)+""+iterator.previousIndex());
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
		JGrafica jG = new JGrafica(jsJF.getSize());
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
		}
	}

}
