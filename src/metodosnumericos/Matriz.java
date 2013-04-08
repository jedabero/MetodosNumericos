package metodosnumericos;

import java.util.Scanner;

/**
 *
 * @author Jedabero
 */
public class Matriz {
    
    private double matriz[][];  //Matriz

    public double[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(double[][] matriz) {
        this.matriz = matriz;
    }
    
    private int n;  //Número de filas
    private int m;  //Número de columnas
    
    public Matriz(){
        this(false);
    }
    
    public Matriz(boolean ingresar){
        if(ingresar){
            Scanner in = new Scanner(System.in);
            System.out.println("Filas: ");
            this.n = in.nextInt();
            System.out.println("Columnas: ");
            this.m = in.nextInt();
            matriz = new double[n][m];
            ingresarMatriz();
            imprimirMatriz("Matriz "+n+"x"+m);
        } else {
            n = 3;
            m = 3;
            matriz = new double[n][m];
            escMatrizIdentidad();
            imprimirMatriz("Matriz Identidad "+n+"x"+m);
        }
    }
    
    public Matriz(int n, int m){
        this(n, m, false);
    }
    
    public Matriz(int n, int m, boolean ingresar){
        this.n = n;
        this.m = m;
        matriz = new double[n][m];
        
        if(ingresar){
            ingresarMatriz();
            imprimirMatriz("Matriz "+n+"x"+m);
        } else {
            escMatrizIdentidad();
            imprimirMatriz("Matriz Identidad "+n+"x"+m);
        }
        
    }
    
    public Matriz(double [][] matriz){
        this.matriz = matriz;
        this.n = matriz.length;
        this.m = matriz[0].length;
        imprimirMatriz("Matriz "+n+"x"+m);
    }
    
    /**
     * Convierte la matriz en la matriz indentidad
     */
    private void escMatrizIdentidad() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if(i!=j) {
                    matriz[i][j] = 0d;
                } else {
                    matriz[i][j] = 1d;
                }
            }
        }
    }
    
    /**
     * Se llama cuando es necesario que el usario ingreselos datos de la matriz.
     */
    private void ingresarMatriz(){
        Scanner in = new Scanner(System.in);
        double[][] tempM = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print("Ingrese A("+(i+1)+","+(j+1)+") ");
                tempM[i][j] = in.nextDouble();
            }
        }
        setMatriz(tempM);
    }
    
    /**
     * Muestra en consola la matriz actual, junto con el titulo deseado.
     * @param titulo el titulo.
     */
    private void imprimirMatriz(String titulo){
        System.out.println("\n\t"+titulo);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print("\t"+getMatriz()[i][j]);
            }
            System.out.println();
        }
    }
    
}
