/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.mb;

import br.com.senac.dao.UsuarioDAO;
import br.com.senac.entity.Usuario;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
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
    
    private String login;
    private String senha;
    
    private boolean error = false;
    private String message;
    private Pattern emailPattern;
    private Pattern cpfPattern;
    private Matcher matcher;
    private static final String CPF_PATTERN = "[0-9]{2,3}?\\.[0-9]{3}?\\.[0-9]{3}?\\-[0-9]{2}?";
    private static final String EMAIL_PATTERN = 
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public UsuarioController() {
        emailPattern = Pattern.compile(EMAIL_PATTERN);
        cpfPattern = Pattern.compile(CPF_PATTERN);
    }

    public void init() {
    }
    
    public String login(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        Map parameterMap = context.getExternalContext().getRequestParameterMap();

        Map map = new HashMap();
        map.put("email", parameterMap.get("email").toString());
        map.put("senha", parameterMap.get("senha").toString());
        try {
            List<Usuario> user = dao.findWithNamedQuery("Usuarios.loginAcesso", map);
            if(user.size() > 0){
                context.getExternalContext().getSessionMap().put("usuariologado", true); 
                context.getExternalContext().getSessionMap().put("usuarioNome", user.get(0).getNome()); 
                context.getExternalContext().getSessionMap().put("usuarioSobrenome", user.get(0).getSobrenome()); 
                context.getExternalContext().getSessionMap().put("usuarioEmail", user.get(0).getEmail()); 
                return "pretty:home";
            } else {
                
                System.out.println("passei por aqui.. o bixo eh invalido.");
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário ou senha inválido.", null));
                return "pretty:login";
            }
            
        } catch(EJBException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO: Não foi possivel fazer a consulta! Tente novamente.", null));
            return "pretty:login";
        }
    }

    public String salvar() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        Map parameterMap = context.getExternalContext().getRequestParameterMap();

        usuario.setNome(parameterMap.get("nome").toString());
        usuario.setSobrenome(parameterMap.get("sobrenome").toString());
        usuario.setEmail(parameterMap.get("email").toString());
        usuario.setCpf(parameterMap.get("cpf").toString().replace(".", "").replace("-", ""));
        usuario.setSenha(parameterMap.get("senha").toString());
        usuario.setDatacriacao(new Date());
        
        try {
            dao.create(usuario);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário cadastrado com sucesso.", null)); 
        } catch (EJBException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO: Usuário não foi cadastrado! Tente novamente.", null)); 
            return "pretty:usuarioNew";
        }

        return "pretty:usuario";

    }

    public String editar() {

        FacesContext context = FacesContext.getCurrentInstance();
        Map parameterMap = context.getExternalContext().getRequestParameterMap();


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

    public String update() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
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
        context.getExternalContext().getFlash().setKeepMessages(true);
        Map parameterMap = context.getExternalContext().getRequestParameterMap();

        Integer id = Integer.parseInt((String) parameterMap.get("id"));

        try {
            dao.delete(id);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário excluído com sucesso.", null)); 
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
    
    //Validações
     public void validateEmailAddress(FacesContext context, UIComponent component, Object value) {
         String email = (String) value;
         matcher = emailPattern.matcher(email);
        
        if(!matcher.matches()) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setDetail("E-mail inválido.");
            context.addMessage("frmUsuario:email", message);
            throw new ValidatorException(message);
        }
     }
    
    public void validateEmailUnico(FacesContext context, UIComponent component, Object value) {
        String email = (String) value;
        Map map = new HashMap();
        map.put("email", email);
        
        matcher = emailPattern.matcher(email);
        
        if(!matcher.matches()) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setDetail("E-mail inválido.");
            context.addMessage("frmUsuario:email", message);
            throw new ValidatorException(message);
        }
        
        List<Usuario> user = dao.findWithNamedQuery("Usuarios.countPorEmail", map);
        if(!user.isEmpty()){
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setDetail("O E-mail '" + email + "' já foi cadastrado anteriormente.");
            context.addMessage("frmUsuario:email", message);
            throw new ValidatorException(message);
        }
    }
    
    public void validateCPFUnico(FacesContext context, UIComponent component, Object value){
        String cpf = (String) value;
        Map map = new HashMap();
        map.put("cpf", cpf.toString().replace(".", "").replace("-", ""));
        matcher = cpfPattern.matcher(cpf);
        if(!matcher.matches()) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setDetail("CPF inválido.");
            context.addMessage("frmUsuario:cpf", message);
            throw new ValidatorException(message);
        }
        
        List<Usuario> user = dao.findWithNamedQuery("Usuarios.countPorCpf", map);
        if(!user.isEmpty()){
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setDetail("O CPF '" + cpf + "' já foi cadastrado anteriormente.");
            context.addMessage("frmUsuario:cpf", message);
            throw new ValidatorException(message);
        }
    }
    
    // getter & setters
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

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    
}
