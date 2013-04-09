/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Registre_Deces;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sfayn
 */
@Local
public interface Registre_DecesFacadeLocal {

    void create(Registre_Deces registre_Deces);

    void edit(Registre_Deces registre_Deces);

    void remove(Registre_Deces registre_Deces);

    Registre_Deces find(Object id);

    List<Registre_Deces> findAll();

    List<Registre_Deces> findRange(int[] range);

    int count();
    
}
