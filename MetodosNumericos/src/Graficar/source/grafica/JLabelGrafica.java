package grafica;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import resources.O;
import resources.math.Big;
import funciones.FuncionBase;

/**
 * Esta clase crea un JLabel con la gr�fica que se mostrar� en panelGrafica.
 * @author Jedabero
 * @since 0.1
 * @deprecated Desde la versi�n 0.4, por {@link JGrafica}
 * @see JGrafica
 */
public class JLabelGrafica extends JLabel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3859589025341129117L;
	private BufferedImage grafica;
	private ArrayList<FuncionBase> grupoFB;
	private ArrayList<Color> tempColorList;
	private ListIterator<FuncionBase> i = null;
	
	/**
	 * @return una imagen con la gr�fica
	 */
	public BufferedImage getGrafica(){return grafica;}
	
	private BigDecimal[] minYs;
	private BigDecimal[] maxYs;

	
	private BigDecimal minX;
	private BigDecimal maxX;
	private BigDecimal minY;
	private BigDecimal maxY;
	private BigDecimal ejeX;
	private BigDecimal ejeY;
	
	private BigDecimal gridX[];
	private BigDecimal gridY[];
	
	private static BigDecimal w;
	private static BigDecimal h;
	
	private static BasicStroke bs1 = new BasicStroke(1);
	private static BasicStroke bs2 = new BasicStroke(2);
	//private static BasicStroke bs3 = new BasicStroke(3);
	
	private int divisiones;
	private boolean divPrin = true;
	private boolean divSec = false;
	private boolean maxYInt = false;
	private boolean etiquetas = true;
	private boolean rangeSetted = false;
	private static BigDecimal[] range = new BigDecimal[2];
	
	/**
	 * @return regresa si las divisiones principales est�n dibujadas
	 */
	public boolean isDivPrin(){return divPrin;}
	/**
	 * @return regresa si las divisiones secundarias est�n dibujadas
	 */
	public boolean isDivSec(){return divSec;}
	/**
	 * @return regresa si est� dibujado con enteros como m�ximos
	 */
	public boolean isMaxYInt(){return maxYInt;}
	/**
	 * @return regresa si las etiquetas de eje est�n dibujadas
	 */
	public boolean isEtiquetas(){return etiquetas;}
	/**
	 * @return regresa si la gr�fica tiene un rango especifico
	 */
	public boolean isRangeSetted(){return rangeSetted;}
	/**
	 * @return regresa el rango de la gr�fica
	 */
	public static BigDecimal[] getRange(){return range;}
	
	/**
	 * @return regresa el m�nimo valor de X
	 */
	public BigDecimal getMinX(){return minX;}
	/**
	 * @return regresa el m�ximo valor de X
	 */
	public BigDecimal getMaxX(){return maxX;}
	/**
	 * @return regresa el m�nimo valor de Y
	 */
	public BigDecimal getMinY(){return minY;}
	/**
	 * @return regresa el m�ximo valor de Y
	 */
	public BigDecimal getMaxY(){return maxY;}
	/**
	 * @return regresa la ubicaci�n del eje X
	 */
	public BigDecimal getEjeX(){return ejeX;}
	/**
	 * @return regresa la ubicaci�n del eje Y
	 */
	public BigDecimal getEjeY(){return ejeY;}
	
	/**
	 * M�todo en el que se realiza todo el dibujado de la(s) gr�fica(s).
	 */
	private void dibujaGrafica(){
		//Se inicializan el label, y las dimensiones; se crea un ambiente 2D.
		grafica = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		w = BigDecimal.valueOf(grafica.getWidth());
		h = BigDecimal.valueOf(grafica.getHeight());
		Graphics2D g2d = grafica.createGraphics();
		
		//Se determina un grosor de l�nea, un color base, y se rellena la imagen.
		g2d.setStroke(bs2);
		g2d.setColor(new Color(238, 238, 238));
		g2d.fillRect(0, 0, grafica.getWidth(), grafica.getHeight());
		
		//Se dibujan los ejes, divisiones y etiquetas.
		dibujarEjeDivsEtiq(g2d, divPrin, divSec, maxYInt, etiquetas, rangeSetted);
		
		//El grosor de las l�neas de las funciones es el segundo.
		g2d.setStroke(bs2);
		
		//En este iterador los puntos se convierten a la correspondiente 
		//posici�n en pixeles.
		for(i = grupoFB.listIterator();i.hasNext();){
			FuncionBase fa = i.next();
			
			int x[] = new int[fa.getX().length];
			int y[] = new int[fa.getY().length];
			
			
			for(int j=0;j<fa.getX().length;j++){
				x[j] = (int)(getWidth()*(
						(fa.getX()[j].subtract(minX).divide(maxX.subtract(minX),
								5, RoundingMode.HALF_UP).doubleValue())));
				y[j] = (int)(getHeight()*(1 -
						(fa.getY()[j].subtract(minY).divide(maxY.subtract(minY),
								5, RoundingMode.HALF_UP).doubleValue())));
			}
			
			g2d.setColor(tempColorList.get(i.previousIndex()));
			
			g2d.drawPolyline( x, y, x.length);
			
		}
	}
	
	/**
	 * En este m�todo se dibujan: Ejes, Divisiones, Etiquetas.
	 * @param g			el ambiente de la imagen.
	 * @param fm		el FontMetrics para las etiquetas.
	 * @param pg		determina si se pintan las divisiones principales.
	 * @param sg		determina si se pintan las divisiones secundarias.
	 * @param closeInt	determina si los m�ximos son enteros o los de las funciones.
	 * @param et		determina si se dibujan las etiquetas de eje.
	 */
	private void dibujarEjeDivsEtiq(Graphics2D g, boolean pg, boolean sg,
			boolean closeInt, boolean et, boolean rs){
		//Se inicializan los arrays que guardaran los m�ximos y m�nimos.
		minYs = new BigDecimal[grupoFB.size()];
		maxYs = new BigDecimal[grupoFB.size()];
		
		//Iterador guardar cada dato en los arrays.
		int k = 0;
		for(i = grupoFB.listIterator();i.hasNext();){
			FuncionBase fa = i.next();
			minYs[k] = Big.min(fa.getY());
			maxYs[k] = Big.max(fa.getY());
			k++;
		}
		
		minX = FuncionBase.getIntervalo()[0];	//Se guarda el m�nimo X
		maxX = FuncionBase.getIntervalo()[1];	//Se guarda el m�ximo X
		minY = Big.min(minYs);			//Se guarda el m�nimo Y
		maxY = Big.max(maxYs);			//Se guarda el m�ximo Y
		
		//Aqu� se asegura que para funciones con pendiente 0 (f(x) = k) haya un
		//rango, es decir, que el m�nimo sea diferente del m�ximo, en el caso 
		//que sean iguales, se duplicara uno, y el otro tendr� el valor opuesto,
		// o uno(1) y menos uno(-1) para el caso de f(x) = 0;
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
		
		//Si se quiera dibujar con los enteros como m�ximos y m�nimos.
		if(closeInt){
			maxX = Big.compareToInt(maxX, true);
			maxY = Big.compareToInt(maxY, true);
			minX = Big.compareToInt(minX, false);
			minY = Big.compareToInt(minY, false);
		}
		O.pln("before change Y: min= "+minY+", max= "+maxY);
		if(rs){
			O.pln(range[0]+", "+range[1]);
			minY = range[0];
			maxY = range[1];
		}else{
			range[0] = minY;
			range[1] = maxY;
		}
		
		//Siempre se chequea el valor de los m�ximos de la gr�fica.
		O.pln("X: min= "+minX+", max= "+maxX);
		O.pln("Y: min= "+minY+", max= "+maxY);
		
		//A continuaci�n se realizan c�lculos para determinar como y donde se
		//dibujan las divisiones principales, secundarias y los ejes. 
		//El grosor de esas l�neas es el m�s delgado
		g.setStroke(bs1);
		
		BigDecimal zeroMinusMinX = BigDecimal.ZERO.subtract(minX);
		BigDecimal intervaloX = maxX.subtract(minX);
		BigDecimal zeroXfraction = zeroMinusMinX.divide(intervaloX, 10, RoundingMode.HALF_UP);
		ejeX = w.multiply(zeroXfraction);
		BigDecimal zeroMinusMinY = BigDecimal.ZERO.subtract(minY);
		BigDecimal rangoY = maxY.subtract(minY);
		BigDecimal zeroYfraction = zeroMinusMinY.divide(rangoY, 10, RoundingMode.HALF_UP);
		ejeY = h.multiply(BigDecimal.ONE.subtract(zeroYfraction));
		
		divisiones =
				intervaloX.divide(FuncionBase.getPaso(), 5, RoundingMode.HALF_UP).intValue() + 1;
		
		gridX = new BigDecimal[divisiones];
		gridY = new BigDecimal[divisiones];
		
		int x[] = new int[gridX.length];
		int y[] = new int[gridY.length];
		
		for(int i=0;i<gridX.length;i++){
			gridX[i] = minX.add(BigDecimal.valueOf(i).multiply(intervaloX.divide(BigDecimal.valueOf(divisiones-1), 5, RoundingMode.HALF_UP)));
			gridY[i] = minY.add(BigDecimal.valueOf(i).multiply(rangoY.divide(BigDecimal.valueOf(divisiones-1), 5, RoundingMode.HALF_UP)));
			if(gridX[i].compareTo(BigDecimal.ZERO)==0){
				gridX[i] = BigDecimal.ZERO;
			}
			if(gridY[i].compareTo(BigDecimal.ZERO)==0){
				gridY[i] = BigDecimal.ZERO;
			}
			x[i] = (int)(getWidth()*(
					(gridX[i].subtract(minX).divide(maxX.subtract(minX),
							5, RoundingMode.HALF_UP).doubleValue())));
			y[i] = (int)(getHeight()*(1 -
					(gridY[i].subtract(minY).divide(maxY.subtract(minY),
							5, RoundingMode.HALF_UP).doubleValue())));
		}
		
		//TODO fix grids
		int bigS = (gridX.length-1)/20;
		int smallS = bigS/5;
		
		//Divisiones secundarias.
		if(sg){
			g.setColor(new Color(200, 200, 200));
			for(int i=0;i<101;i++){
				g.drawLine(x[0], y[i*smallS], x[gridX.length-1], y[i*smallS]);
				g.drawLine(x[i*smallS], y[0], x[i*smallS], y[gridX.length-1]);
			}
		}
		
		//Divisiones principales.
		if(pg){
			g.setColor(new Color(150, 150, 150));
			for(int i=0;i<21;i++){
				g.drawLine(x[0], y[i*bigS], x[gridX.length-1], y[i*bigS]);
				g.drawLine(x[i*bigS], y[0], x[i*bigS], y[gridX.length-1]);
			}
		}
		
		//Ejes X y Y.
		g.setColor(Color.BLACK);
		g.drawLine(ejeX.intValue(), 0, ejeX.intValue(), getHeight());
		g.drawLine(0, ejeY.intValue(), getWidth(), ejeY.intValue());
		g.drawRect(0, 0, grafica.getWidth()-1, grafica.getHeight()-1);
		
		//Se crea un FontMetrics para los c�lculos de la posici�n de las etiquetas
		FontMetrics fm = g.getFontMetrics();
		//Etiquetas de ejes.
		if(et){
			g.setColor(new Color(200, 0, 0));
			for(int i=0;i<21;i++){
				String strX = gridX[i*bigS].stripTrailingZeros().toPlainString();
				g.drawString(strX, x[i*bigS]-(fm.stringWidth(strX)/2),
						ejeY.intValue()-1);
				String srtY = gridY[i*bigS].stripTrailingZeros().toPlainString();
				g.drawString(srtY, ejeX.intValue()+5,
						y[i*bigS]+(fm.stringWidth(strX)/4));
			}
		}
		
	}//Fin de dibujarEjeDivsEtiq.
	
	/**
	 * M�todo para repintar la imagen en el JLabel.
	 */
	public void pintaGrafica(){
		dibujaGrafica();
		setIcon(new ImageIcon(grafica));
	}
	
	/**
	 * M�todo para cambiar el tama�o.
	 * @param w	nuevo ancho.
	 * @param h nuevo alto.
	 */
	public void actualizaTama�o(int w, int h){
		setSize(w, h);
		pintaGrafica();
	}
	
	/**
	 * M�todo que actualiza las funciones o los colores respectivos.
	 * @param al	lista con las funciones.
	 */
	@SuppressWarnings("unchecked")
	public void actualizaLista(ArrayList<?> al){
		if(al.get(0) instanceof FuncionBase){
			this.grupoFB = (ArrayList<FuncionBase>) al;
		}else if(al.get(0) instanceof Color){
			this.tempColorList = (ArrayList<Color>) al;
		}
	}
	
	/**
	 * M�todo que determina si las divisiones principales se dibujan o no.
	 * @param pg
	 */
	public void dibujaDivPrin(boolean pg){
		this.divPrin = pg;
		pintaGrafica();
	}
	
	/**
	 * M�todo que determina si las divisiones secundarias se dibujan o no.
	 * @param sg
	 */
	public void dibujaDivSec(boolean sg){
		this.divSec = sg;
		pintaGrafica();
	}
	
	/**
	 * M�todo que determina si se dibuja con enteros como m�ximos y m�nimos.
	 * @param cI
	 */
	public void actualizaMaximos(boolean cI){
		this.maxYInt = cI;
		pintaGrafica();
	}
	
	/**
	 * M�todo que determina si las etiquetas de eje se dibujan o no.
	 * @param et
	 */
	public void dibujaEtiquetas(boolean et){
		this.etiquetas = et;
		pintaGrafica();
	}
	
	/**
	 * Modifica el rango de la gr�fica a uno especifico.
	 * @param rs
	 * @param r
	 */
	public void setRange(boolean rs, BigDecimal[] r){
		O.pln(rs+","+r[0]+","+r[1]);
		this.rangeSetted = rs;
		JLabelGrafica.range = r;
		pintaGrafica();
	}
	
	/**
	 * Constructor que crea un JLabel con la imagen de la gr�fica.
	 * @param fb	lista de funciones
	 * @param c		lista de colores
	 * @param w		ancho
	 * @param h		alto
	 * @param pg 	determina si se dibujan las divisiones principales
	 * @param sg	determina si se dibujan las divisiones secundarias
	 * @param mi	determina si se hace la gr�fica con m�ximos y m�nimos enteros
	 * @param et	determina si se dibujan las etiquetas de eje
	 * @param rs	determina si se hace la grafica con un rango de Y especifico 
	 */
	public JLabelGrafica(ArrayList<FuncionBase> fb, ArrayList<Color> c,
			int w, int h, boolean pg, boolean sg, boolean mi, boolean et, boolean rs){
		this.grupoFB = fb;
		this.tempColorList = c;
		this.divPrin = pg;
		this.divSec = sg;
		this.maxYInt = mi;
		this.etiquetas = et;
		this.rangeSetted = rs;
		
		setSize(w, h);
		pintaGrafica();
		setHorizontalAlignment(JLabel.CENTER);
		
	}
	
}
