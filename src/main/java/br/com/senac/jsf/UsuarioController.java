package br.com.senac.jsf;

import br.com.senac.entity.Tipo;
import br.com.senac.entity.Usuario;
import br.com.senac.jsf.util.JsfUtil;
import br.com.senac.jsf.util.PaginationHelper;
import br.com.senac.mb.UsuarioFacade;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

@Named("usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {

    private Usuario current;
    private DataModel items = null;
    @EJB
    private br.com.senac.mb.UsuarioFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private Pattern emailPattern;
    private Pattern cpfPattern;
    private Matcher matcher;
    private static final String CPF_PATTERN = "[0-9]{2,3}?\\.[0-9]{3}?\\.[0-9]{3}?\\-[0-9]{2}?";
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private String nome;
    private String cpf;
    private String sobrenome;
    private String email;
    private String senha;

    public UsuarioController() {
        emailPattern = Pattern.compile(EMAIL_PATTERN);
        cpfPattern = Pattern.compile(CPF_PATTERN);
    }

    public Usuario getSelected() {
        if (current == null) {
            current = new Usuario();
            selectedItemIndex = -1;
        }
        return current;
    }

    private UsuarioFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {
                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String cadastro() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        Map parameterMap = context.getExternalContext().getRequestParameterMap();

        Usuario u = new Usuario();
        u.setNome(parameterMap.get("nome").toString());
        u.setEmail(parameterMap.get("email").toString());
        u.setSobrenome(parameterMap.get("sobrenome").toString());
        u.setCpf(parameterMap.get("cpf").toString().replace("-", "").replace(".", ""));
        u.setSenha(parameterMap.get("senha").toString());
        Tipo t = new Tipo();
        t.setId(3);
        u.setIdtipo(t);
        try {
            getFacade().create(u);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário cadastrado com sucesso.", null));
            return "pretty:login";
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário NÃO efetuado. Tente novamente.", null));
            return "pretty:cadastro";
        }
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Usuario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Usuario();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Bundle").getString("UsuarioCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Usuario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Bundle").getString("UsuarioUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Usuario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Bundle").getString("UsuarioDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/resources/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Usuario getUsuario(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    public void validateEmailAddress(FacesContext context, UIComponent component, Object value) {

        matcher = emailPattern.matcher(value.toString());

        if (!matcher.matches()) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setDetail("E-mail inválido.");
            context.addMessage("frmUsuario:email", message);
            throw new ValidatorException(message);
        }
    }

    public void validateEmailUnico(FacesContext context, UIComponent component, Object value) {
        Map map = new HashMap();
        map.put("email", value.toString());

        matcher = emailPattern.matcher(value.toString());

        if (!matcher.matches()) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setDetail("E-mail inválido.");
            context.addMessage("frmUsuario:email", message);
            throw new ValidatorException(message);
        }

        List<Usuario> user = ejbFacade.findWithNamedQuery("Usuario.findByEmail", map, 0);
        if (!user.isEmpty()) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setDetail("O E-mail '" + email + "' já foi cadastrado anteriormente.");
            context.addMessage("frmUsuario:email", message);
            throw new ValidatorException(message);
        }
    }

    public void validateCPFUnico(FacesContext context, UIComponent component, Object value) {

        Map map = new HashMap();
        map.put("cpf", value.toString().replace(".", "").replace("-", ""));
        matcher = cpfPattern.matcher(value.toString());
        if (!matcher.matches()) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setDetail("CPF inválido.");
            context.addMessage("frmUsuario:cpf", message);
            throw new ValidatorException(message);
        }

        if (!validaCPF(value.toString().replace(".", "").replace("-", ""))) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setDetail("CPF inválido.");
            context.addMessage("frmUsuario:cpf", message);
            throw new ValidatorException(message);
        }



        List<Usuario> user = ejbFacade.findWithNamedQuery("Usuario.findByCpf", map, 0);
        if (!user.isEmpty()) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setDetail("O CPF '" + value.toString() + "' já foi cadastrado anteriormente.");
            context.addMessage("frmUsuario:cpf", message);
            throw new ValidatorException(message);
        }
    }

    public boolean validaCPF(String cpf) {
        boolean ret = false;
        String base = "000000000";
        String digitos = "00";

        if (cpf.equals("00000000000")
                || cpf.equals("11111111111")
                || cpf.equals("22222222222")
                || cpf.equals("33333333333")
                || cpf.equals("44444444444")
                || cpf.equals("55555555555")
                || cpf.equals("66666666666")
                || cpf.equals("77777777777")
                || cpf.equals("88888888888")
                || cpf.equals("99999999999")) {
            return false;
        }

        if (cpf.length() <= 11) {
            if (cpf.length() < 11) {
                cpf = base.substring(0, 11 - cpf.length()) + cpf;
                base = cpf.substring(0, 9);
            }
            base = cpf.substring(0, 9);
            digitos = cpf.substring(9, 11);
            int soma = 0, mult = 11;
            int[] var = new int[11];
            // Recebe os números e realiza a multiplicação e soma.  
            for (int i = 0; i < 9; i++) {
                var[i] = Integer.parseInt("" + cpf.charAt(i));
                if (i < 9) {
                    soma += (var[i] * --mult);
                }
            }
            // Cria o primeiro dígito verificador.  
            int resto = soma % 11;
            if (resto < 2) {
                var[9] = 0;
            } else {
                var[9] = 11 - resto;
            }
            // Reinicia os valores.  
            soma = 0;
            mult = 11;
            // Realiza a multiplicação e soma do segundo dígito.  
            for (int i = 0; i < 10; i++) {
                soma += var[i] * mult--;
            }
            // Cria o segundo dígito verificador.  
            resto = soma % 11;
            if (resto < 2) {
                var[10] = 0;
            } else {
                var[10] = 11 - resto;
            }
            if ((digitos.substring(0, 1).equalsIgnoreCase(new Integer(var[9])
                    .toString()))
                    && (digitos.substring(1, 2).equalsIgnoreCase(new Integer(
                    var[10]).toString()))) {
                ret = true;
            }
        }
        System.out.println(ret);
        return ret;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @FacesConverter(forClass = Usuario.class)
    public static class UsuarioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsuarioController controller = (UsuarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usuarioController");
            return controller.getUsuario(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Usuario) {
                Usuario o = (Usuario) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Usuario.class.getName());
            }
        }
    }
}
