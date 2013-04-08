package metodosnumericos;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jedabero
 */
public class SistemaEcuacionesLineales {
    
    private Matriz matrizCoef;  //Matriz de coeficientes.
    private Matriz vectorB;
    private Matriz matrizAmpliada;
    
    private int numEq;  //Número de ecuaciones.
    private int numIn;  //Número de incognitas.
    
    /**
     * Constructor sin parámetros de matriz.
     * Por obligación se le pedirá al usuario por consola que ingrese el número
     * de ecuaciones y el número de incognitas.
     * Luego se le pedirá que ingrese cada uno de los coeficientes.
     */
    public SistemaEcuacionesLineales(){
        // Ingresar tamaño del sistema
        Scanner in = new Scanner(System.in);
        System.out.println("Ecuaciones (Filas): ");
        this.numEq = in.nextInt();
        System.out.println("Incognitas: ");
        this.numIn = in.nextInt();
        // Inicializar las matrices.
        matrizCoef = new Matriz(numEq,numIn, true);
        vectorB = new Matriz(numEq,1, true);
        try {
            matrizAmpliada = Matriz.ampliada(matrizCoef, vectorB);
        } catch (Exception ex) {
            Logger.getLogger(SistemaEcuacionesLineales.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Muestra del sistema resultante.
        imprimirMatriz("Matriz Original");
    }
    
    /**
     * Constructor que crea una matriz de nxm.
     * El usuario debe ingresar cada uno de los coeficientes.
     * @param n El número de ecuaciones.
     * @param m El número de incognitas.
     */
    public SistemaEcuacionesLineales(int n, int m){
        this.numEq=n;
        this.numIn=m;
        // Inicializar las matrices.
        matrizCoef = new Matriz(n,m, true);
        vectorB = new Matriz(n,1, true);
        try {
            matrizAmpliada = Matriz.ampliada(matrizCoef, vectorB);
        } catch (Exception ex) {
            Logger.getLogger(SistemaEcuacionesLineales.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Muestra del sistema resultante.
        imprimirMatriz("Matriz Original");
    }
    
    /**
     * Constructor que crea una matriz a partir de un arreglo bidimensional.
     * @param matriz Arreglo bidimensional.
     */
    public SistemaEcuacionesLineales(double matriz[][]){
        matrizAmpliada = new Matriz(matriz);
        numEq = matriz.length;
        numIn = matriz[0].length-1;
        // Inicializar las matrices.
        matrizCoef = new Matriz(numEq,numIn);
        vectorB = new Matriz(numEq,1);
        for (int i = 0; i < numEq; i++) {
            System.arraycopy(matriz[i], 0, matrizCoef.getMatriz()[i], 0, matriz[0].length-1);
            vectorB.getMatriz()[i][0] = matriz[i][numIn];
        }
        // Muestra del sistema resultante.
        imprimirMatriz("Matriz Original");
    }
    
    /**
     * Muestra en consola la matriz actual, junto con el titulo deseado.
     * @param titulo el titulo.
     */
    private void imprimirMatriz(String titulo){
        System.out.println("\n\t"+titulo);
        double temp[][] = getMatrizAmpliada().getMatriz();
        for (int i = 0; i < numEq; i++) {
            for (int j = 0; j < numIn; j++) {
                System.out.print("\t"+Float.parseFloat(""+temp[i][j]));
            }
            System.out.print("\t|\t"+Float.parseFloat(""+temp[i][numIn]));
            System.out.println();
        }
    }
    
    /**
     * Se usa en los métodos de Gauss y Jordan para simplificar la ecuación en
     * la fila i entre el coeficiente A[i][i]
     * @param i el indice de la ecuación.
     */
    private void simpFila(int i){
        double temp[][] = getMatrizAmpliada().getMatriz();
        double divisor = temp[i][i];
        for (int j = 0; j < numIn+1; j++) {
            temp[i][j] /= divisor;
        }
        this.setMatriz(new Matriz(temp));
    }
    
    /**
     * Se utiliza en los métodos de Gauss y Jordan para reducir a cero la
     * variable A[k][i].
     * @param k 
     * @param i 
     */
    private void cerosColumna(int k, int i){
        double temp[][] = getMatrizAmpliada().getMatriz();
        double pivote = -temp[k][i];
        for (int j = 0; j < numIn+1; j++) {
            temp[k][j] += pivote*temp[i][j];
        }
        this.setMatriz(new Matriz(temp));
    }
    
    /**
     * Método de Gauss. Vuelve la matriz de coeficientes enuna matriz triangular
     * superior.
     */
    public void metodoGauss(){
        for (int i = 0; i < numEq; i++) {
            simpFila(i);
            imprimirMatriz("Gauss paso."+(i+1));
            for (int k = i+1; k < numEq; k++) {
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
        for (int i = 0; i < numEq; i++) {
            simpFila(i);
            imprimirMatriz("Jordan paso."+(i+1));
            
            for (int k = 0; k < numEq; k++) {
                if (k!=i) {
                    cerosColumna(k, i);
                    imprimirMatriz("Jordan pivote."+(k+1));
                }
            }
        }
        
        imprimirMatriz("Matriz Jordan");
    }
    
    /**
     * TODO desciption and fix it because it doesn't work!
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
    public Matriz getMatrizAmpliada() {
        return matrizAmpliada;
    }
    
    /**
     * 
     * @param matriz 
     */
    public void setMatriz(Matriz matriz) {
        this.matrizAmpliada = matriz;
    }
    
}
