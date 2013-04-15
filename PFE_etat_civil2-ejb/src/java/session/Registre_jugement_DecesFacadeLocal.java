/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Registre_jugement_Deces;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sfayn
 */
@Local
public interface Registre_jugement_DecesFacadeLocal {

    void create(Registre_jugement_Deces registre_jugement_Deces);

    void edit(Registre_jugement_Deces registre_jugement_Deces);

    void remove(Registre_jugement_Deces registre_jugement_Deces);

    Registre_jugement_Deces find(Object id);

    List<Registre_jugement_Deces> findAll();

    List<Registre_jugement_Deces> findRange(int[] range);

    int count();
    
}
