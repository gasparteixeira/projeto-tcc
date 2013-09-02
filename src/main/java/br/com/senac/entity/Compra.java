/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Gaspar
 */
@Entity
@Table(name = "compra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Compra.findAll", query = "SELECT c FROM Compra c"),
    @NamedQuery(name = "Compra.findById", query = "SELECT c FROM Compra c WHERE c.id = :id"),
    @NamedQuery(name = "Compra.findByValor", query = "SELECT c FROM Compra c WHERE c.valor = :valor"),
    @NamedQuery(name = "Compra.findByCartao", query = "SELECT c FROM Compra c WHERE c.cartao = :cartao"),
    @NamedQuery(name = "Compra.findByCodigo", query = "SELECT c FROM Compra c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "Compra.findByDatacadastro", query = "SELECT c FROM Compra c WHERE c.datacadastro = :datacadastro")})
public class Compra implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor", nullable = false, precision = 8, scale = 2)
    private BigDecimal valor;
    @Size(max = 60)
    @Column(name = "cartao", length = 60)
    private String cartao;
    @Size(max = 12)
    @Column(name = "codigo", length = 12)
    private String codigo;
    @Column(name = "datacadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datacadastro;
    @JoinColumn(name = "idformapagamento", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Formapagamento idformapagamento;
    @JoinColumn(name = "idendereco", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Endereco idendereco;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idcompra")
    private List<Carrinhocompra> carrinhocompraList;

    public Compra() {
    }

    public Compra(Integer id) {
        this.id = id;
    }

    public Compra(Integer id, BigDecimal valor) {
        this.id = id;
        this.valor = valor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getCartao() {
        return cartao;
    }

    public void setCartao(String cartao) {
        this.cartao = cartao;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getDatacadastro() {
        return datacadastro;
    }

    public void setDatacadastro(Date datacadastro) {
        this.datacadastro = datacadastro;
    }

    public Formapagamento getIdformapagamento() {
        return idformapagamento;
    }

    public void setIdformapagamento(Formapagamento idformapagamento) {
        this.idformapagamento = idformapagamento;
    }

    public Endereco getIdendereco() {
        return idendereco;
    }

    public void setIdendereco(Endereco idendereco) {
        this.idendereco = idendereco;
    }

    @XmlTransient
    public List<Carrinhocompra> getCarrinhocompraList() {
        return carrinhocompraList;
    }

    public void setCarrinhocompraList(List<Carrinhocompra> carrinhocompraList) {
        this.carrinhocompraList = carrinhocompraList;
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
        if (!(object instanceof Compra)) {
            return false;
        }
        Compra other = (Compra) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.senac.entity.Compra[ id=" + id + " ]";
    }
    
}
