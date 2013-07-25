/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 *
 * @author Sfayn
 */
@Entity
@Table( name="type_donnees_marginales")
public class Type_Donnees_Marginales implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private String libelleAr;
    private String libelleFr;
    @Lob
    private String descrAr;
    @Lob
    private String descrFr;
    @Lob
    private String attrs;

    public String getAttrs() throws UnsupportedEncodingException {
        return attrs == null ? "" : URLDecoder.decode(attrs, "UTF-8");
    }

    public void setAttrs(String attrs) {
        this.attrs = attrs;        
    }

    

    public String getDescrAr() throws UnsupportedEncodingException {
        return descrAr == null ? "" : URLDecoder.decode(descrAr, "UTF-8");
    }

    public void setDescrAr(String descrAr) {
        this.descrAr = descrAr;
    }

    public String getDescrFr() {
        return descrFr;
    }

    public void setDescrFr(String descrFr) {
        this.descrFr = descrFr;
    }


    
    public String getLibelleAr()  throws UnsupportedEncodingException {
        return libelleAr == null ? "" : URLDecoder.decode(libelleAr, "UTF-8");
    }

    public void setLibelleAr(String libelleAr) {
        this.libelleAr = libelleAr;
    }

    public String getLibelleFr() {
        return libelleFr;
    }

    public void setLibelleFr(String libelleFr) {
        this.libelleFr = libelleFr;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Type_Donnees_Marginales)) {
            return false;
        }
        Type_Donnees_Marginales other = (Type_Donnees_Marginales) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return libelleAr;
    }
    
}
