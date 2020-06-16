/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele.Case;

import javafx.scene.shape.Rectangle;

/**
 *
 * @author Epulapp
 */
public class CaseGrilleJava extends Rectangle {
    
    public enum typeCase {
        COULOIR,
        PAC_GOMME,
        SUPER_PAC_GOMME,
        MUR,
        BLINKY,
        INKY,
        PINKY,
        CLYDE,
        PACMAN
    }
    
    public typeCase type;
    
    public CaseGrilleJava(int width, int heigth){
        super(width, heigth);
        type = typeCase.COULOIR;
    }
    
}
