package Modele.Entite;

import Modele.Configuration;
import javafx.scene.image.Image;

public class Inky extends Fantome {
    
    public Inky() {
        this.imGhost = new Image(Configuration.PATH_TO_IMG + "BlueGhost.png");
    }

    @Override
    public String getName() {
        return "Inky"; //To change body of generated methods, choose Tools | Templates.
    }
}
