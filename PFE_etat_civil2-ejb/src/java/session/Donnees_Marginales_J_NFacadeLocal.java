/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Donnees_Marginales_J_N;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sfayn
 */
@Local
public interface Donnees_Marginales_J_NFacadeLocal {

    void create(Donnees_Marginales_J_N donnees_Marginales_J_N);

    void edit(Donnees_Marginales_J_N donnees_Marginales_J_N);

    void remove(Donnees_Marginales_J_N donnees_Marginales_J_N);

    Donnees_Marginales_J_N find(Object id);

    List<Donnees_Marginales_J_N> findAll();

    List<Donnees_Marginales_J_N> findRange(int[] range);

    int count();
    
}
