/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Sfayn
 */
@Entity
@Table( 
        uniqueConstraints=
            @UniqueConstraint(columnNames={"numReg", "annee"})
    )
public class Registre_Deces implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer numReg;
    private String annee;
    private Integer numPreAct;
    private Integer nbrAct;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateEnr;

    public Integer getNumReg() {
        return numReg;
    }

    public void setNumReg(Integer numReg) {
        this.numReg = numReg;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public Integer getNumPreAct() {
        return numPreAct;
    }

    public void setNumPreAct(Integer numPreAct) {
        this.numPreAct = numPreAct;
    }

    public Integer getNbrAct() {
        return nbrAct;
    }

    public void setNbrAct(Integer nbrAct) {
        this.nbrAct = nbrAct;
    }

    public Date getDateEnr() {
        return dateEnr;
    }

    public void setDateEnr(Date dateEnr) {
        this.dateEnr = dateEnr;
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
        if (!(object instanceof Registre_Deces)) {
            return false;
        }
        Registre_Deces other = (Registre_Deces) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return numReg+"";
    }
    
}
