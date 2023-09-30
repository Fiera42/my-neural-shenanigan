public class Harmonique {

    public static int freqMax = 20000;
    public int[] harmoniques;

    public Harmonique(int frequence) {

        int tailleTab = freqMax/frequence;

        this.harmoniques = new int[tailleTab];

        for(int i = 0; i < tailleTab; i++) {
            this.harmoniques[i] = (frequence * (i+1))-1;
            //System.out.println("[" + i + "]" + (frequence * (i + 1)));
        }
    }
}