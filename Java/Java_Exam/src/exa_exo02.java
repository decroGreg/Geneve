/**
 * Complétez le code pour qu'il compile et affiche les résultats donnés en commentaires.
 * Pour la classe Pomme, le paramètre du constructuer correspond à son âge et c'est le critère
 * de comparaison.
 */
public class exa_exo02 {

    public static void main(String[] args) {
        compare(3, 6); // affiche : 3 is lower than 6
        compare("Bob", "Bob"); // affiche : Bob is equal to Bob
        compare(new Pomme(124), new Pomme(9)); // affiche : Pomme(124) is greater than Pomme(9)
    }

    /**
     * Méthode qui compare 2 objets et affiche le résultat en console.
     */
    // votre méthode "compare" ici ...
    public static <V extends Object> void  compare(V ob1, V ob2){
        String a="";
        String b="";
        if(ob1==ob2||ob1.equals(ob2)){
            System.out.println(ob1.toString()+"is equal to "+ob2.toString());
            return;
        }
        if(ob1.getClass().getName().equals(Pomme.class.getName())){
            a=((Pomme)ob1).getAge()+"";
            b=((Pomme)ob1).getAge()+"";
        }
        else{
            a=ob1.toString();
            b=ob2.toString();
        }
        if(a.compareTo(b)>0){
            System.out.println(ob1.toString()+"is greater then "+ob2.toString());
            return;
        }



    }
}
