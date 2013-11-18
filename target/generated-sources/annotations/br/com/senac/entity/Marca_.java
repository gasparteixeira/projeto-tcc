package br.com.senac.entity;

import br.com.senac.entity.Produto;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-11-16T23:48:05")
@StaticMetamodel(Marca.class)
public class Marca_ { 

    public static volatile SingularAttribute<Marca, Integer> id;
    public static volatile ListAttribute<Marca, Produto> produtoList;
    public static volatile SingularAttribute<Marca, String> nome;

}