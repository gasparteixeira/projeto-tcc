package br.com.senac.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-11-09T16:30:26")
@StaticMetamodel(Situacao.class)
public class Situacao_ { 

    public static volatile SingularAttribute<Situacao, Integer> id;
    public static volatile SingularAttribute<Situacao, String> status;
    public static volatile SingularAttribute<Situacao, Boolean> retrono;
    public static volatile SingularAttribute<Situacao, Integer> situacao;
    public static volatile SingularAttribute<Situacao, Date> datacadastro;
    public static volatile SingularAttribute<Situacao, Integer> idcompra;

}