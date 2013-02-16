/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Sex;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sfayn
 */
@Local
public interface SexFacadeLocal {

    void create(Sex sex);

    void edit(Sex sex);

    void remove(Sex sex);

    Sex find(Object id);

    List<Sex> findAll();

    List<Sex> findRange(int[] range);

    int count();
    
}
