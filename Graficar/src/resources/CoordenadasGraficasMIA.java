/**
 * 
 */
package resources;

import grafica.JGrafica;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.event.MouseInputAdapter;

import stream.O;

/**
 * @author Jedabero
 *
 */
public class CoordenadasGraficasMIA extends MouseInputAdapter {
	
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
		// TODO Auto-generated constructor stub
		this.jGra = jg;
		this.X = x;
		this.Y = y;
	}
	
	public void mousePressed(MouseEvent me) { }

	public void mouseDragged(MouseEvent me) { }

	public void mouseReleased(MouseEvent me) { }
	
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
		
		BigDecimalCoord coords = new BigDecimalCoord(gCoords);
		boolean xInBounds = (x>=gCoords.x)&&(x<=(gCoords.x+graW));
		boolean yinBounds = (y>=gCoords.y)&&(y<=(gCoords.y+graH));
		if(xInBounds&&yinBounds){//TODO Coordinates
			x -= gCoords.x;
			x1 = BigDecimal.valueOf(x).divide(gW, 3, RoundingMode.HALF_UP);
			x1 = X.min().add(X.getLength().multiply(x1));
			x1 = x1.setScale(3, RoundingMode.HALF_UP);
			
			y -= gCoords.y;
			y1 = BigDecimal.valueOf(y).divide(gH, 3, RoundingMode.HALF_UP);
			y1 = Y.max().subtract(Y.getLength().multiply(y1));
			y1 = y1.setScale(2, RoundingMode.HALF_UP);
			
			
			
					
			//O.pln(coords.x+","+coords.y);
			jGra.setToolTipText(""+new BigDecimalCoord(x1, y1));
		}
		
	}
	
}
