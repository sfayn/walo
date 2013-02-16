/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sfayn
 */
@Local
public interface DateFacadeLocal {

    void create(Date date);

    void edit(Date date);

    void remove(Date date);

    Date find(Object id);

    List<Date> findAll();

    List<Date> findRange(int[] range);

    int count();
    
}
