/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.validate;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Gaspar
 */
public class EmailValidator implements Validator{
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException {
         
        String email = (String) value;
         
        if(!email.contains("@")) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("Email is not valid.");
            message.setDetail("Email is not valid.");
            context.addMessage("frmUsuario:email", message);
            throw new ValidatorException(message);
        }
    }
}
    

