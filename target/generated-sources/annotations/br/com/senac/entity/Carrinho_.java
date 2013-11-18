package br.com.senac.entity;

import br.com.senac.entity.Carrinhocompra;
import br.com.senac.entity.Produto;
import br.com.senac.entity.Usuario;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-11-16T23:48:05")
@StaticMetamodel(Carrinho.class)
public class Carrinho_ { 

    public static volatile SingularAttribute<Carrinho, Integer> id;
    public static volatile SingularAttribute<Carrinho, Usuario> idusuario;
    public static volatile SingularAttribute<Carrinho, Integer> quantidade;
    public static volatile SingularAttribute<Carrinho, BigDecimal> valor;
    public static volatile SingularAttribute<Carrinho, Date> datafechamento;
    public static volatile SingularAttribute<Carrinho, Date> datacadastro;
    public static volatile ListAttribute<Carrinho, Carrinhocompra> carrinhocompraList;
    public static volatile SingularAttribute<Carrinho, Produto> idproduto;

}