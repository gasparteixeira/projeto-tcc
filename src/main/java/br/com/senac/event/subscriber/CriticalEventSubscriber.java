package br.com.senac.event.subscriber;

import br.com.senac.event.event.PedidoEvent;
import br.com.senac.mb.PedidoMB;
import java.util.Map;
import javax.faces.bean.ManagedProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class CriticalEventSubscriber implements StatementSubscriber {

    private static Logger LOG = LoggerFactory.getLogger(CriticalEventSubscriber.class);

    private static final String CRITICAL_EVENT_THRESHOLD = "10000";


    @ManagedProperty(value="#{pedidoMB}")
    public PedidoMB pedido;
    
    public String getStatement() {
        
        String crtiticalEventExpression = "select * from PedidoEvent "
                + "match_recognize ( "
                + "       measures A as total "
                + "       pattern (A) "
                + "       define "
                + "             A as A.valor > " + CRITICAL_EVENT_THRESHOLD + ") ";
        
        return crtiticalEventExpression;
    }
    
    /**
     * Listener metodo chamado quando Esper detectou um evento critico.
     */
    public void update(Map<String, PedidoEvent> eventMap) {

        PedidoEvent total = (PedidoEvent) eventMap.get("total");


        StringBuilder sb = new StringBuilder();
        sb.append("<div class='alerta'>");
        sb.append("\n* [ALERTA] : PEDIDO DE ALTO VALOR = " + total);
        sb.append("</div>");

        PedidoMB.sb.append( sb.toString());
        
        LOG.debug(sb.toString());
       
        
    }


    
    
}
