package DTO;

import DTO.Pizzaadicional;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-12-22T01:01:40")
@StaticMetamodel(Ingredienteadicional.class)
public class Ingredienteadicional_ { 

    public static volatile SingularAttribute<Ingredienteadicional, String> descripcion;
    public static volatile ListAttribute<Ingredienteadicional, Pizzaadicional> pizzaadicionalList;
    public static volatile SingularAttribute<Ingredienteadicional, Double> valor;
    public static volatile SingularAttribute<Ingredienteadicional, Integer> idIngrediente;

}