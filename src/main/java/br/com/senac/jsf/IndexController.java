/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.jsf;

import br.com.senac.entity.Carrinho;
import br.com.senac.entity.Categoria;
import br.com.senac.entity.Marca;
import br.com.senac.entity.Produto;
import br.com.senac.entity.Usuario;
import br.com.senac.event.event.LoginEvent;
import br.com.senac.mb.CarrinhoFacade;
import br.com.senac.mb.CategoriaFacade;
import br.com.senac.mb.GaleriaFacade;
import br.com.senac.mb.MarcaFacade;
import br.com.senac.mb.ProdutoFacade;
import br.com.senac.mb.UsuarioFacade;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Gaspar
 */
@ManagedBean
@SessionScoped
public class IndexController implements Serializable {

    @EJB
    private CategoriaFacade categoriaFacade;
    @EJB
    private MarcaFacade marcaFacade;
    @EJB
    private ProdutoFacade produtoFacade;
    @EJB
    private GaleriaFacade galeriaFacade;
    private Produto selectedProduto;
    private Produto selectedCarrinho;
    private Produto selectedEvent;
    private Integer quantidade = 1;
    private BigDecimal total;
    @EJB
    private UsuarioFacade usuarioFacade;
    private Usuario usuarioSelected;
    @EJB
    private CarrinhoFacade carrinhoFacade;
    private Carrinho selectedCarrinhoObj;
    private String email;
    private String senha;
    private Pattern pattern;
    private Matcher matcher;
    private static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{5,20})";
    public static boolean showEventoProduto = false;


    public IndexController() {
        pattern = Pattern.compile(PASSWORD_PATTERN);
    }

    public List<Categoria> getListaCategoria() {
        List<Categoria> lista = categoriaFacade.findAll();
        return lista;
    }

    public List<Marca> getListaMarca() {
        List<Marca> lista = marcaFacade.findAll();
        return lista;
    }

    public List<Produto> getListaProduto() {
        List<Produto> lista = produtoFacade.findAll();
        return lista;
    }

    public List<Carrinho> getListaCarrinho() throws IOException {
        List<Carrinho> listcar = new ArrayList<Carrinho>();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpServletResponse res = (HttpServletResponse) context.getExternalContext().getResponse();

        if (req.getSession().getAttribute("usuarioId") == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário tem estar logado para comprar.", null));
            res.sendRedirect("login");
        }
        Map map = new HashMap();
        Usuario u = new Usuario();
        u.setId(Integer.valueOf(req.getSession().getAttribute("usuarioId").toString()));
        map.put("idusuario", u);
        listcar = carrinhoFacade.findWithNamedQuery("Carrinho.findByUserId", map, 0);

        return listcar;
    }

    public String showItem() {

        //Produto p = new Produto(selectedEvent.getId(), selectedEvent.getNome(), selectedEvent.getPreco(), selectedEvent.getCodigo(), selectedEvent.getIdmarca());
        Produto p = new Produto();
        runProduto(p);

        return "show";
    }

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        Map parameterMap = context.getExternalContext().getRequestParameterMap();
        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();

        Map map = new HashMap();
        map.put("email", parameterMap.get("email").toString());
        map.put("senha", parameterMap.get("senha").toString());

        LoginEvent le = new LoginEvent(parameterMap.get("email").toString(), parameterMap.get("senha").toString());

        run(le);

        matcher = pattern.matcher(parameterMap.get("senha").toString());
        if (!matcher.matches()) {
            return null;
        }
        try {
            List<Usuario> user = usuarioFacade.findWithNamedQuery("Usuario.findByLogin", map, 0);
            if (user.size() > 0) {
                context.getExternalContext().getSessionMap().put("usuariologado", true);
                context.getExternalContext().getSessionMap().put("usuarioNome", user.get(0).getNome());
                context.getExternalContext().getSessionMap().put("usuarioId", user.get(0).getId());
                context.getExternalContext().getSessionMap().put("usuarioSobrenome", user.get(0).getSobrenome());
                context.getExternalContext().getSessionMap().put("usuarioEmail", user.get(0).getEmail());
                context.getExternalContext().getSessionMap().put("tipousuario", user.get(0).getIdtipo().getId());
                selectedCarrinho = (Produto) context.getExternalContext().getSessionMap().get("produtoAdicionado");
                if (user.get(0).getIdtipo().getId() == 3) {
                    if (selectedCarrinho != null) {
                        selectedCarrinhoObj = new Carrinho();
                        selectedCarrinhoObj.setIdproduto(selectedCarrinho);
                        Usuario sessao = new Usuario();
                        sessao.setId(Integer.valueOf(req.getSession().getAttribute("usuarioId").toString()));
                        selectedCarrinhoObj.setIdusuario(sessao);
                        selectedCarrinhoObj.setQuantidade(1);
                        selectedCarrinhoObj.setValor(selectedCarrinho.getPreco());
                        try {
                            carrinhoFacade.create(selectedCarrinhoObj);
                        } catch (Exception e) {
                            e.printStackTrace();
                            return "pretty:home";
                        }
                    }

                    return "pretty:carrinho";
                }
                return "pretty:home";
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário ou senha inválido.", null));
                return "pretty:login";
            }
        } catch (EJBException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO: Não foi possivel fazer a consulta! Tente novamente.", null));
            return "pretty:login";
        }
    }

    public void run(LoginEvent e) {

        Configuration configuration = new Configuration();
        configuration.addEventType("LoginEvent", LoginEvent.class);

        EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider(configuration);

        String query = "select * from LoginEvent(email = 'gaspar.teixeira@gmail.com').win:time_batch(30)";

        EPStatement stmt = engine.getEPAdministrator().createEPL(query);
        stmt.setSubscriber(new Observer());

        engine.getEPRuntime().sendEvent(e);
    }

    public void runProduto(Produto p) {

        Configuration configuration = new Configuration();
        configuration.addEventTypeAutoName("br.com.senac.entity");

        EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider(configuration);

        String query = "select * from Produto "
                + "match_recognize ( "
                + "       measures A as total "
                + "       pattern (A) "
                + "       define "
                + "             A as A.idmarca.getId() = 1 and A.preco > 2000 ) ";

        EPStatement stmt = engine.getEPAdministrator().createEPL(query);
        stmt.setSubscriber(new ObserverProduto());

        engine.getEPRuntime().sendEvent(p);
    }

    public String meuCarrinho() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map parameterMap = context.getExternalContext().getRequestParameterMap();
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        selectedCarrinho = produtoFacade.find(Integer.valueOf(parameterMap.get("itemCarrinho").toString()));
        selectedCarrinho.setDesconto(selectedCarrinho.getDesconto() != null ? selectedCarrinho.getDesconto() : new BigDecimal(BigInteger.ZERO));

        context.getExternalContext().getSessionMap().put("produtoAdicionado", selectedCarrinho);

        Object br = context.getExternalContext().getSessionMap().get("usuariologado");
        if (br == null) {
            return "pretty:login";
        }
        Object pr = context.getExternalContext().getSessionMap().get("produtoAdicionado");
        if (pr != null) {

            if (selectedCarrinho != null) {
                selectedCarrinhoObj = new Carrinho();
                selectedCarrinhoObj.setIdproduto(selectedCarrinho);
                Usuario sessao = new Usuario();
                sessao.setId(Integer.valueOf(req.getSession().getAttribute("usuarioId").toString()));
                selectedCarrinhoObj.setIdusuario(sessao);
                selectedCarrinhoObj.setQuantidade(1);
                selectedCarrinhoObj.setValor(selectedCarrinho.getPreco());
                try {
                    carrinhoFacade.create(selectedCarrinhoObj);
                } catch (Exception e) {
                    e.printStackTrace();
                    return "pretty:home";
                }
            }

            return "pretty:carrinho";
        }

        return "pretty:home";
    }

    public Integer getContaCarrinho() {
        Integer itens = 0;
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
        if (req.getSession().getAttribute("usuarioId") != null) {
            List<Carrinho> listcar = new ArrayList<Carrinho>();
            Map map = new HashMap();
            Usuario u = new Usuario();
            u.setId(Integer.valueOf(req.getSession().getAttribute("usuarioId").toString()));
            map.put("idusuario", u);
            listcar = carrinhoFacade.findWithNamedQuery("Carrinho.findByUserId", map, 0);
            itens = listcar.size();
        }
        return itens;
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "pretty:home";
    }

    public BigDecimal getTotalProduto() {
        BigDecimal valor = selectedCarrinho.getPreco().subtract(selectedCarrinho.getDesconto());
        return valor;
    }

    public CategoriaFacade getCategoriaFacade() {
        return categoriaFacade;
    }

    public void setCategoriaFacade(CategoriaFacade categoriaFacade) {
        this.categoriaFacade = categoriaFacade;
    }

    public MarcaFacade getMarcaFacade() {
        return marcaFacade;
    }

    public void setMarcaFacade(MarcaFacade marcaFacade) {
        this.marcaFacade = marcaFacade;
    }

    public ProdutoFacade getProdutoFacade() {
        return produtoFacade;
    }

    public void setProdutoFacade(ProdutoFacade produtoFacade) {
        this.produtoFacade = produtoFacade;
    }

    public GaleriaFacade getGaleriaFacade() {
        return galeriaFacade;
    }

    public void setGaleriaFacade(GaleriaFacade galeriaFacade) {
        this.galeriaFacade = galeriaFacade;
    }

    public Produto getSelectedProduto() {
        return selectedProduto;
    }

    public void setSelectedProduto(Produto selectedProduto) {
        this.selectedProduto = selectedProduto;
    }

    public Produto getSelectedCarrinho() {
        return selectedCarrinho;
    }

    public void setSelectedCarrinho(Produto selectedCarrinho) {
        this.selectedCarrinho = selectedCarrinho;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public UsuarioFacade getUsuarioFacade() {
        return usuarioFacade;
    }

    public void setUsuarioFacade(UsuarioFacade usuarioFacade) {
        this.usuarioFacade = usuarioFacade;
    }

    public Usuario getUsuarioSelected() {
        return usuarioSelected;
    }

    public void setUsuarioSelected(Usuario usuarioSelected) {
        this.usuarioSelected = usuarioSelected;
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

    public CarrinhoFacade getCarrinhoFacade() {
        return carrinhoFacade;
    }

    public void setCarrinhoFacade(CarrinhoFacade carrinhoFacade) {
        this.carrinhoFacade = carrinhoFacade;
    }

    public Carrinho getSelectedCarrinhoObj() {
        return selectedCarrinhoObj;
    }

    public void setSelectedCarrinhoObj(Carrinho selectedCarrinhoObj) {
        this.selectedCarrinhoObj = selectedCarrinhoObj;
    }

    public Produto getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(Produto selectedEvent) {
        this.selectedEvent = selectedEvent;
    }
    

    public boolean getAlerta(){
        return showEventoProduto;
    }
    public void cancelar(){
        showEventoProduto = false;
    }

    public class Observer {

        public void update(LoginEvent event) {
            System.out.println(event.getEmail());
        }
    }

    public class ObserverProduto {

        public void update(Map<String, Produto> eventMap) {
          Produto total = (Produto) eventMap.get("total");
            
            System.out.println("a marca sansung foi clicada: " + total.getCodigo());
            IndexController.showEventoProduto = true;
        }
    }
}
