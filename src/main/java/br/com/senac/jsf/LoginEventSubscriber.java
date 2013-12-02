/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.jsf;

import br.com.senac.entity.LoginEvent;
import br.com.senac.event.subscriber.StatementSubscriber;
import java.util.Map;
import javax.faces.bean.ManagedProperty;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gaspar
 */
@Component
public class LoginEventSubscriber implements StatementSubscriber{


   @ManagedProperty(value="#{indexController}")
   private IndexController index;
    
    @Override
    public String getStatement() {

        String query = "select d.email, d.senha " +
                       "from LoginEvent as c unidirectional, LoginStream.win:time(10 min) as d  " +
                       "group by d.email having count(d.email) >= 2";
        return query;
    }
    
    public void update(Map<String, LoginEvent> eventMap) {
            IndexController.mensagemSenha = "<h3><i class='icon-info-sign'></i>Atenção:</h3> Você já tentou 3 vezes logar com o e-mail '<b>" + eventMap.get("d.email") + "</b>'.<br/>Provavelmente você esqueceu sua senha. <b>Habilitei</b> o link abaixo para a troca de senha.";
            IndexController.alertaUser = Boolean.TRUE;

        }

}
