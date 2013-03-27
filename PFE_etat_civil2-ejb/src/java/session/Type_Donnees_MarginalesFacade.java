/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Type_Donnees_Marginales;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Sfayn
 */
@Stateless
@LocalBean
public class Type_Donnees_MarginalesFacade extends AbstractFacade<Type_Donnees_Marginales> implements Type_Donnees_MarginalesFacadeLocal {
    @PersistenceContext(unitName = "PFE_etat_civil2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Type_Donnees_MarginalesFacade() {
        super(Type_Donnees_Marginales.class);
    }
    
}
