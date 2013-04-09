/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Registre_Deces;
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
public class Registre_DecesFacade extends AbstractFacade<Registre_Deces> implements Registre_DecesFacadeLocal {
    @PersistenceContext(unitName = "PFE_etat_civil2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Registre_DecesFacade() {
        super(Registre_Deces.class);
    }
    
}
