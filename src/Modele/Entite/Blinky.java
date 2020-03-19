package Modele.Entite;

import Modele.Configuration;
import javafx.scene.image.Image;

public class Blinky extends Fantome {
    private final Image imGhost = new Image(Configuration.PATH_TO_IMG + "RedGhost.png");

    @Override
    public Image getImGhost() {
        return imGhost;
    }
}
