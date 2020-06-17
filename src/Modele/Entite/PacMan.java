package Modele.Entite;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Modele.Configuration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Epulapp
 */
public class PacMan extends Entite {
    private Image imPacman = new Image(Configuration.PATH_TO_IMG + "/Sprite/TestAnim/pm1.png");

    private boolean animation = false;
    public List<Image> images = new ArrayList<>();

    public Image getImPacman() {
        synchronized(this){
            return imPacman;
        }
    }
    
    public PacMan(){
        super();
        Runnable r = new Runnable() {
            @Override public void run() {
                while (true) {                    
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PacMan.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        (new Thread(r)).start();
        for(int i =1; i <= 4; i++){
            images.add(new Image(Configuration.PATH_TO_IMG + "/Sprite/TestAnim/pm"+ i +".png"));
        }
    }

    public void setImPacman(Image imPacman) {
            this.imPacman = imPacman;
    }
}
