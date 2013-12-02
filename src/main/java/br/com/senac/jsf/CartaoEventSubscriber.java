/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.jsf;

import br.com.senac.entity.Cartao;
import br.com.senac.event.subscriber.StatementSubscriber;
import java.util.Map;
import javax.faces.bean.ManagedProperty;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gaspar
 */
@Component
public class CartaoEventSubscriber implements StatementSubscriber{


   @ManagedProperty(value="#{fechamentoController}")
   private FechamentoController fechamento;
    
    @Override
    public String getStatement() {
  
        String query = "select d.numero " +
                       "from Cartao as c unidirectional, CartaoStream.win:time(10 min) as d " +
                       "where d.numero = c.numero having count(d.numero) >= 2 ";
        return query;
    }
    
    public void update(Map<String, Cartao> eventMap) {
            FechamentoController.viewCartao = true;

        }

}
