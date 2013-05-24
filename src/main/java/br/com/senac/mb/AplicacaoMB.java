/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.mb;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author Gaspar
 */
@ManagedBean
@ApplicationScoped
public class AplicacaoMB {
    
    @ManagedProperty(value = "#{pedidoMB}")
    private PedidoMB pedido;
    
    public void handle(String message){
        //pedido.showMessage(message);
        System.out.println("entrei em aplicacao.");
    }
    
}
