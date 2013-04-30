/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Jugement_Deces;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sfayn
 */
@Local
public interface Jugement_DecesFacadeLocal {

    void create(Jugement_Deces jugement_Deces);

    void edit(Jugement_Deces jugement_Deces);

    void remove(Jugement_Deces jugement_Deces);

    Jugement_Deces find(Object id);

    List<Jugement_Deces> findAll();

    List<Jugement_Deces> findRange(int[] range);

    int count();
    
}
