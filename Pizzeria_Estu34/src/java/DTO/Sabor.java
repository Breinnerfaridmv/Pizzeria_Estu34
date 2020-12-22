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
@Table(name = "sabor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sabor.findAll", query = "SELECT s FROM Sabor s")
    , @NamedQuery(name = "Sabor.findByIdSabor", query = "SELECT s FROM Sabor s WHERE s.idSabor = :idSabor")
    , @NamedQuery(name = "Sabor.findByDescripcion", query = "SELECT s FROM Sabor s WHERE s.descripcion = :descripcion")
    , @NamedQuery(name = "Sabor.findByImagen", query = "SELECT s FROM Sabor s WHERE s.imagen = :imagen")})
public class Sabor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_sabor")
    private Integer idSabor;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "imagen")
    private String imagen;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSabor")
    private List<Pizza> pizzaList;

    public Sabor() {
    }

    public Sabor(Integer idSabor) {
        this.idSabor = idSabor;
    }

    public Sabor(Integer idSabor, String descripcion, String imagen) {
        this.idSabor = idSabor;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public Integer getIdSabor() {
        return idSabor;
    }

    public void setIdSabor(Integer idSabor) {
        this.idSabor = idSabor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @XmlTransient
    public List<Pizza> getPizzaList() {
        return pizzaList;
    }

    public void setPizzaList(List<Pizza> pizzaList) {
        this.pizzaList = pizzaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSabor != null ? idSabor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sabor)) {
            return false;
        }
        Sabor other = (Sabor) object;
        if ((this.idSabor == null && other.idSabor != null) || (this.idSabor != null && !this.idSabor.equals(other.idSabor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DAOS.Sabor[ idSabor=" + idSabor + " ]";
    }
    
}
