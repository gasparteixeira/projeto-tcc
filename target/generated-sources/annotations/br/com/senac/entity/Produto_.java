package br.com.senac.entity;

import br.com.senac.entity.Carrinho;
import br.com.senac.entity.Categoria;
import br.com.senac.entity.Galeria;
import br.com.senac.entity.Marca;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-11-16T23:48:05")
@StaticMetamodel(Produto.class)
public class Produto_ { 

    public static volatile SingularAttribute<Produto, Integer> id;
    public static volatile SingularAttribute<Produto, String> codigo;
    public static volatile SingularAttribute<Produto, BigDecimal> desconto;
    public static volatile SingularAttribute<Produto, BigDecimal> preco;
    public static volatile SingularAttribute<Produto, String> informacoes;
    public static volatile SingularAttribute<Produto, Marca> idmarca;
    public static volatile ListAttribute<Produto, Carrinho> carrinhoList;
    public static volatile SingularAttribute<Produto, Categoria> idcategoria;
    public static volatile SingularAttribute<Produto, String> nome;
    public static volatile SingularAttribute<Produto, Date> datacadastro;
    public static volatile ListAttribute<Produto, Galeria> galeriaList;

}