package Modele.Entite;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javafx.scene.image.Image;

/**
 *
 * @author Epulapp
 */
public class PacMan extends Entite {
    private final static Image imPacman = new Image("assets/Pacman.png");

    public static Image getImPacman() {
        return imPacman;
    }
}
