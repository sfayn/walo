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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@NamedQueries({
    @NamedQuery(name="Acte_Deces.countByTriMonths",
                query="SELECT COUNT(c) FROM Acte_Deces c WHERE FUNC('YEAR',c.dateTah_G)=:year AND (FUNC('MONTH',c.dateTah_G)=:month1 OR FUNC('MONTH',c.dateTah_G)=:month2 OR FUNC('MONTH',c.dateTah_G)=:month3)"),
    @NamedQuery(name="Acte_Deces.countBySex",
                query="SELECT COUNT(c),s.libelleFr FROM Acte_Deces c, Sex s WHERE s = c.sex AND FUNC('YEAR',c.dateDecesG)=:year AND FUNC('MONTH',c.dateDecesG)=:month GROUP BY c.sex"),
    @NamedQuery(name="Acte_Deces.countByDate",
                query="SELECT COUNT(c),FUNC('MONTH',c.dateTah_G) FROM Acte_Deces c WHERE FUNC('YEAR',c.dateTah_G)=:year GROUP BY FUNC('YEAR',c.dateTah_G), FUNC('MONTH',c.dateTah_G) ORDER BY FUNC('MONTH',c.dateTah_G) ASC"),
    @NamedQuery(name="Acte_Deces.countByMonth",
                query="SELECT COUNT(c) FROM Acte_Deces c WHERE FUNC('YEAR',c.dateTah_G)=:year AND FUNC('MONTH',c.dateTah_G)=:month"),
    @NamedQuery(name="Acte_Deces.countByAge",
                query="SELECT COUNT(c), s.libelleFr FROM Acte_Deces c, Sex s WHERE :year-FUNC('YEAR',c.date_de_naiss_G)<=:max AND :year-FUNC('YEAR',c.date_de_naiss_G)>=:min AND FUNC('YEAR',c.dateDecesG)=:year AND FUNC('MONTH',c.dateDecesG)=:month AND c.sex=s GROUP BY c.sex"),
})
public class Acte_Deces implements Serializable {
    @OneToMany(mappedBy = "acte", targetEntity=Donnees_Marginales_A_D.class, fetch = FetchType.EAGER,orphanRemoval=true)
    private List<Donnees_Marginales_A_D> donnees_Marginaless;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer numActe;
    @ManyToOne
    private Registre_Deces registre;
    @Lob
    private String nom_Ar;
    private String nom_Fr;
    @ManyToOne
    private Sex sex;
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
    //pere
    @Lob
    private String prenomP_Ar;
    private String prenomP_Fr;
    @Lob
    private String professionP_Ar;
    private String professionP_Fr;
    @Lob
    private String adresseP_Fr;
    @Lob
    private String adresseP_Ar;
    private String nationaliteP_Fr="Marocaine";
    @Lob
    private String nationaliteP_Ar="مغربية";
    //mere
    private String prenomM_Ar;
    private String prenomM_Fr;
    @Lob
    private String professionM_Ar;
    private String professionM_Fr;
    @Lob
    private String adresseM_Fr;
    @Lob
    private String adresseM_Ar;
    private String nationaliteM_Fr="Marocaine";
    @Lob
    private String nationaliteM_Ar="مغربية";
    
    
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
    private boolean checked = false;
    @ManyToOne
    private User createdBy;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date createdAt;
    @Lob
    private String officierAr;
    private String officierFr;

    public String getOfficierFr() {
        return officierFr;
    }

    public void setOfficierFr(String officierFr) {
        this.officierFr = officierFr;
    }

    public String getOfficierAr() throws UnsupportedEncodingException {
        return officierAr == null ? "" : URLDecoder.decode(officierAr, "UTF-8");
    }

    public void setOfficierAr(String officierAr) {
        this.officierAr = officierAr;
    }


    public void setNationaliteP_Ar(String nationaliteP_Ar) {
        this.nationaliteP_Ar = nationaliteP_Ar;
    }

    public void setNationaliteM_Fr(String nationaliteM_Fr) {
        this.nationaliteM_Fr = nationaliteM_Fr;
    }

    public void setNationaliteM_Ar(String nationaliteM_Ar) {
        this.nationaliteM_Ar = nationaliteM_Ar;
    }

    public Acte_Deces() {
        donnees_Marginaless=new ArrayList<Donnees_Marginales_A_D>();
    }

    
    
    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public List<Donnees_Marginales_A_D> getDonnees_Marginaless() {
        return donnees_Marginaless;
    }

    public void setDonnees_Marginaless(List<Donnees_Marginales_A_D> donnees_Marginaless) {
        this.donnees_Marginaless = donnees_Marginaless;
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

    public String getProfessionP_Ar() throws UnsupportedEncodingException {
        return professionP_Ar == null ? "" : URLDecoder.decode(professionP_Ar, "UTF-8");
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

    public String getAdresseP_Fr() {
        return adresseP_Fr;
    }

    public void setAdresseP_Fr(String adresseP_Fr) {
        this.adresseP_Fr = adresseP_Fr;
    }

    public String getAdresseP_Ar() throws UnsupportedEncodingException {
       return adresseP_Ar == null ? "" : URLDecoder.decode(adresseP_Ar, "UTF-8");        
    }

    public void setAdresseP_Ar(String adresseP_Ar) {
        this.adresseP_Ar = adresseP_Ar;
    }

    public String getNationaliteP_Fr() {
        return nationaliteP_Fr;
    }

    public void setNationaliteP_Fr(String nationalteP_Fr) {
        this.nationaliteP_Fr = nationalteP_Fr;
    }

    public String getNationaliteP_Ar() throws UnsupportedEncodingException {
        return nationaliteP_Ar == null ? "" : URLDecoder.decode(nationaliteP_Ar, "UTF-8");        
    }

    public void setNationalteP_Ar(String nationalteP_Ar) {
        this.nationaliteP_Ar = nationalteP_Ar;
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

    public String getProfessionM_Ar() throws UnsupportedEncodingException {
        return professionM_Ar == null ? "" : URLDecoder.decode(professionM_Ar, "UTF-8");        
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

    public String getAdresseM_Fr() {
        return adresseM_Fr;
    }

    public void setAdresseM_Fr(String adresseM_Fr) {
        this.adresseM_Fr = adresseM_Fr;
    }

    public String getAdresseM_Ar() throws UnsupportedEncodingException {
        return adresseM_Ar == null ? "" : URLDecoder.decode(adresseM_Ar, "UTF-8");        
    }

    public void setAdresseM_Ar(String adresseM_Ar) {
        this.adresseM_Ar = adresseM_Ar;
    }

    public String getNationaliteM_Fr() {
        return nationaliteM_Fr;
    }

    public void setNationalteM_Fr(String nationalteM_Fr) {
        this.nationaliteM_Fr = nationalteM_Fr;
    }

    public String getNationaliteM_Ar() throws UnsupportedEncodingException {
        return nationaliteM_Ar == null ? "" : URLDecoder.decode(nationaliteM_Ar, "UTF-8");        
    }

    public void setNationalteM_Ar(String nationalteM_Ar) {
        this.nationaliteM_Ar = nationalteM_Ar;
    }

    
    
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    
    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    
    public boolean isNoMJD() {
        return noMJD;
    }

    public void setNoMJD(boolean noMJD) {
        this.noMJD = noMJD;
    }

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

    public Date getDateDecesH() {
        return dateDecesH;
    }

    public void setDateDecesH(Date dateDeces) {
        this.dateDecesH = dateDeces;
    }
    public Date getDateDecesG() {
        return dateDecesG;
    }

    public void setDateDecesG(Date dateDeces) {
        this.dateDecesG = dateDeces;
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
