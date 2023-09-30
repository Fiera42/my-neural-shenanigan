import java.util.Random;
import java.math.*;

public class Convoluteur {
    
    private static Random random = new Random();
    private double[][] kernel;

    public Convoluteur() {
        this.kernel = new double[3][3];
        remplirKernel();
    }

    public Convoluteur(int x, int y) {
        this.kernel = new double[x][y];
        remplirKernel();
    }

    private void remplirKernel(){
        for(int x = 0; x < this.kernel.length; x++) {
            for(int y = 0; y < this.kernel[x].length; y++) {
                this.kernel[x][y] = random.nextDouble()*2 - 1;
            }
        }
    }
}