
package vectores;

import java.math.BigDecimal;
import java.util.Scanner;

import resources.O;

/**
 *
 * @author Jedabero
 */
@SuppressWarnings("javadoc")
public class Vector {
    
    private BigDecimal vector[];
    
    public BigDecimal[] getVector(){
        return vector;
    }
    
    public void setVector(BigDecimal v[]){
        this.vector = v;
    }
    
    private int lentgh;
    
    public int lentgh(){
        return lentgh;
    }
    
    public void setlentgh(int l){
        this.lentgh = l;
    }
    
    public Vector(int l, boolean ingresar){
        this.lentgh = l;
        vector = new BigDecimal[l];
        
        if(ingresar){
            ingresarVector();
        } else {
            //escVectorIdentidad();
        }
    }
    
    public Vector(BigDecimal v[]){
        this.vector = v;
        this.lentgh = v.length;
    }

    private void ingresarVector() {
        Scanner in = new Scanner(System.in);
        BigDecimal[] tempV = new BigDecimal[lentgh];
        for (int i = 0; i < lentgh; i++) {
            O.p("Ingrese V("+(i+1)+") ");
            tempV[i] = new BigDecimal(in.nextDouble());
        }
        in.close();
        setVector(tempV);
    }
    
}
