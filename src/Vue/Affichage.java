/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Modele.Case.Couloir;
import Modele.Case.Mur;
import Modele.Case.PacGomme;
import Modele.Case.SuperPacGomme;
import Modele.Configuration;
import Modele.Entite.Fantome;
import Modele.Entite.PacMan;
import Modele.Grille.Grille;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Epulapp
 */
public class Affichage extends Application {

    //Initialisation des modèles
    private PacMan pm = new PacMan();
    private PacGomme pg = new PacGomme();
    private SuperPacGomme spg = new SuperPacGomme();
    private Fantome rGhost = new Fantome();
    private Fantome pGhost = new Fantome();
    private Fantome bGhost = new Fantome();
    private Fantome oGhost = new Fantome();
    private Mur wall = new Mur();
    private Couloir corridor = new Couloir();
    private Grille maGrille = new Grille();

    private Image imIcon = new Image(Configuration.PATH_TO_IMG + "icon.png",Configuration.IMG_WIDTH,Configuration.IMG_HEIGHT,false,false);

    private GridPane grid = new GridPane();

    private int size_x  = Configuration.LARGEUR_GRILLE;
    private int size_y = Configuration.HAUTEUR_GRILLE;

    private ImageView[][]tab = new ImageView[size_x][size_y];

    @Override
    public void start(Stage primaryStage) {

        //Ajout des items à la grille
        StackPane root = new StackPane();
        root.getChildren().add(grid); //Ajout de la grille

        // initialisation de la grille (sans image)
        for (int i = 0; i < size_x; i++) {
            for (int j = 0; j < size_y; j++) {
                ImageView img = new ImageView();
                tab[i][j] = img;
                grid.add(img, i, j);
            }
        }

        setGridImg();

        Scene scene = new Scene(root, Configuration.WINDOW_WIDTH, Configuration.WINDOW_HEIGTH, Paint.valueOf("Black"));
        primaryStage.getIcons().add(imIcon);
        primaryStage.setTitle("Pacman");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    Observer o =  new Observer() { // l'observer observe l'obervable (update est exécuté dès notifyObservers() est appelé côté modèle )
        @Override
        public void update(Observable o, Object arg) {
            setGridImg();
        }
    };

    //Affiche les images correspondantes au type de case présente dans le tableau
    private void setGridImg(){
        for (int i = 0; i < size_x ; i++) {
            for (int j = 0; j < size_y; j++) {

                if(maGrille.tabCaseStatique[i][j] instanceof Mur) {
                    tab[i][j].setImage(wall.getImFullWall());
                }
                else if (maGrille.tabCaseStatique[i][j] instanceof Couloir){
                    //Rien à ajouter parce que les couloir c'est noir
                }
                else if (maGrille.tabCaseStatique[i][j] instanceof PacGomme) {
                    tab[i][j].setImage(pg.getImPacGomme());
                }
                else if (maGrille.tabCaseStatique[i][j] instanceof SuperPacGomme){
                    tab[i][j].setImage(spg.getImSuperPacGomme());
                }
                tab[i][j].setFitWidth(Configuration.IMG_WIDTH);
                tab[i][j].setFitHeight(Configuration.IMG_HEIGHT);
                tab[i][j].setPreserveRatio(true);
                tab[i][j].setSmooth(true);
                tab[i][j].setCache(true);
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
