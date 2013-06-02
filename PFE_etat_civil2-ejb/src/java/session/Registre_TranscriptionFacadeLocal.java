/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Registre_Transcription;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sfayn
 */
@Local
public interface Registre_TranscriptionFacadeLocal {

    void create(Registre_Transcription registre_Transcription);

    void edit(Registre_Transcription registre_Transcription);

    void remove(Registre_Transcription registre_Transcription);

    Registre_Transcription find(Object id);

    List<Registre_Transcription> findAll();

    List<Registre_Transcription> findRange(int[] range);

    int count();
    
}
