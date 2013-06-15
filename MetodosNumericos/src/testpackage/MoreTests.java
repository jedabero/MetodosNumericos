
package testpackage;

import java.math.BigDecimal;

import resources.O;
import resources.math.vectores.Matriz;

/**
 *
 * @author jberdugo10
 */
public class MoreTests {
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        
    	BigDecimal matriz[][] = new BigDecimal[][]{
    			{BigDecimal.valueOf(3),BigDecimal.valueOf(1),BigDecimal.valueOf(2)},
    			{BigDecimal.valueOf(1),BigDecimal.valueOf(3),BigDecimal.valueOf(2)},
    			{BigDecimal.valueOf(1),BigDecimal.valueOf(2),BigDecimal.valueOf(3)}
    	};
    	
    	Matriz m = new Matriz(matriz);
    	O.pln(m);
    	O.pln(m.esEstrictamenteDiagonalDominante());
    	
    }
    
}
