/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Acte_Deces;
import bean.Acte_Naissance;
import bean.Registre;
import bean.Registre_Deces;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jws.WebService;
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
@WebService()
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
    public List<Registre> findByDate_Deces(String annee) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Registre_Deces.class);
        Root emp = cq.from(Registre_Deces.class);
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
    
    public List<Object[]> countByAge(Integer annee, Integer month, Integer min, Integer max) {
        Query queryProductsByName = em.createNamedQuery("Acte_Deces.countByAge");
        queryProductsByName.setParameter("year", annee);
        queryProductsByName.setParameter("month", month);
        queryProductsByName.setParameter("min", min);
        queryProductsByName.setParameter("max", max);
        List<Object[]> results = queryProductsByName.getResultList();
        for (Object[] result : results) {
            String sex = ((String) result[1]);
            int count = ((Number) result[0]).intValue();
            System.out.println("sex: "+sex+", count: "+count);
        }
        
        return results;
    }
    
    public List<Object[]> countBySex(Integer annee, Integer month) {
        Query queryProductsByName = em.createNamedQuery("Acte_Deces.countBySex");
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
    
    public List<Object[]> countByDate(Integer annee) {
        Query queryProductsByName = em.createNamedQuery("Acte_Deces.countByDate");
        queryProductsByName.setParameter("year", annee);
        List<Object[]> results = queryProductsByName.getResultList();
        for (Object[] result : results) {
            int sex = ((Number) result[1]).intValue();
            int count = ((Number) result[0]).intValue();
            System.out.println("sex: "+sex+", count: "+count);
        }
        
        return results;
    }
    
    public int countByUser(Long user) {
        Query queryProductsByName = em.createNamedQuery("Acte_Deces.countByUser");
        queryProductsByName.setParameter("user", user.intValue());
        int count = Integer.parseInt(queryProductsByName.getSingleResult()+"");
        return count;
    }
    
    public Integer countByTriMonths(Integer annee, Integer month1, Integer month2, Integer month3) {
        Query queryProductsByName = em.createNamedQuery("Acte_Deces.countByTriMonths");
        queryProductsByName.setParameter("year", annee);
        queryProductsByName.setParameter("month1", month1);
        queryProductsByName.setParameter("month2", month2);
        queryProductsByName.setParameter("month3", month3);
        Integer results = Integer.parseInt(queryProductsByName.getSingleResult()+"");
        
        return results;
    }
    
    public Integer countByMonth(Integer annee, Integer month) {
        Query queryProductsByName = em.createNamedQuery("Acte_Deces.countByTriMonths");
        queryProductsByName.setParameter("year", annee);
        queryProductsByName.setParameter("month", month);
        Integer results = queryProductsByName.getFirstResult();
        
        return results;
    }
    
    public List<Acte_Deces> findByAnnee(Long acte, Long annee) {
        Query queryProductsByName = em.createNamedQuery("Acte_Deces.findByAnnee");
        String a = "%";
        String b = "%";
        if(acte>0){
            a=""+acte;
        }
        
        if(annee>0 ){
            b = ""+annee;
        }
        queryProductsByName.setParameter("acte", a);
        queryProductsByName.setParameter("annee", b);
        List<Acte_Deces> results = queryProductsByName.getResultList();

        return results;
    }
    
    public List<Object[]> countByYearsMonths(Integer year1, Integer year2) {
        Query queryProductsByName = em.createNamedQuery("Acte_Deces.countByYearsMonths");
        queryProductsByName.setParameter("year1", year1);
        queryProductsByName.setParameter("year2", year2);
        List<Object[]> results = queryProductsByName.getResultList();
        /*for (Object[] result : results) {
            int year = ((Number) result[0]).intValue();
            int count = ((Number) result[1]).intValue();
            int month = ((Number) result[2]).intValue();
            //System.out.println("year: "+year+" | month: "+month+" | count: "+count);
        }*/
        return results;
    }
}
