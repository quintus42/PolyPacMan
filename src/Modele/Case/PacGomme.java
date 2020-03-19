/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele.Case;

import Modele.Configuration;
import javafx.scene.image.Image;

/**
 *
 * @author Epulapp
 */
public class PacGomme extends Couloir {

    private final Image imPacGomme = new Image(Configuration.PATH_TO_IMG + "PacGomme.png");

    public Image getImPacGomme() {
        return imPacGomme;
    }
}
