/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Modele.Case.Mur;
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
public class Affichage extends Application implements Observer {

    private final int IMG_WIDTH = 45;

    private final int IMG_HEIGHT = 45;

    public void initialisationModele (){
        //Initialisation des modèles
        PacMan pm = new PacMan();
        Fantome rGhost = new Fantome();
        Fantome pGhost = new Fantome();
        Fantome bGhost = new Fantome();
        Fantome oGhost = new Fantome();
        Mur wall = new Mur();
        Grille maGrille = new Grille();
    }
    @Override
    public void start(Stage primaryStage) {


        Image imIcon = new Image(Configuration.PATH_TO_IMG + "icon.png",IMG_WIDTH,IMG_HEIGHT,false,false);

        //A remplacer par grille custom dès que possible
        GridPane grid = new GridPane();

        //Ajout des items à la grille
        StackPane root = new StackPane();
        root.getChildren().add(grid); //Ajout de la grille

        int size_x  = 20;
        int size_y = 21;

        // initialisation de la grille (sans image)
        ImageView[][]tab = new ImageView[size_x][size_y];
        for (int i = 0; i < size_x; i++) {
            for (int j = 0; j < size_y; j++) {
                ImageView img = new ImageView();
                tab[i][j] = img;
                grid.add(img, i, j);
            }
        }


        // Pour ajouter les murs sur tous le tour de la grille
//        for (int i = 0; i < size_x; i++) {
//            for (int j = 0; j < size_y; j++) {
////                if (i == 0 || j == 0 || i == (size_x-1) || j == (size_y-1)){
//                    tab[i][j].setImage(wall.getImFullWall());
////                }
//            }
//        }


        Scene scene = new Scene(root, IMG_WIDTH * size_x, IMG_HEIGHT * size_y, Paint.valueOf("Black"));
        primaryStage.getIcons().add(imIcon);
        primaryStage.setTitle("Pacman");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @Override
    public void update(Observable observable, Object o) {

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
