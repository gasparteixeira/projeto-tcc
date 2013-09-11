package br.com.senac.event;


import br.com.senac.event.util.RandomPedidoEventGenerator;
import com.espertech.esper.client.EPStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class StartDemo {

    private static Logger LOG = LoggerFactory.getLogger(StartDemo.class);

    
  
    public static void main(String[] args) throws Exception {
        


//        LOG.debug("Iniciando...");
//
//        long noOfTemperatureEvents = 1000;
//
//        if (args.length != 1) {
//            LOG.debug("No override of number of events detected - defaulting to " + noOfTemperatureEvents + " events.");
//        } else {
//            noOfTemperatureEvents = Long.valueOf(args[0]);
//        }
//
//        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(new String[] { "application-context.xml" });
//        BeanFactory factory = (BeanFactory) appContext;
//
//        RandomPedidoEventGenerator generator = (RandomPedidoEventGenerator) factory.getBean("eventGenerator");
//        generator.startSendingTemperatureReadings(noOfTemperatureEvents);

    }

}
