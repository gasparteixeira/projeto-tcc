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
import br.com.senac.entity.LoginEvent;
import br.com.senac.mb.CarrinhoFacade;
import br.com.senac.mb.CategoriaFacade;
import br.com.senac.mb.GaleriaFacade;
import br.com.senac.mb.MarcaFacade;
import br.com.senac.mb.ProdutoFacade;
import br.com.senac.mb.UsuarioFacade;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
    private Carrinho selectItemRemove;
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
    public static boolean viewProduto = false;
    public static StringBuilder monitorProduto = new StringBuilder();
    public static StringBuilder monitorVisualizacao = new StringBuilder();
    public static List<Produto> maisVistos = new ArrayList<Produto>();
    @EJB
    private ServicoEventoController service;
    

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
    
    public void monitoraProduto(){
        
    }
    
    public void monitoraVisualizcao(){
        System.out.println("passei."+maisVistos.toString());
    }

    public String removerItem() {

        carrinhoFacade.remove(selectItemRemove);
        return null;
    }

    public void observar() {
        viewProduto = false;
        sugereProduto(selectedProduto);
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
        return valor;
    }

    public String showItem() {
        viewProduto = false;

        Produto p = new Produto(selectedEvent.getId(), selectedEvent.getNome(), selectedEvent.getPreco(), selectedEvent.getCodigo(), selectedEvent.getIdmarca());
        controlaValorProduto(p);
        sugereProduto(p);

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

        observaErroSenha(le);

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
                return null;
            }
        } catch (EJBException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO: Não foi possivel fazer a consulta! Tente novamente.", null));
            return null;
        }
    }

    public void controlaValorProduto(Produto p) {

        if (!service.getStatus()) {
            service.init();
            service.setStatus(Boolean.TRUE);
        }
        service.getEpService().getEPRuntime().sendEvent(p);

    }

    public void observaErroSenha(LoginEvent u) {

        if (!service.getStatus()) {
            service.init();
            service.setStatus(Boolean.TRUE);
        }
        service.getEpService().getEPRuntime().sendEvent(u);
    }

    public void sugereProduto(Produto p) {

        if (!service.getStatus()) {
            service.init();
            service.setStatus(Boolean.TRUE);
        }
        service.getEpService().getEPRuntime().sendEvent(p);
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

    public boolean isViewProduto() {
        return viewProduto;
    }

    public void setViewProduto(boolean viewProduto) {
        this.viewProduto = viewProduto;
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

    public Carrinho getSelectItemRemove() {
        return selectItemRemove;
    }

    public void setSelectItemRemove(Carrinho selectItemRemove) {
        this.selectItemRemove = selectItemRemove;
    }

    public ServicoEventoController getService() {
        return service;
    }

    public void setService(ServicoEventoController service) {
        this.service = service;
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

    public StringBuilder getMonitorProduto() {
        return monitorProduto;
    }    

    public void setMonitorProduto(StringBuilder monitorProduto) {
        IndexController.monitorProduto = monitorProduto;
    }

    public  StringBuilder getMonitorVisualizacao() {
        return monitorVisualizacao;
    }

    public  void setMonitorVisualizacao(StringBuilder monitorVisualizacao) {
        IndexController.monitorVisualizacao = monitorVisualizacao;
    }
    
    

    public  List<Produto> getMaisVistos() {
        List<Produto> auxiliar = new ArrayList<Produto>();
        List<Produto> noDuplicate = new ArrayList<Produto>();
        monitorVisualizacao.delete(0, monitorVisualizacao.length());
        
        Map<Produto, Integer> map = new HashMap<Produto, Integer>();
        for(Produto temp: maisVistos){
            Integer count = map.get(temp);
            map.put(temp, (count == null) ? 1 : count + 1);
        }
        
        List<Map.Entry> a = new ArrayList<Map.Entry>(map.entrySet());
        
        Collections.sort(a,
         new Comparator() {
             public int compare(Object o1, Object o2) {
                 Map.Entry e1 = (Map.Entry) o1;
                 Map.Entry e2 = (Map.Entry) o2;
                 return ((Comparable) e2.getValue()).compareTo(e1.getValue());
             }
         });

       
        for (Map.Entry e : a) {
            noDuplicate.add((Produto) e.getKey());
            Produto p = (Produto)e.getKey();
            monitorVisualizacao.append("<span class='normal'>").append("Produto: ").append(p.getNome()).append(", R$= ").append(p.getPreco())
                    .append(", (").append(Integer.valueOf(e.getValue().toString())/2).append(") ").append(" vez(es) visto.").append("</span>");
        }
        

        for(Produto v:noDuplicate ){
            for(Produto p: listaProdutos){
                if(p.getId() == v.getId()){
                    if(!auxiliar.contains(p) && auxiliar.size() < 4){
                        auxiliar.add(p);
                    } 
                }
            }
        }
        
        return auxiliar;
    }

    public  void setMaisVistos(List<Produto> maisVistos) {
        IndexController.maisVistos = maisVistos;
    }

}
