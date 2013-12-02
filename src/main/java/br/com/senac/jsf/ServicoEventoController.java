/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.jsf;

import br.com.senac.event.subscriber.StatementSubscriber;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import javax.ejb.ConcurrencyManagement;
import static javax.ejb.ConcurrencyManagementType.CONTAINER;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Gaspar
 */
@Singleton
@ConcurrencyManagement(CONTAINER)
@Stateless
public class ServicoEventoController {

    private static Logger LOG = LoggerFactory.getLogger(ServicoEventoController.class);
    private EPServiceProvider epService;
    private EPStatement produtoEventStatement;
    //produtos
    private ProdutoEventSubscriber produtoEventSubscriber = new ProdutoEventSubscriber();
    private ProdutoInsertSubscriber produtoInsertSubscriber = new ProdutoInsertSubscriber();
    private ProdutoValorSubscriber produtoValorSubscriber = new ProdutoValorSubscriber();
    private ProdutoEventMonitorSubscriber produtoEventMonitorSubscriber = new ProdutoEventMonitorSubscriber();
    //cartao - fraude
    private CartaoEventSubscriber cartaoEventSubscriber = new CartaoEventSubscriber();
    private CartaoInsertSubscriber cartaoInsertSubscriber = new CartaoInsertSubscriber();
    //Login - auxilio
    private LoginEventSubscriber loginEventSubscriber = new LoginEventSubscriber();
    private LoginInsertSubscriber loginInsertSubscriber = new LoginInsertSubscriber();
    private Boolean status = false;

    public ServicoEventoController() {
        Configuration config = new Configuration();
        config.addEventTypeAutoName("br.com.senac.entity");
        epService = EPServiceProviderManager.getDefaultProvider(config);
    }

    public void init() {
        createProdutoObserver();
    }

    public void createProdutoObserver() {

        //Eventos para monitorar produtos
        preparaConsulta(produtoInsertSubscriber);
        preparaConsulta(produtoEventSubscriber);
        preparaConsulta(produtoValorSubscriber);
        preparaConsulta(produtoEventMonitorSubscriber);

        // Eventos para fraude cartao
        preparaConsulta(cartaoInsertSubscriber);
        preparaConsulta(cartaoEventSubscriber);
        
        // Eventos para monitorar login
        preparaConsulta(loginInsertSubscriber);
        preparaConsulta(loginEventSubscriber);

    }

    public void preparaConsulta(StatementSubscriber stSub) {
        produtoEventStatement = epService.getEPAdministrator().createEPL(stSub.getStatement());
        produtoEventStatement.setSubscriber(stSub);
        LOG.debug("Criei statement com a class "+stSub.getClass().getSimpleName());
    }

    public EPServiceProvider getEpService() {
        return epService;
    }

    public void setEpService(EPServiceProvider epService) {
        this.epService = epService;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
