/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "pizza")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pizza.findAll", query = "SELECT p FROM Pizza p")
    , @NamedQuery(name = "Pizza.findByIdPizza", query = "SELECT p FROM Pizza p WHERE p.idPizza = :idPizza")
    , @NamedQuery(name = "Pizza.findByValor", query = "SELECT p FROM Pizza p WHERE p.valor = :valor")})
public class Pizza implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_pizza")
    private Integer idPizza;
    @Basic(optional = false)
    @Column(name = "valor")
    private double valor;
    @JoinColumn(name = "id_tipo", referencedColumnName = "id_tipo")
    @ManyToOne(optional = false)
    private Tipo idTipo;
    @JoinColumn(name = "id_sabor", referencedColumnName = "id_sabor")
    @ManyToOne(optional = false)
    private Sabor idSabor;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pizza")
    private Pizzaadicional pizzaadicional;

    public Pizza() {
    }

    public Pizza(Integer idPizza) {
        this.idPizza = idPizza;
    }

    public Pizza(Integer idPizza, double valor) {
        this.idPizza = idPizza;
        this.valor = valor;
    }

    public Integer getIdPizza() {
        return idPizza;
    }

    public void setIdPizza(Integer idPizza) {
        this.idPizza = idPizza;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Tipo getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Tipo idTipo) {
        this.idTipo = idTipo;
    }

    public Sabor getIdSabor() {
        return idSabor;
    }

    public void setIdSabor(Sabor idSabor) {
        this.idSabor = idSabor;
    }

    public Pizzaadicional getPizzaadicional() {
        return pizzaadicional;
    }

    public void setPizzaadicional(Pizzaadicional pizzaadicional) {
        this.pizzaadicional = pizzaadicional;
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
        if (!(object instanceof Pizza)) {
            return false;
        }
        Pizza other = (Pizza) object;
        if ((this.idPizza == null && other.idPizza != null) || (this.idPizza != null && !this.idPizza.equals(other.idPizza))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DAOS.Pizza[ idPizza=" + idPizza + " ]";
    }
    
}
