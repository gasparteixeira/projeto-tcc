/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.jsf;

import br.com.senac.entity.Produto;
import br.com.senac.event.subscriber.StatementSubscriber;

import java.util.Map;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gaspar
 */
@Component
public class ProdutoInsertSubscriber implements StatementSubscriber {
    
    @Override
    public String getStatement() {    
        String query = "insert into ProdutoStream select * from Produto.std:lastevent() as a";
        return query;
    }
    
    public void update(Map<String, Produto> eventMap) {

    }
    
    
    
}
