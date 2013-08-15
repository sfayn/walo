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
@Table( name = "jugement_naissance",
        uniqueConstraints =
@UniqueConstraint(columnNames = {"numActe", "registre_id"}))
@NamedQueries({
    @NamedQuery(name="Jugement_Naissance.countByMonth",
                query="SELECT COUNT(c) FROM Jugement_Naissance c WHERE FUNC('YEAR',c.dateJug)=:year AND FUNC('MONTH',c.dateJug)=:month"),
    @NamedQuery(name="Jugement_Naissance.findByAnnee",
                query="SELECT c FROM Jugement_Naissance c, Registre_jugement_Naissance r WHERE c.registre=r AND c.numActe LIKE :acte AND r.annee LIKE :annee"),
    @NamedQuery(name="Jugement_Naissance.countByTriMonths",
                query="SELECT COUNT(c) FROM Jugement_Naissance c WHERE FUNC('YEAR',c.dateJug)=:year AND (FUNC('MONTH',c.dateJug)=:month1 OR FUNC('MONTH',c.dateJug)=:month2 OR FUNC('MONTH',c.dateJug)=:month3)")
})
public class Jugement_Naissance implements Serializable {
    @OneToMany(mappedBy = "jugement", targetEntity=Donnees_Marginales_J_N.class, fetch = FetchType.EAGER,orphanRemoval=true)
    private List<Donnees_Marginales_J_N> donnees_Marginaless;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer numActe;
    private boolean checked = false;
    @ManyToOne
    private Registre_jugement_Naissance registre;
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
    //info pere
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
    @Lob
    private String tasrhAr;
    private String tasrhFr;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDeclaration;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDeclaration_H;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateJug;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateJug_H;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datesdistr;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datesdistr_H;
    @Lob
    private String descriptionAr;
    @Lob
    private String descriptionFr;
    @ManyToOne
    private User createdBy;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date createdAt;
    @Lob
    private String officierAr;
    private String officierFr;

    public String getOfficierAr() throws UnsupportedEncodingException {
        return officierAr == null ? "" : URLDecoder.decode(officierAr, "UTF-8");
    }

    public void setOfficierAr(String officierAr) {
        this.officierAr = officierAr;
    }

    public String getOfficierFr() {
        return officierFr;
    }

    public void setOfficierFr(String officierFr) {
        this.officierFr = officierFr;
    }
    

    public Date getDateDeclaration_H() {
        return dateDeclaration_H;
    }

    public void setDateDeclaration_H(Date dateDeclaration_H) {
        this.dateDeclaration_H = dateDeclaration_H;
    }

    public Date getDateJug_H() {
        return dateJug_H;
    }

    public void setDateJug_H(Date dateJug_H) {
        this.dateJug_H = dateJug_H;
    }

    public Date getDatesdistr_H() {
        return datesdistr_H;
    }

    public void setDatesdistr_H(Date datesdistr_H) {
        this.datesdistr_H = datesdistr_H;
    }
    
    
    public Jugement_Naissance() {
        donnees_Marginaless=new ArrayList<Donnees_Marginales_J_N>();
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

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }
  
    public List<Donnees_Marginales_J_N> getDonnees_Marginaless() {
        return donnees_Marginaless;
    }

    public void setDonnees_Marginaless(List<Donnees_Marginales_J_N> donnees_Marginaless) {
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

    public Registre_jugement_Naissance getRegistre() {
        return registre;
    }

    public void setRegistre(Registre_jugement_Naissance registre) {
        this.registre = registre;
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
