/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Donnees_Marginales_J_D;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sfayn
 */
@Local
public interface Donnees_Marginales_J_DFacadeLocal {

    void create(Donnees_Marginales_J_D donnees_Marginales_J_D);

    void edit(Donnees_Marginales_J_D donnees_Marginales_J_D);

    void remove(Donnees_Marginales_J_D donnees_Marginales_J_D);

    Donnees_Marginales_J_D find(Object id);

    List<Donnees_Marginales_J_D> findAll();

    List<Donnees_Marginales_J_D> findRange(int[] range);

    int count();
    
}
