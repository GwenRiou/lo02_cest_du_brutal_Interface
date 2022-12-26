package Model;
import java.lang.Math;
import java.util.*;

public class Etudiant implements Strategie{
    
	
    private String type;
    private int id;
    private int ects=30;
    private int force;
    private int dexterite;
    private int resistance;
    private int constitution;
    private int initiative;
    
    private Joueur belongsTo ; // set 1 pour j1 et set � 2 pour j2
    private Zone isInZone;
    private enumStrategie strategie;
    

    // constructeur 
    //ects = 30 pour tous les �tudiants donc pas dans l'appel construction
    public Etudiant(String type, int force, int dexterite, 
            int resistance, int constitution, int initiative,Joueur idJoueur) {
        this.type = type;
        this.ects = ects;
        this.force = force;
        this.dexterite = dexterite;
        this.resistance = resistance;
        this.constitution = constitution;
        this.initiative = initiative;
        this.belongsTo = idJoueur;
        this.strategie = enumStrategie.RANDOM;
        this.isInZone = new Zone("le camion");
    }
    
    

    public Etudiant() {
        
    }
    
    public String toString() {
        StringBuffer sb = new StringBuffer ("L'Etudiant n# ");
        sb.append(this.id);
        sb.append(" est de type : " );
        sb.append(this.type);
        sb.append(" a PV : ");
        sb.append(this.ects);
        sb.append(", Force : ");
        sb.append(this.force);
        sb.append(", Dexterite : ");
        sb.append(this.dexterite);
        sb.append(", Resistance : ");
        sb.append(this.resistance);
        sb.append(", Constitution : ");
        sb.append(this.constitution);
        sb.append(", Initiative : ");
        sb.append(this.initiative);
        sb.append(", Strategie : ");
        sb.append(this.strategie);
        sb.append(", Joueur : ");
        sb.append(this.belongsTo.getUserName());
        
            
        return sb.toString();
    }
    
    

    public void displayCaracteristics(){
        System.out.println(getType()+getEcts()+getForce()+getDexterite()+getResistance()+getConstitution()+getInitiative()+getStrategieString());
    }

    private void attack(ZoneCombat zone) {//only attack student inside zone
        
        ArrayList<Etudiant> enemyTeam = new ArrayList<Etudiant>();
        Iterator<Etudiant> it = zone.getEtuDansZoneArrayList().iterator();
        Etudiant enemyEtu;
        while(it.hasNext()) {//insert all enemies into a list
           enemyEtu = it.next();
           if(!enemyEtu.belongsTo.equals(this.belongsTo)) {
               enemyTeam.add(enemyEtu);
           }
        }
        Iterator<Etudiant> it2 = enemyTeam.iterator();
        Etudiant target = enemyTeam.get(0);
        Etudiant etudiantTemp;
        while(it2.hasNext()) {//scan for the student with the least HP
            etudiantTemp = it2.next();
            //System.out.println(target.id+"ects:"+target.ects+target.belongsTo.getUserName());
            if (etudiantTemp.ects<=target.ects) {
                target = etudiantTemp; 
            }
        }
        //attacking...
        //System.out.println(this.belongsTo.getUserName()+" : "+this.id+" Tente d'attaquer");
        final int damageReference = 10;
        double damageCoefficient = Math.max(0, Math.min(100, 10*this.force-5*target.resistance));
        double y = Math.random();
        int x = (int) (Math.random()*100);
        int damageTaken = (int) ((y*(1+damageCoefficient))*damageReference);        
        if (x>0 && x<(40 + 3*this.dexterite)) {
            target.ects -= damageTaken;
            System.out.println(this.belongsTo.getUserName()+" : "+this.getId()+" attacks "+target.id+", dealing "+ damageTaken + "HP");
        }
        if (target.ects <= 0) {//kill student
            System.out.println(target.belongsTo.getUserName()+"'s etu #"+target.getId()+" was killed by"+this.id);//
            target.isInZone.getEtuDansZoneArrayList().remove(target);
            //=====count number of players alive
            
            int studentCountj1 = 0;
            int studentCountj2 = 0;
            Iterator<Etudiant> iter = zone.getEtuDansZoneArrayList().iterator();
            Etudiant etu;
            do{//insert all enemies into a list
               etu = iter.next();
               if(etu.belongsTo.equals(Partie.getInstance().getListJ().get(0))) {
                   studentCountj1++;
                   //System.out.println(enemyEtu.belongsTo.getUserName()+"etu #"+enemyEtu.getId()+"enemy added");
               }
               else {
                   studentCountj2++;
               }
            }while(iter.hasNext());
            if (studentCountj1 == 0) {
                zone.setControleZone(ControleZone.CONTROLEPARJOUEUR2,this.belongsTo);
            }
            else if(studentCountj2 == 0) {
                zone.setControleZone(ControleZone.CONTROLEPARJOUEUR1,this.belongsTo);
            }
        
        }   
        
    } 
    
    private void heal(Zone zone) {//only attack student inside zone
        ArrayList<Etudiant> allyTeam = new ArrayList<Etudiant>();
        Iterator<Etudiant> it = zone.getEtuDansZoneArrayList().iterator();
        Etudiant allyEtu;
        while(it.hasNext()) {//insert all enemies into a list
            allyEtu = it.next();
           if(allyEtu.belongsTo.equals(this.belongsTo)) {
               allyTeam.add(allyEtu);
           }
        }
        Iterator<Etudiant> it2 = allyTeam.iterator();
        Etudiant target = allyTeam.get(0);
        Etudiant etudiantTemp;
        while(it.hasNext()) {//scan for the student with the least HP
            etudiantTemp = it2.next();
            if (etudiantTemp.ects<=target.ects) {
                target = etudiantTemp;
            }
        }
        //healing...
        System.out.println(this.belongsTo.getUserName()+" : "+this.id+" Tente de soigner");
        int healAmount;
        int x = (int) (Math.random()*100);
        double y = Math.random()*0.6;//0<y<0,6
        if (x>0 && x<(20 + 6*this.dexterite)) {
            healAmount = (int) (y*(10+target.constitution));
            if (healAmount < (30 + target.constitution)) {
                target.ects +=healAmount;
            }
            else {
                healAmount = 30 + this.constitution;
                target.ects += healAmount;
            }
            System.out.println("etu #"+target.getId()+" Got healed "+healAmount+" HP!");
        }
        System.out.println(this.id+"");
    } 
    
    public void agir() {
        if (this.strategie == strategie.OFFENSIVE) {
            attack((ZoneCombat)this.isInZone);
        }
        else if (this.strategie == strategie.DEFENSIVE) {
            heal(this.isInZone);
        }
        else if (this.strategie == strategie.RANDOM) {
            if(Math.random()>0.5) {
                
                attack((ZoneCombat)this.isInZone);
                
            }
            else {
                
                heal(this.isInZone);
                
            }
        }
        
    }
    
    
    
    
    
    
    //getters & setters
    
    public String getType(){return this.type;}
    public int getEcts(){return this.ects;}
    public int getForce(){return this.force;}
    public int getDexterite(){return this.dexterite;}
    public int getResistance(){return this.resistance;}
    public int getConstitution(){return this.constitution;}
    public int getInitiative(){return this.initiative;}
    public int getId() {return id;}
    public enumStrategie getStrategie() {return strategie;}
    public String getStrategieString() {return strategie.toString();}
    public Joueur getBelongsTo() {return belongsTo;}
    public Zone getIsInZone() {return isInZone;}

    public void setIsInZone(Zone isInZone) {this.isInZone = isInZone;}
    public void setForce(int newForce){this.force=newForce;}
    public void setDexterite(int newDexterite){this.dexterite= newDexterite;}
    public void setResistance(int newResistance){this.resistance= newResistance;}
    public void setConstitution(int newConstisution){this.constitution= newConstisution;}
    public void setInitiative(int newInitiative){this.initiative= newInitiative;}
    public void setId(int id) {this.id = id;}
    public void setStrategie(String strategie) {
        try{
            this.strategie = enumStrategie.valueOf(strategie.toUpperCase());
        }
        catch (IllegalArgumentException e) {
            System.out.println("Veuillez entrer une strategie valide");
        }
    }
}
