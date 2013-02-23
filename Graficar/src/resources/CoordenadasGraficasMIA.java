/**
 * 
 */
package resources;

import grafica.JGrafica;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

/**
 * @author Jedabero
 *
 */
public class CoordenadasGraficasMIA extends MouseInputAdapter {
	
	private JGrafica jGra;
	
	/**
	 * @param jg 
	 * 
	 */
	public CoordenadasGraficasMIA(JGrafica jg) {
		// TODO Auto-generated constructor stub
		this.jGra = jg;
	}
	
	public void mousePressed(MouseEvent me) { }

	public void mouseDragged(MouseEvent me) { }

	public void mouseReleased(MouseEvent me) { }
	
	public void mouseMoved(MouseEvent me){
		int graW = jGra.getWidth();
		int graH = jGra.getHeight();
		Point gCoords = jGra.getgCoords();
		
		Point mP = me.getPoint();
		int x=mP.x, y=mP.y;
		boolean xInBounds = (x>=gCoords.x)&&(x<=(graW-gCoords.x));
		boolean yinBounds = (y>=gCoords.y)&&(y<=(graH-gCoords.y));
		if(xInBounds&&yinBounds){//TODO Coordinates
			x -= gCoords.x;
			y -= gCoords.y;
		}
		jGra.setToolTipText((xInBounds&&yinBounds)?"("+x+", "+y+")":null);
	}

}
