package Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class Reserve extends Zone {
    
   
    //instantiation
    public Reserve() {
     super("Reserve");
    }

    
    

    
    
    public boolean isEmpty() {
       return this.etuDansZone.isEmpty(); 
   }
    
    public void affecterReserve(Etudiant etudiant) {
        this.etuDansZone.add(etudiant);
    }
    
    //TODO    /!\ à changer: ID étudiant 
    public void removeReserve(Etudiant etudiant) {
        this.etuDansZone.remove(etudiant); 
    }
  //Getter & setter
    public ArrayList<Etudiant> getListeEtudiantsReserve() {
         return this.etuDansZone;
     }
     public void setListeEtudiantsReserve(ArrayList<Etudiant> listeEtudiantsReserve) {
         this.etuDansZone = listeEtudiantsReserve;
     }
     public int getNombreEtuReserve() {return this.etuDansZone.size();}
} 
