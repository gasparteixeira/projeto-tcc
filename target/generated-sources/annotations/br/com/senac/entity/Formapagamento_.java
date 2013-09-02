package br.com.senac.entity;

import br.com.senac.entity.Compra;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-09-02T00:45:33")
@StaticMetamodel(Formapagamento.class)
public class Formapagamento_ { 

    public static volatile SingularAttribute<Formapagamento, Integer> id;
    public static volatile ListAttribute<Formapagamento, Compra> compraList;
    public static volatile SingularAttribute<Formapagamento, String> nome;
    public static volatile SingularAttribute<Formapagamento, Integer> parcela;

}