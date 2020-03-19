/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele.Grille;

import Modele.Case.CaseStatique;
import Modele.Configuration;
import Modele.Entite.Entite;
import Modele.Entite.PacMan;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Point2D;
import javafx.util.Pair;


/**
 *
 * @author Epulapp
 */
public class Grille extends Observable implements Runnable{

    public CaseStatique[][] tabCaseStatique;
    public HashMap<Modele.Entite.Entite, Point> tabEntites;
    public HashMap<Point, Modele.Entite.Entite> tabPosition;
    private boolean partieEnCours = false;
    private boolean superPacGomme = false;
    private boolean jeuEnPause = false;
    
    public Grille(){
        tabCaseStatique = new CaseStatique[Configuration.LARGEUR_GRILLE][Configuration.HAUTEUR_GRILLE];
        tabEntites = new HashMap<Modele.Entite.Entite, Point>();
        tabPosition = new HashMap<Point, Modele.Entite.Entite>();

        Point position;
        
        //Pacman
        position = new Point(0,0);
        tabEntites.put(new Modele.Entite.PacMan(), position);
        tabPosition.put(position, new Modele.Entite.PacMan());
        
        //Blinky
        position = new Point(8,10);
        tabEntites.put(new Modele.Entite.PacMan(), position);
        tabPosition.put(position, new Modele.Entite.PacMan());
        
        //Pinky
        position = new Point(9,10);
        tabEntites.put(new Modele.Entite.PacMan(), position);
        tabPosition.put(position, new Modele.Entite.PacMan());
        
        //Inky
        position = new Point(10,10);
        tabEntites.put(new Modele.Entite.PacMan(), position);
        tabPosition.put(position, new Modele.Entite.PacMan());
        
        //Clyde
        position = new Point(11,10);
        tabEntites.put(new Modele.Entite.PacMan(), position);
        tabPosition.put(position, new Modele.Entite.PacMan());
        
    }
    
    public boolean depOk(Modele.Entite.Entite e){
        Point position = tabEntites.get(e);
        switch(e.direction){
            case HAUT:
                position = position.add(0, e.vitesse);
                break;
            case BAS:
                position = position.add(0, e.vitesse);
                break;
            case DROITE:
                position = position.add(e.vitesse, 0);
                break;
            case GAUCHE:
                position = position.add(e.vitesse, 0);
                break;
        }
        position = gestionBordGrille(position);
        if (tabPosition.get(position) != null) {
            //Si Pacman a manger une super Pac-Gomme, les fantômes sont vulnérables
            if (!superPacGomme) {
                //Gestion de la fin de la partie
                partieEnCours = false;
                return false;
            }
        }else if(tabCaseStatique[position.getX()][position.getY()] instanceof Modele.Case.Mur){
            //On ne peut pas aller dans un mur
            return false;
        }else if(!(tabCaseStatique[position.getX()][position.getY()] instanceof Modele.Case.Couloir)){
            //Gestion des différents bonus et des points (Pac-Gommes + Super Pac-Gommes + Fruits)
            return true;
        }else{
            //On est dans le cas ou la case est vide
            return true;
        }
        return false;
    }

    private Point gestionBordGrille(Point position) {
        
        if (position.getX() < 0) {
            position.setX(Configuration.LARGEUR_GRILLE);
        }else if(position.getX() > Configuration.LARGEUR_GRILLE){
            position.setX(0);
        }
        
        if (position.getY() < 0) {
            position.setY(Configuration.HAUTEUR_GRILLE);
        }else if(position.getY() > Configuration.HAUTEUR_GRILLE){
            position.setY(0);
        }
        
        return position;
    }
    
    public void changerDirection(Entite e, Modele.Entite.Direction d){
        e.direction = d;
        tabEntites.replace(e, tabEntites.get(e));
        tabPosition.replace(tabEntites.get(e), e);
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
            tabPosition.replace(tabEntites.get(e), e);
        }
    }
    private void deplacementEntite(Entite e) {
        Point oldPos = tabEntites.get(e);
        switch(e.direction){
            case HAUT:
                tabEntites.replace(e, oldPos, oldPos.add(0, e.vitesse));
                tabPosition.remove(oldPos, e);
                tabPosition.put(oldPos.add(0, e.vitesse), e);
                break;
            case BAS:
                tabEntites.replace(e, oldPos, oldPos.add(0, -e.vitesse));
                tabPosition.remove(oldPos, e);
                tabPosition.put(oldPos.add(0, -e.vitesse), e);
                break;
            case DROITE:
                tabEntites.replace(e, oldPos, oldPos.add(e.vitesse, 0));
                tabPosition.remove(oldPos, e);
                tabPosition.put(oldPos.add(e.vitesse, 0), e);
                break;
            case GAUCHE:
                tabEntites.replace(e, oldPos, oldPos.add(-e.vitesse, 0));
                tabPosition.remove(oldPos, e);
                tabPosition.put(oldPos.add(-e.vitesse, 0), e);
                break;
        }
    }
    
    public void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        partieEnCours = true;
        while(partieEnCours){
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

    public void pauseGame() {
        jeuEnPause = true;
    }

    public void unPauseGame() {
        jeuEnPause = false;
    }
}
