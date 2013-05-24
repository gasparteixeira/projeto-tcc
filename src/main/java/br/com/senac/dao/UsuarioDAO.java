/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.dao;

import br.com.senac.entity.Usuario;
import javax.ejb.Stateless;

/**
 *
 * @author Gaspar
 */
@Stateless
public class UsuarioDAO extends DataAccessObject<Usuario> {

    public UsuarioDAO() {
        super(Usuario.class);
    }

    public Usuario newUsuario() {
        return new Usuario();
    }
}
