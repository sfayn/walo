/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author Sfayn
 */
public class Value {
    public String valeur;

    public Value(String valeur) {
        this.valeur = valeur;
    }

    
    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    @Override
    public String toString() {
        return valeur;
    }
    
    
    
}
