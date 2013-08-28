/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.mb;

import br.com.senac.dao.MarcaDAO;
import br.com.senac.entity.Marca;
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
public class MarcaController implements Serializable {

    private @Inject
    MarcaDAO dao;
    private Marca selectedMarca;
    private Marca marca = new Marca();
    private Integer id;
    private List<Marca> listaDeMarcas = new ArrayList<Marca>();

    public MarcaController() {
    }

    public void init() {
    }

    public String salvarMarca() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map parameterMap = context.getExternalContext().getRequestParameterMap();

        marca.setNome(parameterMap.get("nome").toString());
        dao.create(marca);

        return "pretty:marca";
    }

    public List<Marca> getListaDeMarcas() {
        listaDeMarcas = dao.findWithNamedQuery("Marcas.listaMarcas");
        return listaDeMarcas;
    }

    public List<Marca> getMarcaPorParametro(String id) {
        Map map = new HashMap();
        map.put("id", id);
        return dao.findWithNamedQuery("Marcas.showMarca", map);
    }

    public void setListaDeMarcas(List<Marca> listaDeMarcas) {
        this.listaDeMarcas = listaDeMarcas;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Marca getSelectedMarca() {
        return selectedMarca;
    }

    public void setSelectedMarca(Marca selectedMarca) {
        this.selectedMarca = selectedMarca;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
