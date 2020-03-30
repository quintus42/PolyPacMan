package Modele;

public class Configuration {

    public final static String PATH_TO_ASSETS_FOLDER = "src/assets";

    public static int IMG_WIDTH = 25; // X

    public static int IMG_HEIGHT = 25 ; // Y

    public final static String PATH_TO_IMG = "file:" + PATH_TO_ASSETS_FOLDER + "/";

    public static int HAUTEUR_GRILLE = 20; // Y

    public static int LARGEUR_GRILLE = 20; // X

    public static int WINDOW_WIDTH = IMG_WIDTH * LARGEUR_GRILLE; // X
 
    public static int WINDOW_HEIGTH = IMG_HEIGHT * HAUTEUR_GRILLE; // Y
    
    public final static String CHEMIN_FICHIER_CUSTOMMAP = "src/Assets/Maps/CustomMap.txt";

    public static void setHauteurGrille(int hauteur){
        HAUTEUR_GRILLE = hauteur;
        WINDOW_HEIGTH = IMG_HEIGHT * HAUTEUR_GRILLE;
    }
    
    public static void setLargeurGrille(int largeur){
        LARGEUR_GRILLE = largeur;
        WINDOW_WIDTH = IMG_WIDTH * LARGEUR_GRILLE;
    }
    
    public static void setLargeurImage(int largeur){
        IMG_WIDTH = largeur;
        WINDOW_WIDTH = IMG_WIDTH * LARGEUR_GRILLE;
    }
    
    public static void setHauteurImage(int hauteur){
        IMG_HEIGHT = hauteur;
        WINDOW_HEIGTH = IMG_HEIGHT * HAUTEUR_GRILLE;
    }
    
}
