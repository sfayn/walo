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
import javax.persistence.Temporal;

/**
 *
 * @author Sfayn
 */
@Entity
public class Acte_Deces implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer numActe;
    @ManyToOne
    private Registre_Deces registre;
    @OneToOne
    private Acte_Naissance acte_Naissance;
    @Lob
    private String profession_Ar;
    private String profession_Fr;
    private String cin;
    @Lob
    private String situation_familiale;
    @Lob
    private String adresse_Ar;
    private String adresse_Fr;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDeces;
    @Lob
    private String lieuDeces_Ar;
    private String lieuDeces_Fr;
    private boolean typeT = true;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateTah_H;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateTah_G;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateHo = null;
    @Lob
    private String declaration_Ar;
    private String declaration_Fr;

    public Integer getNumActe() {
        return numActe;
    }

    public void setNumActe(Integer numActe) {
        this.numActe = numActe;
    }
    
    public Registre_Deces getRegistre() {
        return registre;
    }

    public void setRegistre(Registre_Deces registre) {
        this.registre = registre;
    }

    
    public Acte_Naissance getActe_Naissance() {
        return acte_Naissance;
    }

    public void setActe_Naissance(Acte_Naissance acte_Naissance) {
        this.acte_Naissance = acte_Naissance;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getSituation_familiale() throws UnsupportedEncodingException {
        return situation_familiale == null ? "" : URLDecoder.decode(situation_familiale, "UTF-8");
    }

    public void setSituation_familiale(String situation_familiale) {
        this.situation_familiale = situation_familiale;
    }

    public String getAdresse_Ar() throws UnsupportedEncodingException {
        return adresse_Ar == null ? "" : URLDecoder.decode(adresse_Ar, "UTF-8");
    }

    public void setAdresse_Ar(String adresse_Ar) {
        this.adresse_Ar = adresse_Ar;
    }

    public String getAdresse_Fr() {
        return adresse_Fr;
    }

    public void setAdresse_Fr(String adresse_Fr) {
        this.adresse_Fr = adresse_Fr;
    }

    public Date getDateDeces() {
        return dateDeces;
    }

    public void setDateDeces(Date dateDeces) {
        this.dateDeces = dateDeces;
    }

    public String getLieuDeces_Ar() throws UnsupportedEncodingException {
        return lieuDeces_Ar == null ? "" : URLDecoder.decode(lieuDeces_Ar, "UTF-8");
    }

    public void setLieuDeces_Ar(String lieuDeces_Ar) {
        this.lieuDeces_Ar = lieuDeces_Ar;
    }

    public String getLieuDeces_Fr() {
        return lieuDeces_Fr;
    }

    public void setLieuDeces_Fr(String lieuDeces_Fr) {
        this.lieuDeces_Fr = lieuDeces_Fr;
    }

    public boolean isTypeT() {
        return typeT;
    }

    public void setTypeT(boolean typeT) {
        this.typeT = typeT;
    }

    public Date getDateTah_H() {
        return dateTah_H;
    }

    public void setDateTah_H(Date dateTah_H) {
        this.dateTah_H = dateTah_H;
    }

    public Date getDateTah_G() {
        return dateTah_G;
    }

    public void setDateTah_G(Date dateTah_G) {
        this.dateTah_G = dateTah_G;
    }

    public Date getDateHo() {
        return dateHo;
    }

    public void setDateHo(Date dateHo) {
        this.dateHo = dateHo;
    }

    public String getDeclaration_Ar() throws UnsupportedEncodingException {
        return declaration_Ar == null ? "" : URLDecoder.decode(declaration_Ar, "UTF-8");
    }

    public void setDeclaration_Ar(String declaration_Ar) {
        this.declaration_Ar = declaration_Ar;
    }

    public String getDeclaration_Fr() {
        return declaration_Fr;
    }

    public void setDeclaration_Fr(String declaration_Fr) {
        this.declaration_Fr = declaration_Fr;
    }
    
    
    
    
    public String getProfession_Ar() throws UnsupportedEncodingException {
        return profession_Ar == null ? "" : URLDecoder.decode(profession_Ar, "UTF-8");
    }

    public void setProfession_Ar(String profession_Ar) {
        this.profession_Ar = profession_Ar;
    }

    public String getProfession_Fr() {
        return profession_Fr;
    }

    public void setProfession_Fr(String profession_Fr) {
        this.profession_Fr = profession_Fr;
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
        if (!(object instanceof Acte_Deces)) {
            return false;
        }
        Acte_Deces other = (Acte_Deces) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.a[ id=" + id + " ]";
    }
}
