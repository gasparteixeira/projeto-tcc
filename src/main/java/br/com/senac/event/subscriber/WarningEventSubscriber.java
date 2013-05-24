package br.com.senac.event.subscriber;

import br.com.senac.event.event.PedidoEvent;
import br.com.senac.mb.PedidoMB;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



@Component
public class WarningEventSubscriber implements StatementSubscriber {

    private static Logger LOG = LoggerFactory.getLogger(WarningEventSubscriber.class);

    private static final String WARNING_EVENT_THRESHOLD = "8000";

    
    public String getStatement() {
        
        /* Se dois pedidos estiverem acima de 8 mil consecuitivamente, alertar */
        String warningEventExpression = "select * from PedidoEvent "
                + "match_recognize ( "
                + "       measures A as total1, B as total2 "
                + "       pattern (A B) " 
                + "       define " 
                + "               A as A.valor > " + WARNING_EVENT_THRESHOLD + ", "
                + "               B as B.valor > " + WARNING_EVENT_THRESHOLD + ")";
        
        return warningEventExpression;
    }
    
    public void update(Map<String, PedidoEvent> eventMap) {

        PedidoEvent total1 = (PedidoEvent) eventMap.get("total1");
        PedidoEvent total2 = (PedidoEvent) eventMap.get("total2");

        StringBuilder sb = new StringBuilder();
        sb.append("<div class='atencao'>");
        sb.append("\n- [ATENÇÃO] : PEDIDOS ALTOS EM SEQUENCIA = " + total1 + ", " + total2);
        sb.append("</div>");
        
        PedidoMB.sb.append( sb.toString());

        LOG.debug(sb.toString());
    }
}
