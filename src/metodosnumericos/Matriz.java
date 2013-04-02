package metodosnumericos;

import java.util.Scanner;

/**
 *
 * @author Jedabero
 */
public class Matriz {
    
    private double matrizCoef[][];  //Matriz de coeficientes.
    private double vectorB[];
    private double matrizAmpliada[][];
    
    private int n;  //Número de ecuaciones.
    private int m;  //Número de incognitas.
    
    /**
     * Constructor sin parámetros de matriz.
     * Por obligación se le pedirá al usuario por consola que ingrese el número
     * de ecuaciones y el número de incognitas.
     * Luego se le pedirá que ingrese cada uno de los coeficientes.
     */
    public Matriz(){
        // Ingresar tamaño del sistema
        Scanner in = new Scanner(System.in);
        System.out.println("Ecuaciones (Filas): ");
        this.n = in.nextInt();
        System.out.println("Incognitas: ");
        this.m = in.nextInt();
        // Inicializar las matrices.
        matrizAmpliada = new double[n][m+1];
        matrizCoef = new double[n][m];
        vectorB = new double[n];
        // Ingreso de datos y muestra del sistema resultante.
        ingresarMatriz();
        imprimirMatriz("Matriz Original");
    }
    
    /**
     * Constructor que crea una matriz de nxm.
     * El usuario debe ingresar cada uno de los coeficientes.
     * @param n El número de ecuaciones.
     * @param m El número de incognitas.
     */
    public Matriz(int n, int m){
        this.n=n;
        this.m=m;
        // Inicializar las matrices.
        matrizAmpliada = new double[n][m+1];
        matrizCoef = new double[n][m];
        vectorB = new double[n];
        // Ingreso de datos y muestra del sistema resultante.
        ingresarMatriz();
        imprimirMatriz("Matriz Original");
    }
    
    /**
     * Constructor que crea una matriz a partir de un arreglo bidimensional.
     * @param matriz Arreglo bidimensional.
     */
    public Matriz(double matriz[][]){
        this.matrizAmpliada = matriz;
        n = matriz.length;
        m = matriz[0].length-1;
        // Inicializar las matrices.
        this.matrizCoef = new double[n][m];
        vectorB = new double[n];
        for (int i = 0; i < matriz.length; i++) {
            System.arraycopy(matriz[i], 0, matrizCoef[i], 0, matriz[0].length-1);
            vectorB[i] = matriz[i][matriz[0].length-1];
        }
        // Muestra del sistema resultante.
        imprimirMatriz("Matriz Original");
    }
    
    /**
     * Devuelve la copia de la matriz actual.
     * @param m la matriz a copiar.
     * @return una copia de la matriz m.
     */
    public static Matriz copy(Matriz m){
        return new Matriz(m.getMatriz());
    }
    
    /**
     * Se llama cuando es necesario que el usario ingreselos datos de la matriz.
     */
    private void ingresarMatriz(){
        Scanner in = new Scanner(System.in);
        double[][] tempM = new double[n][m+1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m+1; j++) {
                System.out.print("Ingrese A("+(i+1)+","+(j+1)+") ");
                tempM[i][j] = in.nextDouble();
                if (j<m) {
                    matrizCoef[i][j] = tempM[i][j];
                    System.out.println("A["+(i+1)+","+(j+1)+"]="+matrizCoef[i][j]);
                }else {
                    vectorB[i] = tempM[i][j];
                    System.out.println("b["+(i+1)+"]="+vectorB[i]);
                }
            }
        }
        setMatriz(tempM);
    }
    
    /**
     * Muestra en consola la matriz actual, junto con el titulo deseado.
     * @param titulo el titulo.
     */
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
    
    /**
     * Se usa en los métodos de Gauss y Jordan para simplificar la ecuación en
     * la fila i entre el coeficiente A[i][i]
     * @param i el indice de la ecuación.
     */
    private void simpFila(int i){
        double divisor = this.getMatriz()[i][i];
        double[][] tempM = this.getMatriz();
        for (int j = 0; j < m+1; j++) {
            tempM[i][j] /= divisor;
        }
        this.setMatriz(tempM);
    }
    
    /**
     * Se utiliza en los métodos de Gauss y Jordan para reducir a cero la
     * variable A[k][i].
     * @param k 
     * @param i 
     */
    private void cerosColumna(int k, int i){
        double pivote = -this.getMatriz()[k][i];
        double[][] tempM = this.getMatriz();
        for (int j = 0; j < m+1; j++) {
            tempM[k][j] += pivote*this.getMatriz()[i][j];
        }
        this.setMatriz(tempM);
    }
    
    /**
     * Método de Gauss. Vuelve la matriz de coeficientes enuna matriz triangular
     * superior.
     */
    public void metodoGauss(){
        for (int i = 0; i < n; i++) {
            simpFila(i);
            imprimirMatriz("Gauss paso."+(i+1));
            for (int k = i+1; k < n; k++) {
                cerosColumna(k, i);
                imprimirMatriz("Gauss pivote."+(k+1));
            }
        }
        
        imprimirMatriz("Matriz Gaussiana");
    }
    
    /**
     * Método de Jordan. Reduce la matriz de coeficientes a la matriz identidad.
     */
    public void metodoJordan(){
        for (int i = 0; i < n; i++) {
            simpFila(i);
            imprimirMatriz("Jordan paso."+(i+1));
            
            for (int k = 0; k < n; k++) {
                if (k!=i) {
                    cerosColumna(k, i);
                    imprimirMatriz("Jordan pivote."+(k+1));
                }
            }
        }
        
        imprimirMatriz("Matriz Jordan");
    }
    
    /**
     * 
     * @param maxIt
     * @param tol 
     */
    public void metodoJacobi(int maxIt, double tol){
        
    }
    
    /**
     * 
     * @param maxIt
     * @param tol 
     */
    public void metodoSeidel(int maxIt, double tol){
        
    }
    
    /**
     * 
     * @return 
     */
    public double[][] getMatriz() {
        return matrizAmpliada;
    }
    
    /**
     * 
     * @param matriz 
     */
    public void setMatriz(double[][] matriz) {
        this.matrizAmpliada = matriz;
    }
    
}
