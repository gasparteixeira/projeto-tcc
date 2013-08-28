/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;

/**
 *
 * @author Gaspar
 */
@Entity
@Table(name = "produto")
@NamedQueries({
    @NamedQuery(name = "Produtos.listaProdutos", query = "SELECT u FROM Produto u"),
    @NamedQuery(name = "Produtos.countProdutosTotal", query = "SELECT COUNT(u) FROM Produto u")})
public class Produto implements Serializable {

    public final static String ALL = "Produtos.listaProdutos";
    public final static String TOTAL = "Produtos.countProdutosTotal";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false, name = "ID", columnDefinition = "SERIAL")
    private Integer id;
    @Column(nullable = false, length = 60)
    private String nome;
    @Column(nullable = false)
    private double preco;
    @Column(nullable = true)
    private double desconto;
    @ManyToOne
    @JoinColumn(name = "IDCATEGORIA")
    private Categoria idcategoria;
    @ManyToOne
    @JoinColumn(name = "IDMARCA")
    private Marca idmarca;
    @Column(nullable = false, length = 32)
    private String codigo;
    @Column(nullable = false)
    private String informacoes;
    @Column(nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datacadastro;

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

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public Categoria getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(Categoria idcategoria) {
        this.idcategoria = idcategoria;
    }

    public Marca getIdmarca() {
        return idmarca;
    }

    public void setIdmarca(Marca idmarca) {
        this.idmarca = idmarca;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getInformacoes() {
        return informacoes;
    }

    public void setInformacoes(String informacoes) {
        this.informacoes = informacoes;
    }

    public Date getDatacadastro() {
        return datacadastro;
    }

    public void setDatacadastro(Date datacadastro) {
        this.datacadastro = datacadastro;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Produto other = (Produto) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Produto{" + "id=" + id + '}';
    }
}
