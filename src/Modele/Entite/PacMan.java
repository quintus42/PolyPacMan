package Modele.Entite;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Modele.Configuration;
import javafx.scene.image.Image;

/**
 *
 * @author Epulapp
 */
public class PacMan extends Entite {
    private Image imPacman = new Image(Configuration.PATH_TO_IMG + "Pacman.png");
    
    private boolean animation = false;

    public Image getImPacman() {
        synchronized(this){
            
            System.out.println("changement pacman");
            animation = !animation;
//            if (animation) {
//                imPacman = new Image(Configuration.PATH_TO_IMG + "Pacman.png");
//            }else{
//                imPacman = new Image(Configuration.PATH_TO_IMG + "PacmanFerme.png");
//            }
            return imPacman;
        }
    }
    
    public PacMan(){
        super();
    }
}
