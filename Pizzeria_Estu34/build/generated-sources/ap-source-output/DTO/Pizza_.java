package DTO;

import DTO.Pizzaadicional;
import DTO.Sabor;
import DTO.Tipo;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-12-22T01:01:40")
@StaticMetamodel(Pizza.class)
public class Pizza_ { 

    public static volatile SingularAttribute<Pizza, Pizzaadicional> pizzaadicional;
    public static volatile SingularAttribute<Pizza, Integer> idPizza;
    public static volatile SingularAttribute<Pizza, Double> valor;
    public static volatile SingularAttribute<Pizza, Tipo> idTipo;
    public static volatile SingularAttribute<Pizza, Sabor> idSabor;

}