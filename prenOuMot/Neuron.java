import java.util.Random;
import java.math.*;

public class Neuron {

    private static Random random = new Random();
    private double biais, poid1, poid2;
    private double precBiais, precPoid1, precPoid2;

    public Neuron() {
        this.biais = random.nextDouble()*2 - 1;
        this.poid1 = random.nextDouble()*2 - 1;
        this.poid2 = random.nextDouble()*2 - 1;
        this.precBiais = random.nextDouble()*2 - 1;
        this.precPoid1 = random.nextDouble()*2 - 1;
        this.precPoid2 = random.nextDouble()*2 - 1;
    }

    public double compute(double valeur1, double valeur2) {
        double res = this.poid1 * valeur1;
        res += this.poid2 * valeur2;
        res += this.biais;

        res = 1/(1 + Math.exp(-res)); //compress res between -1 and 1

        return res;
    }

    public void mutation(){
        int attributAMuter = random.nextInt(3);
        double mutation = random.nextDouble()*2 - 1;

        switch(attributAMuter) {
            case 0 :
                this.biais += mutation;
                break;
            case 1 :
                this.poid1 += mutation;
                break;
            case 2 :
                this.poid2 += mutation;
                break;
        }
    }

    public void demute() {
        this.biais = this.precBiais;
        this.poid1 = this.precPoid1;
        this.poid2 = this.precPoid2;
    }

    public void sauvegarde(){
        this.precBiais = this.biais;
        this.precPoid1 = this.poid1;
        this.precPoid2 = this.poid2;
    }

    public String toString() {
        String res = "";

        res += "biais[" + this.biais + "]\n";
        res += "poid1[" + this.poid1 + "]\n";
        res += "poid2[" + this.poid2 + "]";

        return res;
    }
}