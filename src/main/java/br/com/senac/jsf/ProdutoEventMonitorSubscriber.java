/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.jsf;

import br.com.senac.entity.Produto;
import br.com.senac.event.subscriber.StatementSubscriber;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.faces.bean.ManagedProperty;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gaspar
 */
@Component
public class ProdutoEventMonitorSubscriber implements StatementSubscriber {

    @ManagedProperty(value = "#{indexController}")
    private IndexController index;
    private static int contador = 0;

    @Override
    public String getStatement() {
        String query = "select * "
                + "from Produto.win:time(60 min) output every 1 events ";
        return query;
    }

    public void update(Map<String, Produto> eventMap) {
        if (contador % 2 == 0) {
            StringBuilder sb = new StringBuilder();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String classe = (eventMap.get("stream_0").getPreco().intValue() > 2000) ? "especial" : "normal";
            sb.append("<span class='");
            sb.append(classe);
            sb.append("'>");
            sb.append("Produto visualizado: ")
                    .append("NOME= ")
                    .append(eventMap.get("stream_0").getNome())
                    .append(", VALOR= ")
                    .append(eventMap.get("stream_0").getPreco())
                    .append(", DATA= ")
                    .append(dateFormat.format(date));
            sb.append("</span>");
            IndexController.monitorProduto.append(sb);
        }
        contador++;
    }
}
