/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.jsf;

import br.com.senac.entity.Categoria;
import br.com.senac.entity.Galeria;
import br.com.senac.entity.Marca;
import br.com.senac.entity.Produto;
import br.com.senac.mb.CategoriaFacade;
import br.com.senac.mb.GaleriaFacade;
import br.com.senac.mb.MarcaFacade;
import br.com.senac.mb.ProdutoFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Gaspar
 */
@ManagedBean
@ViewScoped
public class IndexController implements Serializable {

    @EJB
    private CategoriaFacade categoriaFacade;
    @EJB
    private MarcaFacade marcaFacade;
    @EJB
    private ProdutoFacade produtoFacade;
    @EJB
    private GaleriaFacade galeriaFacade;

    public IndexController() {
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
        List<Produto> retorno = new ArrayList<Produto>();
        List<Produto> lista = produtoFacade.findAll();
        List<Galeria> listag = galeriaFacade.findAll();
        for(Produto p: lista){
            for(Galeria g: listag){
                if(p.getId().equals(g.getIdproduto())){
                    p.getGaleriaList().add(g);
                }
            }
            retorno.add(p);
        }
        return retorno;
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
}
