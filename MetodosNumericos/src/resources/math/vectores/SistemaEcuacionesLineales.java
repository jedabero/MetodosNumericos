package resources.math.vectores;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import resources.O;

/**
 *
 * @author Jedabero
 */
@SuppressWarnings("javadoc")
public class SistemaEcuacionesLineales {
    
    private Matriz matrizCoef;  //Matriz de coeficientes.
    private Matriz vectorB;
    private Matriz matrizAmpliada;
    
    private int numEq;  //Numero de ecuaciones.
    private int numIn;  //Numero de incognitas.
    
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
     * Constructor sin parametros de matriz.
     * Por obligacian se le pedira al usuario por consola que ingrese el numero
     * de ecuaciones y el numero de incognitas.
     * Luego se le pedira que ingrese cada uno de los coeficientes.
     */
    public SistemaEcuacionesLineales(){
        // Ingresar tamano del sistema
        Scanner in = new Scanner(System.in);
        O.pln("Ecuaciones (Filas): ");
        this.numEq = in.nextInt();
        O.pln("Incognitas: ");
        this.numIn = in.nextInt();
        in.close();
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
     * @param n El numero de ecuaciones.
     * @param m El numero de incognitas.
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
    public SistemaEcuacionesLineales(BigDecimal matriz[][]){
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
    public void imprimirMatriz(String titulo){
        O.pln("\n\t"+titulo);
        BigDecimal temp[][] = getMatrizAmpliada().getMatriz();
        for (int i = 0; i < numEq; i++) {
            for (int j = 0; j < numIn; j++) {
                O.p("\t"+Float.parseFloat(""+temp[i][j]));
            }
            O.p("\t|\t"+Float.parseFloat(""+temp[i][numIn])+"\n");
        }
    }
    
    private BigDecimal[][] copiarMatriz(BigDecimal[][] src){
        BigDecimal dest[][] = new BigDecimal[src.length][src[0].length];
        for (int i = 0; i < src.length; i++) {
            System.arraycopy(src[i], 0, dest[i], 0, src[0].length);
        }
        return dest;
    }
    
    /**
     * Se usa en los metodos de Gauss y Jordan para simplificar la ecuacion en
     * la fila i entre el coeficiente A[i][i]
     * @param i el indice de la ecuacion.
     */
    private void simpFila(int i, BigDecimal[][] mt){
        BigDecimal divisor = mt[i][i];
        for (int j = 0; j < numIn+1; j++) {
            mt[i][j] = mt[i][j].divide(divisor, 10, RoundingMode.HALF_UP);
        }
    }
    
    /**
     * Se utiliza en los metodos de Gauss y Jordan para reducir a cero la
     * variable A[k][i].
     * @param k 
     * @param i 
     */
    private void cerosColumna(int k, int i, BigDecimal[][] mt){
        BigDecimal pivote = mt[k][i].negate();
        for (int j = 0; j < numIn+1; j++) {
            mt[k][j] = mt[k][j].add(pivote.multiply(mt[i][j]));
        }
    }
    
    public Matriz metodoCramer() throws Exception{
        return getMatrizCoef().inversa().multipicar(getVectorB()).prox(9);
    }
    
    /**
     * Metodo de Gauss. Vuelve la matriz de coeficientes en una matriz triangular
     * superior.
     */
    public Matriz metodoGauss(){
        BigDecimal mTemp[][] = getMatrizAmpliada().getMatriz();
        BigDecimal matFin[][] = copiarMatriz(mTemp);
        
        for (int i = 0; i < numEq; i++) {
            simpFila(i, matFin);
            for (int k = i+1; k < numEq; k++) {
                cerosColumna(k, i, matFin);
            }
        }
        
        return new Matriz(matFin).prox(20);
    }
    
    /**
     * Metodo de Jordan. Reduce la matriz de coeficientes a la matriz identidad.
     */
    public Matriz metodoJordan(){
        BigDecimal mTemp[][] = getMatrizAmpliada().getMatriz();
        BigDecimal matFin[][] = copiarMatriz(mTemp);
        
        for (int i = 0; i < numEq; i++) {
            simpFila(i, matFin);
            
            for (int k = 0; k < numEq; k++) {
                if (k!=i) {
                    cerosColumna(k, i, matFin);
                }
            }
        }
        
        return new Matriz(matFin).prox(20);
    }
    
    /**
     * TODO description and fix it because it doesn't work!
     * @param maxIt
     * @param tol 
     */
    public Matriz metodoJacobi(int maxIt, BigDecimal tol) throws Exception {
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
                if(e.getMatriz()[i][0].compareTo(tol)<=0){
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
        
        return Matriz.ampliada(X, e).prox(tol.scale()+3);
    }
    
    /**
     * 
     * @param maxIt
     * @param tol 
     */
    public Matriz metodoSeidel(int maxIt, BigDecimal tol) throws Exception{
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
                if(e.getMatriz()[i][0].compareTo(tol)<=0){
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
        
        return Matriz.ampliada(X, e).prox(tol.scale()+3);
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
    
    /*
     * para que los metodos anteriores funcionen correctamente es necesario
     * que la diagonal no tenga ningun cero, los metodos a continuacion son para
     * resolver tal caso. BUT THEY FAIL :(
    public double[][] changeRows(double[][] mt, int pos1, int pos2){
        double row[] = new double[mt[0].length];
        for (int j = 0; j < mt[0].length; j++) {
            row[j] = mt[pos1][j];
            mt[pos1][j] = mt[pos2][j];
            mt[pos2][j] = row[j];
        }
        imprimirMatriz("Fila "+pos1+" cambiada por fila "+pos2);
        return mt;
        
    }
    
    private boolean isElemZero(double[][] mTemp, int i, int j){
        if (mTemp[i][j]==0) {
            return true;
        }else{
            return false;
        }
    }
    
    private int diagElemZeroPos(double[][] mTemp){
        for (int i = 0; i < mTemp.length; i++) {
            if(isElemZero(mTemp, i, i)){
                return  i;
            }
        }
        return -1;
    }
    
    private double[][] checkForCorrectDiagonal(double[][] mTemp) {
        int zeroPos = diagElemZeroPos(mTemp);
        
        System.out.println("fila"+zeroPos+" con zero");
        if (zeroPos>=0) {
            int colPos = -1;
            for (int i = zeroPos; i < mTemp.length; i++) {
                if (mTemp[i][zeroPos]!=0) {
                    colPos = i;
                    break;
                }
            }
            if(colPos>=0){
                mTemp = changeRows(mTemp, zeroPos, colPos);
            }
            imprimirMatriz("Matriz lista: ("+zeroPos+"<->"+colPos+")");
            for (int i = 0; i < mTemp.length; i++) {
                if (mTemp[i][i]==0) {
                    mTemp = checkForCorrectDiagonal(mTemp);
                    break;
                }
            }
        }
        
        return mTemp;
    }
    */
    
}
