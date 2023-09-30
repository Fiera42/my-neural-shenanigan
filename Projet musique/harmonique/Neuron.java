import java.util.Random;
import java.math.*;

public class Neuron {

    private static Random random = new Random();

    private double[] poids;
    private double[] precPoids;
    private double biais, precBiais;

    public Neuron(int entree) {

        this.poids = new double[entree];
        this.precPoids = new double[entree];

        for(int poidCourant = 0; poidCourant < entree; poidCourant++){
            this.poids[poidCourant] = random.nextDouble()*2 - 1;
            this.precPoids[poidCourant] = random.nextDouble()*2 - 1;
        }

        this.biais = random.nextDouble()*2 - 1;
        this.precBiais = random.nextDouble()*2 - 1;
    }

    public double compute(double[] valeurs) { //must be the same size as the number of entry nodes
        double res = 0;
        for(int poidCourant = 0; poidCourant < this.poids.length; poidCourant++){
            res += valeurs[poidCourant] * this.poids[poidCourant];
        }
        res += biais;
        res = 1/(1 + Math.exp(-res)); //compress res between 0 and 1

        return res;
    }

    public void mutation(){
        int attributAMuter = random.nextInt(2);
        double mutation = random.nextDouble()*2 - 1;

        switch(attributAMuter) {
            case 0 :
                this.biais += mutation;
                break;
            case 1 :
                this.poids[random.nextInt(this.poids.length)] += mutation;
                break;
        }
    }

    public void demute() {
        this.biais = this.precBiais;

        for(int poidCourant = 0; poidCourant < this.poids.length; poidCourant++) {
            this.poids[poidCourant] = this.precPoids[poidCourant];
        }
    }

    public void sauvegarde(){
        this.precBiais = this.biais;

        for(int poidCourant = 0; poidCourant < this.poids.length; poidCourant++) {
            this.precPoids[poidCourant] = this.poids[poidCourant];
        }
    }

    public String toString() {
        String res = "Nombre de poids " + this.poids.length;
        return res;
    }
}