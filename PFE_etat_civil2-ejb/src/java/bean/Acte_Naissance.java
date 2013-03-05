/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.Date;
import javax.ejb.LocalBean;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Sfayn
 */
@Entity
@LocalBean
public class Acte_Naissance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer numActe;
    private boolean checked = false;
    //info citoyen
    private String nom_Ar;
    private String nom_Fr;
    private String prenom_Ar;
    private String prenom_Fr;
    private String profession_Ar;
    private String profession_Fr;
    private String lieu_de_Naiss_Fr;
    private String lieu_de_Naiss_Ar;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date date_de_naiss_H;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date date_de_naiss_G;
    //info pere
    private String nomP_Ar;
    private String nomP_Fr;
    private String prenomP_Ar;
    private String prenomP_Fr;
    private String professionP_Ar;
    private String professionP_Fr;
    private String lieu_de_NaissP_Fr;
    private String lieu_de_NaissP_Ar;
    private String nationalteP_Fr;
    private String nationalteP_Ar;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date date_de_naissP_H;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date date_de_naissP_G;
    //info mere
    private String nomM_Ar;
    private String nomM_Fr;
    private String prenomM_Ar;
    private String prenomM_Fr;
    private String professionM_Ar;
    private String professionM_Fr;
    private String lieu_de_NaissM_Fr;
    private String lieu_de_NaissM_Ar;
    private String nationalteM_Fr;
    private String nationalteM_Ar;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date date_de_naissM_H;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date date_de_naissM_G;
    //info parent
    private String addressePa_Ar;
    private String addressePa_Fr;
    @ManyToOne
    private Sex sex;
    //info tassri7
    private boolean typeT=true;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateTah_H;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateTah_G;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateHo=null;
    private String declaration_Ar;
    private String declaration_Fr;

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

    public String getDeclaration_Ar() {
        return declaration_Ar;
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

    public Date getDate_de_naissP_H() {
        return date_de_naissP_H;
    }

    public void setDate_de_naissP_H(Date date_de_naissP_H) {
        this.date_de_naissP_H = date_de_naissP_H;
    }

    public Date getDate_de_naissP_G() {
        return date_de_naissP_G;
    }

    public void setDate_de_naissP_G(Date date_de_naissP_G) {
        this.date_de_naissP_G = date_de_naissP_G;
    }

    public Date getDate_de_naissM_H() {
        return date_de_naissM_H;
    }

    public void setDate_de_naissM_H(Date date_de_naissM_H) {
        this.date_de_naissM_H = date_de_naissM_H;
    }

    public Date getDate_de_naissM_G() {
        return date_de_naissM_G;
    }

    public void setDate_de_naissM_G(Date date_de_naissM_G) {
        this.date_de_naissM_G = date_de_naissM_G;
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

    public String getNomP_Ar() {
        return nomP_Ar;
    }

    public void setNomP_Ar(String nomP_Ar) {
        this.nomP_Ar = nomP_Ar;
    }

    public String getNomP_Fr() {
        return nomP_Fr;
    }

    public void setNomP_Fr(String nomP_Fr) {
        this.nomP_Fr = nomP_Fr;
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

    public String getProfessionP_Ar() {
        return professionP_Ar;
    }

    public void setProfessionP_Ar(String professionP_Ar) {
        this.professionP_Ar = professionP_Ar;
    }

    public String getProfessionP_Fr() {
        return professionP_Fr;
    }

    public void setProfessionP_Fr(String professionP_Fr) {
        this.professionP_Fr = professionP_Fr;
    }

    public String getLieu_de_NaissP_Fr() {
        return lieu_de_NaissP_Fr;
    }

    public void setLieu_de_NaissP_Fr(String lieu_de_NaissP_Fr) {
        this.lieu_de_NaissP_Fr = lieu_de_NaissP_Fr;
    }

    public String getLieu_de_NaissP_Ar() {
        return lieu_de_NaissP_Ar;
    }

    public void setLieu_de_NaissP_Ar(String lieu_de_NaissP_Ar) {
        this.lieu_de_NaissP_Ar = lieu_de_NaissP_Ar;
    }

    public String getNationalteP_Fr() {
        return nationalteP_Fr;
    }

    public void setNationalteP_Fr(String nationalteP_Fr) {
        this.nationalteP_Fr = nationalteP_Fr;
    }

    public String getNationalteP_Ar() {
        return nationalteP_Ar;
    }

    public void setNationalteP_Ar(String nationalteP_Ar) {
        this.nationalteP_Ar = nationalteP_Ar;
    }

    public String getNomM_Ar() {
        return nomM_Ar;
    }

    public void setNomM_Ar(String nomM_Ar) {
        this.nomM_Ar = nomM_Ar;
    }

    public String getNomM_Fr() {
        return nomM_Fr;
    }

    public void setNomM_Fr(String nomM_Fr) {
        this.nomM_Fr = nomM_Fr;
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

    public String getProfessionM_Ar() {
        return professionM_Ar;
    }

    public void setProfessionM_Ar(String professionM_Ar) {
        this.professionM_Ar = professionM_Ar;
    }

    public String getProfessionM_Fr() {
        return professionM_Fr;
    }

    public void setProfessionM_Fr(String professionM_Fr) {
        this.professionM_Fr = professionM_Fr;
    }

    public String getLieu_de_NaissM_Fr() {
        return lieu_de_NaissM_Fr;
    }

    public void setLieu_de_NaissM_Fr(String lieu_de_NaissM_Fr) {
        this.lieu_de_NaissM_Fr = lieu_de_NaissM_Fr;
    }

    public String getLieu_de_NaissM_Ar() {
        return lieu_de_NaissM_Ar;
    }

    public void setLieu_de_NaissM_Ar(String lieu_de_NaissM_Ar) {
        this.lieu_de_NaissM_Ar = lieu_de_NaissM_Ar;
    }

    public String getNationalteM_Fr() {
        return nationalteM_Fr;
    }

    public void setNationalteM_Fr(String nationalteM_Fr) {
        this.nationalteM_Fr = nationalteM_Fr;
    }

    public String getNationalteM_Ar() {
        return nationalteM_Ar;
    }

    public void setNationalteM_Ar(String nationalteM_Ar) {
        this.nationalteM_Ar = nationalteM_Ar;
    }

    public String getAddressePa_Ar() {
        return addressePa_Ar;
    }

    public void setAddressePa_Ar(String addressePa_Ar) {
        this.addressePa_Ar = addressePa_Ar;
    }

    public String getAddressePa_Fr() {
        return addressePa_Fr;
    }

    public void setAddressePa_Fr(String addressePa_Fr) {
        this.addressePa_Fr = addressePa_Fr;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
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
        if (!(object instanceof Acte_Naissance)) {
            return false;
        }
        Acte_Naissance other = (Acte_Naissance) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.Acte_Naissance[ id=" + id + " ]";
    }
}
