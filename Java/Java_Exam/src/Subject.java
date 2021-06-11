/**
 * Classe à compléter.
 */
public class Subject <V extends  SubjectTypes> {
    
    // membres ici ...
    private String titre;
    private V type;
    // constructeur ici ...
    public Subject(String s,V v){
        this.titre=s;
        this.type=v;
    }
    // méthode print (NE PAS MODIFIER)
    public void print() {
        System.out.println(titre +" "+ type.getSubject());
    }
}