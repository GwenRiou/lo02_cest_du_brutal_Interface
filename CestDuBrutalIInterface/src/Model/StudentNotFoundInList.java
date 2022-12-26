package Model;

public class StudentNotFoundInList extends Exception{
    public StudentNotFoundInList() {
        super("Etudiant inexistant dans cette liste \n");//TODO changer cette phrase elle est moche
    }
}
