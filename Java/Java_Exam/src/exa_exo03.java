import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Streams.
 * En utilisant que des streams, complétez le code pour qu'il compile et
 * affiche le résultat spécifié en commentaires (dans la méthode main).
 */
public class exa_exo03 {
    public static void main(String[] args) {
        System.out.println(average(Arrays.asList(1,3,7))); // doit afficher : 3.6666666666666665
        System.out.println(search(Arrays.asList("ooo", "123", "oooo", "aeo"))); // doit afficher : [ooo]
    }


    /**
     * Méthode qui retourne la moyenne des Integers de la liste reçue.
     * @return
     */
    // votre méthode 'average' ici ...
    public static double average(List<Integer> list){
        return list.stream().mapToInt(x->x).average().orElse(0);
    }


    /**
     * Méthode qui retourne une liste de tous les strings 
     * commencant par la lettre 'o' (minuscule) et qui ont exactement 3 caractères.
     * @return
     */
    // votre méthode 'search' ici ...
    public static String search(List<String> l){
        return "["+l.stream().filter(e->e.length()==3).filter(e->e.charAt(0)=='o').collect(Collectors.joining(" "))+"]";
    }
}