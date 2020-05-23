/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele.Case;

/**
 *
 * @author Epulapp
 */
public class Couloir extends CaseStatique {
    public boolean mangee = false;

    public Couloir(String path) {
        super(path);
    }
    
    public Couloir() {
        super();
    }
    
    protected void traitementCase(){
        return;
    }

    @Override
    public void effectuerTraitement() {
        this.mangee = true;
        super.setImg(null);
    }
}
