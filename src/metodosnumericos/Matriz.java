package metodosnumericos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

/**
 * 
 * @author Jedabero
 */
public class Matriz {
    
    private BigDecimal matriz[][];  //Matriz

    public BigDecimal[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(BigDecimal[][] matriz) {
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
            matriz = new BigDecimal[n][m];
            ingresarMatriz();
        } else {
            n = 3;
            m = 3;
            matriz = new BigDecimal[n][m];
            escMatrizIdentidad();
        }
    }
    
    public Matriz(int n, int m){
        this(n, m, false);
    }
    
    public Matriz(int n, int m, boolean ingresar){
        this.n = n;
        this.m = m;
        matriz = new BigDecimal[n][m];
        
        if(ingresar){
            ingresarMatriz();
        } else {
            escMatrizIdentidad();
        }
        
    }
    
    public Matriz(BigDecimal [][] matriz){
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
                    matriz[i][j] = BigDecimal.ZERO;
                } else {
                    matriz[i][j] = BigDecimal.ONE;
                }
            }
        }
    }
    
    /**
     * Se llama cuando es necesario que el usario ingreselos datos de la matriz.
     */
    private void ingresarMatriz(){
        Scanner in = new Scanner(System.in);
        BigDecimal[][] tempM = new BigDecimal[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print("Ingrese A("+(i+1)+","+(j+1)+") ");
                tempM[i][j] = new BigDecimal(in.nextDouble());
            }
        }
        setMatriz(tempM);
    }
    
    /**
     * Muestra en consola la matriz actual, junto con el titulo deseado.
     * @param titulo el titulo.
     */
    public String imprimirMatriz(String titulo){
        String s = "\n\t" + titulo+"\n";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                s += "\t"+getMatriz()[i][j].stripTrailingZeros();
            }
            s += "\n";
        }
        return s;
    }
    
    public boolean esCuadrada(){
        return (n==m);
    }
    
    public Matriz diagonal() throws Exception{
        System.out.println("Obteniendo matriz diagonal...");
        if (esCuadrada()) {
            BigDecimal temp[][] = new BigDecimal[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if(i==j){
                        temp[i][j] = matriz[i][j];
                    }else{
                        temp[i][j] = BigDecimal.ZERO;
                    }
                }
                
            }
            Matriz mtrz = new Matriz(temp);
            System.out.println(mtrz.imprimirMatriz("Matriz diagonal"));
            return mtrz;
        } else {
            throw new Exception("No es cuadrada.");
        }
    }
    
    public Matriz trianguloSuperior() throws Exception{
        System.out.println("Obteniendo matriz de triangulo superior...");
        if (esCuadrada()) {
            BigDecimal temp[][] = new BigDecimal[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (i<=j) {
                        temp[i][j] = matriz[i][j];
                    } else {
                        temp[i][j] = BigDecimal.ZERO;
                    }
                }
            }
            Matriz mtrz = new Matriz(temp);
            System.out.println(mtrz.imprimirMatriz("Matriz triangular superior"));
            return mtrz;
        } else {
            throw new Exception("No es cuadrada.");
        }
    }
    
    public Matriz trianguloSuperiorEstricto() throws Exception{
        System.out.println("Obteniendo matriz de triangulo superior estricto...");
        Matriz mtrz = trianguloSuperior().restar(diagonal());
        System.out.println(mtrz.imprimirMatriz("Matriz triangula superior estricta"));
        return mtrz;
    }
    
    public Matriz trianguloInferior() throws Exception{
        System.out.println("Obteniendo matriz de triangulo inferior...");
        if (esCuadrada()) {
            BigDecimal temp[][] = new BigDecimal[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (i>=j) {
                        temp[i][j] = matriz[i][j];
                    } else {
                        temp[i][j] = BigDecimal.ZERO;
                    }
                }
            }
            Matriz mtrz = new Matriz(temp);
            System.out.println(mtrz.imprimirMatriz("Matriz triangular inferior"));
            return mtrz;
        } else {
            throw new Exception("No es cuadrada.");
        }
    }
    
    public Matriz trianguloInferiorEstricto() throws Exception{
        System.out.println("Obteniendo matriz de triangulo inferior estricto...");
        Matriz mtrz = trianguloInferior().restar(diagonal());
        System.out.println(mtrz.imprimirMatriz("Matriz triangula inferior estricta"));
        return mtrz;
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
        BigDecimal temp[][] = AB.getMatriz();
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
        System.out.println(AB.imprimirMatriz("Matriz "+nT+"x"+mT));
        return AB;
    }
    
    public static Matriz identidad(int n, int m){
        return new Matriz(n, m);
    }
    
    public static Matriz cero(int n, int m){
        BigDecimal temp[][] = new BigDecimal[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                temp[i][j] = BigDecimal.ZERO;
            }
        }
        return new Matriz(temp);
    }
    
    public Matriz transpuesta(){
        System.out.println("Obteniendo transpuesta...");
        BigDecimal[][] tempM = new BigDecimal[m][n];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                tempM[j][i] = matriz[i][j];
            }
        }
        Matriz mtrz = new Matriz(tempM);
        System.out.println(mtrz.imprimirMatriz("Matriz transpuesta"));
        return mtrz;
    }
    
    public Matriz inversa() throws Exception{
        System.out.println("Obteniendo inversa...");
        BigDecimal invDet = BigDecimal.ONE.divide(det(), 15, RoundingMode.UP);
        Matriz mtrz = adjunta().multipicar(invDet);
        System.out.println(mtrz.imprimirMatriz("Matriz inversa"));
        return mtrz;
    }
    
    public BigDecimal cofactor(int i, int j) throws Exception{
        System.out.println("Obteniendo cofactor "+i+","+j+"...");
        BigDecimal detRed = reducida(i, j).det();
        BigDecimal cof = detRed.multiply(BigDecimal.ONE.negate().pow(i+j));
        System.out.println("... cofactor "+i+","+j+" = "+cof);
        return cof;
    }
    
    public Matriz cofactor() throws Exception{
        System.out.println("Obteniendo matriz de cofactores...");
        BigDecimal temp[][] = new BigDecimal[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                temp[i][j] = cofactor(i,j);
            }
        }
        Matriz mtrz = new Matriz(temp);
        System.out.println(mtrz.imprimirMatriz("Matriz cofactor"));
        return mtrz;
    }
    
    public Matriz adjunta() throws Exception{
        System.out.println("Obteniendo matriz adjunta...");
        Matriz mtrz = cofactor().transpuesta();
        System.out.println(mtrz.imprimirMatriz("Matriz adjunta"));
        return mtrz;
    }
    
    public BigDecimal traza()throws Exception{
        System.out.print("Sacando el valor de la traza...");
        BigDecimal tr = BigDecimal.ZERO;
        if (!esCuadrada()) {
            throw new Exception("Matriz no cuadrada.");
        } else {
            for (int i = 0; i < n; i++) {
                tr = tr.add(getMatriz()[i][i]);
            }
            System.out.println(" "+tr);
            return tr;
        }
    }
    
    public Matriz abs(){
        System.out.println("Sacando valor absoluto a: "+this);
        BigDecimal temp[][] = new BigDecimal[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                temp[i][j] = getMatriz()[i][j].abs();
            }
        }
        Matriz mtrz = new Matriz(temp);
        System.out.println(mtrz.imprimirMatriz("Matriz con valor absoluto"));
        return mtrz;
    }
    
    public static Matriz abs(Matriz a){
        return a.abs();
    }
    
    public Matriz sumar(Matriz sumando) throws Exception{
        System.out.println("Sumando "+sumando);
        if((n==sumando.n)&&(m==sumando.m)){
            BigDecimal[][] matTemp = new BigDecimal[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    matTemp[i][j] = getMatriz()[i][j].add(sumando.getMatriz()[i][j]);
                }
            }
            Matriz mtrz = new Matriz(matTemp);
            System.out.println(mtrz.imprimirMatriz("Matriz resultante"));
            return mtrz;
        }else{
            throw new Exception("Tamaño(s) diferente(s): n:"+n+"!="+sumando.n
                    +" & m:"+m+"!="+sumando.m);
        }
    }
    
    public Matriz restar(Matriz sustraendo) throws Exception{
        return sumar(sustraendo.multipicar(-1));
    }
    
    public Matriz multipicar(int escalar){
        BigDecimal e = new BigDecimal(escalar);
        return multipicar(e);
    }
    
    public Matriz multipicar(float escalar){
        BigDecimal e = new BigDecimal(escalar);
        return multipicar(e);
    }
    
    public Matriz multipicar(double escalar){
        BigDecimal e = new BigDecimal(escalar);
        return multipicar(e);
    }
    
    public Matriz multipicar(BigDecimal escalar){
        System.out.println("Multiplicando por "+escalar);
        BigDecimal[][] matTemp = new BigDecimal[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matTemp[i][j] = getMatriz()[i][j].multiply(escalar);
            }
        }
        Matriz mtrz = new Matriz(matTemp);
        System.out.println(mtrz.imprimirMatriz("Matriz resultante"));
        return mtrz;
    }
    
    public Matriz multipicar(Matriz multiplicando) throws Exception{
        System.out.println("Multiplicando por "+multiplicando);
        if((m==multiplicando.n)){
            BigDecimal[][] matTemp = new BigDecimal[n][multiplicando.m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < multiplicando.m; j++) {
                    matTemp[i][j] = BigDecimal.ZERO;
                    for (int r = 0; r < m; r++) {
                        BigDecimal mult = multiplicando.getMatriz()[r][j];
                        BigDecimal toAdd = getMatriz()[i][r].multiply(mult);
                        matTemp[i][j] = matTemp[i][j].add(toAdd);
                    }
                }
            }
            Matriz mtrz = new Matriz(matTemp);
            System.out.println(mtrz.imprimirMatriz("Matriz resultante"));
            return mtrz;
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
    public BigDecimal det() throws Exception{
        System.out.println("Obteniendo determinate...");
        BigDecimal mTemp[][] = getMatriz();
        BigDecimal det = BigDecimal.ZERO;
        if(esCuadrada()){
            if (mTemp.length<2) {
                det = mTemp[0][0];
            } else if (mTemp.length==2) {
                BigDecimal d1 = mTemp[0][0].multiply(mTemp[1][1]);
                BigDecimal d2 = mTemp[0][1].multiply(mTemp[1][0]);
                det = d1.subtract(d2);
            } else {
                for (int i = 0; i < m; i++) {
                    BigDecimal asd = mTemp[0][i].multiply(reducida(0, i).det());
                    if (i%2==0) {
                        det = det.add(asd);
                    } else {
                        det = det.subtract(asd);
                    }
                }
            }
            System.out.println("... determinante = "+det);
            return det;
        }else{
            throw new Exception("Matriz no cuadrada.");
        }
    }
    
    public Matriz reducida(int i, int j) throws Exception{
        System.out.println("Reduciento matriz en "+i+", "+j+"...");
        BigDecimal mTemp[][] = getMatriz();
        BigDecimal matRed[][] = new BigDecimal[mTemp.length-1][mTemp[0].length-1];
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
            Matriz mtrz = new Matriz(matRed);
            System.out.println(mtrz.imprimirMatriz("Matriz reducida en "+i+", "+j));
            return mtrz;
        } else {
            throw new Exception("Indice(s) por fuera de la(s) dimension(es)");
        }
    }
    
    @Override
    public String toString(){
        return imprimirMatriz("Matriz "+n+"x"+m);
    }
    
}
