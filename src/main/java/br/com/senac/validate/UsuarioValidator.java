/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.validate;

import br.com.senac.dao.UsuarioDAO;
import br.com.senac.entity.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Gaspar
 */
@Named
public class UsuarioValidator implements Validator, Serializable {
    
    private @Inject
    UsuarioDAO dao;

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        String email = (String) o;
        Map map = new HashMap();
        map.put("email", email);
        List<Usuario> user = new ArrayList<Usuario>();
        try {
            user = dao.findWithNamedQuery("Usuarios.countPorEmail", map);
        } catch (Exception e){
            e.printStackTrace();
        }
        
        if(!user.isEmpty()) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("O E-amil informado já foi cadastrado.");
            message.setDetail("O E-amil informado já foi cadastrado.");
            fc.addMessage("frmUsuario:email", message);
            throw new ValidatorException(message);
        }
    }
    
}
