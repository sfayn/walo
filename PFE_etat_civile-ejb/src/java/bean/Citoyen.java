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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author Sfayn
 */
@Entity
public class Citoyen implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nomAr;
    private String nomFr;
    private String prenomAr;
    private String prenomFr;
    private String professionAr;
    private String professionFr;
    private Integer numActe;    
    private String addresseAr;
    private String addresseFr;
    @ManyToOne
    private Ville ville;
    @ManyToOne
    private Citoyen pere;
    @ManyToOne
    private Citoyen mere;
    @ManyToOne
    private Nationalite nationalite;   
    @ManyToOne
    private Sex sex;
    @OneToOne
    private Date date;

    public String getNomAr() {
        return nomAr;
    }

    public void setNomAr(String nomAr) {
        this.nomAr = nomAr;
    }

    public String getNomFr() {
        return nomFr;
    }

    public void setNomFr(String nomFr) {
        this.nomFr = nomFr;
    }

    public String getPrenomAr() {
        return prenomAr;
    }

    public void setPrenomAr(String prenomAr) {
        this.prenomAr = prenomAr;
    }

    public String getPrenomFr() {
        return prenomFr;
    }

    public void setPrenomFr(String prenomFr) {
        this.prenomFr = prenomFr;
    }

    public String getProfessionAr() {
        return professionAr;
    }

    public void setProfessionAr(String professionAr) {
        this.professionAr = professionAr;
    }

    public String getProfessionFr() {
        return professionFr;
    }

    public void setProfessionFr(String professionFr) {
        this.professionFr = professionFr;
    }

    public Integer getNumActe() {
        return numActe;
    }

    public void setNumActe(Integer numActe) {
        this.numActe = numActe;
    }

    public String getAddresseAr() {
        return addresseAr;
    }

    public void setAddresseAr(String addresseAr) {
        this.addresseAr = addresseAr;
    }

    public String getAddresseFr() {
        return addresseFr;
    }

    public void setAddresseFr(String addresseFr) {
        this.addresseFr = addresseFr;
    }

    public Ville getVille() {
        return ville;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }

    public Citoyen getPere() {
        return pere;
    }

    public void setPere(Citoyen pere) {
        this.pere = pere;
    }

    public Citoyen getMere() {
        return mere;
    }

    public void setMere(Citoyen mere) {
        this.mere = mere;
    }

    public Nationalite getNationalite() {
        return nationalite;
    }

    public void setNationalite(Nationalite nationalite) {
        this.nationalite = nationalite;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
        if (!(object instanceof Citoyen)) {
            return false;
        }
        Citoyen other = (Citoyen) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.Citoyen[ id=" + id + " ]";
    }
    
}
