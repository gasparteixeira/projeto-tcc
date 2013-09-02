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
@Table(name = "formapagamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Formapagamento.findAll", query = "SELECT f FROM Formapagamento f"),
    @NamedQuery(name = "Formapagamento.findById", query = "SELECT f FROM Formapagamento f WHERE f.id = :id"),
    @NamedQuery(name = "Formapagamento.findByNome", query = "SELECT f FROM Formapagamento f WHERE f.nome = :nome"),
    @NamedQuery(name = "Formapagamento.findByParcela", query = "SELECT f FROM Formapagamento f WHERE f.parcela = :parcela")})
public class Formapagamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "nome", nullable = false, length = 60)
    private String nome;
    @Column(name = "parcela")
    private Integer parcela;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idformapagamento")
    private List<Compra> compraList;

    public Formapagamento() {
    }

    public Formapagamento(Integer id) {
        this.id = id;
    }

    public Formapagamento(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
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

    public Integer getParcela() {
        return parcela;
    }

    public void setParcela(Integer parcela) {
        this.parcela = parcela;
    }

    @XmlTransient
    public List<Compra> getCompraList() {
        return compraList;
    }

    public void setCompraList(List<Compra> compraList) {
        this.compraList = compraList;
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
        if (!(object instanceof Formapagamento)) {
            return false;
        }
        Formapagamento other = (Formapagamento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.senac.entity.Formapagamento[ id=" + id + " ]";
    }
    
}
