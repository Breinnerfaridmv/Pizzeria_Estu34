/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PC
 */
@Entity
@Table(name = "pizzaadicional")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pizzaadicional.findAll", query = "SELECT p FROM Pizzaadicional p")
    , @NamedQuery(name = "Pizzaadicional.findByIdPizza", query = "SELECT p FROM Pizzaadicional p WHERE p.idPizza = :idPizza")})
public class Pizzaadicional implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_pizza")
    private Integer idPizza;
    @JoinColumn(name = "id_pizza", referencedColumnName = "id_pizza", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Pizza pizza;
    @JoinColumn(name = "id_ingrediente", referencedColumnName = "id_ingrediente")
    @ManyToOne(optional = false)
    private Ingredienteadicional idIngrediente;

    public Pizzaadicional() {
    }

    public Pizzaadicional(Integer idPizza) {
        this.idPizza = idPizza;
    }

    public Integer getIdPizza() {
        return idPizza;
    }

    public void setIdPizza(Integer idPizza) {
        this.idPizza = idPizza;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public Ingredienteadicional getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(Ingredienteadicional idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPizza != null ? idPizza.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pizzaadicional)) {
            return false;
        }
        Pizzaadicional other = (Pizzaadicional) object;
        if ((this.idPizza == null && other.idPizza != null) || (this.idPizza != null && !this.idPizza.equals(other.idPizza))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DAOS.Pizzaadicional[ idPizza=" + idPizza + " ]";
    }
    
}
