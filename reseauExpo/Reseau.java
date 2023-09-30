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

    public Reseau(int entree, int sortie) {
        if(entree < 0) entree = (entree*entree)/2;
        if(entree == 0) entree = 1;
        if(sortie < 0) sortie = (sortie*sortie)/2;
        if(sortie == 0) sortie = 1;

        this.Neurones = new Neuron[entree + 2][];
        this.Neurones[0] = new Neuron[entree];
        this.Neurones[this.Neurones.length-1] = new Neuron[sortie];

        System.out.println("-------Creation du niveau 0--------");
        for(int i = 0; i < entree; i++){
            this.Neurones[0][i] = new Neuron(1);
            System.out.println("Creation du neurone " + i + " du niveau 0");
            System.out.println(this.Neurones[0][i]);
        }

        int tailleNiveau = entree;
        for(int niveauCourant = 1; niveauCourant < this.Neurones.length-1; niveauCourant++) {
            this.Neurones[niveauCourant] = new Neuron[tailleNiveau * entree];
            tailleNiveau--;
            System.out.println("\n--------Creation du niveau " + niveauCourant + "--------");
            System.out.println("[Taille du niveau " + this.Neurones[niveauCourant].length + "]\n");
            for(int neuroneCourant = 0; neuroneCourant < this.Neurones[niveauCourant].length; neuroneCourant++){
                this.Neurones[niveauCourant][neuroneCourant] = new Neuron(this.Neurones[niveauCourant-1].length);
                System.out.println("Creation du neurone " + neuroneCourant + " du niveau " + niveauCourant);
                System.out.println(this.Neurones[niveauCourant][neuroneCourant]);
            }
        }

        System.out.println("\n--------Creation du niveau " + (entree + 1) + "--------");
        for(int i = 0; i < sortie; i++){
            this.Neurones[entree + 1][i] = new Neuron(entree);
            System.out.println("Creation du neurone " + i + " du niveau " + (entree + 1));
            System.out.println(this.Neurones[entree + 1][i]);
        }
    }

    public double Prediction(int[] mot) { //take an ascii word the lenght of the neural network + 1
        if(mot.length != this.Neurones[0].length+1) return -1; //if word of invalid lenght, return error

        double[] res = new double[mot.length];
        double[] temp;
        for(int i = 0; i < mot.length; i++) res[i] = mot[i]; //setup the result tab

        for(int niveauCourant = 0; niveauCourant < this.Neurones.length; niveauCourant++){
            temp = new double[this.Neurones[niveauCourant].length];
            for(int neuroneCourant = 0; neuroneCourant < this.Neurones[niveauCourant].length; neuroneCourant++){
                temp[neuroneCourant] = this.Neurones[niveauCourant][neuroneCourant].compute(res);
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
}