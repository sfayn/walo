/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Ville;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sfayn
 */
@Local
public interface VilleFacadeLocal {

    void create(Ville ville);

    void edit(Ville ville);

    void remove(Ville ville);

    Ville find(Object id);

    List<Ville> findAll();

    List<Ville> findRange(int[] range);

    int count();
    
}
