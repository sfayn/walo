/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Type_Donnees_Marginales;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sfayn
 */
@Local
public interface Type_Donnees_MarginalesFacadeLocal {

    void create(Type_Donnees_Marginales type_Donnees_Marginales);

    void edit(Type_Donnees_Marginales type_Donnees_Marginales);

    void remove(Type_Donnees_Marginales type_Donnees_Marginales);

    Type_Donnees_Marginales find(Object id);

    List<Type_Donnees_Marginales> findAll();

    List<Type_Donnees_Marginales> findRange(int[] range);

    int count();
    
}
