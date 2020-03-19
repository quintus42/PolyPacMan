package Modele.Entite;

import Modele.Configuration;
import javafx.scene.image.Image;

public class Inky extends Fantome {
    private final Image imGhost = new Image(Configuration.PATH_TO_IMG + "BlueGhost.png");

    @Override
    public Image getImGhost() {
        return imGhost;
    }
}
