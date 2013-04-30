/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Acte_Naissance;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sfayn
 */
@Local
public interface Acte_NaissanceFacadeLocal {

    void create(Acte_Naissance acte_Naissance);

    void edit(Acte_Naissance acte_Naissance);

    void remove(Acte_Naissance acte_Naissance);

    Acte_Naissance find(Object id);

    List<Acte_Naissance> findAll();

    List<Acte_Naissance> findRange(int[] range);

    int count();
    
}
