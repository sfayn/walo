/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Sfayn
 */
@Entity
public class Donnees_Marginales_A_D implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;    
    @ManyToOne
    private Type_Donnees_Marginales type;    
    @ManyToOne
    private Acte_Deces acte;
    @Lob
    private String descAr;
    @Lob
    private String descFr;

    public Acte_Deces getActe() {
        return acte;
    }

    public void setActe(Acte_Deces acte) {
        this.acte = acte;
    }    
    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Type_Donnees_Marginales getType() {
        return type;
    }

    public void setType(Type_Donnees_Marginales type) {
        this.type = type;
    }



    public String getDescAr() throws UnsupportedEncodingException {
        return descAr == null ? "" : URLDecoder.decode(descAr, "UTF-8");
    }

    public void setDescAr(String descAr) {
        this.descAr = descAr;
        
    }

    public String getDescFr() {
        return descFr;
    }

    public void setDescFr(String descFr) {
        this.descFr = descFr;
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
        if (!(object instanceof Donnees_Marginales_A_D)) {
            return false;
        }
        Donnees_Marginales_A_D other = (Donnees_Marginales_A_D) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.Donnees_Marginales_A_D[ id=" + id + " ]";
    }
    
}
