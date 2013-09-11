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
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Gaspar
 */
@Entity
@Table(name = "carrinho")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Carrinho.findAll", query = "SELECT c FROM Carrinho c"),
    @NamedQuery(name = "Carrinho.findById", query = "SELECT c FROM Carrinho c WHERE c.id = :id"),
    @NamedQuery(name = "Carrinho.findByUserId", query = "SELECT c FROM Carrinho c WHERE c.idusuario = :idusuario"),
    @NamedQuery(name = "Carrinho.findByQuantidade", query = "SELECT c FROM Carrinho c WHERE c.quantidade = :quantidade"),
    @NamedQuery(name = "Carrinho.findByValor", query = "SELECT c FROM Carrinho c WHERE c.valor = :valor"),
    @NamedQuery(name = "Carrinho.findByDatacadastro", query = "SELECT c FROM Carrinho c WHERE c.datacadastro = :datacadastro"),
    @NamedQuery(name = "Carrinho.findByDatafechamento", query = "SELECT c FROM Carrinho c WHERE c.datafechamento = :datafechamento")})
public class Carrinho implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantidade", nullable = false)
    private int quantidade;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor", nullable = false, precision = 8, scale = 2)
    private BigDecimal valor;
    @Column(name = "datacadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datacadastro;
    @Column(name = "datafechamento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datafechamento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idcarrinho")
    private List<Carrinhocompra> carrinhocompraList;
    @JoinColumn(name = "idusuario", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Usuario idusuario;
    @JoinColumn(name = "idproduto", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Produto idproduto;
    
    @PrePersist
    protected void onCreate() {
        datacadastro = new Date();
    }

    public Carrinho() {
    }

    public Carrinho(Integer id) {
        this.id = id;
    }

    public Carrinho(Integer id, int quantidade, BigDecimal valor) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getDatacadastro() {
        return datacadastro;
    }

    public void setDatacadastro(Date datacadastro) {
        this.datacadastro = datacadastro;
    }

    public Date getDatafechamento() {
        return datafechamento;
    }

    public void setDatafechamento(Date datafechamento) {
        this.datafechamento = datafechamento;
    }

    @XmlTransient
    public List<Carrinhocompra> getCarrinhocompraList() {
        return carrinhocompraList;
    }

    public void setCarrinhocompraList(List<Carrinhocompra> carrinhocompraList) {
        this.carrinhocompraList = carrinhocompraList;
    }

    public Usuario getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Usuario idusuario) {
        this.idusuario = idusuario;
    }

    public Produto getIdproduto() {
        return idproduto;
    }

    public void setIdproduto(Produto idproduto) {
        this.idproduto = idproduto;
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
        if (!(object instanceof Carrinho)) {
            return false;
        }
        Carrinho other = (Carrinho) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.senac.entity.Carrinho[ id=" + id + " ]";
    }
    
}
