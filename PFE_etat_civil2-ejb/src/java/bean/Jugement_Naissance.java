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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Sfayn
 */
@Entity
@Table( 
        uniqueConstraints =
@UniqueConstraint(columnNames = {"numActe", "registre_id"}))
public class Jugement_Naissance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer numActe;
    private boolean checked = false;
    @ManyToOne
    private Registre_jugement_Naissance registre;
    @OneToOne
    private Acte_Naissance acte_Naissance;
    @Lob
    private String tasrhAr;
    private String tasrhFr;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDeclaration;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateJug;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datesdistr;
    @Lob
    private String descriptionAr;
    @Lob
    private String descriptionFr;
    @ManyToOne
    private User createdBy;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date createdAt;

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Integer getNumActe() {
        return numActe;
    }

    public void setNumActe(Integer numActe) {
        this.numActe = numActe;
    }

    public Registre_jugement_Naissance getRegistre() {
        return registre;
    }

    public void setRegistre(Registre_jugement_Naissance registre) {
        this.registre = registre;
    }

    public Acte_Naissance getActe_Naissance() {
        return acte_Naissance;
    }

    public void setActe_Naissance(Acte_Naissance acte_Naissance) {
        this.acte_Naissance = acte_Naissance;
    }

    public String getTasrhAr() throws UnsupportedEncodingException {
        return tasrhAr == null ? "" : URLDecoder.decode(tasrhAr, "UTF-8");
    }

    public void setTasrhAr(String tasrhAr) {
        this.tasrhAr = tasrhAr;
    }

    public String getTasrhFr() {
        return tasrhFr;
    }

    public void setTasrhFr(String tasrhFr) {
        this.tasrhFr = tasrhFr;
    }

    public Date getDateDeclaration() {
        return dateDeclaration;
    }

    public void setDateDeclaration(Date dateDeclaration) {
        this.dateDeclaration = dateDeclaration;
    }

    public Date getDateJug() {
        return dateJug;
    }

    public void setDateJug(Date dateJug) {
        this.dateJug = dateJug;
    }

    public Date getDatesdistr() {
        return datesdistr;
    }

    public void setDatesdistr(Date datesdistr) {
        this.datesdistr = datesdistr;
    }

    public String getDescriptionAr() throws UnsupportedEncodingException {
        return descriptionAr == null ? "" : URLDecoder.decode(descriptionAr, "UTF-8");
    }

    public void setDescriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
    }

    public String getDescriptionFr() {
        return descriptionFr;
    }

    public void setDescriptionFr(String descriptionFr) {
        this.descriptionFr = descriptionFr;
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
        if (!(object instanceof Jugement_Naissance)) {
            return false;
        }
        Jugement_Naissance other = (Jugement_Naissance) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.Jugement_Naissance[ id=" + id + " ]";
    }
}
