/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
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
            @UniqueConstraint(columnNames={"numActe", "registre_id"})
    )
public class Jugement_Deces implements Serializable {
    @OneToMany(mappedBy = "jugement", targetEntity=Donnees_Marginales_J_D.class, fetch = FetchType.EAGER,orphanRemoval=true)
    private List<Donnees_Marginales_J_D> donnees_Marginaless;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private boolean checked = false;
    private Integer numActe;
    @ManyToOne
    private Registre_jugement_Deces registre;
    @Lob
    private String nom_Ar;
    private String nom_Fr;
    @Lob
    private String prenom_Ar;
    private String prenom_Fr;
    private String lieu_de_Naiss_Fr;
    @Lob
    private String lieu_de_Naiss_Ar;
    private boolean noMJ=false;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date date_de_naiss_H;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date date_de_naiss_G;
    @Lob
    private String profession_Ar;
    private String profession_Fr;
    private String cin;
    @Lob
    private String situation_familiale;
    @Lob
    private String adresse_Ar;
    private String adresse_Fr;
    private boolean noMJD = false;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDecesG;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDecesH;
    @Lob
    private String lieuDeces_Ar;
    private String lieuDeces_Fr;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateTah_H;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateTah_G;    
    @Lob
    private String descrAr;
    @Lob
    private String descrFr;
    @Lob
    private String prenomP_Ar;
    private String prenomP_Fr;    
    @Lob
    private String nationaliteP_Ar="مغربية";
    private String nationaliteP_Fr="Marocaine";
    //info mere
    @Lob
    private String prenomM_Ar;
    private String prenomM_Fr;
    @Lob
    private String nationaliteM_Ar="مغربية";
    private String nationaliteM_Fr="Marocaine";
    @ManyToOne
    private Sex sex;
    @ManyToOne
    private User createdBy;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date createdAt;

    public Jugement_Deces() {
        donnees_Marginaless=new ArrayList<Donnees_Marginales_J_D>();
    }

    public String getLieu_de_Naiss_Fr() {
        return lieu_de_Naiss_Fr;
    }

    public void setLieu_de_Naiss_Fr(String lieu_de_Naiss_Fr) {
        this.lieu_de_Naiss_Fr = lieu_de_Naiss_Fr;
    }

    public String getLieu_de_Naiss_Ar() {
        return lieu_de_Naiss_Ar;
    }

    public void setLieu_de_Naiss_Ar(String lieu_de_Naiss_Ar) {
        this.lieu_de_Naiss_Ar = lieu_de_Naiss_Ar;
    }

    public Date getDate_de_naiss_H() {
        return date_de_naiss_H;
    }

    public void setDate_de_naiss_H(Date date_de_naiss_H) {
        this.date_de_naiss_H = date_de_naiss_H;
    }

    public Date getDate_de_naiss_G() {
        return date_de_naiss_G;
    }

    public void setDate_de_naiss_G(Date date_de_naiss_G) {
        this.date_de_naiss_G = date_de_naiss_G;
    }

    public boolean isNoMJD() {
        return noMJD;
    }

    public void setNoMJD(boolean noMJD) {
        this.noMJD = noMJD;
    }
    

    public String getNom_Ar() {
        return nom_Ar;
    }

    public void setNom_Ar(String nom_Ar) {
        this.nom_Ar = nom_Ar;
    }

    public String getNom_Fr() {
        return nom_Fr;
    }

    public void setNom_Fr(String nom_Fr) {
        this.nom_Fr = nom_Fr;
    }

    public String getPrenom_Ar() {
        return prenom_Ar;
    }

    public void setPrenom_Ar(String prenom_Ar) {
        this.prenom_Ar = prenom_Ar;
    }

    public String getPrenom_Fr() {
        return prenom_Fr;
    }

    public void setPrenom_Fr(String prenom_Fr) {
        this.prenom_Fr = prenom_Fr;
    }

    public String getProfession_Ar() {
        return profession_Ar;
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

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getSituation_familiale() {
        return situation_familiale;
    }

    public void setSituation_familiale(String situation_familiale) {
        this.situation_familiale = situation_familiale;
    }

    public String getAdresse_Ar() {
        return adresse_Ar;
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

    public boolean isNoMJ() {
        return noMJ;
    }

    public void setNoMJ(boolean noMJ) {
        this.noMJ = noMJ;
    }

    public Date getDateDecesG() {
        return dateDecesG;
    }

    public void setDateDecesG(Date dateDecesG) {
        this.dateDecesG = dateDecesG;
    }

    public Date getDateDecesH() {
        return dateDecesH;
    }

    public void setDateDecesH(Date dateDecesH) {
        this.dateDecesH = dateDecesH;
    }

    public String getLieuDeces_Ar() {
        return lieuDeces_Ar;
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

    public String getPrenomP_Ar() {
        return prenomP_Ar;
    }

    public void setPrenomP_Ar(String prenomP_Ar) {
        this.prenomP_Ar = prenomP_Ar;
    }

    public String getPrenomP_Fr() {
        return prenomP_Fr;
    }

    public void setPrenomP_Fr(String prenomP_Fr) {
        this.prenomP_Fr = prenomP_Fr;
    }

    public String getNationaliteP_Ar() {
        return nationaliteP_Ar;
    }

    public void setNationaliteP_Ar(String nationaliteP_Ar) {
        this.nationaliteP_Ar = nationaliteP_Ar;
    }

    public String getNationaliteP_Fr() {
        return nationaliteP_Fr;
    }

    public void setNationaliteP_Fr(String nationaliteP_Fr) {
        this.nationaliteP_Fr = nationaliteP_Fr;
    }

    public String getPrenomM_Ar() {
        return prenomM_Ar;
    }

    public void setPrenomM_Ar(String prenomM_Ar) {
        this.prenomM_Ar = prenomM_Ar;
    }

    public String getPrenomM_Fr() {
        return prenomM_Fr;
    }

    public void setPrenomM_Fr(String prenomM_Fr) {
        this.prenomM_Fr = prenomM_Fr;
    }

    public String getNationaliteM_Ar() {
        return nationaliteM_Ar;
    }

    public void setNationaliteM_Ar(String nationaliteM_Ar) {
        this.nationaliteM_Ar = nationaliteM_Ar;
    }

    public String getNationaliteM_Fr() {
        return nationaliteM_Fr;
    }

    public void setNationaliteM_Fr(String nationaliteM_Fr) {
        this.nationaliteM_Fr = nationaliteM_Fr;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }
    
    

    public List<Donnees_Marginales_J_D> getDonnees_Marginaless() {
        return donnees_Marginaless;
    }

    public void setDonnees_Marginaless(List<Donnees_Marginales_J_D> donnees_Marginaless) {
        this.donnees_Marginaless = donnees_Marginaless;
    }
    
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

    public Registre_jugement_Deces getRegistre() {
        return registre;
    }

    public void setRegistre(Registre_jugement_Deces registre) {
        this.registre = registre;
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
