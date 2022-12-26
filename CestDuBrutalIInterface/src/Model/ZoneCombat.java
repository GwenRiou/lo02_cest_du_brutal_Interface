package Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class ZoneCombat extends Zone implements Runnable{

    private ControleZone controleZone; // il renplacera la deuxieme ligne // CONTROLEPARJOUEUR1,CONTROLEPARJOUEUR2,DISPUTE;
    //private int controleZone; //0 controler par un joueur , 1 pas de controle
    private Partie partie;
    private Joueur controlePar;
    Thread t ;
    int numAction;
    

    public ZoneCombat(String name) {
        super(name);
        this.controleZone=ControleZone.DISPUTE;
        this.partie= Partie.getInstance();
        t = new Thread(this, name);
        numAction=0;
    }
    
    public void combat() {
        String nom = getZoneName();
        t.start();
    }
    
    public void interrupted() {          
            t.interrupt();
    }
    
    public void initialiser() {
        this.sortStudentList(etuDansZone);
    }
    
    public void frenesie() {
        System.out.println("######################################################################################################");
        System.out.println("                                         FRENESIE                                                     ");
        System.out.println("######################################################################################################");
        Iterator<Etudiant> it =etuDansZone.iterator();
        while(it.hasNext()){
            Etudiant etu = it.next();
            etu.setStrategie("OFFENSIVE");
            etu.setForce(etu.getForce()+100);
        }
        
    }
            
    public void run() {
        initialiser();
        int i = 0;
        while(controleZone == ControleZone.DISPUTE) {   // on se bat si la zone n'est pas contorlee     
            try { 
                 numAction++;
                 if(numAction>500) {
                     numAction=0;
                     frenesie();
                 }
                //action d'un combat
                
                this.etuDansZone.get(i).agir();//TODO DEPLACER A LA FIN DE LA LISTE apres agir
                i++;
                i = i%etuDansZone.size();
                        
                if (controleZone ==ControleZone.DISPUTE) {
                    partie.declencherTreve(this.controlePar,this, "Pas de treve");
                    //System.out.println(Thread.currentThread().getName() + " n'est pas controlee (prod)");
                }
                else {
                    
                    System.out.println(this.getZoneName() +  " est controlee par "+ this.controlePar.getUserName());
                    
                    partie.declencherTreve(this.controlePar,this, "0");    
                }
                
                //sleep present pour ralentir l'execution 
                Thread.sleep((long)(Math.random()*20));               
            } catch (InterruptedException e) {
                //e.printStackTrace();
                break;// on sort du while
            }           
        }
    }
    
    public boolean zoneWithTwoEtu(Joueur j) {
        Iterator<Etudiant> it =etuDansZone.iterator();
        while(it.hasNext()){
            Etudiant etu = it.next();
            if(etu.getBelongsTo()==j) return true; // on a un �tudiant du joueur deux dedans
        }
        return false;
    }

    public void displayECTS() {
        ArrayList<Etudiant>  etulist= this.etuDansZone;
        int nbECTS=0;
        int nbEtu=0;
        for (ListIterator<Etudiant> it = etulist.listIterator(); it.hasNext();) { //scan through all students
            Etudiant s = it.next();
            nbECTS+=s.getEcts();
            nbEtu++;
            
        }    
        System.out.println("nombre de credits ECTS "+nbECTS);
        System.out.println("nombre d'etudiant dans la zone "+nbEtu);
        System.out.println();
    }
   

    
    //getters
    public ControleZone getControleZone() {return this.controleZone;}
    public Joueur getControlePar() {return controlePar;}
    //setters
    public void setControleZone(ControleZone control,Joueur j) {this.controleZone = control;this.controlePar = j;}
    public void setControlePar(Joueur controlePar) {this.controlePar = controlePar;}

}
