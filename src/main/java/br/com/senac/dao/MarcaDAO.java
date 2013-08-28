/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.dao;

import br.com.senac.entity.Marca;
import javax.ejb.Stateless;

/**
 *
 * @author Gaspar
 */
@Stateless
public class MarcaDAO extends DataAccessObject<Marca> {
    
    public MarcaDAO(){
        super(Marca.class);
    }
    
    public Marca newMarca(){
        return new Marca();
    }
}
