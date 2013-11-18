package br.com.senac.event.handler;

import br.com.senac.entity.PedidoEvent;
import br.com.senac.event.subscriber.StatementSubscriber;
import br.com.senac.mb.PedidoMB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import javax.faces.bean.ManagedProperty;


@Component
@Scope(value = "singleton")
public class PedidoEventHandler implements InitializingBean{

    private static Logger LOG = LoggerFactory.getLogger(PedidoEventHandler.class);

    private EPServiceProvider epService;
    private EPStatement criticalEventStatement;
    private EPStatement warningEventStatement;
    private EPStatement monitorEventStatement;
    @ManagedProperty(value="#{pedidoMB}")
    private PedidoMB pedido;

    @Autowired
    @Qualifier("criticalEventSubscriber")
    private StatementSubscriber criticalEventSubscriber;

    @Autowired
    @Qualifier("warningEventSubscriber")
    private StatementSubscriber warningEventSubscriber;

    @Autowired
    @Qualifier("monitorEventSubscriber")
    private StatementSubscriber monitorEventSubscriber;

    
    public void initService() {

        LOG.debug("Inicializando Serviço ..");
        Configuration config = new Configuration();
        config.addEventTypeAutoName("br.com.senac.entity");
        epService = EPServiceProviderManager.getDefaultProvider(config);

        createCriticalPedidoCheckExpression();
        createWarningPedidoCheckExpression();
        createPedidoMonitorExpression();

    }


    private void createCriticalPedidoCheckExpression() {
        
        LOG.debug("criando Pedido Crítico baseado na Expressão");
        criticalEventStatement = epService.getEPAdministrator().createEPL(criticalEventSubscriber.getStatement());
        criticalEventStatement.setSubscriber(criticalEventSubscriber);
    }

    
    private void createWarningPedidoCheckExpression() {

        LOG.debug("criando Pedido Alerta baseado na Expressão");
        warningEventStatement = epService.getEPAdministrator().createEPL(warningEventSubscriber.getStatement());
        warningEventStatement.setSubscriber(warningEventSubscriber);
    }

    private void createPedidoMonitorExpression() {

        LOG.debug("Monitorando pedidos por tempo de execução");
        monitorEventStatement = epService.getEPAdministrator().createEPL(monitorEventSubscriber.getStatement());
        monitorEventStatement.setSubscriber(monitorEventSubscriber);
    }

    public void handle(PedidoEvent event) {

        LOG.debug(event.toString());
        String evento = "<div class='evento'>"+event.toString()+"</div>";
        PedidoMB.sb.append(evento);
        epService.getEPRuntime().sendEvent(event);

    }

    @Override
    public void afterPropertiesSet() {
        
        LOG.debug("Configurando...");
        initService();
    }
}
