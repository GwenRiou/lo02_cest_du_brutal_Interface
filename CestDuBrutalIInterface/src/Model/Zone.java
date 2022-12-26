package Model;
import java.util.*;//for EVERYTHIGN



public class Zone {
    private String zoneName;
    protected ArrayList<Etudiant> etuDansZone;
    private static ArrayList<ZoneCombat> zoneList = new ArrayList<ZoneCombat>();
    
    //constructeur
    public Zone(String zoneName) {
        this.etuDansZone = new ArrayList<Etudiant>();
        this.zoneName = zoneName;
    }
    
    
    public void affectation(Etudiant etu) {
        this.etuDansZone.add(etu);
    }
    
    public static void setZones() {
        zoneList.add(new ZoneCombat("La Bibliotheque"));
        zoneList.add(new ZoneCombat("Le BDE"));
        zoneList.add(new ZoneCombat("Le Quartier Administratif"));
        zoneList.add(new ZoneCombat("Les Halles Industrielles"));
        zoneList.add(new ZoneCombat("La Halle Sportive"));
    }
    
    public static void displayAllZones(){
  
            Iterator<ZoneCombat> it =zoneList.iterator();
            while(it.hasNext()){
                Zone zoneTemp = it.next();
                System.out.println("- " + zoneTemp.getZoneName());
            }

   }
    public static void displayControlledZones(Joueur j) {
        Iterator<ZoneCombat> it =zoneList.iterator();
        while(it.hasNext()){
            ZoneCombat zoneTemp = it.next();
            if(zoneTemp.getControlePar() != null) {
                if(zoneTemp.getControlePar().equals(j))
                    System.out.println("- " + zoneTemp.getZoneName());
            }
        }
    }

    
    public static void displayActiveZones(Joueur j) {
        Iterator<ZoneCombat> it =zoneList.iterator();
        while(it.hasNext()){
            ZoneCombat zoneTemp = it.next();
            if(zoneTemp.getControlePar() == null) {
                    System.out.println("- " + zoneTemp.getZoneName());
            }
        }
    }
        
    public static Zone getZone(int index) { // called inside another getZone() to fetch the Student at the given iterator
        Zone zon = zoneList.get(index);
        return zon;
    }
    
    public Etudiant drawEtudiantDansZone(int index) {// called inside another getEtudiantDansZone() to fetch the Student at the given iterator 
        Etudiant etu = etuDansZone.get(index);         
        return etu;
    }
    public Etudiant drawEtudiantDansZone(Joueur j) throws StudentNotFoundInList{
        ArrayList<Etudiant>  etulist= this.etuDansZone;//
        boolean entryIsntValid = true;
        while (entryIsntValid) {
            try {//try finding a student in the zone
                int id = Integer.parseInt(Partie.getUserInput("Choisissez l'etudiant dans "+ this.zoneName));//will return a NumberFormatException if written wrong
                entryIsntValid = false;
                for (ListIterator<Etudiant> it = etulist.listIterator(); it.hasNext();) { //let's find the student
                    Etudiant s = it.next();
                    if((s.getId() == id) & j.equals(s.getBelongsTo())) { //TODO set student isInZone to the zone hes affected in
                        return drawEtudiantDansZone(it.previousIndex()); //return the student if the id condition is met
                    }
                    //if the id matches the student AND the student belongs to the player, return the student
                }//exits this loop if it hasnt found a student
            }
            catch (NumberFormatException e){
                System.out.println("Veuillez entrer un ID valide \n");               
            }
        }
        throw new StudentNotFoundInList();

    }
    
    
    public static void sortStudentList(ArrayList<Etudiant> studentListToSort) {
        studentListToSort.sort((etu1,etu2) -> etu2.getInitiative()-(etu1.getInitiative())); //j'ai pas cherche a comprendre en dÃ©tail la syntaxe....
    }
    
   
    public void removeStudentFromZone(Etudiant etu) {
        etuDansZone.remove(etu);
    }
    
    public static boolean allZoneNotEmpty() {        
        ListIterator<ZoneCombat> it =zoneList.listIterator();
        while(it.hasNext()){
            Zone zoneTemp = it.next();
            if(zoneTemp.getNombreEtu()==0) return false;// si on a une zone vide
        }
        return true;
    }
    public static boolean allZoneWithTwoStudent(Joueur j) {
        ListIterator<ZoneCombat> it =zoneList.listIterator();
        while(it.hasNext()){
            ZoneCombat zoneTemp = it.next();
            if(!zoneTemp.zoneWithTwoEtu(j)) return false; //si on a une zone ou il manque un étudiant du j2 
        }
        return true;
    }
    public static void displayAllStudentInZones() {
        Iterator<ZoneCombat> it =zoneList.iterator();
        while(it.hasNext()){
            Zone zoneTemp = it.next();
            zoneTemp.displayEtudiantDansZoneList();
        }
    }
    
    public static void melee() {
        Iterator<ZoneCombat> it =zoneList.iterator();
        while(it.hasNext()){
            ZoneCombat zoneTemp = it.next();
            zoneTemp.combat();
        }
    }

    public static void interrupteAll() {
        Iterator<ZoneCombat> it =zoneList.iterator();
        while(it.hasNext()){
            ZoneCombat zoneTemp = it.next();
            zoneTemp.interrupted();
        }
    }
    
    public static void initialiserZone() {// order the list of etu by initiative on every zone 
        Iterator<ZoneCombat> it = zoneList.iterator();
        while(it.hasNext()){
            ZoneCombat zoneTemp = it.next();
            zoneTemp.initialiser();
        }
    }

    public static boolean FinDePartie() {
        Iterator<ZoneCombat> it =zoneList.iterator();
        int numZoneControlByPlayer1=0;
        int numZoneControlByPlayer2=0;  
        int numZoneToEndGame=((zoneList.size()/2)+1);
        while(it.hasNext()){
            ZoneCombat zoneTemp = it.next();
            if(zoneTemp.getControleZone()==ControleZone.CONTROLEPARJOUEUR1) {numZoneControlByPlayer1++;};
            
            if(zoneTemp.getControleZone()==ControleZone.CONTROLEPARJOUEUR2) {numZoneControlByPlayer2++;};
        }
        System.out.println("Le nombre de zone controle par j1 = "+ numZoneControlByPlayer1);
        System.out.println("Le nombre de zone controle par j2 = "+ numZoneControlByPlayer2);
        if(numZoneControlByPlayer1>=numZoneToEndGame) {
            System.out.println( Partie.getNamePlayer(1) +" a gagne");
            return true;
        }
        if(numZoneControlByPlayer2>=numZoneToEndGame) {
            System.out.println( Partie.getNamePlayer(2) +" a gagne");
            
            return true;
        };
        return false;
        
    }


    public void displayEtudiantDansZoneList() {
        ArrayList<Etudiant>  etulist= this.etuDansZone;
        System.out.println("\n==Etudiants dans "+this.zoneName+":==");
        for (ListIterator<Etudiant> it = etulist.listIterator(); it.hasNext();) { //scan through all students
            Etudiant s = it.next();
            System.out.println(s);//use the tostring method to print the student's ids
        }    
    }
    public void displayEtudiantDansZoneList(Joueur j) {
        ArrayList<Etudiant>  etulist= this.etuDansZone;
        System.out.println("\n==Etudiants dans "+this.zoneName+":==");
        for (ListIterator<Etudiant> it = etulist.listIterator(); it.hasNext();) { //scan through all students
            Etudiant s = it.next();
            if(s.getBelongsTo()==j) System.out.println(s);//use the tostring method to print the student's ids
        }    
    }
    
    public static boolean displayECTSPerZone() {
        ListIterator<ZoneCombat> it =zoneList.listIterator();
        while(it.hasNext()){
            ZoneCombat zoneTemp = it.next();
            System.out.println("==Dans la zone "+zoneTemp.getZoneName()+":==");
            zoneTemp.displayECTS();
            }
        return true;
    }
    
    //getters
    public String getZoneName() {return zoneName;}
    public int getNombreEtu() {return this.etuDansZone.size();} 
    
    public ArrayList<Etudiant> getEtuDansZoneArrayList() {return etuDansZone;}
    //setters
    public void setZoneName(String zoneName) {this.zoneName = zoneName;}
    public static void setZoneList(ArrayList<ZoneCombat> zoneList) {Zone.zoneList = zoneList;}


    public static ArrayList<ZoneCombat> getZoneList() {return zoneList;}
    public void addEtudiantDansZone(Etudiant etudiant) {
        this.etuDansZone.add(etudiant);
        System.out.println("L'etudiant a bien ete ajoute a la zone");
    }
    
}


