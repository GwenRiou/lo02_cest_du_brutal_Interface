package Model;

public class ZoneNotFoundInList extends Exception{
    public ZoneNotFoundInList() {
        super("Cette zone n'existe pas.");//TODO changer cette phrase elle est moche
    }
}
