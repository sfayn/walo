/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Acte_Naissance;
import bean.Jugement_Naissance;
import bean.Registre;
import bean.Registre_jugement_Deces;
import bean.Registre_jugement_Naissance;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
    public List<Registre> findByDate(String annee) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Registre.class);
        Root emp = cq.from(Registre.class);
        Predicate predicate = cb.equal(emp.get("annee"),annee);
        cq.where(predicate);
        cq.select(emp);
        return getEntityManager().createQuery(cq).getResultList();
    }
    public List<Acte_Naissance> findActe_Naissance(int numActe,Registre registre) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Acte_Naissance.class);
        Root emp = cq.from(Acte_Naissance.class);
        final List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(emp.get("registre"),registre));
        predicates.add(cb.equal(emp.get("numActe"),numActe));
        cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        cq.select(emp);
        return getEntityManager().createQuery(cq).getResultList();
    }
    public List<Registre_jugement_Naissance> findByDate_Jug_Naissance(String annee) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Registre_jugement_Naissance.class);
        Root emp = cq.from(Registre_jugement_Naissance.class);
        Predicate predicate = cb.equal(emp.get("annee"),annee);
        cq.where(predicate);
        cq.select(emp);
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    public Integer countByTriMonths(Integer annee, Integer month1, Integer month2, Integer month3) {
        Query queryProductsByName = em.createNamedQuery("Jugement_Naissance.countByTriMonths");
        queryProductsByName.setParameter("year", annee);
        queryProductsByName.setParameter("month1", month1);
        queryProductsByName.setParameter("month2", month2);
        queryProductsByName.setParameter("month3", month3);
        Integer results = queryProductsByName.getFirstResult();
        
        return results;
    }
    
    public Integer countByMonth(Integer annee, Integer month) {
        Query queryProductsByName = em.createNamedQuery("Jugement_Naissance.countByMonth");
        queryProductsByName.setParameter("year", annee);
        queryProductsByName.setParameter("month", month);
        Integer results = queryProductsByName.getFirstResult();
        
        return results;
    }
}
