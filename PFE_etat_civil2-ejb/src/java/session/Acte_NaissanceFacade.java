/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Acte_Naissance;
import bean.Registre;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
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
public class Acte_NaissanceFacade extends AbstractFacade<Acte_Naissance> implements Acte_NaissanceFacadeLocal {

    @PersistenceContext(unitName = "PFE_etat_civil2-ejbPU")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public Acte_NaissanceFacade() {
        super(Acte_Naissance.class);
    }

    public List<Registre> findByDate(String annee) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Registre.class);
        Root emp = cq.from(Registre.class);
        Predicate predicate = cb.equal(emp.get("annee"), annee);
        cq.where(predicate);
        cq.select(emp);

        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<Object[]> countByDate(Integer annee) {
        Query queryProductsByName = em.createNamedQuery("Acte_Naissance.countByDate");
        queryProductsByName.setParameter("year", annee);
        List<Object[]> results = queryProductsByName.getResultList();

        return results;
    }

    public List<Object[]> countByDateAndSex(Integer annee) {
        Query queryProductsByName = em.createNamedQuery("Acte_Naissance.countByDateAndSex");
        queryProductsByName.setParameter("year", annee);
        List<Object[]> results = queryProductsByName.getResultList();

        return results;
    }
    
    public List<Object[]> countBySex(Integer annee, Integer month) {
        Query queryProductsByName = em.createNamedQuery("Acte_Naissance.countBySex");
        queryProductsByName.setParameter("year", annee);
        queryProductsByName.setParameter("month", month);
        List<Object[]> results = queryProductsByName.getResultList();
        for (Object[] result : results) {
            String sex = ((String) result[1]);
            int count = ((Number) result[0]).intValue();
            System.out.println("sex: "+sex+", count: "+count);
        }
        
        return results;
    }

    public List<Object[]> countByAgeMere(Integer annee, Integer month, Integer min, Integer max) {
        Query queryProductsByName = em.createNamedQuery("Acte_Naissance.countByAgeMere");
        queryProductsByName.setParameter("year", annee);
        queryProductsByName.setParameter("max", max);
        queryProductsByName.setParameter("min", min);
        queryProductsByName.setParameter("month", month);
        List<Object[]> results = queryProductsByName.getResultList();
        for (Object[] result : results) {
            String sex = ((String) result[1]);
            int count = ((Number) result[0]).intValue();
            //System.out.println(min+""+max+" --> sex: "+sex+", count: "+count);
        }
        return results;
    }
}
