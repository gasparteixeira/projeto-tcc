/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.mb.utils;

import br.com.senac.entity.Marca;
import br.com.senac.mb.MarcaController;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Gaspar
 */
@FacesConverter(value="marcaConverter")
public class MarcaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        FacesContext context = FacesContext.getCurrentInstance();
        MarcaController marca = (MarcaController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "categoriaController");
        return marca.getMarcaPorParametro(value);
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        
        if(o != null && o instanceof Marca)
            return ((Marca)o).toString();
        return "";  
    }
    
}
