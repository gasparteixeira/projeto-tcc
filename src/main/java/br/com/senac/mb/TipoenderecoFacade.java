/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.mb;

import br.com.senac.entity.Tipoendereco;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Gaspar
 */
@Stateless
public class TipoenderecoFacade extends AbstractFacade<Tipoendereco> {
    @PersistenceContext(unitName = "ecommercePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoenderecoFacade() {
        super(Tipoendereco.class);
    }
    
}
