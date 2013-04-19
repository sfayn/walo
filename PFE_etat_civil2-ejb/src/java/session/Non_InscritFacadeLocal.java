/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Non_Inscrit;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sfayn
 */
@Local
public interface Non_InscritFacadeLocal {

    void create(Non_Inscrit non_Inscrit);

    void edit(Non_Inscrit non_Inscrit);

    void remove(Non_Inscrit non_Inscrit);

    Non_Inscrit find(Object id);

    List<Non_Inscrit> findAll();

    List<Non_Inscrit> findRange(int[] range);

    int count();
    
}
