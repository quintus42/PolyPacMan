/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele.Entite;

import Modele.Configuration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;

/**
 *
 * @author Epulapp
 */

public class Fantome extends Entite implements Runnable{

    protected Image imGhost;
    
    public static boolean spgEnCours;

    public Image getImGhost() {
        if (spgEnCours) {
            //return null;
            return new Image(Configuration.PATH_TO_IMG + "icon.png");
        }else{
            return imGhost;
        }
    }
    
    public String getName(){
        return "";
    }
    
    public Fantome(){
        super();
    }
    
    public void changerDirection(){
        int dir = (int) (Math.random() * 4);
            switch(dir){
                case 0:
                    this.direction =  Direction.HAUT;
                    return;
                case 1:
                    this.direction =  Direction.BAS;
                    return;
                case 2:
                    this.direction =  Direction.GAUCHE;
                    return;
                case 3:
                    this.direction =  Direction.DROITE;
                    return;
                case 4:
                    this.direction =  Direction.AUCUNE;
                    return;
            }
            this.direction = Direction.AUCUNE;
    }

    @Override
    public void run() {
        while (true) {            
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Fantome.class.getName()).log(Level.SEVERE, null, ex);
            }
            changerDirection();
        }
    }
}
