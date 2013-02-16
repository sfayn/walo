/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Citoyen;
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
public class CitoyenFacade extends AbstractFacade<Citoyen> implements CitoyenFacadeLocal {
    @PersistenceContext(unitName = "PFE_etat_civile-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CitoyenFacade() {
        super(Citoyen.class);
    }
    
}
