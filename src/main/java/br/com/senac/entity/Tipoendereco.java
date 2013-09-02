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
@Table(name = "tipoendereco")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipoendereco.findAll", query = "SELECT t FROM Tipoendereco t"),
    @NamedQuery(name = "Tipoendereco.findById", query = "SELECT t FROM Tipoendereco t WHERE t.id = :id"),
    @NamedQuery(name = "Tipoendereco.findByNome", query = "SELECT t FROM Tipoendereco t WHERE t.nome = :nome"),
    @NamedQuery(name = "Tipoendereco.findBySigla", query = "SELECT t FROM Tipoendereco t WHERE t.sigla = :sigla")})
public class Tipoendereco implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "nome", nullable = false, length = 32)
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "sigla", nullable = false, length = 4)
    private String sigla;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idtipoendereco")
    private List<Endereco> enderecoList;

    public Tipoendereco() {
    }

    public Tipoendereco(Integer id) {
        this.id = id;
    }

    public Tipoendereco(Integer id, String nome, String sigla) {
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

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    @XmlTransient
    public List<Endereco> getEnderecoList() {
        return enderecoList;
    }

    public void setEnderecoList(List<Endereco> enderecoList) {
        this.enderecoList = enderecoList;
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
        if (!(object instanceof Tipoendereco)) {
            return false;
        }
        Tipoendereco other = (Tipoendereco) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.senac.entity.Tipoendereco[ id=" + id + " ]";
    }
    
}
