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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Lob;
import javax.persistence.Temporal;

/**
 *
 * @author Sfayn
 */
@Entity
public class Jugement_Deces implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer numActe;
    @ManyToOne
    private Registre_jugement_Deces registre;
    @OneToOne
    private Acte_Deces acte_deces;
    @Lob
    private String descrAr;
    private String descrFr;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datesdistr;

    public Integer getNumActe() {
        return numActe;
    }

    public void setNumActe(Integer numActe) {
        this.numActe = numActe;
    }

    public Registre_jugement_Deces getRegistre() {
        return registre;
    }

    public void setRegistre(Registre_jugement_Deces registre) {
        this.registre = registre;
    }

    public Acte_Deces getActe_deces() {
        return acte_deces;
    }

    public void setActe_deces(Acte_Deces acte_deces) {
        this.acte_deces = acte_deces;
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

    public Date getDatesdistr() {
        return datesdistr;
    }

    public void setDatesdistr(Date datesdistr) {
        this.datesdistr = datesdistr;
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
        if (!(object instanceof Jugement_Deces)) {
            return false;
        }
        Jugement_Deces other = (Jugement_Deces) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.Jugement_Deces[ id=" + id + " ]";
    }
    
}
