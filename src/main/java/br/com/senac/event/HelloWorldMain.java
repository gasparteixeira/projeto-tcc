/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.event;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

/**
 *
 * @author Gaspar
 */
public class HelloWorldMain {
    
    public static void main(String[] args) {

        HelloWorldMain helloWorld = new HelloWorldMain();
        helloWorld.run();
    }

    public void run() {

        Configuration configuration = new Configuration();
        configuration.addEventType("HelloWorld", HelloWorld.class); // or put HelloWorld in default package

        EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider(configuration);
        String query = "select a.id, count(*) from pattern [" +
        "every a=Status -> (timer:interval(10 sec) and not Status(id=a.id)] group by id ";
        EPStatement stmt = engine.getEPAdministrator().createEPL("select * from HelloWorld");
        stmt.setSubscriber(new Observer());

        engine.getEPRuntime().sendEvent(new HelloWorld());
    }

    public class Observer {
        public void update(HelloWorld event)
          { System.out.println ("Hello World");}
    }
    
}
