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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Gaspar
 */
@Entity
@Table(name = "situacao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Situacao.findAll", query = "SELECT s FROM Situacao s"),
    @NamedQuery(name = "Situacao.findById", query = "SELECT s FROM Situacao s WHERE s.id = :id"),
    @NamedQuery(name = "Situacao.findByIdcompra", query = "SELECT s FROM Situacao s WHERE s.idcompra = :idcompra"),
    @NamedQuery(name = "Situacao.findByRetrono", query = "SELECT s FROM Situacao s WHERE s.retrono = :retrono"),
    @NamedQuery(name = "Situacao.findByStatus", query = "SELECT s FROM Situacao s WHERE s.status = :status"),
    @NamedQuery(name = "Situacao.findBySituacao", query = "SELECT s FROM Situacao s WHERE s.situacao = :situacao"),
    @NamedQuery(name = "Situacao.findByDatacadastro", query = "SELECT s FROM Situacao s WHERE s.datacadastro = :datacadastro")})
public class Situacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idcompra", nullable = false)
    private int idcompra;
    @Column(name = "retrono")
    private Boolean retrono;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "status", nullable = false, length = 255)
    private String status;
    @Column(name = "situacao")
    private Integer situacao;
    @Column(name = "datacadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datacadastro;

    public Situacao() {
    }

    public Situacao(Integer id) {
        this.id = id;
    }

    public Situacao(Integer id, int idcompra, String status) {
        this.id = id;
        this.idcompra = idcompra;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdcompra() {
        return idcompra;
    }

    public void setIdcompra(int idcompra) {
        this.idcompra = idcompra;
    }

    public Boolean getRetrono() {
        return retrono;
    }

    public void setRetrono(Boolean retrono) {
        this.retrono = retrono;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSituacao() {
        return situacao;
    }

    public void setSituacao(Integer situacao) {
        this.situacao = situacao;
    }

    public Date getDatacadastro() {
        return datacadastro;
    }

    public void setDatacadastro(Date datacadastro) {
        this.datacadastro = datacadastro;
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
        if (!(object instanceof Situacao)) {
            return false;
        }
        Situacao other = (Situacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.senac.entity.Situacao[ id=" + id + " ]";
    }
    
}
