/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Donnees_Marginales_Transcription;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sfayn
 */
@Local
public interface Donnees_Marginales_TranscriptionFacadeLocal {

    void create(Donnees_Marginales_Transcription donnees_Marginales_Transcription);

    void edit(Donnees_Marginales_Transcription donnees_Marginales_Transcription);

    void remove(Donnees_Marginales_Transcription donnees_Marginales_Transcription);

    Donnees_Marginales_Transcription find(Object id);

    List<Donnees_Marginales_Transcription> findAll();

    List<Donnees_Marginales_Transcription> findRange(int[] range);

    int count();
    
}
