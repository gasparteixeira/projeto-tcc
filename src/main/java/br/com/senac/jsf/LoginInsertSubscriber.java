/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.jsf;

import br.com.senac.entity.LoginEvent;
import br.com.senac.event.subscriber.StatementSubscriber;

import java.util.Map;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gaspar
 */
@Component
public class LoginInsertSubscriber implements StatementSubscriber {
    
    @Override
    public String getStatement() {    
        String query = "insert into LoginStream select * from LoginEvent.std:lastevent() as a";
        return query;
    }
    
    public void update(Map<String, LoginEvent> eventMap) {
        
    }
    
    
    
}
