import java.util.Random;

public class Test {
    public static void main(String[] args) {

        double[][] musique = new double[1000][Harmonique.freqMax];
        Random random = new Random();

        for(int x = 0; x < musique.length; x++) {
            for(int y = 0; y < Harmonique.freqMax; y++) {
                musique[x][y] = random.nextDouble()*100;
            }
        }

        Reseau reseau = new Reseau();
        System.out.println(reseau.nbParametres + " Parametres cree");

        reseau.evalue(musique);
    }
}