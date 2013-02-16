/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Nationalite;
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
public class NationaliteFacade extends AbstractFacade<Nationalite> implements NationaliteFacadeLocal {
    @PersistenceContext(unitName = "PFE_etat_civile-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NationaliteFacade() {
        super(Nationalite.class);
    }
    
}
