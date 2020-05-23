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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

//import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;
import javafx.stage.*;
import javafx.util.Duration;
import javafx.scene.input.InputEvent;

/**
 *
 * @author Epulapp
 */
public class Affichage extends Application {

    // <editor-fold defaultstate="collapsed" desc="Variables">
    
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
    
    private Scene scene;
    
    private Stage pStage;

    private int animationPmIndex = 0;
    private Timeline pmAnimationTimeLine;
    // </editor-fold>

    private void majTabAffichage(int sizex, int sizey){
        
        Group root = new Group();
        grid = new GridPane();
        size_x = sizex;
        size_y = sizey;
        tab = new ImageView[size_x][size_y];
        for (int i = 0; i < size_x; i++) {
            for (int j = 0; j < size_y; j++) {
                ImageView img = new ImageView();
                tab[i][j] = img;
                grid.add(img, i, j);
            }
        }
        root.getChildren().add(grid); 
        root.setStyle("-fx-background-color: #808080;");
        Image imIcon = new Image(Configuration.PATH_TO_IMG + "icon.png",Configuration.IMG_WIDTH,Configuration.IMG_HEIGHT,false,false);
        Scene scene = new Scene(root, Configuration.WINDOW_WIDTH, Configuration.WINDOW_HEIGTH, Paint.valueOf("Black"));
        pStage.getIcons().add(imIcon);
        pStage.setTitle("Pacman");
        pStage.setScene(scene);
        pStage.show();
        setKeyEvents(root);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();

        pStage = primaryStage;
        
        pStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

        Button btnJouer = new Button("Jouer");
        btnJouer.setMinSize(120, 30);
        btnJouer.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                //Stage st = new Stage(StageStyle.DECORATED);
                lancerPartie();
            }
        });
        Button btnQuitter = new Button("Quiter");
        btnQuitter.setMinSize(120, 30);
        btnQuitter.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });
        
        Image image = new Image(Configuration.PATH_TO_IMG + "HomeMenu.jpg");
        ImageView im = new ImageView(image);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(im, btnJouer, btnQuitter); // hbox with button and text on top of image view
        
        stackPane.setAlignment(btnJouer, Pos.BOTTOM_CENTER);
        stackPane.setAlignment(btnQuitter, Pos.BOTTOM_CENTER);
        stackPane.setMargin(btnJouer, new Insets(0, 0, 100, 0));
        stackPane.setMargin(btnQuitter, new Insets(0, 0, 60, 0));
        
        root.getChildren().add(stackPane);

        scene = new Scene(root, 405, 405, Paint.valueOf("Black"));
        Image imIcon = new Image(Configuration.PATH_TO_IMG + "icon.png",Configuration.IMG_WIDTH,Configuration.IMG_HEIGHT,false,false);
        pStage.getIcons().add(imIcon);
        pStage.setTitle("Pacman");
        pStage.setScene(scene);
        pStage.show();
        
    }
    private void initPacManAnim(){
         pmAnimationTimeLine = null;
         pmAnimationTimeLine = new Timeline(new KeyFrame(Duration.seconds(0.2), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(animationPmIndex >= pm.images.size())
                    animationPmIndex = 0;
                pm.setImPacman(pm.images.get(animationPmIndex));
                animationPmIndex++;
            }
        }));
        pmAnimationTimeLine.setCycleCount(Timeline.INDEFINITE);
        pmAnimationTimeLine.play();
    }
    private void lancerEcranVictoire(){
        Group root = new Group();
        
        Stage victoire = new Stage(StageStyle.DECORATED);
        
        victoire.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

        Button btnJouer = new Button("Rejouer");
        btnJouer.setMinSize(120, 30);
        btnJouer.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                //Stage st = new Stage(StageStyle.DECORATED);
                lancerPartie();
            }
        });
        Button btnQuitter = new Button("Quiter");
        btnQuitter.setMinSize(120, 30);
        btnQuitter.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });
        Button btnSuivant = new Button("Niveau Suivant");
        btnSuivant.setMinSize(120, 30);
        btnSuivant.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });
        
        Image image = new Image(Configuration.PATH_TO_IMG + "Victoire.jpg");
        ImageView im = new ImageView(image);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(im, btnJouer, btnSuivant, btnQuitter); // hbox with button and text on top of image view
        
        stackPane.setAlignment(btnJouer, Pos.BOTTOM_CENTER);
        stackPane.setAlignment(btnSuivant, Pos.BOTTOM_CENTER);
        stackPane.setAlignment(btnQuitter, Pos.BOTTOM_CENTER);
        stackPane.setMargin(btnJouer, new Insets(0, 0, 10, 0));
        stackPane.setMargin(btnSuivant, new Insets(0, 0, 50, 0));
        stackPane.setMargin(btnQuitter, new Insets(0, 0, 90, 0));
        
        root.getChildren().add(stackPane);

        Scene s = new Scene(root, 405, 405, Paint.valueOf("Black"));
        victoire.setTitle("Pacman");
        victoire.setScene(s);
        victoire.show();
    }
    
    private void lancerPartie(){

        //Ajout des items à la grille
        StackPane root = new StackPane();//Ajout de la grille
        
        grid = new GridPane();

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
        root.setStyle("-fx-background-color: #000000;");
        Image imIcon = new Image(Configuration.PATH_TO_IMG + "icon.png",Configuration.IMG_WIDTH,Configuration.IMG_HEIGHT,false,false);
        scene = new Scene(root, Configuration.WINDOW_WIDTH, Configuration.WINDOW_HEIGTH, Paint.valueOf("Black"));
        pStage.getIcons().add(imIcon);
        pStage.setTitle("Pacman");
        pStage.setScene(scene);
        pStage.show();
        initPacManAnim();
        try {
            
            setKeyEvents(root);

            Observer o =  new Observer() { // l'observer observe l'obervable (update est exécuté dès notifyObservers() est appelé côté modèle )
                @Override
                public void update(Observable o, Object arg) {
                    if (maGrille.Victoire) {
                        maGrille.Victoire = false;
                        Platform.runLater(new Runnable() {
                            @Override public void run() {
                                lancerEcranVictoire();
                            }
                        });
                        maGrille.stop();
                    }else{
                        if (maGrille.tabCaseStatique.length != size_x || maGrille.tabCaseStatique[0].length != size_y ) {
                            majTabAffichage(maGrille.tabCaseStatique.length, maGrille.tabCaseStatique[0].length);
                        }
                        setGridImg();
                        setGridEntityImg();
                    }
                }
            };
            
            maGrille.addObserver(o);
            maGrille.lireGrilleFichier(Configuration.CHEMIN_FICHIER_CUSTOMMAP);
            maGrille.start();
            
            grid.requestFocus();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void setKeyEvents(Parent root){
        root.setOnKeyPressed(new EventHandler<javafx.scene.input.KeyEvent>() { // on écoute le clavier

                @Override
                public void handle(javafx.scene.input.KeyEvent event) {
                    switch(event.getCode()){
                        case ESCAPE: 
                            maGrille.stop();
                            start(pStage);
                            break;
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
                                pmAnimationTimeLine.stop();
                                //maGrille.lireGrilleFichier(Configuration.CHEMIN_FICHIER_CUSTOMMAP);
                                //Platform.runLater(maGrille);
                                //maGrille.start();
                                lancerPartie();
                            }else{
                                //maGrille.lireGrilleFichier(Configuration.CHEMIN_FICHIER_CUSTOMMAP);
                                //Platform.runLater(maGrille);
                                //maGrille.start();
                                lancerPartie();
                            }
                            break;
                    }
                }
            });
    }

    // <editor-fold defaultstate="collapsed" desc="View Update">
    
    //Affiche les images correspondantes au type de case présente dans le tableau
    private void setGridImg(){
        for (int i = 0; i < size_x ; i++) {
            for (int j = 0; j < size_y; j++) {
                if (maGrille.tabCaseStatique[i][j] == null) {
                    continue;
                }
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

        try{
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
        }catch (Exception ignored){
        }

    }
    
    // </editor-fold>
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
