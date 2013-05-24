/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.mb;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Gaspar
 */
@ManagedBean
@RequestScoped
public class IndexController implements Serializable {
    private String nome = "Gaspar";
    private String categoria;
    
    public String inicial(){
        
        
        return "usuario";
    }
    
    public void executeAcao(){
        System.out.println("acao executada");
    }
    
    public String getNome(){
        return this.nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
}
