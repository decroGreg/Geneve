/**
 * Classe à compléter.
 */
public class Pomme extends SubjectTypes{
    private int age;
    public Pomme(int qt) {
        age =qt;
    }
    public int getAge(){
        return age;
    }
    @Override
    public String getSubject() {
        return "["+getClass()+"]";
    }
    public String toString(){
        return "pomme("+ age +")";
    }
}