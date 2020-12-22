package DTO;

import DTO.Pizza;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-12-22T01:01:40")
@StaticMetamodel(Sabor.class)
public class Sabor_ { 

    public static volatile SingularAttribute<Sabor, String> descripcion;
    public static volatile ListAttribute<Sabor, Pizza> pizzaList;
    public static volatile SingularAttribute<Sabor, String> imagen;
    public static volatile SingularAttribute<Sabor, Integer> idSabor;

}