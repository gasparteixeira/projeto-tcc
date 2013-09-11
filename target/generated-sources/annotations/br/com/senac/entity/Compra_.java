package br.com.senac.entity;

import br.com.senac.entity.Carrinhocompra;
import br.com.senac.entity.Endereco;
import br.com.senac.entity.Formapagamento;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-09-10T23:06:12")
@StaticMetamodel(Compra.class)
public class Compra_ { 

    public static volatile SingularAttribute<Compra, Integer> id;
    public static volatile SingularAttribute<Compra, String> codigo;
    public static volatile SingularAttribute<Compra, BigDecimal> valor;
    public static volatile SingularAttribute<Compra, Endereco> idendereco;
    public static volatile SingularAttribute<Compra, String> cartao;
    public static volatile SingularAttribute<Compra, Date> datacadastro;
    public static volatile ListAttribute<Compra, Carrinhocompra> carrinhocompraList;
    public static volatile SingularAttribute<Compra, Formapagamento> idformapagamento;

}