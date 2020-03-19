/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele.Entite;

/**
 *
 * @author Epulapp
 */
public abstract class Entite {
    public Direction direction;
    public int vitesse;
    
    public Entite(){
        this.direction = Direction.AUCUNE;
        this.vitesse = 1;
    }
}
