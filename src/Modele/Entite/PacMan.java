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
    private final Image imPacman = new Image(Configuration.PATH_TO_IMG + "Pacman.png");

    public Image getImPacman() {
        return imPacman;
    }
    
    public PacMan(){
        super();
    }
}
