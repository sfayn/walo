/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Registre_Transcription;
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
public class Registre_TranscriptionFacade extends AbstractFacade<Registre_Transcription> implements Registre_TranscriptionFacadeLocal {
    @PersistenceContext(unitName = "PFE_etat_civil2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Registre_TranscriptionFacade() {
        super(Registre_Transcription.class);
    }
    
}
