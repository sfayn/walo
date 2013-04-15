/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Registre_jugement_Naissance;
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
public class Registre_jugement_NaissanceFacade extends AbstractFacade<Registre_jugement_Naissance> implements Registre_jugement_NaissanceFacadeLocal {
    @PersistenceContext(unitName = "PFE_etat_civil2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Registre_jugement_NaissanceFacade() {
        super(Registre_jugement_Naissance.class);
    }
    
}
