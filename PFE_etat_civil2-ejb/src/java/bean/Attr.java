/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
/**
 *
 * @author Sfayn
*/
public class Attr implements Serializable {
    private static final long serialVersionUID = 1L;
    private String libelle;

    
    private Type_Donnees_Marginales tdm;

    public Attr() {       
    }

    public Attr(String libelle) {
       this.libelle=libelle;
    }


    public Type_Donnees_Marginales getTdm() {
        return tdm;
    }

    public void setTdm(Type_Donnees_Marginales tdm) {
        this.tdm = tdm;
    }


    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return libelle;
    }
    
    
}
