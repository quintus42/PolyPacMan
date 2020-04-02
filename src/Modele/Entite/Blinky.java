package Modele.Entite;

import Modele.Configuration;
import javafx.scene.image.Image;

public class Blinky extends Fantome {

    public Blinky() {
        this.imGhost = new Image(Configuration.PATH_TO_IMG + "RedGhost.png");
    }

    @Override
    public String getName() {
        return "Blinky"; //To change body of generated methods, choose Tools | Templates.
    }
}
