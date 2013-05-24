/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.mb.utils;

import javax.faces.context.*;
import java.io.IOException;
import javax.faces.component.UIComponent;

public class InputRenderer extends com.sun.faces.renderkit.html_basic.TextRenderer {

    // Put all of the attributes you want to render here...
    private static final String[] ATTRIBUTES = {"required", "placeholder"};

    @Override
    protected void getEndTextToRender(FacesContext context,
            UIComponent component, String currentValue) throws IOException {
        final ResponseWriter originalResponseWriter = context.getResponseWriter();
        context.setResponseWriter(new ResponseWriterWrapper() {
            @Override
// As of JSF 1.2 this method is now public.
            public ResponseWriter getWrapped() {
                return originalResponseWriter;
            }

            @Override
            public void startElement(String name, UIComponent component)
                    throws IOException {
                super.startElement(name, component);
                if ("input".equals(name)) {
                    for (String attribute : ATTRIBUTES) {
                        Object value = component.getAttributes().get(attribute);
                        if (value != null) {
                            super.writeAttribute(attribute, value, attribute);
                        }
                    }
                }
            }
        });

        super.getEndTextToRender(context, component, currentValue);
        context.setResponseWriter(originalResponseWriter); // Restore original writer.




    }
}