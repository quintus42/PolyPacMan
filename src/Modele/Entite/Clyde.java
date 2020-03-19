package Modele.Entite;

import Modele.Configuration;
import javafx.scene.image.Image;

public class Clyde  extends Fantome {

    private final Image imGhost = new Image(Configuration.PATH_TO_IMG + "OrangeGhost.png");


    @Override
    public Image getImGhost() {
        return imGhost;
    }
}
