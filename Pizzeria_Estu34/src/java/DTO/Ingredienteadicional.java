/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author PC
 */
@Entity
@Table(name = "ingredienteadicional")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ingredienteadicional.findAll", query = "SELECT i FROM Ingredienteadicional i")
    , @NamedQuery(name = "Ingredienteadicional.findByIdIngrediente", query = "SELECT i FROM Ingredienteadicional i WHERE i.idIngrediente = :idIngrediente")
    , @NamedQuery(name = "Ingredienteadicional.findByDescripcion", query = "SELECT i FROM Ingredienteadicional i WHERE i.descripcion = :descripcion")
    , @NamedQuery(name = "Ingredienteadicional.findByValor", query = "SELECT i FROM Ingredienteadicional i WHERE i.valor = :valor")})
public class Ingredienteadicional implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_ingrediente")
    private Integer idIngrediente;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "valor")
    private double valor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idIngrediente")
    private List<Pizzaadicional> pizzaadicionalList;

    public Ingredienteadicional() {
    }

    public Ingredienteadicional(Integer idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public Ingredienteadicional(Integer idIngrediente, String descripcion, double valor) {
        this.idIngrediente = idIngrediente;
        this.descripcion = descripcion;
        this.valor = valor;
    }

    public Integer getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(Integer idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @XmlTransient
    public List<Pizzaadicional> getPizzaadicionalList() {
        return pizzaadicionalList;
    }

    public void setPizzaadicionalList(List<Pizzaadicional> pizzaadicionalList) {
        this.pizzaadicionalList = pizzaadicionalList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idIngrediente != null ? idIngrediente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ingredienteadicional)) {
            return false;
        }
        Ingredienteadicional other = (Ingredienteadicional) object;
        if ((this.idIngrediente == null && other.idIngrediente != null) || (this.idIngrediente != null && !this.idIngrediente.equals(other.idIngrediente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DAOS.Ingredienteadicional[ idIngrediente=" + idIngrediente + " ]";
    }
    
}
