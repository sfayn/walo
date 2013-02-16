/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Sfayn
 */
@Entity
public class Date implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer dateG;
    private Integer dateH;
    private String dateHAr;
    private String dateHFr;
    private String dateGAr;
    private String dateGFr;
    private String HeureAr;
    private String HeureFr;
    private String minFr;
    private String minAr;

    public Integer getDateG() {
        return dateG;
    }

    public void setDateG(Integer dateG) {
        this.dateG = dateG;
    }

    public Integer getDateH() {
        return dateH;
    }

    public void setDateH(Integer dateH) {
        this.dateH = dateH;
    }

    public String getDateHAr() {
        return dateHAr;
    }

    public void setDateHAr(String dateHAr) {
        this.dateHAr = dateHAr;
    }

    public String getDateHFr() {
        return dateHFr;
    }

    public void setDateHFr(String dateHFr) {
        this.dateHFr = dateHFr;
    }

    public String getDateGAr() {
        return dateGAr;
    }

    public void setDateGAr(String dateGAr) {
        this.dateGAr = dateGAr;
    }

    public String getDateGFr() {
        return dateGFr;
    }

    public void setDateGFr(String dateGFr) {
        this.dateGFr = dateGFr;
    }

    public String getHeureAr() {
        return HeureAr;
    }

    public void setHeureAr(String HeureAr) {
        this.HeureAr = HeureAr;
    }

    public String getHeureFr() {
        return HeureFr;
    }

    public void setHeureFr(String HeureFr) {
        this.HeureFr = HeureFr;
    }

    public String getMinFr() {
        return minFr;
    }

    public void setMinFr(String minFr) {
        this.minFr = minFr;
    }

    public String getMinAr() {
        return minAr;
    }

    public void setMinAr(String minAr) {
        this.minAr = minAr;
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
        if (!(object instanceof Date)) {
            return false;
        }
        Date other = (Date) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.Date[ id=" + id + " ]";
    }
    
}
