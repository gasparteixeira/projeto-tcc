package br.com.senac.entity;

import br.com.senac.entity.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-11-09T16:30:26")
@StaticMetamodel(Tipo.class)
public class Tipo_ { 

    public static volatile SingularAttribute<Tipo, Integer> id;
    public static volatile ListAttribute<Tipo, Usuario> usuarioList;
    public static volatile SingularAttribute<Tipo, String> nome;

}