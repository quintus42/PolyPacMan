package Modele.Entite;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Modele.Configuration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;

/**
 *
 * @author Epulapp
 */
public class PacMan extends Entite {
    private static Image imPacman = new Image(Configuration.PATH_TO_IMG + "Pacman.png");
    
    private boolean animation = false;

    public Image getImPacman() {
        synchronized(this){
            return imPacman;
        }
    }
    
    public PacMan(){
        super();
        Runnable r = new Runnable() {
            @Override public void run() {
                while (true) {                    
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PacMan.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    animation = !animation;
                    if (animation) {
                        imPacman = new Image(Configuration.PATH_TO_IMG + "Pacman.png");
                    }else{
                        imPacman = new Image(Configuration.PATH_TO_IMG + "PacmanFerme.png");
                    }
                }
            }
        };
        (new Thread(r)).start();
    }
}
