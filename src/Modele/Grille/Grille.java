/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele.Grille;

import Modele.Case.CaseStatique;
import java.util.Dictionary;
import javafx.geometry.Point2D;


/**
 *
 * @author Epulapp
 */
public class Grille {
    private CaseStatique[][] tabCaseStatique;
    private Dictionary<Modele.Entite.Entite, Point2D> tabEntites;

    public boolean depOk(String dep, Modele.Entite.Entite e){
        return false;
    }
}
