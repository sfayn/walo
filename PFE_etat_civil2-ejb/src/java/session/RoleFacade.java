/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Role;
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
public class RoleFacade extends AbstractFacade<Role> implements RoleFacadeLocal {
    @PersistenceContext(unitName = "PFE_etat_civil2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RoleFacade() {
        super(Role.class);
    }
    
}
