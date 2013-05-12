/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Donnees_Marginales_J_D;
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
public class Donnees_Marginales_J_DFacade extends AbstractFacade<Donnees_Marginales_J_D> implements Donnees_Marginales_J_DFacadeLocal {
    @PersistenceContext(unitName = "PFE_etat_civil2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Donnees_Marginales_J_DFacade() {
        super(Donnees_Marginales_J_D.class);
    }
    
}
