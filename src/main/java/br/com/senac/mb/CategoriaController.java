/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.mb;

import br.com.senac.dao.CategoriaDAO;
import br.com.senac.entity.Categoria;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Gaspar
 */
@Named
@SessionScoped
public class CategoriaController implements Serializable {

    private @Inject
    CategoriaDAO dao;
    private Categoria selectedCats;
    private Categoria categoria = new Categoria();
    private Integer id;
    
    private List<Categoria> listaDeCategorias = new ArrayList<Categoria>();

    public CategoriaController() {
    }

    public void init() {
        
    }

    public String salvarCategoria() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map parameterMap = context.getExternalContext().getRequestParameterMap();

        categoria.setNome(parameterMap.get("nome").toString());
        dao.create(categoria);

        return "pretty:categoria";
    }

    public List<Categoria> getListaDeCategorias() {
        listaDeCategorias  = dao.findWithNamedQuery("Categorias.listaCategorias");
        return listaDeCategorias;
    }
    
    public List<Categoria> getCategoriaPorParametro(String id){
        Map map = new HashMap();
        map.put("id", id);
        return dao.findWithNamedQuery("Categorias.showCategoria", map);
    }

    public void setListaDeCategorias(List<Categoria> listaDeCategorias) {
        this.listaDeCategorias = listaDeCategorias;
    }


    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Categoria getSelectedCats() {
        return selectedCats;
    }

    public void setSelectedCats(Categoria selectedCats) {
        this.selectedCats = selectedCats;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
