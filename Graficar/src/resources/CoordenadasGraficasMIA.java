/**
 * 
 */
package resources;

import grafica.JGrafica;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.event.MouseInputAdapter;

/**
 * @author Jedabero
 * @since 0.4
 */
public class CoordenadasGraficasMIA extends MouseInputAdapter{
	
	private JGrafica jGra;
	private Interval X;
	private	Interval Y;
	
	/**
	 * @param jg 
	 * @param x 
	 * @param y 
	 * 
	 */
	public CoordenadasGraficasMIA(JGrafica jg, Interval x, Interval y) {
		this.jGra = jg;
		this.X = x;
		this.Y = y;
	}
	
	/**
	 * @param x
	 * @param y
	 */
	public void updateIntervals(Interval x, Interval y){
		this.X = x;
		this.Y = y;
	}
	
	public void mousePressed(MouseEvent me) { }

	public void mouseDragged(MouseEvent me) { }

	public void mouseReleased(MouseEvent me) { }
	
	public void mouseWheelMoved(MouseWheelEvent mwe) {
		int whlrot = mwe.getWheelRotation();
		BigDecimal bdwhlrot = BigDecimal.valueOf(whlrot).divide(BigDecimal.TEN);
		X.max = X.max.add(bdwhlrot);
		X.min = X.min.subtract(bdwhlrot);
		Y.max = Y.max.add(bdwhlrot);
		Y.min = Y.min.subtract(bdwhlrot);
		jGra.updateIntervals(X, Y);
	}
	
	public void mouseMoved(MouseEvent me){
		int graW = jGra.getgDim().width;
		int graH = jGra.getgDim().height;
		BigDecimal gW = BigDecimal.valueOf(graW);
		BigDecimal gH = BigDecimal.valueOf(graH);
		Point gCoords = jGra.getgCoords();
		
		Point mP = me.getPoint();
		int x = mP.x;
		int y = mP.y;
		BigDecimal x1;
		BigDecimal y1;
		
		
		boolean xInBounds = (x>=gCoords.x)&&(x<=(gCoords.x+graW));
		boolean yInBounds = (y>=gCoords.y)&&(y<=(gCoords.y+graH));
		if(xInBounds&&yInBounds){//TODO Coordinates
			x -= gCoords.x;
			x1 = BigDecimal.valueOf(x).divide(gW, 10, RoundingMode.HALF_UP);
			x1 = X.min().add(X.length().multiply(x1));
			x1 = x1.setScale(3, RoundingMode.HALF_UP);
			
			y -= gCoords.y;
			y1 = BigDecimal.valueOf(y).divide(gH, 10, RoundingMode.HALF_UP);
			y1 = Y.max().subtract(Y.length().multiply(y1));
			y1 = y1.setScale(3, RoundingMode.HALF_UP);
			
			
			BigDecimalCoord coords = new BigDecimalCoord(x1, y1);
			
			jGra.setToolTipText(""+coords);
		}
		
	}
	
}
