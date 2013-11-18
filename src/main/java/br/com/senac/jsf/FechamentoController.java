/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.jsf;

import br.com.senac.entity.Carrinho;
import br.com.senac.entity.Cartao;
import br.com.senac.entity.Formapagamento;
import br.com.senac.entity.Usuario;
import br.com.senac.mb.CarrinhoFacade;
import br.com.senac.mb.CompraFacade;
import br.com.senac.mb.FormapagamentoFacade;
import br.com.senac.mb.ProdutoFacade;
import br.com.senac.mb.UsuarioFacade;
import com.espertech.esper.client.EPServiceProvider;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Gaspar
 */
@ManagedBean
@SessionScoped
public class FechamentoController implements Serializable {

    @EJB
    private ProdutoFacade produtoFacade;
    @EJB
    private UsuarioFacade usuarioFacade;
    @EJB
    private CompraFacade compraFacade;
    @EJB
    private FormapagamentoFacade formapagamentoFacade;
    @EJB
    private CarrinhoFacade carrinhoFacade;
    private Formapagamento selectedForma;
    private String formaPagar = "1";
    private Cartao cartaoCredito = new Cartao();
    private Carrinho car;
    private Usuario usuarioSelected;
    private EPServiceProvider epService;

    public FechamentoController() {
    }

    public BigDecimal getValor() throws IOException {
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
        BigDecimal soma   = new BigDecimal(0);
        for(Carrinho c: listcar){
            soma = soma.add(c.getValor());
        }
        return soma;
    }

    public String pagar() {

        return null;
    }

    public ProdutoFacade getProdutoFacade() {
        return produtoFacade;
    }

    public void setProdutoFacade(ProdutoFacade produtoFacade) {
        this.produtoFacade = produtoFacade;
    }

    public UsuarioFacade getUsuarioFacade() {
        return usuarioFacade;
    }

    public void setUsuarioFacade(UsuarioFacade usuarioFacade) {
        this.usuarioFacade = usuarioFacade;
    }

    public CompraFacade getCompraFacade() {
        return compraFacade;
    }

    public void setCompraFacade(CompraFacade compraFacade) {
        this.compraFacade = compraFacade;
    }

    public FormapagamentoFacade getFormapagamentoFacade() {
        return formapagamentoFacade;
    }

    public void setFormapagamentoFacade(FormapagamentoFacade formapagamentoFacade) {
        this.formapagamentoFacade = formapagamentoFacade;
    }

    public CarrinhoFacade getCarrinhoFacade() {
        return carrinhoFacade;
    }

    public void setCarrinhoFacade(CarrinhoFacade carrinhoFacade) {
        this.carrinhoFacade = carrinhoFacade;
    }

    public Usuario getUsuarioSelected() {
        return usuarioSelected;
    }

    public void setUsuarioSelected(Usuario usuarioSelected) {
        this.usuarioSelected = usuarioSelected;
    }

    public Formapagamento getSelectedForma() {
        return selectedForma;
    }

    public void setSelectedForma(Formapagamento selectedForma) {
        this.selectedForma = selectedForma;
    }

    public String getFormaPagar() {
        return formaPagar;
    }

    public void setFormaPagar(String formaPagar) {
        this.formaPagar = formaPagar;
    }

    public Cartao getCartaoCredito() {
        return cartaoCredito;
    }

    public void setCartaoCredito(Cartao cartaoCredito) {
        this.cartaoCredito = cartaoCredito;
    }

    public Carrinho getCar() {
        return car;
    }

    public void setCar(Carrinho car) {
        this.car = car;
    }

    public void validateCartao(FacesContext context, UIComponent component, Object value) {

        String cardNumber = value.toString().replace(" ", "").replace("-", "");

        int sum = 0;
        int digit = 0;
        int addend = 0;
        boolean timesTwo = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            digit = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (timesTwo) {
                addend = digit * 2;
                if (addend > 9) {
                    addend -= 9;
                }
            } else {
                addend = digit;
            }
            sum += addend;
            timesTwo = !timesTwo;
        }
        int modulus = sum % 10;
        if (modulus != 0) {
             FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setDetail("Cartão inválido.");
            context.addMessage("frmCompra:cartao", message);
            throw new ValidatorException(message);
        }
    }
}
