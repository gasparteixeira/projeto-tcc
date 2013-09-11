package br.com.senac.entity;

import br.com.senac.entity.Produto;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-09-10T23:06:12")
@StaticMetamodel(Galeria.class)
public class Galeria_ { 

    public static volatile SingularAttribute<Galeria, Integer> id;
    public static volatile SingularAttribute<Galeria, String> imagem;
    public static volatile SingularAttribute<Galeria, Produto> idproduto;
    public static volatile SingularAttribute<Galeria, Boolean> destaque;

}