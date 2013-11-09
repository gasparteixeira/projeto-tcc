/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.event.handler;

import br.com.senac.event.subscriber.StatementSubscriber;
import br.com.senac.mb.PedidoMB;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import javax.faces.bean.ManagedProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gaspar
 */
@Component
@Scope(value = "singleton")
public class CompraEventHandler implements InitializingBean{
    
    private static Logger LOG = LoggerFactory.getLogger(CompraEventHandler.class);
    
    private EPServiceProvider epService;
    private EPStatement monitorEventStatement;
    
    @ManagedProperty(value="#{pedidoMB}")
    private PedidoMB pedido;
    
    
    @Autowired
    @Qualifier("monitorEventSubscriber")
    private StatementSubscriber monitorEventSubscriber;
    
    public void initService() {
        LOG.debug("Inicializando Serviço ..");
        Configuration config = new Configuration();
        config.addEventTypeAutoName("br.com.senac.event.event");
        epService = EPServiceProviderManager.getDefaultProvider(config);
        
        createPedidoMonitorExpression();
    }

    public PedidoMB getPedido() {
        return pedido;
    }

    public void setPedido(PedidoMB pedido) {
        this.pedido = pedido;
    }
    
    private void createPedidoMonitorExpression() {
        
        LOG.debug("Monitorando pedidos por tempo de execução");
        monitorEventStatement = epService.getEPAdministrator().createEPL(monitorEventSubscriber.getStatement());
        monitorEventStatement.setSubscriber(monitorEventSubscriber);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    

    
}
