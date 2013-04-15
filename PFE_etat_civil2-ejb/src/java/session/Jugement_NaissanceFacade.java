/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Jugement_Naissance;
import bean.Registre_jugement_Deces;
import bean.Registre_jugement_Naissance;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Sfayn
 */
@Stateless
@LocalBean
public class Jugement_NaissanceFacade extends AbstractFacade<Jugement_Naissance> implements Jugement_NaissanceFacadeLocal {
    @PersistenceContext(unitName = "PFE_etat_civil2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Jugement_NaissanceFacade() {
        super(Jugement_Naissance.class);
    }
    public List<Registre_jugement_Naissance> findByDate(String annee) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Registre_jugement_Naissance.class);
        Root emp = cq.from(Registre_jugement_Naissance.class);
        Predicate predicate = cb.equal(emp.get("annee"),annee);
        cq.where(predicate);
        cq.select(emp);
        return getEntityManager().createQuery(cq).getResultList();
    }
}
