package Modele.Entite;

import Modele.Configuration;
import javafx.scene.image.Image;

public class Clyde  extends Fantome {
    
    public Clyde() {
        this.imGhost = new Image(Configuration.PATH_TO_IMG + "OrangeGhost.png");
    }

    @Override
    public String getName() {
        return "Clyde"; //To change body of generated methods, choose Tools | Templates.
    }
}
