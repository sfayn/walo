/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import util.Helper;

/**
 *
 * @author Sfayn
 */
@Entity
@Table( name="user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    private String prenom;
    private String login;
    private String password;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date lastLogin;
    @ManyToOne
    private Role role;
    @OneToMany(mappedBy = "createdBy", orphanRemoval=false)
    private List<Acte_Naissance> acte_Naissances;

    public List<Acte_Naissance> getActe_Naissances() {
        return acte_Naissances;
    }

    public void setActe_Naissances(List<Acte_Naissance> acte_Naissances) {
        this.acte_Naissances = acte_Naissances;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Hashage du Mot de passe par MD5
     */
    public void setPassword(String password) {
        if (password != null) {
            this.password = Helper.md5(password);
        }
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
        if (object == null) {
            return false;
        }
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if (this.login == null || this.password == null || other.login == null || other.password == null) {
            return false;
        }
        if (!this.login.equals(other.login) || !this.password.equals(other.password)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.User[ id=" + id + " ]";
    }
}