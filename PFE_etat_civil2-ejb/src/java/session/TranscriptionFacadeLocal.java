/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import bean.Transcription;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sfayn
 */
@Local
public interface TranscriptionFacadeLocal {

    void create(Transcription transcription);

    void edit(Transcription transcription);

    void remove(Transcription transcription);

    Transcription find(Object id);

    List<Transcription> findAll();

    List<Transcription> findRange(int[] range);

    int count();
    
}
