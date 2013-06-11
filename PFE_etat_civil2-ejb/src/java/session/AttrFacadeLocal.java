/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Attr;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sfayn
 */
@Local
public interface AttrFacadeLocal {

    void create(Attr attr);

    void edit(Attr attr);

    void remove(Attr attr);

    Attr find(Object id);

    List<Attr> findAll();

    List<Attr> findRange(int[] range);

    int count();
    
}
