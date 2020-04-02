package Modele.Entite;

import Modele.Configuration;
import javafx.scene.image.Image;

public class Pinky extends Fantome {
    
    public Pinky() {
        this.imGhost = new Image(Configuration.PATH_TO_IMG + "PinkGhost.png");
    }

    @Override
    public String getName() {
        return "Pinky"; //To change body of generated methods, choose Tools | Templates.
    }
}
