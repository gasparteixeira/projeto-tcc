package br.com.senac.entity;

import br.com.senac.entity.Carrinho;
import br.com.senac.entity.Compra;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-11-09T16:30:26")
@StaticMetamodel(Carrinhocompra.class)
public class Carrinhocompra_ { 

    public static volatile SingularAttribute<Carrinhocompra, Integer> id;
    public static volatile SingularAttribute<Carrinhocompra, Carrinho> idcarrinho;
    public static volatile SingularAttribute<Carrinhocompra, Compra> idcompra;

}