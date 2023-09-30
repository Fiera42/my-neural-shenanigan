public class Reseau {
    public static int tailleKernel = 200;
    public static int dureeMemoire = 100;

    public Harmonique[] tabHarmoniques;
    public Neuron[][] neurones;
    public int nbParametres;

    public Reseau() {
        this.tabHarmoniques = new Harmonique[Harmonique.freqMax];
        this.neurones = new Neuron[4][Harmonique.freqMax];
        this.nbParametres = 0;

        for(int harmonieCourante = 0; harmonieCourante < Harmonique.freqMax; harmonieCourante++) {
            this.tabHarmoniques[harmonieCourante] = new Harmonique(harmonieCourante + 1);

            this.neurones[0][harmonieCourante] = new Neuron(this.tabHarmoniques[harmonieCourante].harmoniques.length); //couche de neurones harmoniques
            this.nbParametres += this.tabHarmoniques[harmonieCourante].harmoniques.length + 1; //nb of weight + bias

            this.neurones[2][harmonieCourante] = new Neuron(dureeMemoire); //couche de neurones de memoire
            this.nbParametres += dureeMemoire + 1; //nb of weight + bias

        }

        if(tailleKernel % 2 != 0) tailleKernel++; //tailleKernel doit etre impaire

        for(int harmonieCourante = 0; harmonieCourante < Harmonique.freqMax; harmonieCourante++) {

            if(-(tailleKernel / 2) + harmonieCourante < 0) {
                this.neurones[1][harmonieCourante] = new Neuron(tailleKernel-(tailleKernel / 2) + harmonieCourante); //couche de neurone de convolution
                this.nbParametres += tailleKernel-(tailleKernel / 2) + harmonieCourante;

                this.neurones[3][harmonieCourante] = new Neuron(tailleKernel-(tailleKernel / 2) + harmonieCourante); //couche de neurone de convolution
                this.nbParametres += tailleKernel-(tailleKernel / 2) + harmonieCourante;
            } else 
            
            if((tailleKernel / 2) + harmonieCourante >= Harmonique.freqMax) {
                this.neurones[1][harmonieCourante] = new Neuron(tailleKernel - (((tailleKernel / 2) + harmonieCourante) - Harmonique.freqMax)); //couche de neurone de convolution
                this.nbParametres += tailleKernel - (((tailleKernel / 2) + harmonieCourante) - Harmonique.freqMax);

                this.neurones[3][harmonieCourante] = new Neuron(tailleKernel - (((tailleKernel / 2) + harmonieCourante) - Harmonique.freqMax)); //couche de neurone de convolution
                this.nbParametres += tailleKernel - (((tailleKernel / 2) + harmonieCourante) - Harmonique.freqMax);
            } else 
            
            {
                this.neurones[1][harmonieCourante] = new Neuron(tailleKernel); //couche de neurone de convolution
                this.nbParametres += tailleKernel;

                this.neurones[3][harmonieCourante] = new Neuron(tailleKernel); //couche de neurone de convolution
                this.nbParametres += tailleKernel;
            }
        }
    }

    public double[][] evalue(double[][] musique){ //amplitude en db[temps, en millieme de seconde][frequence en hz])

        double[][] memoire = new double[dureeMemoire][Harmonique.freqMax]; //initialise la memoire
        for(int t = 0; t < memoire.length; t++) {
            for(int frequence = 0; frequence < memoire[t].length; frequence++) { 
                memoire[t][frequence] = 0;
            }
        }

        
        double[][] res = new double[musique.length][Harmonique.freqMax];

        for(int t = 0; t < musique.length; t++) { //couche d'harmonie

            double[] temp = new double[Harmonique.freqMax];
            double[] input;

            for(int neuroneCourant = 0; neuroneCourant < Harmonique.freqMax; neuroneCourant++) { //effectue le calcul de la premiere couche
                input = new double[this.tabHarmoniques[neuroneCourant].harmoniques.length]; //initialise le tableau d'harmonie
                for(int harmonieCourante = 0; harmonieCourante < this.tabHarmoniques[neuroneCourant].harmoniques.length; harmonieCourante++) {
                    input[harmonieCourante] = musique[t][this.tabHarmoniques[neuroneCourant].harmoniques[harmonieCourante]]; //remplie le tableau de calcul avec les harmonies
                }
                temp[neuroneCourant] = this.neurones[0][neuroneCourant].compute(input); //sauvegarde les calculs
            }

            input = temp;
            temp = new double[Harmonique.freqMax];
            double[] convoTemp = new double[tailleKernel];

            for(int neuroneCourant = 0; neuroneCourant < Harmonique.freqMax; neuroneCourant++) { //couche de convolution
                if(-(tailleKernel / 2) + neuroneCourant < 0) {
                    convoTemp = new double[tailleKernel-(tailleKernel / 2) + neuroneCourant];
                    for(int i = 0; i < convoTemp.length; i++) {
                        convoTemp[i] = input[i];
                    }

                } else 

                if((tailleKernel / 2) + neuroneCourant >= Harmonique.freqMax) {
                    convoTemp = new double[tailleKernel - (((tailleKernel / 2) + neuroneCourant) - Harmonique.freqMax)];
                    for(int i = 0; i < convoTemp.length; i++) {
                        convoTemp[i] = input[neuroneCourant-(tailleKernel / 2) + i];
                    }
                } else 
                
                {
                    convoTemp = new double[tailleKernel];
                    for(int i = 0; i < convoTemp.length; i++) {
                        convoTemp[i] = input[neuroneCourant-(tailleKernel / 2) + i];
                    }
                }

                temp[neuroneCourant] = this.neurones[1][neuroneCourant].compute(convoTemp);
            }

            for(int i = memoire.length - 1; i > 0; i--) { //setup memory
                memoire[i] = memoire[i-1];
            }

            memoire[0] = temp;
            temp = new double[Harmonique.freqMax];
            input = new double[dureeMemoire];

            for(int frequenceCourante = 0; frequenceCourante < Harmonique.freqMax; frequenceCourante++) { //couche memoire
                for(int tempsMemoire = 0; tempsMemoire < dureeMemoire; tempsMemoire++) {
                    input[tempsMemoire] = memoire[tempsMemoire][frequenceCourante];
                }
                temp[frequenceCourante] = this.neurones[2][frequenceCourante].compute(input);
            }

            input = temp;
            temp = new double[Harmonique.freqMax];

            for(int neuroneCourant = 0; neuroneCourant < Harmonique.freqMax; neuroneCourant++) { //couche de convolution
                if(-(tailleKernel / 2) + neuroneCourant < 0) {
                    convoTemp = new double[tailleKernel-(tailleKernel / 2) + neuroneCourant];
                    for(int i = 0; i < convoTemp.length; i++) {
                        convoTemp[i] = input[i];
                    }

                } else 

                if((tailleKernel / 2) + neuroneCourant >= Harmonique.freqMax) {
                    convoTemp = new double[tailleKernel - (((tailleKernel / 2) + neuroneCourant) - Harmonique.freqMax)];
                    for(int i = 0; i < convoTemp.length; i++) {
                        convoTemp[i] = input[neuroneCourant-(tailleKernel / 2) + i];
                    }
                } else 
                
                {
                    convoTemp = new double[tailleKernel];
                    for(int i = 0; i < convoTemp.length; i++) {
                        convoTemp[i] = input[neuroneCourant-(tailleKernel / 2) + i];
                    }
                }

                temp[neuroneCourant] = this.neurones[3][neuroneCourant].compute(convoTemp);

                
            }

            if(t % 250 == 0) System.out.println("t = " + t);
            res[t] = temp;
        }

        return res;
    } 
}