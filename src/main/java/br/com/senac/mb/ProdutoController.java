/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.mb;

import br.com.senac.dao.ProdutoDAO;
import br.com.senac.entity.Categoria;
import br.com.senac.entity.Marca;
import br.com.senac.entity.Produto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Gaspar
 */
@Named
@SessionScoped
public class ProdutoController implements Serializable {

    private @Inject
    ProdutoDAO dao;
    private Produto selectedProduto;
    private Produto produto = new Produto();
    private Integer id;
    private List<Produto> listaDeProdutos = new ArrayList<Produto>();
    private Categoria categoria = new Categoria();
    private Marca marca = new Marca();
    public ProdutoController() {
    }

    public void init() {
    }
    
    public String salvar(){
        FacesContext context = FacesContext.getCurrentInstance();
        Map parameterMap = context.getExternalContext().getRequestParameterMap();
        
       /* produto.setNome(parameterMap.get("nome").toString());
        produto.setPreco(Double.valueOf(parameterMap.get("preco").toString()));
        produto.setDesconto(Double.valueOf(parameterMap.get("desconto").toString()));
        produto.setCodigo(parameterMap.get("codigo").toString());
        produto.setInformacoes(parameterMap.get("informacoes").toString());
        categoria.setId(Integer.valueOf(parameterMap.get("idcategoria").toString()));
        produto.setIdcategoria(categoria);
        marca.setId(Integer.valueOf(parameterMap.get("marca").toString()));
        produto.setIdmarca(marca);
        produto.setDatacadastro(new Date());*/

        try {
            dao.create(produto);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Produto cadastrado com sucesso.", null)); 
        } catch(EJBException e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO: Produto não foi cadastrado! Tente novamente.", null)); 
            return "pretty:produtoNew";
        }
        
        return "pettry:produto";
    }
    
    public List<Produto> getListarProdutos(){
        
        listaDeProdutos = dao.findWithNamedQuery("Produtos.listaProdutos");
        return listaDeProdutos;
    }
    /** lista de marcas **/
    public List<SelectItem> getListaDeCategorias() {
        DataModel lista = (DataModel) new CategoriaController().getListaDeCategorias();
        List<SelectItem> listaItens = new ArrayList<SelectItem>();
        for(Object object:lista){
            Categoria c  = (Categoria) object;
            listaItens.add(new SelectItem(c, c.getNome()));
        }
            
        return listaItens;
    }
    /** lista de marcas **/
    public List<SelectItem> getListaDeMarcas() {
        DataModel lista = (DataModel) new MarcaController().getListaDeMarcas();
        List<SelectItem> listaItens = new ArrayList<SelectItem>();
        for(Object object:lista){
            Marca c  = (Marca) object;
            listaItens.add(new SelectItem(c, c.getNome()));
        }
            
        return listaItens;
    }

    public Produto getSelectedProduto() {
        return selectedProduto;
    }

    public void setSelectedProduto(Produto selectedProduto) {
        this.selectedProduto = selectedProduto;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    
}
