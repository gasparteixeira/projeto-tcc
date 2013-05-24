/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.validate;

import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Gaspar
 */
public class ValidatorConfirmaSenha implements Validator {

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {

        Map parameterMap = fc.getExternalContext().getRequestParameterMap();

        String senha = (String) parameterMap.get("senha");

        String repsenha = (String) o;

        if (repsenha != null && repsenha.length() != 0 && !repsenha.equals(senha)) {
            throw new ValidatorException(new FacesMessage("Senha confirmada incorretamente."));
        }
    }
}
