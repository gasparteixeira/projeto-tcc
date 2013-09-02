/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Gaspar
 */
@Entity
@Table(name = "tipotelefone")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipotelefone.findAll", query = "SELECT t FROM Tipotelefone t"),
    @NamedQuery(name = "Tipotelefone.findById", query = "SELECT t FROM Tipotelefone t WHERE t.id = :id"),
    @NamedQuery(name = "Tipotelefone.findByNome", query = "SELECT t FROM Tipotelefone t WHERE t.nome = :nome"),
    @NamedQuery(name = "Tipotelefone.findBySigla", query = "SELECT t FROM Tipotelefone t WHERE t.sigla = :sigla")})
public class Tipotelefone implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nome", nullable = false, length = 45)
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sigla", nullable = false)
    private char sigla;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idtipotelefone")
    private List<Telefone> telefoneList;

    public Tipotelefone() {
    }

    public Tipotelefone(Integer id) {
        this.id = id;
    }

    public Tipotelefone(Integer id, String nome, char sigla) {
        this.id = id;
        this.nome = nome;
        this.sigla = sigla;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public char getSigla() {
        return sigla;
    }

    public void setSigla(char sigla) {
        this.sigla = sigla;
    }

    @XmlTransient
    public List<Telefone> getTelefoneList() {
        return telefoneList;
    }

    public void setTelefoneList(List<Telefone> telefoneList) {
        this.telefoneList = telefoneList;
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
        if (!(object instanceof Tipotelefone)) {
            return false;
        }
        Tipotelefone other = (Tipotelefone) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.senac.entity.Tipotelefone[ id=" + id + " ]";
    }
    
}
