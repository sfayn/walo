/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Jugement_Naissance;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sfayn
 */
@Local
public interface Jugement_NaissanceFacadeLocal {

    void create(Jugement_Naissance jugement_Naissance);

    void edit(Jugement_Naissance jugement_Naissance);

    void remove(Jugement_Naissance jugement_Naissance);

    Jugement_Naissance find(Object id);

    List<Jugement_Naissance> findAll();

    List<Jugement_Naissance> findRange(int[] range);

    int count();
    
}
