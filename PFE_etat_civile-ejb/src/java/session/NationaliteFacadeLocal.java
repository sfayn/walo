/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Nationalite;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sfayn
 */
@Local
public interface NationaliteFacadeLocal {

    void create(Nationalite nationalite);

    void edit(Nationalite nationalite);

    void remove(Nationalite nationalite);

    Nationalite find(Object id);

    List<Nationalite> findAll();

    List<Nationalite> findRange(int[] range);

    int count();
    
}
