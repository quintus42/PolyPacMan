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
public abstract class CaseStatique {
    
    private Image imgCase;
    
    public Image getImg(){
        return imgCase;
    }
    
    public abstract void effectuerTraitement();
    
    protected CaseStatique(String path){
        this.imgCase = new Image(path);
    }
    
    protected CaseStatique(){
        this.imgCase = null;
    }
    
    protected void setImg(Image im){
        this.imgCase = im;
    }
}
