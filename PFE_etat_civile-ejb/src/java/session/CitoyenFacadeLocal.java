/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Citoyen;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sfayn
 */
@Local
public interface CitoyenFacadeLocal {

    void create(Citoyen citoyen);

    void edit(Citoyen citoyen);

    void remove(Citoyen citoyen);

    Citoyen find(Object id);

    List<Citoyen> findAll();

    List<Citoyen> findRange(int[] range);

    int count();
    
}
