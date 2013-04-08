/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Acte_Deces;
import bean.Acte_Naissance;
import bean.Registre;
import java.util.ArrayList;
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
public class Acte_DecesFacade extends AbstractFacade<Acte_Deces> implements Acte_DecesFacadeLocal {
    @PersistenceContext(unitName = "PFE_etat_civil2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Acte_DecesFacade() {
        super(Acte_Deces.class);
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
    public List<Acte_Naissance> findActe_Naiss(int numActe,Registre registre) {
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
}
