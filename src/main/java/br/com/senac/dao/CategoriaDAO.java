/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.dao;

import br.com.senac.entity.Categoria;
import javax.ejb.Stateless;

/**
 *
 * @author Gaspar
 */
@Stateless
public class CategoriaDAO extends DataAccessObject<Categoria>{
    
    public CategoriaDAO(){
        super(Categoria.class);
    }
    
    public Categoria newCategoria(){
        return new Categoria();
    }
}
