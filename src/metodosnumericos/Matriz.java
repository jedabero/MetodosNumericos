/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metodosnumericos;

import java.util.Scanner;

/**
 *
 * @author Jedabero
 */
public class Matriz {
    
    private double matriz[][];
    private double matrizAmpliada[][];
    
    private int n;
    private int m;
    
    public Matriz(){
        Scanner in = new Scanner(System.in);
        System.out.println("Ecuaciones (Filas)");
        this.n = in.nextInt();
        System.out.println("Incognitas");
        this.m = in.nextInt();
        matrizAmpliada = new double[n][m+1];
        matriz = new double[n][m];
        ingresarMatriz();
        imprimirMatriz("Matriz Original");
    }
    
    public Matriz(int n, int m){
        this.n=n;
        this.m=m;
        matrizAmpliada = new double[n][m+1];
        matriz = new double[n][m];
        ingresarMatriz();
        imprimirMatriz("Matriz Original");
    }
    
    public Matriz(double matriz[][]){
        this.matrizAmpliada = matriz;
        n = matriz.length;
        m = matriz[0].length-1;
        this.matriz = new double[n][m];
        for (int i = 0; i < matriz.length; i++) {
            System.arraycopy(matriz[i], 0, this.matriz[i], 0, matriz[0].length-1);
        }
        imprimirMatriz("Matriz Original");
    }
    
    public static Matriz copy(Matriz m){
        return new Matriz(m.getMatriz());
    }
    
    private void ingresarMatriz(){
        Scanner in = new Scanner(System.in);
        double[][] tempM = new double[n][m+1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m+1; j++) {
                System.out.print("Ingrese A("+(i+1)+","+(j+1)+") ");
                tempM[i][j] = in.nextDouble();
            }
        }
        setMatriz(tempM);
    }
    
    public final void imprimirMatriz(String titulo){
        System.out.println("\n\t"+titulo);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print("\t"+Float.parseFloat(""+getMatriz()[i][j]));
            }
            System.out.print("\t|\t"+Float.parseFloat(""+getMatriz()[i][m]));
            System.out.println();
        }
    }
    
    private void simpFila(int i){
        double divisor = this.getMatriz()[i][i];
        double[][] tempM = this.getMatriz();
        for (int j = 0; j < m+1; j++) {
            tempM[i][j] /= divisor;
        }
        this.setMatriz(tempM);
    }
    
    private void cerosColumna(int k, int i){
        double pivote = -this.getMatriz()[k][i];
        double[][] tempM = this.getMatriz();
        for (int j = 0; j < m+1; j++) {
            tempM[k][j] += pivote*this.getMatriz()[i][j];
        }
        this.setMatriz(tempM);
    }
    
    public void metodoGauss(){
        for (int i = 0; i < n; i++) {
            simpFila(i);
            imprimirMatriz("Gauss paso"+(i+1));
            for (int k = i+1; k < n; k++) {
                cerosColumna(k, i);
                imprimirMatriz("Gauss pivote"+(k+1));
            }
        }
        
        imprimirMatriz("Matriz Gaussiana");
    }
    
    public void metodoJordan(){
        for (int i = 0; i < n; i++) {
            simpFila(i);
            imprimirMatriz("Jordan paso"+(i+1));
            
            for (int k = 0; k < n; k++) {
                if (k!=i) {
                    cerosColumna(k, i);
                    imprimirMatriz("Jordan pivote"+(k+1));
                }
            }
        }
        
        imprimirMatriz("Matriz Jordan");
    }
    
    public void metodoJacobi(int maxIt, double tol){
        
    }
    
    public void metodoSeidel(int maxIt, double tol){
        
    }

    public double[][] getMatriz() {
        return matrizAmpliada;
    }

    public void setMatriz(double[][] matriz) {
        this.matrizAmpliada = matriz;
    }
    
}
