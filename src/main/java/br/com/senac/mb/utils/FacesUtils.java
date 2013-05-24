/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.mb.utils;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Gaspar
 */
public class FacesUtils {
    public static <T> T getManagedBean(String key){
        HttpServletRequest request =
                (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return (T) request.getSession().getAttribute(key);
    }
}
