package Modele.Entite;

import Modele.Configuration;
import javafx.scene.image.Image;

public class Pinky extends Fantome {

    private final Image imGhost = new Image(Configuration.PATH_TO_IMG + "PinkGhost.png");

    @Override
    public Image getImGhost() {
        return imGhost;
    }
}
