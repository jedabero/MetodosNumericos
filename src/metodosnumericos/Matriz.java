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
    
    public static Matriz identidad(int n, int m){
        return new Matriz(n, m);
    }
    
    public Matriz transpuesta(){
        double[][] tempM = new double[m][n];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                tempM[j][i] = matriz[i][j];
            }
        }
        return new Matriz(tempM);
    }
    
    public Matriz suma(Matriz matrizASumar) throws Exception{
        if((n==matrizASumar.n)&&(m==matrizASumar.m)){
            double[][] matTemp1 = new double[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    matTemp1[i][j] = getMatriz()[i][j] + matrizASumar.getMatriz()[i][j];
                }
            }
            return new Matriz(matTemp1);
        }else{
            throw new Exception("Tamaño(s) diferente(s): n:"
                    + n+"!="+matrizASumar.n+" & m:"+m+"!="+matrizASumar.m);
        }
    }
    
    public Matriz multipicar(int escalar){
        double[][] matTemp1 = new double[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    matTemp1[i][j] = escalar*getMatriz()[i][j];
                }
            }
            return new Matriz(matTemp1);
    }
    
    public Matriz multipicar(float escalar){
        double[][] matTemp1 = new double[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    matTemp1[i][j] = escalar*getMatriz()[i][j];
                }
            }
            return new Matriz(matTemp1);
    }
    
    public Matriz multipicar(double escalar){
        double[][] matTemp1 = new double[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    matTemp1[i][j] = escalar*getMatriz()[i][j];
                }
            }
            return new Matriz(matTemp1);
    }
    
    public Matriz multipicar(Matriz b) throws Exception{
        if((m==b.n)){
            double[][] matTemp1 = new double[n][b.m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < b.m; j++) {
                    matTemp1[i][j] = 0d;
                    for (int r = 0; r < m; r++) {
                        matTemp1[i][j] += getMatriz()[i][r]*b.getMatriz()[r][j];
                    }
                }
            }
            return new Matriz(matTemp1);
        }else{
            throw new Exception("Columnas diferente de filas: Columnas:"
                    + m +" & Filas:"+b.n);
        }
    }
    
    /**
     * TODO FIX!!!!
     * @return
     * @throws Exception 
     */
    public double det() throws Exception{
        double mTemp[][] = getMatriz();
        double det = 0d;
        if(n==m){
            if (n==2) {
                System.out.println("dffs");
                det += (mTemp[0][0]*mTemp[1][1]) - (mTemp[0][1]*mTemp[1][0]);
            } else {
                return 1;
            }
            return det;
        }else{
            throw new Exception("Matriz no cuadrada.");
        }
    }
    
    public static void main(String[] args) throws Exception {
        double mat1[][] = { {1,2}, {4,5}};
        Matriz m1 = new Matriz(mat1);
        double mat2[][] = { {9,8,7}, {6,5,4}, {3,2,1}};
        Matriz m2 = new Matriz(mat2);
        System.out.println("det="+m1.det());
        
    }
    
}
