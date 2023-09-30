import java.util.Random;
public class Reseau {
    private Neuron[][] Neurones;
    private static Random random = new Random();

    public static double meanSquareLoss(double[] predictions, double[] reponses) {
        double res = 0;
        for(int i = 0; i < predictions.length; i++){
            double erreur = reponses[i] - predictions[i];
            res += (erreur * erreur);
        }
        return res / (predictions.length);
    }

    public Reseau(int nbNiveaux) {
        if(nbNiveaux < 0) nbNiveaux = (nbNiveaux*nbNiveaux)/2;
        if(nbNiveaux == 0) nbNiveaux = 1;

        this.Neurones = new Neuron[nbNiveaux][];

        for(int niveauCourant = 0; niveauCourant < nbNiveaux; niveauCourant++) {
            this.Neurones[niveauCourant] = new Neuron[nbNiveaux-niveauCourant];
            System.out.println("Creation du niveau " + niveauCourant);
            System.out.println("Taille du niveau " + this.Neurones[niveauCourant].length);
            for(int neuroneCourant = 0; neuroneCourant < this.Neurones[niveauCourant].length; neuroneCourant++){
                this.Neurones[niveauCourant][neuroneCourant] = new Neuron();
                System.out.println("Creation du neurone " + neuroneCourant + " du niveau " + niveauCourant);
            }
        }
    }

    public double Prediction(int[] mot) { //take an ascii word the lenght of the neural network + 1
        if(mot.length != this.Neurones.length+1) return -1; //if word of invalid lenght, return error

        double[] res = new double[mot.length];
        double[] temp;
        for(int i = 0; i < mot.length; i++) res[i] = mot[i]; //setup the result tab

        for(int niveauCourant = 0; niveauCourant < this.Neurones.length; niveauCourant++){
            temp = new double[this.Neurones[niveauCourant].length];
            for(int neuroneCourant = 0; neuroneCourant < this.Neurones[niveauCourant].length; neuroneCourant++){
                temp[neuroneCourant] = this.Neurones[niveauCourant][neuroneCourant].compute(res[neuroneCourant], res[neuroneCourant+1]);
            }
            res = temp;
        }
        return res[0];
    }

    public void entrainement(int[][] donnee, double[] reponse){

        double meilleurResultat = -666;
        
        for(int nbEntrainement = 0; nbEntrainement < 1000000; nbEntrainement++){
            int mutationLayer = random.nextInt(this.Neurones.length);
            int mutationPosition = random.nextInt(this.Neurones[mutationLayer].length);

            Neuron neuroneMuter = this.Neurones[mutationLayer][mutationPosition];
            neuroneMuter.mutation();

            double[] predictions = new double[donnee.length];
            for(int i = 0; i < donnee.length; i++){
                predictions[i] = this.Prediction(donnee[i]);
            }

            double margeErreur = meanSquareLoss(predictions, reponse);

            if(meilleurResultat == -666) {
                meilleurResultat = margeErreur;
                neuroneMuter.sauvegarde();
            } else {
                if (margeErreur < meilleurResultat) {
                    meilleurResultat = margeErreur;
                    neuroneMuter.sauvegarde();
                } else {
                    neuroneMuter.demute();
                }
            }
        }
    }

    public String toString() {
        String res = "";

        for(int niveauCourant = 0; niveauCourant < this.Neurones.length; niveauCourant++) {
            res += "------------NIVEAU " + niveauCourant + "-----------\n";
            for(int neuroneCourant = 0; neuroneCourant < this.Neurones[niveauCourant].length; neuroneCourant++){
                res += this.Neurones[niveauCourant][neuroneCourant];
                if(!((neuroneCourant == this.Neurones[niveauCourant].length-1) && (niveauCourant == this.Neurones.length -1))) 
                    res += "\n\n";
            }
        }

        return res;
    }
}