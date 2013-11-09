package br.com.senac.entity;

import br.com.senac.entity.Tipotelefone;
import br.com.senac.entity.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-11-09T16:30:26")
@StaticMetamodel(Telefone.class)
public class Telefone_ { 

    public static volatile SingularAttribute<Telefone, Integer> id;
    public static volatile SingularAttribute<Telefone, Usuario> idusuario;
    public static volatile SingularAttribute<Telefone, Integer> ddd;
    public static volatile SingularAttribute<Telefone, Tipotelefone> idtipotelefone;
    public static volatile SingularAttribute<Telefone, Integer> numero;

}