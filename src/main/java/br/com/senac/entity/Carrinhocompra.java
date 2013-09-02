/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Gaspar
 */
@Entity
@Table(name = "carrinhocompra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Carrinhocompra.findAll", query = "SELECT c FROM Carrinhocompra c"),
    @NamedQuery(name = "Carrinhocompra.findById", query = "SELECT c FROM Carrinhocompra c WHERE c.id = :id")})
public class Carrinhocompra implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @JoinColumn(name = "idcompra", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Compra idcompra;
    @JoinColumn(name = "idcarrinho", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Carrinho idcarrinho;

    public Carrinhocompra() {
    }

    public Carrinhocompra(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Compra getIdcompra() {
        return idcompra;
    }

    public void setIdcompra(Compra idcompra) {
        this.idcompra = idcompra;
    }

    public Carrinho getIdcarrinho() {
        return idcarrinho;
    }

    public void setIdcarrinho(Carrinho idcarrinho) {
        this.idcarrinho = idcarrinho;
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
        if (!(object instanceof Carrinhocompra)) {
            return false;
        }
        Carrinhocompra other = (Carrinhocompra) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.senac.entity.Carrinhocompra[ id=" + id + " ]";
    }
    
}
