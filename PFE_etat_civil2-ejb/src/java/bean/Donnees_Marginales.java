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

/**
 *
 * @author Sfayn
 */
@Entity
@Table( name = "donnees_marginales")
public class Donnees_Marginales implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;    
    @ManyToOne
    private Type_Donnees_Marginales type;
    private String attrValues="";

    public String getAttrValues() throws UnsupportedEncodingException {
        return attrValues == null ? "" : URLDecoder.decode(attrValues, "UTF-8");
    }

    public void setAttrValues(String attrValues) {
        this.attrValues = attrValues;
    }
    
    @ManyToOne
    private Acte_Naissance acte;
    @Lob
    private String descAr;
    @Lob
    private String descFr;

    public Date getDate() {
        return date;
    }

    public Acte_Naissance getActe() {
        return acte;
    }

    public void setActe(Acte_Naissance acte) {
        this.acte = acte;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Type_Donnees_Marginales getType() {
        return type;
    }

    public void setType(Type_Donnees_Marginales type) {
        this.type = type;
    }

    public String getDescAr() throws UnsupportedEncodingException {
        return descAr == null ? "" : URLDecoder.decode(descAr, "UTF-8");
    }

    public void setDescAr(String descAr) {
        this.descAr = descAr;
    }

    public String getDescFr() {
        return descFr;
    }

    public void setDescFr(String descFr) {
        this.descFr = descFr;
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
    public String toString() {
        return "bean.Donnees_Marginales[ id=" + id + " ]";
    }
    
}
