package DTO;

import DTO.Pizza;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-12-22T01:01:40")
@StaticMetamodel(Tipo.class)
public class Tipo_ { 

    public static volatile SingularAttribute<Tipo, String> descripcion;
    public static volatile ListAttribute<Tipo, Pizza> pizzaList;
    public static volatile SingularAttribute<Tipo, Integer> idTipo;

}