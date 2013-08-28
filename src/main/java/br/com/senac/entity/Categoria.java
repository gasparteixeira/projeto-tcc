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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Gaspar
 */
@Entity
@Table(name="categoria")
@NamedQueries({
    @NamedQuery(name = "Categorias.listaCategorias", query = "SELECT u FROM Categoria u"),
    @NamedQuery(name = "Categorias.showCategoria", query = "SELECT u FROM Categoria u WHERE u.id = :id"),
    @NamedQuery(name = "Categorias.countCategoriasTotal", query = "SELECT COUNT(u) FROM Categoria u")
})

public class Categoria implements Serializable{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable=false,name="ID", columnDefinition = "SERIAL")
    private Integer id;
    @Column(nullable = false, length = 60)
    private String nome;
    
  
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Categoria other = (Categoria) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Categoria{" + "id=" + id + '}';
    }
    
    
}
