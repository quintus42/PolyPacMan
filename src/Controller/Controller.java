/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Modele.Entite.Direction;
import Modele.Grille.Grille;

/**
 *
 * @author Epulapp
 */
public class Controller {
    private Grille grille;
    
    
    public void lancerPartie(){
        grille = new Grille();
        grille.start();
    }
    
    public void changerDirection(Direction d){
        grille.changerDirectionPacman(d);
    }
    
    public void pauseGame(){
        grille.pauseGame();
    }
    
    public void unPauseGame(){
        grille.unPauseGame();
    }
}
