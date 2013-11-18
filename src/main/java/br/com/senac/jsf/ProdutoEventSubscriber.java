/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.jsf;

import br.com.senac.entity.Produto;
import br.com.senac.event.subscriber.StatementSubscriber;
import java.util.Map;
import javax.faces.bean.ManagedProperty;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gaspar
 */
@Component
public class ProdutoEventSubscriber implements StatementSubscriber{


   @ManagedProperty(value="#{indexController}")
   private IndexController index;
    
    @Override
    public String getStatement() {
  
        String query = "select d.id, d.nome " +
                       "from Produto as c unidirectional, ProdutoStream.win:time(10 min) as d " +
                       "where d.id = c.id";
        return query;
    }
    
    public void update(Map<String, Produto> eventMap) {

            IndexController.viewProduto = true;

        }

}
