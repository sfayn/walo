/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Registre_jugement_Naissance;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sfayn
 */
@Local
public interface Registre_jugement_NaissanceFacadeLocal {

    void create(Registre_jugement_Naissance registre_jugement_Naissance);

    void edit(Registre_jugement_Naissance registre_jugement_Naissance);

    void remove(Registre_jugement_Naissance registre_jugement_Naissance);

    Registre_jugement_Naissance find(Object id);

    List<Registre_jugement_Naissance> findAll();

    List<Registre_jugement_Naissance> findRange(int[] range);

    int count();
    
}
