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
    
    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }
    
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
        } else {
            n = 3;
            m = 3;
            matriz = new double[n][m];
            escMatrizIdentidad();
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
        } else {
            escMatrizIdentidad();
        }
        
    }
    
    public Matriz(double [][] matriz){
        this.matriz = matriz;
        this.n = matriz.length;
        this.m = matriz[0].length;
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
    public void imprimirMatriz(String titulo){
        System.out.println("\n\t"+titulo);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print("\t"+getMatriz()[i][j]);
            }
            System.out.println();
        }
    }
    
    public boolean esCuadrada(){
        return (n==m);
    }
    
    public Matriz diagonal() throws Exception{
        if (esCuadrada()) {
            double temp[][] = new double[n][m];
            for (int i = 0; i < n; i++) {
                temp[i][i] = matriz[i][i];
            }
            return new Matriz(temp);
        } else {
            throw new Exception("No es cuadrada.");
        }
    }
    
    public Matriz trianguloSuperior() throws Exception{
        if (esCuadrada()) {
            double temp[][] = new double[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (i<=j) {
                        temp[i][j] = matriz[i][j];
                    } else {
                        temp[i][j] = 0d;
                    }
                }
            }
            return new Matriz(temp);
        } else {
            throw new Exception("No es cuadrada.");
        }
    }
    
    public Matriz trianguloSuperiorEstricto() throws Exception{
        return trianguloSuperior().restar(diagonal());
    }
    
    public Matriz trianguloInferior() throws Exception{
        if (esCuadrada()) {
            double temp[][] = new double[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (i>=j) {
                        temp[i][j] = matriz[i][j];
                    } else {
                        temp[i][j] = 0d;
                    }
                }
            }
            return new Matriz(temp);
        } else {
            throw new Exception("No es cuadrada.");
        }
    }
    
    public Matriz trianguloInferiorEstricto() throws Exception{
        return trianguloInferior().restar(diagonal());
    }
    
    public static Matriz ampliada(Matriz a, Matriz b)throws Exception{
        Matriz AB;
        int nT = 0;
        int mT = 0;
        if(a.getN()==b.getN()){
            nT = a.getN();
            mT = a.getM()+b.getM();
            AB = new Matriz(nT, mT);
        }else{
            throw new Exception("Diferentes tamaños de filas");
        }
        double temp[][] = AB.getMatriz();
        for (int i = 0; i < nT; i++) {
            for (int j = 0; j < mT; j++) {
                if (j<a.getM()) {
                    temp[i][j] = a.getMatriz()[i][j];
                } else {
                    temp[i][j] = b.getMatriz()[i][j-a.getM()];
                }
            }
        }
        AB.setMatriz(temp);
        AB.imprimirMatriz("Matriz "+nT+"x"+mT);
        return AB;
    }
    
    public static Matriz identidad(int n, int m){
        return new Matriz(n, m);
    }
    
    public static Matriz cero(int n, int m){
        double temp[][] = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                temp[i][j] = 0d;
            }
        }
        return new Matriz(temp);
    }
    
    public static Matriz num(int n, int m, double num){
        double temp[][] = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                temp[i][j] = num;
            }
        }
        return new Matriz(temp);
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
    
    public Matriz inversa() throws Exception{
        return adjunta().multipicar(1.0/det());
    }
    
    public double cofactor(int i, int j) throws Exception{
        return Math.pow(-1, (i+j))*reducida(i, j).det();
    }
    
    public Matriz cofactor() throws Exception{
        double temp[][] = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                temp[i][j] = cofactor(i,j);
            }
        }
        return new Matriz(temp);
    }
    
    public Matriz adjunta() throws Exception{
        return cofactor().transpuesta();
    }
    
    public double traza()throws Exception{
        double tr = 0d;
        if (!esCuadrada()) {
            throw new Exception("Matriz no cuadrada.");
        } else {
            for (int i = 0; i < 10; i++) {
                tr += getMatriz()[i][i];
            }
            return tr;
        }
    }
    
    public Matriz abs(){
        double temp[][] = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                temp[i][j] = Math.abs(getMatriz()[i][j]);
            }
        }
        return new Matriz(temp);
    }
    
    public static Matriz abs(Matriz a){
        double temp[][] = new double[a.getN()][a.getM()];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[0].length; j++) {
                temp[i][j] = Math.abs(a.getMatriz()[i][j]);
            }
        }
        return new Matriz(temp);
    }
    
    public Matriz sumar(Matriz sumando) throws Exception{
        if((n==sumando.n)&&(m==sumando.m)){
            double[][] matTemp1 = new double[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    matTemp1[i][j] = getMatriz()[i][j] + sumando.getMatriz()[i][j];
                }
            }
            return new Matriz(matTemp1);
        }else{
            throw new Exception("Tamaño(s) diferente(s): n:"+n
                    +"!="+sumando.n+" & m:"+m+"!="+sumando.m);
        }
    }
    
    public Matriz restar(Matriz sustraendo) throws Exception{
        return sumar(sustraendo.multipicar(-1));
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
    
    public Matriz multipicar(Matriz multiplicando) throws Exception{
        if((m==multiplicando.n)){
            double[][] matTemp1 = new double[n][multiplicando.m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < multiplicando.m; j++) {
                    matTemp1[i][j] = 0d;
                    for (int r = 0; r < m; r++) {
                        double mult = multiplicando.getMatriz()[r][j];
                        matTemp1[i][j] += getMatriz()[i][r]*mult;
                    }
                }
            }
            return new Matriz(matTemp1);
        }else{
            throw new Exception("Columnas diferente de filas: Columnas:"
                    + m +" & Filas:"+multiplicando.n);
        }
    }
    
    /**
     * Regresa el valor del determinante de la matriz.
     * @return determinante
     * @throws Exception si no es cuadrada
     */
    public double det() throws Exception{
        double mTemp[][] = getMatriz();
        double det = 0d;
        if(esCuadrada()){
            if (n==2) {
                det = (mTemp[0][0]*mTemp[1][1]) - (mTemp[0][1]*mTemp[1][0]);
            } else {
                for (int i = 0; i < m; i++) {
                    if (i%2==0) {
                        det += mTemp[0][i]*reducida(0, i).det();
                    } else {
                        det -= mTemp[0][i]*reducida(0, i).det();
                    }
                }
            }
            return det;
        }else{
            throw new Exception("Matriz no cuadrada.");
        }
    }
    
    public Matriz reducida(int i, int j) throws Exception{
        double mTemp[][] = getMatriz();
        double matRed[][] = new double[mTemp.length-1][mTemp[0].length-1];
        if((i<n)&&(j<m)){
            int g = 0;
            for (int k = 0; k < n; k++) {
            int h = 0;
                for (int l = 0; l < m; l++) {
                    if((k!=i)&&(l!=j)){
                        matRed[g][h] = mTemp[k][l];
                        h++;
                    }
                }
                if(k!=i){
                    g++;
                }
            }
            return new Matriz(matRed);
        } else {
            throw new Exception("Indice(s) por fuera de la(s) dimension(es)");
        }
    }
    
}
