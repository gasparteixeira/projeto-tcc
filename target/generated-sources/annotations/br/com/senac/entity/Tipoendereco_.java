package br.com.senac.entity;

import br.com.senac.entity.Endereco;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-09-10T23:06:12")
@StaticMetamodel(Tipoendereco.class)
public class Tipoendereco_ { 

    public static volatile SingularAttribute<Tipoendereco, Integer> id;
    public static volatile SingularAttribute<Tipoendereco, String> sigla;
    public static volatile SingularAttribute<Tipoendereco, String> nome;
    public static volatile ListAttribute<Tipoendereco, Endereco> enderecoList;

}