/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Registre;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sfayn
 */
@Local
public interface RegistreFacadeLocal {

    void create(Registre registre);

    void edit(Registre registre);

    void remove(Registre registre);

    Registre find(Object id);

    List<Registre> findAll();

    List<Registre> findRange(int[] range);

    int count();
    
}
