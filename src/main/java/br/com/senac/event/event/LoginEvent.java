/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.event.event;

/**
 *
 * @author Gaspar
 */
public class LoginEvent {
    private String email;
    private String senha;
    
    public LoginEvent(String email, String senha){
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Login com e-mail [" + email + ']';
    }
    
    
    
}
