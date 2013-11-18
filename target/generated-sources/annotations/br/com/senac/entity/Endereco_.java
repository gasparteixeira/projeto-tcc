package br.com.senac.entity;

import br.com.senac.entity.Compra;
import br.com.senac.entity.Tipoendereco;
import br.com.senac.entity.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-11-16T23:48:05")
@StaticMetamodel(Endereco.class)
public class Endereco_ { 

    public static volatile SingularAttribute<Endereco, Integer> id;
    public static volatile SingularAttribute<Endereco, String> bairro;
    public static volatile SingularAttribute<Endereco, String> cidade;
    public static volatile SingularAttribute<Endereco, Usuario> idusuario;
    public static volatile SingularAttribute<Endereco, Integer> cep;
    public static volatile SingularAttribute<Endereco, String> pais;
    public static volatile SingularAttribute<Endereco, String> uf;
    public static volatile ListAttribute<Endereco, Compra> compraList;
    public static volatile SingularAttribute<Endereco, String> rua;
    public static volatile SingularAttribute<Endereco, Tipoendereco> idtipoendereco;
    public static volatile SingularAttribute<Endereco, Integer> numero;

}