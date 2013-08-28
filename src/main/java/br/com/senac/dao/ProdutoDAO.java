/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.dao;

import br.com.senac.entity.Produto;

/**
 *
 * @author Gaspar
 */
public class ProdutoDAO extends DataAccessObject<Produto>{

    public ProdutoDAO() {
        super(Produto.class);
    }
    
    public Produto newProduto(){
        return new Produto();
    }
    
    
}
