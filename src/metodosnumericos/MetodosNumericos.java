/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metodosnumericos;

/**
 *
 * @author jberdugo10
 */
public class MetodosNumericos {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        double mat[][] = {
            {4,-1,0,0,1},
            {-1,4,-1,0,1},
            {0,-1,4,-1,1},
            {0,0,-1,4,1}};
        double mat1[][] = new double[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            System.arraycopy(mat[i], 0, mat1[i], 0, mat[0].length);
        }
        SistemaEcuacionesLineales m = new SistemaEcuacionesLineales(mat1);
        m.metodoGauss();
        SistemaEcuacionesLineales B = new SistemaEcuacionesLineales(mat);
        B.metodoJordan();
        
        double mat3[][] = {
            {1,-0.25,0,0,0.25},
            {-0.25,1,-0.25,0,0.25},
            {0,-0.25,1,-0.25,0.25},
            {0,0,-0.25,1,0.25}};
        SistemaEcuacionesLineales sel = new SistemaEcuacionesLineales(mat3);
        sel.metodoJacobi(15, 0.001d);
        
        sel.getMatrizAmpliada().imprimirMatriz("Matriz");
        sel.getMatrizCoef().imprimirMatriz("Matriz de Coeficientes");
        sel.getMatrizCoef().inversa().imprimirMatriz("Matriz inversa de Coeficientes");
        sel.getVectorB().imprimirMatriz("Vector de constantes");
        sel.getMatrizCoef().inversa().multipicar(sel.getVectorB()).imprimirMatriz("Hope this works");
        
    }
}
