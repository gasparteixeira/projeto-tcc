/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.mb;

import br.com.senac.dao.UsuarioDAO;
import br.com.senac.entity.Usuario;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.persistence.exceptions.QueryException;

/**
 *
 * @author Gaspar
 */
@Named
@SessionScoped
public class UsuarioController implements Serializable {

    private @Inject
    UsuarioDAO dao;
    private Usuario selectedUsers;
    private Usuario usuario = new Usuario();
    private String confirmaSenha = "";
    private Integer id;

    public UsuarioController() {
    }

    public void init() {
    }

    public String salvar() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map parameterMap = context.getExternalContext().getRequestParameterMap();

        usuario.setNome(parameterMap.get("nome").toString());
        usuario.setSobrenome(parameterMap.get("sobrenome").toString());
        usuario.setEmail(parameterMap.get("email").toString());
        usuario.setCpf(parameterMap.get("cpf").toString().replace(".", "").replace("-", ""));
        usuario.setSenha(parameterMap.get("senha").toString());
        usuario.setDatacriacao(new Date());

        dao.create(usuario);

        return "pretty:usuario";

    }

    public String editar() {

        FacesContext context = FacesContext.getCurrentInstance();
        Map parameterMap = context.getExternalContext().getRequestParameterMap();
        
        System.out.println("objeto "+parameterMap.get("selectedUsers"));
        
        /*Usuario u = (Usuario) parameterMap.get("selectedUsers");
      
        usuario.setNome(u.getNome());
        usuario.setEmail(u.getEmail());
        usuario.setCpf(u.getCpf());
        usuario.setSenha(u.getSenha());
        usuario.setSobrenome(u.getSobrenome());
        usuario.setDatacriacao(u.getDatacriacao());
        usuario.setId(u.getId());*/

        

        return "pretty:usuarioEdit";
    }
    
    public String update(){
        FacesContext context = FacesContext.getCurrentInstance();
        Map parameterMap = context.getExternalContext().getRequestParameterMap();
        
        /*usuario.setNome(parameterMap.get("nome").toString());
         usuario.setSobrenome(parameterMap.get("sobrenome").toString());
         usuario.setEmail(parameterMap.get("email").toString());
         usuario.setCpf(parameterMap.get("cpf").toString().replace(".", "").replace("-", ""));
         usuario.setSenha(parameterMap.get("senha").toString());
         usuario.setDatacriacao(new Date());

         dao.update(usuario);*/
        
         return "pretty:usuario";
    }

    public String deletar() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map parameterMap = context.getExternalContext().getRequestParameterMap();

        Integer id = Integer.parseInt((String) parameterMap.get("id"));

        try {
            dao.delete(id);
        } catch (QueryException e) {
            e.getStackTrace();
        }

        return "pretty:usuario";
    }

    public String cancelar() {

        setUsuario(new Usuario());

        return "pretty:usuario";
    }

    public List<Usuario> listarUsuario() {

        List<Usuario> user = dao.findWithNamedQuery("Usuarios.listaUsuarios");

        return user;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getConfirmaSenha() {
        return confirmaSenha;
    }

    public void setConfirmaSenha(String confirmaSenha) {
        this.confirmaSenha = confirmaSenha;
    }

    public Usuario getSelectedUsers() {
        return selectedUsers;
    }

    public void setSelectedUsers(Usuario selectedUsers) {
        this.selectedUsers = selectedUsers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
