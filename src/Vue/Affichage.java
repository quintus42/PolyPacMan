/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Modele.Case.CaseGrilleJava;
import Modele.Configuration;
import Modele.Entite.*;
import Modele.Grille.Grille;
import com.sun.javafx.scene.control.skin.EmbeddedTextContextMenuContent;

import java.io.File;
import java.io.FilenameFilter;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import javafx.stage.*;
import javafx.util.Duration;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

/**
 *
 * @author Epulapp
 */
public class Affichage extends Application {

    // <editor-fold defaultstate="collapsed" desc="Variables">
    
    //Initialisation des modèles
    private final PacMan pm = new PacMan();
//    private PacGomme pg = new PacGomme();
//    private SuperPacGomme spg = new SuperPacGomme();
//    private Blinky rGhost = new Blinky();
//    private Pinky pGhost = new Pinky();
//    private Inky bGhost = new Inky();
//    private Clyde oGhost = new Clyde();
//    private Mur wall = new Mur();
//    private Couloir corridor = new Couloir();

    private Grille maGrille = new Grille();
    private GridPane grid = new GridPane();

    private int size_x  = Configuration.LARGEUR_GRILLE;
    private int size_y = Configuration.HAUTEUR_GRILLE;

    private ImageView[][]tab = new ImageView[size_x][size_y];
    
    private Scene scene;
    
    private Stage pStage;
    
    private String niveauEnCours = "";
    
    private Boolean modeHistoire = false;
    
    private int animationPmIndex = 0;
    
    private Label lifes = new Label("3");
    
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

        Button btnJouerHistoire = new Button("Jouer (Histoire)");
        btnJouerHistoire.setMinSize(120, 30);
        btnJouerHistoire.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                //Stage st = new Stage(StageStyle.DECORATED);
                modeHistoire = true;
                lancerPartie("niveau1.txt");
            }
        });
        
        Button btnJouerPerso = new Button("Jouer (choix du niveau)");
        btnJouerPerso.setMinSize(120, 30);
        btnJouerPerso.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                //Stage st = new Stage(StageStyle.DECORATED);
                modeHistoire = false;
                lancerPartie("");
            }
        });
        
        Button btnPerso = new Button("Personnaliser");
        btnPerso.setMinSize(120, 30);
        btnPerso.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                //Stage st = new Stage(StageStyle.DECORATED);
                lancerPersonnalisation();
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
        stackPane.getChildren().addAll(im, btnJouerHistoire, btnJouerPerso, btnPerso, btnQuitter); // hbox with button and text on top of image view
        
        stackPane.setAlignment(btnJouerHistoire, Pos.BOTTOM_CENTER);
        stackPane.setAlignment(btnJouerPerso, Pos.BOTTOM_CENTER);
        stackPane.setAlignment(btnPerso, Pos.BOTTOM_CENTER);
        stackPane.setAlignment(btnQuitter, Pos.BOTTOM_CENTER);
        stackPane.setMargin(btnJouerHistoire, new Insets(0, 0, 140, 0));
        stackPane.setMargin(btnJouerPerso, new Insets(0, 0, 100, 0));
        stackPane.setMargin(btnPerso, new Insets(0, 0, 60, 0));
        stackPane.setMargin(btnQuitter, new Insets(0, 0, 20, 0));
        
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
    
    private void lancerEcran(Boolean defaite){
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
                victoire.close();
                lancerPartie(niveauEnCours);
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
        
        StackPane stackPane = new StackPane();
        
        if (!defaite) {
            Button btnSuivant = new Button("Niveau Suivant");
            btnSuivant.setMinSize(120, 30);
            btnSuivant.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    victoire.close();
                    lancerPartie("");
                }
            });
            
            Image image = new Image(Configuration.PATH_TO_IMG + "Victoire.jpg");
            ImageView im = new ImageView(image);

            stackPane.getChildren().addAll(im, btnJouer, btnSuivant, btnQuitter); // hbox with button and text on top of image view

            stackPane.setAlignment(btnJouer, Pos.BOTTOM_CENTER);
            stackPane.setAlignment(btnSuivant, Pos.BOTTOM_CENTER);
            stackPane.setAlignment(btnQuitter, Pos.BOTTOM_CENTER);
            stackPane.setMargin(btnJouer, new Insets(0, 0, 50, 0));
            stackPane.setMargin(btnSuivant, new Insets(0, 0, 90, 0));
            stackPane.setMargin(btnQuitter, new Insets(0, 0, 10, 0));
        }else{
            Image image = new Image(Configuration.PATH_TO_IMG + "Defaite.jpg");
            ImageView im = new ImageView(image);

            stackPane.getChildren().addAll(im, btnJouer, btnQuitter); // hbox with button and text on top of image view

            stackPane.setAlignment(btnJouer, Pos.BOTTOM_CENTER);
            stackPane.setAlignment(btnQuitter, Pos.BOTTOM_CENTER);
            stackPane.setMargin(btnJouer, new Insets(0, 0, 50, 0));
            stackPane.setMargin(btnQuitter, new Insets(0, 0, 10, 0));
        }
        
        root.getChildren().add(stackPane);

        Scene s = new Scene(root, 405, 405, Paint.valueOf("Black"));
        victoire.setTitle("Pacman");
        victoire.setScene(s);
        victoire.show();
    }
    
    private void lancerPersonnalisation(){
        
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Attention");
        dialog.setHeaderText("Veuillez saisir des valeurs entières positives");

        // Set the icon (must be included in the project).
        dialog.setGraphic(new ImageView());

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField x = new TextField();
        x.setPromptText("15 <= Hauteur <= 40");
        TextField y = new TextField();
        y.setPromptText("15 <= Largeur <= 40");

        grid.add(new Label("Hauteur :"), 0, 0);
        grid.add(x, 1, 0);
        grid.add(new Label("Largeur :"), 0, 1);
        grid.add(y, 1, 1);

        //Disable du boutton de validation.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Validation.
        x.textProperty().addListener((observable, oldValue, newValue) -> {
            if (y.getText()!="") {
                loginButton.setDisable(newValue.trim().isEmpty());
            }
        });
        y.textProperty().addListener((observable, oldValue, newValue) -> {
            if (x.getText()!="") {
                loginButton.setDisable(newValue.trim().isEmpty());
            }
        });

        dialog.getDialogPane().setContent(grid);

        // Pour enlever le focus du textfield
        Platform.runLater(() -> grid.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(x.getText(), y.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(xy -> {
            System.out.println("x=" + xy.getKey() + ", y=" + xy.getValue());
            Personnalisation(Integer.parseInt(xy.getKey()),  Integer.parseInt(xy.getValue()));
        });
    }
    
    private ArrayList<String> finder(String folder){
        File dir = new File(folder);

        File[] files = dir.listFiles(new FilenameFilter() { 
                 public boolean accept(File dir, String filename)
                      { return filename.endsWith(".txt"); }
        } );
        
        ArrayList<String> filesName = new ArrayList<String>();
        
        for (int i = 0; i < files.length; i++) {
            filesName.add(files[i].getName());
        }
        
        return filesName;
    }
    
    private String popupSelectionNiveau(String headerTxt){
        // Create the custom dialog.
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(headerTxt);
        dialog.setHeaderText("Choix du niveau");

        // Set the icon (must be included in the project).
        dialog.setGraphic(new ImageView());

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        final ComboBox comboBox = new ComboBox();
        comboBox.getItems().addAll(finder(Configuration.PATH_MAPS_ASSETS_FOLDER));
        
        grid.add(new Label("Selection du niveau :"), 0, 0);
        grid.add(comboBox, 1, 0);

        //Disable du boutton de validation.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Validation.
        comboBox.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
            if (newValue == null) {
                loginButton.setDisable(true);
            }else{
                loginButton.setDisable(false);
            }
         }
        ); 
        

        dialog.getDialogPane().setContent(grid);
        
        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return comboBox.getSelectionModel().getSelectedItem().toString();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        
        if (result.isPresent()) {
            return result.get();
        }else{
            return "";
        }
    }
    
    private String creerPopupChoixFichier(){
        // Create the custom dialog.
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Attention");
        dialog.setHeaderText("Veuillez choisir un fichier pour enregistrer la map");

        // Set the icon (must be included in the project).
        dialog.setGraphic(new ImageView());

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        final ComboBox comboBox = new ComboBox();
        comboBox.getItems().addAll(finder(Configuration.PATH_MAPS_ASSETS_FOLDER));
        
        final CheckBox cb = new CheckBox("Créer un nouveau fichier");
        final TextField tf = new TextField();
        tf.setPromptText("custom.txt");
        tf.setVisible(false);
        final Label lb = new Label("Utiliser la forme 'niveaux.txt' (x etant un nombre) pour alimenter le mode histoire");
        lb.setVisible(false);

        grid.add(new Label("Selection du fichier :"), 0, 0);
        grid.add(comboBox, 1, 0);
        grid.add(cb, 0, 1);
        grid.add(tf, 1, 1);
        grid.add(lb, 2, 1);

        //Disable du boutton de validation.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Validation.
        cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
                tf.setVisible(new_val);
                lb.setVisible(new_val);
                if (new_val) {
                    comboBox.getSelectionModel().clearSelection();
                    loginButton.setDisable(true);
                }
            }
        });
        tf.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });
        comboBox.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
            if (newValue == null) {
                loginButton.setDisable(true);
            }else{
                cb.setSelected(false);
                loginButton.setDisable(false);
            }
         }
        ); 
        

        dialog.getDialogPane().setContent(grid);
        
        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                if (comboBox.getSelectionModel().getSelectedItem()==null) {
                    return tf.getText();
                }else{
                    return comboBox.getSelectionModel().getSelectedItem().toString();
                }
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        
        if (result.isPresent()) {
            return result.get();
        }else{
            return "";
        }
    }
    
    private void creerLegendePersonnalisation(){
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Légende");
        dialog.setHeaderText("");

        // Set the icon (must be included in the project).
        dialog.setGraphic(new ImageView());

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        Rectangle couloir = new Rectangle(Configuration.IMG_WIDTH-1,Configuration.IMG_HEIGHT-1);
        couloir.setFill(Color.BLACK);
        grid.add(couloir, 0, 8);
        grid.add(new Label("Couloir"), 1, 8);
        
        Rectangle mur = new Rectangle(Configuration.IMG_WIDTH-1,Configuration.IMG_HEIGHT-1);
        mur.setFill(Color.BLUE);
        grid.add(mur, 0, 0);
        grid.add(new Label("Mur (1 click gauche)"), 1, 0);
        
        Rectangle pacGomme = new Rectangle(Configuration.IMG_WIDTH-1,Configuration.IMG_HEIGHT-1);
        pacGomme.setFill(Color.YELLOW);
        grid.add(pacGomme, 0, 1);
        grid.add(new Label("Pac-Gomme (2 clicks gauche)"), 1, 1);
        
        Rectangle superPacGomme = new Rectangle(Configuration.IMG_WIDTH-1,Configuration.IMG_HEIGHT-1);
        superPacGomme.setFill(Color.YELLOWGREEN);
        grid.add(superPacGomme, 0, 2);
        grid.add(new Label("Super Pac-Gomme (3 clicks gauche)"), 1, 2);
        
        Rectangle Inky = new Rectangle(Configuration.IMG_WIDTH-1,Configuration.IMG_HEIGHT-1);
        Inky.setFill(Color.rgb(134, 236, 247, 1));
        grid.add(Inky, 0, 3);
        grid.add(new Label("Inky (1 click droit)"), 1, 3);
        
        Rectangle BLINKY = new Rectangle(Configuration.IMG_WIDTH-1,Configuration.IMG_HEIGHT-1);
        BLINKY.setFill(Color.RED);
        grid.add(BLINKY, 0, 4);
        grid.add(new Label("Blinky (2 clicks droit)"), 1, 4);
        
        Rectangle PINKY = new Rectangle(Configuration.IMG_WIDTH-1,Configuration.IMG_HEIGHT-1);
        PINKY.setFill(Color.rgb(229, 184, 242, 1));
        grid.add(PINKY, 0, 5);
        grid.add(new Label("Pinky (3 clicks droit)"), 1, 5);
        
        Rectangle CLYDE = new Rectangle(Configuration.IMG_WIDTH-1,Configuration.IMG_HEIGHT-1);
        CLYDE.setFill(Color.rgb(255, 205, 97, 1));
        grid.add(CLYDE, 0, 6);
        grid.add(new Label("Clyde (4 clicks droits)"), 1, 6);
        
        Rectangle pacman = new Rectangle(Configuration.IMG_WIDTH-1,Configuration.IMG_HEIGHT-1);
        pacman.setFill(Color.rgb(105, 99, 44, 1));
        grid.add(pacman, 0, 7);
        grid.add(new Label("Pacman (1 click molette)"), 1, 7);

        dialog.getDialogPane().setContent(grid);

        // Convert the result to a username-password-pair when the login button is clicked.
//        dialog.setResultConverter(dialogButton -> {
//            if (dialogButton == loginButtonType) {
//                return null;
//            }
//            return null;
//        });

        dialog.show();
    }
    
    private void Personnalisation(int x, int y){
        //Ajout des items à la grille
        StackPane root = new StackPane();//Ajout de la grille
        
        grid = new GridPane();
        
        Configuration.setHauteurGrille(x);
        Configuration.setLargeurGrille(y);

        // initialisation de la grille (sans image)
        int i = 0;
        int j = 0;
        for (i = 0; i < y; i++) {
            for (j = 0; j < x; j++) {
                CaseGrilleJava rect = new CaseGrilleJava(Configuration.IMG_WIDTH-1,Configuration.IMG_HEIGHT-1);
                rect.setStyle("-fx-fill: black; -fx-stroke: white; -fx-stroke-width: 0.5;");
                rect.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent t) {
                        if (t.getButton() == MouseButton.SECONDARY) {
                            switch(rect.type){
                                case INKY:
                                    rect.type = CaseGrilleJava.typeCase.BLINKY;
                                    rect.setFill(Color.RED);
                                    break;
                                case BLINKY:
                                    rect.type = CaseGrilleJava.typeCase.PINKY;
                                    rect.setFill(Color.rgb(229, 184, 242, 1));
                                    break;
                                case PINKY:
                                    rect.type = CaseGrilleJava.typeCase.CLYDE;
                                    rect.setFill(Color.rgb(255, 205, 97, 1));
                                    break;
                                case CLYDE:
                                    rect.type = CaseGrilleJava.typeCase.COULOIR;
                                    rect.setFill(Color.BLACK);
                                    break;
                                default :
                                    rect.type = CaseGrilleJava.typeCase.INKY;
                                    rect.setFill(Color.rgb(134, 236, 247, 1));
                                    break;
                            }
                        }
                        else if(t.getButton() == MouseButton.MIDDLE){
                            switch(rect.type){
                                case PACMAN:
                                    rect.type = CaseGrilleJava.typeCase.COULOIR;
                                    rect.setFill(Color.BLACK);
                                    break;
                                default :
                                    rect.type = CaseGrilleJava.typeCase.PACMAN;
                                    rect.setFill(Color.rgb(105, 99, 44, 1));
                                    break;
                            }
                        }
                        else{
                           switch(rect.type){
                            case COULOIR:
                                rect.type = CaseGrilleJava.typeCase.MUR;
                                rect.setFill(Color.BLUE);
                                break;
                            case MUR:
                                rect.type = CaseGrilleJava.typeCase.PAC_GOMME;
                                rect.setFill(Color.YELLOW);
                                break;
                            case PAC_GOMME:
                                rect.type = CaseGrilleJava.typeCase.SUPER_PAC_GOMME;
                                rect.setFill(Color.YELLOWGREEN);
                                break;
                            case SUPER_PAC_GOMME:
                                rect.type = CaseGrilleJava.typeCase.COULOIR;
                                rect.setFill(Color.BLACK);
                                break;
                           case INKY:
                                rect.type = CaseGrilleJava.typeCase.COULOIR;
                                rect.setFill(Color.BLACK);
                                break;
                            case BLINKY:
                                rect.type = CaseGrilleJava.typeCase.COULOIR;
                                rect.setFill(Color.BLACK);
                                break;
                            case PINKY:
                                rect.type = CaseGrilleJava.typeCase.COULOIR;
                                rect.setFill(Color.BLACK);
                                break;
                            case CLYDE:
                                rect.type = CaseGrilleJava.typeCase.COULOIR;
                                rect.setFill(Color.BLACK);
                                break;
                            }
                        } 
                    }
                });
                grid.add(rect, i, j);
            }
        }
        grid.add(new Label("Attention à placer au minimum :"), 0, j+1,i, 1);
        grid.add(new Label("- Une pacgomme."), 0, j+2,i, 1);
        grid.add(new Label("- Pacman."), 0, j+3,i, 1);
        grid.add(new Label("- les 4 fantômes."), 0, j+4,i, 1);
        
        
        Button btnOk = new Button("OK");
        btnOk.setMinSize(120, Configuration.IMG_HEIGHT);
        btnOk.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                String fichier = creerPopupChoixFichier();
                if (fichier.isEmpty()) {
                    fichier = Configuration.PATH_MAPS_ASSETS_FOLDER + "\\CustomMap.txt";
                }else{
                    if (!fichier.contains(".txt")) {
                        fichier = fichier.concat(".txt");
                    }
                    fichier = Configuration.PATH_MAPS_ASSETS_FOLDER + "\\" + fichier;
                }
                maGrille.setCustomMap(x, y, grid, fichier);
                start(pStage);
            }
        });
        grid.add(btnOk, i/2, j+5,i/2, 1);
        
        Button btnLegende = new Button("Légende");
        btnLegende.setMinSize(120, Configuration.IMG_HEIGHT);
        btnLegende.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                creerLegendePersonnalisation();
            }
        });
        grid.add(btnLegende, 0, j+5,i/2, 1);

        root.getChildren().add(grid); 
        root.setStyle("-fx-background-color: #000000;");
        Image imIcon = new Image(Configuration.PATH_TO_IMG + "icon.png",Configuration.IMG_WIDTH,Configuration.IMG_HEIGHT,false,false);
        scene = new Scene(root, Configuration.WINDOW_WIDTH, Configuration.WINDOW_HEIGTH+4*Configuration.IMG_HEIGHT, Paint.valueOf("Black"));
        pStage.getIcons().add(imIcon);
        pStage.setTitle("Personnalisation de la map custom");
        pStage.setScene(scene);
        pStage.show();
    }
    
    private String nextLvl(){
        if (!niveauEnCours.isEmpty()) {
            char last = niveauEnCours.charAt(niveauEnCours.length()-5);
            int lvl = Integer.parseInt(String.valueOf(last));
            String newLvl = niveauEnCours.substring(0, niveauEnCours.length() -5);
            newLvl = newLvl.concat(String.valueOf(lvl+1));
            newLvl = newLvl.concat(".txt");
            return newLvl;
        }
        return "";
    }
    
    private void lancerPartie(String lvl){

        //Ajout des items à la grille
        BorderPane root = new BorderPane();//Ajout de la grille
        
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

        root.setCenter(grid);
        root.setStyle("-fx-background-color: #000000;");
        
        BorderPane bottom = new BorderPane();
        
        StackPane stack = new StackPane();
        stack.getChildren().add(new Rectangle(Configuration.WINDOW_WIDTH,100,Color.WHITE));
        
        GridPane lifesPane = new GridPane();
        lifesPane.add(new Label("Nombre de vie(s) restante(s) :"), 0, 0);
        lifesPane.add(lifes, 1, 0);
        
        stack.getChildren().add(lifesPane);
        
        root.setBottom(stack);
        
        Image imIcon = new Image(Configuration.PATH_TO_IMG + "icon.png",Configuration.IMG_WIDTH,Configuration.IMG_HEIGHT,false,false);
        scene = new Scene(root, Configuration.WINDOW_WIDTH, Configuration.WINDOW_HEIGTH + Configuration.IMG_HEIGHT, Color.WHITE);
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
                    
                    Platform.runLater(new Runnable() {
                            @Override public void run() {
                                lifes.setText("  " + maGrille.remainingLifes);
                            }
                        });
                    if (maGrille.Victoire) {
                        maGrille.Victoire = false;
                        Platform.runLater(new Runnable() {
                            @Override public void run() {
                                maGrille.stop();
                                maGrille = new Grille();
                                lancerEcran(false);
                            }
                        });
                    }else if(maGrille.Defaite){
                        maGrille.Defaite = false;
                        Platform.runLater(new Runnable() {
                            @Override public void run() {
                                maGrille.stop();
                                maGrille = new Grille();
                                lancerEcran(true);
                            }
                        });
                    }
                    else{
                        if (maGrille.tabCaseStatique.length != size_x || maGrille.tabCaseStatique[0].length != size_y ) {
                            majTabAffichage(maGrille.tabCaseStatique.length, maGrille.tabCaseStatique[0].length);
                        }
                        setGridImg();
                        setGridEntityImg();
                    }
                }
            };
            
            String niveau = "";
            if (lvl.isEmpty() && modeHistoire == false) {
                niveau = popupSelectionNiveau("Attention");
            }else if(lvl.isEmpty() && modeHistoire){
                String nextLvl = nextLvl();
                File f = new File(Configuration.PATH_MAPS_ASSETS_FOLDER + "\\" + nextLvl);
                if (f.exists()) {
                    niveau = nextLvl;
                }else{
                    niveau = popupSelectionNiveau("Niveau " + nextLvl + " inexistant, veuillez en choisir un nouveau.");
                }
            }else{
                niveau = lvl;
            }
            niveauEnCours = niveau;
            
            maGrille.addObserver(o);
            maGrille.lireGrilleFichier(Configuration.PATH_MAPS_ASSETS_FOLDER + "\\" + niveau);
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
                            maGrille = new Grille();
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
                                maGrille = new Grille();
                                lancerPartie(niveauEnCours);
                            }else{
                                lancerPartie(niveauEnCours);
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
                }else{
                    tab[i][j].setImage(maGrille.tabCaseStatique[i][j].getImg());
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
        synchronized(this){
            try {
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
            } catch (Exception e) {
            }
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
