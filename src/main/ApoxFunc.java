/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import metodosnumericos.Matriz;
import metodosnumericos.SistemaEcuacionesLineales;

/**
 *
 * @author jberdugo10
 */
public class ApoxFunc {
    
    public static void main(String[] args) {
        int n = 0;
        Scanner in = new Scanner(System.in);
        System.out.println("Ingrese numero de puntos");
        n = in.nextInt();
        
        double x[] = new double[n];
        double fx[] = new double[n];
        System.out.println("Ingrese T.T");
        for (int i = 0; i < n; i++) {
            x[i] = in.nextDouble();
            fx[i] = in.nextDouble();
        }
        
        double mat[][] = new double[n][n+1];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                mat[i][j] = Math.pow(x[i],j);
            }
            mat[i][n] = fx[i];
        }
        
        SistemaEcuacionesLineales sel = new SistemaEcuacionesLineales(mat);
        Matriz coef;
        try {
            coef = sel.metodoCramer();
            coef.imprimirMatriz("Res");
        } catch (Exception ex) {
            Logger.getLogger(ApoxFunc.class.getName()).log(Level.SEVERE, null, ex);
            coef = new Matriz(mat);
        }
        
        String pol = "";
        pol += coef.getMatriz()[0][0] +" + "+coef.getMatriz()[1][0]+"x" + " + "+coef.getMatriz()[2][0]+"x^2";
        
        System.out.println(pol);
        
    }
    
}
