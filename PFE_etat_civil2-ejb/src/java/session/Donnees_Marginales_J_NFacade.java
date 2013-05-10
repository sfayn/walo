/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Donnees_Marginales_J_N;
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
public class Donnees_Marginales_J_NFacade extends AbstractFacade<Donnees_Marginales_J_N> implements Donnees_Marginales_J_NFacadeLocal {
    @PersistenceContext(unitName = "PFE_etat_civil2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Donnees_Marginales_J_NFacade() {
        super(Donnees_Marginales_J_N.class);
    }
    
}
