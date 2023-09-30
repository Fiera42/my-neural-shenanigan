public class Prog {
    public static void main(String[] args) {

        Reseau reseau = new Reseau(4);

        int[][] motEntrainement = {
            {97, 114, 98, 114, 101}, //arbre
            {102, 108, 97, 110, 99}, //flanc
            {101, 99, 114, 105, 115}, //ecris
            {111, 117, 98, 108, 105}, //oubli
            {97, 112, 112, 97, 116}, //appat
            {98, 101, 109, 111, 108}, //bemol
            {99, 111, 108, 105, 115}, //colis
            {101, 112, 105, 99, 101}, //epice
            {103, 111, 98, 101, 114}, //gober
            {106, 117, 115, 116, 101}, //juste

            {76, 111, 103, 97, 110}, //Logan
            {74, 97, 99, 111, 98}, //Jacob
            {70, 101, 108, 105, 120}, //Felix
            {76, 117, 99, 97, 115}, //Lucas
            {69, 109, 105, 108, 101}, //Emile
            {66, 105, 108, 108, 121}, //Billy
            {69, 116, 104, 97, 110}, //Ethan
            {76, 111, 117, 105, 115}, //Louis
            {74, 97, 109, 101, 115}, //James
            {68, 121, 108, 97, 110} //Dylan
        };

        double[] reponse = {0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,};

        System.out.println("Reseau cree\n");

        reseau.entrainement(motEntrainement, reponse);

        int[] mot = {69, 100, 100, 105, 101};
        System.out.println("Eddie :" + reseau.Prediction(mot));

        mot[0] =76;
        mot[1] =117;
        mot[2] =99;
        mot[3] =97;
        mot[4] =115;
        System.out.println("Lucas :" + reseau.Prediction(mot));

        mot[0] =78;
        mot[1] =111;
        mot[2] =108;
        mot[3] =97;
        mot[4] =110;
        System.out.println("Nolan :" + reseau.Prediction(mot));

        mot[0] =99;
        mot[1] =114;
        mot[2] =101;
        mot[3] =112;
        mot[4] =101;
        System.out.println("crepe :" + reseau.Prediction(mot));

        mot[0] =101;
        mot[1] =120;
        mot[2] =105;
        mot[3] =103;
        mot[4] =117;
        System.out.println("exigu :" + reseau.Prediction(mot));

        mot[0] =106;
        mot[1] =111;
        mot[2] =117;
        mot[3] =101;
        mot[4] =116;
        System.out.println("jouet :" + reseau.Prediction(mot));
    }
}