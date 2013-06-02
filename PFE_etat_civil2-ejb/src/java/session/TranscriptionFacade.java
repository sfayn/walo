/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Registre_Transcription;
import bean.Transcription;
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
public class TranscriptionFacade extends AbstractFacade<Transcription> implements TranscriptionFacadeLocal {
    @PersistenceContext(unitName = "PFE_etat_civil2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TranscriptionFacade() {
        super(Transcription.class);
    }
        public List<Registre_Transcription> findByDate(String annee) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Registre_Transcription.class);
        Root emp = cq.from(Registre_Transcription.class);
        Predicate predicate = cb.equal(emp.get("annee"), annee);
        cq.where(predicate);
        cq.select(emp);
        return getEntityManager().createQuery(cq).getResultList();
    } 
}
