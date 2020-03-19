/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele.Case;

import javafx.scene.image.Image;

/**
 *
 * @author Epulapp
 */
public class Mur extends CaseStatique {


    private final String PATH_TO_IMG = "file:src/assets/";
    private final Image imFullWall = new Image(PATH_TO_IMG + "FullWall.png");
    private final Image imFullWallWithHoles = new Image(PATH_TO_IMG + "FullWidthWithHoles.png");;
    private final Image imLargeWall = new Image(PATH_TO_IMG + "LargeWall.png");
    private final Image imLongWall = new Image(PATH_TO_IMG + "LongWall.png");

    public Mur (){
    }

    public Image getImFullWall() {
        return imFullWall;
    }

    public Image getImFullWallWithHoles() {
        return imFullWallWithHoles;
    }

    public Image getImLargeWall() {
        return imLargeWall;
    }

    public Image getImLongWall() {
        return imLongWall;
    }
}
