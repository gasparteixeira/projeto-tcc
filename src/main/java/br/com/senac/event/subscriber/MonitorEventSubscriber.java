package br.com.senac.event.subscriber;

import br.com.senac.mb.PedidoMB;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class MonitorEventSubscriber implements StatementSubscriber {

    private static Logger LOG = LoggerFactory.getLogger(MonitorEventSubscriber.class);


    public String getStatement() {

        // simples monitoramento de EPL com tempo de amostra
        return "select avg(valor) as avg_val from PedidoEvent.win:time_batch(5 sec)";
    }

    public void update(Map<String, Double> eventMap) {

        Double avg = (Double) eventMap.get("avg_val");

        StringBuilder sb = new StringBuilder();
        sb.append("<div class='normal'>");
        sb.append("\n- [MONITOR] VALOR NORMAL =  R$ " + Math.round(avg*100.0)/100.0);
        sb.append("</div>");

        PedidoMB.sb.append( sb.toString());
        LOG.debug(sb.toString());
    }
}
