/**
 * 
 */
package grafica;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.event.MouseInputAdapter;

import resources.O;
import resources.math.BigInterval;

/**
 * @author Jedabero
 * @since 0.4
 */
public class CoordenadasGraficasMIA extends MouseInputAdapter {

	private JGrafica jGra;
	private BigInterval X;
	private BigInterval Y;

	private BigDecimal dx;
	private BigDecimal dy;
	private static BigDecimal uno = BigDecimal.ONE;
	private static BigDecimal dos = new BigDecimal(2);
	private static BigDecimal ten = BigDecimal.TEN;
	private static BigDecimal five = ten.divide(dos);

	private Point prevPoint = new Point();

	/**
	 * @param jg
	 * @param x
	 * @param y
	 * 
	 */
	public CoordenadasGraficasMIA(JGrafica jg, BigInterval x, BigInterval y) {
		this.jGra = jg;
		this.X = x;
		this.Y = y;
	}

	/**
	 * @param x
	 * @param y
	 */
	public void updateIntervals(BigInterval x, BigInterval y) {
		this.X = x;
		this.Y = y;
	}

	public void mousePressed(MouseEvent me) {
		O.pln("Mouse pressed at (x=" + me.getX() + ", y=" + me.getY() + ").");
		prevPoint = me.getPoint();
	}

	public void mouseDragged(MouseEvent mde) {
		Point currPoint = mde.getPoint();

		dx = BigDecimal.valueOf((prevPoint.x - currPoint.x) / 200.0);
		O.pln("dx:" + dx);
		dy = BigDecimal.valueOf((prevPoint.y - currPoint.y) / 200.0).negate();
		O.pln("dy:" + dy);

		X.setMax(X.max().add(dx));
		X.setMin(X.min().add(dx));
		Y.setMax(Y.max().add(dy));
		Y.setMin(Y.min().add(dy));
		jGra.updateIntervals(X, Y);

		prevPoint = currPoint;
	}

	public void mouseReleased(MouseEvent me) {
		O.pln("Mouse released at (x=" + me.getX() + ", y=" + me.getY() + ").");
	}

	public void mouseWheelMoved(MouseWheelEvent mwe) {
		// Get X and Y scales and precisions
		int xScale = X.length().scale();
		int xPresi = X.length().precision();
		O.pln("xScale:" + xScale + "|xPresicion:" + xPresi);
		if (xScale > 0) {
			dx = uno.divide(ten.pow(xScale - 1));
		} else {
			dx = uno;
		}
		O.pln("dx:" + dx);

		int yScale = Y.length().scale();
		int yPresi = Y.length().precision();
		O.pln("yScale:" + yScale + "|yPresicion:" + yPresi);
		if (yScale > 0) {
			dy = uno.divide(ten.pow(yScale - 1));
		} else {
			dy = uno;
		}
		O.pln("dy:" + dy);
		// TODO coordGmia xPresi

		int whlrot = mwe.getWheelRotation();
		BigDecimal bdwhlrot = new BigDecimal(Integer.toString(whlrot));
		int num = bdwhlrot.abs().intValue();
		int numSign = bdwhlrot.signum();
		switch (num) {
		case 1:
			dx = dx.multiply(uno.divide(ten));
			dy = dy.multiply(uno.divide(ten));
			break;
		case 2:
			dx = dx.multiply(dos.divide(ten));
			dy = dy.multiply(uno.divide(ten));
			break;
		case 3:
		default:
			dx = dx.multiply(five.divide(ten));
			dy = dy.multiply(uno.divide(ten));
			break;
		}
		if (numSign < 0) {
			dx = dx.negate();
			dy = dy.negate();
		}

		O.pln(dx + ", " + bdwhlrot);
		/*
		 * dx = X.max().round(new MathContext(1, RoundingMode.DOWN));
		 * O.pln(dx+", "+bdwhlrot); dx =
		 * dx.multiply(bdwhlrot).divide(BigDecimal.TEN, 10,
		 * RoundingMode.HALF_EVEN);
		 * 
		 * bdwhlrot = bdwhlrot.divide(BigDecimal.TEN, 10,
		 * RoundingMode.HALF_EVEN); O.pln(dx+", "+bdwhlrot);
		 */
		X.setMax(X.max().add(dx));
		X.setMin(X.min().subtract(dx));
		Y.setMax(Y.max().add(dy));
		Y.setMin(Y.min().subtract(dy));
		jGra.updateIntervals(X, Y);
	}

	public void mouseMoved(MouseEvent me) {
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

		boolean xInBounds = (x >= gCoords.x) && (x <= (gCoords.x + graW));
		boolean yInBounds = (y >= gCoords.y) && (y <= (gCoords.y + graH));
		if (xInBounds && yInBounds) {// TODO Coordinates
			x -= gCoords.x;
			x1 = BigDecimal.valueOf(x).divide(gW, 10, RoundingMode.HALF_UP);
			x1 = X.min().add(X.length().multiply(x1));
			x1 = x1.setScale(3, RoundingMode.HALF_UP);

			y -= gCoords.y;
			y1 = BigDecimal.valueOf(y).divide(gH, 10, RoundingMode.HALF_UP);
			y1 = Y.max().subtract(Y.length().multiply(y1));
			y1 = y1.setScale(3, RoundingMode.HALF_UP);

			BigPoint coords = new BigPoint(x1, y1);

			jGra.setToolTipText("" + coords);
		}

	}

}
