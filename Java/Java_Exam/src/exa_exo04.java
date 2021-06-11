import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Time/Date.
 * Complétez le code pour qu'il compile et affiche les résultats donnés en commentaires.
 */
public class exa_exo04 {
    
    public static void main(String[] args) {
        compare("01-01-2019", "02-02-2020"); // affiche : 2019-01-01 is before 2020-02-02
        compare("01-01-2019", "01-01-2019"); // affiche : 2019-01-01 is equal to 2019-01-01
        compare("02-02-2020", "01-01-2019"); // affiche : 2020-02-02 is after 2019-01-01
    }

    /**
     * Méthode 'compare' qui compare deux dates au format String et affiche le résultat.
     */
    // votre code ici ...
    public static void compare(String s1,String s2){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate d1=LocalDate.parse(s1,formatter);
        LocalDate d2=LocalDate.parse(s2,formatter);
        if(d1.isAfter(d2)){
            System.out.println(d1.toString()+" is after "+d2.toString());
            return;
        }else if(d1.isBefore(d2)){
            System.out.println(d1.toString()+" is before "+d2.toString());
            return;
        }
        System.out.println(d1.toString()+" is equal "+d2.toString());

    }
}