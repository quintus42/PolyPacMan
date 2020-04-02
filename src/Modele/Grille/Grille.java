/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele.Grille;

import Modele.Case.CaseStatique;
import Modele.Case.Couloir;
import Modele.Case.Mur;
import Modele.Case.PacGomme;
import Modele.Case.SuperPacGomme;
import Modele.Configuration;
import Modele.Entite.Blinky;
import Modele.Entite.Clyde;
import Modele.Entite.Entite;
import Modele.Entite.Inky;
import Modele.Entite.PacMan;
import Modele.Entite.Pinky;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Epulapp
 */
public class Grille extends Observable implements Runnable{

    public CaseStatique[][] tabCaseStatique;
    public HashMap<Modele.Entite.Entite, Point> tabEntites;
    public HashMap<Point, Modele.Entite.Entite> tabPosition;
    public boolean partieEnCours = false;
    private boolean superPacGomme = false;
    private boolean jeuEnPause = false;
    private Thread tInky;
    private Thread tPinky;
    private Thread tBlinky;
    private Thread tClyde;
    
    public Grille(){
        tabCaseStatique = new CaseStatique[Configuration.LARGEUR_GRILLE][Configuration.HAUTEUR_GRILLE];
        tabEntites = new HashMap<Modele.Entite.Entite, Point>();
        tabPosition = new HashMap<Point, Modele.Entite.Entite>();

        Point position;
        
        //Pacman
        position = new Point(1,1);
        tabEntites.put(new Modele.Entite.PacMan(), position);
        tabPosition.put(position, new Modele.Entite.PacMan());
        
        //Blinky
        position = new Point(8,10);
        Blinky blinky = new Blinky();
        tabEntites.put(blinky, position);
        tabPosition.put(position, blinky);
        
        //Pinky
        position = new Point(9,10);
        Pinky pinky = new Pinky();
        tabEntites.put(pinky, position);
        tabPosition.put(position, pinky);
        
        //Inky
        position = new Point(10,10);
        Inky inky = new Inky();
        tabEntites.put(inky, position);
        tabPosition.put(position, inky);
        
        //Clyde
        position = new Point(11,10);
        Clyde clyde = new Clyde();
        tabEntites.put(clyde, position);
        tabPosition.put(position, clyde);
        
        tClyde = new Thread(clyde);
        tPinky = new Thread(pinky);
        tInky = new Thread(inky);
        tBlinky = new Thread(blinky);
        
        tClyde.start();
        tPinky.start();
        tInky.start();
        tBlinky.start();
        
    }
    
    public boolean depOk(Modele.Entite.Entite e){
        Point position = tabEntites.get(e);
        switch(e.direction){
            case HAUT:
                position = position.add(0, -e.vitesse);
                break;
            case BAS:
                position = position.add(0, e.vitesse);
                break;
            case DROITE:
                position = position.add(e.vitesse, 0);
                break;
            case GAUCHE:
                position = position.add(-e.vitesse, 0);
                break;
            case AUCUNE :
                return false;
        }
        position = gestionBordGrille(position);
        
        Iterator<HashMap.Entry<Point, Entite> > 
            iterator = tabPosition.entrySet().iterator(); 
  
        // flag to store result 
        boolean isKeyPresent = false; 
  
        // Iterate over the HashMap 
        while (iterator.hasNext()) { 
  
            // Get the entry at this iteration 
            HashMap.Entry<Point, Entite> 
                entry 
                = iterator.next(); 
  
            // Check if this key is the required key 
            if (position.equals(entry.getKey()) && (e instanceof PacMan || entry.getValue() instanceof PacMan)) { 
  
                isKeyPresent = true; 
            } 
        } 
        if (isKeyPresent) {
            //Si Pacman a manger une super Pac-Gomme, les fantômes sont vulnérables
            if (!superPacGomme) {
                //Gestion de la fin de la partie
                partieEnCours = false;
                return false;
            }
        }
//        if (tabPosition.containsKey(position)) {
//            //Si Pacman a manger une super Pac-Gomme, les fantômes sont vulnérables
//            if (!superPacGomme) {
//                //Gestion de la fin de la partie
//                partieEnCours = false;
//                return false;
//            }
//        }
        if(tabCaseStatique[position.getX()][position.getY()] instanceof Modele.Case.Mur){
            //On ne peut pas aller dans un mur
            return false;
        }else if(!(tabCaseStatique[position.getX()][position.getY()] instanceof Modele.Case.Couloir)){
            //Gestion des différents bonus et des points (Pac-Gommes + Super Pac-Gommes + Fruits)
            return true;
        }else{
            //On est dans le cas ou la case est vide
            return true;
        }
        //return false;
    }
    
    public Point getPositionPacman(){
        Iterator<HashMap.Entry<Point, Entite> > 
            iterator = tabPosition.entrySet().iterator(); 
  
        while (iterator.hasNext()) { 
  
            HashMap.Entry<Point, Entite> 
                entry 
                = iterator.next(); 
            
            if (entry.getValue() instanceof PacMan) { 
               return entry.getKey();
            } 
        }
        return null;
    }

    private Point gestionBordGrille(Point position) {
        
        if (position.getX() < 0) {
            position.setX(Configuration.LARGEUR_GRILLE-1);
        }else if(position.getX() > Configuration.LARGEUR_GRILLE-1){
            position.setX(0);
        }
        
        if (position.getY() < 0) {
            position.setY(Configuration.HAUTEUR_GRILLE-1);
        }else if(position.getY() > Configuration.HAUTEUR_GRILLE-1){
            position.setY(0);
        }
        
        return position;
    }
    
    private void replaceEntiteTabPosition(Entite e, Point p, Entite newE, Point newP){
//        tabPosition.forEach((k, v) -> {
//            if (k == p || v == e) {
//                tabPosition.remove(k, v);
//                tabPosition.put(newP, newE);
//            }
//        });
        tabPosition.entrySet().removeIf(ent -> (ent.getKey() == p || ent.getValue() == e));
        tabPosition.put(newP, newE);
    }
    
    public void changerDirection(Entite e, Modele.Entite.Direction d){
        e.direction = d;
        tabEntites.replace(e, tabEntites.get(e));
        replaceEntiteTabPosition(e, tabEntites.get(e), e, tabEntites.get(e));
        //tabPosition.replace(tabEntites.get(e), e);
    }
    
    public void changerDirectionPacman(Modele.Entite.Direction d){
        Entite e = null;
        for(Entite ent: tabEntites.keySet()){
            if (ent instanceof PacMan) {
                e = ent;
            }
        }
        if (e != null) {
            e.direction = d;
            tabEntites.replace(e, tabEntites.get(e));
            replaceEntiteTabPosition(e, tabEntites.get(e), e, tabEntites.get(e));
            //tabPosition.remove(tabEntites.get(e));
            //tabPosition.put(tabEntites.get(e), e);
        }
    }
    
    public void changerPositionPacman(Point p){
        Entite e = null;
        for(Entite ent: tabEntites.keySet()){
            if (ent instanceof PacMan) {
                e = ent;
            }
        }
        if (e != null) {
            Point ancienPoint = tabEntites.get(e);
            tabEntites.replace(e, p);
            replaceEntiteTabPosition(e, ancienPoint, e, p);
            //tabPosition.remove(ancienPoint);
            //tabPosition.put(p, e);
        }
    }
    
    public void changerPositionEntite(Point p, String nom){
        Entite e = null;
        for(Entite ent: tabEntites.keySet()){
            switch(nom){
                case "PacMan":
                    if (ent instanceof PacMan) {
                        e = ent;
                    }
                    break;
                case "Blinky":
                    if (ent instanceof Blinky) {
                        e = ent;
                    }
                    break;
                case "Pinky":
                    if (ent instanceof Pinky) {
                        e = ent;
                    }
                    break;
                case "Inky":
                    if (ent instanceof Inky) {
                        e = ent;
                    }
                    break;
                case "Clyde":
                    if (ent instanceof Clyde) {
                        e = ent;
                    }
                    break;
            }
        }
        if (e != null) {
            Point ancienPoint = tabEntites.get(e);
            tabEntites.replace(e, p);
            replaceEntiteTabPosition(e, ancienPoint, e, p);;
        }
    }
    
    private void deplacementEntite(Entite e) {
        Point oldPos = tabEntites.get(e);
        Point newPos = null;
        switch(e.direction){
            case HAUT:
                newPos = oldPos.add(0, -e.vitesse);
                newPos = gestionBordGrille(newPos);
                tabEntites.replace(e, oldPos, newPos);
                replaceEntiteTabPosition(e, oldPos, e, newPos);
                //tabPosition.remove(oldPos);
                //tabPosition.put(oldPos.add(0, -e.vitesse), e);
                break;
            case BAS:
                newPos = oldPos.add(0, e.vitesse);
                newPos = gestionBordGrille(newPos);
                tabEntites.replace(e, oldPos, newPos);
                replaceEntiteTabPosition(e, oldPos, e, newPos);
                //tabPosition.remove(oldPos);
                //tabPosition.put(oldPos.add(0, e.vitesse), e);
                break;
            case DROITE:
                newPos = oldPos.add(e.vitesse, 0);
                newPos = gestionBordGrille(newPos);
                tabEntites.replace(e, oldPos, newPos);
                replaceEntiteTabPosition(e, oldPos, e, newPos);
                //tabPosition.remove(oldPos);
                //tabPosition.put(oldPos.add(e.vitesse, 0), e);
                break;
            case GAUCHE:
                newPos = oldPos.add(-e.vitesse, 0);
                newPos = gestionBordGrille(newPos);
                tabEntites.replace(e, oldPos, newPos);
                replaceEntiteTabPosition(e, oldPos, e, newPos);
                //tabPosition.remove(oldPos);
                //tabPosition.put(oldPos.add(-e.vitesse, 0), e);
                break;
        }
    }
    
    public void start() {
        new Thread(this).start();
    }
    
    public void stop(){
        while (partieEnCours) {            
            partieEnCours = false;
        }
    }

    @Override
    public void run() {
        partieEnCours = true;
        while(partieEnCours){
            if (verifierVictoire()) {
                return;
            }else{
                
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Grille.class.getName()).log(Level.SEVERE, null, ex);
                }

                for (Modele.Entite.Entite e : tabEntites.keySet()) 
                { 
                    if (!jeuEnPause) {
                        if (depOk(e)) {
                            deplacementEntite(e);
                        }
                    }
                }
                setChanged(); 
                notifyObservers();
            }
        }
    }

    public void pauseGame() {
        jeuEnPause = true;
    }

    public void unPauseGame() {
        jeuEnPause = false;
    }
    
    public boolean lireGrilleFichier(String cheminFichier){
        try{
            partieEnCours = true;
            BufferedReader buffer = new BufferedReader(new FileReader(cheminFichier)); //on ouvre le fichier une première fois pour le nb de ligne
            String ligne;
            int nbLigne = 0;
            int ligneLength = 0;
            while ((ligne = buffer.readLine()) != null){ //on lit le fichier
                nbLigne++;
                ligneLength = ligne.length();
            }
            buffer.close();
            buffer = new BufferedReader(new FileReader(Configuration.CHEMIN_FICHIER_CUSTOMMAP)); //on ouvre le fichier une première fois pour le nb de ligne
            int y = 0;
            tabCaseStatique = new CaseStatique[ligneLength][nbLigne];
            while ((ligne = buffer.readLine()) != null){ //on lit le fichier
                int index = 0;
                for(char c: ligne.toCharArray()){
                    // A améliorer plus tard pour charger une map
                    switch(c){
                        case 'm' : //mur
                            tabCaseStatique[index][y] = new Mur();
                            break;
                        case 'c' : //couloir
                            tabCaseStatique[index][y] = new Couloir();
                            break;
                        case 'p' : //pacman on définit ici sa position !
                            tabCaseStatique[index][y] = new Couloir();
                            changerPositionEntite(new Point(index, y), "PacMan");
                            break;
                        case '1' : //Inky
                            tabCaseStatique[index][y] = new Couloir();
                            changerPositionEntite(new Point(index, y), "Inky");
                            break;
                        case '2' : //Pinky
                            tabCaseStatique[index][y] = new Couloir();
                            changerPositionEntite(new Point(index, y), "Pinky");
                            break;
                        case '3' : //Blinky
                            tabCaseStatique[index][y] = new Couloir();
                            changerPositionEntite(new Point(index, y), "Blinky");
                            break;
                        case '4' : //Clyde
                            tabCaseStatique[index][y] = new Couloir();
                            changerPositionEntite(new Point(index, y), "Clyde");
                            break;
                        case 'g' : //super pac-gomme
                            tabCaseStatique[index][y] = new PacGomme();
                            break;
                        case 'G' : //super pac-gomme
                            tabCaseStatique[index][y] = new SuperPacGomme();
                            break;
                    }
                    index++;
                }
                y++;
            }
            buffer.close();
            setChanged(); 
            notifyObservers();
        }catch(Exception e) {
            return false;
        }
        return true;
    }
    
    private boolean verifierVictoire(){
        for (int i = 0; i < tabCaseStatique.length; i++) {
            for (int j = 0; j < tabCaseStatique[i].length; j++) {
                if (tabCaseStatique[i][j] instanceof PacGomme || tabCaseStatique[i][j] instanceof SuperPacGomme) {
                    if (!((Couloir)tabCaseStatique[i][j]).mangee) {
                        return false;
                    }
                }
            }
        }
        partieEnCours = false;
        return true;
    }
    
}
