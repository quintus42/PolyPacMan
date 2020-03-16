/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grille;

import Case.CaseStatique;
import java.util.Dictionary;
import javafx.geometry.Point2D;


/**
 *
 * @author Epulapp
 */
public class Grille {
    private CaseStatique[][] tabCaseStatique;
    private Dictionary<Entite.Entite, Point2D> tabEntites;
    
    public boolean depOk(String dep, Entite.Entite e){
        return false;
    }
}
