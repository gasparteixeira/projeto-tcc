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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Categoria idCategoria;
    private Marca idMarca;
    private String nomeProduto;
    private List<Produto> listaProdutos = new ArrayList<Produto>();
    private String tituloPagina;

    public IndexController() {
        pattern = Pattern.compile(PASSWORD_PATTERN);
        tituloPagina = "Todos os produtos";

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
        if (listaProdutos == null || listaProdutos.isEmpty()) {
            listaProdutos = produtoFacade.findAll();
        }

        return listaProdutos;
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

    public String porCategoria() {
        Map map = new HashMap();
        Produto p = new Produto();
        p.setIdcategoria(idCategoria);
        map.put("idcategoria", idCategoria);

        listaProdutos = produtoFacade.findWithNamedQuery("Produto.findByCategoria", map, 0);
        tituloPagina = "Pela categoria '" + listaProdutos.get(0).getIdcategoria().getNome() + "'";

        return null;
    }

    public String porMarca() {
        Map map = new HashMap();
        Produto p = new Produto();
        p.setIdmarca(idMarca);
        map.put("idmarca", idMarca);

        listaProdutos = produtoFacade.findWithNamedQuery("Produto.findByMarca", map, 0);
        tituloPagina = "Pela marca '" + listaProdutos.get(0).getIdmarca().getNome() + "'";
        return null;
    }

    public String porBusca() {
        Map map = new HashMap();
        Produto p = new Produto();
        p.setNome(nomeProduto);
        map.put("nomeProduto", "%" + nomeProduto + "%");

        listaProdutos = produtoFacade.findWithNamedQuery("Produto.findByBusca", map, 0);
        tituloPagina = "Resultado da busca por '" + nomeProduto + "'.";

        return null;
    }

    public String getPegaDataAtual() {
        Date hoje = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        return sdf.format(hoje);
    }

    public String getPegaDiaTempo() {
        Date hoje = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("E, HH:mm");
        return sdf.format(hoje);
    }

    public String getDolarCotacao() {
        String valor = "2,18";
        /*try {
            URL url = new URL("http://dolarhoje.com/cotacao.txt");
            InputStream is = url.openStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((valor = br.readLine()) != null) {

            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        return valor;
    }

    public String showItem() {

        Produto p = new Produto(selectedEvent.getId(), selectedEvent.getNome(), selectedEvent.getPreco(), selectedEvent.getCodigo(), selectedEvent.getIdmarca());
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

        Usuario us = new Usuario(parameterMap.get("email").toString(), parameterMap.get("senha").toString());

        runUsuario(us);

        matcher = pattern.matcher(parameterMap.get("senha").toString());
        if (!matcher.matches()) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("Usuário e ou senha inválidos.");

            context.addMessage(null, message);

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

    public void runUsuario(Usuario u) {

        Configuration configuration = new Configuration();
        configuration.addEventTypeAutoName("br.com.senac.entity");

        EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider(configuration);

        String query = "select * from Usuario "
                + "match_recognize ( "
                + "       measures A as total, B as total1, C as total2  "
                + "       pattern (A B C) "
                + "       define "
                + "             A as A.senha != '' and A.email != '', "
                + "             B as B.senha = A.senha and B.email = A.email, "
                + "             C as C.senha = A.senha and C.email = A.email ) ";

        EPStatement stmt = engine.getEPAdministrator().createEPL(query);
        stmt.setSubscriber(new ObserverUsuario());

        engine.getEPRuntime().sendEvent(u);
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

    public boolean getAlerta() {
        return showEventoProduto;
    }

    public void cancelar() {
        showEventoProduto = false;
    }

    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    public List<Produto> getListaProdutos() {
        return listaProdutos;
    }

    public void setListaProdutos(List<Produto> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }

    public Marca getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Marca idMarca) {
        this.idMarca = idMarca;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getTituloPagina() {
        return tituloPagina;
    }

    public void setTituloPagina(String tituloPagina) {
        this.tituloPagina = tituloPagina;
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
    public static Boolean alertaUser = Boolean.FALSE;
    public static String mensagemSenha = "";
    private String mensagem = "";

    public String getMensagem() {

        return mensagem = mensagemSenha;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public void cancela() {
        alertaUser = Boolean.FALSE;
        mensagemSenha = "";
    }

    public Boolean getAlertar() {
        return alertaUser;
    }

    public class ObserverUsuario {

        public void update(Map<String, Usuario> eventMap) {

            Usuario total = (Usuario) eventMap.get("total");
            mensagemSenha = "<h3><i class='icon-info-sign'></i>Atenção:</h3> Você já tentou 3 vezes a senha '<b>" + total.getSenha() + "</b>' para o e-mail '<b>" + total.getEmail() + "</b>'.<br/>Provavelmente você esqueceu sua senha. <b>Habilitei</b> o link abaixo para a troca de senha.";
            System.out.println("O ususario total eh " + total.getSenha());
            alertaUser = Boolean.TRUE;
        }
    }
}
