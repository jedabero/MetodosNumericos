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
    
    public Matriz getMatrizCoef() {
        return matrizCoef;
    }

    public Matriz getVectorB() {
        return vectorB;
    }

    public int getNumEq() {
        return numEq;
    }

    public int getNumIn() {
        return numIn;
    }
    
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
    
    private double[][] copiarMatriz(double[][] src){
        double dest[][] = new double[src.length][src[0].length];
        for (int i = 0; i < src.length; i++) {
            System.arraycopy(src[i], 0, dest[i], 0, src[0].length);
        }
        return dest;
    }
    
    /**
     * Se usa en los métodos de Gauss y Jordan para simplificar la ecuación en
     * la fila i entre el coeficiente A[i][i]
     * @param i el indice de la ecuación.
     */
    private void simpFila(int i, double[][] mt){
        double divisor = mt[i][i];
        for (int j = 0; j < numIn+1; j++) {
            mt[i][j] /= divisor;
        }
    }
    
    /**
     * Se utiliza en los métodos de Gauss y Jordan para reducir a cero la
     * variable A[k][i].
     * @param k 
     * @param i 
     */
    private void cerosColumna(int k, int i, double[][] mt){
        double pivote = -mt[k][i];
        for (int j = 0; j < numIn+1; j++) {
            mt[k][j] += pivote*mt[i][j];
        }
    }
    
    public Matriz metodoCramer() throws Exception{
        return getMatrizAmpliada().inversa().multipicar(getVectorB());
    }
    
    /**
     * Método de Gauss. Vuelve la matriz de coeficientes enuna matriz triangular
     * superior.
     */
    public Matriz metodoGauss(){
        double mTemp[][] = getMatrizAmpliada().getMatriz();
        double matFin[][] = copiarMatriz(mTemp);
        
        for (int i = 0; i < numEq; i++) {
            simpFila(i, matFin);
            for (int k = i+1; k < numEq; k++) {
                cerosColumna(k, i, matFin);
            }
        }
        
        return new Matriz(matFin);
    }
    
    /**
     * Método de Jordan. Reduce la matriz de coeficientes a la matriz identidad.
     */
    public Matriz metodoJordan(){
        double mTemp[][] = getMatrizAmpliada().getMatriz();
        double matFin[][] = copiarMatriz(mTemp);
        for (int i = 0; i < numEq; i++) {
            simpFila(i, matFin);
            
            for (int k = 0; k < numEq; k++) {
                if (k!=i) {
                    cerosColumna(k, i, matFin);
                }
            }
        }
        
        return new Matriz(matFin);
    }
    
    /**
     * TODO desciption and fix it because it doesn't work!
     * @param maxIt
     * @param tol 
     */
    public Matriz metodoJacobi(int maxIt, double tol) throws Exception {
        Matriz X = Matriz.cero(numIn,1);
        Matriz e = Matriz.cero(numIn,1);
        Matriz diag = matrizCoef.diagonal();
        Matriz diagInv = diag.inversa();
        Matriz tie = matrizCoef.trianguloInferiorEstricto();
        Matriz r = tie.sumar(matrizCoef.trianguloSuperiorEstricto());
        
        boolean doLoop = true;
        boolean swi[] = new boolean[numIn];
        for (int i = 0; i < swi.length; i++) {
            swi[i] = false;
        }
        int k = 0;
        while (doLoop&&(k<=maxIt)) {            
            Matriz rX = r.multipicar(X);
            Matriz b_rX = vectorB.restar(rX);
            Matriz X1 = diagInv.multipicar(b_rX);
            e = Matriz.abs(X1.restar(X));
            for (int i = 0; i < numIn; i++) {
                if(e.getMatriz()[i][0]<=tol){
                    swi[i] = true;
                }
            }
            int cont = 0;
            for (int i = 0; i < numIn; i++) {
                if(swi[i]){
                    cont++;
                }
            }
            
            if (cont==numIn) {
                doLoop = false;
            } else {
                X = X1;
                k++;
            }
        }
        
        return Matriz.ampliada(X, e);
    }
    
    /**
     * 
     * @param maxIt
     * @param tol 
     */
    public Matriz metodoSeidel(int maxIt, double tol) throws Exception{
        Matriz X = Matriz.cero(numIn,1);
        Matriz e = Matriz.cero(numIn,1);
        Matriz lInv = matrizCoef.trianguloInferior().inversa();
        Matriz us = matrizCoef.trianguloSuperiorEstricto();
        
        boolean doLoop = true;
        boolean swi[] = new boolean[numIn];
        for (int i = 0; i < swi.length; i++) {
            swi[i] = false;
        }
        int k = 0;
        while (doLoop&&(k<=maxIt)) {            
            Matriz usX = us.multipicar(X);
            Matriz b_usX = vectorB.restar(usX);
            Matriz X1 = lInv.multipicar(b_usX);
            e = Matriz.abs(X1.restar(X));
            for (int i = 0; i < numIn; i++) {
                if(e.getMatriz()[i][0]<=tol){
                    swi[i] = true;
                }
            }
            
            int cont = 0;
            for (int i = 0; i < numIn; i++) {
                if(swi[i]){
                    cont++;
                }
            }
            
            if (cont==numIn) {
                doLoop = false;
            } else {
                X = X1;
                k++;
            }
        }
        
        return Matriz.ampliada(X, e);
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
