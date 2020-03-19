/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele.Entite;

import Modele.Configuration;
import javafx.scene.image.Image;

/**
 *
 * @author Epulapp
 */

public class Fantome extends Entite{

    private final static Image imOrangeGhost = new Image(Configuration.PATH_TO_IMG + "OrangeGhost.png");
    private final static Image imBlueGhost = new Image(Configuration.PATH_TO_IMG + "BlueGhost.png");
    private final static Image imRedGhost = new Image(Configuration.PATH_TO_IMG + "RedGhost.png");
    private final static Image imPinkGhost = new Image(Configuration.PATH_TO_IMG + "PinkGhost.png");

    public static Image getImOrangeGhost() {
        return imOrangeGhost;
    }

    public static Image getImBlueGhost() {
        return imBlueGhost;
    }

    public static Image getImRedGhost() {
        return imRedGhost;
    }

    public static Image getImPinkGhost() {
        return imPinkGhost;
    }
    
    public Fantome(){
        super();
    }
}
