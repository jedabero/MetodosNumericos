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
    public static void main(String[] args) {
        double mat[][] = {
            {4,-1,0,0,1},
            {-1,4,-1,0,1},
            {0,-1,4,-1,1},
            {0,0,-1,4,1}};
        double mat1[][] = new double[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                mat1[i][j] = mat[i][j];
                
            }
            
        }
        Matriz m = new Matriz(mat1);
        m.metodoGauss();
        Matriz B = new Matriz(mat);
        B.metodoJordan();
    }
}
