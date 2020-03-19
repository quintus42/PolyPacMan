package Modele;

public class Configuration {

    public final static String PATH_TO_ASSETS_FOLDER = "src/assets";

    public final static String PATH_TO_IMG = "file:" + PATH_TO_ASSETS_FOLDER + "/";

    public final static int HAUTEUR_GRILLE = 20; // Y

    public final static int LARGEUR_GRILLE = 20; // X

    public final static int WINDOW_WIDTH = 500; // X

    public final static int WINDOW_HEIGTH = 500; // Y

    public final static int IMG_WIDTH = WINDOW_WIDTH /  LARGEUR_GRILLE; // X

    public final static int IMG_HEIGHT = WINDOW_HEIGTH /  HAUTEUR_GRILLE ; // Y

    public final static String CHEMIN_FICHIER_CUSTOMMAP = "file:src/assets/maps/CustomMap.txt";

}
