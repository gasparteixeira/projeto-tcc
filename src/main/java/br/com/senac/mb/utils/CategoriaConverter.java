/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.mb.utils;

import br.com.senac.entity.Categoria;
import br.com.senac.mb.CategoriaController;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Gaspar
 */
@FacesConverter(value="categoriaConverter")
public class CategoriaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        FacesContext context = FacesContext.getCurrentInstance();
        CategoriaController categoria = (CategoriaController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "categoriaController");
        return categoria.getCategoriaPorParametro(value);
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        
        if(o != null && o instanceof Categoria)
            return ((Categoria)o).toString();
        return "";  
    }
    
}
