package br.com.senac.entity;

import br.com.senac.entity.Carrinho;
import br.com.senac.entity.Endereco;
import br.com.senac.entity.Telefone;
import br.com.senac.entity.Tipo;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-09-10T23:06:12")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile SingularAttribute<Usuario, Integer> id;
    public static volatile SingularAttribute<Usuario, Date> datacriacao;
    public static volatile SingularAttribute<Usuario, String> sobrenome;
    public static volatile SingularAttribute<Usuario, String> email;
    public static volatile ListAttribute<Usuario, Carrinho> carrinhoList;
    public static volatile ListAttribute<Usuario, Telefone> telefoneList;
    public static volatile SingularAttribute<Usuario, String> nome;
    public static volatile SingularAttribute<Usuario, String> cpf;
    public static volatile SingularAttribute<Usuario, Tipo> idtipo;
    public static volatile SingularAttribute<Usuario, String> senha;
    public static volatile ListAttribute<Usuario, Endereco> enderecoList;

}