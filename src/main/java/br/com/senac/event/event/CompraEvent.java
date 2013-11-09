/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.event.event;

import java.util.Date;

/**
 *
 * @author Gaspar
 */
public class CompraEvent {
    
    private Integer idUsuario;
    
    private String nome;
    private String message;
    private Date dataTempo;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDataTempo() {
        return dataTempo;
    }

    public void setDataTempo(Date dataTempo) {
        this.dataTempo = dataTempo;
    }

    @Override
    public String toString() {
        return "Mensagem [" + message + ']';
    }
    
    
    
}
