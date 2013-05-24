/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.mb;

import br.com.senac.event.util.RandomPedidoEventGenerator;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author Gaspar
 */
@ManagedBean
@ViewScoped
public class PedidoMB  implements Serializable{
    private static Logger LOG = LoggerFactory.getLogger(PedidoMB.class);
    private String message ;
    public static String debugs;
    public static StringBuilder sb = new StringBuilder();
    public static RandomPedidoEventGenerator generator;


    
    
    public String startEvent(){
        debugs = "iniciando...";
        sb.append(debugs);

        long maxNumberEvents = 1000;

        
        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(new String[] { "application-context.xml" });
        BeanFactory factory = (BeanFactory) appContext;

        generator = (RandomPedidoEventGenerator) factory.getBean("eventGenerator");
        generator.startSendingPedidoReadings(maxNumberEvents);
        
        return null;
    }
    
    public String stopEvent(){
        generator.startSendingPedidoReadings(0L);
        return null;
    }
    
    public void showMessage(){
      this.setMessage(sb.toString());
        RequestContext.getCurrentInstance().update("messages");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    
    
}
