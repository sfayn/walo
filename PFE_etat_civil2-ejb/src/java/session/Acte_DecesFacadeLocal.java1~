/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Acte_Deces;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sfayn
 */
@Local
public interface Acte_DecesFacadeLocal {

    void create(Acte_Deces acte_Deces);

    void edit(Acte_Deces acte_Deces);

    void remove(Acte_Deces acte_Deces);

    Acte_Deces find(Object id);

    List<Acte_Deces> findAll();

    List<Acte_Deces> findRange(int[] range);

    int count();
    
}
