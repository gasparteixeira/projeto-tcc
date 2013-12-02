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
public class ProdutoValorSubscriber implements StatementSubscriber{


   @ManagedProperty(value="#{indexController}")
   private IndexController index;
    
    @Override
    public String getStatement() {
  
        String query = "select * from Produto "
                + "match_recognize ( "
                + "       measures A as total "
                + "       pattern (A) "
                + "       define "
                + "             A as A.preco > 2000 ) ";
        return query;
    }
    
    
    public void update(Map<String, Produto> eventMap) {

            IndexController.showEventoProduto = true;

        }

}
