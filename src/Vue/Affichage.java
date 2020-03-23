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
import Modele.Entite.*;
import Modele.Grille.Grille;
import Modele.Grille.Point;
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

import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.input.KeyCode;

/**
 *
 * @author Epulapp
 */
public class Affichage extends Application {

    //Initialisation des modèles
    private PacMan pm = new PacMan();
    private PacGomme pg = new PacGomme();
    private SuperPacGomme spg = new SuperPacGomme();
    private Blinky rGhost = new Blinky();
    private Pinky pGhost = new Pinky();
    private Inky bGhost = new Inky();
    private Clyde oGhost = new Clyde();
    private Mur wall = new Mur();
    private Couloir corridor = new Couloir();

    private Grille maGrille = new Grille();
    private GridPane grid = new GridPane();

    private int size_x  = Configuration.LARGEUR_GRILLE;
    private int size_y = Configuration.HAUTEUR_GRILLE;

    private ImageView[][]tab = new ImageView[size_x][size_y];

    @Override
    public void start(Stage primaryStage) {
        
        maGrille = new Grille();

        //Ajout des items à la grille
        StackPane root = new StackPane();//Ajout de la grille

        // initialisation de la grille (sans image)
        for (int i = 0; i < size_x; i++) {
            for (int j = 0; j < size_y; j++) {
                ImageView img = new ImageView();
                tab[i][j] = img;
                grid.add(img, i, j);
            }
        }

        setGridImg();

        root.getChildren().add(grid); 
        Image imIcon = new Image(Configuration.PATH_TO_IMG + "icon.png",Configuration.IMG_WIDTH,Configuration.IMG_HEIGHT,false,false);

        Scene scene = new Scene(root, Configuration.WINDOW_WIDTH, Configuration.WINDOW_HEIGTH, Paint.valueOf("Black"));
        primaryStage.getIcons().add(imIcon);
        primaryStage.setTitle("Pacman");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        try {
            root.setOnKeyPressed(new EventHandler<javafx.scene.input.KeyEvent>() { // on écoute le clavier

                @Override
                public void handle(javafx.scene.input.KeyEvent event) {
                    switch(event.getCode()){
                        case Z :
                        case UP :
                            maGrille.changerDirectionPacman(Direction.HAUT);
                            pm.rotation = -90;
                            pm.scaling = 1;
                            break;
                        case Q :
                        case LEFT :
                            maGrille.changerDirectionPacman(Direction.GAUCHE);
                            pm.rotation = 0;
                            pm.scaling = -1;
                            break;
                        case S :
                        case DOWN :
                            maGrille.changerDirectionPacman(Direction.BAS);
                            pm.rotation = 90;
                            pm.scaling = 1;
                            break;
                        case D :
                        case RIGHT :
                            maGrille.changerDirectionPacman(Direction.DROITE);
                            pm.rotation = 0;
                            pm.scaling = 1;
                            break;
                        case P :
                            if (maGrille.jeuEnPause) {
                                maGrille.unPauseGame();
                            }else{
                                maGrille.pauseGame();
                            }
                            break;
                        case N :
                            maGrille.changerDirectionPacman(Direction.AUCUNE);
                            pm.rotation = 0;
                            pm.scaling = 1;
                            if (maGrille.partieEnCours) {
                                maGrille.stop();
                                maGrille.lireGrilleFichier(Configuration.CHEMIN_FICHIER_CUSTOMMAP);
                                maGrille.start();
                            }else{
                                maGrille.lireGrilleFichier(Configuration.CHEMIN_FICHIER_CUSTOMMAP);
                                maGrille.start();
                            }
                            break;
                    }
                }
            });

            Observer o =  new Observer() { // l'observer observe l'obervable (update est exécuté dès notifyObservers() est appelé côté modèle )
                @Override
                public void update(Observable o, Object arg) {
                    setGridImg();
                    setGridEntityImg();
                }
            };
            
            maGrille.addObserver(o);
            
            grid.requestFocus();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //Affiche les images correspondantes au type de case présente dans le tableau
    private void setGridImg(){
        for (int i = 0; i < size_x ; i++) {
            for (int j = 0; j < size_y; j++) {
                if(maGrille.tabCaseStatique[i][j] instanceof Mur) {
                    tab[i][j].setImage(wall.getImFullWall());
                }
                else{
                    //Rien à ajouter parce que les couloir c'est noir
                    tab[i][j].setImage(null);
                }
                if (maGrille.tabCaseStatique[i][j] instanceof PacGomme) {
                    Point posPacman = maGrille.getPositionPacman();
                    if (posPacman.equals(new Point(i, j))) {
                        ((Couloir)(maGrille.tabCaseStatique[i][j])).mangee = true;
                    }
                    if (((Couloir)(maGrille.tabCaseStatique[i][j])).mangee) {
                        tab[i][j].setImage(null);
                    }else{
                        tab[i][j].setImage(pg.getImPacGomme());
                    }
                }
                else if (maGrille.tabCaseStatique[i][j] instanceof SuperPacGomme){
                    Point posPacman = maGrille.getPositionPacman();
                    if (posPacman.equals(new Point(i, j))) {
                        ((Couloir)(maGrille.tabCaseStatique[i][j])).mangee = true;
                    }
                    if (((Couloir)(maGrille.tabCaseStatique[i][j])).mangee) {
                        tab[i][j].setImage(null);
                    }else{
                        tab[i][j].setImage(spg.getImSuperPacGomme());
                    }
                }
                else if(maGrille.tabPosition.get(new Point(i,j)) instanceof PacMan){
                    tab[i][j].setImage(pm.getImPacman());

                }
                else if(maGrille.tabPosition.get(new Point(i,j)) instanceof Fantome){
                    Fantome ghost = (Fantome) maGrille.tabPosition.get(new Point(i,j));
                    tab[i][j].setImage(ghost.getImGhost());
                }
                tab[i][j].setFitWidth(Configuration.IMG_WIDTH);
                tab[i][j].setFitHeight(Configuration.IMG_HEIGHT);
                tab[i][j].setPreserveRatio(true);
                tab[i][j].setSmooth(true);
                tab[i][j].setCache(true);
            }
        }
    }
    
    private void setGridEntityImg(){
//        maGrille.tabEntites.forEach((k, v) -> {
//            if(k instanceof PacMan) {
//                tab[v.getX()][v.getY()].setImage(pm.getImPacman());
//            }
//            else if (k instanceof Fantome){
//                tab[v.getX()][v.getY()].setImage(Fantome.getImBlueGhost());
//            }
//            tab[v.getX()][v.getY()].setFitWidth(Configuration.IMG_WIDTH);
//            tab[v.getX()][v.getY()].setFitHeight(Configuration.IMG_HEIGHT);
//            tab[v.getX()][v.getY()].setPreserveRatio(true);
//            tab[v.getX()][v.getY()].setSmooth(true);
//            tab[v.getX()][v.getY()].setCache(true);
//        });
        maGrille.tabPosition.forEach((v, k) -> {
            //A enelver pour les taiwip fantomes //
            /////////////////////////////////////////////
            tab[v.getX()][v.getY()].setRotate(0);
            tab[v.getX()][v.getY()].setScaleX(1);
            ////////////////////////////////////////////
            if(k instanceof PacMan) {
                tab[v.getX()][v.getY()].setImage(pm.getImPacman());
                tab[v.getX()][v.getY()].setRotate(pm.rotation);
                tab[v.getX()][v.getY()].setScaleX(pm.scaling);
            }
            else if (k instanceof Fantome){
                Fantome ghost = (Fantome) k;
                tab[v.getX()][v.getY()].setImage(ghost.getImGhost());

            }
            tab[v.getX()][v.getY()].setFitWidth(Configuration.IMG_WIDTH);
            tab[v.getX()][v.getY()].setFitHeight(Configuration.IMG_HEIGHT);
            tab[v.getX()][v.getY()].setPreserveRatio(true);
            tab[v.getX()][v.getY()].setSmooth(true);
            tab[v.getX()][v.getY()].setCache(true);
        });
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
