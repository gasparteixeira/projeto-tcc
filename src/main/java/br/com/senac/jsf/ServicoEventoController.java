/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.jsf;

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
    private ProdutoEventSubscriber produtoEventSubscriber = new ProdutoEventSubscriber();
    private ProdutoInsertSubscriber produtoInsertSubscriber = new ProdutoInsertSubscriber();
    private Boolean status = false;
    
    public ServicoEventoController(){
        Configuration config = new Configuration();
        config.addEventTypeAutoName("br.com.senac.entity");
        epService = EPServiceProviderManager.getDefaultProvider(config);
        System.out.println("criei o servico...");
    }

    public void init(){
        createProdutoObserver();
    }
    
    public void createProdutoObserver(){
        // inserindo na janela
        produtoEventStatement = epService.getEPAdministrator().createEPL(produtoInsertSubscriber.getStatement());
        produtoEventStatement.setSubscriber(produtoInsertSubscriber);
        // lendo eventos
        produtoEventStatement = epService.getEPAdministrator().createEPL(produtoEventSubscriber.getStatement());
        produtoEventStatement.setSubscriber(produtoEventSubscriber);
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
