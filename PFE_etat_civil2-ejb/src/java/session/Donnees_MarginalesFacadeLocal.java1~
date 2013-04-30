/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Donnees_Marginales;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sfayn
 */
@Local
public interface Donnees_MarginalesFacadeLocal {

    void create(Donnees_Marginales donnees_Marginales);

    void edit(Donnees_Marginales donnees_Marginales);

    void remove(Donnees_Marginales donnees_Marginales);

    Donnees_Marginales find(Object id);

    List<Donnees_Marginales> findAll();

    List<Donnees_Marginales> findRange(int[] range);

    int count();
    
}
