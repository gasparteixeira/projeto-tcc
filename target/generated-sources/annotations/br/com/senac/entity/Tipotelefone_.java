package br.com.senac.entity;

import br.com.senac.entity.Telefone;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-11-09T16:30:26")
@StaticMetamodel(Tipotelefone.class)
public class Tipotelefone_ { 

    public static volatile SingularAttribute<Tipotelefone, Integer> id;
    public static volatile SingularAttribute<Tipotelefone, Character> sigla;
    public static volatile ListAttribute<Tipotelefone, Telefone> telefoneList;
    public static volatile SingularAttribute<Tipotelefone, String> nome;

}