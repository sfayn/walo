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
            @UniqueConstraint(columnNames={"numCertificat"})
    )
public class Non_Inscrit implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer numCertificat;
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
    //info pere
    @Lob
    private String prenomP_Ar;
    private String prenomP_Fr;
    //info mere
    @Lob
    private String prenomM_Ar;
    private String prenomM_Fr;
    //
    @Lob
    private String addressePa_Ar;
    @Lob
    private String addressePa_Fr;
    @ManyToOne
    private Sex sex;
    @Lob
    private String donnee_Marginal;
    private Integer annee;
    private Integer numCahier;
    private Integer numActe;
    @Lob
    private String bureau_E_C;
    @Lob
    private String officier_E_C;

    public Integer getNumCahier() {
        return numCahier;
    }

    public void setNumCahier(Integer numCahier) {
        this.numCahier = numCahier;
    }

    public Integer getNumCertificat() {
        return numCertificat;
    }

    public void setNumCertificat(Integer numCertificat) {
        this.numCertificat = numCertificat;
    }

    public String getNom_Ar() throws UnsupportedEncodingException {
        return nom_Ar == null ? "" : URLDecoder.decode(nom_Ar, "UTF-8");
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

    public String getPrenom_Ar() throws UnsupportedEncodingException {
        return prenom_Ar == null ? "" : URLDecoder.decode(prenom_Ar, "UTF-8");
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

    public String getLieu_de_Naiss_Fr() {
        return lieu_de_Naiss_Fr;
    }

    public void setLieu_de_Naiss_Fr(String lieu_de_Naiss_Fr) {
        this.lieu_de_Naiss_Fr = lieu_de_Naiss_Fr;
    }

    public String getLieu_de_Naiss_Ar() throws UnsupportedEncodingException {
        return lieu_de_Naiss_Ar == null ? "" : URLDecoder.decode(lieu_de_Naiss_Ar, "UTF-8");
    }

    public void setLieu_de_Naiss_Ar(String lieu_de_Naiss_Ar) {
        this.lieu_de_Naiss_Ar = lieu_de_Naiss_Ar;
    }

    public boolean isNoMJ() {
        return noMJ;
    }

    public void setNoMJ(boolean noMJ) {
        this.noMJ = noMJ;
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

    public String getPrenomP_Ar() throws UnsupportedEncodingException {
        return prenomP_Ar == null ? "" : URLDecoder.decode(prenomP_Ar, "UTF-8");
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

    public String getPrenomM_Ar() throws UnsupportedEncodingException {
        return prenomM_Ar == null ? "" : URLDecoder.decode(prenomM_Ar, "UTF-8");
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

    public String getAddressePa_Ar() throws UnsupportedEncodingException {
        return addressePa_Ar == null ? "" : URLDecoder.decode(addressePa_Ar, "UTF-8");
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

    public String getDonnee_Marginal() throws UnsupportedEncodingException {
        return donnee_Marginal == null ? "" : URLDecoder.decode(donnee_Marginal, "UTF-8");
    }

    public void setDonnee_Marginal(String donnee_Marginal) {
        this.donnee_Marginal = donnee_Marginal;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public Integer getNumActe() {
        return numActe;
    }

    public void setNumActe(Integer numActe) {
        this.numActe = numActe;
    }

    public String getBureau_E_C() throws UnsupportedEncodingException {
        return bureau_E_C == null ? "" : URLDecoder.decode(bureau_E_C, "UTF-8");
    }

    public void setBureau_E_C(String bureau_E_C) {
        this.bureau_E_C = bureau_E_C;
    }

    public String getOfficier_E_C() throws UnsupportedEncodingException {
        return officier_E_C == null ? "" : URLDecoder.decode(officier_E_C, "UTF-8");
    }

    public void setOfficier_E_C(String officier_E_C) {
        this.officier_E_C = officier_E_C;
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
        if (!(object instanceof Non_Inscrit)) {
            return false;
        }
        Non_Inscrit other = (Non_Inscrit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.Non_Inscrit[ id=" + id + " ]";
    }
    
}
