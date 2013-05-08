
package ui.main;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.logging.Level;
import java.util.logging.Logger;

import vectores.Matriz;
import vectores.SistemaEcuacionesLineales;

/**
 *
 * @author jberdugo10
 */
public class ApoxFunc {
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        int n = 5;
        BigDecimal x[] = new BigDecimal[n];
        BigDecimal fx[] = new BigDecimal[n];
        for (int i = 0; i < n; i++) {
            x[i] = BigDecimal.valueOf(i);
            fx[i] = BigDecimal.valueOf(Math.pow(10, i));
        }
        
        BigDecimal mat[][] = new BigDecimal[n][n+1];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                mat[i][j] = x[i].pow(j);
            }
            mat[i][n] = fx[i];
        }
        
        SistemaEcuacionesLineales sel = new SistemaEcuacionesLineales(mat);
        Matriz coef;
        try {
            System.out.println("Determinante!!!!!!"+sel.getMatrizCoef().det());
            coef = sel.metodoCramer();
            coef.imprimirMatriz("Res");
        } catch (Exception ex) {
            Logger.getLogger(ApoxFunc.class.getName()).log(Level.SEVERE, null, ex);
            coef = new Matriz(mat);
        }
        
        String pol = "";
        for (int i = 0; i < n; i++) {
            pol += " + "+coef.getMatriz()[i][0].setScale(9, RoundingMode.DOWN).stripTrailingZeros()+"x^"+i;    
        }
        System.out.println(pol);
        
    }
    
}
