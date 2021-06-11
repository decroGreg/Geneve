/**
 * Generics.
 * Complétez le code pour qu'il compile et affiche les résultats donnés en commentaires.
 */
public class exa_exo01 {
    public static void main(String[] args) {
        Subject<Nature> s1 = new Subject<>("Pourquoi le ciel est bleu ?", new Nature());
        s1.print(); // affiche : Pourquoi le ciel est bleu ?  [Nature]
        Subject<Mathematics> s2 = new Subject<>("Principes de soustraction !", new Mathematics());
        s2.print(); // affiche : Principes de soustraction !  [Mathematics]
    }
}