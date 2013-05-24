package br.com.senac.event.util;

import br.com.senac.event.event.PedidoEvent;
import br.com.senac.event.handler.PedidoEventHandler;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class RandomPedidoEventGenerator {

    private static Logger LOG = LoggerFactory.getLogger(RandomPedidoEventGenerator.class);

    @Autowired
    private PedidoEventHandler pedidoEventHandler;

    public void startSendingTemperatureReadings(final long noOfTemperatureEvents) {

        ExecutorService xrayExecutor = Executors.newSingleThreadExecutor();

        xrayExecutor.submit(new Runnable() {
            public void run() {

                LOG.debug(getStartingMessage());
                
                int count = 0;
                while (count < noOfTemperatureEvents) {
                    double max = 11000;
                    double min = 30;
                    double randon  = new Random().nextDouble();
                    double resultado = min + (randon *(max - min));
                    resultado = Math.round(resultado*100.0)/100.0;
                    PedidoEvent ve = new PedidoEvent(resultado, new Date());
                    pedidoEventHandler.handle(ve);
                    count++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        LOG.error("Thread Interrompida", e);
                    }
                }

            }
            
            public int tempo(){
                return new Random().nextInt(5000);
            }
        });
    }

    
    private String getStartingMessage(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n************************************************************");
        sb.append("\n* INICIANDO - ");
        sb.append("\n* FAVOR AGUARDE - PEDIDOS ESTAO SENDO GERADOS ALEATORIAMENTE");
        sb.append("\n* LOGO VERAS PEDIDOS CRITICOS E ALERTAS!");
        sb.append("\n************************************************************\n");
        return sb.toString();
    }
}
