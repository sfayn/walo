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
//SELECT YEAR(`DATETAH_G`),count(*),MONTH(`DATETAH_G`) FROM `acte_naissance` WHERE YEAR(`DATETAH_G`)>=1978 AND YEAR(`DATETAH_G`) <= 1992 GROUP BY YEAR(`DATETAH_G`), MONTH(`DATETAH_G`)
@NamedQueries({
    @NamedQuery(name="Acte_Naissance.countByMonth",
                query="SELECT COUNT(c) FROM Acte_Naissance c WHERE FUNC('YEAR',c.dateTah_G)=:year AND FUNC('MONTH',c.dateTah_G)=:month"),
    @NamedQuery(name="Acte_Naissance.countByYearsMonths",
                query="SELECT FUNC('YEAR',c.dateTah_G), COUNT(c), FUNC('MONTH',c.dateTah_G) FROM Acte_Naissance c WHERE FUNC('YEAR',c.dateTah_G)>=:year1 AND FUNC('YEAR',c.dateTah_G)<=:year2 GROUP BY FUNC('YEAR',c.dateTah_G), FUNC('MONTH',c.dateTah_G)"),
    @NamedQuery(name="Acte_Naissance.countBySex",
                query="SELECT COUNT(c),s.libelleFr FROM Acte_Naissance c, Sex s WHERE s = c.sex AND FUNC('YEAR',c.dateTah_G)=:year AND FUNC('MONTH',c.dateTah_G)=:month GROUP BY c.sex, FUNC('YEAR',c.dateTah_G), FUNC('MONTH',c.dateTah_G)"),
    @NamedQuery(name="Acte_Naissance.countByDateAndSex",
                query="SELECT COUNT(c),FUNC('MONTH',c.dateTah_G),s.libelleFr FROM Acte_Naissance c, Sex s WHERE s = c.sex AND FUNC('YEAR',c.dateTah_G)=:year GROUP BY c.sex, FUNC('YEAR',c.dateTah_G), FUNC('MONTH',c.dateTah_G) ORDER BY FUNC('MONTH',c.dateTah_G) ASC"),
    @NamedQuery(name="Acte_Naissance.countByDate",
                query="SELECT COUNT(c),FUNC('MONTH',c.dateTah_G) FROM Acte_Naissance c WHERE FUNC('YEAR',c.dateTah_G)=:year GROUP BY FUNC('YEAR',c.dateTah_G), FUNC('MONTH',c.dateTah_G) ORDER BY FUNC('MONTH',c.dateTah_G) ASC"),
    @NamedQuery(name="Acte_Naissance.countByTriMonths",
                query="SELECT COUNT(c) FROM Acte_Naissance c WHERE FUNC('YEAR',c.dateTah_G)=:year AND (FUNC('MONTH',c.dateTah_G)=:month1 OR FUNC('MONTH',c.dateTah_G)=:month2 OR FUNC('MONTH',c.dateTah_G)=:month3)"),
    @NamedQuery(name="Acte_Naissance.findByAnnee",
                query="SELECT c FROM Acte_Naissance c, Registre r WHERE c.registre=r AND c.numActe LIKE :acte AND r.annee LIKE :annee"),
    @NamedQuery(name="Acte_Naissance.countByAgeMere",
                query="SELECT COUNT(c),s.libelleFr FROM Acte_Naissance c, Sex s WHERE :year-FUNC('YEAR',c.date_de_naissM_G)<=:max AND :year-FUNC('YEAR',c.date_de_naissM_G)>=:min AND c.sex=s AND FUNC('YEAR',c.dateTah_G)=:year AND FUNC('MONTH',c.dateTah_G)=:month GROUP BY s.libelleFr"),
    @NamedQuery(name="Acte_Naissance.countByUser",
                query="SELECT COUNT(c) FROM Acte_Naissance c, User u WHERE c.createdBy=u AND u.id=:user")
})
public class Acte_Naissance implements Serializable {
    @OneToMany(mappedBy = "acte", targetEntity=Donnees_Marginales.class, fetch = FetchType.EAGER,orphanRemoval=true)
    private List<Donnees_Marginales> donnees_Marginaless;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer numActe;
    private boolean checked = false;
    //info citoyen
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
    private boolean smj=false;
    //info pere
    @Lob
    private String prenomP_Ar;
    private String prenomP_Fr;
    @Lob
    private String professionP_Ar;
    private String professionP_Fr;
    @Lob
    private String lieu_de_NaissP_Fr;
    @Lob
    private String lieu_de_NaissP_Ar;
    private String nationalteP_Fr="Marocaine";
    @Lob
    private String nationalteP_Ar="مغربية";
    private boolean noMJP=false;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date date_de_naissP_H;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date date_de_naissP_G;
    private boolean decesP=false;
    
    //info mere
    @Lob
    private String prenomM_Ar;
    private String prenomM_Fr;
    @Lob
    private String professionM_Ar;
    private String professionM_Fr;
    private String lieu_de_NaissM_Fr;
    @Lob
    private String lieu_de_NaissM_Ar;
    private String nationalteM_Fr="Marocaine";
    @Lob
    private String nationalteM_Ar="مغربية";
    private boolean noMJM=false;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date date_de_naissM_H;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date date_de_naissM_G;
    private boolean decesM=false;
    //info parent
    @Lob
    private String addressePa_Ar;
    @Lob
    private String addressePa_Fr;
    @ManyToOne
    private Sex sex;
    //info tassri7
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
    @ManyToOne
    private User createdBy;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date createdAt;
    @ManyToOne
    private Registre registre;
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
    
    
    

    public boolean isNoMJ() {
        return noMJ;
    }

    public void setNoMJ(boolean noMJ) {
        this.noMJ = noMJ;
    }

    public boolean isNoMJP() {
        return noMJP;
    }

    public void setNoMJP(boolean noMJP) {
        this.noMJP = noMJP;
    }

    public boolean isNoMJM() {
        return noMJM;
    }

    public void setNoMJM(boolean noMJM) {
        this.noMJM = noMJM;
    }

    public Acte_Naissance() {
        donnees_Marginaless=new ArrayList<Donnees_Marginales>();
    }
    
    

    public List<Donnees_Marginales> getDonnees_Marginaless() {
        return donnees_Marginaless;
    }

    public void setDonnees_Marginaless(List<Donnees_Marginales> donnees_Marginaless) {
        this.donnees_Marginaless = donnees_Marginaless;
    }

    public Registre getRegistre() {
        return registre;
    }

    public void setRegistre(Registre registre) {
        this.registre = registre;
    }

    public boolean isDecesP() {
        return decesP;
    }

    public void setDecesP(boolean decesP) {
        this.decesP = decesP;
    }

    public boolean isDecesM() {
        return decesM;
    }

    public void setDecesM(boolean decesM) {
        this.decesM = decesM;
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

    public String getLieu_de_Naiss_Ar() throws UnsupportedEncodingException {
        return lieu_de_Naiss_Ar == null ? "" : URLDecoder.decode(lieu_de_Naiss_Ar, "UTF-8");
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

    public String getLieu_de_NaissP_Fr() {
        return lieu_de_NaissP_Fr;
    }

    public void setLieu_de_NaissP_Fr(String lieu_de_NaissP_Fr) {
        this.lieu_de_NaissP_Fr = lieu_de_NaissP_Fr;
    }

    public String getLieu_de_NaissP_Ar() throws UnsupportedEncodingException {
        return lieu_de_NaissP_Ar == null ? "" : URLDecoder.decode(lieu_de_NaissP_Ar, "UTF-8");
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

    public String getNationalteP_Ar() throws UnsupportedEncodingException {
        return nationalteP_Ar == null ? "" : URLDecoder.decode(nationalteP_Ar, "UTF-8");
    }

    public void setNationalteP_Ar(String nationalteP_Ar) {
        this.nationalteP_Ar = nationalteP_Ar;
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

    public String getLieu_de_NaissM_Fr() {
        return lieu_de_NaissM_Fr;
    }

    public void setLieu_de_NaissM_Fr(String lieu_de_NaissM_Fr) {
        this.lieu_de_NaissM_Fr = lieu_de_NaissM_Fr;
    }

    public String getLieu_de_NaissM_Ar() throws UnsupportedEncodingException {
        return lieu_de_NaissM_Ar == null ? "" : URLDecoder.decode(lieu_de_NaissM_Ar, "UTF-8");
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

    public String getNationalteM_Ar() throws UnsupportedEncodingException {
        return nationalteM_Ar == null ? "" : URLDecoder.decode(nationalteM_Ar, "UTF-8");
    }

    public void setNationalteM_Ar(String nationalteM_Ar) {
        this.nationalteM_Ar = nationalteM_Ar;
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
